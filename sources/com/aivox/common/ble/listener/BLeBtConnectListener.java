package com.aivox.common.ble.listener;

import android.bluetooth.BluetoothDevice;
import com.aivox.base.common.MyEnum;

/* JADX INFO: loaded from: classes.dex */
public interface BLeBtConnectListener {
    void connectFailed(String str);

    void connectSuccess();

    void openBle();

    void refreshDeviceList(BluetoothDevice bluetoothDevice, int i, MyEnum.BLE_DEVICE_MODEL ble_device_model);
}
