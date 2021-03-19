package ai.fedml.iot.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import ai.fedml.iot.device.DeviceUtil;
import ai.fedml.iot.utils.ServiceUtils;

abstract class BaseServiceManager {

    private final String TAG = BaseServiceManager.this.getClass().getSimpleName();
    private Context mContext;
    private String mServiceAction;
    private boolean isDestroy = false;
    private boolean mServiceConnected = false;

    protected Handler mMainHandler = new Handler(Looper.getMainLooper());

    private ServiceKeepAliveThread mKeepAliveThread = null;

    private class ServiceKeepAliveThread extends Thread {

        public boolean mIsRunning = true;

        @Override
        public void run() {
            Log.d(TAG, "SDK ServiceKeepAliveThread start Success, mIsRunning = " + mIsRunning);
            try {
                while (mIsRunning) {
                    checkServiceState();
                    Thread.sleep(2000);
                }
            } catch (InterruptedException e) {
                Log.i(TAG, e.toString());
            }
        }

        public void stopThread() {
            mIsRunning = false;
            Log.d(TAG, "stop SDK ServiceKeepAliveThread");
        }
    }

    private void checkServiceState() {
        if (!mServiceConnected) {
            Log.v(TAG, "checkServiceState, mServiceConnected " + mServiceConnected + ", isDestroy = " + isDestroy + ", mContext = " + mContext);
            if (!isDestroy && mContext != null) {
                startAndBindService();
            }
        }
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected, isDestroy = " + isDestroy + ", mServiceConnected = " + mServiceConnected);
            mServiceConnected = false;
            BaseServiceManager.this.onServiceDisconnected(name);
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected, isDestroy = " + isDestroy + ", mServiceConnected = " + mServiceConnected + ", action = " + mServiceAction);
            mServiceConnected = true;

            if (isDestroy) {
                Context context = mContext;
                if (context != null) {
                    context.unbindService(this);
                }
            } else {
                onServiceConnectedOk(service);
            }
        }
    };

    private BaseServiceManager() {
    }

    protected BaseServiceManager(String serviceAction) {
        mServiceAction = serviceAction;
    }

    public void init(Context context) {
        synchronized (this) {
            Log.i(TAG, "init start");
            if (context == null) {
                throw new RuntimeException("context is null");
            }
            mContext = context;
            isDestroy = false;
            startAndBindService();
            mMainHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mKeepAliveThread = new ServiceKeepAliveThread();
                    mKeepAliveThread.start();
                }
            }, 5000);
            Log.i(TAG, "init end");
        }
    }

    public void destroy() {
        synchronized (this) {
            isDestroy = true;
            if (mContext != null) {
                if (mServiceConnected) {
                    mContext.unbindService(mServiceConnection);
                }
            }
            mContext = null;
            if (mKeepAliveThread != null) {
                mKeepAliveThread.stopThread();
                mKeepAliveThread = null;
            }
            dispose();
        }
    }

    public void unbindService() {
        if (mServiceConnected) {
            mContext.unbindService(mServiceConnection);
            mServiceConnected = false;
        }
    }

    private void startAndBindService() {
        Context context = mContext;
        if (context == null) {
            Log.d(TAG, "startAndBindService, context = null, return");
            return;
        }
        String hostPkg = DeviceUtil.getAppPackageName(context);
        Log.d(TAG, "hostPkg = " + hostPkg);
        Intent intent = getMatchIntent(context, mServiceAction);
        try {
            if (intent != null) {
                //context.startService(intent);
                boolean ret = context.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
                Log.e(TAG, "bindService startService and bind " + ret);
            } else {
                Log.e(TAG, "can not find intent for action " + mServiceAction + " in " + hostPkg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "bindService error", e);
        }
    }

    protected Intent getMatchIntent(Context context, String action) {
        String hostPkg = DeviceUtil.getAppPackageName(context);
        Intent intent = ServiceUtils.getMatchIntentInPackage(context, new Intent(action), hostPkg);
        Log.i(TAG, "bindService, hostPkg = " + hostPkg + " intent:" + intent);
        return intent;
    }

    protected abstract void onServiceConnectedOk(IBinder service);


    protected abstract void onServiceDisconnected(ComponentName name);

    protected abstract void dispose();
}
