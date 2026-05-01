package com.jieli.jl_bt_ota.tool;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import com.aivox.base.util.MyAnimationUtil;
import com.jieli.jl_bt_ota.constant.ErrorCode;
import com.jieli.jl_bt_ota.constant.JL_Constant;
import com.jieli.jl_bt_ota.impl.BluetoothOTAManager;
import com.jieli.jl_bt_ota.interfaces.BtEventCallback;
import com.jieli.jl_bt_ota.model.BleScanMessage;
import com.jieli.jl_bt_ota.model.OTAError;
import com.jieli.jl_bt_ota.model.ReConnectDevMsg;
import com.jieli.jl_bt_ota.model.base.BaseError;
import com.jieli.jl_bt_ota.util.BluetoothUtil;
import com.jieli.jl_bt_ota.util.CHexConver;
import com.jieli.jl_bt_ota.util.CommonUtil;
import com.jieli.jl_bt_ota.util.JL_Log;
import com.jieli.jl_bt_ota.util.ParseDataUtil;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class DeviceReConnectManager {
    public static long RECONNECT_TIMEOUT = 65000;

    /* JADX INFO: renamed from: h */
    private static final String f737h = "DeviceReConnectManager";

    /* JADX INFO: renamed from: i */
    private static final int f738i = 3000;

    /* JADX INFO: renamed from: j */
    private static final int f739j = 20000;

    /* JADX INFO: renamed from: k */
    private static final int f740k = 2;

    /* JADX INFO: renamed from: l */
    private static final int f741l = 30000;

    /* JADX INFO: renamed from: m */
    private static final int f742m = 2000;

    /* JADX INFO: renamed from: n */
    private static final int f743n = 37973;

    /* JADX INFO: renamed from: o */
    private static final int f744o = 37974;

    /* JADX INFO: renamed from: p */
    private static final int f745p = 37975;

    /* JADX INFO: renamed from: a */
    private final Context f746a;

    /* JADX INFO: renamed from: b */
    private final BluetoothOTAManager f747b;

    /* JADX INFO: renamed from: c */
    private volatile ReConnectDevMsg f748c;

    /* JADX INFO: renamed from: d */
    private long f749d = 0;

    /* JADX INFO: renamed from: e */
    private final Map<String, BleScanMessage> f750e = new HashMap();

    /* JADX INFO: renamed from: f */
    private final Handler f751f = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.jieli.jl_bt_ota.tool.DeviceReConnectManager.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case DeviceReConnectManager.f743n /* 37973 */:
                    DeviceReConnectManager.this.m801b();
                    break;
                case DeviceReConnectManager.f744o /* 37974 */:
                    JL_Log.m853w(DeviceReConnectManager.f737h, "MSG_RECONNECT_DEVICE_TIMEOUT", "" + DeviceReConnectManager.this.f748c);
                    if (DeviceReConnectManager.this.f748c != null) {
                        DeviceReConnectManager.this.f748c.setState(0);
                        DeviceReConnectManager deviceReConnectManager = DeviceReConnectManager.this;
                        deviceReConnectManager.m790a(OTAError.buildError(ErrorCode.SUB_ERR_RECONNECT_TIMEOUT, deviceReConnectManager.f748c.toString()));
                    }
                    break;
                case DeviceReConnectManager.f745p /* 37975 */:
                    JL_Log.m853w(DeviceReConnectManager.f737h, "MSG_CONNECT_DEVICE_TIMEOUT", "" + DeviceReConnectManager.this.f748c);
                    if (DeviceReConnectManager.this.f748c != null) {
                        DeviceReConnectManager.this.f748c.setState(0);
                        DeviceReConnectManager deviceReConnectManager2 = DeviceReConnectManager.this;
                        deviceReConnectManager2.m795a(deviceReConnectManager2.f748c.getAddress());
                    }
                    break;
            }
            return true;
        }
    });

    /* JADX INFO: renamed from: g */
    private final BtEventCallback f752g;

    public DeviceReConnectManager(Context context, BluetoothOTAManager bluetoothOTAManager) {
        BtEventCallback btEventCallback = new BtEventCallback() { // from class: com.jieli.jl_bt_ota.tool.DeviceReConnectManager.2
            @Override // com.jieli.jl_bt_ota.interfaces.BtEventCallback, com.jieli.jl_bt_ota.interfaces.IBluetoothCallback
            public void onAdapterStatus(boolean z, boolean z2) {
                if (z || !DeviceReConnectManager.this.isDeviceReconnecting()) {
                    return;
                }
                JL_Log.m845d(DeviceReConnectManager.f737h, "onAdapterStatus", "bluetooth is off.");
            }

            @Override // com.jieli.jl_bt_ota.interfaces.BtEventCallback, com.jieli.jl_bt_ota.interfaces.IBluetoothCallback
            public void onConnection(BluetoothDevice bluetoothDevice, int i) {
                byte[] rawData;
                if (bluetoothDevice == null || !DeviceReConnectManager.this.isDeviceReconnecting() || i == 3) {
                    return;
                }
                boolean z = DeviceReConnectManager.this.f751f.hasMessages(DeviceReConnectManager.f745p) || DeviceReConnectManager.this.m810f();
                JL_Log.m845d(DeviceReConnectManager.f737h, "onConnection", "isConnecting: " + z + ", status = " + i);
                if (z) {
                    BleScanMessage bleScanMessage = (BleScanMessage) DeviceReConnectManager.this.f750e.get(bluetoothDevice.getAddress());
                    if (bleScanMessage != null) {
                        JL_Log.m845d(DeviceReConnectManager.f737h, "onConnection", "bleScanMessage: " + bleScanMessage);
                        rawData = bleScanMessage.getRawData();
                    } else {
                        rawData = null;
                    }
                    boolean zM796a = DeviceReConnectManager.this.m796a(bluetoothDevice, rawData);
                    JL_Log.m853w(DeviceReConnectManager.f737h, "onConnection", CommonUtil.formatString("device : %s, status : %d, isReConnectDevice : %s", DeviceReConnectManager.this.m799b(bluetoothDevice), Integer.valueOf(i), Boolean.valueOf(zM796a)));
                    if (zM796a) {
                        if (DeviceReConnectManager.this.f748c != null) {
                            DeviceReConnectManager.this.f748c.setState(0);
                        }
                        DeviceReConnectManager.this.f751f.removeMessages(DeviceReConnectManager.f745p);
                        if (i == 1 || i == 4) {
                            JL_Log.m845d(DeviceReConnectManager.f737h, "onConnection", "reconnect device success.");
                            DeviceReConnectManager.this.stopReconnectTask();
                        } else if (i == 2 || i == 0) {
                            JL_Log.m849i(DeviceReConnectManager.f737h, "onConnection", "connect device failed.");
                            DeviceReConnectManager.this.m795a(bluetoothDevice.getAddress());
                        }
                    }
                }
            }

            @Override // com.jieli.jl_bt_ota.interfaces.BtEventCallback, com.jieli.jl_bt_ota.interfaces.IBluetoothCallback
            public void onDiscovery(BluetoothDevice bluetoothDevice, BleScanMessage bleScanMessage) {
                byte[] rawData;
                if (bluetoothDevice == null || !DeviceReConnectManager.this.isDeviceReconnecting()) {
                    return;
                }
                if (bleScanMessage != null) {
                    DeviceReConnectManager.this.f750e.put(bluetoothDevice.getAddress(), bleScanMessage);
                    rawData = bleScanMessage.getRawData();
                } else {
                    rawData = null;
                }
                boolean zM796a = DeviceReConnectManager.this.m796a(bluetoothDevice, rawData);
                JL_Log.m845d(DeviceReConnectManager.f737h, "onDiscovery", CommonUtil.formatString("isReConnectDevice : %s, device : %s", Boolean.valueOf(zM796a), DeviceReConnectManager.this.m799b(bluetoothDevice)));
                if (zM796a) {
                    DeviceReConnectManager.this.m789a(bluetoothDevice);
                    DeviceReConnectManager.this.m811g();
                }
            }

            @Override // com.jieli.jl_bt_ota.interfaces.BtEventCallback, com.jieli.jl_bt_ota.interfaces.IBluetoothCallback
            public void onDiscoveryStatus(boolean z, boolean z2) {
                JL_Log.m845d(DeviceReConnectManager.f737h, "onDiscoveryStatus", "bStart : " + z2);
                if (!DeviceReConnectManager.this.isDeviceReconnecting() || DeviceReConnectManager.this.m810f()) {
                    return;
                }
                if (!z2) {
                    JL_Log.m845d(DeviceReConnectManager.f737h, "onDiscoveryStatus", "ready start scan");
                    DeviceReConnectManager.this.f751f.removeMessages(DeviceReConnectManager.f743n);
                    DeviceReConnectManager.this.f751f.sendEmptyMessageDelayed(DeviceReConnectManager.f743n, 1000L);
                } else {
                    if (DeviceReConnectManager.this.f748c == null || DeviceReConnectManager.this.f748c.getState() != 0) {
                        return;
                    }
                    DeviceReConnectManager.this.f748c.setState(1);
                }
            }
        };
        this.f752g = btEventCallback;
        this.f746a = context;
        this.f747b = bluetoothOTAManager;
        bluetoothOTAManager.registerBluetoothCallback(btEventCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: g */
    public void m811g() {
        JL_Log.m845d(f737h, "stopScan", "---->");
        this.f747b.stopBLEScan();
        this.f747b.stopDeviceScan();
    }

    public String getReconnectAddress() {
        ReConnectDevMsg reConnectDevMsgM808e = m808e();
        if (reConnectDevMsgM808e == null) {
            return null;
        }
        return reConnectDevMsgM808e.getAddress();
    }

    public boolean isDeviceReconnecting() {
        return this.f751f.hasMessages(f744o);
    }

    public boolean isWaitingForUpdate() {
        return m808e() != null;
    }

    public void release() {
        setReConnectDevMsg(null);
        stopReconnectTask();
        this.f747b.unregisterBluetoothCallback(this.f752g);
        this.f751f.removeCallbacksAndMessages(null);
    }

    public void setReConnectDevMsg(ReConnectDevMsg reConnectDevMsg) {
        if (this.f748c != reConnectDevMsg) {
            this.f748c = reConnectDevMsg;
            this.f750e.clear();
            JL_Log.m845d(f737h, "setReConnectDevMsg", "" + reConnectDevMsg);
        }
    }

    public void setReconnectAddress(String str) {
        if (!BluetoothAdapter.checkBluetoothAddress(str)) {
            setReConnectDevMsg(null);
        } else if (this.f748c == null) {
            setReConnectDevMsg(new ReConnectDevMsg(this.f747b.getBluetoothOption().getPriority(), str));
        } else {
            this.f748c.setAddress(str);
            JL_Log.m845d(f737h, "setReconnectAddress", "" + this.f748c);
        }
    }

    public void setReconnectUseADV(boolean z) {
        if (this.f748c != null) {
            this.f748c.setUseADV(z);
        }
    }

    public void startReconnectTask() {
        if (isDeviceReconnecting()) {
            return;
        }
        String str = f737h;
        JL_Log.m849i(str, "startReconnectTask", "start....");
        m788a(m803c());
        JL_Log.m849i(str, "startReconnectTask", "timeout = " + RECONNECT_TIMEOUT);
        this.f751f.sendEmptyMessageDelayed(f744o, RECONNECT_TIMEOUT);
        this.f751f.sendEmptyMessage(f743n);
    }

    public void stopReconnectTask() {
        JL_Log.m849i(f737h, "stopReconnectTask", "isReconnecting : " + isDeviceReconnecting() + ", isWaitingForUpdate : " + isWaitingForUpdate());
        m788a(0L);
        setReConnectDevMsg(null);
        m811g();
        this.f751f.removeCallbacksAndMessages(null);
    }

    /* JADX INFO: renamed from: c */
    private long m803c() {
        return Calendar.getInstance().getTimeInMillis();
    }

    /* JADX INFO: renamed from: d */
    private long m805d() {
        long jM803c = RECONNECT_TIMEOUT - (m803c() - this.f749d);
        if (jM803c < 0) {
            return 0L;
        }
        return jM803c;
    }

    /* JADX INFO: renamed from: e */
    private ReConnectDevMsg m808e() {
        if (this.f748c == null) {
            return null;
        }
        return this.f748c.cloneObject();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: f */
    public boolean m810f() {
        return m808e() != null && m808e().getState() == 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b */
    public void m801b() {
        int iStartBLEScan;
        ReConnectDevMsg reConnectDevMsgM808e = m808e();
        if (reConnectDevMsgM808e == null) {
            JL_Log.m853w(f737h, "doReconnectTask", "reConnectDevMsg is null.");
            stopReconnectTask();
            return;
        }
        if (!BluetoothUtil.isBluetoothEnable()) {
            JL_Log.m853w(f737h, "doReconnectTask", "Bluetooth is close.");
            this.f751f.removeMessages(f743n);
            this.f751f.sendEmptyMessageDelayed(f743n, 3000L);
            return;
        }
        if (reConnectDevMsgM808e.getState() == 2) {
            JL_Log.m853w(f737h, "doReconnectTask", "Task is connecting. " + reConnectDevMsgM808e);
            if (this.f751f.hasMessages(f745p)) {
                return;
            }
            this.f751f.sendEmptyMessageDelayed(f745p, 30000L);
            return;
        }
        boolean zIsConnectedDevice = this.f747b.isConnectedDevice();
        String str = f737h;
        JL_Log.m849i(str, "doReconnectTask", reConnectDevMsgM808e + ", isDevConnected : " + zIsConnectedDevice);
        if (zIsConnectedDevice) {
            JL_Log.m849i(str, "doReconnectTask", "device is connected. " + reConnectDevMsgM808e + ", device = " + this.f747b.getConnectedDevice());
            return;
        }
        BluetoothDevice bluetoothDeviceM798b = m798b(reConnectDevMsgM808e.getAddress());
        JL_Log.m853w(str, "doReconnectTask", "connectedDevice : " + m799b(bluetoothDeviceM798b));
        if (bluetoothDeviceM798b != null) {
            m789a(bluetoothDeviceM798b);
            return;
        }
        if (reConnectDevMsgM808e.isUseADV() && reConnectDevMsgM808e.getWay() != 0) {
            reConnectDevMsgM808e.setWay(0);
        }
        if (this.f747b.isScanning()) {
            int scanType = this.f747b.getScanType();
            boolean z = scanType == 2;
            if (!z) {
                z = (reConnectDevMsgM808e.getWay() == 1 && scanType == 1) || (reConnectDevMsgM808e.getWay() == 0 && scanType == 0);
            }
            JL_Log.m849i(str, "doReconnectTask", "isScanOk : " + z + ", scanType = " + scanType);
            if (z) {
                return;
            }
            m811g();
            SystemClock.sleep(100L);
        }
        long jM805d = m805d();
        JL_Log.m845d(str, "doReconnectTask", "leftTime ： " + jM805d + ", beginTaskTime : " + this.f749d);
        if (jM805d < RECONNECT_TIMEOUT - 40000 && !reConnectDevMsgM808e.isUseADV()) {
            int i = reConnectDevMsgM808e.getWay() == 1 ? 0 : 2;
            long j = jM805d - 3000;
            if (j > 0) {
                jM805d = j;
            }
            iStartBLEScan = this.f747b.startDeviceScan(jM805d, i);
            JL_Log.m849i(str, "doReconnectTask", "startDeviceScan : " + iStartBLEScan + ", way = " + i + ", timeout = " + jM805d);
        } else {
            long jMin = Math.min(jM805d, 20000L);
            if (reConnectDevMsgM808e.getWay() == 1) {
                iStartBLEScan = this.f747b.startDeviceScan(jMin, 1);
                JL_Log.m849i(str, "doReconnectTask", "startDeviceScan : " + iStartBLEScan + ", scanTime = " + jMin);
            } else {
                iStartBLEScan = this.f747b.startBLEScan(jMin);
                JL_Log.m849i(str, "doReconnectTask", "startBLEScan : " + iStartBLEScan + ", scanTime = " + jMin);
            }
        }
        if (iStartBLEScan != 0) {
            this.f751f.removeMessages(f743n);
            this.f751f.sendEmptyMessageDelayed(f743n, 3000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public boolean m796a(BluetoothDevice bluetoothDevice, byte[] bArr) {
        ReConnectDevMsg reConnectDevMsgM808e;
        if (bluetoothDevice == null || (reConnectDevMsgM808e = m808e()) == null) {
            return false;
        }
        String address = reConnectDevMsgM808e.getAddress();
        if (!BluetoothAdapter.checkBluetoothAddress(address)) {
            return false;
        }
        String str = f737h;
        JL_Log.m845d(str, "checkIsReconnectDevice", "device : " + m799b(bluetoothDevice));
        if (!reConnectDevMsgM808e.isUseADV()) {
            return address.equals(bluetoothDevice.getAddress());
        }
        JL_Log.m845d(str, "checkIsReconnectDevice", "advertiseRawData : " + CHexConver.byte2HexStr(bArr));
        BleScanMessage oTAFlagFilterWithBroad = ParseDataUtil.parseOTAFlagFilterWithBroad(bArr, JL_Constant.OTA_IDENTIFY);
        if (oTAFlagFilterWithBroad == null) {
            return false;
        }
        JL_Log.m845d(str, "checkIsReconnectDevice", "" + oTAFlagFilterWithBroad);
        return address.equalsIgnoreCase(oTAFlagFilterWithBroad.getOldBleAddress());
    }

    /* JADX INFO: renamed from: a */
    private void m788a(long j) {
        this.f749d = j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m789a(BluetoothDevice bluetoothDevice) {
        String str = f737h;
        JL_Log.m845d(str, "connectBtDevice", this.f748c + ", device : " + bluetoothDevice);
        if (this.f748c == null || this.f748c.getState() == 2) {
            return;
        }
        this.f748c.setState(2);
        long jM805d = m805d();
        JL_Log.m849i(str, "connectBtDevice", "left time = " + jM805d);
        if (jM805d <= MyAnimationUtil.ANI_TIME_2000) {
            this.f751f.removeMessages(f744o);
            this.f751f.sendEmptyMessageDelayed(f744o, 31000L);
            JL_Log.m849i(str, "connectBtDevice", "reset time >>> ");
        }
        this.f751f.removeMessages(f745p);
        this.f751f.sendEmptyMessageDelayed(f745p, 30000L);
        this.f747b.connectBluetoothDevice(bluetoothDevice);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m790a(BaseError baseError) {
        if (baseError == null) {
            return;
        }
        if (this.f747b.isOTA()) {
            this.f747b.errorEventCallback(baseError);
        }
        stopReconnectTask();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m795a(String str) {
        long jM805d = m805d();
        String str2 = f737h;
        JL_Log.m845d(str2, "dealWithConnectFailed", "address : " + str + ", Left Time = " + jM805d);
        if (jM805d <= MyAnimationUtil.ANI_TIME_2000) {
            JL_Log.m849i(str2, "dealWithConnectFailed", "time not enough.");
            m790a(OTAError.buildError(ErrorCode.SUB_ERR_RECONNECT_FAILED, str));
        } else {
            JL_Log.m849i(str2, "dealWithConnectFailed", "resume reconnect task.");
            this.f751f.removeMessages(f743n);
            this.f751f.sendEmptyMessage(f743n);
        }
    }

    /* JADX INFO: renamed from: b */
    private BluetoothDevice m798b(String str) {
        List<BluetoothDevice> systemConnectedBtDeviceList;
        if (BluetoothAdapter.checkBluetoothAddress(str) && (systemConnectedBtDeviceList = BluetoothUtil.getSystemConnectedBtDeviceList(this.f746a)) != null && !systemConnectedBtDeviceList.isEmpty()) {
            for (BluetoothDevice bluetoothDevice : systemConnectedBtDeviceList) {
                if (str.equals(bluetoothDevice.getAddress())) {
                    return bluetoothDevice;
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b */
    public String m799b(BluetoothDevice bluetoothDevice) {
        return BluetoothUtil.printBtDeviceInfo(this.f746a, bluetoothDevice);
    }
}
