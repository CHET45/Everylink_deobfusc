package com.aivox.jieliota.util;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.aivox.jieliota.tool.ota.ble.BleManager;
import com.aivox.jieliota.tool.ota.ble.interfaces.BleEventCallback;
import com.aivox.jieliota.tool.ota.ble.model.BleScanInfo;
import com.aivox.jieliota.util.ReConnectHelper;
import com.jieli.jl_bt_ota.constant.JL_Constant;
import com.jieli.jl_bt_ota.model.BleScanMessage;
import com.jieli.jl_bt_ota.tool.DeviceReConnectManager;
import com.jieli.jl_bt_ota.util.BluetoothUtil;
import com.jieli.jl_bt_ota.util.JL_Log;
import com.jieli.jl_bt_ota.util.ParseDataUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ReConnectHelper.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m1900d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\b\u0018\u0000 (2\u00020\u0001:\u0002()B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\u0019\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u001a\u001a\u00020\u000eH\u0002J\u0016\u0010\u001b\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u000eJ\u001c\u0010\u001e\u001a\u00020\n2\b\u0010\u001f\u001a\u0004\u0018\u00010\u00162\b\u0010 \u001a\u0004\u0018\u00010\u000fH\u0002J\b\u0010!\u001a\u00020\"H\u0002J\u0010\u0010#\u001a\u00020\n2\b\u0010$\u001a\u0004\u0018\u00010\u0012J\u0006\u0010%\u001a\u00020\"J\u0010\u0010&\u001a\u00020\"2\u0006\u0010\u001a\u001a\u00020\u000eH\u0002J\b\u0010'\u001a\u00020\"H\u0002R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\t\u0010\u000bR\u001a\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0015\u001a\u0004\u0018\u00010\u00168BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018¨\u0006*"}, m1901d2 = {"Lcom/aivox/jieliota/util/ReConnectHelper;", "", "mContext", "Landroid/content/Context;", "mBtManager", "Lcom/aivox/jieliota/tool/ota/ble/BleManager;", "(Landroid/content/Context;Lcom/aivox/jieliota/tool/ota/ble/BleManager;)V", "bleEventCallback", "Lcom/aivox/jieliota/tool/ota/ble/interfaces/BleEventCallback;", "isReconnecting", "", "()Z", "mBleAdvCache", "", "", "Lcom/jieli/jl_bt_ota/model/BleScanMessage;", "mParams", "", "Lcom/aivox/jieliota/util/ReConnectHelper$ReconnectParam;", "mUIHandler", "Landroid/os/Handler;", "systemConnectedDevice", "Landroid/bluetooth/BluetoothDevice;", "getSystemConnectedDevice", "()Landroid/bluetooth/BluetoothDevice;", "getCacheParam", "address", "isMatchAddress", "srcAddress", "checkAddress", "isReconnectDevice", "device", "message", "processReconnectTask", "", "putParam", "param", "release", "removeParam", "stopBtScan", "Companion", "ReconnectParam", "jieliota_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
public final class ReConnectHelper {
    private static final long FAILED_DELAY = 3000;
    private static final int MSG_PROCESS_TASK = 2;
    private static final int MSG_RECONNECT_TIMEOUT = 1;
    private static final long SCAN_TIMEOUT = 20000;
    private final BleEventCallback bleEventCallback;
    private final Map<String, BleScanMessage> mBleAdvCache;
    private final BleManager mBtManager;
    private final Context mContext;
    private final List<ReconnectParam> mParams;
    private final Handler mUIHandler;
    private static final String TAG = "ReConnectHelper";
    private static final long RECONNECT_TIMEOUT = DeviceReConnectManager.RECONNECT_TIMEOUT;

    public ReConnectHelper(Context mContext, BleManager mBtManager) {
        Intrinsics.checkNotNullParameter(mContext, "mContext");
        Intrinsics.checkNotNullParameter(mBtManager, "mBtManager");
        this.mContext = mContext;
        this.mBtManager = mBtManager;
        this.mParams = new ArrayList();
        this.mBleAdvCache = new HashMap();
        this.mUIHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.aivox.jieliota.util.ReConnectHelper$$ExternalSyntheticLambda0
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                return ReConnectHelper.mUIHandler$lambda$0(this.f$0, message);
            }
        });
        BleEventCallback bleEventCallback = new BleEventCallback() { // from class: com.aivox.jieliota.util.ReConnectHelper$bleEventCallback$1
            @Override // com.aivox.jieliota.tool.ota.ble.interfaces.BleEventCallback, com.aivox.jieliota.tool.ota.ble.interfaces.IBleEventCallback
            public void onAdapterChange(boolean bEnabled) {
                if (this.this$0.isReconnecting() && bEnabled) {
                    JL_Log.m848i(ReConnectHelper.TAG, "onAdapterChange : bluetooth is on, try to start le scan.");
                    this.this$0.mUIHandler.sendEmptyMessage(2);
                }
            }

            @Override // com.aivox.jieliota.tool.ota.ble.interfaces.BleEventCallback, com.aivox.jieliota.tool.ota.ble.interfaces.IBleEventCallback
            public void onDiscoveryBleChange(boolean bStart) {
                if (this.this$0.isReconnecting()) {
                    boolean zIsConnecting = this.this$0.mBtManager.isConnecting();
                    JL_Log.m848i(ReConnectHelper.TAG, "onDiscoveryBleChange : " + bStart + ", isConnecting = " + zIsConnecting);
                    if (bStart || zIsConnecting) {
                        return;
                    }
                    this.this$0.mUIHandler.sendEmptyMessage(2);
                }
            }

            @Override // com.aivox.jieliota.tool.ota.ble.interfaces.BleEventCallback, com.aivox.jieliota.tool.ota.ble.interfaces.IBleEventCallback
            public void onDiscoveryBle(BluetoothDevice device, BleScanInfo bleScanMessage) {
                Intrinsics.checkNotNullParameter(bleScanMessage, "bleScanMessage");
                if (!this.this$0.isReconnecting() || device == null) {
                    return;
                }
                BleScanMessage oTAFlagFilterWithBroad = ParseDataUtil.parseOTAFlagFilterWithBroad(bleScanMessage.getRawData(), JL_Constant.OTA_IDENTIFY);
                if (oTAFlagFilterWithBroad != null) {
                    Map map = this.this$0.mBleAdvCache;
                    String address = device.getAddress();
                    Intrinsics.checkNotNullExpressionValue(address, "getAddress(...)");
                    map.put(address, oTAFlagFilterWithBroad);
                    JL_Log.m844d(ReConnectHelper.TAG, "onDiscoveryBle : put data in map.");
                }
                boolean zIsReconnectDevice = this.this$0.isReconnectDevice(device, oTAFlagFilterWithBroad);
                JL_Log.m844d(ReConnectHelper.TAG, "onDiscoveryBle : " + device + ", isReconnectDevice = " + zIsReconnectDevice + ", " + oTAFlagFilterWithBroad);
                if (zIsReconnectDevice) {
                    this.this$0.stopBtScan();
                    ReConnectHelper reConnectHelper = this.this$0;
                    String address2 = device.getAddress();
                    Intrinsics.checkNotNullExpressionValue(address2, "getAddress(...)");
                    ReConnectHelper.ReconnectParam cacheParam = reConnectHelper.getCacheParam(address2);
                    if (cacheParam != null) {
                        cacheParam.setConnectAddress(device.getAddress());
                    }
                    JL_Log.m844d(ReConnectHelper.TAG, "onDiscoveryBle : " + device + ", param = " + cacheParam);
                    this.this$0.mBtManager.connectBleDevice(device);
                }
            }

            @Override // com.aivox.jieliota.tool.ota.ble.interfaces.BleEventCallback, com.aivox.jieliota.tool.ota.ble.interfaces.IBleEventCallback
            public void onBleConnection(BluetoothDevice device, int status) {
                if (!this.this$0.isReconnecting() || device == null) {
                    return;
                }
                BleScanMessage bleScanMessage = (BleScanMessage) this.this$0.mBleAdvCache.get(device.getAddress());
                if (this.this$0.isReconnectDevice(device, bleScanMessage)) {
                    JL_Log.m848i(ReConnectHelper.TAG, "onBleConnection : " + device + ", status = " + status + ", " + bleScanMessage);
                    if (status == 0) {
                        JL_Log.m848i(ReConnectHelper.TAG, "-onConnection- resume reconnect task.");
                        this.this$0.mUIHandler.sendEmptyMessage(2);
                    } else {
                        if (status != 2) {
                            return;
                        }
                        JL_Log.m852w(ReConnectHelper.TAG, "onBleConnection : removeParam >>> " + device.getAddress());
                        ReConnectHelper reConnectHelper = this.this$0;
                        String address = device.getAddress();
                        Intrinsics.checkNotNullExpressionValue(address, "getAddress(...)");
                        reConnectHelper.removeParam(address);
                    }
                }
            }
        };
        this.bleEventCallback = bleEventCallback;
        mBtManager.registerBleEventCallback(bleEventCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean mUIHandler$lambda$0(ReConnectHelper this$0, Message msg) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(msg, "msg");
        int i = msg.what;
        if (i == 1) {
            this$0.stopBtScan();
            this$0.mParams.clear();
        } else if (i == 2) {
            this$0.processReconnectTask();
        } else if (msg.obj instanceof String) {
            Object obj = msg.obj;
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.String");
            this$0.removeParam((String) obj);
        }
        return true;
    }

    public final void release() {
        this.mParams.clear();
        this.mBleAdvCache.clear();
        this.mUIHandler.removeCallbacksAndMessages(null);
        this.mBtManager.unregisterBleEventCallback(this.bleEventCallback);
    }

    public final boolean isReconnecting() {
        return this.mUIHandler.hasMessages(1);
    }

    public final boolean isMatchAddress(String srcAddress, String checkAddress) {
        Intrinsics.checkNotNullParameter(srcAddress, "srcAddress");
        Intrinsics.checkNotNullParameter(checkAddress, "checkAddress");
        ReconnectParam cacheParam = getCacheParam(srcAddress);
        if (cacheParam == null || !BluetoothAdapter.checkBluetoothAddress(checkAddress)) {
            return false;
        }
        return Intrinsics.areEqual(checkAddress, cacheParam.getDeviceAddress()) || Intrinsics.areEqual(checkAddress, cacheParam.getConnectAddress());
    }

    public final boolean putParam(ReconnectParam param) {
        if (param == null) {
            return false;
        }
        if (this.mParams.contains(param)) {
            return true;
        }
        if (!this.mParams.add(param)) {
            return false;
        }
        Handler handler = this.mUIHandler;
        int iHashCode = this.mParams.hashCode();
        long j = RECONNECT_TIMEOUT;
        handler.sendEmptyMessageDelayed(iHashCode, j);
        if (!isReconnecting()) {
            Handler handler2 = this.mUIHandler;
            handler2.sendMessageDelayed(handler2.obtainMessage(1, param.getDeviceAddress()), j + ((long) 10000));
            this.mUIHandler.sendEmptyMessage(2);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void stopBtScan() {
        this.mBtManager.stopLeScan();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ReconnectParam getCacheParam(String address) {
        if (!BluetoothAdapter.checkBluetoothAddress(address)) {
            return null;
        }
        BleScanMessage bleScanMessage = this.mBleAdvCache.get(address);
        for (ReconnectParam reconnectParam : new ArrayList(this.mParams)) {
            if (Intrinsics.areEqual(address, reconnectParam.getDeviceAddress()) || (bleScanMessage != null && Intrinsics.areEqual(reconnectParam.getDeviceAddress(), bleScanMessage.getOldBleAddress()))) {
                return reconnectParam;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void removeParam(String address) {
        ReconnectParam cacheParam = getCacheParam(address);
        if (cacheParam == null) {
            return;
        }
        if (this.mParams.remove(cacheParam)) {
            this.mUIHandler.removeMessages(cacheParam.hashCode());
            if (this.mParams.isEmpty()) {
                this.mUIHandler.removeMessages(1);
                return;
            }
        }
        this.mUIHandler.sendEmptyMessage(2);
    }

    private final void processReconnectTask() {
        if (this.mBtManager.isBleScanning()) {
            this.mUIHandler.sendEmptyMessageDelayed(2, FAILED_DELAY);
            return;
        }
        BluetoothDevice systemConnectedDevice = getSystemConnectedDevice();
        if (systemConnectedDevice != null) {
            String address = systemConnectedDevice.getAddress();
            Intrinsics.checkNotNullExpressionValue(address, "getAddress(...)");
            ReconnectParam cacheParam = getCacheParam(address);
            if (cacheParam != null) {
                cacheParam.setConnectAddress(systemConnectedDevice.getAddress());
            }
            this.mBtManager.connectBleDevice(systemConnectedDevice);
            return;
        }
        if (this.mBtManager.startLeScan(SCAN_TIMEOUT)) {
            return;
        }
        JL_Log.m848i(TAG, "processReconnectTask : start Le scan failed.");
        this.mUIHandler.sendEmptyMessageDelayed(2, FAILED_DELAY);
    }

    private final BluetoothDevice getSystemConnectedDevice() {
        List<BluetoothDevice> systemConnectedBtDeviceList = BluetoothUtil.getSystemConnectedBtDeviceList(this.mContext);
        if (systemConnectedBtDeviceList != null && !systemConnectedBtDeviceList.isEmpty()) {
            for (BluetoothDevice bluetoothDevice : systemConnectedBtDeviceList) {
                if (isReconnectDevice(bluetoothDevice, null)) {
                    return bluetoothDevice;
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isReconnectDevice(BluetoothDevice device, BleScanMessage message) {
        boolean zAreEqual = false;
        if (device != null && !this.mParams.isEmpty()) {
            for (ReconnectParam reconnectParam : new ArrayList(this.mParams)) {
                if (reconnectParam.getIsUseNewADV() && message != null && message.isOTA()) {
                    zAreEqual = Intrinsics.areEqual(reconnectParam.getDeviceAddress(), message.getOldBleAddress());
                } else {
                    zAreEqual = Intrinsics.areEqual(reconnectParam.getDeviceAddress(), device.getAddress());
                }
                if (zAreEqual) {
                    break;
                }
            }
        }
        return zAreEqual;
    }

    /* JADX INFO: compiled from: ReConnectHelper.kt */
    @Metadata(m1900d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010\u000e\u001a\u00020\u00052\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0003H\u0016R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\r¨\u0006\u0013"}, m1901d2 = {"Lcom/aivox/jieliota/util/ReConnectHelper$ReconnectParam;", "", "deviceAddress", "", "isUseNewADV", "", "(Ljava/lang/String;Z)V", "connectAddress", "getConnectAddress", "()Ljava/lang/String;", "setConnectAddress", "(Ljava/lang/String;)V", "getDeviceAddress", "()Z", "equals", "o", "hashCode", "", "toString", "jieliota_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
    public static final class ReconnectParam {
        private String connectAddress;
        private final String deviceAddress;
        private final boolean isUseNewADV;

        public ReconnectParam(String deviceAddress, boolean z) {
            Intrinsics.checkNotNullParameter(deviceAddress, "deviceAddress");
            this.deviceAddress = deviceAddress;
            this.isUseNewADV = z;
        }

        public final String getDeviceAddress() {
            return this.deviceAddress;
        }

        /* JADX INFO: renamed from: isUseNewADV, reason: from getter */
        public final boolean getIsUseNewADV() {
            return this.isUseNewADV;
        }

        public final String getConnectAddress() {
            return this.connectAddress;
        }

        public final void setConnectAddress(String str) {
            this.connectAddress = str;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || !Intrinsics.areEqual(getClass(), o.getClass())) {
                return false;
            }
            ReconnectParam reconnectParam = (ReconnectParam) o;
            return this.isUseNewADV == reconnectParam.isUseNewADV && Intrinsics.areEqual(this.deviceAddress, reconnectParam.deviceAddress);
        }

        public int hashCode() {
            return Objects.hash(this.deviceAddress, Boolean.valueOf(this.isUseNewADV));
        }

        public String toString() {
            return "ReconnectParam{deviceAddress='" + this.deviceAddress + "', isUseNewADV=" + this.isUseNewADV + ", connectAddress='" + this.connectAddress + "'}";
        }
    }
}
