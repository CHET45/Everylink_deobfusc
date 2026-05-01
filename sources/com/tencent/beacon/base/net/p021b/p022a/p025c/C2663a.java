package com.tencent.beacon.base.net.p021b.p022a.p025c;

import java.io.IOException;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.b.a.c.a */
/* JADX INFO: compiled from: BitTreeDecoder.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2663a {

    /* JADX INFO: renamed from: a */
    short[] f1296a;

    /* JADX INFO: renamed from: b */
    int f1297b;

    public C2663a(int i) {
        this.f1297b = i;
        this.f1296a = new short[1 << i];
    }

    /* JADX INFO: renamed from: a */
    public void m1289a() {
        C2665c.m1298a(this.f1296a);
    }

    /* JADX INFO: renamed from: b */
    public int m1290b(C2665c c2665c) throws IOException {
        int i = 1;
        int i2 = 0;
        for (int i3 = 0; i3 < this.f1297b; i3++) {
            int iM1300a = c2665c.m1300a(this.f1296a, i);
            i = (i << 1) + iM1300a;
            i2 |= iM1300a << i3;
        }
        return i2;
    }

    /* JADX INFO: renamed from: a */
    public int m1288a(C2665c c2665c) throws IOException {
        int iM1300a = 1;
        for (int i = this.f1297b; i != 0; i--) {
            iM1300a = c2665c.m1300a(this.f1296a, iM1300a) + (iM1300a << 1);
        }
        return iM1300a - (1 << this.f1297b);
    }

    /* JADX INFO: renamed from: a */
    public static int m1287a(short[] sArr, int i, C2665c c2665c, int i2) throws IOException {
        int i3 = 1;
        int i4 = 0;
        for (int i5 = 0; i5 < i2; i5++) {
            int iM1300a = c2665c.m1300a(sArr, i + i3);
            i3 = (i3 << 1) + iM1300a;
            i4 |= iM1300a << i5;
        }
        return i4;
    }
}
