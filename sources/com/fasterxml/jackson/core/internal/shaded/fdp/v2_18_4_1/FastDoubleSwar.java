package com.fasterxml.jackson.core.internal.shaded.fdp.v2_18_4_1;

import kotlin.UByte;

/* JADX INFO: loaded from: classes3.dex */
class FastDoubleSwar {
    public static double fma(double d, double d2, double d3) {
        return (d * d2) + d3;
    }

    protected static boolean isDigit(byte b) {
        return ((char) (b + (-48))) < '\n';
    }

    protected static boolean isDigit(char c) {
        return ((char) (c + 65488)) < '\n';
    }

    public static boolean isEightDigitsUtf16(long j, long j2) {
        return ((((j + 19703549022044230L) | (j - 13511005043687472L)) | ((j2 + 19703549022044230L) | (j2 - 13511005043687472L))) & (-35747867511423104L)) == 0;
    }

    public static boolean isEightDigitsUtf8(long j) {
        return (((j + 5063812098665367110L) | (j - 3472328296227680304L)) & (-9187201950435737472L)) == 0;
    }

    public static boolean isEightZeroesUtf16(long j, long j2) {
        return j == 13511005043687472L && j2 == 13511005043687472L;
    }

    public static boolean isEightZeroesUtf8(long j) {
        return j == 3472328296227680304L;
    }

    public static int tryToParseEightDigitsUtf16(long j, long j2) {
        long j3 = j - 13511005043687472L;
        long j4 = j2 - 13511005043687472L;
        if ((((j + 19703549022044230L) | j3 | (j2 + 19703549022044230L) | j4) & (-35747867511423104L)) != 0) {
            return -1;
        }
        return ((int) ((j4 * 281475406208040961L) >>> 48)) + (((int) ((j3 * 281475406208040961L) >>> 48)) * 10000);
    }

    public static int tryToParseEightDigitsUtf8(long j) {
        long j2 = j - 3472328296227680304L;
        if ((((j + 5063812098665367110L) | j2) & (-9187201950435737472L)) != 0) {
            return -1;
        }
        long j3 = (10 * j2) + (j2 >>> 8);
        return (int) ((((j3 & 1095216660735L) * 4294967296000100L) + (((j3 >>> 16) & 1095216660735L) * 42949672960001L)) >>> 32);
    }

    public static long tryToParseEightHexDigitsUtf8(long j) {
        long j2 = (j - 3472328296227680304L) & (-9187201950435737472L);
        long j3 = (5063812098665367110L + j) & (-9187201950435737472L);
        long j4 = j | 2314885530818453536L;
        long j5 = j4 - 3472328296227680304L;
        if ((j2 | j3) != ((j4 - 7451037802321897319L) & (-9187201950435737472L) & (j4 - (-2242545357980376863L)))) {
            return -1L;
        }
        long j6 = (j3 >>> 7) * 255;
        long j7 = ((~j6) & j5) | (j5 - (j6 & 2821266740684990247L));
        long j8 = (j7 | (j7 >>> 4)) & 71777214294589695L;
        long j9 = j8 | (j8 >>> 8);
        return (j9 & 65535) | ((j9 >>> 16) & 4294901760L);
    }

    public static int tryToParseFourDigitsUtf16(long j) {
        long j2 = j - 13511005043687472L;
        if ((((j + 19703549022044230L) | j2) & (-35747867511423104L)) != 0) {
            return -1;
        }
        return (int) ((j2 * 281475406208040961L) >>> 48);
    }

    public static int tryToParseFourDigitsUtf8(int i) {
        int i2 = i - 808464432;
        if ((((i + 1179010630) | i2) & (-2139062144)) != 0) {
            return -1;
        }
        int i3 = (i2 * 2561) >>> 8;
        return ((i3 & 255) * 100) + ((i3 & 16711680) >> 16);
    }

    FastDoubleSwar() {
    }

    public static boolean isEightDigits(byte[] bArr, int i) {
        return isEightDigitsUtf8(readLongLE(bArr, i));
    }

    public static boolean isEightDigits(char[] cArr, int i) {
        return isEightDigitsUtf16(((long) cArr[i]) | (((long) cArr[i + 1]) << 16) | (((long) cArr[i + 2]) << 32) | (((long) cArr[i + 3]) << 48), (((long) cArr[i + 7]) << 48) | ((long) cArr[i + 4]) | (((long) cArr[i + 5]) << 16) | (((long) cArr[i + 6]) << 32));
    }

    public static boolean isEightDigits(CharSequence charSequence, int i) {
        boolean zIsDigit = true;
        for (int i2 = 0; i2 < 8; i2++) {
            zIsDigit &= isDigit(charSequence.charAt(i2 + i));
        }
        return zIsDigit;
    }

    public static boolean isEightZeroes(byte[] bArr, int i) {
        return isEightZeroesUtf8(readLongLE(bArr, i));
    }

    public static boolean isEightZeroes(CharSequence charSequence, int i) {
        boolean z = true;
        for (int i2 = 0; i2 < 8; i2++) {
            z &= '0' == charSequence.charAt(i2 + i);
        }
        return z;
    }

    public static boolean isEightZeroes(char[] cArr, int i) {
        return isEightZeroesUtf16(((long) cArr[i]) | (((long) cArr[i + 1]) << 16) | (((long) cArr[i + 2]) << 32) | (((long) cArr[i + 3]) << 48), (((long) cArr[i + 7]) << 48) | ((long) cArr[i + 4]) | (((long) cArr[i + 5]) << 16) | (((long) cArr[i + 6]) << 32));
    }

    public static int readIntBE(byte[] bArr, int i) {
        return (bArr[i + 3] & UByte.MAX_VALUE) | ((bArr[i] & UByte.MAX_VALUE) << 24) | ((bArr[i + 1] & UByte.MAX_VALUE) << 16) | ((bArr[i + 2] & UByte.MAX_VALUE) << 8);
    }

    public static int readIntLE(byte[] bArr, int i) {
        return (bArr[i] & UByte.MAX_VALUE) | ((bArr[i + 3] & UByte.MAX_VALUE) << 24) | ((bArr[i + 2] & UByte.MAX_VALUE) << 16) | ((bArr[i + 1] & UByte.MAX_VALUE) << 8);
    }

    public static long readLongBE(byte[] bArr, int i) {
        return (((long) bArr[i + 7]) & 255) | ((((long) bArr[i]) & 255) << 56) | ((((long) bArr[i + 1]) & 255) << 48) | ((((long) bArr[i + 2]) & 255) << 40) | ((((long) bArr[i + 3]) & 255) << 32) | ((((long) bArr[i + 4]) & 255) << 24) | ((((long) bArr[i + 5]) & 255) << 16) | ((((long) bArr[i + 6]) & 255) << 8);
    }

    public static long readLongLE(byte[] bArr, int i) {
        return (((long) bArr[i]) & 255) | ((((long) bArr[i + 7]) & 255) << 56) | ((((long) bArr[i + 6]) & 255) << 48) | ((((long) bArr[i + 5]) & 255) << 40) | ((((long) bArr[i + 4]) & 255) << 32) | ((((long) bArr[i + 3]) & 255) << 24) | ((((long) bArr[i + 2]) & 255) << 16) | ((((long) bArr[i + 1]) & 255) << 8);
    }

    public static int tryToParseEightDigits(char[] cArr, int i) {
        return tryToParseEightDigitsUtf16(((long) cArr[i]) | (((long) cArr[i + 1]) << 16) | (((long) cArr[i + 2]) << 32) | (((long) cArr[i + 3]) << 48), (((long) cArr[i + 7]) << 48) | ((long) cArr[i + 4]) | (((long) cArr[i + 5]) << 16) | (((long) cArr[i + 6]) << 32));
    }

    public static int tryToParseEightDigits(byte[] bArr, int i) {
        return tryToParseEightDigitsUtf8(readLongLE(bArr, i));
    }

    public static int tryToParseEightDigits(CharSequence charSequence, int i) {
        return tryToParseEightDigitsUtf16(((long) charSequence.charAt(i)) | (((long) charSequence.charAt(i + 1)) << 16) | (((long) charSequence.charAt(i + 2)) << 32) | (((long) charSequence.charAt(i + 3)) << 48), (((long) charSequence.charAt(i + 7)) << 48) | ((long) charSequence.charAt(i + 4)) | (((long) charSequence.charAt(i + 5)) << 16) | (((long) charSequence.charAt(i + 6)) << 32));
    }

    public static int tryToParseEightDigitsUtf8(byte[] bArr, int i) {
        return tryToParseEightDigitsUtf8(readLongLE(bArr, i));
    }

    public static long tryToParseEightHexDigits(CharSequence charSequence, int i) {
        return tryToParseEightHexDigitsUtf16((((long) charSequence.charAt(i)) << 48) | (((long) charSequence.charAt(i + 1)) << 32) | (((long) charSequence.charAt(i + 2)) << 16) | ((long) charSequence.charAt(i + 3)), ((long) charSequence.charAt(i + 7)) | (((long) charSequence.charAt(i + 4)) << 48) | (((long) charSequence.charAt(i + 5)) << 32) | (((long) charSequence.charAt(i + 6)) << 16));
    }

    public static long tryToParseEightHexDigits(char[] cArr, int i) {
        return tryToParseEightHexDigitsUtf16((((long) cArr[i]) << 48) | (((long) cArr[i + 1]) << 32) | (((long) cArr[i + 2]) << 16) | ((long) cArr[i + 3]), ((long) cArr[i + 7]) | (((long) cArr[i + 4]) << 48) | (((long) cArr[i + 5]) << 32) | (((long) cArr[i + 6]) << 16));
    }

    public static long tryToParseEightHexDigits(byte[] bArr, int i) {
        return tryToParseEightHexDigitsUtf8(readLongBE(bArr, i));
    }

    public static long tryToParseEightHexDigitsUtf16(long j, long j2) {
        if (((j | j2) & (-71777214294589696L)) != 0) {
            return -1L;
        }
        long j3 = j * 65792;
        long j4 = j2 * 65792;
        return tryToParseEightHexDigitsUtf8(((j3 & 4294901760L) << 16) | (j3 & (-281474976710656L)) | (((-281474976710656L) & j4) >>> 32) | ((j4 & 4294901760L) >>> 16));
    }

    public static int tryToParseFourDigits(char[] cArr, int i) {
        return tryToParseFourDigitsUtf16((((long) cArr[i + 3]) << 48) | ((long) cArr[i]) | (((long) cArr[i + 1]) << 16) | (((long) cArr[i + 2]) << 32));
    }

    public static int tryToParseFourDigits(CharSequence charSequence, int i) {
        return tryToParseFourDigitsUtf16((((long) charSequence.charAt(i + 3)) << 48) | ((long) charSequence.charAt(i)) | (((long) charSequence.charAt(i + 1)) << 16) | (((long) charSequence.charAt(i + 2)) << 32));
    }

    public static int tryToParseFourDigits(byte[] bArr, int i) {
        return tryToParseFourDigitsUtf8(readIntLE(bArr, i));
    }

    public static int tryToParseUpTo7Digits(byte[] bArr, int i, int i2) {
        int i3 = 0;
        boolean zIsDigit = true;
        while (i < i2) {
            byte b = bArr[i];
            zIsDigit &= isDigit(b);
            i3 = ((i3 * 10) + b) - 48;
            i++;
        }
        if (zIsDigit) {
            return i3;
        }
        return -1;
    }

    public static int tryToParseUpTo7Digits(char[] cArr, int i, int i2) {
        int i3 = 0;
        boolean zIsDigit = true;
        while (i < i2) {
            char c = cArr[i];
            zIsDigit &= isDigit(c);
            i3 = ((i3 * 10) + c) - 48;
            i++;
        }
        if (zIsDigit) {
            return i3;
        }
        return -1;
    }

    public static int tryToParseUpTo7Digits(CharSequence charSequence, int i, int i2) {
        int i3 = 0;
        boolean zIsDigit = true;
        while (i < i2) {
            char cCharAt = charSequence.charAt(i);
            zIsDigit &= isDigit(cCharAt);
            i3 = ((i3 * 10) + cCharAt) - 48;
            i++;
        }
        if (zIsDigit) {
            return i3;
        }
        return -1;
    }

    public static void writeIntBE(byte[] bArr, int i, int i2) {
        bArr[i] = (byte) (i2 >>> 24);
        bArr[i + 1] = (byte) (i2 >>> 16);
        bArr[i + 2] = (byte) (i2 >>> 8);
        bArr[i + 3] = (byte) i2;
    }

    public static void writeLongBE(byte[] bArr, int i, long j) {
        bArr[i] = (byte) (j >>> 56);
        bArr[i + 1] = (byte) (j >>> 48);
        bArr[i + 2] = (byte) (j >>> 40);
        bArr[i + 3] = (byte) (j >>> 32);
        bArr[i + 4] = (byte) (j >>> 24);
        bArr[i + 5] = (byte) (j >>> 16);
        bArr[i + 6] = (byte) (j >>> 8);
        bArr[i + 7] = (byte) j;
    }
}
