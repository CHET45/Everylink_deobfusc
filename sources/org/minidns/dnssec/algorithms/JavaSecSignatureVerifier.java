package org.minidns.dnssec.algorithms;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import org.minidns.dnssec.DnssecValidationFailedException;
import org.minidns.dnssec.SignatureVerifier;
import org.minidns.record.DNSKEY;
import org.minidns.record.RRSIG;

/* JADX INFO: loaded from: classes4.dex */
public abstract class JavaSecSignatureVerifier implements SignatureVerifier {
    private final KeyFactory keyFactory;
    private final String signatureAlgorithm;

    protected abstract PublicKey getPublicKey(DNSKEY dnskey) throws DnssecValidationFailedException.DnssecInvalidKeySpecException, DnssecValidationFailedException.DataMalformedException;

    protected abstract byte[] getSignature(RRSIG rrsig) throws DnssecValidationFailedException.DataMalformedException;

    public JavaSecSignatureVerifier(String str, String str2) throws NoSuchAlgorithmException {
        this.keyFactory = KeyFactory.getInstance(str);
        this.signatureAlgorithm = str2;
        Signature.getInstance(str2);
    }

    public KeyFactory getKeyFactory() {
        return this.keyFactory;
    }

    @Override // org.minidns.dnssec.SignatureVerifier
    public boolean verify(byte[] bArr, RRSIG rrsig, DNSKEY dnskey) throws DnssecValidationFailedException {
        try {
            PublicKey publicKey = getPublicKey(dnskey);
            Signature signature = Signature.getInstance(this.signatureAlgorithm);
            signature.initVerify(publicKey);
            signature.update(bArr);
            return signature.verify(getSignature(rrsig));
        } catch (ArithmeticException e) {
            e = e;
            throw new DnssecValidationFailedException("Validating signature failed", e);
        } catch (InvalidKeyException e2) {
            e = e2;
            throw new DnssecValidationFailedException("Validating signature failed", e);
        } catch (NoSuchAlgorithmException e3) {
            throw new AssertionError(e3);
        } catch (SignatureException e4) {
            e = e4;
            throw new DnssecValidationFailedException("Validating signature failed", e);
        }
    }
}
