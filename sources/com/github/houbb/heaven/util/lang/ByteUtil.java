package com.github.houbb.heaven.util.lang;

import kotlin.UByte;

/* JADX INFO: loaded from: classes3.dex */
public final class ByteUtil {
    private ByteUtil() {
    }

    public static int byteToInt(byte[] bArr) {
        return (bArr[3] & UByte.MAX_VALUE) | ((bArr[0] & UByte.MAX_VALUE) << 24) | ((bArr[1] & UByte.MAX_VALUE) << 16) | ((bArr[2] & UByte.MAX_VALUE) << 8);
    }

    public byte[] intToByte(int i) {
        return new byte[]{(byte) ((i >> 24) & 255), (byte) ((i >> 16) & 255), (byte) ((i >> 8) & 255), (byte) (i & 255)};
    }
}
