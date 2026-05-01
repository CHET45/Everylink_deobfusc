package org.minidns.record;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import org.minidns.constants.DnssecConstants;
import org.minidns.record.Record;
import org.minidns.util.Base64;

/* JADX INFO: loaded from: classes4.dex */
public class DNSKEY extends Data {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final short FLAG_REVOKE = 128;
    public static final short FLAG_SECURE_ENTRY_POINT = 1;
    public static final short FLAG_ZONE = 256;
    public static final byte PROTOCOL_RFC4034 = 3;
    public final DnssecConstants.SignatureAlgorithm algorithm;
    public final byte algorithmByte;
    public final short flags;
    private final byte[] key;
    private transient String keyBase64Cache;
    private transient BigInteger keyBigIntegerCache;
    private transient Integer keyTag;
    public final byte protocol;

    public static DNSKEY parse(DataInputStream dataInputStream, int i) throws IOException {
        short s = dataInputStream.readShort();
        byte b = dataInputStream.readByte();
        byte b2 = dataInputStream.readByte();
        byte[] bArr = new byte[i - 4];
        dataInputStream.readFully(bArr);
        return new DNSKEY(s, b, b2, bArr);
    }

    private DNSKEY(short s, byte b, DnssecConstants.SignatureAlgorithm signatureAlgorithm, byte b2, byte[] bArr) {
        this.flags = s;
        this.protocol = b;
        this.algorithmByte = b2;
        this.algorithm = signatureAlgorithm == null ? DnssecConstants.SignatureAlgorithm.forByte(b2) : signatureAlgorithm;
        this.key = bArr;
    }

    public DNSKEY(short s, byte b, byte b2, byte[] bArr) {
        this(s, b, DnssecConstants.SignatureAlgorithm.forByte(b2), b2, bArr);
    }

    public DNSKEY(short s, byte b, DnssecConstants.SignatureAlgorithm signatureAlgorithm, byte[] bArr) {
        this(s, b, signatureAlgorithm, signatureAlgorithm.number, bArr);
    }

    @Override // org.minidns.record.Data
    public Record.TYPE getType() {
        return Record.TYPE.DNSKEY;
    }

    public int getKeyTag() {
        if (this.keyTag == null) {
            byte[] byteArray = toByteArray();
            long j = 0;
            for (int i = 0; i < byteArray.length; i++) {
                j += (i & 1) > 0 ? ((long) byteArray[i]) & 255 : (((long) byteArray[i]) & 255) << 8;
            }
            this.keyTag = Integer.valueOf((int) ((j + ((j >> 16) & 65535)) & 65535));
        }
        return this.keyTag.intValue();
    }

    @Override // org.minidns.record.Data
    public void serialize(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeShort(this.flags);
        dataOutputStream.writeByte(this.protocol);
        dataOutputStream.writeByte(this.algorithmByte);
        dataOutputStream.write(this.key);
    }

    public String toString() {
        return ((int) this.flags) + ' ' + ((int) this.protocol) + ' ' + this.algorithm + ' ' + Base64.encodeToString(this.key);
    }

    public int getKeyLength() {
        return this.key.length;
    }

    public byte[] getKey() {
        return (byte[]) this.key.clone();
    }

    public DataInputStream getKeyAsDataInputStream() {
        return new DataInputStream(new ByteArrayInputStream(this.key));
    }

    public String getKeyBase64() {
        if (this.keyBase64Cache == null) {
            this.keyBase64Cache = Base64.encodeToString(this.key);
        }
        return this.keyBase64Cache;
    }

    public BigInteger getKeyBigInteger() {
        if (this.keyBigIntegerCache == null) {
            this.keyBigIntegerCache = new BigInteger(this.key);
        }
        return this.keyBigIntegerCache;
    }

    public boolean keyEquals(byte[] bArr) {
        return Arrays.equals(this.key, bArr);
    }

    public boolean isSecureEntryPoint() {
        return (this.flags & 1) == 1;
    }
}
