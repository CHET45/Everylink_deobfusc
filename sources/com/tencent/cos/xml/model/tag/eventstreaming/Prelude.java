package com.tencent.cos.xml.model.tag.eventstreaming;

import android.support.v4.media.session.PlaybackStateCompat;
import java.nio.ByteBuffer;
import java.util.zip.CRC32;

/* JADX INFO: loaded from: classes4.dex */
final class Prelude {
    static final int LENGTH = 8;
    static final int LENGTH_WITH_CRC = 12;
    private final long headersLength;
    private final int totalLength;

    private static long intToUnsignedLong(int i) {
        return ((long) i) & 4294967295L;
    }

    private Prelude(int i, long j) {
        this.totalLength = i;
        this.headersLength = j;
    }

    static Prelude decode(ByteBuffer byteBuffer) {
        ByteBuffer byteBufferDuplicate = byteBuffer.duplicate();
        long jComputePreludeCrc = computePreludeCrc(byteBufferDuplicate);
        long jIntToUnsignedLong = intToUnsignedLong(byteBufferDuplicate.getInt());
        long jIntToUnsignedLong2 = intToUnsignedLong(byteBufferDuplicate.getInt());
        long jIntToUnsignedLong3 = intToUnsignedLong(byteBufferDuplicate.getInt());
        if (jComputePreludeCrc != jIntToUnsignedLong3) {
            throw new IllegalArgumentException(String.format("Prelude checksum failure: expected 0x%x, computed 0x%x", Long.valueOf(jIntToUnsignedLong3), Long.valueOf(jComputePreludeCrc)));
        }
        if (jIntToUnsignedLong2 < 0 || jIntToUnsignedLong2 > PlaybackStateCompat.ACTION_PREPARE_FROM_URI) {
            throw new IllegalArgumentException("Illegal headers_length value: " + jIntToUnsignedLong2);
        }
        long j = (jIntToUnsignedLong - jIntToUnsignedLong2) - 16;
        if (j < 0 || j > 16777216) {
            throw new IllegalArgumentException("Illegal payload size: " + j);
        }
        return new Prelude(toIntExact(jIntToUnsignedLong), jIntToUnsignedLong2);
    }

    private static int toIntExact(long j) {
        int i = (int) j;
        if (i == j) {
            return i;
        }
        throw new ArithmeticException("integer overflow");
    }

    private static long computePreludeCrc(ByteBuffer byteBuffer) {
        byte[] bArr = new byte[8];
        byteBuffer.duplicate().get(bArr);
        CRC32 crc32 = new CRC32();
        crc32.update(bArr, 0, 8);
        return crc32.getValue();
    }

    int getTotalLength() {
        return this.totalLength;
    }

    long getHeadersLength() {
        return this.headersLength;
    }
}
