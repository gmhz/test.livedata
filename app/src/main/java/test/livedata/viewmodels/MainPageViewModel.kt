package test.livedata.viewmodels

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
import org.json.JSONException
import test.livedata.models.DatedLocation
import test.livedata.models.LivePersonLocation
import java.net.URISyntaxException
import java.util.*
import kotlin.collections.HashMap
import org.json.JSONObject
import test.livedata.enums.SocketConnectionStatus

class MainPageViewModel(context: Context) : ViewModel() {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    val currentStatus: MutableLiveData<SocketConnectionStatus> =
        MutableLiveData(SocketConnectionStatus.DISCONNECTED).apply {
            this.value = SocketConnectionStatus.DISCONNECTED
        }
    var datedItemsHashMap = HashMap<String, DatedLocation>()
    val liveListOfLocations = MutableLiveData<List<LivePersonLocation>>()

    val clearerHandler = Handler()
    val clearer = object : Runnable {
        override fun run() {
            val newHashMap = HashMap<String, DatedLocation>()
            for (key in datedItemsHashMap.keys) {
                datedItemsHashMap[key]?.let { localValue ->
                    newHashMap[key]?.let { value ->
                        if (localValue.trackDate > Date(Date().time - 1000 * 7)) {
                            newHashMap[key] =
                                DatedLocation(value.trackDate, value.location)
                        }
                    }
                }
            }
            datedItemsHashMap = newHashMap
            liveListOfLocations.postValue(datedItemsHashMap.values.map { dl -> dl.location })

            clearerHandler.postDelayed(this, 1000)
        }
    }

    val connectHandler = Handler()
    val connecter = object : Runnable {
        override fun run() {
            if (prefs.contains("host") && prefs.contains("username")) {
                if (currentStatus.value == SocketConnectionStatus.DISCONNECTED) {
                    try {
                        val opts = IO.Options()
                        opts.reconnection = true
                        val socket: Socket =
                            IO.socket("http://" + prefs.getString("host", ""), opts)
                        socket.on(Socket.EVENT_CONNECT) {
                            currentStatus.postValue(SocketConnectionStatus.CONNECTED)
                            clearerHandler.postDelayed(clearer, 1000)
                        }.on("new_location") {
                            if (it != null && it[0] != null) {
                                try {
                                    val obj = it[0] as JSONObject
                                    Log.e("socket.io", obj.toString())
                                    val newLoc = LivePersonLocation(
                                        obj.getString("username"),
                                        obj.getDouble("lat"),
                                        obj.getDouble("lng"),
                                        obj.getInt("speed"),
                                        obj.getDouble("bearing").toFloat()
                                    )

                                    datedItemsHashMap[newLoc.username] =
                                        DatedLocation(Date(), newLoc)
                                    liveListOfLocations.postValue(datedItemsHashMap.values.map { dl -> dl.location })
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }
                            }
                        }.on(Socket.EVENT_DISCONNECT) {
                            clearerHandler.removeCallbacks(clearer)
                            currentStatus.postValue(SocketConnectionStatus.DISCONNECTED)
                            connectHandler.postDelayed(this, 500)
                        }.on(Socket.EVENT_CONNECT_ERROR) {
                            clearerHandler.removeCallbacks(clearer)
                            currentStatus.postValue(SocketConnectionStatus.DISCONNECTED)
                        }.on(Socket.EVENT_CONNECT_TIMEOUT) {
                            clearerHandler.removeCallbacks(clearer)
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
            } else {
                currentStatus.postValue(SocketConnectionStatus.FILL_FORM)
            }
        }
    }

    fun connect() {
        if (currentStatus.value != SocketConnectionStatus.CONNECTED)
            connectHandler.postDelayed(connecter, 10)
    }

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
        connectHandler.removeCallbacksAndMessages(null)
    }

}