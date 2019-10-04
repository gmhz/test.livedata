package test.livedata

import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import java.net.URISyntaxException

class MainPageViewModel(context: Context) : ViewModel() {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    val currentStatus: MutableLiveData<SocketConnectionStatus> =
        MutableLiveData(SocketConnectionStatus.DISCONNECTED).apply {
            this.value = SocketConnectionStatus.DISCONNECTED
        }
    val currentValue = MutableLiveData("")

    val handler = Handler()
    val connecter = object : Runnable {
        override fun run() {
            if (prefs.contains("host") && currentStatus.value == SocketConnectionStatus.DISCONNECTED) {
                try {
                    val opts = IO.Options()
                    opts.forceNew = true
                    opts.reconnection = true
                    val socket: Socket = IO.socket("http://" + prefs.getString("host", ""), opts)
                    socket.on(Socket.EVENT_CONNECT) {
                        currentStatus.postValue(SocketConnectionStatus.CONNECTED)
                    }.on("upd") {
                        Log.e("socket.io", it.toString())
                        val newValue = it[0] as String
                        currentValue.postValue(newValue)
                    }.on(Socket.EVENT_DISCONNECT) {
                        currentStatus.postValue(SocketConnectionStatus.DISCONNECTED)
                        handler.postDelayed(this, 500)
                    }.on(Socket.EVENT_CONNECT_ERROR) {
                        currentStatus.postValue(SocketConnectionStatus.DISCONNECTED)
                    }.on(Socket.EVENT_CONNECT_TIMEOUT) {
                        currentStatus.postValue(SocketConnectionStatus.DISCONNECTED)
                    }

                    socket.connect()
                } catch (e: URISyntaxException) {
                    currentStatus.postValue(SocketConnectionStatus.DISCONNECTED)
                    prefs.edit().remove("host").apply()
                }
            } else {
                currentStatus.postValue(SocketConnectionStatus.DISCONNECTED)
            }
        }
    }

    fun connect() {
        handler.postDelayed(connecter, 10)
    }

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}