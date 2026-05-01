package com.jieli.jl_bt_ota.impl;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.jieli.jl_bt_ota.constant.BluetoothConstant;
import com.jieli.jl_bt_ota.interfaces.JieLiLibLoader;
import com.jieli.jl_bt_ota.util.BluetoothUtil;
import com.jieli.jl_bt_ota.util.CHexConver;
import com.jieli.jl_bt_ota.util.CommonUtil;
import com.jieli.jl_bt_ota.util.JL_Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class RcspAuth {
    private static final int AUTH_RETRY_COUNT = 2;
    private static final long DEFAULT_AUTH_TIMEOUT = 3000;
    private static long DELAY_AUTH_WAITING_TIME = 3000;
    public static final int ERR_AUTH_DATA_CHECK = 40980;
    public static final int ERR_AUTH_DATA_SEND = 40979;
    public static final int ERR_AUTH_DEVICE_TIMEOUT = 40977;
    public static final int ERR_AUTH_USER_STOP = 40978;
    public static final int ERR_NONE = 0;
    private static final int MSG_AUTH_DEVICE_TIMEOUT = 18;
    private static final int MSG_SEND_AUTH_DATA_TIMEOUT = 17;
    public static boolean SUPPORT_RESET_FLAG = false;
    private static final String TAG = "RcspAuth";
    private static volatile boolean mIsLibLoaded = false;
    public static final JieLiLibLoader sLocalLibLoader = new JieLiLibLoader() { // from class: com.jieli.jl_bt_ota.impl.RcspAuth$$ExternalSyntheticLambda0
        @Override // com.jieli.jl_bt_ota.interfaces.JieLiLibLoader
        public final void loadLibrary(String str) {
            System.loadLibrary(str);
        }
    };
    private final boolean isLibInit;
    private final Context mContext;
    private final IRcspAuthOp mIRcspAuthOp;
    private final List<OnRcspAuthListener> mOnRcspAuthListeners = Collections.synchronizedList(new ArrayList());
    private final Map<String, AuthTask> mAuthTaskMap = Collections.synchronizedMap(new HashMap());
    private final Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.jieli.jl_bt_ota.impl.RcspAuth.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            int i = message.what;
            if (i == 17) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) message.obj;
                AuthTask authTask = (AuthTask) RcspAuth.this.mAuthTaskMap.get(bluetoothDevice.getAddress());
                if (authTask == null) {
                    return false;
                }
                if (authTask.getRetryNum() < 2) {
                    authTask.setRetryNum(authTask.getRetryNum() + 1);
                    RcspAuth.this.sendAuthDataToDevice(authTask.getDevice(), authTask.getRandomData());
                    RcspAuth.this.mHandler.removeMessages(18);
                    RcspAuth.this.mHandler.sendMessageDelayed(RcspAuth.this.mHandler.obtainMessage(17, bluetoothDevice), RcspAuth.DELAY_AUTH_WAITING_TIME);
                } else {
                    RcspAuth.this.onAuthFailed(bluetoothDevice, RcspAuth.ERR_AUTH_DEVICE_TIMEOUT);
                }
            } else if (i == 18) {
                BluetoothDevice bluetoothDevice2 = (BluetoothDevice) message.obj;
                AuthTask authTask2 = (AuthTask) RcspAuth.this.mAuthTaskMap.get(bluetoothDevice2.getAddress());
                if (authTask2 != null && !authTask2.isAuthDevice()) {
                    RcspAuth.this.onAuthFailed(bluetoothDevice2, RcspAuth.ERR_AUTH_DEVICE_TIMEOUT);
                }
            }
            return true;
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    static class AuthTask {

        /* JADX INFO: renamed from: a */
        private BluetoothDevice f692a;

        /* JADX INFO: renamed from: b */
        private boolean f693b;

        /* JADX INFO: renamed from: c */
        private boolean f694c;

        /* JADX INFO: renamed from: d */
        private byte[] f695d;

        /* JADX INFO: renamed from: e */
        private int f696e;

        private AuthTask() {
        }

        public BluetoothDevice getDevice() {
            return this.f692a;
        }

        public byte[] getRandomData() {
            return this.f695d;
        }

        public int getRetryNum() {
            return this.f696e;
        }

        public boolean isAuthDevice() {
            return this.f694c;
        }

        public boolean isAuthProgressResult() {
            return this.f693b;
        }

        public void setAuthDevice(boolean z) {
            this.f694c = z;
        }

        public void setAuthProgressResult(boolean z) {
            this.f693b = z;
        }

        public AuthTask setDevice(BluetoothDevice bluetoothDevice) {
            this.f692a = bluetoothDevice;
            return this;
        }

        public AuthTask setRandomData(byte[] bArr) {
            this.f695d = bArr;
            return this;
        }

        public void setRetryNum(int i) {
            this.f696e = i;
        }

        public String toString() {
            return "AuthTask{device=" + this.f692a + ", isAuthProgressResult=" + this.f693b + ", isAuthDevice=" + this.f694c + ", randomData=" + CHexConver.byte2HexStr(this.f695d) + ", retryNum=" + this.f696e + '}';
        }
    }

    public interface IRcspAuthOp {
        boolean sendAuthDataToDevice(BluetoothDevice bluetoothDevice, byte[] bArr);
    }

    public interface OnRcspAuthListener {
        void onAuthFailed(BluetoothDevice bluetoothDevice, int i, String str);

        void onAuthSuccess(BluetoothDevice bluetoothDevice);

        void onInitResult(boolean z);
    }

    public RcspAuth(Context context, IRcspAuthOp iRcspAuthOp, OnRcspAuthListener onRcspAuthListener) {
        if (iRcspAuthOp == null) {
            throw new IllegalArgumentException("IRcspAuthOp can not be null.");
        }
        loadLibrariesOnce(null);
        this.isLibInit = nativeInit();
        this.mContext = context;
        this.mIRcspAuthOp = iRcspAuthOp;
        addListener(onRcspAuthListener);
    }

    private String getErrorMsg(int i) {
        switch (i) {
            case ERR_AUTH_DEVICE_TIMEOUT /* 40977 */:
                return "Auth device timeout.";
            case ERR_AUTH_USER_STOP /* 40978 */:
                return "User stop auth device.";
            case ERR_AUTH_DATA_SEND /* 40979 */:
                return "Failed to send data.";
            case ERR_AUTH_DATA_CHECK /* 40980 */:
                return "Check auth data error.";
            default:
                return "";
        }
    }

    private byte[] getResetAuthFlagCmdData() {
        return CHexConver.hexStr2Bytes("FEDCBAC00600020001EF");
    }

    private boolean isValidAuthData(byte[] bArr) {
        byte b;
        return (bArr == null || bArr.length == 0 || ((bArr.length != 5 || bArr[0] != 2) && (bArr.length != 17 || ((b = bArr[0]) != 0 && b != 1)))) ? false : true;
    }

    public static void loadLibrariesOnce(JieLiLibLoader jieLiLibLoader) {
        synchronized (RcspAuth.class) {
            if (!mIsLibLoaded) {
                if (jieLiLibLoader == null) {
                    jieLiLibLoader = sLocalLibLoader;
                }
                jieLiLibLoader.loadLibrary(BluetoothConstant.JL_AUTH);
                mIsLibLoaded = true;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAuthFailed(BluetoothDevice bluetoothDevice, int i) {
        onAuthFailed(bluetoothDevice, i, getErrorMsg(i));
    }

    private void onAuthSuccess(final BluetoothDevice bluetoothDevice) {
        JL_Log.m853w(TAG, "onAuthSuccess", CommonUtil.formatString("device = %s,  auth ok.", printDeviceInfo(bluetoothDevice)));
        this.mHandler.post(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.RcspAuth$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2671lambda$onAuthSuccess$2$comjielijl_bt_otaimplRcspAuth(bluetoothDevice);
            }
        });
        if (bluetoothDevice != null) {
            this.mAuthTaskMap.remove(bluetoothDevice.getAddress());
        }
    }

    private void onInitResult(final boolean z) {
        this.mHandler.post(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.RcspAuth$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2672lambda$onInitResult$1$comjielijl_bt_otaimplRcspAuth(z);
            }
        });
    }

    private String printDeviceInfo(BluetoothDevice bluetoothDevice) {
        return BluetoothUtil.printBtDeviceInfo(this.mContext, bluetoothDevice);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean sendAuthDataToDevice(BluetoothDevice bluetoothDevice, byte[] bArr) {
        if (bluetoothDevice == null || bArr == null) {
            return false;
        }
        boolean zSendAuthDataToDevice = this.mIRcspAuthOp.sendAuthDataToDevice(bluetoothDevice, bArr);
        JL_Log.m849i(TAG, "sendAuthDataToDevice", CommonUtil.formatString("device : %s, authData : %s", printDeviceInfo(bluetoothDevice), CHexConver.byte2HexStr(bArr)));
        return zSendAuthDataToDevice;
    }

    public static boolean setAuthTimeout(long j) {
        if (j < DEFAULT_AUTH_TIMEOUT) {
            JL_Log.m845d(TAG, "setAuthTimeout", CommonUtil.formatString("The set time[%d] cannot be less than the minimum time[%d].", Long.valueOf(j), Long.valueOf(DEFAULT_AUTH_TIMEOUT)));
            return false;
        }
        DELAY_AUTH_WAITING_TIME = j;
        return true;
    }

    public void addListener(OnRcspAuthListener onRcspAuthListener) {
        if (onRcspAuthListener != null) {
            this.mOnRcspAuthListeners.add(onRcspAuthListener);
            onRcspAuthListener.onInitResult(this.isLibInit);
        }
    }

    public void destroy() {
        this.mHandler.removeCallbacksAndMessages(null);
        this.mAuthTaskMap.clear();
        this.mOnRcspAuthListeners.clear();
        mIsLibLoaded = false;
    }

    public byte[] getAuthData(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return getEncryptedAuthData(bArr);
    }

    public byte[] getAuthOkData() {
        return new byte[]{2, 112, 97, 115, 115};
    }

    protected native byte[] getEncryptedAuthData(byte[] bArr);

    protected native byte[] getRandomAuthData();

    public byte[] getRandomData() {
        return getRandomAuthData();
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x00a9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void handleAuthData(android.bluetooth.BluetoothDevice r9, byte[] r10) {
        /*
            Method dump skipped, instruction units count: 257
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jieli.jl_bt_ota.impl.RcspAuth.handleAuthData(android.bluetooth.BluetoothDevice, byte[]):void");
    }

    /* JADX INFO: renamed from: lambda$onAuthFailed$3$com-jieli-jl_bt_ota-impl-RcspAuth, reason: not valid java name */
    /* synthetic */ void m2670lambda$onAuthFailed$3$comjielijl_bt_otaimplRcspAuth(BluetoothDevice bluetoothDevice, int i, String str) {
        if (this.mOnRcspAuthListeners.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList(this.mOnRcspAuthListeners);
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            ((OnRcspAuthListener) obj).onAuthFailed(bluetoothDevice, i, str);
        }
    }

    /* JADX INFO: renamed from: lambda$onAuthSuccess$2$com-jieli-jl_bt_ota-impl-RcspAuth, reason: not valid java name */
    /* synthetic */ void m2671lambda$onAuthSuccess$2$comjielijl_bt_otaimplRcspAuth(BluetoothDevice bluetoothDevice) {
        if (this.mOnRcspAuthListeners.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList(this.mOnRcspAuthListeners);
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((OnRcspAuthListener) obj).onAuthSuccess(bluetoothDevice);
        }
    }

    /* JADX INFO: renamed from: lambda$onInitResult$1$com-jieli-jl_bt_ota-impl-RcspAuth, reason: not valid java name */
    /* synthetic */ void m2672lambda$onInitResult$1$comjielijl_bt_otaimplRcspAuth(boolean z) {
        if (this.mOnRcspAuthListeners.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList(this.mOnRcspAuthListeners);
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((OnRcspAuthListener) obj).onInitResult(z);
        }
    }

    /* JADX INFO: renamed from: lambda$startAuth$0$com-jieli-jl_bt_ota-impl-RcspAuth, reason: not valid java name */
    /* synthetic */ void m2673lambda$startAuth$0$comjielijl_bt_otaimplRcspAuth(BluetoothDevice bluetoothDevice, AuthTask authTask) {
        if (!sendAuthDataToDevice(bluetoothDevice, authTask.getRandomData())) {
            onAuthFailed(bluetoothDevice, ERR_AUTH_DATA_SEND, "Failed to send data.");
            return;
        }
        this.mHandler.removeMessages(17);
        Handler handler = this.mHandler;
        handler.sendMessageDelayed(handler.obtainMessage(17, bluetoothDevice), DELAY_AUTH_WAITING_TIME);
    }

    protected native boolean nativeInit();

    public void removeListener(OnRcspAuthListener onRcspAuthListener) {
        if (onRcspAuthListener != null) {
            this.mOnRcspAuthListeners.remove(onRcspAuthListener);
        }
    }

    public int setDeviceConnectionLinkKey(byte[] bArr) {
        return setLinkKey(bArr);
    }

    protected native int setLinkKey(byte[] bArr);

    public boolean startAuth(final BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            return false;
        }
        if (this.mAuthTaskMap.containsKey(bluetoothDevice.getAddress())) {
            AuthTask authTask = this.mAuthTaskMap.get(bluetoothDevice.getAddress());
            if (authTask != null && (authTask.isAuthDevice() || this.mHandler.hasMessages(18))) {
                JL_Log.m849i(TAG, "startAuth", "The device has been certified or certification of device is in progress.");
                return true;
            }
            this.mAuthTaskMap.remove(bluetoothDevice.getAddress());
        }
        JL_Log.m849i(TAG, "startAuth", "device = " + printDeviceInfo(bluetoothDevice));
        final AuthTask randomData = new AuthTask().setDevice(bluetoothDevice).setRandomData(getRandomData());
        this.mAuthTaskMap.put(bluetoothDevice.getAddress(), randomData);
        if (SUPPORT_RESET_FLAG) {
            boolean zSendAuthDataToDevice = sendAuthDataToDevice(bluetoothDevice, getResetAuthFlagCmdData());
            if (zSendAuthDataToDevice) {
                this.mHandler.postDelayed(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.RcspAuth$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m2673lambda$startAuth$0$comjielijl_bt_otaimplRcspAuth(bluetoothDevice, randomData);
                    }
                }, 500L);
            }
            return zSendAuthDataToDevice;
        }
        boolean zSendAuthDataToDevice2 = sendAuthDataToDevice(bluetoothDevice, randomData.getRandomData());
        if (zSendAuthDataToDevice2) {
            this.mHandler.removeMessages(17);
            Handler handler = this.mHandler;
            handler.sendMessageDelayed(handler.obtainMessage(17, bluetoothDevice), DELAY_AUTH_WAITING_TIME);
        }
        return zSendAuthDataToDevice2;
    }

    public void stopAuth(BluetoothDevice bluetoothDevice) {
        stopAuth(bluetoothDevice, true);
    }

    private void onAuthFailed(final BluetoothDevice bluetoothDevice, final int i, final String str) {
        JL_Log.m847e(TAG, "onAuthFailed", CommonUtil.formatString("device = %s,  code = %d, message = %s.", bluetoothDevice, Integer.valueOf(i), str));
        this.mHandler.removeMessages(17);
        this.mHandler.removeMessages(18);
        this.mHandler.post(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.RcspAuth$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2670lambda$onAuthFailed$3$comjielijl_bt_otaimplRcspAuth(bluetoothDevice, i, str);
            }
        });
        if (bluetoothDevice != null) {
            this.mAuthTaskMap.remove(bluetoothDevice.getAddress());
        }
    }

    public void stopAuth(BluetoothDevice bluetoothDevice, boolean z) {
        if (bluetoothDevice == null) {
            return;
        }
        AuthTask authTaskRemove = this.mAuthTaskMap.remove(bluetoothDevice.getAddress());
        if (z) {
            if (authTaskRemove != null) {
                onAuthFailed(bluetoothDevice, ERR_AUTH_USER_STOP);
            }
            this.mHandler.removeMessages(17);
            this.mHandler.removeMessages(18);
        }
    }
}
