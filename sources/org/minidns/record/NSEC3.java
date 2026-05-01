package org.minidns.record;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.minidns.dnslabel.DnsLabel;
import org.minidns.record.Record;
import org.minidns.util.Base32;

/* JADX INFO: loaded from: classes4.dex */
public class NSEC3 extends Data {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final byte FLAG_OPT_OUT = 1;
    private static final Map<Byte, HashAlgorithm> HASH_ALGORITHM_LUT = new HashMap();
    public final byte flags;
    public final HashAlgorithm hashAlgorithm;
    public final byte hashAlgorithmByte;
    public final int iterations;
    private final byte[] nextHashed;
    private String nextHashedBase32Cache;
    private DnsLabel nextHashedDnsLabelCache;
    private final byte[] salt;
    private final byte[] typeBitmap;
    public final List<Record.TYPE> types;

    public enum HashAlgorithm {
        RESERVED(0, "Reserved"),
        SHA1(1, "SHA-1");

        public final String description;
        public final byte value;

        HashAlgorithm(int i, String str) {
            if (i < 0 || i > 255) {
                throw new IllegalArgumentException();
            }
            byte b = (byte) i;
            this.value = b;
            this.description = str;
            NSEC3.HASH_ALGORITHM_LUT.put(Byte.valueOf(b), this);
        }

        public static HashAlgorithm forByte(byte b) {
            return (HashAlgorithm) NSEC3.HASH_ALGORITHM_LUT.get(Byte.valueOf(b));
        }
    }

    public static NSEC3 parse(DataInputStream dataInputStream, int i) throws IOException {
        byte b = dataInputStream.readByte();
        byte b2 = dataInputStream.readByte();
        int unsignedShort = dataInputStream.readUnsignedShort();
        int unsignedByte = dataInputStream.readUnsignedByte();
        byte[] bArr = new byte[unsignedByte];
        if (dataInputStream.read(bArr) != unsignedByte) {
            throw new IOException();
        }
        int unsignedByte2 = dataInputStream.readUnsignedByte();
        byte[] bArr2 = new byte[unsignedByte2];
        if (dataInputStream.read(bArr2) != unsignedByte2) {
            throw new IOException();
        }
        int i2 = i - ((unsignedByte + 6) + unsignedByte2);
        byte[] bArr3 = new byte[i2];
        if (dataInputStream.read(bArr3) != i2) {
            throw new IOException();
        }
        return new NSEC3(b, b2, unsignedShort, bArr, bArr2, NSEC.readTypeBitMap(bArr3));
    }

    private NSEC3(HashAlgorithm hashAlgorithm, byte b, byte b2, int i, byte[] bArr, byte[] bArr2, List<Record.TYPE> list) {
        this.hashAlgorithmByte = b;
        this.hashAlgorithm = hashAlgorithm == null ? HashAlgorithm.forByte(b) : hashAlgorithm;
        this.flags = b2;
        this.iterations = i;
        this.salt = bArr;
        this.nextHashed = bArr2;
        this.types = list;
        this.typeBitmap = NSEC.createTypeBitMap(list);
    }

    public NSEC3(byte b, byte b2, int i, byte[] bArr, byte[] bArr2, List<Record.TYPE> list) {
        this(null, b, b2, i, bArr, bArr2, list);
    }

    public NSEC3(byte b, byte b2, int i, byte[] bArr, byte[] bArr2, Record.TYPE... typeArr) {
        this(null, b, b2, i, bArr, bArr2, Arrays.asList(typeArr));
    }

    @Override // org.minidns.record.Data
    public Record.TYPE getType() {
        return Record.TYPE.NSEC3;
    }

    @Override // org.minidns.record.Data
    public void serialize(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(this.hashAlgorithmByte);
        dataOutputStream.writeByte(this.flags);
        dataOutputStream.writeShort(this.iterations);
        dataOutputStream.writeByte(this.salt.length);
        dataOutputStream.write(this.salt);
        dataOutputStream.writeByte(this.nextHashed.length);
        dataOutputStream.write(this.nextHashed);
        dataOutputStream.write(this.typeBitmap);
    }

    public String toString() {
        StringBuilder sbAppend = new StringBuilder().append(this.hashAlgorithm).append(' ').append((int) this.flags).append(' ').append(this.iterations).append(' ').append(this.salt.length == 0 ? "-" : new BigInteger(1, this.salt).toString(16).toUpperCase()).append(' ').append(Base32.encodeToString(this.nextHashed));
        Iterator<Record.TYPE> it = this.types.iterator();
        while (it.hasNext()) {
            sbAppend.append(' ').append(it.next());
        }
        return sbAppend.toString();
    }

    public byte[] getSalt() {
        return (byte[]) this.salt.clone();
    }

    public int getSaltLength() {
        return this.salt.length;
    }

    public byte[] getNextHashed() {
        return (byte[]) this.nextHashed.clone();
    }

    public String getNextHashedBase32() {
        if (this.nextHashedBase32Cache == null) {
            this.nextHashedBase32Cache = Base32.encodeToString(this.nextHashed);
        }
        return this.nextHashedBase32Cache;
    }

    public DnsLabel getNextHashedDnsLabel() {
        if (this.nextHashedDnsLabelCache == null) {
            this.nextHashedDnsLabelCache = DnsLabel.from(getNextHashedBase32());
        }
        return this.nextHashedDnsLabelCache;
    }

    public void copySaltInto(byte[] bArr, int i) {
        byte[] bArr2 = this.salt;
        System.arraycopy(bArr2, 0, bArr, i, bArr2.length);
    }
}
