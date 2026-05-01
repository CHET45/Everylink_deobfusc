package com.aivox.common.ble;

import com.aivox.base.common.Constant;
import com.aivox.base.common.GlassesCmd;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.ThreadPoolManager;
import com.aivox.common.ble.service.BleBtService;
import com.aivox.common.ble.service.CommonServiceUtils;
import com.aivox.common.speech2text.BaseAudioDataSource;
import com.tencent.aai.exception.ClientException;
import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/* JADX INFO: loaded from: classes.dex */
public class BleDataSourceDown extends BaseAudioDataSource {
    private static final String TAG = "BleDataSourceDown";
    private static BleDataSourceDown down;
    private byte[] curData;
    private boolean isRecognizing;
    private boolean isRecording = false;
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

    public BleDataSourceDown() {
        if (this.mDownByteQueue == null) {
            this.mDownByteQueue = new LinkedBlockingDeque();
        }
    }

    public static BleDataSourceDown getInstance() {
        if (down == null) {
            down = new BleDataSourceDown();
        }
        return down;
    }

    @Override // com.aivox.common.speech2text.BaseAudioDataSource
    public boolean isEmpty() {
        return this.mDownByteQueue.isEmpty();
    }

    @Override // com.aivox.common.speech2text.BaseAudioDataSource
    public void changePause(boolean z, boolean z2) {
        if (!z || this.isRecording) {
            if (z) {
                if (BleBtService.getInstance().isGlass()) {
                    BleBtService.getInstance().sendGlassCmd(GlassesCmd.BLE_AUDIO_CONTROL, 0);
                } else {
                    CommonServiceUtils.getInstance().sendData(Constant.CmdUpEnd);
                }
            } else {
                ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.aivox.common.ble.BleDataSourceDown.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            Thread.sleep(100L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (BleBtService.getInstance().isGlass()) {
                            BleBtService.getInstance().sendGlassCmd(GlassesCmd.BLE_AUDIO_CONTROL, 1);
                        } else {
                            CommonServiceUtils.getInstance().sendData(Constant.CmdUpStart);
                        }
                    }
                });
            }
            if (z && z2) {
                BleDataManager.getInstance().sendRecordPauseMsg(Constant.AudioStreamType.RIGHT);
            }
            this.isRecording = !z;
        }
    }

    @Override // com.aivox.common.speech2text.BaseAudioDataSource
    public void stopRecord() {
        this.isRecording = false;
        this.mDownByteQueue.clear();
        BleDataManager.getInstance().save();
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

    public void writeDownData(byte[] bArr) {
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

    private byte[] copyMutiByteArray(byte[]... bArr) {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bArr.length * Constant.EVENT.BLE_SHOW_CONNECT_DIALOG);
        for (byte[] bArr2 : bArr) {
            byteBufferAllocate.put(bArr2);
        }
        return byteBufferAllocate.array();
    }
}
