package com.aivox.jieliota.tool.ota.ble.interfaces;

import android.bluetooth.BluetoothDevice;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public interface OnWriteDataCallback {
    void onBleResult(BluetoothDevice bluetoothDevice, UUID uuid, UUID uuid2, boolean z, byte[] bArr);
}
