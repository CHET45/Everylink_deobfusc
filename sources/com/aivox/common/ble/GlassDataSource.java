package com.aivox.common.ble;

import com.aivox.base.common.Constant;
import com.aivox.base.common.GlassesCmd;
import com.aivox.base.util.ThreadPoolManager;
import com.aivox.common.ble.service.BleBtService;
import com.aivox.common.ble.service.CommonServiceUtils;
import com.aivox.common.speech2text.BaseAudioDataSource;
import kotlin.UByte;

/* JADX INFO: loaded from: classes.dex */
public class GlassDataSource extends BaseAudioDataSource {
    private static final int MAX_BUFFER_SIZE = 160000;
    private static GlassDataSource glass;
    private volatile boolean isRunning;
    private final String TAG = getClass().getSimpleName();
    private volatile boolean isRecording = false;
    private volatile boolean isRecognizing = false;
    private int audioType = 2;
    private final byte[] buffer = new byte[MAX_BUFFER_SIZE];
    private int head = 0;
    private int tail = 0;
    private int count = 0;
    private final Object lock = new Object();

    @Override // com.tencent.aai.audio.data.PcmAudioDataSource
    public boolean isSetSaveAudioRecordFiles() {
        return false;
    }

    public GlassDataSource() {
        this.isRunning = false;
        this.isRunning = false;
    }

    public static GlassDataSource getInstance() {
        if (glass == null) {
            synchronized (GlassDataSource.class) {
                if (glass == null) {
                    glass = new GlassDataSource();
                }
            }
        }
        return glass;
    }

    public void setAudioType(int i) {
        this.audioType = i;
    }

    public void addData(byte[] bArr) {
        if (this.isRecording && this.isRecognizing && this.isRunning && bArr != null && bArr.length != 0) {
            synchronized (this.lock) {
                int length = bArr.length;
                int i = this.count;
                int i2 = MAX_BUFFER_SIZE - i;
                if (length > i2) {
                    int i3 = length - i2;
                    if (i3 % 2 != 0) {
                        i3++;
                    }
                    this.head = (this.head + i3) % MAX_BUFFER_SIZE;
                    int i4 = i - i3;
                    this.count = i4;
                    if (i4 < 0) {
                        this.count = 0;
                    }
                }
                int i5 = this.tail;
                if (i5 + length <= MAX_BUFFER_SIZE) {
                    System.arraycopy(bArr, 0, this.buffer, i5, length);
                } else {
                    int i6 = MAX_BUFFER_SIZE - i5;
                    System.arraycopy(bArr, 0, this.buffer, i5, i6);
                    System.arraycopy(bArr, i6, this.buffer, 0, length - i6);
                }
                this.tail = (this.tail + length) % MAX_BUFFER_SIZE;
                this.count += length;
            }
        }
    }

    @Override // com.tencent.aai.audio.data.PcmAudioDataSource
    public int read(short[] sArr, int i) {
        if (sArr == null || i <= 0) {
            return 0;
        }
        synchronized (this.lock) {
            int i2 = this.count;
            if (i2 < 2) {
                return 0;
            }
            int iMin = Math.min(i2 / 2, i);
            int i3 = iMin * 2;
            for (int i4 = 0; i4 < iMin; i4++) {
                byte[] bArr = this.buffer;
                int i5 = this.head;
                byte b = bArr[i5];
                int i6 = (i5 + 1) % MAX_BUFFER_SIZE;
                this.head = i6;
                byte b2 = bArr[i6];
                this.head = (i6 + 1) % MAX_BUFFER_SIZE;
                sArr[i4] = (short) ((b2 << 8) | (b & UByte.MAX_VALUE));
            }
            this.count -= i3;
            return iMin;
        }
    }

    @Override // com.tencent.aai.audio.data.PcmAudioDataSource
    public void start() {
        this.isRunning = true;
        clearBuffer();
    }

    @Override // com.tencent.aai.audio.data.PcmAudioDataSource
    public void stop() {
        this.isRunning = false;
    }

    public int availableBytes() {
        int i;
        synchronized (this.lock) {
            i = this.count;
        }
        return i;
    }

    public int availableShorts() {
        int i;
        synchronized (this.lock) {
            i = this.count / 2;
        }
        return i;
    }

    public void clearBuffer() {
        synchronized (this.lock) {
            this.head = 0;
            this.tail = 0;
            this.count = 0;
        }
    }

    @Override // com.aivox.common.speech2text.BaseAudioDataSource
    public void changePause(final boolean z, final boolean z2) {
        if (!z || this.isRecording) {
            ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.aivox.common.ble.GlassDataSource$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2443lambda$changePause$0$comaivoxcommonbleGlassDataSource(z, z2);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$changePause$0$com-aivox-common-ble-GlassDataSource, reason: not valid java name */
    /* synthetic */ void m2443lambda$changePause$0$comaivoxcommonbleGlassDataSource(boolean z, boolean z2) {
        try {
            Thread.sleep(200L);
            this.isRecording = !z;
            if (!z) {
                if (BleBtService.getInstance().isGlass()) {
                    BleBtService.getInstance().sendGlassCmd(GlassesCmd.BLE_AUDIO_CONTROL, Integer.valueOf(this.audioType));
                    return;
                } else {
                    CommonServiceUtils.getInstance().sendData(Constant.CmdUpStart);
                    return;
                }
            }
            if (BleBtService.getInstance().isGlass()) {
                BleBtService.getInstance().sendGlassCmd(GlassesCmd.BLE_AUDIO_CONTROL, 0);
            } else {
                CommonServiceUtils.getInstance().sendData(Constant.CmdUpEnd);
            }
            if (z2) {
                BleDataManager.getInstance().sendRecordPauseMsg(Constant.AudioStreamType.RIGHT);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override // com.aivox.common.speech2text.BaseAudioDataSource
    public void stopRecord() {
        this.isRecording = false;
        clearBuffer();
        BleDataManager.getInstance().save(true);
    }

    @Override // com.aivox.common.speech2text.BaseAudioDataSource
    public void startRecord(boolean z) {
        this.isRecording = true;
    }

    @Override // com.aivox.common.speech2text.BaseAudioDataSource
    public byte[] getData() {
        return new byte[0];
    }

    @Override // com.aivox.common.speech2text.BaseAudioDataSource
    public boolean isRecording() {
        return this.isRecording;
    }

    @Override // com.aivox.common.speech2text.BaseAudioDataSource
    public void setRecognizing(boolean z) {
        this.isRecognizing = z;
    }

    @Override // com.aivox.common.speech2text.BaseAudioDataSource
    public boolean isEmpty() {
        boolean z;
        synchronized (this.lock) {
            z = this.count == 0;
        }
        return z;
    }
}
