package org.minidns.dnssec.algorithms;

import java.io.DataInputStream;
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
class EcgostSignatureVerifier extends JavaSecSignatureVerifier {
    private static final int LENGTH = 32;
    private static final ECParameterSpec SPEC = new ECParameterSpec(new EllipticCurve(new ECFieldFp(new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFD97", 16)), new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFD94", 16), new BigInteger("A6", 16)), new ECPoint(BigInteger.ONE, new BigInteger("8D91E471E0989CDA27DF505A453F2B7635294F2DDF23E3B122ACC99C9E9F1E14", 16)), new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF6C611070995AD10045841B09B761B893", 16), 1);

    EcgostSignatureVerifier() throws NoSuchAlgorithmException {
        super("ECGOST3410", "GOST3411withECGOST3410");
    }

    @Override // org.minidns.dnssec.algorithms.JavaSecSignatureVerifier
    protected byte[] getSignature(RRSIG rrsig) {
        return rrsig.getSignature();
    }

    @Override // org.minidns.dnssec.algorithms.JavaSecSignatureVerifier
    protected PublicKey getPublicKey(DNSKEY dnskey) throws DnssecValidationFailedException.DnssecInvalidKeySpecException, DnssecValidationFailedException.DataMalformedException {
        DataInputStream keyAsDataInputStream = dnskey.getKeyAsDataInputStream();
        try {
            byte[] bArr = new byte[32];
            keyAsDataInputStream.readFully(bArr);
            reverse(bArr);
            BigInteger bigInteger = new BigInteger(1, bArr);
            byte[] bArr2 = new byte[32];
            keyAsDataInputStream.readFully(bArr2);
            reverse(bArr2);
            try {
                return getKeyFactory().generatePublic(new ECPublicKeySpec(new ECPoint(bigInteger, new BigInteger(1, bArr2)), SPEC));
            } catch (InvalidKeySpecException e) {
                throw new DnssecValidationFailedException.DnssecInvalidKeySpecException(e);
            }
        } catch (IOException e2) {
            throw new DnssecValidationFailedException.DataMalformedException(e2, dnskey.getKey());
        }
    }

    private static void reverse(byte[] bArr) {
        for (int i = 0; i < bArr.length / 2; i++) {
            int length = (bArr.length - i) - 1;
            byte b = bArr[i];
            bArr[i] = bArr[length];
            bArr[length] = b;
        }
    }
}
