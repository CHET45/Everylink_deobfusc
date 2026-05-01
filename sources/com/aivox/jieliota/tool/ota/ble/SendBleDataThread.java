package com.aivox.jieliota.tool.ota.ble;

import android.bluetooth.BluetoothGatt;
import com.aivox.jieliota.tool.ota.ble.interfaces.IBleOp;
import com.aivox.jieliota.tool.ota.ble.interfaces.OnThreadStateListener;
import com.aivox.jieliota.tool.ota.ble.interfaces.OnWriteDataCallback;
import com.jieli.jl_bt_ota.util.JL_Log;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

/* JADX INFO: loaded from: classes.dex */
public class SendBleDataThread extends Thread {
    private static final String TAG = "SendBleDataThread";
    private volatile boolean isDataSend;
    private volatile boolean isThreadWaiting;
    private volatile boolean isWaitingForCallback;
    private final IBleOp mBleManager;
    private BleSendTask mCurrentTask;
    private final OnThreadStateListener mListener;
    private final LinkedBlockingQueue<BleSendTask> mQueue;
    private volatile int retryNum;

    public SendBleDataThread(IBleOp iBleOp, OnThreadStateListener onThreadStateListener) {
        super(TAG);
        this.mQueue = new LinkedBlockingQueue<>();
        this.isDataSend = false;
        this.isThreadWaiting = false;
        this.isWaitingForCallback = false;
        this.retryNum = 0;
        this.mBleManager = iBleOp;
        this.mListener = onThreadStateListener;
    }

    public boolean isRunning() {
        return this.isDataSend;
    }

    public boolean addSendTask(BluetoothGatt bluetoothGatt, UUID uuid, UUID uuid2, byte[] bArr, OnWriteDataCallback onWriteDataCallback) {
        IBleOp iBleOp = this.mBleManager;
        if (iBleOp == null || bluetoothGatt == null || uuid == null || uuid2 == null || bArr == null || bArr.length == 0) {
            return false;
        }
        int bleMtu = iBleOp.getBleMtu();
        JL_Log.m844d(TAG, "addSendTask : " + bleMtu);
        int length = bArr.length;
        int i = length / bleMtu;
        boolean zAddSendData = false;
        for (int i2 = 0; i2 < i; i2++) {
            byte[] bArr2 = new byte[bleMtu];
            System.arraycopy(bArr, i2 * bleMtu, bArr2, 0, bleMtu);
            zAddSendData = addSendData(bluetoothGatt, uuid, uuid2, bArr2, onWriteDataCallback);
        }
        int i3 = length % bleMtu;
        if (i3 == 0) {
            return zAddSendData;
        }
        byte[] bArr3 = new byte[i3];
        System.arraycopy(bArr, length - i3, bArr3, 0, i3);
        return addSendData(bluetoothGatt, uuid, uuid2, bArr3, onWriteDataCallback);
    }

    public void wakeupSendThread(BleSendTask bleSendTask) {
        BleSendTask bleSendTask2;
        if (bleSendTask == null || ((bleSendTask2 = this.mCurrentTask) != null && bleSendTask2.equals(bleSendTask))) {
            if (bleSendTask != null) {
                bleSendTask.setCallback(this.mCurrentTask.getCallback());
                this.mCurrentTask = bleSendTask;
            }
            synchronized (this.mQueue) {
                if (this.isThreadWaiting) {
                    if (this.isWaitingForCallback) {
                        this.mQueue.notifyAll();
                    } else {
                        this.mQueue.notify();
                    }
                } else if (this.isWaitingForCallback) {
                    this.mQueue.notify();
                }
            }
        }
    }

    private boolean addSendData(BluetoothGatt bluetoothGatt, UUID uuid, UUID uuid2, byte[] bArr, OnWriteDataCallback onWriteDataCallback) {
        boolean z;
        if (!this.isDataSend) {
            return false;
        }
        try {
            this.mQueue.put(new BleSendTask(bluetoothGatt, uuid, uuid2, bArr, onWriteDataCallback));
            z = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            z = false;
        }
        if (z && this.isThreadWaiting && !this.isWaitingForCallback) {
            this.isThreadWaiting = false;
            synchronized (this.mQueue) {
                this.mQueue.notify();
            }
        }
        return z;
    }

    @Override // java.lang.Thread
    public synchronized void start() {
        this.isDataSend = true;
        super.start();
    }

    public synchronized void stopThread() {
        this.isDataSend = false;
        wakeupSendThread(null);
    }

    private void callbackResult(BleSendTask bleSendTask, boolean z) {
        if (bleSendTask != null && bleSendTask.getCallback() != null) {
            if (bleSendTask.getBleGatt() == null) {
                return;
            }
            bleSendTask.getCallback().onBleResult(bleSendTask.getBleGatt().getDevice(), bleSendTask.getServiceUUID(), bleSendTask.getCharacteristicUUID(), z, bleSendTask.getData());
            return;
        }
        JL_Log.m848i(TAG, "getCallback is null.");
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        JL_Log.m844d(TAG, "send ble data thread is started.");
        OnThreadStateListener onThreadStateListener = this.mListener;
        if (onThreadStateListener != null) {
            onThreadStateListener.onStart(getId(), getName());
        }
        if (this.mBleManager != null) {
            synchronized (this.mQueue) {
                while (this.isDataSend) {
                    this.mCurrentTask = null;
                    this.isThreadWaiting = false;
                    this.isWaitingForCallback = false;
                    if (this.mQueue.isEmpty()) {
                        this.isThreadWaiting = true;
                        JL_Log.m844d(TAG, "queue is empty, so waiting for data");
                        try {
                            this.mQueue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        BleSendTask bleSendTaskPeek = this.mQueue.peek();
                        this.mCurrentTask = bleSendTaskPeek;
                        if (bleSendTaskPeek != null) {
                            this.isWaitingForCallback = this.mBleManager.writeDataByBle(bleSendTaskPeek.mGatt, this.mCurrentTask.getServiceUUID(), this.mCurrentTask.getCharacteristicUUID(), this.mCurrentTask.getData());
                            if (this.isWaitingForCallback) {
                                try {
                                    this.mQueue.wait(8000L);
                                } catch (InterruptedException e2) {
                                    e2.printStackTrace();
                                }
                            } else {
                                this.mCurrentTask.setStatus(-1);
                            }
                            JL_Log.m844d(TAG, "data send ret :" + this.mCurrentTask.getStatus());
                            if (this.mCurrentTask.getStatus() != 0) {
                                this.retryNum++;
                                if (this.retryNum >= 3) {
                                    callbackResult(this.mCurrentTask, false);
                                    this.mQueue.clear();
                                } else if (this.mCurrentTask.getStatus() != -1) {
                                    this.mCurrentTask.setStatus(-1);
                                    try {
                                        sleep(10L);
                                    } catch (InterruptedException e3) {
                                        e3.printStackTrace();
                                    }
                                }
                            } else {
                                callbackResult(this.mCurrentTask, true);
                            }
                        }
                        this.retryNum = 0;
                        if (!this.mQueue.isEmpty()) {
                            this.mQueue.poll();
                        }
                    }
                }
            }
            this.isWaitingForCallback = false;
            this.isThreadWaiting = false;
            this.mQueue.clear();
            OnThreadStateListener onThreadStateListener2 = this.mListener;
            if (onThreadStateListener2 != null) {
                onThreadStateListener2.onEnd(getId(), getName());
            }
            JL_Log.m844d(TAG, "send ble data thread exit.");
        }
    }

    public static class BleSendTask {
        private UUID characteristicUUID;
        private byte[] data;
        private OnWriteDataCallback mCallback;
        private BluetoothGatt mGatt;
        private UUID serviceUUID;
        private int status = -1;

        public BleSendTask(BluetoothGatt bluetoothGatt, UUID uuid, UUID uuid2, byte[] bArr, OnWriteDataCallback onWriteDataCallback) {
            this.mGatt = bluetoothGatt;
            this.serviceUUID = uuid;
            this.characteristicUUID = uuid2;
            this.data = bArr;
            this.mCallback = onWriteDataCallback;
        }

        public BluetoothGatt getBleGatt() {
            return this.mGatt;
        }

        public void setDevice(BluetoothGatt bluetoothGatt) {
            this.mGatt = bluetoothGatt;
        }

        public UUID getServiceUUID() {
            return this.serviceUUID;
        }

        public void setServiceUUID(UUID uuid) {
            this.serviceUUID = uuid;
        }

        public UUID getCharacteristicUUID() {
            return this.characteristicUUID;
        }

        public void setCharacteristicUUID(UUID uuid) {
            this.characteristicUUID = uuid;
        }

        public byte[] getData() {
            return this.data;
        }

        public void setData(byte[] bArr) {
            this.data = bArr;
        }

        public OnWriteDataCallback getCallback() {
            return this.mCallback;
        }

        public void setCallback(OnWriteDataCallback onWriteDataCallback) {
            this.mCallback = onWriteDataCallback;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int i) {
            this.status = i;
        }

        public String toString() {
            return "BleSendTask{mGatt=" + this.mGatt + ", serviceUUID=" + this.serviceUUID + ", characteristicUUID=" + this.characteristicUUID + ", data=" + Arrays.toString(this.data) + ", status=" + this.status + ", mCallback=" + this.mCallback + '}';
        }

        public int hashCode() {
            BluetoothGatt bluetoothGatt = this.mGatt;
            if (bluetoothGatt != null && this.serviceUUID != null && this.characteristicUUID != null) {
                return bluetoothGatt.hashCode() + this.serviceUUID.hashCode() + this.characteristicUUID.hashCode();
            }
            return super.hashCode();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof BleSendTask)) {
                return false;
            }
            BleSendTask bleSendTask = (BleSendTask) obj;
            BluetoothGatt bluetoothGatt = this.mGatt;
            return bluetoothGatt != null && this.serviceUUID != null && this.characteristicUUID != null && bluetoothGatt.equals(bleSendTask.getBleGatt()) && this.serviceUUID.equals(bleSendTask.getServiceUUID()) && this.characteristicUUID.equals(bleSendTask.getCharacteristicUUID());
        }
    }
}
