package test.livedata

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainPageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val factory = InjectorUtils.provideLoginViewModelFactory(applicationContext)
        viewModel = ViewModelProvider(this, factory).get(MainPageViewModel::class.java)

        viewModel.currentStatus.observe(this, Observer {
            when (it) {
                SocketConnectionStatus.CONNECTED -> {
                    connectedLayout.visibility = View.VISIBLE
                    disconnectedLayout.visibility = View.GONE
                }
                SocketConnectionStatus.DISCONNECTED -> {
                    val host =
                        getSharedPreferences("prefs", Context.MODE_PRIVATE).getString("host", "")
                    connectionStatusTV.text =
                        getString(R.string.couldnt_connection_to_the_server_s, host)
                    connectedLayout.visibility = View.GONE
                    disconnectedLayout.visibility = View.VISIBLE
                }
            }
        })

        viewModel.currentValue.observe(this, Observer {
            valueTV.text = it
        })

    }

    override fun onResume() {
        super.onResume()
        viewModel.connect()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_main_setting) {
            showIpConfig()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showIpConfig() {
        val prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Type the ip of the local socket.io server")
        val input = EditText(this@MainActivity)
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        input.layoutParams = lp
        input.setText(prefs.getString("host", ""))

        builder.setView(input)

        builder.setNegativeButton("cancel", null)

        builder.setPositiveButton("ok") { dialog, which ->
            input.text.toString()?.let {
                prefs.edit().putString("host", it).apply()
                viewModel.connect()
            }
        }
        builder.show()
    }
}
