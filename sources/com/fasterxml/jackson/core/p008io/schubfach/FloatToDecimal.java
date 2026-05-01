package com.fasterxml.jackson.core.p008io.schubfach;

/* JADX INFO: loaded from: classes3.dex */
public final class FloatToDecimal {
    private static final int BQ_MASK = 255;
    private static final int C_MIN = 8388608;
    static final int C_TINY = 8;
    static final int E_MAX = 39;
    static final int E_MIN = -44;

    /* JADX INFO: renamed from: H */
    static final int f463H = 9;
    static final int K_MAX = 31;
    static final int K_MIN = -45;
    private static final int MASK_28 = 268435455;
    private static final long MASK_32 = 4294967295L;
    private static final int MINUS_INF = 4;
    private static final int MINUS_ZERO = 2;
    private static final int NAN = 5;
    private static final int NON_SPECIAL = 0;

    /* JADX INFO: renamed from: P */
    static final int f464P = 24;
    private static final int PLUS_INF = 3;
    private static final int PLUS_ZERO = 1;
    static final int Q_MAX = 104;
    static final int Q_MIN = -149;
    private static final int T_MASK = 8388607;

    /* JADX INFO: renamed from: W */
    private static final int f465W = 8;
    public final int MAX_CHARS = 15;
    private final byte[] bytes = new byte[15];
    private int index;

    private FloatToDecimal() {
    }

    public static String toString(float f) {
        return new FloatToDecimal().toDecimalString(f);
    }

    private String toDecimalString(float f) {
        int decimal = toDecimal(f);
        if (decimal == 0) {
            return charsToString();
        }
        if (decimal == 1) {
            return "0.0";
        }
        if (decimal == 2) {
            return "-0.0";
        }
        if (decimal == 3) {
            return "Infinity";
        }
        if (decimal == 4) {
            return "-Infinity";
        }
        return "NaN";
    }

    private int toDecimal(float f) {
        int iFloatToRawIntBits = Float.floatToRawIntBits(f);
        int i = T_MASK & iFloatToRawIntBits;
        int i2 = (iFloatToRawIntBits >>> 23) & 255;
        if (i2 >= 255) {
            if (i != 0) {
                return 5;
            }
            return iFloatToRawIntBits > 0 ? 3 : 4;
        }
        this.index = -1;
        if (iFloatToRawIntBits < 0) {
            append(45);
        }
        if (i2 == 0) {
            if (i == 0) {
                return iFloatToRawIntBits == 0 ? 1 : 2;
            }
            if (i < 8) {
                return toDecimal(Q_MIN, i * 10, -1);
            }
            return toDecimal(Q_MIN, i, 0);
        }
        int i3 = 150 - i2;
        int i4 = i | 8388608;
        if ((i3 > 0) & (i3 < 24)) {
            int i5 = i4 >> i3;
            if ((i5 << i3) == i4) {
                return toChars(i5, 0);
            }
        }
        return toDecimal(-i3, i4, 0);
    }

    private int toDecimal(int i, int i2, int i3) {
        long j;
        int iFlog10threeQuartersPow2;
        int i4 = i2 & 1;
        long j2 = i2 << 2;
        long j3 = j2 + 2;
        if ((i2 != 8388608) | (i == Q_MIN)) {
            j = j2 - 2;
            iFlog10threeQuartersPow2 = MathUtils.flog10pow2(i);
        } else {
            j = j2 - 1;
            iFlog10threeQuartersPow2 = MathUtils.flog10threeQuartersPow2(i);
        }
        int iFlog2pow10 = i + MathUtils.flog2pow10(-iFlog10threeQuartersPow2) + 33;
        long jM516g1 = 1 + MathUtils.m516g1(iFlog10threeQuartersPow2);
        int iRop = rop(jM516g1, j2 << iFlog2pow10);
        int iRop2 = rop(jM516g1, j << iFlog2pow10);
        int iRop3 = rop(jM516g1, j3 << iFlog2pow10);
        int i5 = iRop >> 2;
        if (i5 >= 100) {
            int i6 = ((int) ((((long) i5) * 1717986919) >>> 34)) * 10;
            int i7 = i6 + 10;
            boolean z = iRop2 + i4 <= (i6 << 2);
            if (z != ((i7 << 2) + i4 <= iRop3)) {
                if (!z) {
                    i6 = i7;
                }
                return toChars(i6, iFlog10threeQuartersPow2);
            }
        }
        int i8 = i5 + 1;
        boolean z2 = iRop2 + i4 <= (i5 << 2);
        if (z2 != ((i8 << 2) + i4 <= iRop3)) {
            if (!z2) {
                i5 = i8;
            }
            return toChars(i5, iFlog10threeQuartersPow2 + i3);
        }
        int i9 = iRop - ((i5 + i8) << 1);
        if (i9 >= 0 && (i9 != 0 || (i5 & 1) != 0)) {
            i5 = i8;
        }
        return toChars(i5, iFlog10threeQuartersPow2 + i3);
    }

    private static int rop(long j, long j2) {
        long jMultiplyHigh = MathUtils.multiplyHigh(j, j2);
        return (int) ((((jMultiplyHigh & MASK_32) + MASK_32) >>> 32) | (jMultiplyHigh >>> 31));
    }

    private int toChars(int i, int i2) {
        int iFlog10pow2 = MathUtils.flog10pow2(32 - Integer.numberOfLeadingZeros(i));
        long j = i;
        if (j >= MathUtils.pow10(iFlog10pow2)) {
            iFlog10pow2++;
        }
        int iPow10 = (int) (j * MathUtils.pow10(9 - iFlog10pow2));
        int i3 = i2 + iFlog10pow2;
        int i4 = (int) ((((long) iPow10) * 1441151881) >>> 57);
        int i5 = iPow10 - (100000000 * i4);
        if (i3 > 0 && i3 <= 7) {
            return toChars1(i4, i5, i3);
        }
        if (-3 < i3 && i3 <= 0) {
            return toChars2(i4, i5, i3);
        }
        return toChars3(i4, i5, i3);
    }

    private int toChars1(int i, int i2, int i3) {
        appendDigit(i);
        int iM514y = m514y(i2);
        int i4 = 1;
        while (i4 < i3) {
            int i5 = iM514y * 10;
            appendDigit(i5 >>> 28);
            iM514y = i5 & MASK_28;
            i4++;
        }
        append(46);
        while (i4 <= 8) {
            int i6 = iM514y * 10;
            appendDigit(i6 >>> 28);
            iM514y = i6 & MASK_28;
            i4++;
        }
        removeTrailingZeroes();
        return 0;
    }

    private int toChars2(int i, int i2, int i3) {
        appendDigit(0);
        append(46);
        while (i3 < 0) {
            appendDigit(0);
            i3++;
        }
        appendDigit(i);
        append8Digits(i2);
        removeTrailingZeroes();
        return 0;
    }

    private int toChars3(int i, int i2, int i3) {
        appendDigit(i);
        append(46);
        append8Digits(i2);
        removeTrailingZeroes();
        exponent(i3 - 1);
        return 0;
    }

    private void append8Digits(int i) {
        int iM514y = m514y(i);
        for (int i2 = 0; i2 < 8; i2++) {
            int i3 = iM514y * 10;
            appendDigit(i3 >>> 28);
            iM514y = i3 & MASK_28;
        }
    }

    private void removeTrailingZeroes() {
        int i;
        byte b;
        while (true) {
            byte[] bArr = this.bytes;
            i = this.index;
            b = bArr[i];
            if (b != 48) {
                break;
            } else {
                this.index = i - 1;
            }
        }
        if (b == 46) {
            this.index = i + 1;
        }
    }

    /* JADX INFO: renamed from: y */
    private int m514y(int i) {
        return ((int) (MathUtils.multiplyHigh(((long) (i + 1)) << 28, 193428131138340668L) >>> 20)) - 1;
    }

    private void exponent(int i) {
        append(69);
        if (i < 0) {
            append(45);
            i = -i;
        }
        if (i < 10) {
            appendDigit(i);
            return;
        }
        int i2 = (i * 103) >>> 10;
        appendDigit(i2);
        appendDigit(i - (i2 * 10));
    }

    private void append(int i) {
        byte[] bArr = this.bytes;
        int i2 = this.index + 1;
        this.index = i2;
        bArr[i2] = (byte) i;
    }

    private void appendDigit(int i) {
        byte[] bArr = this.bytes;
        int i2 = this.index + 1;
        this.index = i2;
        bArr[i2] = (byte) (i + 48);
    }

    private String charsToString() {
        return new String(this.bytes, 0, 0, this.index + 1);
    }
}
