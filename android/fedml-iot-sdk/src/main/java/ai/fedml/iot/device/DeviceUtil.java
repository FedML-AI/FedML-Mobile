package ai.fedml.iot.device;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

public class DeviceUtil {
    private static final String TAG = DeviceUtil.class.getSimpleName();

    private static String sAppVersionName;
    private static String sPackageName;
    private static int sAppVersionCode;

    public static String getBrand() {
        String brand = Build.BRAND;
        if (brand == null) {
            brand = "";
        }
        return brand;
    }

    public static String getManufacturer() {
        String manufacturer = Build.MANUFACTURER;
        if (manufacturer == null) {
            manufacturer = "";
        }
        return manufacturer;
    }

    public static String getModel() {
        String model = Build.MODEL;
        if (model == null) {
            model = "";
        }
        return model;
    }

    public static String getProduct() {
        String product = Build.PRODUCT;
        if (product == null) {
            product = "";
        }
        return product;
    }

    public static String getHardware() {
        String hardware = Build.HARDWARE;
        if (hardware == null) {
            hardware = "";
        }
        return hardware;
    }

    public static String getBoard() {
        String board = Build.BOARD;
        if (board == null) {
            board = "";
        }
        return board;
    }

    public static String getDevice() {
        String device = Build.DEVICE;
        if (device == null) {
            device = "";
        }
        return device;
    }

    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    public static int getAppVersionCode(Context context) {
        initAppVersion(context);
        return sAppVersionCode;
    }

    public static String getAppVersionName(Context context) {
        initAppVersion(context);
        return sAppVersionName;
    }

    public static String getAppPackageName(Context context) {
        initAppVersion(context);
        return sPackageName;
    }

    private static void initAppVersion(Context context) {
        if (!TextUtils.isEmpty(sPackageName)) {
            return;
        }
        PackageManager manager = context.getPackageManager();
        try {
            sPackageName = context.getPackageName();
            PackageInfo info = manager.getPackageInfo(sPackageName, 0);
            sAppVersionName = info.versionName;
            sAppVersionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            sAppVersionName = "NULL";
            sAppVersionCode = -1;
        }
    }


}
