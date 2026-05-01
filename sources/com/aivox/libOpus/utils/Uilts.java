package com.aivox.libOpus.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Uilts.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m1900d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0017\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0004¨\u0006\t"}, m1901d2 = {"Lcom/aivox/libOpus/utils/Uilts;", "", "()V", "byteArrayToShortArray", "", "byteArray", "", "shortArrayToByteArray", "shortArray", "libOpus_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
public final class Uilts {
    public static final Uilts INSTANCE = new Uilts();

    private Uilts() {
    }

    public final short[] byteArrayToShortArray(byte[] byteArray) {
        Intrinsics.checkNotNullParameter(byteArray, "byteArray");
        short[] sArr = new short[byteArray.length / 2];
        ByteBuffer.wrap(byteArray).order(ByteOrder.nativeOrder()).asShortBuffer().get(sArr);
        return sArr;
    }

    public final byte[] shortArrayToByteArray(short[] shortArray) {
        Intrinsics.checkNotNullParameter(shortArray, "shortArray");
        int length = shortArray.length;
        byte[] bArr = new byte[length << 1];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i2] = (byte) shortArray[i];
            bArr[i2 + 1] = (byte) (((long) r4) >> 8);
        }
        return bArr;
    }
}
