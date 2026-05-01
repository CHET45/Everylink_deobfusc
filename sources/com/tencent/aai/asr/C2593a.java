package com.tencent.aai.asr;

/* JADX INFO: renamed from: com.tencent.aai.asr.a */
/* JADX INFO: loaded from: classes4.dex */
public class C2593a {

    /* JADX INFO: renamed from: a */
    public static volatile C2593a f925a;

    /* JADX INFO: renamed from: b */
    public static long f926b;

    /* JADX INFO: renamed from: a */
    public static C2593a m881a() {
        if (f925a == null) {
            synchronized (C2593a.class) {
                if (f925a == null) {
                    f925a = new C2593a();
                    long j = f926b;
                    if (j != 0) {
                        QcloudAsrRealtimeUtilsNative.uninit(j);
                    }
                    f926b = QcloudAsrRealtimeUtilsNative.init(16000, 1);
                }
            }
        }
        return f925a;
    }

    /* JADX INFO: renamed from: b */
    public static void m882b() {
        synchronized (C2593a.class) {
            if (f925a != null) {
                f925a = null;
                long j = f926b;
                if (j != 0) {
                    QcloudAsrRealtimeUtilsNative.uninit(j);
                    f926b = 0L;
                }
            }
        }
    }

    /* JADX INFO: renamed from: a */
    public int m883a(short[] sArr, byte[] bArr) {
        synchronized (C2593a.class) {
            long j = f926b;
            if (j == 0) {
                return -1;
            }
            return QcloudAsrRealtimeUtilsNative.encode(j, sArr, bArr);
        }
    }
}
