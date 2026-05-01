package com.fasterxml.jackson.core.p008io.schubfach;

/* JADX INFO: loaded from: classes3.dex */
public final class DoubleToDecimal {
    private static final int BQ_MASK = 2047;
    private static final long C_MIN = 4503599627370496L;
    static final long C_TINY = 3;
    static final int E_MAX = 309;
    static final int E_MIN = -323;

    /* JADX INFO: renamed from: H */
    static final int f460H = 17;
    static final int K_MAX = 292;
    static final int K_MIN = -324;
    private static final int MASK_28 = 268435455;
    private static final long MASK_63 = Long.MAX_VALUE;
    private static final int MINUS_INF = 4;
    private static final int MINUS_ZERO = 2;
    private static final int NAN = 5;
    private static final int NON_SPECIAL = 0;

    /* JADX INFO: renamed from: P */
    static final int f461P = 53;
    private static final int PLUS_INF = 3;
    private static final int PLUS_ZERO = 1;
    static final int Q_MAX = 971;
    static final int Q_MIN = -1074;
    private static final long T_MASK = 4503599627370495L;

    /* JADX INFO: renamed from: W */
    private static final int f462W = 11;
    public final int MAX_CHARS = 24;
    private final byte[] bytes = new byte[24];
    private int index;

    private DoubleToDecimal() {
    }

    public static String toString(double d) {
        return new DoubleToDecimal().toDecimalString(d);
    }

    private String toDecimalString(double d) {
        int decimal = toDecimal(d);
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

    private int toDecimal(double d) {
        long jDoubleToRawLongBits = Double.doubleToRawLongBits(d);
        long j = T_MASK & jDoubleToRawLongBits;
        int i = ((int) (jDoubleToRawLongBits >>> 52)) & BQ_MASK;
        if (i >= BQ_MASK) {
            if (j != 0) {
                return 5;
            }
            return jDoubleToRawLongBits > 0 ? 3 : 4;
        }
        this.index = -1;
        if (jDoubleToRawLongBits < 0) {
            append(45);
        }
        if (i == 0) {
            if (j == 0) {
                return jDoubleToRawLongBits == 0 ? 1 : 2;
            }
            if (j < 3) {
                return toDecimal(Q_MIN, j * 10, -1);
            }
            return toDecimal(Q_MIN, j, 0);
        }
        int i2 = 1075 - i;
        long j2 = j | C_MIN;
        if ((i2 < 53) & (i2 > 0)) {
            long j3 = j2 >> i2;
            if ((j3 << i2) == j2) {
                return toChars(j3, 0);
            }
        }
        return toDecimal(-i2, j2, 0);
    }

    private int toDecimal(int i, long j, int i2) {
        long j2;
        int iFlog10threeQuartersPow2;
        int i3 = ((int) j) & 1;
        long j3 = j << 2;
        long j4 = j3 + 2;
        if ((j != C_MIN) | (i == Q_MIN)) {
            j2 = j3 - 2;
            iFlog10threeQuartersPow2 = MathUtils.flog10pow2(i);
        } else {
            j2 = j3 - 1;
            iFlog10threeQuartersPow2 = MathUtils.flog10threeQuartersPow2(i);
        }
        int iFlog2pow10 = i + MathUtils.flog2pow10(-iFlog10threeQuartersPow2) + 2;
        long jM516g1 = MathUtils.m516g1(iFlog10threeQuartersPow2);
        long jM515g0 = MathUtils.m515g0(iFlog10threeQuartersPow2);
        long jRop = rop(jM516g1, jM515g0, j3 << iFlog2pow10);
        long jRop2 = rop(jM516g1, jM515g0, j2 << iFlog2pow10);
        long jRop3 = rop(jM516g1, jM515g0, j4 << iFlog2pow10);
        long j5 = jRop >> 2;
        if (j5 >= 100) {
            long jMultiplyHigh = MathUtils.multiplyHigh(j5, 1844674407370955168L) * 10;
            long j6 = jMultiplyHigh + 10;
            int i4 = iFlog10threeQuartersPow2;
            long j7 = i3;
            boolean z = jRop2 + j7 <= (jMultiplyHigh << 2);
            if (z != ((j6 << 2) + j7 <= jRop3)) {
                if (!z) {
                    jMultiplyHigh = j6;
                }
                return toChars(jMultiplyHigh, i4);
            }
            iFlog10threeQuartersPow2 = i4;
        }
        long j8 = j5 + 1;
        long j9 = i3;
        boolean z2 = jRop2 + j9 <= (j5 << 2);
        if (z2 != ((j8 << 2) + j9 <= jRop3)) {
            if (!z2) {
                j5 = j8;
            }
            return toChars(j5, iFlog10threeQuartersPow2 + i2);
        }
        long j10 = jRop - ((j5 + j8) << 1);
        if (j10 >= 0 && (j10 != 0 || (1 & j5) != 0)) {
            j5 = j8;
        }
        return toChars(j5, iFlog10threeQuartersPow2 + i2);
    }

    private static long rop(long j, long j2, long j3) {
        long jMultiplyHigh = MathUtils.multiplyHigh(j2, j3);
        long j4 = j * j3;
        long jMultiplyHigh2 = MathUtils.multiplyHigh(j, j3);
        long j5 = (j4 >>> 1) + jMultiplyHigh;
        return (jMultiplyHigh2 + (j5 >>> 63)) | (((j5 & Long.MAX_VALUE) + Long.MAX_VALUE) >>> 63);
    }

    private int toChars(long j, int i) {
        int iFlog10pow2 = MathUtils.flog10pow2(64 - Long.numberOfLeadingZeros(j));
        if (j >= MathUtils.pow10(iFlog10pow2)) {
            iFlog10pow2++;
        }
        long jPow10 = j * MathUtils.pow10(17 - iFlog10pow2);
        int i2 = i + iFlog10pow2;
        long jMultiplyHigh = MathUtils.multiplyHigh(jPow10, 193428131138340668L) >>> 20;
        int i3 = (int) (jPow10 - (100000000 * jMultiplyHigh));
        int i4 = (int) ((1441151881 * jMultiplyHigh) >>> 57);
        int i5 = (int) (jMultiplyHigh - ((long) (100000000 * i4)));
        if (i2 > 0 && i2 <= 7) {
            return toChars1(i4, i5, i3, i2);
        }
        if (-3 < i2 && i2 <= 0) {
            return toChars2(i4, i5, i3, i2);
        }
        return toChars3(i4, i5, i3, i2);
    }

    private int toChars1(int i, int i2, int i3, int i4) {
        appendDigit(i);
        int iM513y = m513y(i2);
        int i5 = 1;
        while (i5 < i4) {
            int i6 = iM513y * 10;
            appendDigit(i6 >>> 28);
            iM513y = i6 & MASK_28;
            i5++;
        }
        append(46);
        while (i5 <= 8) {
            int i7 = iM513y * 10;
            appendDigit(i7 >>> 28);
            iM513y = i7 & MASK_28;
            i5++;
        }
        lowDigits(i3);
        return 0;
    }

    private int toChars2(int i, int i2, int i3, int i4) {
        appendDigit(0);
        append(46);
        while (i4 < 0) {
            appendDigit(0);
            i4++;
        }
        appendDigit(i);
        append8Digits(i2);
        lowDigits(i3);
        return 0;
    }

    private int toChars3(int i, int i2, int i3, int i4) {
        appendDigit(i);
        append(46);
        append8Digits(i2);
        lowDigits(i3);
        exponent(i4 - 1);
        return 0;
    }

    private void lowDigits(int i) {
        if (i != 0) {
            append8Digits(i);
        }
        removeTrailingZeroes();
    }

    private void append8Digits(int i) {
        int iM513y = m513y(i);
        for (int i2 = 0; i2 < 8; i2++) {
            int i3 = iM513y * 10;
            appendDigit(i3 >>> 28);
            iM513y = i3 & MASK_28;
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
    private int m513y(int i) {
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
        if (i >= 100) {
            int i2 = (i * 1311) >>> 17;
            appendDigit(i2);
            i -= i2 * 100;
        }
        int i3 = (i * 103) >>> 10;
        appendDigit(i3);
        appendDigit(i - (i3 * 10));
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
