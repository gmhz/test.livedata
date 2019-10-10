package test.livedata.ui

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
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*
import utils.InjectorUtils
import test.livedata.viewmodels.MainPageViewModel
import test.livedata.R
import test.livedata.enums.SocketConnectionStatus

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    var map: GoogleMap? = null
    lateinit var viewModel: MainPageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addMapFragment()

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
                SocketConnectionStatus.FILL_FORM -> {
                    connectionStatusTV.text = getString(R.string.fill_host_ip_and_username)
                    connectedLayout.visibility = View.GONE
                    disconnectedLayout.visibility = View.VISIBLE
                }
            }
        })

        val markers = HashMap<String, Marker?>()
        viewModel.liveListOfLocations.observe(this, Observer {
            val toDelete = HashMap(markers)
            for (location in it) {
                val mo = MarkerOptions()
                    .position(LatLng(location.lat, location.lng))
                    .rotation(location.bearing)
                    .title(location.username)
                if (!markers.containsKey(location.username)) {
                    markers[location.username] = map?.addMarker(mo)
                } else {
                    markers[location.username]?.let { m ->
                        m.position = LatLng(location.lat, location.lng)
                        m.rotation = location.bearing
                    }
                }

                if (toDelete.containsKey(location.username)) {
                    toDelete.remove(location.username)
                }
            }

//            for (key in toDelete.keys) {
//                markers[key]?.remove()
//            }
        })

    }

    private fun addMapFragment() {
        val mMapFragment = SupportMapFragment.newInstance()
        mMapFragment.getMapAsync(this)
        supportFragmentManager.beginTransaction()
            .replace(R.id.map, mMapFragment)
            .commit()
    }

    override fun onMapReady(gMap: GoogleMap) {
        map = gMap
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
        builder.setTitle("Configuration")
        val inputIp = EditText(this@MainActivity)
        inputIp.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        inputIp.setText(prefs.getString("host", ""))
        inputIp.hint = "IP of the server"

        val inputNick = EditText(this@MainActivity)
        inputNick.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        inputNick.setText(prefs.getString("username", ""))
        inputNick.hint = "Type username"

        val ll = LinearLayout(this@MainActivity)
        ll.orientation = LinearLayout.VERTICAL
        ll.addView(inputIp)
        ll.addView(
            View(this@MainActivity).apply {
                layoutParams =
                    LinearLayout.LayoutParams(1, (8 * resources.displayMetrics.density).toInt())
            }
        )
        ll.addView(inputNick)
        builder.setView(ll)

        builder.setNegativeButton("cancel", null)

        builder.setPositiveButton("ok") { dialog, which ->
            inputIp.text.toString()?.let {
                prefs.edit().putString("host", it).apply()
                viewModel.connect()
            }

            inputNick.text.toString()?.let {
                prefs.edit().putString("username", it).apply()
                viewModel.connect()
            }
        }
        builder.show()
    }
}
