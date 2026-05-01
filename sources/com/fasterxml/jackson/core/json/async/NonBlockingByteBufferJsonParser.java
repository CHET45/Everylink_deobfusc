package com.fasterxml.jackson.core.json.async;

import com.fasterxml.jackson.core.async.ByteBufferFeeder;
import com.fasterxml.jackson.core.async.NonBlockingInputFeeder;
import com.fasterxml.jackson.core.p008io.IOContext;
import com.fasterxml.jackson.core.sym.ByteQuadsCanonicalizer;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import kotlin.UByte;

/* JADX INFO: loaded from: classes3.dex */
public class NonBlockingByteBufferJsonParser extends NonBlockingUtf8JsonParserBase implements ByteBufferFeeder {
    private ByteBuffer _inputBuffer;

    @Override // com.fasterxml.jackson.core.JsonParser
    public NonBlockingInputFeeder getNonBlockingInputFeeder() {
        return this;
    }

    public NonBlockingByteBufferJsonParser(IOContext iOContext, int i, ByteQuadsCanonicalizer byteQuadsCanonicalizer) {
        super(iOContext, i, byteQuadsCanonicalizer);
        this._inputBuffer = ByteBuffer.wrap(NO_BYTES);
    }

    @Override // com.fasterxml.jackson.core.async.ByteBufferFeeder
    public void feedInput(ByteBuffer byteBuffer) throws IOException {
        if (this._inputPtr < this._inputEnd) {
            _reportError("Still have %d undecoded bytes, should not call 'feedInput'", Integer.valueOf(this._inputEnd - this._inputPtr));
        }
        int iPosition = byteBuffer.position();
        int iLimit = byteBuffer.limit();
        if (iLimit < iPosition) {
            _reportError("Input end (%d) may not be before start (%d)", Integer.valueOf(iLimit), Integer.valueOf(iPosition));
        }
        if (this._endOfInput) {
            _reportError("Already closed, can not feed more input");
        }
        this._currInputProcessed += (long) this._origBufferLen;
        this._streamReadConstraints.validateDocumentLength(this._currInputProcessed);
        this._currInputRowStart = iPosition - (this._inputEnd - this._currInputRowStart);
        this._currBufferStart = iPosition;
        this._inputBuffer = byteBuffer;
        this._inputPtr = iPosition;
        this._inputEnd = iLimit;
        this._origBufferLen = iLimit - iPosition;
    }

    @Override // com.fasterxml.jackson.core.json.async.NonBlockingJsonParserBase, com.fasterxml.jackson.core.JsonParser
    public int releaseBuffered(OutputStream outputStream) throws IOException {
        int i = this._inputEnd - this._inputPtr;
        if (i > 0) {
            Channels.newChannel(outputStream).write(this._inputBuffer);
        }
        return i;
    }

    @Override // com.fasterxml.jackson.core.json.async.NonBlockingUtf8JsonParserBase
    protected byte getNextSignedByteFromBuffer() {
        ByteBuffer byteBuffer = this._inputBuffer;
        int i = this._inputPtr;
        this._inputPtr = i + 1;
        return byteBuffer.get(i);
    }

    @Override // com.fasterxml.jackson.core.json.async.NonBlockingUtf8JsonParserBase
    protected int getNextUnsignedByteFromBuffer() {
        ByteBuffer byteBuffer = this._inputBuffer;
        int i = this._inputPtr;
        this._inputPtr = i + 1;
        return byteBuffer.get(i) & UByte.MAX_VALUE;
    }

    @Override // com.fasterxml.jackson.core.json.async.NonBlockingUtf8JsonParserBase
    protected byte getByteFromBuffer(int i) {
        return this._inputBuffer.get(i);
    }
}
