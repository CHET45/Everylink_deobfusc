package com.tencent.beacon.base.net.p021b.p022a.p025c;

import androidx.core.view.ViewCompat;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.b.a.c.c */
/* JADX INFO: compiled from: RCDecoder.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2665c {

    /* JADX INFO: renamed from: a */
    int f1300a;

    /* JADX INFO: renamed from: b */
    int f1301b;

    /* JADX INFO: renamed from: c */
    InputStream f1302c;

    /* JADX INFO: renamed from: a */
    public final void m1302a(InputStream inputStream) {
        this.f1302c = inputStream;
    }

    /* JADX INFO: renamed from: b */
    public final void m1303b() {
        this.f1302c = null;
    }

    /* JADX INFO: renamed from: a */
    public final void m1301a() throws IOException {
        this.f1301b = 0;
        this.f1300a = -1;
        for (int i = 0; i < 5; i++) {
            this.f1301b = (this.f1301b << 8) | this.f1302c.read();
        }
    }

    /* JADX INFO: renamed from: a */
    public final int m1299a(int i) throws IOException {
        int i2 = 0;
        while (i != 0) {
            int i3 = this.f1300a >>> 1;
            this.f1300a = i3;
            int i4 = this.f1301b;
            int i5 = (i4 - i3) >>> 31;
            int i6 = i4 - ((i5 - 1) & i3);
            this.f1301b = i6;
            i2 = (i2 << 1) | (1 - i5);
            if ((i3 & ViewCompat.MEASURED_STATE_MASK) == 0) {
                this.f1301b = (i6 << 8) | this.f1302c.read();
                this.f1300a <<= 8;
            }
            i--;
        }
        return i2;
    }

    /* JADX INFO: renamed from: a */
    public int m1300a(short[] sArr, int i) throws IOException {
        short s = sArr[i];
        int i2 = this.f1300a;
        int i3 = (i2 >>> 11) * s;
        int i4 = this.f1301b;
        if ((i4 ^ Integer.MIN_VALUE) < (Integer.MIN_VALUE ^ i3)) {
            this.f1300a = i3;
            sArr[i] = (short) (s + ((2048 - s) >>> 5));
            if ((i3 & ViewCompat.MEASURED_STATE_MASK) != 0) {
                return 0;
            }
            this.f1301b = (i4 << 8) | this.f1302c.read();
            this.f1300a <<= 8;
            return 0;
        }
        int i5 = i2 - i3;
        this.f1300a = i5;
        int i6 = i4 - i3;
        this.f1301b = i6;
        sArr[i] = (short) (s - (s >>> 5));
        if ((i5 & ViewCompat.MEASURED_STATE_MASK) != 0) {
            return 1;
        }
        this.f1301b = (i6 << 8) | this.f1302c.read();
        this.f1300a <<= 8;
        return 1;
    }

    /* JADX INFO: renamed from: a */
    public static void m1298a(short[] sArr) {
        for (int i = 0; i < sArr.length; i++) {
            sArr[i] = 1024;
        }
    }
}
