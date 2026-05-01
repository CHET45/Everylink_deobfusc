package com.aivox.jieliota.tool.ota;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.content.Context;
import com.aivox.jieliota.tool.bluetooth.BluetoothHelper;
import com.aivox.jieliota.tool.bluetooth.OnBTEventCallback;
import com.aivox.jieliota.tool.config.ConfigHelper;
import com.aivox.jieliota.tool.ota.spp.SppManager;
import com.aivox.jieliota.util.AppUtil;
import com.jieli.jl_bt_ota.impl.BluetoothOTAManager;
import com.jieli.jl_bt_ota.impl.RcspAuth;
import com.jieli.jl_bt_ota.model.BluetoothOTAConfigure;
import com.jieli.jl_bt_ota.util.BluetoothUtil;
import com.jieli.jl_bt_ota.util.CHexConver;
import com.jieli.jl_bt_ota.util.JL_Log;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: OTAManager.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m1900d1 = {"\u0000Q\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0003*\u0001\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J\u0012\u0010\u0010\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J\n\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016J\n\u0010\u0013\u001a\u0004\u0018\u00010\u000fH\u0016J\u0014\u0010\u0014\u001a\u0004\u0018\u00010\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u000fH\u0002J\b\u0010\u0017\u001a\u00020\rH\u0016J\u001c\u0010\u0018\u001a\u00020\u00192\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\r2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0015R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, m1901d2 = {"Lcom/aivox/jieliota/tool/ota/OTAManager;", "Lcom/jieli/jl_bt_ota/impl/BluetoothOTAManager;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "bluetoothHelper", "Lcom/aivox/jieliota/tool/bluetooth/BluetoothHelper;", "btEventCallback", "com/aivox/jieliota/tool/ota/OTAManager$btEventCallback$1", "Lcom/aivox/jieliota/tool/ota/OTAManager$btEventCallback$1;", "configHelper", "Lcom/aivox/jieliota/tool/config/ConfigHelper;", "connectBluetoothDevice", "", "bluetoothDevice", "Landroid/bluetooth/BluetoothDevice;", "disconnectBluetoothDevice", "getConnectedBluetoothGatt", "Landroid/bluetooth/BluetoothGatt;", "getConnectedDevice", "printDeviceInfo", "", "device", "release", "sendDataToDevice", "", "bytes", "", "setReconnectAddr", "addr", "jieliota_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
public final class OTAManager extends BluetoothOTAManager {
    private final BluetoothHelper bluetoothHelper;
    private final OTAManager$btEventCallback$1 btEventCallback;
    private final ConfigHelper configHelper;

    public final void setReconnectAddr(String addr) {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.aivox.jieliota.tool.ota.OTAManager$btEventCallback$1] */
    public OTAManager(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        ConfigHelper companion = ConfigHelper.INSTANCE.getInstance();
        this.configHelper = companion;
        BluetoothHelper companion2 = BluetoothHelper.INSTANCE.getInstance();
        this.bluetoothHelper = companion2;
        ?? r1 = new OnBTEventCallback() { // from class: com.aivox.jieliota.tool.ota.OTAManager$btEventCallback$1
            @Override // com.aivox.jieliota.tool.bluetooth.OnBTEventCallback
            public void onDeviceConnection(BluetoothDevice device, int way, int status) {
                super.onDeviceConnection(device, way, status);
                int iChangeConnectStatus = AppUtil.changeConnectStatus(status);
                JL_Log.m848i(this.this$0.TAG, "onConnection >>> device : " + this.this$0.printDeviceInfo(device) + ", way = " + way + " status ：" + status + ", change status : " + iChangeConnectStatus);
                this.this$0.onBtDeviceConnection(device, iChangeConnectStatus);
            }

            @Override // com.aivox.jieliota.tool.bluetooth.OnBTEventCallback
            public void onReceiveData(BluetoothDevice device, int way, UUID uuid, byte[] data) {
                super.onReceiveData(device, way, uuid, data);
                JL_Log.m844d(this.this$0.TAG, "onReceiveData >>> " + this.this$0.printDeviceInfo(device) + ", way = " + way + ",\nuuid = " + uuid + ", data ：" + CHexConver.byte2HexStr(data) + ' ');
                if (way == 1 && !Intrinsics.areEqual(SppManager.UUID_SPP, uuid)) {
                    JL_Log.m844d(this.this$0.TAG, "onReceiveData >>> skip spec");
                } else {
                    this.this$0.onReceiveDeviceData(device, data);
                }
            }

            @Override // com.aivox.jieliota.tool.bluetooth.OnBTEventCallback
            public void onBleMtuChange(BluetoothDevice device, int mtu, int status) {
                super.onBleMtuChange(device, mtu, status);
                OTAManager oTAManager = this.this$0;
                oTAManager.onMtuChanged(oTAManager.bluetoothHelper.getConnectedGatt(), mtu, status);
            }
        };
        this.btEventCallback = r1;
        BluetoothOTAConfigure bluetoothOTAConfigure = new BluetoothOTAConfigure();
        bluetoothOTAConfigure.setPriority(!companion.isBleWay() ? 1 : 0);
        bluetoothOTAConfigure.setUseReconnect(companion.isUseCustomReConnectWay() && companion.isHidDevice());
        bluetoothOTAConfigure.setUseAuthDevice(companion.isUseDeviceAuth());
        bluetoothOTAConfigure.setMtu(20);
        bluetoothOTAConfigure.setNeedChangeMtu(false);
        bluetoothOTAConfigure.setUseJLServer(false);
        configure(bluetoothOTAConfigure);
        RcspAuth.setAuthTimeout(5000L);
        companion2.registerCallback((OnBTEventCallback) r1);
        if (companion2.isConnected()) {
            onBtDeviceConnection(companion2.getConnectedDevice(), 1);
            if (companion.isBleWay()) {
                onMtuChanged(companion2.getConnectedGatt(), companion2.getBleMtu() + 3, 0);
            }
        }
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothManager
    public BluetoothDevice getConnectedDevice() {
        return this.bluetoothHelper.getConnectedDevice();
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothManager
    public BluetoothGatt getConnectedBluetoothGatt() {
        return this.bluetoothHelper.getConnectedGatt();
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothManager
    public void connectBluetoothDevice(BluetoothDevice bluetoothDevice) {
        if (this.bluetoothHelper.connectBleDevice(bluetoothDevice)) {
            return;
        }
        onBtDeviceConnection(bluetoothDevice, 2);
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothManager
    public void disconnectBluetoothDevice(BluetoothDevice bluetoothDevice) {
        this.bluetoothHelper.disconnectDevice(bluetoothDevice);
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothManager
    public boolean sendDataToDevice(BluetoothDevice bluetoothDevice, byte[] bytes) {
        JL_Log.m844d(this.TAG, "sendDataToDevice : device = " + printDeviceInfo(bluetoothDevice) + "\ndata = [" + CHexConver.byte2HexStr(bytes) + ']');
        return this.bluetoothHelper.writeDataToDevice(bluetoothDevice, bytes);
    }

    @Override // com.jieli.jl_bt_ota.impl.BluetoothOTAManager, com.jieli.jl_bt_ota.impl.BluetoothBreProfiles, com.jieli.jl_bt_ota.impl.BluetoothDiscovery, com.jieli.jl_bt_ota.impl.BluetoothBase, com.jieli.jl_bt_ota.interfaces.IUpgradeManager
    public void release() {
        super.release();
        this.bluetoothHelper.unregisterCallback(this.btEventCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String printDeviceInfo(BluetoothDevice device) {
        return BluetoothUtil.printBtDeviceInfo(this.context, device);
    }
}
