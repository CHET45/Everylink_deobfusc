package com.aivox.jieliota.tool.ota.ble.interfaces;

import android.bluetooth.BluetoothGatt;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public interface IBleOp {
    int getBleMtu();

    boolean writeDataByBle(BluetoothGatt bluetoothGatt, UUID uuid, UUID uuid2, byte[] bArr);
}
