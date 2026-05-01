package org.minidns.record;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import org.minidns.constants.DnssecConstants;

/* JADX INFO: loaded from: classes4.dex */
public abstract class DelegatingDnssecRR extends Data {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public final DnssecConstants.SignatureAlgorithm algorithm;
    public final byte algorithmByte;
    protected final byte[] digest;
    private transient BigInteger digestBigIntCache;
    private transient String digestHexCache;
    public final DnssecConstants.DigestAlgorithm digestType;
    public final byte digestTypeByte;
    public final int keyTag;

    protected static SharedData parseSharedData(DataInputStream dataInputStream, int i) throws IOException {
        int unsignedShort = dataInputStream.readUnsignedShort();
        byte b = dataInputStream.readByte();
        byte b2 = dataInputStream.readByte();
        int i2 = i - 4;
        byte[] bArr = new byte[i2];
        if (dataInputStream.read(bArr) != i2) {
            throw new IOException();
        }
        return new SharedData(unsignedShort, b, b2, bArr);
    }

    protected static final class SharedData {
        protected final byte algorithm;
        protected final byte[] digest;
        protected final byte digestType;
        protected final int keyTag;

        private SharedData(int i, byte b, byte b2, byte[] bArr) {
            this.keyTag = i;
            this.algorithm = b;
            this.digestType = b2;
            this.digest = bArr;
        }
    }

    protected DelegatingDnssecRR(int i, DnssecConstants.SignatureAlgorithm signatureAlgorithm, byte b, DnssecConstants.DigestAlgorithm digestAlgorithm, byte b2, byte[] bArr) {
        this.keyTag = i;
        this.algorithmByte = b;
        this.algorithm = signatureAlgorithm == null ? DnssecConstants.SignatureAlgorithm.forByte(b) : signatureAlgorithm;
        this.digestTypeByte = b2;
        this.digestType = digestAlgorithm == null ? DnssecConstants.DigestAlgorithm.forByte(b2) : digestAlgorithm;
        this.digest = bArr;
    }

    protected DelegatingDnssecRR(int i, byte b, byte b2, byte[] bArr) {
        this(i, null, b, null, b2, bArr);
    }

    protected DelegatingDnssecRR(int i, DnssecConstants.SignatureAlgorithm signatureAlgorithm, DnssecConstants.DigestAlgorithm digestAlgorithm, byte[] bArr) {
        this(i, signatureAlgorithm, signatureAlgorithm.number, digestAlgorithm, digestAlgorithm.value, bArr);
    }

    protected DelegatingDnssecRR(int i, DnssecConstants.SignatureAlgorithm signatureAlgorithm, byte b, byte[] bArr) {
        this(i, signatureAlgorithm, signatureAlgorithm.number, null, b, bArr);
    }

    @Override // org.minidns.record.Data
    public void serialize(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeShort(this.keyTag);
        dataOutputStream.writeByte(this.algorithmByte);
        dataOutputStream.writeByte(this.digestTypeByte);
        dataOutputStream.write(this.digest);
    }

    public String toString() {
        return this.keyTag + ' ' + this.algorithm + ' ' + this.digestType + ' ' + new BigInteger(1, this.digest).toString(16).toUpperCase();
    }

    public BigInteger getDigestBigInteger() {
        if (this.digestBigIntCache == null) {
            this.digestBigIntCache = new BigInteger(1, this.digest);
        }
        return this.digestBigIntCache;
    }

    public String getDigestHex() {
        if (this.digestHexCache == null) {
            this.digestHexCache = getDigestBigInteger().toString(16).toUpperCase();
        }
        return this.digestHexCache;
    }

    public boolean digestEquals(byte[] bArr) {
        return Arrays.equals(this.digest, bArr);
    }
}
