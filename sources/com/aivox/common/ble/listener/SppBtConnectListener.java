package com.aivox.common.ble.listener;

import android.bluetooth.BluetoothDevice;

/* JADX INFO: loaded from: classes.dex */
public interface SppBtConnectListener {
    void onConnectFailed(String str);

    void onConnectSuccess(BluetoothDevice bluetoothDevice);
}
