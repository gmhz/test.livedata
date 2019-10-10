package test.livedata.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import test.livedata.includes.Helper
import test.livedata.includes.Key
import java.util.*

class MyAccountManager(val context: Context) {

    fun clear() {
        prefs.edit().clear().apply()
    }

    var hasLocalData: Boolean
        get() = prefs.getBoolean(Key.HAS_LOCAL_DATA, false)
        set(value) = prefs.edit().putBoolean(Key.HAS_LOCAL_DATA, value).apply()

    var contactsExpandedMenuShown: Boolean
        get() = prefs.getBoolean(Key.CONTACTS_EXPANDED_MENU_SHOWN, false)
        set(value) = prefs.edit().putBoolean(Key.CONTACTS_EXPANDED_MENU_SHOWN, value).apply()

    var justAuthorized: Boolean
        get() = prefs.getBoolean(Key.JUST_AUTHORIZED, false)
        set(value) = prefs.edit().putBoolean(Key.JUST_AUTHORIZED, value).apply()

    var introShown: Boolean
        get() = prefs.getBoolean(Key.INTRO_SHOWN, false)
        set(value) = prefs.edit().putBoolean(Key.INTRO_SHOWN, value).apply()

    var receivePush: Boolean
        get() = prefs.getBoolean(Key.RECEIVE_PUSH, true)
        set(value) = prefs.edit().putBoolean(Key.RECEIVE_PUSH, value).apply()

    var showSmartNotification: Boolean
        get() = prefs.getBoolean(Key.SHOW_NOTIFICATION, true)
        set(value) = prefs.edit().putBoolean(Key.SHOW_NOTIFICATION, value).apply()

    var versionCode: Int
        get() = prefs.getInt(Key.VERSION_CODE, 0)
        set(value) = prefs.edit().putInt(Key.VERSION_CODE, value).apply()

    var versionUpdatedAtMillis: Long
        get() = prefs.getLong(Key.VERSION_UPDATED_AT_MILLIS, 0)
        set(value) = prefs.edit().putLong(Key.VERSION_UPDATED_AT_MILLIS, value).apply()

    var floatingOrderWindowX: Int
        get() = prefs.getInt(Key.FLOATING_ORDER_WINDOW_X_POS, 0)
        set(value) = prefs.edit().putInt(Key.FLOATING_ORDER_WINDOW_X_POS, value).apply()

    var floatingOrderWindowY: Int
        get() = prefs.getInt(Key.FLOATING_ORDER_WINDOW_Y_POS, 0)
        set(value) = prefs.edit().putInt(Key.FLOATING_ORDER_WINDOW_Y_POS, value).apply()

    private val prefs = context.getSharedPreferences(Key.AUTH_PREFS, MODE_PRIVATE)

    var phoneNumber: String
        get() = prefs.getString(Key.PHONE_NUMBER, "")!!
        set(value) = prefs.edit().putString(Key.PHONE_NUMBER, value).apply()

    var lastAuthMillis: Long
        get() = prefs.getLong(Key.LAST_AUTH_DATE, 0)
        set(value) = prefs.edit().putLong(Key.LAST_AUTH_DATE, value).apply()

    var lastAdShownMillis: Long
        get() = prefs.getLong(Key.LAST_AD_SHOWN_DATE, 0)
        set(value) = prefs.edit().putLong(Key.LAST_AD_SHOWN_DATE, value).apply()

    var balanceUpdatedAtMillis: Long
        get() = prefs.getLong(Key.BALANCE_UPDATED_AT_MILLIS, 0)
        set(value) = prefs.edit().putLong(Key.BALANCE_UPDATED_AT_MILLIS, value).apply()

    var authToken: String
        get() = prefs.getString(Key.AUTH_TOKEN, "")!!
        set(value) = prefs.edit().putString(Key.AUTH_TOKEN, value).apply()

    var pushToken: String
        get() = prefs.getString(Key.PUSH_TOKEN, "")!!
        set(value) = prefs.edit().putString(Key.PUSH_TOKEN, value).apply()

    var userId: Int
        get() = prefs.getInt(Key.USER_ID, 0)
        set(value) = prefs.edit().putInt(Key.USER_ID, value).apply()

    var userBalanceId: Int
        get() = prefs.getInt(Key.USER_BALANCE_ID, 0)
        set(value) = prefs.edit().putInt(Key.USER_BALANCE_ID, value).apply()

    var username: String
        get() = prefs.getString(Key.USER_NAME, "")!!
        set(value) = prefs.edit().putString(Key.USER_NAME, value).apply()

    var userFullBalance: String
        get() = prefs.getString(Key.USER_FULL_BALANCE, "")!!
        set(value) = prefs.edit().putString(Key.USER_FULL_BALANCE, value).apply()

    var userAvailableBalance: String
        get() = prefs.getString(Key.USER_AVAILABLE_BALANCE, "")!!
        set(value) = prefs.edit().putString(Key.USER_AVAILABLE_BALANCE, value).apply()

    var userTariffStr: String
        get() = prefs.getString(Key.USER_TARIFF, "")!!
        set(value) = prefs.edit().putString(Key.USER_TARIFF, value).apply()

    var currentUserTariffStr: String
        get() = prefs.getString(Key.CURRENT_USER_TARIFF, "")!!
        set(value) = prefs.edit().putString(Key.CURRENT_USER_TARIFF, value).apply()

    var servicesListStr: String
        get() = prefs.getString(Key.SERVICES_LIST, "")!!
        set(value) = prefs.edit().putString(Key.SERVICES_LIST, value).apply()

    var megacomServiceAdsStr: String
        get() = prefs.getString(Key.MEGACOM_SERVICES_ADS_STR, "")!!
        set(value) = prefs.edit().putString(Key.MEGACOM_SERVICES_ADS_STR, value).apply()

    var megacomTariffAdsStr: String
        get() = prefs.getString(Key.MEGACOM_TARIFFS_ADS_STR, "")!!
        set(value) = prefs.edit().putString(Key.MEGACOM_TARIFFS_ADS_STR, value).apply()

    var favoritesSet: MutableSet<String>
        get() = prefs.getStringSet(Key.FAVORITE_CONTACTS, emptySet())!!
        set(value) = prefs.edit().putStringSet(Key.FAVORITE_CONTACTS, value).apply()

    fun loggedIn(): Boolean = authToken != ""

    fun favoritesCount() = favoritesSet.size

    fun logOutWithBroadcast() {
        authToken = ""
        context.sendBroadcast(Intent(Key.SESSION_EXPIRED))
    }

    fun tokenHash(): String {
        return Helper.md5(authToken)
    }

    fun canTransferMoney(): Boolean {
        if (loggedIn()) {
            if (userFullBalance.toFloatOrNull() != null) {
                return userFullBalance.toFloat() > 130
            }
            return true
        }
        return true
    }

    var instagramConnected: Boolean
        get() = prefs.getBoolean(Key.INSTAGRAM_CONNECTED, false)
        set(value) = prefs.edit().putBoolean(Key.INSTAGRAM_CONNECTED, value).apply()

    var dontAskOverlay: Boolean
        get() = prefs.getBoolean(Key.DONT_ASK_OVERLAY, false)
        set(value) = prefs.edit().putBoolean(Key.DONT_ASK_OVERLAY, value).apply()

}