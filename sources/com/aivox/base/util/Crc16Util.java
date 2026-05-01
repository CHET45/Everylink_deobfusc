package com.aivox.base.util;

import kotlin.UByte;

/* JADX INFO: loaded from: classes.dex */
public class Crc16Util {
    private static final int INITIAL = 0;
    private static final int POLYNOMIAL = 40961;
    private static final int XOR_OUT = 0;
    private static final int[] crc16Table = generateCRC16Table();

    private static int[] generateCRC16Table() {
        int[] iArr = new int[256];
        for (int i = 0; i < 256; i++) {
            int i2 = i;
            for (int i3 = 0; i3 < 8; i3++) {
                i2 = (i2 & 1) != 0 ? (i2 >>> 1) ^ POLYNOMIAL : i2 >>> 1;
            }
            iArr[i] = i2;
        }
        return iArr;
    }

    private static int crc16Byte(int i, int i2) {
        return crc16Table[(i ^ i2) & 255] ^ (i >>> 8);
    }

    public static int calculate(Byte[] bArr) {
        int iCrc16Byte = 0;
        for (Byte b : bArr) {
            iCrc16Byte = crc16Byte(iCrc16Byte, b.byteValue() & UByte.MAX_VALUE);
        }
        return 65535 & iCrc16Byte;
    }
}
