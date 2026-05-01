package com.tencent.cos.xml.model.tag.eventstreaming;

import com.tencent.cos.xml.exception.CosXmlServiceException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public final class MessageDecoder {
    private static int defaultDecoderCapacity = 1053696;
    private static int incrementDecoderCapacity = 524288;
    private ByteBuffer buf = ByteBuffer.allocate(defaultDecoderCapacity);

    public boolean hasPendingContent() {
        return this.buf.position() != 0;
    }

    public List<Message> feed(byte[] bArr) throws CosXmlServiceException {
        return feed(bArr, 0, bArr.length);
    }

    public List<Message> feed(byte[] bArr, int i, int i2) throws CosXmlServiceException {
        int intExact;
        ByteBuffer byteBufferSafePut = safePut(this.buf, bArr, i, i2);
        this.buf = byteBufferSafePut;
        ByteBuffer byteBuffer = (ByteBuffer) byteBufferSafePut.duplicate().flip();
        ArrayList arrayList = new ArrayList();
        int i3 = 0;
        while (byteBuffer.remaining() >= 15 && byteBuffer.remaining() >= (intExact = Utils.toIntExact(Prelude.decode(byteBuffer.duplicate()).getTotalLength()))) {
            arrayList.add(Message.decode(byteBuffer));
            i3 += intExact;
        }
        if (i3 > 0) {
            this.buf.flip();
            ByteBuffer byteBuffer2 = this.buf;
            byteBuffer2.position(byteBuffer2.position() + i3);
            this.buf.compact();
        }
        return arrayList;
    }

    private ByteBuffer safePut(ByteBuffer byteBuffer, byte[] bArr, int i, int i2) {
        if (byteBuffer.remaining() < i2) {
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(byteBuffer.capacity() + incrementDecoderCapacity);
            byteBufferCopy(byteBufferAllocate, byteBuffer);
            byteBufferAllocate.put(bArr, i, i2);
            return byteBufferAllocate;
        }
        byteBuffer.put(bArr, i, i2);
        return byteBuffer;
    }

    private void byteBufferCopy(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) {
        System.arraycopy(byteBuffer2.array(), 0, byteBuffer.array(), 0, byteBuffer2.position());
        byteBuffer.position(byteBuffer2.position());
    }
}
