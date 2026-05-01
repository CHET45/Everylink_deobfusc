package com.fasterxml.jackson.core.p008io;

import com.fasterxml.jackson.core.util.BufferRecycler;
import com.fasterxml.jackson.core.util.TextBuffer;
import java.io.IOException;
import java.io.Writer;

/* JADX INFO: loaded from: classes3.dex */
public final class SegmentedStringWriter extends Writer implements BufferRecycler.Gettable {
    private final TextBuffer _buffer;

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() {
    }

    public SegmentedStringWriter(BufferRecycler bufferRecycler) {
        this._buffer = new TextBuffer(bufferRecycler);
    }

    @Override // com.fasterxml.jackson.core.util.BufferRecycler.Gettable
    public BufferRecycler bufferRecycler() {
        return this._buffer.bufferRecycler();
    }

    @Override // java.io.Writer, java.lang.Appendable
    public Writer append(char c) throws IOException {
        write(c);
        return this;
    }

    @Override // java.io.Writer, java.lang.Appendable
    public Writer append(CharSequence charSequence) throws IOException {
        String string = charSequence.toString();
        this._buffer.append(string, 0, string.length());
        return this;
    }

    @Override // java.io.Writer, java.lang.Appendable
    public Writer append(CharSequence charSequence, int i, int i2) throws IOException {
        String string = charSequence.subSequence(i, i2).toString();
        this._buffer.append(string, 0, string.length());
        return this;
    }

    @Override // java.io.Writer
    public void write(char[] cArr) throws IOException {
        this._buffer.append(cArr, 0, cArr.length);
    }

    @Override // java.io.Writer
    public void write(char[] cArr, int i, int i2) throws IOException {
        this._buffer.append(cArr, i, i2);
    }

    @Override // java.io.Writer
    public void write(int i) throws IOException {
        this._buffer.append((char) i);
    }

    @Override // java.io.Writer
    public void write(String str) throws IOException {
        this._buffer.append(str, 0, str.length());
    }

    @Override // java.io.Writer
    public void write(String str, int i, int i2) throws IOException {
        this._buffer.append(str, i, i2);
    }

    public String getAndClear() throws IOException {
        String strContentsAsString = this._buffer.contentsAsString();
        this._buffer.releaseBuffers();
        return strContentsAsString;
    }
}
