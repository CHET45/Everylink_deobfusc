package com.aivox.jieliota.tool.ota.spp;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.aivox.jieliota.tool.ota.spp.interfaces.OnWriteSppDataCallback;
import com.jieli.jl_bt_ota.util.CHexConver;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

/* JADX INFO: loaded from: classes.dex */
public class SendSppDataThread extends Thread {
    private static final String TAG = "SendSppDataThread";
    private final Context mContext;
    private final OnSendDataListener mListener;
    private final ISppOp mSppOp;
    private final LinkedBlockingQueue<SppSendTask> mQueue = new LinkedBlockingQueue<>();
    private volatile boolean isDataSend = false;
    private volatile boolean isQueueEmpty = false;
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public interface ISppOp {
        boolean writeDataToSppDevice(BluetoothDevice bluetoothDevice, UUID uuid, byte[] bArr) throws IOException;
    }

    public interface OnSendDataListener {
        void onThreadStart(long j);

        void onThreadStop(long j);
    }

    public SendSppDataThread(Context context, ISppOp iSppOp, OnSendDataListener onSendDataListener) {
        this.mContext = context;
        this.mSppOp = iSppOp;
        this.mListener = onSendDataListener;
    }

    @Override // java.lang.Thread
    public synchronized void start() {
        this.isDataSend = true;
        super.start();
    }

    public synchronized void stopThread() {
        this.isDataSend = false;
        wakeupThread();
    }

    public void addSendTask(SppSendTask sppSendTask) {
        if (this.isDataSend) {
            try {
                this.mQueue.put(sppSendTask);
                if (this.isQueueEmpty) {
                    this.isQueueEmpty = false;
                    synchronized (this.mQueue) {
                        this.mQueue.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void wakeupThread() {
        synchronized (this.mQueue) {
            if (this.isQueueEmpty) {
                this.mQueue.notifyAll();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:53:? A[RETURN, SYNTHETIC] */
    @Override // java.lang.Thread, java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void run() {
        /*
            r8 = this;
            long r0 = r8.getId()
            com.aivox.jieliota.tool.ota.spp.SendSppDataThread$OnSendDataListener r2 = r8.mListener
            if (r2 == 0) goto L12
            android.os.Handler r2 = r8.mHandler
            com.aivox.jieliota.tool.ota.spp.SendSppDataThread$$ExternalSyntheticLambda0 r3 = new com.aivox.jieliota.tool.ota.spp.SendSppDataThread$$ExternalSyntheticLambda0
            r3.<init>()
            r2.post(r3)
        L12:
            com.aivox.jieliota.tool.ota.spp.SendSppDataThread$ISppOp r2 = r8.mSppOp
            if (r2 == 0) goto L84
            android.content.Context r2 = r8.mContext
            boolean r2 = com.aivox.jieliota.util.AppUtil.checkHasConnectPermission(r2)
            if (r2 != 0) goto L1f
            goto L84
        L1f:
            java.util.concurrent.LinkedBlockingQueue<com.aivox.jieliota.tool.ota.spp.SendSppDataThread$SppSendTask> r2 = r8.mQueue
            monitor-enter(r2)
        L22:
            boolean r3 = r8.isDataSend     // Catch: java.lang.Throwable -> L81
            if (r3 == 0) goto L6c
            java.util.concurrent.LinkedBlockingQueue<com.aivox.jieliota.tool.ota.spp.SendSppDataThread$SppSendTask> r3 = r8.mQueue     // Catch: java.lang.Throwable -> L81
            boolean r3 = r3.isEmpty()     // Catch: java.lang.Throwable -> L81
            r8.isQueueEmpty = r3     // Catch: java.lang.Throwable -> L81
            boolean r3 = r8.isQueueEmpty     // Catch: java.lang.Throwable -> L81
            if (r3 == 0) goto L44
            java.lang.String r3 = "SendSppDataThread"
            java.lang.String r4 = "queue is empty, so waiting for data"
            com.jieli.jl_bt_ota.util.JL_Log.m844d(r3, r4)     // Catch: java.lang.Throwable -> L81
            java.util.concurrent.LinkedBlockingQueue<com.aivox.jieliota.tool.ota.spp.SendSppDataThread$SppSendTask> r3 = r8.mQueue     // Catch: java.lang.InterruptedException -> L3f java.lang.Throwable -> L81
            r3.wait()     // Catch: java.lang.InterruptedException -> L3f java.lang.Throwable -> L81
            goto L22
        L3f:
            r3 = move-exception
            r3.printStackTrace()     // Catch: java.lang.Throwable -> L81
            goto L22
        L44:
            java.util.concurrent.LinkedBlockingQueue<com.aivox.jieliota.tool.ota.spp.SendSppDataThread$SppSendTask> r3 = r8.mQueue     // Catch: java.lang.Throwable -> L81
            java.lang.Object r3 = r3.poll()     // Catch: java.lang.Throwable -> L81
            com.aivox.jieliota.tool.ota.spp.SendSppDataThread$SppSendTask r3 = (com.aivox.jieliota.tool.ota.spp.SendSppDataThread.SppSendTask) r3     // Catch: java.lang.Throwable -> L81
            if (r3 == 0) goto L22
            com.aivox.jieliota.tool.ota.spp.SendSppDataThread$ISppOp r4 = r8.mSppOp     // Catch: java.io.IOException -> L65 java.lang.Throwable -> L81
            android.bluetooth.BluetoothDevice r5 = r3.device     // Catch: java.io.IOException -> L65 java.lang.Throwable -> L81
            java.util.UUID r6 = r3.sppUUID     // Catch: java.io.IOException -> L65 java.lang.Throwable -> L81
            byte[] r7 = r3.data     // Catch: java.io.IOException -> L65 java.lang.Throwable -> L81
            boolean r4 = r4.writeDataToSppDevice(r5, r6, r7)     // Catch: java.io.IOException -> L65 java.lang.Throwable -> L81
            android.os.Handler r5 = r8.mHandler     // Catch: java.lang.Throwable -> L81
            com.aivox.jieliota.tool.ota.spp.SendSppDataThread$$ExternalSyntheticLambda2 r6 = new com.aivox.jieliota.tool.ota.spp.SendSppDataThread$$ExternalSyntheticLambda2     // Catch: java.lang.Throwable -> L81
            r6.<init>()     // Catch: java.lang.Throwable -> L81
            r5.post(r6)     // Catch: java.lang.Throwable -> L81
            goto L22
        L65:
            r3 = move-exception
            r3.printStackTrace()     // Catch: java.lang.Throwable -> L81
            r3 = 0
            r8.isDataSend = r3     // Catch: java.lang.Throwable -> L81
        L6c:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L81
            java.util.concurrent.LinkedBlockingQueue<com.aivox.jieliota.tool.ota.spp.SendSppDataThread$SppSendTask> r2 = r8.mQueue
            r2.clear()
            com.aivox.jieliota.tool.ota.spp.SendSppDataThread$OnSendDataListener r2 = r8.mListener
            if (r2 == 0) goto L80
            android.os.Handler r2 = r8.mHandler
            com.aivox.jieliota.tool.ota.spp.SendSppDataThread$$ExternalSyntheticLambda3 r3 = new com.aivox.jieliota.tool.ota.spp.SendSppDataThread$$ExternalSyntheticLambda3
            r3.<init>()
            r2.post(r3)
        L80:
            return
        L81:
            r0 = move-exception
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L81
            throw r0
        L84:
            com.aivox.jieliota.tool.ota.spp.SendSppDataThread$OnSendDataListener r2 = r8.mListener
            if (r2 == 0) goto L92
            android.os.Handler r2 = r8.mHandler
            com.aivox.jieliota.tool.ota.spp.SendSppDataThread$$ExternalSyntheticLambda1 r3 = new com.aivox.jieliota.tool.ota.spp.SendSppDataThread$$ExternalSyntheticLambda1
            r3.<init>()
            r2.post(r3)
        L92:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aivox.jieliota.tool.ota.spp.SendSppDataThread.run():void");
    }

    /* JADX INFO: renamed from: lambda$run$0$com-aivox-jieliota-tool-ota-spp-SendSppDataThread, reason: not valid java name */
    /* synthetic */ void m2518lambda$run$0$comaivoxjieliotatoolotasppSendSppDataThread(long j) {
        this.mListener.onThreadStart(j);
    }

    /* JADX INFO: renamed from: lambda$run$1$com-aivox-jieliota-tool-ota-spp-SendSppDataThread, reason: not valid java name */
    /* synthetic */ void m2519lambda$run$1$comaivoxjieliotatoolotasppSendSppDataThread(long j) {
        this.mListener.onThreadStop(j);
    }

    static /* synthetic */ void lambda$run$2(SppSendTask sppSendTask, boolean z) {
        if (sppSendTask.callback != null) {
            sppSendTask.callback.onSppResult(sppSendTask.device, sppSendTask.sppUUID, z, sppSendTask.data);
        }
    }

    /* JADX INFO: renamed from: lambda$run$3$com-aivox-jieliota-tool-ota-spp-SendSppDataThread, reason: not valid java name */
    /* synthetic */ void m2520lambda$run$3$comaivoxjieliotatoolotasppSendSppDataThread(long j) {
        this.mListener.onThreadStop(j);
    }

    public static class SppSendTask {
        public OnWriteSppDataCallback callback;
        public byte[] data;
        public BluetoothDevice device;
        public UUID sppUUID;

        public SppSendTask(BluetoothDevice bluetoothDevice, UUID uuid, byte[] bArr, OnWriteSppDataCallback onWriteSppDataCallback) {
            this.device = bluetoothDevice;
            this.sppUUID = uuid;
            this.data = bArr;
            this.callback = onWriteSppDataCallback;
        }

        public String toString() {
            return "SppSendTask{device=" + this.device + ", sppUUID=" + this.sppUUID + ", data=" + CHexConver.byte2HexStr(this.data) + ", callback=" + this.callback + '}';
        }
    }
}
