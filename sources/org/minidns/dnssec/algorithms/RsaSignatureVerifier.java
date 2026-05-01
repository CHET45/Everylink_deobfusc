package org.minidns.dnssec.algorithms;

import com.aivox.common.util.encrypt.RSAUtil;
import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import org.minidns.dnssec.DnssecValidationFailedException;
import org.minidns.record.DNSKEY;
import org.minidns.record.RRSIG;

/* JADX INFO: loaded from: classes4.dex */
class RsaSignatureVerifier extends JavaSecSignatureVerifier {
    RsaSignatureVerifier(String str) throws NoSuchAlgorithmException {
        super(RSAUtil.RSA, str);
    }

    @Override // org.minidns.dnssec.algorithms.JavaSecSignatureVerifier
    protected PublicKey getPublicKey(DNSKEY dnskey) throws DnssecValidationFailedException.DnssecInvalidKeySpecException, DnssecValidationFailedException.DataMalformedException {
        int i;
        DataInputStream keyAsDataInputStream = dnskey.getKeyAsDataInputStream();
        try {
            int unsignedByte = keyAsDataInputStream.readUnsignedByte();
            if (unsignedByte == 0) {
                unsignedByte = keyAsDataInputStream.readUnsignedShort();
                i = 3;
            } else {
                i = 1;
            }
            byte[] bArr = new byte[unsignedByte];
            keyAsDataInputStream.readFully(bArr);
            int i2 = i + unsignedByte;
            BigInteger bigInteger = new BigInteger(1, bArr);
            byte[] bArr2 = new byte[dnskey.getKeyLength() - i2];
            keyAsDataInputStream.readFully(bArr2);
            try {
                return getKeyFactory().generatePublic(new RSAPublicKeySpec(new BigInteger(1, bArr2), bigInteger));
            } catch (InvalidKeySpecException e) {
                throw new DnssecValidationFailedException.DnssecInvalidKeySpecException(e);
            }
        } catch (IOException e2) {
            throw new DnssecValidationFailedException.DataMalformedException(e2, dnskey.getKey());
        }
    }

    @Override // org.minidns.dnssec.algorithms.JavaSecSignatureVerifier
    protected byte[] getSignature(RRSIG rrsig) {
        return rrsig.getSignature();
    }
}
