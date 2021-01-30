package ai.fedml.fedmlsdk.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import ai.fedml.fedmlsdk.ContextHolder;

public class DeviceUtils {
    private static String sDeviceId;

    public static String getsDeviceId() {
        if (TextUtils.isEmpty(sDeviceId)) {
            sDeviceId = getImei(ContextHolder.getAppContext());
        }
        return sDeviceId;
    }

    public static String getImei(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("HardwareIds")
        String deviceId = telephonyManager.getDeviceId();
        // android 10 above replace by ANDROID_ID
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = Settings.System.getString(
                    context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceId;
    }
}
