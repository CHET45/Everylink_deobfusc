package com.aivox.jieliota.tool.ota.ble.model;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import com.aivox.jieliota.tool.ota.ble.SendBleDataThread;
import com.aivox.jieliota.tool.ota.ble.interfaces.IBleOp;
import com.aivox.jieliota.tool.ota.ble.interfaces.OnThreadStateListener;
import com.aivox.jieliota.tool.ota.ble.interfaces.OnWriteDataCallback;
import com.aivox.jieliota.util.AppUtil;
import com.jieli.jl_bt_ota.util.CHexConver;
import com.jieli.jl_bt_ota.util.JL_Log;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class BleDevice {
    private long connectedTime;
    private final Context context;
    private final BluetoothGatt gatt;
    private SendBleDataThread sendDataThread;
    private final String tag = "BleManager";
    private int mtu = 20;

    public BleDevice(Context context, BluetoothGatt bluetoothGatt) {
        this.context = context;
        this.gatt = bluetoothGatt;
    }

    public BluetoothGatt getGatt() {
        return this.gatt;
    }

    public int getMtu() {
        int i = this.mtu;
        return i > 128 ? i - 6 : i;
    }

    public void setMtu(int i) {
        this.mtu = i;
    }

    public long getConnectedTime() {
        return this.connectedTime;
    }

    public void setConnectedTime(long j) {
        this.connectedTime = j;
    }

    public void startSendDataThread() {
        SendBleDataThread sendBleDataThread = this.sendDataThread;
        if (sendBleDataThread == null || !sendBleDataThread.isRunning()) {
            SendBleDataThread sendBleDataThread2 = new SendBleDataThread(new IBleOp() { // from class: com.aivox.jieliota.tool.ota.ble.model.BleDevice.1
                @Override // com.aivox.jieliota.tool.ota.ble.interfaces.IBleOp
                public int getBleMtu() {
                    return BleDevice.this.getMtu();
                }

                @Override // com.aivox.jieliota.tool.ota.ble.interfaces.IBleOp
                public boolean writeDataByBle(BluetoothGatt bluetoothGatt, UUID uuid, UUID uuid2, byte[] bArr) {
                    return BleDevice.this.writeDataToDeviceByBle(bluetoothGatt, uuid, uuid2, bArr);
                }
            }, new OnThreadStateListener() { // from class: com.aivox.jieliota.tool.ota.ble.model.BleDevice.2
                @Override // com.aivox.jieliota.tool.ota.ble.interfaces.OnThreadStateListener
                public void onStart(long j, String str) {
                }

                @Override // com.aivox.jieliota.tool.ota.ble.interfaces.OnThreadStateListener
                public void onEnd(long j, String str) {
                    if (BleDevice.this.sendDataThread == null || BleDevice.this.sendDataThread.getId() != j) {
                        return;
                    }
                    BleDevice.this.sendDataThread = null;
                }
            });
            this.sendDataThread = sendBleDataThread2;
            sendBleDataThread2.start();
        }
    }

    public void stopSendDataThread() {
        SendBleDataThread sendBleDataThread = this.sendDataThread;
        if (sendBleDataThread != null) {
            sendBleDataThread.stopThread();
        }
    }

    public void wakeupSendThread(SendBleDataThread.BleSendTask bleSendTask) {
        if (this.sendDataThread == null || bleSendTask == null || !this.gatt.equals(bleSendTask.getBleGatt())) {
            return;
        }
        this.sendDataThread.wakeupSendThread(bleSendTask);
    }

    public boolean addSendTask(UUID uuid, UUID uuid2, byte[] bArr, OnWriteDataCallback onWriteDataCallback) {
        SendBleDataThread sendBleDataThread = this.sendDataThread;
        if (sendBleDataThread == null || !sendBleDataThread.isRunning()) {
            return false;
        }
        return this.sendDataThread.addSendTask(this.gatt, uuid, uuid2, bArr, onWriteDataCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean writeDataToDeviceByBle(BluetoothGatt bluetoothGatt, UUID uuid, UUID uuid2, byte[] bArr) {
        boolean zWriteCharacteristic = false;
        if (bluetoothGatt == null || uuid == null || uuid2 == null || bArr == null || bArr.length == 0 || !AppUtil.checkHasConnectPermission(this.context)) {
            JL_Log.m844d("BleManager", "writeDataByBle : param is invalid.");
            return false;
        }
        BluetoothGattService service = bluetoothGatt.getService(uuid);
        if (service == null) {
            JL_Log.m844d("BleManager", "writeDataByBle : service is null.");
            return false;
        }
        BluetoothGattCharacteristic characteristic = service.getCharacteristic(uuid2);
        if (characteristic == null) {
            JL_Log.m844d("BleManager", "writeDataByBle : characteristic is null");
            return false;
        }
        try {
            characteristic.setValue(bArr);
            zWriteCharacteristic = bluetoothGatt.writeCharacteristic(characteristic);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JL_Log.m844d("BleManager", "writeDataByBle : send ret : " + zWriteCharacteristic + ", data = " + CHexConver.byte2HexStr(bArr));
        return zWriteCharacteristic;
    }

    public String toString() {
        return "BleDevice{context=" + this.context + ", gatt=" + this.gatt + ", mtu=" + this.mtu + ", connectedTime=" + this.connectedTime + ", sendDataThread=" + this.sendDataThread + '}';
    }
}
