package com.aivox.jieliota.tool.ota.spp;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import com.jieli.jl_bt_ota.util.JL_Log;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class ReceiveSppDataThread extends Thread {
    public static final int EXIT_REASON_IO_EXCEPTION = 2;
    public static final int EXIT_REASON_PARAM_ERROR = 1;
    public static final int EXIT_REASON_SUCCESS = 0;
    private static final String TAG = "ReceiveSppDataThread";
    private volatile boolean isRunning;
    private final int mBlockSize;
    private final BluetoothSocket mBluetoothSocket;
    private final BluetoothDevice mConnectedSppDev;
    private final Context mContext;
    private final OnRecvSppDataListener mOnRecvSppDataListener;
    private final UUID mSppUUID;

    public interface OnRecvSppDataListener {
        void onRecvSppData(long j, BluetoothDevice bluetoothDevice, UUID uuid, byte[] bArr);

        void onThreadStart(long j);

        void onThreadStop(long j, int i, BluetoothDevice bluetoothDevice, UUID uuid);
    }

    public ReceiveSppDataThread(Context context, BluetoothDevice bluetoothDevice, UUID uuid, BluetoothSocket bluetoothSocket, OnRecvSppDataListener onRecvSppDataListener) {
        this(context, bluetoothDevice, uuid, bluetoothSocket, 4096, onRecvSppDataListener);
    }

    public ReceiveSppDataThread(Context context, BluetoothDevice bluetoothDevice, UUID uuid, BluetoothSocket bluetoothSocket, int i, OnRecvSppDataListener onRecvSppDataListener) {
        super("ReceiveSppDataThread : " + bluetoothDevice);
        this.mContext = context;
        this.mConnectedSppDev = bluetoothDevice;
        this.mSppUUID = uuid;
        this.mBluetoothSocket = bluetoothSocket;
        this.mBlockSize = i;
        this.mOnRecvSppDataListener = onRecvSppDataListener;
    }

    public BluetoothSocket getBluetoothSocket() {
        return this.mBluetoothSocket;
    }

    public UUID getSppUUID() {
        return this.mSppUUID;
    }

    public void stopThread() {
        JL_Log.m846e(TAG, "ReceiveDataThread stopThread.");
        this.isRunning = false;
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x0075 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x006f A[SYNTHETIC] */
    @Override // java.lang.Thread, java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void run() {
        /*
            Method dump skipped, instruction units count: 203
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aivox.jieliota.tool.ota.spp.ReceiveSppDataThread.run():void");
    }
}
