package com.tencent.cos.xml.utils;

import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes4.dex */
public class CRC64Calculator {
    private static final int GF2_DIM = 64;
    private static final long INIT = -1;
    private static final long POLY = -3932672073523589310L;

    public static long getCRC64(InputStream inputStream, long j, long j2) throws IOException {
        int i;
        long j3 = j;
        while (true) {
            long j4 = 0;
            if (j3 > 0) {
                long jSkip = inputStream.skip(j3);
                if (jSkip <= 0) {
                    throw new IOException("Failed to skip " + j + " bytes");
                }
                j3 -= jSkip;
            } else {
                CRC64 crc64 = new CRC64();
                byte[] bArr = new byte[8192];
                while (j4 < j2 && (i = inputStream.read(bArr, 0, (int) Math.min(j2 - j4, 8192))) != -1) {
                    crc64.update(bArr, 0, i);
                    j4 += (long) i;
                }
                if (j4 != j2) {
                    throw new IOException("Expected to read " + j2 + " bytes but got " + j4);
                }
                return crc64.getValue();
            }
        }
    }

    public static long combine(long j, long j2, long j3) {
        if (j3 == 0) {
            return j;
        }
        long j4 = ~(~j);
        long j5 = ~j2;
        long[] jArr = new long[64];
        long[] jArr2 = new long[64];
        long[] jArr3 = new long[64];
        jArr[0] = -3932672073523589310L;
        long j6 = 1;
        for (int i = 1; i < 64; i++) {
            jArr[i] = j6;
            j6 <<= 1;
        }
        gf2MatrixSquare(jArr2, jArr);
        gf2MatrixSquare(jArr3, jArr2);
        long jGf2MatrixTimes = j4;
        long j7 = j3;
        do {
            gf2MatrixSquare(jArr2, jArr3);
            if ((j7 & 1) != 0) {
                jGf2MatrixTimes = gf2MatrixTimes(jArr2, jGf2MatrixTimes);
            }
            long j8 = j7 >>> 1;
            if (j8 == 0) {
                break;
            }
            gf2MatrixSquare(jArr3, jArr2);
            if ((j8 & 1) != 0) {
                jGf2MatrixTimes = gf2MatrixTimes(jArr3, jGf2MatrixTimes);
            }
            j7 >>>= 2;
        } while (j7 != 0);
        return ~(jGf2MatrixTimes ^ j5);
    }

    private static long gf2MatrixTimes(long[] jArr, long j) {
        int i = 0;
        long j2 = 0;
        while (j != 0) {
            if ((1 & j) != 0) {
                j2 ^= jArr[i];
            }
            j >>>= 1;
            i++;
        }
        return j2;
    }

    private static void gf2MatrixSquare(long[] jArr, long[] jArr2) {
        for (int i = 0; i < 64; i++) {
            jArr[i] = gf2MatrixTimes(jArr2, jArr2[i]);
        }
    }
}
