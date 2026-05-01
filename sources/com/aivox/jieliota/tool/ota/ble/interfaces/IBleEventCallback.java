package com.aivox.jieliota.tool.ota.ble.interfaces;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattService;
import com.aivox.jieliota.tool.ota.ble.model.BleScanInfo;
import java.util.List;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public interface IBleEventCallback {
    void onAdapterChange(boolean z);

    void onBleConnection(BluetoothDevice bluetoothDevice, int i);

    void onBleDataBlockChanged(BluetoothDevice bluetoothDevice, int i, int i2);

    void onBleDataNotification(BluetoothDevice bluetoothDevice, UUID uuid, UUID uuid2, byte[] bArr);

    void onBleNotificationStatus(BluetoothDevice bluetoothDevice, UUID uuid, UUID uuid2, int i);

    void onBleServiceDiscovery(BluetoothDevice bluetoothDevice, int i, List<BluetoothGattService> list);

    void onBleWriteStatus(BluetoothDevice bluetoothDevice, UUID uuid, UUID uuid2, byte[] bArr, int i);

    void onConnectionUpdated(BluetoothDevice bluetoothDevice, int i, int i2, int i3, int i4);

    void onDiscoveryBle(BluetoothDevice bluetoothDevice, BleScanInfo bleScanInfo);

    void onDiscoveryBleChange(boolean z);
}
