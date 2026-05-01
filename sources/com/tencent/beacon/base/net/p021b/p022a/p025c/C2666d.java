package com.tencent.beacon.base.net.p021b.p022a.p025c;

import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.b.a.c.d */
/* JADX INFO: compiled from: RCEncoder.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2666d {

    /* JADX INFO: renamed from: a */
    private static int[] f1303a = new int[512];

    /* JADX INFO: renamed from: b */
    OutputStream f1304b;

    /* JADX INFO: renamed from: c */
    long f1305c;

    /* JADX INFO: renamed from: d */
    int f1306d;

    /* JADX INFO: renamed from: e */
    int f1307e;

    /* JADX INFO: renamed from: f */
    int f1308f;

    /* JADX INFO: renamed from: g */
    long f1309g;

    static {
        for (int i = 8; i >= 0; i--) {
            int i2 = 8 - i;
            int i3 = 1 << (9 - i);
            for (int i4 = 1 << i2; i4 < i3; i4++) {
                f1303a[i4] = (i << 6) + (((i3 - i4) << 6) >>> i2);
            }
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1310a(OutputStream outputStream) {
        this.f1304b = outputStream;
    }

    /* JADX INFO: renamed from: b */
    public void m1312b() throws IOException {
        this.f1304b.flush();
    }

    /* JADX INFO: renamed from: c */
    public long m1313c() {
        return ((long) this.f1307e) + this.f1309g + 4;
    }

    /* JADX INFO: renamed from: d */
    public void m1314d() {
        this.f1309g = 0L;
        this.f1305c = 0L;
        this.f1306d = -1;
        this.f1307e = 1;
        this.f1308f = 0;
    }

    /* JADX INFO: renamed from: e */
    public void m1315e() {
        this.f1304b = null;
    }

    /* JADX INFO: renamed from: f */
    public void m1316f() throws IOException {
        long j = this.f1305c;
        int i = (int) (j >>> 32);
        if (i != 0 || j < 4278190080L) {
            this.f1309g += (long) this.f1307e;
            int i2 = this.f1308f;
            while (true) {
                this.f1304b.write(i2 + i);
                int i3 = this.f1307e - 1;
                this.f1307e = i3;
                if (i3 == 0) {
                    break;
                } else {
                    i2 = 255;
                }
            }
            this.f1308f = ((int) this.f1305c) >>> 24;
        }
        this.f1307e++;
        this.f1305c = (this.f1305c & 16777215) << 8;
    }

    /* JADX INFO: renamed from: b */
    public static int m1307b(int i, int i2) {
        return f1303a[(((i - i2) ^ (-i2)) & 2047) >>> 2];
    }

    /* JADX INFO: renamed from: a */
    public void m1308a() throws IOException {
        for (int i = 0; i < 5; i++) {
            m1316f();
        }
    }

    /* JADX INFO: renamed from: b */
    public static int m1306b(int i) {
        return f1303a[(2048 - i) >>> 2];
    }

    /* JADX INFO: renamed from: a */
    public void m1309a(int i, int i2) throws IOException {
        for (int i3 = i2 - 1; i3 >= 0; i3--) {
            int i4 = this.f1306d >>> 1;
            this.f1306d = i4;
            if (((i >>> i3) & 1) == 1) {
                this.f1305c += (long) i4;
            }
            if (((-16777216) & i4) == 0) {
                this.f1306d = i4 << 8;
                m1316f();
            }
        }
    }

    /* JADX INFO: renamed from: a */
    public static void m1305a(short[] sArr) {
        for (int i = 0; i < sArr.length; i++) {
            sArr[i] = 1024;
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1311a(short[] sArr, int i, int i2) throws IOException {
        short s = sArr[i];
        int i3 = this.f1306d;
        int i4 = (i3 >>> 11) * s;
        if (i2 == 0) {
            this.f1306d = i4;
            sArr[i] = (short) (s + ((2048 - s) >>> 5));
        } else {
            this.f1305c += ((long) i4) & 4294967295L;
            this.f1306d = i3 - i4;
            sArr[i] = (short) (s - (s >>> 5));
        }
        int i5 = this.f1306d;
        if (((-16777216) & i5) == 0) {
            this.f1306d = i5 << 8;
            m1316f();
        }
    }

    /* JADX INFO: renamed from: a */
    public static int m1304a(int i) {
        return f1303a[i >>> 2];
    }
}
