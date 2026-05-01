package com.tencent.beacon.base.net.p021b.p022a.p023a;

import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.b.a.a.c */
/* JADX INFO: compiled from: OutWindow.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2658c {

    /* JADX INFO: renamed from: a */
    byte[] f1166a;

    /* JADX INFO: renamed from: b */
    int f1167b;

    /* JADX INFO: renamed from: c */
    int f1168c = 0;

    /* JADX INFO: renamed from: d */
    int f1169d;

    /* JADX INFO: renamed from: e */
    OutputStream f1170e;

    /* JADX INFO: renamed from: a */
    public void m1212a(int i) {
        if (this.f1166a == null || this.f1168c != i) {
            this.f1166a = new byte[i];
        }
        this.f1168c = i;
        this.f1167b = 0;
        this.f1169d = 0;
    }

    /* JADX INFO: renamed from: b */
    public void m1217b() throws IOException {
        m1210a();
        this.f1170e = null;
    }

    /* JADX INFO: renamed from: b */
    public byte m1216b(int i) {
        int i2 = (this.f1167b - i) - 1;
        if (i2 < 0) {
            i2 += this.f1168c;
        }
        return this.f1166a[i2];
    }

    /* JADX INFO: renamed from: a */
    public void m1214a(OutputStream outputStream) throws IOException {
        m1217b();
        this.f1170e = outputStream;
    }

    /* JADX INFO: renamed from: a */
    public void m1215a(boolean z) {
        if (z) {
            return;
        }
        this.f1169d = 0;
        this.f1167b = 0;
    }

    /* JADX INFO: renamed from: a */
    public void m1210a() throws IOException {
        int i = this.f1167b;
        int i2 = this.f1169d;
        int i3 = i - i2;
        if (i3 == 0) {
            return;
        }
        this.f1170e.write(this.f1166a, i2, i3);
        if (this.f1167b >= this.f1168c) {
            this.f1167b = 0;
        }
        this.f1169d = this.f1167b;
    }

    /* JADX INFO: renamed from: a */
    public void m1213a(int i, int i2) throws IOException {
        int i3 = (this.f1167b - i) - 1;
        if (i3 < 0) {
            i3 += this.f1168c;
        }
        while (i2 != 0) {
            int i4 = this.f1168c;
            if (i3 >= i4) {
                i3 = 0;
            }
            byte[] bArr = this.f1166a;
            int i5 = this.f1167b;
            int i6 = i5 + 1;
            this.f1167b = i6;
            int i7 = i3 + 1;
            bArr[i5] = bArr[i3];
            if (i6 >= i4) {
                m1210a();
            }
            i2--;
            i3 = i7;
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1211a(byte b) throws IOException {
        byte[] bArr = this.f1166a;
        int i = this.f1167b;
        int i2 = i + 1;
        this.f1167b = i2;
        bArr[i] = b;
        if (i2 >= this.f1168c) {
            m1210a();
        }
    }
}
