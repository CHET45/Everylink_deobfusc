package com.tencent.cos.xml.model.tag.eventstreaming;

import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.p035s3.Base64;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;

/* JADX INFO: loaded from: classes4.dex */
public class Message {
    static final int MESSAGE_OVERHEAD = 16;
    private static final int TRAILING_CRC_LENGTH = 4;
    private final Map<String, HeaderValue> headers;
    private final byte[] payload;

    public Message(Map<String, HeaderValue> map, byte[] bArr) {
        this.headers = map;
        this.payload = (byte[]) bArr.clone();
    }

    public Map<String, HeaderValue> getHeaders() {
        return this.headers;
    }

    public byte[] getPayload() {
        return (byte[]) this.payload.clone();
    }

    public static Message decode(ByteBuffer byteBuffer) throws CosXmlServiceException {
        Prelude preludeDecode = Prelude.decode(byteBuffer);
        int totalLength = preludeDecode.getTotalLength();
        validateMessageCrc(byteBuffer, totalLength);
        byteBuffer.position(byteBuffer.position() + 12);
        long headersLength = preludeDecode.getHeadersLength();
        byte[] bArr = new byte[Utils.toIntExact(headersLength)];
        byteBuffer.get(bArr);
        Map<String, HeaderValue> mapDecodeHeaders = decodeHeaders(ByteBuffer.wrap(bArr));
        byte[] bArr2 = new byte[Utils.toIntExact(((long) (totalLength - 16)) - headersLength)];
        byteBuffer.get(bArr2);
        byteBuffer.getInt();
        return new Message(mapDecodeHeaders, bArr2);
    }

    private static void validateMessageCrc(ByteBuffer byteBuffer, int i) throws CosXmlServiceException {
        CRC32 crc32 = new CRC32();
        CheckSums.update(crc32, (ByteBuffer) byteBuffer.duplicate().limit((byteBuffer.position() + i) - 4));
        long value = crc32.getValue();
        long unsignedLong = Utils.toUnsignedLong(byteBuffer.getInt((byteBuffer.position() + i) - 4));
        if (unsignedLong != value) {
            throw new CosXmlServiceException("CRC failed", new ArithmeticException(String.format("Message checksum failure: expected 0x%x, computed 0x%x", Long.valueOf(unsignedLong), Long.valueOf(value))));
        }
    }

    static Map<String, HeaderValue> decodeHeaders(ByteBuffer byteBuffer) {
        HashMap map = new HashMap();
        while (byteBuffer.hasRemaining()) {
            Header headerDecode = Header.decode(byteBuffer);
            map.put(headerDecode.getName(), headerDecode.getValue());
        }
        return Collections.unmodifiableMap(map);
    }

    public ByteBuffer toByteBuffer() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            encode(byteArrayOutputStream);
            byteArrayOutputStream.close();
            return ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void encode(OutputStream outputStream) {
        try {
            CheckedOutputStream checkedOutputStream = new CheckedOutputStream(outputStream, new CRC32());
            encodeOrThrow(checkedOutputStream);
            long value = checkedOutputStream.getChecksum().getValue();
            outputStream.write((int) ((value >> 24) & 255));
            outputStream.write((int) ((value >> 16) & 255));
            outputStream.write((int) ((value >> 8) & 255));
            outputStream.write((int) (value & 255));
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void encodeOrThrow(OutputStream outputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        Iterator<Map.Entry<String, HeaderValue>> it = this.headers.entrySet().iterator();
        while (it.hasNext()) {
            Header.encode(it.next(), dataOutputStream);
        }
        dataOutputStream.write(this.payload);
        dataOutputStream.flush();
        byte[] prelude = getPrelude(byteArrayOutputStream.size() + 16);
        CRC32 crc32 = new CRC32();
        crc32.update(prelude, 0, prelude.length);
        DataOutputStream dataOutputStream2 = new DataOutputStream(outputStream);
        dataOutputStream2.write(prelude);
        dataOutputStream2.writeInt((int) crc32.getValue());
        dataOutputStream2.flush();
        byteArrayOutputStream.writeTo(outputStream);
    }

    private byte[] getPrelude(int i) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(8);
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        int length = (i - 16) - this.payload.length;
        dataOutputStream.writeInt(i);
        dataOutputStream.writeInt(length);
        dataOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Message message = (Message) obj;
        if (this.headers.equals(message.headers)) {
            return Arrays.equals(this.payload, message.payload);
        }
        return false;
    }

    public int hashCode() {
        return (this.headers.hashCode() * 31) + Arrays.hashCode(this.payload);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, HeaderValue> entry : this.headers.entrySet()) {
            sb.append(entry.getKey());
            sb.append(": ");
            sb.append(entry.getValue().toString());
            sb.append('\n');
        }
        sb.append('\n');
        HeaderValue headerValueFromString = this.headers.get("content-type");
        if (headerValueFromString == null) {
            headerValueFromString = HeaderValue.fromString("application/octet-stream");
        }
        String string = headerValueFromString.getString();
        if (string.contains("json") || string.contains("text")) {
            try {
                sb.append(new String(this.payload, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            sb.append(Base64.encodeAsString(this.payload));
        }
        sb.append('\n');
        return sb.toString();
    }
}
