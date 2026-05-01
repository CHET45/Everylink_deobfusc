package com.aivox.jieliota.tool.ota.spp.interfaces;

import android.bluetooth.BluetoothDevice;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public interface OnWriteSppDataCallback {
    void onSppResult(BluetoothDevice bluetoothDevice, UUID uuid, boolean z, byte[] bArr);
}
