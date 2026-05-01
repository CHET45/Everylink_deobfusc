package com.aivox.jieliota.tool.ota.spp.interfaces;

import android.bluetooth.BluetoothDevice;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public abstract class SppEventCallback implements ISppEventCallback {
    @Override // com.aivox.jieliota.tool.ota.spp.interfaces.ISppEventCallback
    public void onAdapterChange(boolean z) {
    }

    @Override // com.aivox.jieliota.tool.ota.spp.interfaces.ISppEventCallback
    public void onDiscoveryDevice(BluetoothDevice bluetoothDevice, int i) {
    }

    @Override // com.aivox.jieliota.tool.ota.spp.interfaces.ISppEventCallback
    public void onDiscoveryDeviceChange(boolean z) {
    }

    @Override // com.aivox.jieliota.tool.ota.spp.interfaces.ISppEventCallback
    public void onReceiveSppData(BluetoothDevice bluetoothDevice, UUID uuid, byte[] bArr) {
    }

    @Override // com.aivox.jieliota.tool.ota.spp.interfaces.ISppEventCallback
    public void onSppConnection(BluetoothDevice bluetoothDevice, UUID uuid, int i) {
    }
}
