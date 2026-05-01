package com.aivox.jieliota.tool.ota.spp;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.aivox.jieliota.util.AppUtil;
import com.jieli.jl_bt_ota.util.BluetoothUtil;
import com.jieli.jl_bt_ota.util.JL_Log;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class ConnectionSppThread extends Thread {
    private static final String TAG = "ConnectionSppThread";
    private final Context mContext;
    private final BluetoothDevice mDevice;
    private final Handler mHandler;
    private final OnConnectSppListener mListener;
    private final UUID mSppUUID;

    public interface OnConnectSppListener {
        void onThreadStart(long j);

        void onThreadStop(long j, boolean z, BluetoothDevice bluetoothDevice, UUID uuid, BluetoothSocket bluetoothSocket);
    }

    public ConnectionSppThread(Context context, BluetoothDevice bluetoothDevice, UUID uuid, OnConnectSppListener onConnectSppListener) {
        super(TAG);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mContext = context;
        this.mDevice = bluetoothDevice;
        this.mSppUUID = uuid;
        this.mListener = onConnectSppListener;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public synchronized void run() {
        boolean z;
        onThreadStart(getId());
        if (this.mDevice != null && AppUtil.checkHasConnectPermission(this.mContext)) {
            JL_Log.m846e(TAG, "-ConnectionSppThread- connect device : " + BluetoothUtil.printBtDeviceInfo(this.mContext, this.mDevice) + ", uuid = " + this.mSppUUID);
            BluetoothSocket bluetoothSocketCreateRfcommSocketToServiceRecord = null;
            try {
                bluetoothSocketCreateRfcommSocketToServiceRecord = this.mDevice.createRfcommSocketToServiceRecord(this.mSppUUID);
                bluetoothSocketCreateRfcommSocketToServiceRecord.connect();
                z = true;
            } catch (Exception e) {
                e.printStackTrace();
                JL_Log.m846e(TAG, "-ConnectionSppThread- exception : " + e.getMessage() + ", uuid = " + this.mSppUUID);
                z = false;
            }
            boolean z2 = z;
            BluetoothSocket bluetoothSocket = bluetoothSocketCreateRfcommSocketToServiceRecord;
            if (z2) {
                JL_Log.m848i(TAG, "-ConnectionSppThread- connect spp ok, recv max = " + bluetoothSocket.getMaxReceivePacketSize() + ", send max = " + bluetoothSocket.getMaxTransmitPacketSize());
            }
            onThreadStop(getId(), z2, this.mDevice, this.mSppUUID, bluetoothSocket);
            return;
        }
        onThreadStop(getId(), false, null, null, null);
    }

    public void onThreadStart(final long j) {
        if (this.mListener != null) {
            this.mHandler.post(new Runnable() { // from class: com.aivox.jieliota.tool.ota.spp.ConnectionSppThread$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m363x30407fd5(j);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$onThreadStart$0$com-aivox-jieliota-tool-ota-spp-ConnectionSppThread */
    /* synthetic */ void m363x30407fd5(long j) {
        this.mListener.onThreadStart(j);
    }

    public void onThreadStop(final long j, final boolean z, final BluetoothDevice bluetoothDevice, final UUID uuid, final BluetoothSocket bluetoothSocket) {
        if (this.mListener != null) {
            this.mHandler.post(new Runnable() { // from class: com.aivox.jieliota.tool.ota.spp.ConnectionSppThread$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m364x6f631382(j, z, bluetoothDevice, uuid, bluetoothSocket);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$onThreadStop$1$com-aivox-jieliota-tool-ota-spp-ConnectionSppThread */
    /* synthetic */ void m364x6f631382(long j, boolean z, BluetoothDevice bluetoothDevice, UUID uuid, BluetoothSocket bluetoothSocket) {
        this.mListener.onThreadStop(j, z, bluetoothDevice, uuid, bluetoothSocket);
    }
}
