package com.aivox.jieliota.tool.ota.spp.interfaces;

import android.bluetooth.BluetoothDevice;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public interface ISppEventCallback {
    void onAdapterChange(boolean z);

    void onDiscoveryDevice(BluetoothDevice bluetoothDevice, int i);

    void onDiscoveryDeviceChange(boolean z);

    void onReceiveSppData(BluetoothDevice bluetoothDevice, UUID uuid, byte[] bArr);

    void onSppConnection(BluetoothDevice bluetoothDevice, UUID uuid, int i);
}
