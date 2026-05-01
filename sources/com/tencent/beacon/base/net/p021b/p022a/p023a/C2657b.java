package com.tencent.beacon.base.net.p021b.p022a.p023a;

import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.b.a.a.b */
/* JADX INFO: compiled from: InWindow.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2657b {

    /* JADX INFO: renamed from: a */
    public byte[] f1155a;

    /* JADX INFO: renamed from: b */
    InputStream f1156b;

    /* JADX INFO: renamed from: c */
    int f1157c;

    /* JADX INFO: renamed from: d */
    boolean f1158d;

    /* JADX INFO: renamed from: e */
    int f1159e;

    /* JADX INFO: renamed from: f */
    public int f1160f;

    /* JADX INFO: renamed from: g */
    public int f1161g;

    /* JADX INFO: renamed from: h */
    public int f1162h;

    /* JADX INFO: renamed from: i */
    int f1163i;

    /* JADX INFO: renamed from: j */
    int f1164j;

    /* JADX INFO: renamed from: k */
    public int f1165k;

    /* JADX INFO: renamed from: a */
    void m1201a() {
        this.f1155a = null;
    }

    /* JADX INFO: renamed from: b */
    public int m1205b(int i, int i2, int i3) {
        if (this.f1158d) {
            int i4 = this.f1162h + i;
            int i5 = i4 + i3;
            int i6 = this.f1165k;
            if (i5 > i6) {
                i3 = i6 - i4;
            }
        }
        int i7 = i2 + 1;
        int i8 = this.f1160f + this.f1162h + i;
        int i9 = 0;
        while (i9 < i3) {
            byte[] bArr = this.f1155a;
            int i10 = i8 + i9;
            if (bArr[i10] != bArr[i10 - i7]) {
                break;
            }
            i9++;
        }
        return i9;
    }

    /* JADX INFO: renamed from: c */
    public void mo1195c() throws IOException {
        this.f1160f = 0;
        this.f1162h = 0;
        this.f1165k = 0;
        this.f1158d = false;
        m1208f();
    }

    /* JADX INFO: renamed from: d */
    public void m1207d() {
        int i = this.f1160f;
        int i2 = (this.f1162h + i) - this.f1163i;
        if (i2 > 0) {
            i2--;
        }
        int i3 = (i + this.f1165k) - i2;
        for (int i4 = 0; i4 < i3; i4++) {
            byte[] bArr = this.f1155a;
            bArr[i4] = bArr[i2 + i4];
        }
        this.f1160f -= i2;
    }

    /* JADX INFO: renamed from: e */
    public void mo1198e() throws IOException {
        int i = this.f1162h + 1;
        this.f1162h = i;
        if (i > this.f1157c) {
            if (this.f1160f + i > this.f1159e) {
                m1207d();
            }
            m1208f();
        }
    }

    /* JADX INFO: renamed from: f */
    public void m1208f() throws IOException {
        if (this.f1158d) {
            return;
        }
        while (true) {
            int i = this.f1160f;
            int i2 = (0 - i) + this.f1161g;
            int i3 = this.f1165k;
            int i4 = i2 - i3;
            if (i4 == 0) {
                return;
            }
            int i5 = this.f1156b.read(this.f1155a, i + i3, i4);
            if (i5 == -1) {
                int i6 = this.f1165k;
                this.f1157c = i6;
                int i7 = this.f1160f;
                int i8 = i6 + i7;
                int i9 = this.f1159e;
                if (i8 > i9) {
                    this.f1157c = i9 - i7;
                }
                this.f1158d = true;
                return;
            }
            int i10 = this.f1165k + i5;
            this.f1165k = i10;
            int i11 = this.f1162h;
            int i12 = this.f1164j;
            if (i10 >= i11 + i12) {
                this.f1157c = i10 - i12;
            }
        }
    }

    /* JADX INFO: renamed from: g */
    public void m1209g() {
        this.f1156b = null;
    }

    /* JADX INFO: renamed from: a */
    public void m1202a(int i, int i2, int i3) {
        this.f1163i = i;
        this.f1164j = i2;
        int i4 = i + i2 + i3;
        if (this.f1155a == null || this.f1161g != i4) {
            m1201a();
            this.f1161g = i4;
            this.f1155a = new byte[i4];
        }
        this.f1159e = this.f1161g - i2;
    }

    /* JADX INFO: renamed from: b */
    public int m1204b() {
        return this.f1165k - this.f1162h;
    }

    /* JADX INFO: renamed from: b */
    public void m1206b(int i) {
        this.f1160f += i;
        this.f1157c -= i;
        this.f1162h -= i;
        this.f1165k -= i;
    }

    /* JADX INFO: renamed from: a */
    public void m1203a(InputStream inputStream) {
        this.f1156b = inputStream;
    }

    /* JADX INFO: renamed from: a */
    public byte m1200a(int i) {
        return this.f1155a[this.f1160f + this.f1162h + i];
    }
}
