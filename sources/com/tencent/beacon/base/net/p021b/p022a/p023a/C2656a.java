package com.tencent.beacon.base.net.p021b.p022a.p023a;

import com.fasterxml.jackson.core.internal.shaded.fdp.v2_18_4_1.FastDoubleMath;
import java.io.IOException;
import kotlin.UByte;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.b.a.a.a */
/* JADX INFO: compiled from: BinTree.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2656a extends C2657b {

    /* JADX INFO: renamed from: l */
    private static final int[] f1142l = new int[256];

    /* JADX INFO: renamed from: m */
    int f1143m;

    /* JADX INFO: renamed from: o */
    int f1145o;

    /* JADX INFO: renamed from: p */
    int[] f1146p;

    /* JADX INFO: renamed from: q */
    int[] f1147q;

    /* JADX INFO: renamed from: s */
    int f1149s;

    /* JADX INFO: renamed from: n */
    int f1144n = 0;

    /* JADX INFO: renamed from: r */
    int f1148r = 255;

    /* JADX INFO: renamed from: t */
    int f1150t = 0;

    /* JADX INFO: renamed from: u */
    boolean f1151u = true;

    /* JADX INFO: renamed from: v */
    int f1152v = 0;

    /* JADX INFO: renamed from: w */
    int f1153w = 4;

    /* JADX INFO: renamed from: x */
    int f1154x = 66560;

    static {
        for (int i = 0; i < 256; i++) {
            int i2 = i;
            for (int i3 = 0; i3 < 8; i3++) {
                int i4 = i2 & 1;
                i2 >>>= 1;
                if (i4 != 0) {
                    i2 ^= -306674912;
                }
            }
            f1142l[i] = i2;
        }
    }

    /* JADX INFO: renamed from: a */
    public boolean m1194a(int i, int i2, int i3, int i4) {
        int i5;
        if (i > 1073741567) {
            return false;
        }
        this.f1148r = (i3 >> 1) + 16;
        int i6 = i2 + i;
        super.m1202a(i6, i4 + i3, (((i6 + i3) + i4) / 2) + 256);
        this.f1145o = i3;
        int i7 = i + 1;
        if (this.f1144n != i7) {
            this.f1144n = i7;
            this.f1146p = new int[i7 * 2];
        }
        if (this.f1151u) {
            int i8 = i - 1;
            int i9 = i8 | (i8 >> 1);
            int i10 = i9 | (i9 >> 2);
            int i11 = i10 | (i10 >> 4);
            int i12 = ((i11 | (i11 >> 8)) >> 1) | 65535;
            if (i12 > 16777216) {
                i12 >>= 1;
            }
            this.f1149s = i12;
            i5 = i12 + 1 + this.f1154x;
        } else {
            i5 = 65536;
        }
        if (i5 != this.f1150t) {
            this.f1150t = i5;
            this.f1147q = new int[i5];
        }
        return true;
    }

    /* JADX INFO: renamed from: c */
    public void m1196c(int i) {
        boolean z = i > 2;
        this.f1151u = z;
        if (z) {
            this.f1152v = 0;
            this.f1153w = 4;
            this.f1154x = 66560;
        } else {
            this.f1152v = 2;
            this.f1153w = 3;
            this.f1154x = 0;
        }
    }

    /* JADX INFO: renamed from: d */
    public void m1197d(int i) throws IOException {
        int i2;
        byte[] bArr;
        int i3 = i;
        do {
            int i4 = this.f1162h;
            int i5 = this.f1145o;
            int i6 = i4 + i5;
            int i7 = this.f1165k;
            if (i6 > i7 && (i5 = i7 - i4) < this.f1153w) {
                mo1198e();
            } else {
                int i8 = this.f1162h;
                int i9 = this.f1144n;
                int i10 = i8 > i9 ? i8 - i9 : 0;
                int i11 = this.f1160f;
                int i12 = this.f1162h;
                int i13 = i11 + i12;
                if (this.f1151u) {
                    int[] iArr = f1142l;
                    byte[] bArr2 = this.f1155a;
                    int i14 = iArr[bArr2[i13] & UByte.MAX_VALUE] ^ (bArr2[i13 + 1] & UByte.MAX_VALUE);
                    int i15 = i14 & FastDoubleMath.DOUBLE_EXPONENT_BIAS;
                    int[] iArr2 = this.f1147q;
                    iArr2[i15] = i12;
                    int i16 = i14 ^ ((bArr2[i13 + 2] & UByte.MAX_VALUE) << 8);
                    iArr2[(65535 & i16) + 1024] = i12;
                    i2 = ((iArr[bArr2[i13 + 3] & UByte.MAX_VALUE] << 5) ^ i16) & this.f1149s;
                } else {
                    byte[] bArr3 = this.f1155a;
                    i2 = ((bArr3[i13 + 1] & UByte.MAX_VALUE) << 8) ^ (bArr3[i13] & UByte.MAX_VALUE);
                }
                int[] iArr3 = this.f1147q;
                int i17 = this.f1154x + i2;
                int i18 = iArr3[i17];
                iArr3[i17] = this.f1162h;
                int i19 = this.f1143m << 1;
                int i20 = i19 + 1;
                int i21 = this.f1152v;
                int i22 = this.f1148r;
                int i23 = i21;
                while (i18 > i10) {
                    int i24 = i22 - 1;
                    if (i22 == 0) {
                        break;
                    }
                    int i25 = this.f1162h - i18;
                    int i26 = this.f1143m;
                    int i27 = (i25 <= i26 ? i26 - i25 : (i26 - i25) + this.f1144n) << 1;
                    int i28 = this.f1160f + i18;
                    int iMin = Math.min(i21, i23);
                    byte[] bArr4 = this.f1155a;
                    if (bArr4[i28 + iMin] == bArr4[i13 + iMin]) {
                        do {
                            iMin++;
                            if (iMin == i5) {
                                break;
                            } else {
                                bArr = this.f1155a;
                            }
                        } while (bArr[i28 + iMin] == bArr[i13 + iMin]);
                        if (iMin == i5) {
                            int[] iArr4 = this.f1146p;
                            iArr4[i19] = iArr4[i27];
                            iArr4[i20] = iArr4[i27 + 1];
                            break;
                        }
                    }
                    byte[] bArr5 = this.f1155a;
                    if ((bArr5[i28 + iMin] & UByte.MAX_VALUE) < (bArr5[i13 + iMin] & UByte.MAX_VALUE)) {
                        int[] iArr5 = this.f1146p;
                        iArr5[i19] = i18;
                        int i29 = i27 + 1;
                        i18 = iArr5[i29];
                        i19 = i29;
                        i23 = iMin;
                    } else {
                        int[] iArr6 = this.f1146p;
                        iArr6[i20] = i18;
                        i18 = iArr6[i27];
                        i20 = i27;
                        i21 = iMin;
                    }
                    i22 = i24;
                }
                int[] iArr7 = this.f1146p;
                iArr7[i19] = 0;
                iArr7[i20] = 0;
                mo1198e();
            }
            i3--;
        } while (i3 != 0);
    }

    @Override // com.tencent.beacon.base.net.p021b.p022a.p023a.C2657b
    /* JADX INFO: renamed from: e */
    public void mo1198e() throws IOException {
        int i = this.f1143m + 1;
        this.f1143m = i;
        if (i >= this.f1144n) {
            this.f1143m = 0;
        }
        super.mo1198e();
        if (this.f1162h == 1073741823) {
            m1199h();
        }
    }

    /* JADX INFO: renamed from: h */
    void m1199h() {
        int i = this.f1162h;
        int i2 = this.f1144n;
        int i3 = i - i2;
        m1193a(this.f1146p, i2 * 2, i3);
        m1193a(this.f1147q, this.f1150t, i3);
        m1206b(i3);
    }

    @Override // com.tencent.beacon.base.net.p021b.p022a.p023a.C2657b
    /* JADX INFO: renamed from: c */
    public void mo1195c() throws IOException {
        super.mo1195c();
        for (int i = 0; i < this.f1150t; i++) {
            this.f1147q[i] = 0;
        }
        this.f1143m = 0;
        m1206b(-1);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00f5  */
    /* JADX INFO: renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int m1192a(int[] r20) throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 412
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.beacon.base.net.p021b.p022a.p023a.C2656a.m1192a(int[]):int");
    }

    /* JADX INFO: renamed from: a */
    void m1193a(int[] iArr, int i, int i2) {
        for (int i3 = 0; i3 < i; i3++) {
            int i4 = iArr[i3];
            iArr[i3] = i4 <= i2 ? 0 : i4 - i2;
        }
    }
}
