package com.aivox.common.model;

import android.bluetooth.BluetoothDevice;
import com.aivox.base.common.MyEnum;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class LocalDeviceBean implements Serializable {
    private MyEnum.BLE_DEVICE_MODEL bleDevice;
    private BluetoothDevice bluetoothDevice;
    private MyEnum.DEVICE_MODEL device;
    private Boolean isFromNew;
    private Boolean isBleDevice = false;
    private int rssi = 0;

    public LocalDeviceBean(Boolean bool, BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
        this.isFromNew = bool;
    }

    public LocalDeviceBean(Boolean bool, MyEnum.DEVICE_MODEL device_model) {
        this.isFromNew = bool;
        this.device = device_model;
    }

    public LocalDeviceBean(Boolean bool, BluetoothDevice bluetoothDevice, MyEnum.BLE_DEVICE_MODEL ble_device_model) {
        this.bluetoothDevice = bluetoothDevice;
        this.isFromNew = bool;
        this.bleDevice = ble_device_model;
    }

    public BluetoothDevice getBluetoothDevice() {
        return this.bluetoothDevice;
    }

    public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    public Boolean getFromNew() {
        return this.isFromNew;
    }

    public void setFromNew(Boolean bool) {
        this.isFromNew = bool;
    }

    public MyEnum.DEVICE_MODEL getDevice() {
        return this.device;
    }

    public void setDevice(MyEnum.DEVICE_MODEL device_model) {
        this.device = device_model;
    }

    public Boolean getIsBleDevice() {
        return this.isBleDevice;
    }

    public void setBleDevice(Boolean bool) {
        this.isBleDevice = bool;
    }

    public void setBleDevice(MyEnum.BLE_DEVICE_MODEL ble_device_model) {
        this.bleDevice = ble_device_model;
    }

    public MyEnum.BLE_DEVICE_MODEL getBleDevice() {
        return this.bleDevice;
    }

    public int getRssi() {
        return this.rssi;
    }

    public void setRssi(int i) {
        this.rssi = i;
    }
}
