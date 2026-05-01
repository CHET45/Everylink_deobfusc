package org.minidns.record;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.minidns.constants.DnssecConstants;
import org.minidns.dnsname.DnsName;
import org.minidns.record.Record;
import org.minidns.util.Base64;

/* JADX INFO: loaded from: classes4.dex */
public class RRSIG extends Data {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public final DnssecConstants.SignatureAlgorithm algorithm;
    public final byte algorithmByte;
    private transient String base64SignatureCache;
    public final int keyTag;
    public final byte labels;
    public final long originalTtl;
    private final byte[] signature;
    public final Date signatureExpiration;
    public final Date signatureInception;
    public final DnsName signerName;
    public final Record.TYPE typeCovered;

    public static RRSIG parse(DataInputStream dataInputStream, byte[] bArr, int i) throws IOException {
        Record.TYPE type = Record.TYPE.getType(dataInputStream.readUnsignedShort());
        byte b = dataInputStream.readByte();
        byte b2 = dataInputStream.readByte();
        long j = ((long) dataInputStream.readInt()) & 4294967295L;
        Date date = new Date((((long) dataInputStream.readInt()) & 4294967295L) * 1000);
        Date date2 = new Date((4294967295L & ((long) dataInputStream.readInt())) * 1000);
        int unsignedShort = dataInputStream.readUnsignedShort();
        DnsName dnsName = DnsName.parse(dataInputStream, bArr);
        int size = (i - dnsName.size()) - 18;
        byte[] bArr2 = new byte[size];
        if (dataInputStream.read(bArr2) != size) {
            throw new IOException();
        }
        return new RRSIG(type, null, b, b2, j, date, date2, unsignedShort, dnsName, bArr2);
    }

    private RRSIG(Record.TYPE type, DnssecConstants.SignatureAlgorithm signatureAlgorithm, byte b, byte b2, long j, Date date, Date date2, int i, DnsName dnsName, byte[] bArr) {
        this.typeCovered = type;
        this.algorithmByte = b;
        this.algorithm = signatureAlgorithm == null ? DnssecConstants.SignatureAlgorithm.forByte(b) : signatureAlgorithm;
        this.labels = b2;
        this.originalTtl = j;
        this.signatureExpiration = date;
        this.signatureInception = date2;
        this.keyTag = i;
        this.signerName = dnsName;
        this.signature = bArr;
    }

    public RRSIG(Record.TYPE type, int i, byte b, long j, Date date, Date date2, int i2, DnsName dnsName, byte[] bArr) {
        this(type, null, (byte) i, b, j, date, date2, i2, dnsName, bArr);
    }

    public RRSIG(Record.TYPE type, int i, byte b, long j, Date date, Date date2, int i2, String str, byte[] bArr) {
        this(type, null, (byte) i, b, j, date, date2, i2, DnsName.from(str), bArr);
    }

    public RRSIG(Record.TYPE type, DnssecConstants.SignatureAlgorithm signatureAlgorithm, byte b, long j, Date date, Date date2, int i, DnsName dnsName, byte[] bArr) {
        this(type, signatureAlgorithm.number, b, j, date, date2, i, dnsName, bArr);
    }

    public RRSIG(Record.TYPE type, DnssecConstants.SignatureAlgorithm signatureAlgorithm, byte b, long j, Date date, Date date2, int i, String str, byte[] bArr) {
        this(type, signatureAlgorithm.number, b, j, date, date2, i, DnsName.from(str), bArr);
    }

    public byte[] getSignature() {
        return (byte[]) this.signature.clone();
    }

    public DataInputStream getSignatureAsDataInputStream() {
        return new DataInputStream(new ByteArrayInputStream(this.signature));
    }

    public int getSignatureLength() {
        return this.signature.length;
    }

    public String getSignatureBase64() {
        if (this.base64SignatureCache == null) {
            this.base64SignatureCache = Base64.encodeToString(this.signature);
        }
        return this.base64SignatureCache;
    }

    @Override // org.minidns.record.Data
    public Record.TYPE getType() {
        return Record.TYPE.RRSIG;
    }

    @Override // org.minidns.record.Data
    public void serialize(DataOutputStream dataOutputStream) throws IOException {
        writePartialSignature(dataOutputStream);
        dataOutputStream.write(this.signature);
    }

    public void writePartialSignature(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeShort(this.typeCovered.getValue());
        dataOutputStream.writeByte(this.algorithmByte);
        dataOutputStream.writeByte(this.labels);
        dataOutputStream.writeInt((int) this.originalTtl);
        dataOutputStream.writeInt((int) (this.signatureExpiration.getTime() / 1000));
        dataOutputStream.writeInt((int) (this.signatureInception.getTime() / 1000));
        dataOutputStream.writeShort(this.keyTag);
        this.signerName.writeToStream(dataOutputStream);
    }

    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return this.typeCovered + ' ' + this.algorithm + ' ' + ((int) this.labels) + ' ' + this.originalTtl + ' ' + simpleDateFormat.format(this.signatureExpiration) + ' ' + simpleDateFormat.format(this.signatureInception) + ' ' + this.keyTag + ' ' + ((CharSequence) this.signerName) + ". " + getSignatureBase64();
    }
}
