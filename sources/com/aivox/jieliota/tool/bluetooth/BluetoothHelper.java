package com.aivox.jieliota.tool.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import com.aivox.jieliota.tool.config.ConfigHelper;
import com.aivox.jieliota.tool.ota.ble.BleManager;
import com.aivox.jieliota.tool.ota.ble.interfaces.BleEventCallback;
import com.aivox.jieliota.tool.ota.ble.interfaces.OnWriteDataCallback;
import com.aivox.jieliota.tool.ota.ble.model.BleScanInfo;
import com.aivox.jieliota.tool.ota.spp.SppManager;
import com.aivox.jieliota.tool.ota.spp.interfaces.OnWriteSppDataCallback;
import com.aivox.jieliota.tool.ota.spp.interfaces.SppEventCallback;
import com.aivox.jieliota.util.AppUtil;
import com.jieli.jl_bt_ota.util.BluetoothUtil;
import com.jieli.jl_bt_ota.util.CHexConver;
import com.jieli.jl_bt_ota.util.JL_Log;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BluetoothHelper.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m1900d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u0012\n\u0002\b\u0002*\u0002\u0004\r\u0018\u0000 32\u00020\u0001:\u00013B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014J\u0010\u0010\u0015\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014J\u0006\u0010\u0016\u001a\u00020\u0017J\u0010\u0010\u0018\u001a\u00020\u00172\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014J\u0006\u0010\u0019\u001a\u00020\u001aJ\b\u0010\u001b\u001a\u0004\u0018\u00010\u0014J\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dJ\u0006\u0010\u001e\u001a\u00020\u0012J\u0006\u0010\u001f\u001a\u00020\u0012J\u0010\u0010 \u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014J\u0006\u0010!\u001a\u00020\u0012J\u0014\u0010\"\u001a\u0004\u0018\u00010#2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0002J\u0018\u0010$\u001a\u00020\u00172\b\u0010%\u001a\u0004\u0018\u00010#2\u0006\u0010&\u001a\u00020\u0012J\u000e\u0010'\u001a\u00020\u00172\u0006\u0010(\u001a\u00020)J\u000e\u0010*\u001a\u00020\u00122\u0006\u0010+\u001a\u00020,J\u0006\u0010-\u001a\u00020\u0017J\u000e\u0010.\u001a\u00020\u00172\u0006\u0010(\u001a\u00020)J\u001a\u0010/\u001a\u00020\u00122\b\u00100\u001a\u0004\u0018\u00010\u00142\b\u00101\u001a\u0004\u0018\u000102R\u0010\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0005R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00064"}, m1901d2 = {"Lcom/aivox/jieliota/tool/bluetooth/BluetoothHelper;", "", "()V", "bleEventCallback", "com/aivox/jieliota/tool/bluetooth/BluetoothHelper$bleEventCallback$1", "Lcom/aivox/jieliota/tool/bluetooth/BluetoothHelper$bleEventCallback$1;", "bleManager", "Lcom/aivox/jieliota/tool/ota/ble/BleManager;", "btEventCbHelper", "Lcom/aivox/jieliota/tool/bluetooth/BTEventCbHelper;", "configHelper", "Lcom/aivox/jieliota/tool/config/ConfigHelper;", "sppEventCallback", "com/aivox/jieliota/tool/bluetooth/BluetoothHelper$sppEventCallback$1", "Lcom/aivox/jieliota/tool/bluetooth/BluetoothHelper$sppEventCallback$1;", "sppManager", "Lcom/aivox/jieliota/tool/ota/spp/SppManager;", "connectBleDevice", "", "device", "Landroid/bluetooth/BluetoothDevice;", "connectDevice", "destroy", "", "disconnectDevice", "getBleMtu", "", "getConnectedDevice", "getConnectedGatt", "Landroid/bluetooth/BluetoothGatt;", "isConnected", "isConnecting", "isDeviceConnected", "isScanning", "printDeviceInfo", "", "reconnectDevice", "address", "isUseNewAdv", "registerCallback", "callback", "Lcom/aivox/jieliota/tool/bluetooth/OnBTEventCallback;", "startScan", "timeout", "", "stopScan", "unregisterCallback", "writeDataToDevice", "bluetoothDevice", "byteArray", "", "Companion", "jieliota_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
public final class BluetoothHelper {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String TAG = "BluetoothHelper";
    private static volatile BluetoothHelper instance;
    private final BluetoothHelper$bleEventCallback$1 bleEventCallback;
    private final BleManager bleManager;
    private final BTEventCbHelper btEventCbHelper;
    private final ConfigHelper configHelper = ConfigHelper.INSTANCE.getInstance();
    private final BluetoothHelper$sppEventCallback$1 sppEventCallback;
    private final SppManager sppManager;

    /* JADX WARN: Type inference failed for: r2v1, types: [com.aivox.jieliota.tool.bluetooth.BluetoothHelper$bleEventCallback$1] */
    /* JADX WARN: Type inference failed for: r3v0, types: [com.aivox.jieliota.tool.bluetooth.BluetoothHelper$sppEventCallback$1] */
    public BluetoothHelper() {
        BleManager bleManager = BleManager.getInstance();
        Intrinsics.checkNotNull(bleManager);
        this.bleManager = bleManager;
        SppManager sppManager = SppManager.getInstance();
        Intrinsics.checkNotNull(sppManager);
        this.sppManager = sppManager;
        this.btEventCbHelper = new BTEventCbHelper();
        ?? r2 = new BleEventCallback() { // from class: com.aivox.jieliota.tool.bluetooth.BluetoothHelper$bleEventCallback$1
            @Override // com.aivox.jieliota.tool.ota.ble.interfaces.BleEventCallback, com.aivox.jieliota.tool.ota.ble.interfaces.IBleEventCallback
            public void onAdapterChange(boolean bEnabled) {
                this.this$0.btEventCbHelper.onAdapterChange(bEnabled);
            }

            @Override // com.aivox.jieliota.tool.ota.ble.interfaces.BleEventCallback, com.aivox.jieliota.tool.ota.ble.interfaces.IBleEventCallback
            public void onDiscoveryBleChange(boolean bStart) {
                this.this$0.btEventCbHelper.onDiscoveryChange(bStart, 0);
            }

            @Override // com.aivox.jieliota.tool.ota.ble.interfaces.BleEventCallback, com.aivox.jieliota.tool.ota.ble.interfaces.IBleEventCallback
            public void onDiscoveryBle(BluetoothDevice device, BleScanInfo bleScanMessage) {
                this.this$0.btEventCbHelper.onDiscovery(device, bleScanMessage);
            }

            @Override // com.aivox.jieliota.tool.ota.ble.interfaces.BleEventCallback, com.aivox.jieliota.tool.ota.ble.interfaces.IBleEventCallback
            public void onBleConnection(BluetoothDevice device, int status) {
                this.this$0.btEventCbHelper.onDeviceConnection(device, 0, status);
            }

            @Override // com.aivox.jieliota.tool.ota.ble.interfaces.BleEventCallback, com.aivox.jieliota.tool.ota.ble.interfaces.IBleEventCallback
            public void onBleDataNotification(BluetoothDevice device, UUID serviceUuid, UUID characteristicsUuid, byte[] data) {
                this.this$0.btEventCbHelper.onReceiveData(device, 0, characteristicsUuid, data);
            }

            @Override // com.aivox.jieliota.tool.ota.ble.interfaces.BleEventCallback, com.aivox.jieliota.tool.ota.ble.interfaces.IBleEventCallback
            public void onBleDataBlockChanged(BluetoothDevice device, int block, int status) {
                this.this$0.btEventCbHelper.onBleMtuChange(device, block, status);
            }
        };
        this.bleEventCallback = r2;
        ?? r3 = new SppEventCallback() { // from class: com.aivox.jieliota.tool.bluetooth.BluetoothHelper$sppEventCallback$1
            @Override // com.aivox.jieliota.tool.ota.spp.interfaces.SppEventCallback, com.aivox.jieliota.tool.ota.spp.interfaces.ISppEventCallback
            public void onAdapterChange(boolean bEnabled) {
                this.this$0.btEventCbHelper.onAdapterChange(bEnabled);
            }

            @Override // com.aivox.jieliota.tool.ota.spp.interfaces.SppEventCallback, com.aivox.jieliota.tool.ota.spp.interfaces.ISppEventCallback
            public void onDiscoveryDeviceChange(boolean bStart) {
                this.this$0.btEventCbHelper.onDiscoveryChange(bStart, 1);
            }

            @Override // com.aivox.jieliota.tool.ota.spp.interfaces.SppEventCallback, com.aivox.jieliota.tool.ota.spp.interfaces.ISppEventCallback
            public void onDiscoveryDevice(BluetoothDevice device, int rssi) {
                this.this$0.btEventCbHelper.onDiscovery(device, new BleScanInfo().setRssi(rssi));
            }

            @Override // com.aivox.jieliota.tool.ota.spp.interfaces.SppEventCallback, com.aivox.jieliota.tool.ota.spp.interfaces.ISppEventCallback
            public void onSppConnection(BluetoothDevice device, UUID uuid, int status) {
                if (status == 2 && this.this$0.configHelper.isUseMultiSppChannel() && !Intrinsics.areEqual(UUID.fromString(this.this$0.configHelper.getCustomSppChannel()), uuid)) {
                    JL_Log.m848i(BluetoothHelper.TAG, "onSppConnection :: skip custom uuid = " + uuid);
                } else {
                    JL_Log.m844d(BluetoothHelper.TAG, "onSppConnection :: device = " + AppUtil.printBtDeviceInfo(device) + ", uuid = " + uuid + ", status = " + status);
                    this.this$0.btEventCbHelper.onDeviceConnection(device, 1, status);
                }
            }

            @Override // com.aivox.jieliota.tool.ota.spp.interfaces.SppEventCallback, com.aivox.jieliota.tool.ota.spp.interfaces.ISppEventCallback
            public void onReceiveSppData(BluetoothDevice device, UUID uuid, byte[] data) {
                this.this$0.btEventCbHelper.onReceiveData(device, 1, uuid, data);
            }
        };
        this.sppEventCallback = r3;
        bleManager.registerBleEventCallback((BleEventCallback) r2);
        sppManager.registerSppEventCallback((SppEventCallback) r3);
    }

    /* JADX INFO: compiled from: BluetoothHelper.kt */
    @Metadata(m1900d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0007\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\b"}, m1901d2 = {"Lcom/aivox/jieliota/tool/bluetooth/BluetoothHelper$Companion;", "", "()V", "TAG", "", "instance", "Lcom/aivox/jieliota/tool/bluetooth/BluetoothHelper;", "getInstance", "jieliota_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final BluetoothHelper getInstance() {
            BluetoothHelper bluetoothHelper = BluetoothHelper.instance;
            if (bluetoothHelper == null) {
                synchronized (this) {
                    bluetoothHelper = BluetoothHelper.instance;
                    if (bluetoothHelper == null) {
                        bluetoothHelper = new BluetoothHelper();
                        Companion companion = BluetoothHelper.INSTANCE;
                        BluetoothHelper.instance = bluetoothHelper;
                    }
                }
            }
            return bluetoothHelper;
        }
    }

    public final void destroy() {
        this.bleManager.unregisterBleEventCallback(this.bleEventCallback);
        this.bleManager.destroy();
        this.sppManager.unregisterSppEventCallback(this.sppEventCallback);
        this.sppManager.release();
        this.btEventCbHelper.release();
        instance = null;
    }

    public final void registerCallback(OnBTEventCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.btEventCbHelper.registerCallback(callback);
    }

    public final void unregisterCallback(OnBTEventCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.btEventCbHelper.unregisterCallback(callback);
    }

    public final boolean isConnected() {
        return getConnectedDevice() != null;
    }

    public final boolean isDeviceConnected(BluetoothDevice device) {
        return BluetoothUtil.deviceEquals(getConnectedDevice(), device);
    }

    public final boolean isScanning() {
        if (this.configHelper.isBleWay()) {
            return this.bleManager.isBleScanning();
        }
        return this.sppManager.isScanning();
    }

    public final boolean isConnecting() {
        if (this.configHelper.isBleWay()) {
            return this.bleManager.isBleScanning();
        }
        return this.sppManager.isSppConnecting();
    }

    public final BluetoothDevice getConnectedDevice() {
        if (this.configHelper.isBleWay()) {
            return this.bleManager.getConnectedBtDevice();
        }
        return this.sppManager.getConnectedSppDevice();
    }

    public final BluetoothGatt getConnectedGatt() {
        if (this.configHelper.isBleWay()) {
            return this.bleManager.getConnectedBtGatt(getConnectedDevice());
        }
        return null;
    }

    public final int getBleMtu() {
        if (this.configHelper.isBleWay()) {
            return this.bleManager.getBleMtu(getConnectedDevice());
        }
        return 20;
    }

    public final boolean startScan(long timeout) {
        if (this.configHelper.isBleWay()) {
            return this.bleManager.startLeScan(timeout);
        }
        return this.sppManager.startDeviceScan(timeout);
    }

    public final void stopScan() {
        if (this.configHelper.isBleWay()) {
            this.bleManager.stopLeScan();
        } else {
            this.sppManager.stopDeviceScan();
        }
    }

    public final boolean connectDevice(BluetoothDevice device) {
        if (this.configHelper.isBleWay()) {
            return this.bleManager.connectBleDevice(device);
        }
        return this.sppManager.connectSpp(device);
    }

    public final void disconnectDevice(BluetoothDevice device) {
        if (this.configHelper.isBleWay()) {
            this.bleManager.disconnectBleDevice(device);
        } else {
            this.sppManager.disconnectSpp(device, null);
        }
    }

    public final boolean connectBleDevice(BluetoothDevice device) {
        return this.bleManager.connectBleDevice(device);
    }

    public final void reconnectDevice(String address, boolean isUseNewAdv) {
        if (this.configHelper.isBleWay()) {
            this.bleManager.reconnectDevice(address, isUseNewAdv);
        }
    }

    public final boolean writeDataToDevice(BluetoothDevice bluetoothDevice, byte[] byteArray) {
        if (bluetoothDevice == null || byteArray == null || byteArray.length == 0) {
            return false;
        }
        if (this.bleManager.getConnectedBtDevice() != null) {
            this.bleManager.writeDataByBleAsync(bluetoothDevice, BleManager.BLE_UUID_SERVICE, BleManager.BLE_UUID_WRITE, byteArray, new OnWriteDataCallback() { // from class: com.aivox.jieliota.tool.bluetooth.BluetoothHelper$$ExternalSyntheticLambda0
                @Override // com.aivox.jieliota.tool.ota.ble.interfaces.OnWriteDataCallback
                public final void onBleResult(BluetoothDevice bluetoothDevice2, UUID uuid, UUID uuid2, boolean z, byte[] bArr) {
                    BluetoothHelper.writeDataToDevice$lambda$0(this.f$0, bluetoothDevice2, uuid, uuid2, z, bArr);
                }
            });
            return true;
        }
        if (this.sppManager.getConnectedSppDevice() == null) {
            return true;
        }
        this.sppManager.writeDataToSppAsync(bluetoothDevice, SppManager.UUID_SPP, byteArray, new OnWriteSppDataCallback() { // from class: com.aivox.jieliota.tool.bluetooth.BluetoothHelper$$ExternalSyntheticLambda1
            @Override // com.aivox.jieliota.tool.ota.spp.interfaces.OnWriteSppDataCallback
            public final void onSppResult(BluetoothDevice bluetoothDevice2, UUID uuid, boolean z, byte[] bArr) {
                BluetoothHelper.writeDataToDevice$lambda$1(this.f$0, bluetoothDevice2, uuid, z, bArr);
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void writeDataToDevice$lambda$0(BluetoothHelper this$0, BluetoothDevice bluetoothDevice, UUID uuid, UUID uuid2, boolean z, byte[] bArr) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        JL_Log.m848i(TAG, "-writeDataByBleAsync- device:" + this$0.printDeviceInfo(bluetoothDevice) + ", result = " + z + ",\ndata:[" + CHexConver.byte2HexStr(bArr) + ']');
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void writeDataToDevice$lambda$1(BluetoothHelper this$0, BluetoothDevice bluetoothDevice, UUID uuid, boolean z, byte[] bArr) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        JL_Log.m848i(TAG, "-writeDataToSppAsync- device = " + this$0.printDeviceInfo(bluetoothDevice) + ", uuid = " + uuid + ", result = " + z + ",\ndata = " + CHexConver.byte2HexStr(bArr));
    }

    private final String printDeviceInfo(BluetoothDevice device) {
        return AppUtil.printBtDeviceInfo(device);
    }
}
