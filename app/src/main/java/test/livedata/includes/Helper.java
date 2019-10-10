package test.livedata.includes;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import test.livedata.R;

;

public class Helper {
    public static final int APP_NOTIFICATION_ID = 442;
    public static final int SMART_NOTIFICATION_ID = 643;
    public static final int BALANCE_UPDATE_JOB_ID = 532;
    public static final String LOG_TAG = "MEGAMARKET-LOGTAG";
    public static int MAX_SPORTS_IN_PROFILE = 10;
    public static String ymdString = "yyyy-MM-dd";
    public static String ymdTHmsSString = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static String ymdTHmsSZString = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static String ymdHmsSString = "yyyy-MM-dd HH:mm:ss.SSS";
    public static SimpleDateFormat YMdSDF = new SimpleDateFormat(ymdString);
    public static SimpleDateFormat ymdTHmsS = new SimpleDateFormat(ymdTHmsSString);
    public static SimpleDateFormat hmdMY = new SimpleDateFormat("HH:mm dd-MM-yyyy");
    public static SimpleDateFormat hmsdMY = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
    public static SimpleDateFormat dMYhms = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    public static SimpleDateFormat ms = new SimpleDateFormat("mm:ss");
    public static SimpleDateFormat hms = new SimpleDateFormat("HH:mm:ss");
    public static SimpleDateFormat hmMY = new SimpleDateFormat("HH:mm");
    public static final float DISPLACEMENT = 1;
    @Nullable
    public static final String LOW_CHANNEL_ID = "low_importance_channel_id";
    @Nullable
    public static final String LOW_CHANNEL_DESC = "Low importance notifications";
    @Nullable
    public static final String DEF_CHANNEL_ID = "importance_channel_id";
    @Nullable
    public static final String DEF_CHANNEL_DESC = "Default importance notifications";
    public static final int CHOOSE_PLACE_CODE = 32;
    public static final int CHOOSE_SPORT_CODE = 33;
    public static final int CHOOSE_MULTIPLE_SPORT_CODE = 33;
    public static final int CHOOSE_COUNTRY_CODE = 34;
    public static final int CHOOSE_CITY_CODE = 35;
    public static String AUTH_TOKEN = "";
    public static final int DEFAULT_POSTERS_HEIGHT = 132;
    public static final int OPENABLE_GIFT_PROGRESS = 70;
    public static final int CODE_AC_SETTINGS = 231;
    public static final int REQUEST_USSD_TARIFF_CODE = 478;

    public static boolean notEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }

    public static String md5(String in) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(in.getBytes());
            byte[] a = md.digest();
            int len = a.length;
            StringBuilder sb = new StringBuilder(len << 1);
            for (int i = 0; i < len; i++) {
                sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
                sb.append(Character.forDigit(a[i] & 0x0f, 16));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static int getPx(Context context, int dp) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.getDisplayMetrics()
        );
    }

    @NotNull
    public static String _v9334(@Nullable String string) {
        return _x1452(string);
    }

    public static String _v1334(String _487s) {
        byte[] data = Base64.decode(_487s, Base64.DEFAULT);
        String text = null;
        try {
            text = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            text = "";
        }
        return text;
    }

    private static String _x1452(String s) {
        StringBuilder _4y4 = new StringBuilder();
        char[] _gh43 = s.toCharArray();
        for (int _h43a = 0; _h43a < _gh43.length - 1; _h43a += 2) {
            int _y43 = Character.digit(_gh43[_h43a], 16);
            int _o22 = Character.digit(_gh43[_h43a + 1], 16);
            int _y2gc = _y43 * 16 + _o22;
            _4y4.append((char) _y2gc);
        }
        return _4y4.toString();
    }

    public static boolean isValidURL(String url) {
        return Patterns.WEB_URL.matcher(url).matches();
    }

    public static int getDps(Context mContext, int value) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (value * scale + 0.5f);
    }

    public static void showDialog(Context mContext, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        try {
            dialog.show();
        } catch (Exception e) {

        }
    }

    public static void showDialog(Context mContext, String msg, DialogInterface.OnClickListener onOk) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(msg);
        builder.setPositiveButton(R.string.ok, onOk);
        builder.setNegativeButton(R.string.cancel, null);
        AlertDialog dialog = builder.create();
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showUncancelableDialog(Context mContext, String msg, DialogInterface.OnClickListener onOk) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(msg);
        builder.setPositiveButton(R.string.ok, onOk);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showDialog(Context mContext, String msg, DialogInterface.OnClickListener onOk, DialogInterface.OnClickListener onNo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(msg);
        builder.setPositiveButton(R.string.yes, onOk);
        builder.setNegativeButton(R.string.no, onNo);
        AlertDialog dialog = builder.create();
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showDialog(Context mContext, String title, String msg, boolean cancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        if (!title.isEmpty()) {
            builder.setTitle(title);
        }
        builder.setMessage(msg);
        builder.setCancelable(cancelable);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        try {
            dialog.show();
        } catch (Exception e) {

        }
    }

    public static void checkGPS(final Activity activity) {
        boolean gpsEnabled = false;
        boolean networkEnabled = false;

        LocationManager lm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        try {
            networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gpsEnabled && !networkEnabled) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(R.string.attention);
            builder.setMessage(R.string.enable_gps);
            builder.setPositiveButton(R.string.enable, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    public static boolean isLocationServicesEnabled(Activity activity) {
        boolean gpsEnabled = false;
        boolean networkEnabled = false;

        LocationManager lm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        try {
            networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        return gpsEnabled || networkEnabled;
    }

    public static void showDialog(Context mContext, int title, int msg, boolean cancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        if (title != 0) {
            builder.setTitle(title);
        }
        builder.setMessage(msg);
        builder.setCancelable(cancelable);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        try {
            dialog.show();
        } catch (Exception e) {

        }
    }

    public static String formatCardNumber(String value) {
        String input = value.replaceFirst(value.substring(6, 12), "******");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (i % 4 == 0 && i != 0) {
                result.append(" ");
            }
            result.append(input.charAt(i));
        }
        return result.toString();
    }

    public static boolean isJSONValid(String string) {
        try {
            new JSONObject(string);
        } catch (JSONException ex) {
            try {
                new JSONArray(string);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    public static boolean isJSONObjectValid(String string) {
        try {
            new JSONObject(string);
        } catch (JSONException ex) {
            return false;
        }
        return true;
    }

    public static String getFlagUrl(String iso) {
        return "http://flags.fmcdn.net/data/flags/h80/" + iso.toLowerCase() + ".png";
    }

    public static String helpCall(String number) {
        String num;
        if (number.charAt(0) == '+')
            num = number.substring(1, number.length());
        else
            num = "";
        return num;
    }

    public static String getDurationString(int seconds) {

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        if (hours == 0) {
            return twoDigitString(minutes) + ":" + twoDigitString(seconds);
        }
        return twoDigitString(hours) + ":" + twoDigitString(minutes) + ":" + twoDigitString(seconds);
    }

    private static String twoDigitString(int number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }

    public static String getConnectivityInfo(Context context) {

        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connManager.getActiveNetworkInfo();
        if (mobile != null && mobile.isAvailable() && mobile.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
            int type = mobile.getType();
            int subType = mobile.getSubtype();
            if (type == TelephonyManager.NETWORK_TYPE_HSDPA) {
                return "3G" + " " + mobile.getSubtypeName();
            } else if (type == TelephonyManager.NETWORK_TYPE_HSPAP) {
                return "4G" + " " + mobile.getSubtypeName();
            } else if (type == TelephonyManager.NETWORK_TYPE_EDGE) {
                return "2G" + " " + mobile.getSubtypeName();
            }
            return mobile.getTypeName() + " " + mobile.getSubtypeName();
        }
        if (mWifi.isAvailable() && mWifi.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
            return "WiFi";
        }
        try {
            TelephonyManager mgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            int networkType = mgr.getNetworkType();
            if (networkType == TelephonyManager.NETWORK_TYPE_GPRS) {
                Class cmClass = Class.forName(connManager.getClass().getName());
                Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
                method.setAccessible(true);
                if ((Boolean) method.invoke(connManager)) {
                    return "GPRS";
                }
            }
            return "No connection";

        } catch (Exception e) {
            return "Error";
        }

    }

    @SuppressLint("MissingPermission")
    public static int getCellSignalStrength(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return -1;
        }
        try {

            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                for (final CellInfo info : tm.getAllCellInfo()) {
                    if (info instanceof CellInfoGsm) {
                        final CellSignalStrengthGsm gsm = ((CellInfoGsm) info).getCellSignalStrength();
                        gsm.getDbm();
                    } else if (info instanceof CellInfoCdma) {
                        final CellSignalStrengthCdma cdma = ((CellInfoCdma) info).getCellSignalStrength();
                        return cdma.getDbm();
                    } else if (info instanceof CellInfoLte) {
                        CellInfoLte cellInfoLte = (CellInfoLte) tm.getAllCellInfo().get(0);
                        return cellInfoLte.getCellSignalStrength().getDbm();
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        if (info instanceof CellInfoWcdma) {
                            CellInfoWcdma cellInfoLte = (CellInfoWcdma) tm.getAllCellInfo().get(0);
                            return cellInfoLte.getCellSignalStrength().getDbm();
                        } else {
                            throw new Exception("Unknown type of cell signal!");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String getMacAddress(Context context) {
        WifiManager manager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        return info.getMacAddress();
    }

    public static final long SECOND_MILLIS = 1000;
    public static final long MINUTE_MILLIS = 60 * SECOND_MILLIS;
    public static final long HOUR_MILLIS = 60 * MINUTE_MILLIS;
    public static final long DAY_MILLIS = 24 * HOUR_MILLIS;


    public static String serialize(Object object) {
        Gson gson = new Gson();
        JsonElement je = gson.toJsonTree(object);
        return je.getAsJsonObject().toString();
    }

    public static void showToast(Context mContext, String message) {
        if (mContext == null) return;
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context mContext, int message_id) {
        if (mContext == null) return;
        Toast.makeText(mContext, message_id, Toast.LENGTH_SHORT).show();
    }

    public static void showSnackbar(Context mContext, CoordinatorLayout coordinatorContainer, String string) {
        Snackbar snack = Snackbar.make(coordinatorContainer, string, Snackbar.LENGTH_LONG);
        snack.getView().setBackgroundColor(ContextCompat.getColor(mContext, R.color.app_red));
        View view = snack.getView();
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) view.getLayoutParams();

        TypedValue tv = new TypedValue();
        if (mContext.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            params.topMargin = TypedValue.complexToDimensionPixelSize(tv.data, mContext.getResources().getDisplayMetrics());
        }
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
//        snack.getView().setVisibility(View.INVISIBLE);
//        snack.addCallback(new Snackbar.Callback() {
//            @Override
//            public void onShown(Snackbar sb) {
//                super.onShown(sb);
//                sb.getView().setVisibility(View.VISIBLE);
//                new Handler().postDelayed((Runnable) () -> snack.getView().setVisibility(View.INVISIBLE), Snackbar.LENGTH_LONG);
//            }
//
//            @Override
//            public void onDismissed(Snackbar transientBottomBar, int event) {
//                super.onDismissed(transientBottomBar, event);
//
//            }
//        });
        snack.show();
    }

    public static void showBlueSnackbar(Context mContext, CoordinatorLayout coordinatorContainer, String string) {
        Snackbar snack = Snackbar.make(coordinatorContainer, string, Snackbar.LENGTH_LONG);
        snack.getView().setBackgroundColor(ContextCompat.getColor(mContext, R.color.cyan));
        View view = snack.getView();
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) view.getLayoutParams();

        snack.show();
    }

    public static void showBlueSnackbarMainActivity(Context mContext, CoordinatorLayout coordinatorContainer, String string) {
        Snackbar snack = Snackbar.make(coordinatorContainer, string, Snackbar.LENGTH_LONG);
        snack.getView().setBackgroundColor(ContextCompat.getColor(mContext, R.color.cyan));
        View view = snack.getView();
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) view.getLayoutParams();

        float scale = mContext.getResources().getDisplayMetrics().density;
        int pixels = (int) (56 * scale + 0.5f);
        params.bottomMargin = params.topMargin + pixels;

        view.setLayoutParams(params);

        snack.show();
    }

    public static void showToastL(Context mContext, String message) {
        if (mContext == null) return;
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    public static void showToastL(Context mContext, int message_id) {
        if (mContext == null) return;
        Toast.makeText(mContext, message_id, Toast.LENGTH_LONG).show();
    }

    public static void showToast(Context mContext, Throwable t) {
        if (mContext == null) return;

        Toast.makeText(mContext, R.string.data_error, Toast.LENGTH_SHORT).show();
//        String msg = t.getClass().getSimpleName();
//        if (t.getCause() != null)
//            msg += ": " + t.getCause().getMessage();
//        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }

    public static long getDate(@NotNull String toString) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = f.parse(toString);
            return d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getTime(int timeInSeconds) {
        int minutes = timeInSeconds / 60;
        int seconds = timeInSeconds % 60;
        String mins = minutes < 10 ? "0" + String.valueOf(minutes) : String.valueOf(minutes);
        String secondsS = seconds < 10 ? "0" + String.valueOf(seconds) : String.valueOf(seconds);

        return String.format("%s:%s", mins, secondsS);
    }

//    public static float distanceInKM(LatLng ll1, LatLng ll2) {
//        Location loc1 = new Location("");
//        loc1.setLatitude(ll1.latitude);
//        loc1.setLongitude(ll1.longitude);
//
//        Location loc2 = new Location("");
//        loc2.setLatitude(ll2.latitude);
//        loc2.setLongitude(ll2.longitude);
//
//        float distanceInKm = loc1.distanceTo(loc2) / 1000;
//        return distanceInKm;
//    }

    public static boolean isGranted(List<String> permissions, Context context) {

        for (String p : permissions) {
            if (ContextCompat.checkSelfPermission(context, p) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static String convenientDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("EEE, MMM d - HH:mm");
        return format.format(date);
    }

    public static String dateMonthName(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MMM");
        return format.format(date);
    }

    public static String dateDayNum(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("d");
        return format.format(date);
    }

    public static boolean verifyFormat10PhoneNumber(String phoneStr) {
        return !"".equals(phoneStr) && phoneStr.matches("0((77[0-9])|(22[0-2])|(70[0-9])|(50[0-9])|(55[0-9])|(755)|(990)|(995)|(99[7-9]))\\d{6}");
    }

    public static boolean phNum12Verification(String phoneStr) {
        return !"".equals(phoneStr) && phoneStr.matches("996((77[0-9])|(22[0-2])|(70[0-9])|(50[0-9])|(55[0-9])|(755)|(990)|(995)|(99[7-9]))\\d{6}");
    }

    @NotNull
    public static String formatPhone12(String phoneNumber) {
        if (verifyFormat10PhoneNumber(phoneNumber)) {
            return "996" + phoneNumber.substring(1);
        }
        return "";
    }

    @NotNull
    public static String formatAnyNumberTo12(String phoneNumber) {
        if (!"".equals(phoneNumber)) {
            if (phoneNumber.matches("(00996)((77[0-9])|(22[0-2])|(70[0-9])|(50[0-9])|(55[0-9])|(755)|(990)|(995)|(99[7-9]))(\\d{6})")) {
                return phoneNumber.substring(2);
            } else if (phoneNumber.matches("(\\\\+996)((77[0-9])|(22[0-2])|(70[0-9])|(50[0-9])|(55[0-9])|(755)|(990)|(995)|(99[7-9]))(\\d{6})")) {
                return phoneNumber.substring(1);
            } else if (phoneNumber.matches("(0)((77[0-9])|(22[0-2])|(70[0-9])|(50[0-9])|(55[0-9])|(755)|(990)|(995)|(99[7-9]))(\\d{6})")) {
                return "996" + phoneNumber.substring(1);
            } else if (phoneNumber.matches("(996)((77[0-9])|(22[0-2])|(70[0-9])|(50[0-9])|(55[0-9])|(755)|(990)|(995)|(99[7-9]))(\\d{6})")) {
                return phoneNumber;
            }
        }
        return phoneNumber;
    }

    public static String formatAnyNumberTo10(String phoneNumber) {
        if (phoneNumber.length() > 9) {
            return "0" + formatAnyNumberTo12(phoneNumber).substring(3);
        } else {
            return phoneNumber;
        }
    }

    public static String formatAnyNumberTo10Spaced(String phoneNumber) {
        if (phoneNumber.length() > 9) {
            String num = "0" + formatAnyNumberTo12(phoneNumber).substring(3);
            StringBuilder spacedNum = new StringBuilder();
            int index = 0;
            for (char ch : num.toCharArray()) {
                spacedNum.append(ch);
                index++;

                if (index == 1)
                    spacedNum.append(" (");
                if (index == 4)
                    spacedNum.append(") ");
                if (index == 7)
                    spacedNum.append(" ");
            }
            return spacedNum.toString();
        } else {
            return phoneNumber;
        }
    }

    public static boolean isMegacomPhoneNumber(String phoneStr) {
        return !"".equals(phoneStr) && phoneStr.matches("(00996|\\+996|0)((55[0-9])|(755)|(990)|(995)|(99[7-9]))(\\d{6})");
    }

    @NotNull
    public static String formatPlusPhone12(String phoneNumber) {
        if (verifyFormat10PhoneNumber(phoneNumber)) {
            return "+996" + phoneNumber.substring(1);
        }
        return "";
    }

    @NotNull
    public static String formatPhone10(String phoneNumber) {
        if (phNum12Verification(phoneNumber)) {
            return "0" + phoneNumber.substring(3);
        }
        return "";
    }

    public static String parseSms(Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] puds = (Object[]) bundle.get("pdus");
                if (puds != null) {
                    for (int i = 0; i < puds.length; i++) {
                        // do anything with SMS
                        SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) puds[i]);
                        String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                        String message = currentMessage.getDisplayMessageBody();

                        Pattern pattern = Pattern.compile("(\\d{4})");
                        Matcher matcher = pattern.matcher(message);

                        while (matcher.find()) {
                            if (matcher.group(1) != null) {
                                return matcher.group(1);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static String getTimeAgo(long updatedAt, Context context) {
        long time = updatedAt;
        if (time < 1000000000000L) {
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return context.getString(R.string.in_the_future);
        }

        long diff = now - time;
        if (diff < MINUTE_MILLIS) return context.getString(R.string.moments_ago);
        else if (diff < 2 * MINUTE_MILLIS) return context.getString(R.string.a_minute_ago);
        else if (diff < 60 * MINUTE_MILLIS)
            return context.getString(R.string.minutes_ago, diff / MINUTE_MILLIS);
        else if (diff < 2 * HOUR_MILLIS) return context.getString(R.string.an_hour_ago);
        else if (diff < 24 * HOUR_MILLIS)
            return context.getString(R.string.hours_ago, diff / HOUR_MILLIS);
        else if (diff < 48 * HOUR_MILLIS) return context.getString(R.string.yesterday);
        else return context.getString(R.string.days_ago, diff / DAY_MILLIS);
    }

    public static boolean isMIUI(Context context) {
        for (PackageInfo pi : context.getPackageManager().getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES)) {
            if (pi.packageName.startsWith("com.miui.")) {
                return true;
            }
        }
        return false;
    }
}
