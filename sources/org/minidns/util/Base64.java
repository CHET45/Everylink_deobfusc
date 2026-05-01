package org.minidns.util;

import kotlin.UByte;

/* JADX INFO: loaded from: classes4.dex */
public final class Base64 {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    private static final String PADDING = "==";

    private Base64() {
    }

    public static String encodeToString(byte[] bArr) {
        int length = (3 - (bArr.length % 3)) % 3;
        byte[] bArr2 = new byte[bArr.length + length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bArr.length; i += 3) {
            int i2 = ((bArr2[i] & UByte.MAX_VALUE) << 16) + ((bArr2[i + 1] & UByte.MAX_VALUE) << 8) + (bArr2[i + 2] & UByte.MAX_VALUE);
            sb.append(ALPHABET.charAt((i2 >> 18) & 63)).append(ALPHABET.charAt((i2 >> 12) & 63)).append(ALPHABET.charAt((i2 >> 6) & 63)).append(ALPHABET.charAt(i2 & 63));
        }
        return sb.substring(0, sb.length() - length) + PADDING.substring(0, length);
    }
}
