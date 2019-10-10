package test.livedata.bg

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import test.livedata.ui.MainActivity
import test.livedata.R
import test.livedata.data.MyAccountManager
import test.livedata.includes.Helper
import test.livedata.includes.Key
import test.livedata.room.AppDatabase
import test.livedata.room.repos.LocationTrackRepository
import java.util.*
import kotlin.coroutines.CoroutineContext

class LocationTrackFgService : Service(), CoroutineScope, LocationListener {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    companion object {
        var SERVICE_ID = 432
        var ALARM_PENDING_INTENT_CODE = 572
    }

    private var job: Job = Job()

    val criteria = Criteria().apply {
        accuracy = Criteria.ACCURACY_FINE
        powerRequirement = Criteria.POWER_HIGH
        isAltitudeRequired = false
        isSpeedRequired = false
        isBearingRequired = false
        isCostAllowed = true
        horizontalAccuracy = Criteria.ACCURACY_HIGH
        verticalAccuracy = Criteria.ACCURACY_HIGH
    }
    val gpsFreqInMillis = 5000L
    val gpsFreqInDistance = 10F
    var locationFound = false
    lateinit var locationManager: LocationManager
    lateinit var locationTrackRepository: LocationTrackRepository
    private lateinit var myAccountManager: MyAccountManager


    override fun onCreate() {
        super.onCreate()

        myAccountManager = MyAccountManager(this)

        locationTrackRepository =
            LocationTrackRepository.getInstance(AppDatabase.getInstance(this.applicationContext).locationTrackDao())
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!myAccountManager.loggedIn() || !myAccountManager.showSmartNotification) {
            stopService()
        } else if (intent?.action != null && intent.action == Key.ACTION_EXIT) {
            stopService()
        } else {
            val mainActivityIntent = Intent(this, MainActivity::class.java)
            mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent = PendingIntent.getActivity(
                this,
                0,
                mainActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            startForeground(
                SERVICE_ID,
                showBalanceNotification()
                    .setContentIntent(pendingIntent)
                    .build()
            )
        }

        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED) {
            locationManager.removeUpdates(this)
            locationManager.requestLocationUpdates(
                gpsFreqInMillis,
                gpsFreqInDistance,
                criteria,
                this,
                null
            )
        }
        return super.onStartCommand(intent, flags, startId)
    }

    fun stopService() {
        stopForeground(true)
        stopSelf()
    }


    override fun onLocationChanged(location: Location?) {
        location?.apply {
            launch {
                locationTrackRepository.createTrack(
                    latitude,
                    longitude,
                    time
                )
            }
        }
    }

    private fun showBalanceNotification(): NotificationCompat.Builder {
        val mainActivityIntent = Intent(this, MainActivity::class.java)
        val contentIntent = PendingIntent.getActivity(this, 0, mainActivityIntent, 0)

        val mBuilder = NotificationCompat.Builder(this, Helper.LOW_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setSound(null)
            .setVibrate(null)
            .setOngoing(true)
            .setContentTitle("Your location has been shared")
            .setContentIntent(contentIntent)


        return mBuilder
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

    override fun onProviderEnabled(provider: String?) {}

    override fun onProviderDisabled(provider: String?) {}
}
