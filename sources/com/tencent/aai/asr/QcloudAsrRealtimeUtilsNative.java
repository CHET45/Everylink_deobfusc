package com.tencent.aai.asr;

/* JADX INFO: loaded from: classes4.dex */
public class QcloudAsrRealtimeUtilsNative {
    static {
        System.loadLibrary("qcloud_asr_realtime");
    }

    public static native int encode(long j, short[] sArr, byte[] bArr);

    public static native long init(int i, int i2);

    public static native void uninit(long j);
}
