package com.tencent.beacon.base.net.p021b.p022a.p025c;

import java.io.IOException;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.b.a.c.b */
/* JADX INFO: compiled from: BitTreeEncoder.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2664b {

    /* JADX INFO: renamed from: a */
    short[] f1298a;

    /* JADX INFO: renamed from: b */
    int f1299b;

    public C2664b(int i) {
        this.f1299b = i;
        this.f1298a = new short[1 << i];
    }

    /* JADX INFO: renamed from: a */
    public void m1294a() {
        C2665c.m1298a(this.f1298a);
    }

    /* JADX INFO: renamed from: b */
    public void m1297b(C2666d c2666d, int i) throws IOException {
        int i2 = 1;
        for (int i3 = 0; i3 < this.f1299b; i3++) {
            int i4 = i & 1;
            c2666d.m1311a(this.f1298a, i2, i4);
            i2 = (i2 << 1) | i4;
            i >>= 1;
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1295a(C2666d c2666d, int i) throws IOException {
        int i2 = this.f1299b;
        int i3 = 1;
        while (i2 != 0) {
            i2--;
            int i4 = (i >>> i2) & 1;
            c2666d.m1311a(this.f1298a, i3, i4);
            i3 = (i3 << 1) | i4;
        }
    }

    /* JADX INFO: renamed from: b */
    public int m1296b(int i) {
        int iM1307b = 0;
        int i2 = 1;
        for (int i3 = this.f1299b; i3 != 0; i3--) {
            int i4 = i & 1;
            i >>>= 1;
            iM1307b += C2666d.m1307b(this.f1298a[i2], i4);
            i2 = (i2 << 1) | i4;
        }
        return iM1307b;
    }

    /* JADX INFO: renamed from: a */
    public int m1293a(int i) {
        int i2 = this.f1299b;
        int iM1307b = 0;
        int i3 = 1;
        while (i2 != 0) {
            i2--;
            int i4 = (i >>> i2) & 1;
            iM1307b += C2666d.m1307b(this.f1298a[i3], i4);
            i3 = (i3 << 1) + i4;
        }
        return iM1307b;
    }

    /* JADX INFO: renamed from: a */
    public static int m1291a(short[] sArr, int i, int i2, int i3) {
        int iM1307b = 0;
        int i4 = 1;
        while (i2 != 0) {
            int i5 = i3 & 1;
            i3 >>>= 1;
            iM1307b += C2666d.m1307b(sArr[i + i4], i5);
            i4 = (i4 << 1) | i5;
            i2--;
        }
        return iM1307b;
    }

    /* JADX INFO: renamed from: a */
    public static void m1292a(short[] sArr, int i, C2666d c2666d, int i2, int i3) throws IOException {
        int i4 = 1;
        for (int i5 = 0; i5 < i2; i5++) {
            int i6 = i3 & 1;
            c2666d.m1311a(sArr, i + i4, i6);
            i4 = (i4 << 1) | i6;
            i3 >>= 1;
        }
    }
}
