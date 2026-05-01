package com.tencent.beacon.base.net.p021b.p022a.p024b;

import com.aivox.besota.bessdk.BesSdkConstants;
import com.alibaba.fastjson.asm.Opcodes;
import com.tencent.beacon.base.net.p021b.p022a.p023a.C2658c;
import com.tencent.beacon.base.net.p021b.p022a.p025c.C2663a;
import com.tencent.beacon.base.net.p021b.p022a.p025c.C2665c;
import java.io.IOException;
import kotlin.UByte;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.b.a.b.b */
/* JADX INFO: compiled from: Decoder.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2661b {

    /* JADX INFO: renamed from: a */
    C2658c f1183a = new C2658c();

    /* JADX INFO: renamed from: b */
    C2665c f1184b = new C2665c();

    /* JADX INFO: renamed from: c */
    short[] f1185c = new short[Opcodes.CHECKCAST];

    /* JADX INFO: renamed from: d */
    short[] f1186d = new short[12];

    /* JADX INFO: renamed from: e */
    short[] f1187e = new short[12];

    /* JADX INFO: renamed from: f */
    short[] f1188f = new short[12];

    /* JADX INFO: renamed from: g */
    short[] f1189g = new short[12];

    /* JADX INFO: renamed from: h */
    short[] f1190h = new short[Opcodes.CHECKCAST];

    /* JADX INFO: renamed from: i */
    C2663a[] f1191i = new C2663a[4];

    /* JADX INFO: renamed from: j */
    short[] f1192j = new short[114];

    /* JADX INFO: renamed from: k */
    C2663a f1193k = new C2663a(4);

    /* JADX INFO: renamed from: l */
    a f1194l = new a();

    /* JADX INFO: renamed from: m */
    a f1195m = new a();

    /* JADX INFO: renamed from: n */
    b f1196n = new b();

    /* JADX INFO: renamed from: o */
    int f1197o = -1;

    /* JADX INFO: renamed from: p */
    int f1198p = -1;

    /* JADX INFO: renamed from: q */
    int f1199q;

    public C2661b() {
        for (int i = 0; i < 4; i++) {
            this.f1191i[i] = new C2663a(6);
        }
    }

    /* JADX INFO: renamed from: a */
    boolean m1226a(int i) {
        if (i < 0) {
            return false;
        }
        if (this.f1197o != i) {
            this.f1197o = i;
            int iMax = Math.max(i, 1);
            this.f1198p = iMax;
            this.f1183a.m1212a(Math.max(iMax, 4096));
        }
        return true;
    }

    /* JADX INFO: renamed from: com.tencent.beacon.base.net.b.a.b.b$a */
    /* JADX INFO: compiled from: Decoder.java */
    class a {

        /* JADX INFO: renamed from: a */
        short[] f1200a = new short[2];

        /* JADX INFO: renamed from: b */
        C2663a[] f1201b = new C2663a[16];

        /* JADX INFO: renamed from: c */
        C2663a[] f1202c = new C2663a[16];

        /* JADX INFO: renamed from: d */
        C2663a f1203d = new C2663a(8);

        /* JADX INFO: renamed from: e */
        int f1204e = 0;

        a() {
        }

        /* JADX INFO: renamed from: a */
        public void m1232a(int i) {
            while (true) {
                int i2 = this.f1204e;
                if (i2 >= i) {
                    return;
                }
                this.f1201b[i2] = new C2663a(3);
                this.f1202c[this.f1204e] = new C2663a(3);
                this.f1204e++;
            }
        }

        /* JADX INFO: renamed from: a */
        public void m1231a() {
            C2665c.m1298a(this.f1200a);
            for (int i = 0; i < this.f1204e; i++) {
                this.f1201b[i].m1289a();
                this.f1202c[i].m1289a();
            }
            this.f1203d.m1289a();
        }

        /* JADX INFO: renamed from: a */
        public int m1230a(C2665c c2665c, int i) throws IOException {
            if (c2665c.m1300a(this.f1200a, 0) == 0) {
                return this.f1201b[i].m1288a(c2665c);
            }
            if (c2665c.m1300a(this.f1200a, 1) == 0) {
                return this.f1202c[i].m1288a(c2665c) + 8;
            }
            return this.f1203d.m1288a(c2665c) + 16;
        }
    }

    /* JADX INFO: renamed from: a */
    boolean m1227a(int i, int i2, int i3) {
        if (i > 8 || i2 > 4 || i3 > 4) {
            return false;
        }
        this.f1196n.m1235a(i2, i);
        int i4 = 1 << i3;
        this.f1194l.m1232a(i4);
        this.f1195m.m1232a(i4);
        this.f1199q = i4 - 1;
        return true;
    }

    /* JADX INFO: renamed from: com.tencent.beacon.base.net.b.a.b.b$b */
    /* JADX INFO: compiled from: Decoder.java */
    class b {

        /* JADX INFO: renamed from: a */
        a[] f1206a;

        /* JADX INFO: renamed from: b */
        int f1207b;

        /* JADX INFO: renamed from: c */
        int f1208c;

        /* JADX INFO: renamed from: d */
        int f1209d;

        /* JADX INFO: renamed from: com.tencent.beacon.base.net.b.a.b.b$b$a */
        /* JADX INFO: compiled from: Decoder.java */
        class a {

            /* JADX INFO: renamed from: a */
            short[] f1211a = new short[BesSdkConstants.BES_TOTA_START];

            a() {
            }

            /* JADX INFO: renamed from: a */
            public void m1238a() {
                C2665c.m1298a(this.f1211a);
            }

            /* JADX INFO: renamed from: a */
            public byte m1236a(C2665c c2665c) throws IOException {
                int iM1300a = 1;
                do {
                    iM1300a = c2665c.m1300a(this.f1211a, iM1300a) | (iM1300a << 1);
                } while (iM1300a < 256);
                return (byte) iM1300a;
            }

            /* JADX INFO: renamed from: a */
            public byte m1237a(C2665c c2665c, byte b) throws IOException {
                int iM1300a = 1;
                while (true) {
                    int i = (b >> 7) & 1;
                    b = (byte) (b << 1);
                    int iM1300a2 = c2665c.m1300a(this.f1211a, ((i + 1) << 8) + iM1300a);
                    iM1300a = (iM1300a << 1) | iM1300a2;
                    if (i != iM1300a2) {
                        while (iM1300a < 256) {
                            iM1300a = (iM1300a << 1) | c2665c.m1300a(this.f1211a, iM1300a);
                        }
                    } else if (iM1300a >= 256) {
                        break;
                    }
                }
                return (byte) iM1300a;
            }
        }

        b() {
        }

        /* JADX INFO: renamed from: a */
        public void m1235a(int i, int i2) {
            if (this.f1206a != null && this.f1207b == i2 && this.f1208c == i) {
                return;
            }
            this.f1208c = i;
            this.f1209d = (1 << i) - 1;
            this.f1207b = i2;
            int i3 = 1 << (i2 + i);
            this.f1206a = new a[i3];
            for (int i4 = 0; i4 < i3; i4++) {
                this.f1206a[i4] = new a();
            }
        }

        /* JADX INFO: renamed from: a */
        public void m1234a() {
            int i = 1 << (this.f1207b + this.f1208c);
            for (int i2 = 0; i2 < i; i2++) {
                this.f1206a[i2].m1238a();
            }
        }

        /* JADX INFO: renamed from: a */
        a m1233a(int i, byte b) {
            a[] aVarArr = this.f1206a;
            int i2 = i & this.f1209d;
            int i3 = this.f1207b;
            return aVarArr[(i2 << i3) + ((b & UByte.MAX_VALUE) >>> (8 - i3))];
        }
    }

    /* JADX INFO: renamed from: a */
    void m1225a() throws IOException {
        this.f1183a.m1215a(false);
        C2665c.m1298a(this.f1185c);
        C2665c.m1298a(this.f1190h);
        C2665c.m1298a(this.f1186d);
        C2665c.m1298a(this.f1187e);
        C2665c.m1298a(this.f1188f);
        C2665c.m1298a(this.f1189g);
        C2665c.m1298a(this.f1192j);
        this.f1196n.m1234a();
        for (int i = 0; i < 4; i++) {
            this.f1191i[i].m1289a();
        }
        this.f1194l.m1231a();
        this.f1195m.m1231a();
        this.f1193k.m1289a();
        this.f1184b.m1301a();
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x0117, code lost:
    
        r17.f1183a.m1210a();
        r17.f1183a.m1217b();
        r17.f1184b.m1303b();
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0126, code lost:
    
        return true;
     */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00b4  */
    /* JADX INFO: renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean m1228a(java.io.InputStream r18, java.io.OutputStream r19, long r20) throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 335
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.beacon.base.net.p021b.p022a.p024b.C2661b.m1228a(java.io.InputStream, java.io.OutputStream, long):boolean");
    }

    /* JADX INFO: renamed from: a */
    public boolean m1229a(byte[] bArr) {
        if (bArr.length < 5) {
            return false;
        }
        int i = bArr[0] & UByte.MAX_VALUE;
        int i2 = i % 9;
        int i3 = i / 9;
        int i4 = i3 % 5;
        int i5 = i3 / 5;
        int i6 = 0;
        int i7 = 0;
        while (i6 < 4) {
            int i8 = i6 + 1;
            i7 += (bArr[i8] & UByte.MAX_VALUE) << (i6 * 8);
            i6 = i8;
        }
        if (m1227a(i2, i4, i5)) {
            return m1226a(i7);
        }
        return false;
    }
}
