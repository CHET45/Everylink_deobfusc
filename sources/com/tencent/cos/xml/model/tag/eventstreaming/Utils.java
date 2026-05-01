package com.tencent.cos.xml.model.tag.eventstreaming;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import kotlin.UByte;
import kotlin.UShort;

/* JADX INFO: loaded from: classes4.dex */
final class Utils {
    private static final String UTF8 = "UTF-8";

    static long toUnsignedLong(int i) {
        return ((long) i) & 4294967295L;
    }

    private Utils() {
    }

    static int toIntExact(long j) {
        int i = (int) j;
        if (i == j) {
            return i;
        }
        throw new ArithmeticException("integer overflow");
    }

    static String readShortString(ByteBuffer byteBuffer) throws UnsupportedEncodingException {
        int i = byteBuffer.get() & UByte.MAX_VALUE;
        checkStringBounds(i, 255);
        byte[] bArr = new byte[i];
        byteBuffer.get(bArr);
        return new String(bArr, "UTF-8");
    }

    static String readString(ByteBuffer byteBuffer) throws UnsupportedEncodingException {
        int i = byteBuffer.getShort() & UShort.MAX_VALUE;
        checkStringBounds(i, 32767);
        byte[] bArr = new byte[i];
        byteBuffer.get(bArr);
        return new String(bArr, "UTF-8");
    }

    static byte[] readBytes(ByteBuffer byteBuffer) {
        int i = byteBuffer.getShort() & UShort.MAX_VALUE;
        checkByteArrayBounds(i);
        byte[] bArr = new byte[i];
        byteBuffer.get(bArr);
        return bArr;
    }

    static void writeShortString(DataOutputStream dataOutputStream, String str) throws IOException {
        byte[] bytes = str.getBytes("UTF-8");
        checkStringBounds(bytes.length, 255);
        dataOutputStream.writeByte(bytes.length);
        dataOutputStream.write(bytes);
    }

    static void writeString(DataOutputStream dataOutputStream, String str) throws IOException {
        byte[] bytes = str.getBytes("UTF-8");
        checkStringBounds(bytes.length, 32767);
        writeBytes(dataOutputStream, bytes);
    }

    static void writeBytes(DataOutputStream dataOutputStream, byte[] bArr) throws IOException {
        checkByteArrayBounds(bArr.length);
        dataOutputStream.writeShort((short) bArr.length);
        dataOutputStream.write(bArr);
    }

    private static void checkByteArrayBounds(int i) {
        if (i == 0) {
            throw new IllegalArgumentException("Byte arrays may not be empty");
        }
        if (i > 32767) {
            throw new IllegalArgumentException("Illegal byte array length: " + i);
        }
    }

    private static void checkStringBounds(int i, int i2) {
        if (i == 0) {
            throw new IllegalArgumentException("Strings may not be empty");
        }
        if (i > i2) {
            throw new IllegalArgumentException("Illegal string length: " + i);
        }
    }
}
