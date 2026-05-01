package com.jieli.jl_bt_ota.impl;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import com.jieli.jl_bt_ota.constant.ErrorCode;
import com.jieli.jl_bt_ota.model.BleScanMessage;
import com.jieli.jl_bt_ota.model.OTAError;
import com.jieli.jl_bt_ota.util.BluetoothUtil;
import com.jieli.jl_bt_ota.util.CommonUtil;
import com.jieli.jl_bt_ota.util.JL_Log;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public abstract class BluetoothDiscovery extends BluetoothBase {

    /* JADX INFO: renamed from: o */
    private static final int f621o = 4660;

    /* JADX INFO: renamed from: p */
    private static final int f622p = 4661;

    /* JADX INFO: renamed from: e */
    private final List<BluetoothDevice> f623e;

    /* JADX INFO: renamed from: f */
    private final List<BluetoothDevice> f624f;

    /* JADX INFO: renamed from: g */
    private BluetoothDiscoveryReceiver f625g;

    /* JADX INFO: renamed from: h */
    private BluetoothLeScanner f626h;

    /* JADX INFO: renamed from: i */
    private volatile int f627i;

    /* JADX INFO: renamed from: j */
    private volatile boolean f628j;

    /* JADX INFO: renamed from: k */
    private volatile boolean f629k;

    /* JADX INFO: renamed from: l */
    private final Handler f630l;

    /* JADX INFO: renamed from: m */
    private final BluetoothAdapter.LeScanCallback f631m;

    /* JADX INFO: renamed from: n */
    private final ScanCallback f632n;

    private class BluetoothDiscoveryReceiver extends BroadcastReceiver {
        private BluetoothDiscoveryReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) {
                return;
            }
            action.getClass();
            action.hashCode();
            switch (action) {
                case "android.bluetooth.adapter.action.DISCOVERY_FINISHED":
                    JL_Log.m845d(BluetoothDiscovery.this.TAG, "ACTION_DISCOVERY_FINISHED", "---->");
                    BluetoothDiscovery.this.m621a(false);
                    break;
                case "android.bluetooth.adapter.action.DISCOVERY_STARTED":
                    JL_Log.m845d(BluetoothDiscovery.this.TAG, "ACTION_DISCOVERY_STARTED", "---->");
                    if (!BluetoothDiscovery.this.isDeviceScanning()) {
                        BluetoothDiscovery.this.m621a(true);
                        break;
                    }
                    break;
                case "android.bluetooth.device.action.FOUND":
                    BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                    if (bluetoothDevice != null && !BluetoothDiscovery.this.m625a("ACTION_FOUND", true)) {
                        boolean z = (BluetoothDiscovery.this.f627i == 1 && bluetoothDevice.getType() != 2) || (BluetoothDiscovery.this.f627i == 0 && bluetoothDevice.getType() != 1) || BluetoothDiscovery.this.f627i == 2;
                        short shortExtra = intent.getShortExtra("android.bluetooth.device.extra.RSSI", (short) 0);
                        if (z && !BluetoothDiscovery.this.f624f.contains(bluetoothDevice)) {
                            BluetoothDiscovery.this.f624f.add(bluetoothDevice);
                            BluetoothDiscovery.this.mBtEventCbHelper.onDiscovery(bluetoothDevice, new BleScanMessage().setRssi(shortExtra).setEnableConnect(true));
                            break;
                        }
                    }
                    break;
            }
        }
    }

    public BluetoothDiscovery(Context context) {
        super(context);
        this.f623e = new ArrayList();
        this.f624f = new ArrayList();
        this.f628j = false;
        this.f629k = false;
        this.f630l = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.jieli.jl_bt_ota.impl.BluetoothDiscovery$$ExternalSyntheticLambda0
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                return this.f$0.m622a(message);
            }
        });
        this.f631m = new BluetoothAdapter.LeScanCallback() { // from class: com.jieli.jl_bt_ota.impl.BluetoothDiscovery$$ExternalSyntheticLambda1
            @Override // android.bluetooth.BluetoothAdapter.LeScanCallback
            public final void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
                this.f$0.m617a(bluetoothDevice, i, bArr);
            }
        };
        this.f632n = new ScanCallback() { // from class: com.jieli.jl_bt_ota.impl.BluetoothDiscovery.1
            @Override // android.bluetooth.le.ScanCallback
            public void onBatchScanResults(List<ScanResult> list) {
                super.onBatchScanResults(list);
            }

            @Override // android.bluetooth.le.ScanCallback
            public void onScanFailed(int i) {
                super.onScanFailed(i);
                BluetoothDiscovery.this.onError(OTAError.buildError(8194, i, "Scan ble error."));
            }

            @Override // android.bluetooth.le.ScanCallback
            public void onScanResult(int i, ScanResult scanResult) {
                super.onScanResult(i, scanResult);
                if (scanResult == null || scanResult.getScanRecord() == null) {
                    return;
                }
                BluetoothDiscovery.this.m618a(scanResult.getDevice(), scanResult.getRssi(), scanResult.getScanRecord().getBytes(), scanResult.isConnectable());
            }
        };
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        if (bluetoothAdapter != null) {
            this.f626h = bluetoothAdapter.getBluetoothLeScanner();
        }
    }

    /* JADX INFO: renamed from: c */
    private boolean m630c() {
        return BluetoothUtil.isBluetoothEnable();
    }

    /* JADX INFO: renamed from: d */
    private void m631d() {
        m616a(0);
        this.f628j = false;
        this.f629k = false;
        this.f623e.clear();
        this.f624f.clear();
    }

    public ArrayList<BluetoothDevice> getDiscoveredBluetoothDevices() {
        return this.f627i == 0 ? new ArrayList<>(this.f623e) : new ArrayList<>(this.f624f);
    }

    public int getScanType() {
        return this.f627i;
    }

    protected boolean isBleScanning() {
        return this.f628j;
    }

    protected boolean isDeviceScanning() {
        return this.f629k;
    }

    public boolean isScanning() {
        return this.f629k || this.f628j;
    }

    @Override // com.jieli.jl_bt_ota.impl.BluetoothBase
    public void onAdapterStatus(boolean z, boolean z2) {
        super.onAdapterStatus(z, z2);
        if (z) {
            return;
        }
        if (isScanning()) {
            m621a(false);
        }
        m631d();
    }

    @Override // com.jieli.jl_bt_ota.impl.BluetoothBase, com.jieli.jl_bt_ota.interfaces.IUpgradeManager
    public void release() {
        super.release();
        m627b();
        stopDeviceScan();
        stopBLEScan();
        m631d();
        this.f630l.removeCallbacksAndMessages(null);
    }

    public int startBLEScan(long j) {
        if (m624a("startBLEScan")) {
            return ErrorCode.SUB_ERR_OP_FAILED;
        }
        if (isDeviceScanning()) {
            JL_Log.m845d(this.TAG, "startBLEScan", "stopDeviceScan");
            if (stopDeviceScan() == 0) {
                int i = 0;
                do {
                    SystemClock.sleep(30L);
                    i += 30;
                    if (i > 300) {
                        break;
                    }
                } while (this.mBluetoothAdapter.isDiscovering());
            }
        }
        m616a(0);
        if (j <= 0) {
            j = 8000;
        }
        if (isBleScanning()) {
            JL_Log.m849i(this.TAG, "startBLEScan", "scanning ble ..... timeout = " + j);
            BluetoothLeScanner bluetoothLeScanner = this.f626h;
            if (bluetoothLeScanner != null) {
                bluetoothLeScanner.flushPendingScanResults(this.f632n);
            }
            this.f630l.removeMessages(f622p);
            this.f630l.sendEmptyMessageDelayed(f622p, j);
            m621a(true);
            return 0;
        }
        if (this.f626h != null) {
            this.f626h.startScan(new ArrayList(), new ScanSettings.Builder().setScanMode(this.mBluetoothOption.getBleScanMode()).setMatchMode(1).build(), this.f632n);
            JL_Log.m845d(this.TAG, "startBLEScan", "startScan : true");
        } else {
            boolean zStartLeScan = this.mBluetoothAdapter.startLeScan(this.f631m);
            JL_Log.m845d(this.TAG, "startBLEScan", "startLeScan : " + zStartLeScan);
            if (!zStartLeScan) {
                return 8194;
            }
        }
        JL_Log.m849i(this.TAG, "startBLEScan", "Ready to scan, timeout : " + j);
        this.f630l.removeMessages(f622p);
        this.f630l.sendEmptyMessageDelayed(f622p, j);
        m621a(true);
        return 0;
    }

    public int startDeviceScan(long j, int i) {
        if (i == 0) {
            return startBLEScan(j);
        }
        if (m624a("startDeviceScan")) {
            return ErrorCode.SUB_ERR_OP_FAILED;
        }
        if (isBleScanning()) {
            stopBLEScan();
            JL_Log.m849i(this.TAG, "startDeviceScan", "stopBLEScan.");
            SystemClock.sleep(100L);
        }
        m616a(i);
        long j2 = j <= 0 ? 8000L : j;
        if (isDeviceScanning()) {
            JL_Log.m845d(this.TAG, "startDeviceScan", "scanning br/edr ..... timeout = " + j);
            this.f630l.removeMessages(f621o);
            this.f630l.sendEmptyMessageDelayed(f621o, j2);
            m621a(true);
            return 0;
        }
        m615a();
        boolean zStartDiscovery = this.mBluetoothAdapter.startDiscovery();
        JL_Log.m845d(this.TAG, "startDeviceScan", "startDiscovery : " + zStartDiscovery);
        if (!zStartDiscovery) {
            m627b();
            return 8194;
        }
        this.f630l.removeMessages(f621o);
        this.f630l.sendEmptyMessageDelayed(f621o, j2);
        m621a(true);
        return 0;
    }

    public int stopBLEScan() {
        if (m625a("stopBLEScan", true)) {
            return ErrorCode.SUB_ERR_OP_FAILED;
        }
        if (!isBleScanning()) {
            this.mBtEventCbHelper.onDiscoveryStatus(true, false);
            return 0;
        }
        try {
            BluetoothLeScanner bluetoothLeScanner = this.f626h;
            if (bluetoothLeScanner != null) {
                bluetoothLeScanner.stopScan(this.f632n);
                JL_Log.m845d(this.TAG, "stopBLEScan", "stopScan");
            } else {
                this.mBluetoothAdapter.stopLeScan(this.f631m);
                JL_Log.m845d(this.TAG, "stopBLEScan", "stopLeScan");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.f630l.removeMessages(f622p);
        m621a(false);
        return 0;
    }

    public int stopDeviceScan() {
        if (m625a("stopDeviceScan", true)) {
            return ErrorCode.SUB_ERR_OP_FAILED;
        }
        if (!isDeviceScanning()) {
            this.mBtEventCbHelper.onDiscoveryStatus(false, false);
            return 0;
        }
        boolean zCancelDiscovery = this.mBluetoothAdapter.cancelDiscovery();
        JL_Log.m853w(this.TAG, "stopDeviceScan", "cancelDiscovery = " + zCancelDiscovery);
        if (!zCancelDiscovery) {
            return 8194;
        }
        this.f630l.removeMessages(f621o);
        return 0;
    }

    /* JADX INFO: renamed from: b */
    private boolean m629b(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null || !CommonUtil.checkHasConnectPermission(this.context)) {
            return false;
        }
        return bluetoothDevice.getType() == 2 || bluetoothDevice.getType() == 3;
    }

    /* JADX INFO: renamed from: b */
    private void m628b(boolean z) {
        List<BluetoothDevice> systemConnectedBtDeviceList = BluetoothUtil.getSystemConnectedBtDeviceList(this.context);
        if (systemConnectedBtDeviceList == null || systemConnectedBtDeviceList.isEmpty()) {
            return;
        }
        for (BluetoothDevice bluetoothDevice : systemConnectedBtDeviceList) {
            if (z && m629b(bluetoothDevice)) {
                if (!this.f623e.contains(bluetoothDevice)) {
                    this.f623e.add(bluetoothDevice);
                    this.mBtEventCbHelper.onDiscovery(bluetoothDevice, new BleScanMessage());
                }
            } else if (!z && !m629b(bluetoothDevice) && !this.f624f.contains(bluetoothDevice)) {
                this.f624f.add(bluetoothDevice);
                this.mBtEventCbHelper.onDiscovery(bluetoothDevice, new BleScanMessage());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public /* synthetic */ boolean m622a(Message message) {
        int i = message.what;
        if (i == f621o) {
            JL_Log.m849i(this.TAG, "MSG_STOP_EDR", "stopDeviceScan");
            stopDeviceScan();
            return false;
        }
        if (i != f622p) {
            return false;
        }
        JL_Log.m849i(this.TAG, "MSG_STOP_BLE", "stopBLEScan");
        stopBLEScan();
        return false;
    }

    /* JADX INFO: renamed from: a */
    private void m616a(int i) {
        this.f627i = i;
    }

    /* JADX INFO: renamed from: a */
    private boolean m624a(String str) {
        return m625a(str, false);
    }

    /* JADX INFO: renamed from: b */
    private void m627b() {
        Context context;
        BluetoothDiscoveryReceiver bluetoothDiscoveryReceiver = this.f625g;
        if (bluetoothDiscoveryReceiver == null || (context = this.context) == null) {
            return;
        }
        context.unregisterReceiver(bluetoothDiscoveryReceiver);
        this.f625g = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public boolean m625a(String str, boolean z) {
        if (!CommonUtil.checkHasScanPermission(this.context)) {
            JL_Log.m853w(this.TAG, str, "Missing bluetooth scan permissions.");
            return true;
        }
        if (!z && !CommonUtil.checkHasLocationPermission(this.context)) {
            JL_Log.m853w(this.TAG, str, "Missing location permissions.");
            return true;
        }
        if (!BluetoothUtil.isBluetoothEnable()) {
            JL_Log.m853w(this.TAG, str, "Bluetooth is off.");
            return true;
        }
        if (this.mBluetoothAdapter != null) {
            return false;
        }
        JL_Log.m853w(this.TAG, str, "The device is not supported bluetooth.");
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public /* synthetic */ void m617a(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
        m618a(bluetoothDevice, i, bArr, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m618a(BluetoothDevice bluetoothDevice, int i, byte[] bArr, boolean z) {
        if (bluetoothDevice == null || m625a("filterDevice", true) || this.f623e.contains(bluetoothDevice)) {
            return;
        }
        this.f623e.add(bluetoothDevice);
        this.mBtEventCbHelper.onDiscovery(bluetoothDevice, new BleScanMessage().setRawData(bArr).setRssi(i).setEnableConnect(z));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m621a(boolean z) {
        boolean z2 = this.f627i == 0;
        JL_Log.m849i(this.TAG, "notifyDiscoveryStatus", "scanType : " + this.f627i + ", bStart : " + z);
        if (z) {
            if (z2) {
                this.f628j = true;
                this.f623e.clear();
            } else {
                this.f629k = true;
                this.f624f.clear();
            }
        } else if (z2) {
            this.f628j = false;
        } else {
            this.f629k = false;
            this.f630l.removeMessages(f621o);
            m627b();
        }
        this.mBtEventCbHelper.onDiscoveryStatus(z2, z);
        if (!z) {
            m616a(0);
        } else {
            m628b(z2);
        }
    }

    /* JADX INFO: renamed from: a */
    private void m615a() {
        if (this.f625g != null || this.context == null) {
            return;
        }
        this.f625g = new BluetoothDiscoveryReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.adapter.action.DISCOVERY_STARTED");
        intentFilter.addAction("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
        intentFilter.addAction("android.bluetooth.device.action.FOUND");
        this.context.registerReceiver(this.f625g, intentFilter);
    }
}
