package test.livedata

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import androidx.multidex.MultiDexApplication
import modules.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import test.livedata.includes.Helper
import timber.log.Timber
import utils.InternetUtil

class MyApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.tag("debug->$packageName->")
        }

        startKoin {
            androidContext(this@MyApplication)
            modules(appModule)
        }

        InternetUtil.init(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            var defaultImportanceChannel: NotificationChannel? =
                notificationManager.getNotificationChannel(Helper.DEF_CHANNEL_ID)
            if (defaultImportanceChannel == null) {
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                defaultImportanceChannel =
                    NotificationChannel(Helper.DEF_CHANNEL_ID, Helper.DEF_CHANNEL_DESC, importance)
                defaultImportanceChannel.lightColor = Color.YELLOW
                defaultImportanceChannel.enableVibration(false)
                notificationManager.createNotificationChannel(defaultImportanceChannel)
            }

            var lowImportanceChannel: NotificationChannel? =
                notificationManager.getNotificationChannel(Helper.LOW_CHANNEL_ID)
            if (lowImportanceChannel == null) {
                val importance = NotificationManager.IMPORTANCE_LOW
                lowImportanceChannel =
                    NotificationChannel(Helper.LOW_CHANNEL_ID, Helper.LOW_CHANNEL_DESC, importance)
                lowImportanceChannel.lightColor = Color.GREEN
                lowImportanceChannel.setSound(null, null)
                lowImportanceChannel.enableVibration(false)
                notificationManager.createNotificationChannel(lowImportanceChannel)
            }
        }
    }
}