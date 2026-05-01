package org.minidns.dnssec.algorithms;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;
import java.security.spec.InvalidKeySpecException;
import org.minidns.dnssec.DnssecValidationFailedException;
import org.minidns.record.DNSKEY;
import org.minidns.record.RRSIG;

/* JADX INFO: loaded from: classes4.dex */
abstract class EcdsaSignatureVerifier extends JavaSecSignatureVerifier {
    private final int length;
    private final ECParameterSpec spec;

    EcdsaSignatureVerifier(BigInteger[] bigIntegerArr, int i, String str) throws NoSuchAlgorithmException {
        this(new ECParameterSpec(new EllipticCurve(new ECFieldFp(bigIntegerArr[0]), bigIntegerArr[1], bigIntegerArr[2]), new ECPoint(bigIntegerArr[3], bigIntegerArr[4]), bigIntegerArr[5], 1), i, str);
    }

    EcdsaSignatureVerifier(ECParameterSpec eCParameterSpec, int i, String str) throws NoSuchAlgorithmException {
        super("EC", str);
        this.length = i;
        this.spec = eCParameterSpec;
    }

    @Override // org.minidns.dnssec.algorithms.JavaSecSignatureVerifier
    protected byte[] getSignature(RRSIG rrsig) throws DnssecValidationFailedException.DataMalformedException {
        DataInputStream signatureAsDataInputStream = rrsig.getSignatureAsDataInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            byte[] bArr = new byte[this.length];
            signatureAsDataInputStream.readFully(bArr);
            int i = bArr[0] < 0 ? this.length + 1 : this.length;
            byte[] bArr2 = new byte[this.length];
            signatureAsDataInputStream.readFully(bArr2);
            int i2 = bArr2[0] < 0 ? this.length + 1 : this.length;
            dataOutputStream.writeByte(48);
            dataOutputStream.writeByte(i + i2 + 4);
            dataOutputStream.writeByte(2);
            dataOutputStream.writeByte(i);
            if (i > this.length) {
                dataOutputStream.writeByte(0);
            }
            dataOutputStream.write(bArr);
            dataOutputStream.writeByte(2);
            dataOutputStream.writeByte(i2);
            if (i2 > this.length) {
                dataOutputStream.writeByte(0);
            }
            dataOutputStream.write(bArr2);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new DnssecValidationFailedException.DataMalformedException(e, rrsig.getSignature());
        }
    }

    @Override // org.minidns.dnssec.algorithms.JavaSecSignatureVerifier
    protected PublicKey getPublicKey(DNSKEY dnskey) throws DnssecValidationFailedException.DnssecInvalidKeySpecException, DnssecValidationFailedException.DataMalformedException {
        DataInputStream keyAsDataInputStream = dnskey.getKeyAsDataInputStream();
        try {
            byte[] bArr = new byte[this.length];
            keyAsDataInputStream.readFully(bArr);
            BigInteger bigInteger = new BigInteger(1, bArr);
            byte[] bArr2 = new byte[this.length];
            keyAsDataInputStream.readFully(bArr2);
            try {
                return getKeyFactory().generatePublic(new ECPublicKeySpec(new ECPoint(bigInteger, new BigInteger(1, bArr2)), this.spec));
            } catch (InvalidKeySpecException e) {
                throw new DnssecValidationFailedException.DnssecInvalidKeySpecException(e);
            }
        } catch (IOException e2) {
            throw new DnssecValidationFailedException.DataMalformedException(e2, dnskey.getKey());
        }
    }

    public static class P256SHA256 extends EcdsaSignatureVerifier {
        private static BigInteger[] SPEC = {new BigInteger("FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFF", 16), new BigInteger("FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFC", 16), new BigInteger("5AC635D8AA3A93E7B3EBBD55769886BC651D06B0CC53B0F63BCE3C3E27D2604B", 16), new BigInteger("6B17D1F2E12C4247F8BCE6E563A440F277037D812DEB33A0F4A13945D898C296", 16), new BigInteger("4FE342E2FE1A7F9B8EE7EB4A7C0F9E162BCE33576B315ECECBB6406837BF51F5", 16), new BigInteger("FFFFFFFF00000000FFFFFFFFFFFFFFFFBCE6FAADA7179E84F3B9CAC2FC632551", 16)};

        P256SHA256() throws NoSuchAlgorithmException {
            super(SPEC, 32, "SHA256withECDSA");
        }
    }

    public static class P384SHA284 extends EcdsaSignatureVerifier {
        private static BigInteger[] SPEC = {new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFF0000000000000000FFFFFFFF", 16), new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFF0000000000000000FFFFFFFC", 16), new BigInteger("B3312FA7E23EE7E4988E056BE3F82D19181D9C6EFE8141120314088F5013875AC656398D8A2ED19D2A85C8EDD3EC2AEF", 16), new BigInteger("AA87CA22BE8B05378EB1C71EF320AD746E1D3B628BA79B9859F741E082542A385502F25DBF55296C3A545E3872760AB7", 16), new BigInteger("3617DE4A96262C6F5D9E98BF9292DC29F8F41DBD289A147CE9DA3113B5F0B8C00A60B1CE1D7E819D7A431D7C90EA0E5F", 16), new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFC7634D81F4372DDF581A0DB248B0A77AECEC196ACCC52973", 16)};

        P384SHA284() throws NoSuchAlgorithmException {
            super(SPEC, 48, "SHA384withECDSA");
        }
    }
}
