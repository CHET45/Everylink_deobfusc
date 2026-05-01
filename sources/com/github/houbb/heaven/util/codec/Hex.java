package com.github.houbb.heaven.util.codec;

/* JADX INFO: loaded from: classes3.dex */
public class Hex {
    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String encodeToString(byte[] bArr) {
        return new String(encode(bArr));
    }

    public static char[] encode(byte[] bArr) {
        char[] cArr = new char[bArr.length << 1];
        int i = 0;
        for (byte b : bArr) {
            int i2 = i + 1;
            char[] cArr2 = DIGITS;
            cArr[i] = cArr2[(b & 240) >>> 4];
            i += 2;
            cArr[i2] = cArr2[b & 15];
        }
        return cArr;
    }

    public static byte[] decode(byte[] bArr) throws IllegalArgumentException {
        return decode(CodecSupport.toString(bArr));
    }

    public static byte[] decode(String str) {
        return decode(str.toCharArray());
    }

    public static byte[] decode(char[] cArr) throws IllegalArgumentException {
        int length = cArr.length;
        if ((length & 1) != 0) {
            throw new IllegalArgumentException("Odd number of characters.");
        }
        byte[] bArr = new byte[length >> 1];
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int i3 = i + 1;
            int digit = (toDigit(cArr[i], i) << 4) | toDigit(cArr[i3], i3);
            i += 2;
            bArr[i2] = (byte) (digit & 255);
            i2++;
        }
        return bArr;
    }

    protected static int toDigit(char c, int i) throws IllegalArgumentException {
        int iDigit = Character.digit(c, 16);
        if (iDigit != -1) {
            return iDigit;
        }
        throw new IllegalArgumentException("Illegal hexadecimal character " + c + " at index " + i);
    }
}
