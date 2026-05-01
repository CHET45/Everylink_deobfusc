package com.tencent.aai.task;

import com.tencent.aai.asr.C2593a;
import com.tencent.aai.log.AAILogger;

/* JADX INFO: renamed from: com.tencent.aai.task.a */
/* JADX INFO: loaded from: classes4.dex */
public class C2599a {

    /* JADX INFO: renamed from: a */
    public int f932a;

    /* JADX INFO: renamed from: b */
    public AudioPcmData f933b;

    public C2599a(int i, AudioPcmData audioPcmData) {
        this.f932a = i;
        this.f933b = audioPcmData;
    }

    /* JADX INFO: renamed from: a */
    public byte[] m884a() {
        AAILogger.info("AudioRecognizeTask", "pcm audio data length = " + this.f933b.getBuffer().length);
        AudioPcmData audioPcmData = this.f933b;
        int i = 0;
        if (audioPcmData == null || audioPcmData.getBuffer() == null) {
            return new byte[0];
        }
        short[] buffer = this.f933b.getBuffer();
        int length = buffer.length * 2;
        byte[] bArr = new byte[length];
        int i2 = 0;
        while (i < buffer.length) {
            short s = buffer[i];
            bArr[i2] = (byte) (s & 255);
            bArr[i2 + 1] = (byte) ((s >> 8) & 255);
            i++;
            i2 += 2;
        }
        AAILogger.info("AudioRecognizeTask", "pcm audio data length = " + length);
        return bArr;
    }

    /* JADX INFO: renamed from: b */
    public byte[] m885b() {
        AAILogger.info("AudioRecognizeTask", "pcm audio data length = " + this.f933b.getBuffer().length);
        AudioPcmData audioPcmData = this.f933b;
        if (audioPcmData == null || audioPcmData.getBuffer() == null) {
            return new byte[0];
        }
        short[] buffer = this.f933b.getBuffer();
        byte[] bArr = new byte[buffer.length];
        int iM883a = C2593a.m881a().m883a(buffer, bArr);
        byte[] bArr2 = new byte[iM883a];
        System.arraycopy(bArr, 0, bArr2, 0, iM883a);
        AAILogger.info("AudioRecognizeTask", "op audio data length = " + iM883a);
        return bArr2;
    }
}
