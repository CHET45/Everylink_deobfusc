package org.minidns.record;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import org.minidns.record.NSEC3;
import org.minidns.record.Record;

/* JADX INFO: loaded from: classes4.dex */
public class NSEC3PARAM extends Data {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public final byte flags;
    public final NSEC3.HashAlgorithm hashAlgorithm;
    public final byte hashAlgorithmByte;
    public final int iterations;
    private final byte[] salt;

    public static NSEC3PARAM parse(DataInputStream dataInputStream) throws IOException {
        byte b = dataInputStream.readByte();
        byte b2 = dataInputStream.readByte();
        int unsignedShort = dataInputStream.readUnsignedShort();
        int unsignedByte = dataInputStream.readUnsignedByte();
        byte[] bArr = new byte[unsignedByte];
        if (dataInputStream.read(bArr) == unsignedByte || unsignedByte == 0) {
            return new NSEC3PARAM(b, b2, unsignedShort, bArr);
        }
        throw new IOException();
    }

    private NSEC3PARAM(NSEC3.HashAlgorithm hashAlgorithm, byte b, byte b2, int i, byte[] bArr) {
        this.hashAlgorithmByte = b;
        this.hashAlgorithm = hashAlgorithm == null ? NSEC3.HashAlgorithm.forByte(b) : hashAlgorithm;
        this.flags = b2;
        this.iterations = i;
        this.salt = bArr;
    }

    NSEC3PARAM(byte b, byte b2, int i, byte[] bArr) {
        this(null, b, b2, i, bArr);
    }

    @Override // org.minidns.record.Data
    public Record.TYPE getType() {
        return Record.TYPE.NSEC3PARAM;
    }

    @Override // org.minidns.record.Data
    public void serialize(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(this.hashAlgorithmByte);
        dataOutputStream.writeByte(this.flags);
        dataOutputStream.writeShort(this.iterations);
        dataOutputStream.writeByte(this.salt.length);
        dataOutputStream.write(this.salt);
    }

    public String toString() {
        return this.hashAlgorithm + ' ' + ((int) this.flags) + ' ' + this.iterations + ' ' + (this.salt.length == 0 ? "-" : new BigInteger(1, this.salt).toString(16).toUpperCase());
    }

    public int getSaltLength() {
        return this.salt.length;
    }
}
