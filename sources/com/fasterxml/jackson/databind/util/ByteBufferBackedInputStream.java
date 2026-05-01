package com.fasterxml.jackson.databind.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import kotlin.UByte;

/* JADX INFO: loaded from: classes3.dex */
public class ByteBufferBackedInputStream extends InputStream {

    /* JADX INFO: renamed from: _b */
    protected final ByteBuffer f486_b;

    public ByteBufferBackedInputStream(ByteBuffer byteBuffer) {
        this.f486_b = byteBuffer;
    }

    @Override // java.io.InputStream
    public int available() {
        return this.f486_b.remaining();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (this.f486_b.hasRemaining()) {
            return this.f486_b.get() & UByte.MAX_VALUE;
        }
        return -1;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (!this.f486_b.hasRemaining()) {
            return -1;
        }
        int iMin = Math.min(i2, this.f486_b.remaining());
        this.f486_b.get(bArr, i, iMin);
        return iMin;
    }
}
