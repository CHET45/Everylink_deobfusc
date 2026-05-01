package com.aivox.common.ble;

import com.aivox.base.common.Constant;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.ThreadPoolManager;
import com.aivox.common.ble.service.CommonServiceUtils;
import com.aivox.common.speech2text.BaseAudioDataSource;
import com.tencent.aai.exception.ClientException;
import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/* JADX INFO: loaded from: classes.dex */
public class BleDataSourceUp extends BaseAudioDataSource {
    private static final String TAG = "BleDataSourceUp";

    /* JADX INFO: renamed from: up */
    private static BleDataSourceUp f201up;
    private byte[] curData;
    private volatile boolean isRecognizing;
    private volatile boolean isRecording = false;
    private BlockingQueue<byte[]> mDownByteQueue;

    @Override // com.tencent.aai.audio.data.PcmAudioDataSource
    public boolean isSetSaveAudioRecordFiles() {
        return false;
    }

    @Override // com.tencent.aai.audio.data.PcmAudioDataSource
    public void start() throws ClientException {
    }

    @Override // com.tencent.aai.audio.data.PcmAudioDataSource
    public void stop() {
    }

    public BleDataSourceUp() {
        if (this.mDownByteQueue == null) {
            this.mDownByteQueue = new LinkedBlockingDeque();
        }
    }

    private static final class MInstanceHolder {
        static final BleDataSourceUp mInstance = new BleDataSourceUp();

        private MInstanceHolder() {
        }
    }

    public static BleDataSourceUp getInstance() {
        if (f201up == null) {
            f201up = new BleDataSourceUp();
        }
        return f201up;
    }

    @Override // com.aivox.common.speech2text.BaseAudioDataSource
    public void changePause(boolean z, boolean z2) {
        LogUtil.m335d(TAG, "changePause" + z);
        if (!z || this.isRecording) {
            this.isRecording = !z;
            if (z) {
                CommonServiceUtils.getInstance().sendData(Constant.CmdUpEnd);
            } else {
                ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.aivox.common.ble.BleDataSourceUp.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            Thread.sleep(100L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        CommonServiceUtils.getInstance().sendData(Constant.CmdUpStart);
                    }
                });
            }
            if (z && z2) {
                BleDataManager.getInstance().sendRecordPauseMsg(Constant.AudioStreamType.LEFT);
                BleDataManager.getInstance().sendRecordPauseMsg(Constant.AudioStreamType.NORMAL);
                BleDataManager.getInstance().sendRecordPauseMsg(Constant.AudioStreamType.SINGLE_MIX);
            }
        }
    }

    @Override // com.aivox.common.speech2text.BaseAudioDataSource
    public void stopRecord() {
        this.isRecording = false;
        BleDataManager.getInstance().save();
        this.mDownByteQueue.clear();
        System.gc();
    }

    @Override // com.aivox.common.speech2text.BaseAudioDataSource
    public void startRecord(boolean z) {
        this.isRecording = true;
    }

    @Override // com.aivox.common.speech2text.BaseAudioDataSource
    public byte[] getData() {
        try {
            return copyMutiByteArray(this.mDownByteQueue.take(), this.mDownByteQueue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    @Override // com.aivox.common.speech2text.BaseAudioDataSource
    public boolean isRecording() {
        return this.isRecording;
    }

    @Override // com.aivox.common.speech2text.BaseAudioDataSource
    public void setRecognizing(boolean z) {
        this.isRecognizing = z;
    }

    public void writeUpData(byte[] bArr) {
        if (this.isRecording && this.isRecognizing) {
            try {
                this.mDownByteQueue.put(bArr);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.tencent.aai.audio.data.PcmAudioDataSource
    public int read(short[] sArr, int i) {
        try {
            byte[] bArrCopyMutiByteArray = copyMutiByteArray(this.mDownByteQueue.take(), this.mDownByteQueue.take(), this.mDownByteQueue.take(), this.mDownByteQueue.take());
            this.curData = bArrCopyMutiByteArray;
            System.arraycopy(BaseStringUtil.toShortArrayLittle(bArrCopyMutiByteArray), 0, sArr, 0, this.curData.length / 2);
            return this.curData.length / 2;
        } catch (Exception unused) {
            return 0;
        }
    }

    @Override // com.aivox.common.speech2text.BaseAudioDataSource
    public boolean isEmpty() {
        return this.mDownByteQueue.isEmpty();
    }

    private byte[] copyMutiByteArray(byte[]... bArr) {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bArr.length * Constant.EVENT.BLE_SHOW_CONNECT_DIALOG);
        for (byte[] bArr2 : bArr) {
            byteBufferAllocate.put(bArr2);
        }
        return byteBufferAllocate.array();
    }
}
