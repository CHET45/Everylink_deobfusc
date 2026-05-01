package org.minidns.record;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.minidns.record.Record;

/* JADX INFO: loaded from: classes4.dex */
public class TLSA extends Data {
    public final CertUsage certUsage;
    public final byte certUsageByte;
    private final byte[] certificateAssociation;
    public final MatchingType matchingType;
    public final byte matchingTypeByte;
    public final Selector selector;
    public final byte selectorByte;
    private static final Map<Byte, CertUsage> CERT_USAGE_LUT = new HashMap();
    private static final Map<Byte, Selector> SELECTOR_LUT = new HashMap();
    private static final Map<Byte, MatchingType> MATCHING_TYPE_LUT = new HashMap();

    static {
        CertUsage.values();
        Selector.values();
        MatchingType.values();
    }

    public enum CertUsage {
        caConstraint((byte) 0),
        serviceCertificateConstraint((byte) 1),
        trustAnchorAssertion((byte) 2),
        domainIssuedCertificate((byte) 3);

        public final byte byteValue;

        CertUsage(byte b) {
            this.byteValue = b;
            TLSA.CERT_USAGE_LUT.put(Byte.valueOf(b), this);
        }
    }

    public enum Selector {
        fullCertificate((byte) 0),
        subjectPublicKeyInfo((byte) 1);

        public final byte byteValue;

        Selector(byte b) {
            this.byteValue = b;
            TLSA.SELECTOR_LUT.put(Byte.valueOf(b), this);
        }
    }

    public enum MatchingType {
        noHash((byte) 0),
        sha256((byte) 1),
        sha512((byte) 2);

        public final byte byteValue;

        MatchingType(byte b) {
            this.byteValue = b;
            TLSA.MATCHING_TYPE_LUT.put(Byte.valueOf(b), this);
        }
    }

    public static TLSA parse(DataInputStream dataInputStream, int i) throws IOException {
        byte b = dataInputStream.readByte();
        byte b2 = dataInputStream.readByte();
        byte b3 = dataInputStream.readByte();
        int i2 = i - 3;
        byte[] bArr = new byte[i2];
        if (dataInputStream.read(bArr) != i2) {
            throw new IOException();
        }
        return new TLSA(b, b2, b3, bArr);
    }

    TLSA(byte b, byte b2, byte b3, byte[] bArr) {
        this.certUsageByte = b;
        this.certUsage = CERT_USAGE_LUT.get(Byte.valueOf(b));
        this.selectorByte = b2;
        this.selector = SELECTOR_LUT.get(Byte.valueOf(b2));
        this.matchingTypeByte = b3;
        this.matchingType = MATCHING_TYPE_LUT.get(Byte.valueOf(b3));
        this.certificateAssociation = bArr;
    }

    @Override // org.minidns.record.Data
    public Record.TYPE getType() {
        return Record.TYPE.TLSA;
    }

    @Override // org.minidns.record.Data
    public void serialize(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeByte(this.certUsageByte);
        dataOutputStream.writeByte(this.selectorByte);
        dataOutputStream.writeByte(this.matchingTypeByte);
        dataOutputStream.write(this.certificateAssociation);
    }

    public String toString() {
        return ((int) this.certUsageByte) + ' ' + ((int) this.selectorByte) + ' ' + ((int) this.matchingTypeByte) + ' ' + new BigInteger(1, this.certificateAssociation).toString(16);
    }

    public byte[] getCertificateAssociation() {
        return (byte[]) this.certificateAssociation.clone();
    }

    public boolean certificateAssociationEquals(byte[] bArr) {
        return Arrays.equals(this.certificateAssociation, bArr);
    }
}
