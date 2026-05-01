package com.tencent.cos.xml.crypto;

import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes4.dex */
public class LengthCheckInputStream extends SdkFilterInputStream {
    public static final boolean EXCLUDE_SKIPPED_BYTES = false;
    public static final boolean INCLUDE_SKIPPED_BYTES = true;
    private long dataLength;
    private final long expectedLength;
    private final boolean includeSkipped;
    private int markCount;
    private long marked;
    private int resetCount;
    private boolean resetSinceLastMarked;

    public LengthCheckInputStream(InputStream inputStream, long j, boolean z) {
        super(inputStream);
        if (j < 0) {
            throw new IllegalArgumentException();
        }
        this.expectedLength = j;
        this.includeSkipped = z;
    }

    @Override // com.tencent.cos.xml.crypto.SdkFilterInputStream, java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        int i = super.read();
        if (i >= 0) {
            this.dataLength++;
        }
        checkLength(i == -1);
        return i;
    }

    @Override // com.tencent.cos.xml.crypto.SdkFilterInputStream, java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int i3 = super.read(bArr, i, i2);
        this.dataLength += i3 >= 0 ? i3 : 0L;
        checkLength(i3 == -1);
        return i3;
    }

    @Override // com.tencent.cos.xml.crypto.SdkFilterInputStream, java.io.FilterInputStream, java.io.InputStream
    public void mark(int i) {
        if (markSupported()) {
            super.mark(i);
            this.marked = this.dataLength;
            this.markCount++;
            this.resetSinceLastMarked = false;
        }
    }

    @Override // com.tencent.cos.xml.crypto.SdkFilterInputStream, java.io.FilterInputStream, java.io.InputStream
    public void reset() throws IOException {
        if (markSupported()) {
            super.reset();
            this.dataLength = this.marked;
            this.resetCount++;
            this.resetSinceLastMarked = true;
            return;
        }
        throw new IOException("mark/reset not supported");
    }

    private void checkLength(boolean z) throws IOException {
        if (z) {
            if (this.dataLength != this.expectedLength) {
                throw new IOException("Data read has a different length than the expected: " + diagnosticInfo());
            }
        } else if (this.dataLength > this.expectedLength) {
            throw new IOException("More data read than expected: " + diagnosticInfo());
        }
    }

    private String diagnosticInfo() {
        return "dataLength=" + this.dataLength + "; expectedLength=" + this.expectedLength + "; includeSkipped=" + this.includeSkipped + "; in.getClass()=" + this.in.getClass() + "; markedSupported=" + markSupported() + "; marked=" + this.marked + "; resetSinceLastMarked=" + this.resetSinceLastMarked + "; markCount=" + this.markCount + "; resetCount=" + this.resetCount;
    }

    @Override // com.tencent.cos.xml.crypto.SdkFilterInputStream, java.io.FilterInputStream, java.io.InputStream
    public long skip(long j) throws IOException {
        long jSkip = super.skip(j);
        if (this.includeSkipped && jSkip > 0) {
            this.dataLength += jSkip;
            checkLength(false);
        }
        return jSkip;
    }
}
