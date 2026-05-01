package com.tencent.aai.audio.utils;

import androidx.core.view.MotionEventCompat;
import kotlin.UByte;

/* JADX INFO: loaded from: classes4.dex */
public class CharUtils {
    public static short[] byteArray2ShortArray(byte[] bArr, int i) {
        int i2 = 0;
        if (bArr == null || bArr.length < 2 || i < 2) {
            return new short[0];
        }
        int i3 = i / 2;
        short[] sArr = new short[i3];
        int i4 = 0;
        while (i2 < i3) {
            sArr[i2] = (short) (((short) (bArr[i4] & UByte.MAX_VALUE)) | ((short) ((bArr[i4 + 1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)));
            i2++;
            i4 += 2;
        }
        return sArr;
    }

    public static byte[] shortArray2ByteArray(short[] sArr) {
        int i = 0;
        if (sArr == null || sArr.length == 0) {
            return new byte[0];
        }
        byte[] bArr = new byte[sArr.length * 2];
        int i2 = 0;
        while (i < sArr.length) {
            short s = sArr[i];
            bArr[i2] = (byte) (s & 255);
            bArr[i2 + 1] = (byte) ((s >> 8) & 255);
            i++;
            i2 += 2;
        }
        return bArr;
    }
}
