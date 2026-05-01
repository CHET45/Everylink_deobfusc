package org.minidns.util;

import kotlin.UByte;

/* JADX INFO: loaded from: classes4.dex */
public final class Base32 {
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUV";
    private static final String PADDING = "======";

    private Base32() {
    }

    public static String encodeToString(byte[] bArr) {
        int length = ((int) (8.0d - (((double) (bArr.length % 5)) * 1.6d))) % 8;
        byte[] bArr2 = new byte[bArr.length + length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bArr.length; i += 5) {
            long j = (((long) (bArr2[i] & UByte.MAX_VALUE)) << 32) + (((long) (bArr2[i + 1] & UByte.MAX_VALUE)) << 24) + ((long) ((bArr2[i + 2] & UByte.MAX_VALUE) << 16)) + ((long) ((bArr2[i + 3] & UByte.MAX_VALUE) << 8)) + ((long) (bArr2[i + 4] & UByte.MAX_VALUE));
            sb.append(ALPHABET.charAt((int) ((j >> 35) & 31))).append(ALPHABET.charAt((int) ((j >> 30) & 31))).append(ALPHABET.charAt((int) ((j >> 25) & 31))).append(ALPHABET.charAt((int) ((j >> 20) & 31))).append(ALPHABET.charAt((int) ((j >> 15) & 31))).append(ALPHABET.charAt((int) ((j >> 10) & 31))).append(ALPHABET.charAt((int) ((j >> 5) & 31))).append(ALPHABET.charAt((int) (j & 31)));
        }
        return sb.substring(0, sb.length() - length) + PADDING.substring(0, length);
    }
}
