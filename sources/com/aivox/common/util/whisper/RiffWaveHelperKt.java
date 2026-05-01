package com.aivox.common.util.whisper;

import com.aivox.common.socket.DeviceProtocol;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p036io.ByteStreamsKt;
import kotlin.p036io.CloseableKt;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: RiffWaveHelper.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m1900d1 = {"\u0000,\n\u0000\n\u0002\u0010\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0017\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0016\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007\u001a\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b\u001a\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u000e"}, m1901d2 = {"decodeWaveFile", "", "file", "Ljava/io/File;", "encodeWaveFile", "", "data", "", "headerBytes", "", "totalLength", "", "isWavFileWithHeader", "", "common_release"}, m1902k = 2, m1903mv = {1, 9, 0}, m1905xi = 48)
public final class RiffWaveHelperKt {
    public static final float[] decodeWaveFile(File file) throws IOException {
        float fFloatValue;
        Intrinsics.checkNotNullParameter(file, "file");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            ByteStreamsKt.copyTo$default(fileInputStream, byteArrayOutputStream, 0, 2, null);
            CloseableKt.closeFinally(fileInputStream, null);
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
            byteBufferWrap.order(ByteOrder.LITTLE_ENDIAN);
            short s = byteBufferWrap.getShort(22);
            byteBufferWrap.position(44);
            ShortBuffer shortBufferAsShortBuffer = byteBufferWrap.asShortBuffer();
            int iLimit = shortBufferAsShortBuffer.limit();
            shortBufferAsShortBuffer.get(new short[iLimit]);
            int i = iLimit / s;
            float[] fArr = new float[i];
            for (int i2 = 0; i2 < i; i2++) {
                if (s == 1) {
                    fFloatValue = ((Number) RangesKt.coerceIn(Float.valueOf(r2[i2] / 32767.0f), RangesKt.rangeTo(-1.0f, 1.0f))).floatValue();
                } else {
                    int i3 = i2 * 2;
                    fFloatValue = ((Number) RangesKt.coerceIn(Float.valueOf(((r2[i3] + r2[i3 + 1]) / 32767.0f) / 2.0f), RangesKt.rangeTo(-1.0f, 1.0f))).floatValue();
                }
                fArr[i2] = fFloatValue;
            }
            return fArr;
        } finally {
        }
    }

    public static final void encodeWaveFile(File file, short[] data) throws IOException {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(data, "data");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        try {
            FileOutputStream fileOutputStream2 = fileOutputStream;
            fileOutputStream2.write(headerBytes(data.length * 2));
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(data.length * 2);
            byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
            byteBufferAllocate.asShortBuffer().put(data);
            byte[] bArr = new byte[byteBufferAllocate.limit()];
            byteBufferAllocate.get(bArr);
            fileOutputStream2.write(bArr);
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(fileOutputStream, null);
        } finally {
        }
    }

    public static final byte[] headerBytes(int i) {
        if (i < 44) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(44);
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
        byteBufferAllocate.put(DeviceProtocol.MSG_ID_WIFI.ACK_RECORD_STOP);
        byteBufferAllocate.put((byte) 73);
        byteBufferAllocate.put((byte) 70);
        byteBufferAllocate.put((byte) 70);
        byteBufferAllocate.putInt(i - 8);
        byteBufferAllocate.put((byte) 87);
        byteBufferAllocate.put((byte) 65);
        byteBufferAllocate.put((byte) 86);
        byteBufferAllocate.put((byte) 69);
        byteBufferAllocate.put((byte) 102);
        byteBufferAllocate.put((byte) 109);
        byteBufferAllocate.put((byte) 116);
        byteBufferAllocate.put((byte) 32);
        byteBufferAllocate.putInt(16);
        byteBufferAllocate.putShort((short) 1);
        byteBufferAllocate.putShort((short) 1);
        byteBufferAllocate.putInt(16000);
        byteBufferAllocate.putInt(32000);
        byteBufferAllocate.putShort((short) 2);
        byteBufferAllocate.putShort((short) 16);
        byteBufferAllocate.put((byte) 100);
        byteBufferAllocate.put((byte) 97);
        byteBufferAllocate.put((byte) 116);
        byteBufferAllocate.put((byte) 97);
        byteBufferAllocate.putInt(i - 44);
        byteBufferAllocate.position(0);
        byte[] bArr = new byte[byteBufferAllocate.limit()];
        byteBufferAllocate.get(bArr);
        return bArr;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x004f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final boolean isWavFileWithHeader(java.io.File r8) {
        /*
            java.lang.String r0 = "file"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r8, r0)
            boolean r0 = r8.exists()
            r1 = 0
            if (r0 == 0) goto L5f
            boolean r0 = r8.isFile()
            if (r0 != 0) goto L13
            goto L5f
        L13:
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch: java.lang.Exception -> L5b
            r0.<init>(r8)     // Catch: java.lang.Exception -> L5b
            java.io.Closeable r0 = (java.io.Closeable) r0     // Catch: java.lang.Exception -> L5b
            r8 = r0
            java.io.FileInputStream r8 = (java.io.FileInputStream) r8     // Catch: java.lang.Throwable -> L54
            r2 = 12
            byte[] r3 = new byte[r2]     // Catch: java.lang.Throwable -> L54
            int r8 = r8.read(r3)     // Catch: java.lang.Throwable -> L54
            r4 = 0
            if (r8 >= r2) goto L2c
            kotlin.p036io.CloseableKt.closeFinally(r0, r4)     // Catch: java.lang.Exception -> L5b
            return r1
        L2c:
            java.lang.String r8 = new java.lang.String     // Catch: java.lang.Throwable -> L54
            java.nio.charset.Charset r2 = kotlin.text.Charsets.UTF_8     // Catch: java.lang.Throwable -> L54
            r5 = 4
            r8.<init>(r3, r1, r5, r2)     // Catch: java.lang.Throwable -> L54
            java.lang.String r2 = new java.lang.String     // Catch: java.lang.Throwable -> L54
            java.nio.charset.Charset r6 = kotlin.text.Charsets.UTF_8     // Catch: java.lang.Throwable -> L54
            r7 = 8
            r2.<init>(r3, r7, r5, r6)     // Catch: java.lang.Throwable -> L54
            java.lang.String r3 = "RIFF"
            boolean r8 = kotlin.jvm.internal.Intrinsics.areEqual(r8, r3)     // Catch: java.lang.Throwable -> L54
            if (r8 == 0) goto L4f
            java.lang.String r8 = "WAVE"
            boolean r8 = kotlin.jvm.internal.Intrinsics.areEqual(r2, r8)     // Catch: java.lang.Throwable -> L54
            if (r8 == 0) goto L4f
            r8 = 1
            goto L50
        L4f:
            r8 = r1
        L50:
            kotlin.p036io.CloseableKt.closeFinally(r0, r4)     // Catch: java.lang.Exception -> L5b
            return r8
        L54:
            r8 = move-exception
            throw r8     // Catch: java.lang.Throwable -> L56
        L56:
            r2 = move-exception
            kotlin.p036io.CloseableKt.closeFinally(r0, r8)     // Catch: java.lang.Exception -> L5b
            throw r2     // Catch: java.lang.Exception -> L5b
        L5b:
            r8 = move-exception
            r8.printStackTrace()
        L5f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aivox.common.util.whisper.RiffWaveHelperKt.isWavFileWithHeader(java.io.File):boolean");
    }
}
