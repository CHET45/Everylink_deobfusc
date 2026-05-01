package com.github.houbb.heaven.util.codec;

import com.github.houbb.heaven.response.exception.CommonRuntimeException;
import com.github.houbb.heaven.util.lang.StringUtil;
import kotlin.UByte;

/* JADX INFO: loaded from: classes3.dex */
public final class Base64 {
    public static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static final char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();
    private static final byte[] CODES = new byte[256];

    private Base64() {
    }

    static {
        for (int i = 0; i < 256; i++) {
            CODES[i] = -1;
        }
        for (int i2 = 65; i2 <= 90; i2++) {
            CODES[i2] = (byte) (i2 - 65);
        }
        for (int i3 = 97; i3 <= 122; i3++) {
            CODES[i3] = (byte) (i3 - 71);
        }
        for (int i4 = 48; i4 <= 57; i4++) {
            CODES[i4] = (byte) (i4 + 4);
        }
        byte[] bArr = CODES;
        bArr[43] = 62;
        bArr[47] = 63;
    }

    public static String encodeToString(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(byteToHex(b));
        }
        return sb.toString();
    }

    public static String encodeToString(String str) {
        return StringUtil.isEmpty(str) ? str : encodeToString(str.getBytes());
    }

    public static char[] encode(byte[] bArr) {
        boolean z;
        char[] cArr = new char[((bArr.length + 2) / 3) * 4];
        int i = 0;
        int i2 = 0;
        while (i < bArr.length) {
            int i3 = (bArr[i] & UByte.MAX_VALUE) << 8;
            int i4 = i + 1;
            boolean z2 = true;
            if (i4 < bArr.length) {
                i3 |= bArr[i4] & UByte.MAX_VALUE;
                z = true;
            } else {
                z = false;
            }
            int i5 = i3 << 8;
            int i6 = i + 2;
            if (i6 < bArr.length) {
                i5 |= bArr[i6] & UByte.MAX_VALUE;
            } else {
                z2 = false;
            }
            int i7 = i2 + 3;
            char[] cArr2 = ALPHABET;
            int i8 = 64;
            cArr[i7] = cArr2[z2 ? i5 & 63 : 64];
            int i9 = i5 >> 6;
            int i10 = i2 + 2;
            if (z) {
                i8 = i9 & 63;
            }
            cArr[i10] = cArr2[i8];
            cArr[i2 + 1] = cArr2[(i5 >> 12) & 63];
            cArr[i2] = cArr2[(i5 >> 18) & 63];
            i += 3;
            i2 += 4;
        }
        return cArr;
    }

    public static byte[] decode(byte[] bArr) {
        return decode(CodecSupport.toChars(bArr));
    }

    public static byte[] decode(char[] cArr) {
        if (cArr == null) {
            return null;
        }
        int length = ((cArr.length + 3) / 4) * 3;
        if (cArr.length > 0 && cArr[cArr.length - 1] == '=') {
            length--;
        }
        if (cArr.length > 1 && cArr[cArr.length - 2] == '=') {
            length--;
        }
        byte[] bArr = new byte[length];
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        for (char c : cArr) {
            byte b = CODES[c & 255];
            if (b >= 0) {
                int i4 = i3 + 6;
                i2 = (i2 << 6) | b;
                if (i4 >= 8) {
                    i3 -= 2;
                    bArr[i] = (byte) ((i2 >> i3) & 255);
                    i++;
                } else {
                    i3 = i4;
                }
            }
        }
        if (i == length) {
            return bArr;
        }
        throw new CommonRuntimeException("miscalculated data length!");
    }

    public static String decodeToString(String str) {
        return StringUtil.isEmpty(str) ? str : new String(decode(str.toCharArray()));
    }

    public static String decodeToString(byte[] bArr) {
        return decodeToString(CodecSupport.toString(bArr));
    }

    public static String encode(String str) {
        return StringUtil.isEmpty(str) ? str : CodecSupport.toString(encode(CodecSupport.toBytes(str)));
    }

    private static String byteToHex(byte b) {
        int i = b;
        if (b < 0) {
            i = b + 256;
        }
        char[] cArr = HEX_ARRAY;
        return String.valueOf(cArr[i / 16]) + String.valueOf(cArr[i % 16]);
    }
}
