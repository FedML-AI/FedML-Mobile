package ai.fedml.iot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class IOTPluginImpl {
    public static final String COMMON_TAG = "iovplugin_";
    private String TAG = COMMON_TAG + getClass().getSimpleName();
    public static String BASE_URL_IOT_APP_SERVICE = "http://app.iotsupercloud.com";
    public static String BASE_URL_IOT_MQ_SERVICE = "tcp://mq.iotsupercloud.com:1883";

    private Context mContext;

    public IOTPluginImpl(Context context) {
        mContext = context;
    }

    public void init() {
        Log.d(TAG, "initial app domain = " + BASE_URL_IOT_APP_SERVICE);
        Log.d(TAG, "initial data domain = " + BASE_URL_IOT_MQ_SERVICE);

        //init MQTT

        //register broadcast
        registerBroadcast();
    }

    public void unInit() {
        unRegisterBroadcast();
    }

    private void registerBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        //filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_BOOT_COMPLETED);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        mContext.registerReceiver(mSystemBroadcastReceiver, filter);
    }

    private void unRegisterBroadcast() {
        try {
            mContext.unregisterReceiver(mSystemBroadcastReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e(TAG, "unRegisterBroadcast");
    }

    //SCREEN_ON -> ACTION_POWER_CONNECTED -> CONNECTIVITY_CHANGE(true)
    //CONNECTIVITY_CHANGE(true) -> CONNECTIVITY_CHANGE(false) -> SCREEN_OFF
    private BroadcastReceiver mSystemBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "ACTION = " + intent.getAction());
            if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {

            } else if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {

                networking();

            } else if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo info = connectivityManager.getActiveNetworkInfo();
                if (info != null && info.isAvailable()) {
                    onNetworkChanged(true, info.getType());
                } else {
                    onNetworkChanged(false, -1);
                }
            }
        }
    };

    private void onNetworkChanged(boolean isConnected, int networkType) {
        Log.d(TAG, "onNetworkChanged. isConnected = " + isConnected);
        if (isConnected) {
            networking();
        }
    }

    private void networking() {
        reconnectMQTT();
    }

    private void reconnectMQTT() {
        Log.e(TAG, "reconnectMQTT");
    }
}