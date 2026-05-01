package com.tencent.beacon.base.net.p021b.p022a.p024b;

import android.support.v4.media.session.PlaybackStateCompat;
import com.aivox.besota.bessdk.BesSdkConstants;
import com.alibaba.fastjson.asm.Opcodes;
import com.azure.xml.implementation.aalto.util.XmlConsts;
import com.tencent.beacon.base.net.p021b.p022a.InterfaceC2655a;
import com.tencent.beacon.base.net.p021b.p022a.p023a.C2656a;
import com.tencent.beacon.base.net.p021b.p022a.p025c.C2664b;
import com.tencent.beacon.base.net.p021b.p022a.p025c.C2666d;
import com.tencent.beacon.pack.AbstractJceStruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import kotlin.UByte;
import org.videolan.libvlc.MediaPlayer;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.b.a.b.c */
/* JADX INFO: compiled from: Encoder.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2662c {

    /* JADX INFO: renamed from: a */
    static byte[] f1213a;

    /* JADX INFO: renamed from: A */
    boolean f1214A;

    /* JADX INFO: renamed from: E */
    int f1218E;

    /* JADX INFO: renamed from: N */
    long f1227N;

    /* JADX INFO: renamed from: O */
    boolean f1228O;

    /* JADX INFO: renamed from: P */
    InputStream f1229P;

    /* JADX INFO: renamed from: V */
    int f1235V;

    /* JADX INFO: renamed from: ba */
    int f1242ba;

    /* JADX INFO: renamed from: c */
    byte f1243c;

    /* JADX INFO: renamed from: v */
    int f1262v;

    /* JADX INFO: renamed from: w */
    int f1263w;

    /* JADX INFO: renamed from: x */
    int f1264x;

    /* JADX INFO: renamed from: y */
    int f1265y;

    /* JADX INFO: renamed from: z */
    int f1266z;

    /* JADX INFO: renamed from: b */
    int f1241b = C2660a.m1218a();

    /* JADX INFO: renamed from: d */
    int[] f1244d = new int[4];

    /* JADX INFO: renamed from: e */
    d[] f1245e = new d[4096];

    /* JADX INFO: renamed from: f */
    C2656a f1246f = null;

    /* JADX INFO: renamed from: g */
    C2666d f1247g = new C2666d();

    /* JADX INFO: renamed from: h */
    short[] f1248h = new short[Opcodes.CHECKCAST];

    /* JADX INFO: renamed from: i */
    short[] f1249i = new short[12];

    /* JADX INFO: renamed from: j */
    short[] f1250j = new short[12];

    /* JADX INFO: renamed from: k */
    short[] f1251k = new short[12];

    /* JADX INFO: renamed from: l */
    short[] f1252l = new short[12];

    /* JADX INFO: renamed from: m */
    short[] f1253m = new short[Opcodes.CHECKCAST];

    /* JADX INFO: renamed from: n */
    C2664b[] f1254n = new C2664b[4];

    /* JADX INFO: renamed from: o */
    short[] f1255o = new short[114];

    /* JADX INFO: renamed from: p */
    C2664b f1256p = new C2664b(4);

    /* JADX INFO: renamed from: q */
    b f1257q = new b();

    /* JADX INFO: renamed from: r */
    b f1258r = new b();

    /* JADX INFO: renamed from: s */
    c f1259s = new c();

    /* JADX INFO: renamed from: t */
    int[] f1260t = new int[548];

    /* JADX INFO: renamed from: u */
    int f1261u = 32;

    /* JADX INFO: renamed from: B */
    int[] f1215B = new int[256];

    /* JADX INFO: renamed from: C */
    int[] f1216C = new int[512];

    /* JADX INFO: renamed from: D */
    int[] f1217D = new int[16];

    /* JADX INFO: renamed from: F */
    int f1219F = 44;

    /* JADX INFO: renamed from: G */
    int f1220G = 2;

    /* JADX INFO: renamed from: H */
    int f1221H = 3;

    /* JADX INFO: renamed from: I */
    int f1222I = 0;

    /* JADX INFO: renamed from: J */
    int f1223J = 3;

    /* JADX INFO: renamed from: K */
    int f1224K = 4194304;

    /* JADX INFO: renamed from: L */
    int f1225L = -1;

    /* JADX INFO: renamed from: M */
    int f1226M = -1;

    /* JADX INFO: renamed from: Q */
    int f1230Q = 1;

    /* JADX INFO: renamed from: R */
    boolean f1231R = false;

    /* JADX INFO: renamed from: S */
    boolean f1232S = false;

    /* JADX INFO: renamed from: T */
    int[] f1233T = new int[4];

    /* JADX INFO: renamed from: U */
    int[] f1234U = new int[4];

    /* JADX INFO: renamed from: W */
    long[] f1236W = new long[1];

    /* JADX INFO: renamed from: X */
    long[] f1237X = new long[1];

    /* JADX INFO: renamed from: Y */
    boolean[] f1238Y = new boolean[1];

    /* JADX INFO: renamed from: Z */
    byte[] f1239Z = new byte[5];

    /* JADX INFO: renamed from: aa */
    int[] f1240aa = new int[128];

    /* JADX INFO: renamed from: com.tencent.beacon.base.net.b.a.b.c$b */
    /* JADX INFO: compiled from: Encoder.java */
    class b extends a {

        /* JADX INFO: renamed from: f */
        int[] f1272f;

        /* JADX INFO: renamed from: g */
        int f1273g;

        /* JADX INFO: renamed from: h */
        int[] f1274h;

        b() {
            super();
            this.f1272f = new int[4352];
            this.f1274h = new int[16];
        }

        /* JADX INFO: renamed from: a */
        public int m1273a(int i, int i2) {
            return this.f1272f[(i2 * XmlConsts.XML_V_11) + i];
        }

        /* JADX INFO: renamed from: b */
        public void m1274b(int i) {
            this.f1273g = i;
        }

        /* JADX INFO: renamed from: c */
        void m1275c(int i) {
            m1271a(i, this.f1273g, this.f1272f, i * XmlConsts.XML_V_11);
            this.f1274h[i] = this.f1273g;
        }

        /* JADX INFO: renamed from: d */
        public void m1276d(int i) {
            for (int i2 = 0; i2 < i; i2++) {
                m1275c(i2);
            }
        }

        @Override // com.tencent.beacon.base.net.p021b.p022a.p024b.C2662c.a
        /* JADX INFO: renamed from: a */
        public void mo1272a(C2666d c2666d, int i, int i2) throws IOException {
            super.mo1272a(c2666d, i, i2);
            int[] iArr = this.f1274h;
            int i3 = iArr[i2] - 1;
            iArr[i2] = i3;
            if (i3 == 0) {
                m1275c(i2);
            }
        }
    }

    /* JADX INFO: renamed from: com.tencent.beacon.base.net.b.a.b.c$d */
    /* JADX INFO: compiled from: Encoder.java */
    class d {

        /* JADX INFO: renamed from: a */
        public int f1283a;

        /* JADX INFO: renamed from: b */
        public boolean f1284b;

        /* JADX INFO: renamed from: c */
        public boolean f1285c;

        /* JADX INFO: renamed from: d */
        public int f1286d;

        /* JADX INFO: renamed from: e */
        public int f1287e;

        /* JADX INFO: renamed from: f */
        public int f1288f;

        /* JADX INFO: renamed from: g */
        public int f1289g;

        /* JADX INFO: renamed from: h */
        public int f1290h;

        /* JADX INFO: renamed from: i */
        public int f1291i;

        /* JADX INFO: renamed from: j */
        public int f1292j;

        /* JADX INFO: renamed from: k */
        public int f1293k;

        /* JADX INFO: renamed from: l */
        public int f1294l;

        d() {
        }

        /* JADX INFO: renamed from: a */
        public boolean m1284a() {
            return this.f1290h == 0;
        }

        /* JADX INFO: renamed from: b */
        public void m1285b() {
            this.f1290h = -1;
            this.f1284b = false;
        }

        /* JADX INFO: renamed from: c */
        public void m1286c() {
            this.f1290h = 0;
            this.f1284b = false;
        }
    }

    static {
        byte[] bArr = new byte[2048];
        f1213a = bArr;
        bArr[0] = 0;
        bArr[1] = 1;
        int i = 2;
        for (int i2 = 2; i2 < 22; i2++) {
            int i3 = 1 << ((i2 >> 1) - 1);
            int i4 = 0;
            while (i4 < i3) {
                f1213a[i] = (byte) i2;
                i4++;
                i++;
            }
        }
    }

    public C2662c() {
        for (int i = 0; i < 4096; i++) {
            this.f1245e[i] = new d();
        }
        for (int i2 = 0; i2 < 4; i2++) {
            this.f1254n[i2] = new C2664b(6);
        }
    }

    /* JADX INFO: renamed from: d */
    static int m1239d(int i) {
        return i < 2048 ? f1213a[i] : i < 2097152 ? f1213a[i >> 10] + 20 : f1213a[i >> 20] + 40;
    }

    /* JADX INFO: renamed from: e */
    static int m1240e(int i) {
        return i < 131072 ? f1213a[i >> 6] + AbstractJceStruct.ZERO_TAG : i < 134217728 ? f1213a[i >> 16] + 32 : f1213a[i >> 26] + 52;
    }

    /* JADX INFO: renamed from: a */
    void m1245a() {
        this.f1241b = C2660a.m1218a();
        this.f1243c = (byte) 0;
        for (int i = 0; i < 4; i++) {
            this.f1244d[i] = 0;
        }
    }

    /* JADX INFO: renamed from: b */
    void m1252b() {
        if (this.f1246f == null) {
            C2656a c2656a = new C2656a();
            c2656a.m1196c(this.f1230Q == 0 ? 2 : 4);
            this.f1246f = c2656a;
        }
        this.f1259s.m1279a(this.f1222I, this.f1223J);
        int i = this.f1224K;
        if (i == this.f1225L && this.f1226M == this.f1261u) {
            return;
        }
        this.f1246f.m1194a(i, 4096, this.f1261u, MediaPlayer.Event.Vout);
        this.f1225L = this.f1224K;
        this.f1226M = this.f1261u;
    }

    /* JADX WARN: Removed duplicated region for block: B:161:0x042e  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0531  */
    /* JADX WARN: Removed duplicated region for block: B:191:0x0552  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x0562  */
    /* JADX WARN: Removed duplicated region for block: B:198:0x0567  */
    /* JADX WARN: Removed duplicated region for block: B:223:0x0666  */
    /* JADX WARN: Removed duplicated region for block: B:230:0x069d  */
    /* JADX INFO: renamed from: c */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    int m1255c(int r26) throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 1705
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.beacon.base.net.p021b.p022a.p024b.C2662c.m1255c(int):int");
    }

    /* JADX INFO: renamed from: f */
    int m1260f() throws IOException {
        int iM1205b;
        int iM1192a = this.f1246f.m1192a(this.f1260t);
        this.f1263w = iM1192a;
        if (iM1192a > 0) {
            int[] iArr = this.f1260t;
            iM1205b = iArr[iM1192a - 2];
            if (iM1205b == this.f1261u) {
                iM1205b += this.f1246f.m1205b(iM1205b - 1, iArr[iM1192a - 1], 273 - iM1205b);
            }
        } else {
            iM1205b = 0;
        }
        this.f1264x++;
        return iM1205b;
    }

    /* JADX INFO: renamed from: g */
    void m1262g() {
        C2656a c2656a = this.f1246f;
        if (c2656a == null || !this.f1232S) {
            return;
        }
        c2656a.m1209g();
        this.f1232S = false;
    }

    /* JADX INFO: renamed from: g */
    public boolean m1263g(int i) {
        return true;
    }

    /* JADX INFO: renamed from: h */
    void m1264h() {
        this.f1247g.m1315e();
    }

    /* JADX INFO: renamed from: i */
    void m1266i() {
        m1262g();
        m1264h();
    }

    /* JADX INFO: renamed from: j */
    public boolean m1268j(int i) {
        if (i < 5 || i > 273) {
            return false;
        }
        this.f1261u = i;
        return true;
    }

    /* JADX INFO: renamed from: k */
    void m1269k(int i) throws IOException {
        if (this.f1231R) {
            this.f1247g.m1311a(this.f1248h, (this.f1241b << 4) + i, 1);
            this.f1247g.m1311a(this.f1249i, this.f1241b, 0);
            this.f1241b = C2660a.m1222d(this.f1241b);
            this.f1257q.mo1272a(this.f1247g, 0, i);
            this.f1254n[C2660a.m1219a(2)].m1295a(this.f1247g, 63);
            this.f1247g.m1309a(67108863, 26);
            this.f1256p.m1297b(this.f1247g, 15);
        }
    }

    /* JADX INFO: renamed from: h */
    public boolean m1265h(int i) {
        int i2 = 0;
        if (i < 1 || i > 536870912) {
            return false;
        }
        this.f1224K = i;
        while (i > (1 << i2)) {
            i2++;
        }
        this.f1219F = i2 * 2;
        return true;
    }

    /* JADX INFO: renamed from: i */
    public boolean m1267i(int i) {
        if (i < 0 || i > 2) {
            return false;
        }
        int i2 = this.f1230Q;
        this.f1230Q = i;
        if (this.f1246f == null || i2 == i) {
            return true;
        }
        this.f1225L = -1;
        this.f1246f = null;
        return true;
    }

    /* JADX INFO: renamed from: com.tencent.beacon.base.net.b.a.b.c$a */
    /* JADX INFO: compiled from: Encoder.java */
    class a {

        /* JADX INFO: renamed from: a */
        short[] f1267a = new short[2];

        /* JADX INFO: renamed from: b */
        C2664b[] f1268b = new C2664b[16];

        /* JADX INFO: renamed from: c */
        C2664b[] f1269c = new C2664b[16];

        /* JADX INFO: renamed from: d */
        C2664b f1270d = new C2664b(8);

        public a() {
            for (int i = 0; i < 16; i++) {
                this.f1268b[i] = new C2664b(3);
                this.f1269c[i] = new C2664b(3);
            }
        }

        /* JADX INFO: renamed from: a */
        public void m1270a(int i) {
            C2666d.m1305a(this.f1267a);
            for (int i2 = 0; i2 < i; i2++) {
                this.f1268b[i2].m1294a();
                this.f1269c[i2].m1294a();
            }
            this.f1270d.m1294a();
        }

        /* JADX INFO: renamed from: a */
        public void mo1272a(C2666d c2666d, int i, int i2) throws IOException {
            if (i < 8) {
                c2666d.m1311a(this.f1267a, 0, 0);
                this.f1268b[i2].m1295a(c2666d, i);
                return;
            }
            int i3 = i - 8;
            c2666d.m1311a(this.f1267a, 0, 1);
            if (i3 < 8) {
                c2666d.m1311a(this.f1267a, 1, 0);
                this.f1269c[i2].m1295a(c2666d, i3);
            } else {
                c2666d.m1311a(this.f1267a, 1, 1);
                this.f1270d.m1295a(c2666d, i - 16);
            }
        }

        /* JADX INFO: renamed from: a */
        public void m1271a(int i, int i2, int[] iArr, int i3) {
            int i4 = 0;
            int iM1304a = C2666d.m1304a(this.f1267a[0]);
            int iM1306b = C2666d.m1306b(this.f1267a[0]);
            int iM1304a2 = C2666d.m1304a(this.f1267a[1]) + iM1306b;
            int iM1306b2 = iM1306b + C2666d.m1306b(this.f1267a[1]);
            while (i4 < 8) {
                if (i4 >= i2) {
                    return;
                }
                iArr[i3 + i4] = this.f1268b[i].m1293a(i4) + iM1304a;
                i4++;
            }
            while (i4 < 16) {
                if (i4 >= i2) {
                    return;
                }
                iArr[i3 + i4] = this.f1269c[i].m1293a(i4 - 8) + iM1304a2;
                i4++;
            }
            while (i4 < i2) {
                iArr[i3 + i4] = this.f1270d.m1293a(i4 - 16) + iM1306b2;
                i4++;
            }
        }
    }

    /* JADX INFO: renamed from: a */
    int m1242a(int i, int i2) {
        return C2666d.m1304a(this.f1250j[i]) + C2666d.m1304a(this.f1253m[(i << 4) + i2]);
    }

    /* JADX INFO: renamed from: d */
    void m1258d() {
        for (int i = 4; i < 128; i++) {
            int iM1239d = m1239d(i);
            int i2 = (iM1239d >> 1) - 1;
            this.f1240aa[i] = C2664b.m1291a(this.f1255o, (r4 - iM1239d) - 1, i2, i - (((iM1239d & 1) | 2) << i2));
        }
        for (int i3 = 0; i3 < 4; i3++) {
            C2664b c2664b = this.f1254n[i3];
            int i4 = i3 << 6;
            for (int i5 = 0; i5 < this.f1219F; i5++) {
                this.f1215B[i4 + i5] = c2664b.m1293a(i5);
            }
            for (int i6 = 14; i6 < this.f1219F; i6++) {
                int[] iArr = this.f1215B;
                int i7 = i4 + i6;
                iArr[i7] = iArr[i7] + (((i6 >> 1) - 5) << 6);
            }
            int i8 = i3 * 128;
            int i9 = 0;
            while (i9 < 4) {
                this.f1216C[i8 + i9] = this.f1215B[i4 + i9];
                i9++;
            }
            while (i9 < 128) {
                this.f1216C[i8 + i9] = this.f1215B[m1239d(i9) + i4] + this.f1240aa[i9];
                i9++;
            }
        }
        this.f1242ba = 0;
    }

    /* JADX INFO: renamed from: e */
    void m1259e() {
        m1245a();
        this.f1247g.m1314d();
        C2666d.m1305a(this.f1248h);
        C2666d.m1305a(this.f1253m);
        C2666d.m1305a(this.f1249i);
        C2666d.m1305a(this.f1250j);
        C2666d.m1305a(this.f1251k);
        C2666d.m1305a(this.f1252l);
        C2666d.m1305a(this.f1255o);
        this.f1259s.m1278a();
        for (int i = 0; i < 4; i++) {
            this.f1254n[i].m1294a();
        }
        this.f1257q.m1270a(1 << this.f1220G);
        this.f1258r.m1270a(1 << this.f1220G);
        this.f1256p.m1294a();
        this.f1214A = false;
        this.f1265y = 0;
        this.f1266z = 0;
        this.f1264x = 0;
    }

    /* JADX INFO: renamed from: a */
    int m1244a(int i, int i2, int i3, int i4) {
        return this.f1258r.m1273a(i2 - 2, i4) + m1251b(i, i3, i4);
    }

    /* JADX INFO: renamed from: com.tencent.beacon.base.net.b.a.b.c$c */
    /* JADX INFO: compiled from: Encoder.java */
    class c {

        /* JADX INFO: renamed from: a */
        a[] f1276a;

        /* JADX INFO: renamed from: b */
        int f1277b;

        /* JADX INFO: renamed from: c */
        int f1278c;

        /* JADX INFO: renamed from: d */
        int f1279d;

        /* JADX INFO: renamed from: com.tencent.beacon.base.net.b.a.b.c$c$a */
        /* JADX INFO: compiled from: Encoder.java */
        class a {

            /* JADX INFO: renamed from: a */
            short[] f1281a = new short[BesSdkConstants.BES_TOTA_START];

            a() {
            }

            /* JADX INFO: renamed from: a */
            public void m1281a() {
                C2666d.m1305a(this.f1281a);
            }

            /* JADX INFO: renamed from: a */
            public void m1282a(C2666d c2666d, byte b) throws IOException {
                int i = 1;
                for (int i2 = 7; i2 >= 0; i2--) {
                    int i3 = (b >> i2) & 1;
                    c2666d.m1311a(this.f1281a, i, i3);
                    i = (i << 1) | i3;
                }
            }

            /* JADX INFO: renamed from: a */
            public void m1283a(C2666d c2666d, byte b, byte b2) throws IOException {
                int i;
                boolean z = true;
                int i2 = 1;
                for (int i3 = 7; i3 >= 0; i3--) {
                    int i4 = (b2 >> i3) & 1;
                    if (z) {
                        int i5 = (b >> i3) & 1;
                        i = ((i5 + 1) << 8) + i2;
                        z = i5 == i4;
                    } else {
                        i = i2;
                    }
                    c2666d.m1311a(this.f1281a, i, i4);
                    i2 = (i2 << 1) | i4;
                }
            }

            /* JADX INFO: renamed from: a */
            public int m1280a(boolean z, byte b, byte b2) {
                int i;
                int iM1307b = 0;
                int i2 = 7;
                if (z) {
                    i = 1;
                    while (i2 >= 0) {
                        int i3 = (b >> i2) & 1;
                        int i4 = (b2 >> i2) & 1;
                        iM1307b += C2666d.m1307b(this.f1281a[((i3 + 1) << 8) + i], i4);
                        i = (i << 1) | i4;
                        i2--;
                        if (i3 != i4) {
                            break;
                        }
                    }
                } else {
                    i = 1;
                }
                while (i2 >= 0) {
                    int i5 = (b2 >> i2) & 1;
                    iM1307b += C2666d.m1307b(this.f1281a[i], i5);
                    i = (i << 1) | i5;
                    i2--;
                }
                return iM1307b;
            }
        }

        c() {
        }

        /* JADX INFO: renamed from: a */
        public void m1279a(int i, int i2) {
            if (this.f1276a != null && this.f1277b == i2 && this.f1278c == i) {
                return;
            }
            this.f1278c = i;
            this.f1279d = (1 << i) - 1;
            this.f1277b = i2;
            int i3 = 1 << (i2 + i);
            this.f1276a = new a[i3];
            for (int i4 = 0; i4 < i3; i4++) {
                this.f1276a[i4] = new a();
            }
        }

        /* JADX INFO: renamed from: a */
        public void m1278a() {
            int i = 1 << (this.f1277b + this.f1278c);
            for (int i2 = 0; i2 < i; i2++) {
                this.f1276a[i2].m1281a();
            }
        }

        /* JADX INFO: renamed from: a */
        public a m1277a(int i, byte b) {
            a[] aVarArr = this.f1276a;
            int i2 = i & this.f1279d;
            int i3 = this.f1277b;
            return aVarArr[(i2 << i3) + ((b & UByte.MAX_VALUE) >>> (8 - i3))];
        }
    }

    /* JADX INFO: renamed from: f */
    void m1261f(int i) throws IOException {
        if (i > 0) {
            this.f1246f.m1197d(i);
            this.f1264x += i;
        }
    }

    /* JADX INFO: renamed from: a */
    int m1243a(int i, int i2, int i3) {
        int i4;
        int iM1219a = C2660a.m1219a(i2);
        if (i < 128) {
            i4 = this.f1216C[(iM1219a * 128) + i];
        } else {
            i4 = this.f1217D[i & 15] + this.f1215B[(iM1219a << 6) + m1240e(i)];
        }
        return i4 + this.f1257q.m1273a(i2 - 2, i3);
    }

    /* JADX INFO: renamed from: b */
    int m1251b(int i, int i2, int i3) {
        int iM1307b;
        if (i == 0) {
            return C2666d.m1304a(this.f1250j[i2]) + C2666d.m1306b(this.f1253m[(i2 << 4) + i3]);
        }
        int iM1306b = C2666d.m1306b(this.f1250j[i2]);
        if (i == 1) {
            iM1307b = C2666d.m1304a(this.f1251k[i2]);
        } else {
            iM1306b += C2666d.m1306b(this.f1251k[i2]);
            iM1307b = C2666d.m1307b(this.f1252l[i2], i - 2);
        }
        return iM1307b + iM1306b;
    }

    /* JADX INFO: renamed from: a */
    int m1241a(int i) {
        this.f1265y = i;
        d[] dVarArr = this.f1245e;
        int i2 = dVarArr[i].f1289g;
        int i3 = dVarArr[i].f1290h;
        while (true) {
            d[] dVarArr2 = this.f1245e;
            if (dVarArr2[i].f1284b) {
                dVarArr2[i2].m1285b();
                d[] dVarArr3 = this.f1245e;
                int i4 = i2 - 1;
                dVarArr3[i2].f1289g = i4;
                if (dVarArr3[i].f1285c) {
                    dVarArr3[i4].f1284b = false;
                    dVarArr3[i4].f1289g = dVarArr3[i].f1286d;
                    dVarArr3[i4].f1290h = dVarArr3[i].f1287e;
                }
            }
            d[] dVarArr4 = this.f1245e;
            int i5 = dVarArr4[i2].f1290h;
            int i6 = dVarArr4[i2].f1289g;
            dVarArr4[i2].f1290h = i3;
            dVarArr4[i2].f1289g = i;
            if (i2 <= 0) {
                this.f1235V = dVarArr4[0].f1290h;
                int i7 = dVarArr4[0].f1289g;
                this.f1266z = i7;
                return i7;
            }
            i = i2;
            i3 = i5;
            i2 = i6;
        }
    }

    /* JADX INFO: renamed from: b */
    void m1253b(int i) throws IOException {
        m1262g();
        m1269k(i & this.f1221H);
        this.f1247g.m1308a();
        this.f1247g.m1312b();
    }

    /* JADX INFO: renamed from: b */
    public void m1254b(OutputStream outputStream) throws IOException {
        this.f1239Z[0] = (byte) ((((this.f1220G * 5) + this.f1222I) * 9) + this.f1223J);
        int i = 0;
        while (i < 4) {
            int i2 = i + 1;
            this.f1239Z[i2] = (byte) (this.f1224K >> (i * 8));
            i = i2;
        }
        outputStream.write(this.f1239Z, 0, 5);
    }

    /* JADX INFO: renamed from: a */
    public void m1250a(long[] jArr, long[] jArr2, boolean[] zArr) throws IOException {
        jArr[0] = 0;
        jArr2[0] = 0;
        zArr[0] = true;
        InputStream inputStream = this.f1229P;
        if (inputStream != null) {
            this.f1246f.m1203a(inputStream);
            this.f1246f.mo1195c();
            this.f1232S = true;
            this.f1229P = null;
        }
        if (this.f1228O) {
            return;
        }
        this.f1228O = true;
        long j = this.f1227N;
        if (j == 0) {
            if (this.f1246f.m1204b() == 0) {
                m1253b((int) this.f1227N);
                return;
            }
            m1260f();
            this.f1247g.m1311a(this.f1248h, (this.f1241b << 4) + (((int) this.f1227N) & this.f1221H), 0);
            this.f1241b = C2660a.m1221c(this.f1241b);
            byte bM1200a = this.f1246f.m1200a(0 - this.f1264x);
            this.f1259s.m1277a((int) this.f1227N, this.f1243c).m1282a(this.f1247g, bM1200a);
            this.f1243c = bM1200a;
            this.f1264x--;
            this.f1227N++;
        }
        if (this.f1246f.m1204b() == 0) {
            m1253b((int) this.f1227N);
            return;
        }
        while (true) {
            int iM1255c = m1255c((int) this.f1227N);
            int i = this.f1235V;
            int i2 = ((int) this.f1227N) & this.f1221H;
            int i3 = (this.f1241b << 4) + i2;
            if (iM1255c == 1 && i == -1) {
                this.f1247g.m1311a(this.f1248h, i3, 0);
                byte bM1200a2 = this.f1246f.m1200a(0 - this.f1264x);
                c.a aVarM1277a = this.f1259s.m1277a((int) this.f1227N, this.f1243c);
                if (!C2660a.m1220b(this.f1241b)) {
                    aVarM1277a.m1283a(this.f1247g, this.f1246f.m1200a(((-1) - this.f1244d[0]) - this.f1264x), bM1200a2);
                } else {
                    aVarM1277a.m1282a(this.f1247g, bM1200a2);
                }
                this.f1243c = bM1200a2;
                this.f1241b = C2660a.m1221c(this.f1241b);
            } else {
                this.f1247g.m1311a(this.f1248h, i3, 1);
                if (i < 4) {
                    this.f1247g.m1311a(this.f1249i, this.f1241b, 1);
                    if (i == 0) {
                        this.f1247g.m1311a(this.f1250j, this.f1241b, 0);
                        if (iM1255c == 1) {
                            this.f1247g.m1311a(this.f1253m, i3, 0);
                        } else {
                            this.f1247g.m1311a(this.f1253m, i3, 1);
                        }
                    } else {
                        this.f1247g.m1311a(this.f1250j, this.f1241b, 1);
                        if (i == 1) {
                            this.f1247g.m1311a(this.f1251k, this.f1241b, 0);
                        } else {
                            this.f1247g.m1311a(this.f1251k, this.f1241b, 1);
                            this.f1247g.m1311a(this.f1252l, this.f1241b, i - 2);
                        }
                    }
                    if (iM1255c == 1) {
                        this.f1241b = C2660a.m1224f(this.f1241b);
                    } else {
                        this.f1258r.mo1272a(this.f1247g, iM1255c - 2, i2);
                        this.f1241b = C2660a.m1223e(this.f1241b);
                    }
                    int i4 = this.f1244d[i];
                    if (i != 0) {
                        while (i >= 1) {
                            int[] iArr = this.f1244d;
                            iArr[i] = iArr[i - 1];
                            i--;
                        }
                        this.f1244d[0] = i4;
                    }
                } else {
                    this.f1247g.m1311a(this.f1249i, this.f1241b, 0);
                    this.f1241b = C2660a.m1222d(this.f1241b);
                    this.f1257q.mo1272a(this.f1247g, iM1255c - 2, i2);
                    int i5 = i - 4;
                    int iM1239d = m1239d(i5);
                    this.f1254n[C2660a.m1219a(iM1255c)].m1295a(this.f1247g, iM1239d);
                    if (iM1239d >= 4) {
                        int i6 = iM1239d >> 1;
                        int i7 = i6 - 1;
                        int i8 = ((iM1239d & 1) | 2) << i7;
                        int i9 = i5 - i8;
                        if (iM1239d < 14) {
                            C2664b.m1292a(this.f1255o, (i8 - iM1239d) - 1, this.f1247g, i7, i9);
                        } else {
                            this.f1247g.m1309a(i9 >> 4, i6 - 5);
                            this.f1256p.m1297b(this.f1247g, i9 & 15);
                            this.f1218E++;
                        }
                    }
                    for (int i10 = 3; i10 >= 1; i10--) {
                        int[] iArr2 = this.f1244d;
                        iArr2[i10] = iArr2[i10 - 1];
                    }
                    this.f1244d[0] = i5;
                    this.f1242ba++;
                }
                this.f1243c = this.f1246f.m1200a((iM1255c - 1) - this.f1264x);
            }
            int i11 = this.f1264x - iM1255c;
            this.f1264x = i11;
            this.f1227N += (long) iM1255c;
            if (i11 == 0) {
                if (this.f1242ba >= 128) {
                    m1258d();
                }
                if (this.f1218E >= 16) {
                    m1256c();
                }
                jArr[0] = this.f1227N;
                jArr2[0] = this.f1247g.m1313c();
                if (this.f1246f.m1204b() == 0) {
                    m1253b((int) this.f1227N);
                    return;
                } else if (this.f1227N - j >= PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM) {
                    this.f1228O = false;
                    zArr[0] = false;
                    return;
                }
            }
        }
    }

    /* JADX INFO: renamed from: a */
    void m1248a(OutputStream outputStream) {
        this.f1247g.m1310a(outputStream);
    }

    /* JADX INFO: renamed from: a */
    void m1246a(InputStream inputStream, OutputStream outputStream, long j, long j2) {
        this.f1229P = inputStream;
        this.f1228O = false;
        m1252b();
        m1248a(outputStream);
        m1259e();
        m1258d();
        m1256c();
        this.f1257q.m1274b(this.f1261u - 1);
        this.f1257q.m1276d(1 << this.f1220G);
        this.f1258r.m1274b(this.f1261u - 1);
        this.f1258r.m1276d(1 << this.f1220G);
        this.f1227N = 0L;
    }

    /* JADX INFO: renamed from: a */
    public void m1247a(InputStream inputStream, OutputStream outputStream, long j, long j2, InterfaceC2655a interfaceC2655a) throws IOException {
        this.f1232S = false;
        try {
            m1246a(inputStream, outputStream, j, j2);
            while (true) {
                m1250a(this.f1236W, this.f1237X, this.f1238Y);
                if (this.f1238Y[0]) {
                    return;
                }
                if (interfaceC2655a != null) {
                    interfaceC2655a.m1191a(this.f1236W[0], this.f1237X[0]);
                }
            }
        } finally {
            m1266i();
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1249a(boolean z) {
        this.f1231R = z;
    }

    /* JADX INFO: renamed from: c */
    void m1256c() {
        for (int i = 0; i < 16; i++) {
            this.f1217D[i] = this.f1256p.m1296b(i);
        }
        this.f1218E = 0;
    }

    /* JADX INFO: renamed from: c */
    public boolean m1257c(int i, int i2, int i3) {
        if (i2 < 0 || i2 > 4 || i < 0 || i > 8 || i3 < 0 || i3 > 4) {
            return false;
        }
        this.f1222I = i2;
        this.f1223J = i;
        this.f1220G = i3;
        this.f1221H = (1 << i3) - 1;
        return true;
    }
}
