package com.aivox.libOpus.utils;

import android.media.AudioRecord;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: OpusRecorderTask.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m1900d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\b\u0010\f\u001a\u00020\rH\u0016J\u0006\u0010\u000e\u001a\u00020\rR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, m1901d2 = {"Lcom/aivox/libOpus/utils/OpusRecorderTask;", "Ljava/lang/Runnable;", "opusAudioOpusPath", "", "opusAudioPcmPath", "(Ljava/lang/String;Ljava/lang/String;)V", "audioBuffer", "", "audioRecord", "Landroid/media/AudioRecord;", "isRecorder", "", "run", "", "stop", "Companion", "libOpus_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
public final class OpusRecorderTask implements Runnable {
    private byte[] audioBuffer;
    private AudioRecord audioRecord;
    private boolean isRecorder;
    private final String opusAudioOpusPath;
    private final String opusAudioPcmPath;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static int channelConfig = 16;
    private static final int bufferSize = AudioRecord.getMinBufferSize(16000, 16, 2);

    /* JADX INFO: compiled from: OpusRecorderTask.kt */
    @Metadata(m1900d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\b"}, m1901d2 = {"Lcom/aivox/libOpus/utils/OpusRecorderTask$Companion;", "", "()V", "bufferSize", "", "getBufferSize", "()I", "channelConfig", "libOpus_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final int getBufferSize() {
            return OpusRecorderTask.bufferSize;
        }
    }

    public OpusRecorderTask(String opusAudioOpusPath, String opusAudioPcmPath) {
        Intrinsics.checkNotNullParameter(opusAudioOpusPath, "opusAudioOpusPath");
        Intrinsics.checkNotNullParameter(opusAudioPcmPath, "opusAudioPcmPath");
        this.opusAudioOpusPath = opusAudioOpusPath;
        this.opusAudioPcmPath = opusAudioPcmPath;
        this.audioBuffer = new byte[640];
        this.audioRecord = new AudioRecord(1, 16000, channelConfig, 2, bufferSize);
    }

    public final void stop() {
        this.isRecorder = false;
    }

    @Override // java.lang.Runnable
    public void run() throws IOException {
        this.isRecorder = true;
        this.audioRecord.startRecording();
        File file = new File(this.opusAudioOpusPath);
        File file2 = new File(this.opusAudioPcmPath);
        File file3 = new File(file.getParent());
        if (!file3.exists()) {
            file3.mkdirs();
        }
        if (file.exists()) {
            file.delete();
        }
        if (file2.exists()) {
            file2.delete();
        }
        file.createNewFile();
        file2.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file, true);
        FileOutputStream fileOutputStream2 = new FileOutputStream(file2, true);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(fileOutputStream2);
        OpusUtils opusUtils = new OpusUtils();
        long jCreateEncoder = opusUtils.createEncoder(16000, 1, 3);
        while (this.isRecorder) {
            AudioRecord audioRecord = this.audioRecord;
            byte[] bArr = this.audioBuffer;
            int i = audioRecord.read(bArr, 0, bArr.length);
            if (i > 0) {
                byte[] bArr2 = this.audioBuffer;
                if (i <= bArr2.length) {
                    bufferedOutputStream2.write(bArr2);
                    byte[] bArr3 = new byte[this.audioBuffer.length / 8];
                    int iEncode = opusUtils.encode(jCreateEncoder, Uilts.INSTANCE.byteArrayToShortArray(this.audioBuffer), 0, bArr3);
                    if (iEncode > 0) {
                        byte[] bArr4 = new byte[iEncode];
                        System.arraycopy(bArr3, 0, bArr4, 0, iEncode);
                        bufferedOutputStream.write(bArr4);
                    }
                }
            }
        }
        opusUtils.destroyEncoder(jCreateEncoder);
        this.audioRecord.stop();
        this.audioRecord.release();
        bufferedOutputStream2.close();
        fileOutputStream2.close();
        bufferedOutputStream.close();
        fileOutputStream.close();
    }
}
