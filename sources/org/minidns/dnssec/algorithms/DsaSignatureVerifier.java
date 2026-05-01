package org.minidns.dnssec.algorithms;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import org.minidns.dnssec.DnssecValidationFailedException;
import org.minidns.record.DNSKEY;
import org.minidns.record.RRSIG;

/* JADX INFO: loaded from: classes4.dex */
class DsaSignatureVerifier extends JavaSecSignatureVerifier {
    private static final int LENGTH = 20;

    DsaSignatureVerifier(String str) throws NoSuchAlgorithmException {
        super("DSA", str);
    }

    @Override // org.minidns.dnssec.algorithms.JavaSecSignatureVerifier
    protected byte[] getSignature(RRSIG rrsig) throws DnssecValidationFailedException.DataMalformedException {
        int i;
        int i2;
        int i3;
        DataInputStream signatureAsDataInputStream = rrsig.getSignatureAsDataInputStream();
        try {
            signatureAsDataInputStream.readByte();
            byte[] bArr = new byte[20];
            signatureAsDataInputStream.readFully(bArr);
            byte b = bArr[0];
            int i4 = 21;
            if (b == 0) {
                i2 = 0;
                while (i2 < 20 && bArr[i2] == 0) {
                    i2++;
                }
                i = 20 - i2;
            } else if (b < 0) {
                i2 = 0;
                i = 21;
            } else {
                i = 20;
                i2 = 0;
            }
            byte[] bArr2 = new byte[20];
            signatureAsDataInputStream.readFully(bArr2);
            byte b2 = bArr2[0];
            if (b2 == 0) {
                i3 = 0;
                while (i3 < 20 && bArr2[i3] == 0) {
                    i3++;
                }
                i4 = 20 - i3;
            } else {
                if (b2 >= 0) {
                    i4 = 20;
                }
                i3 = 0;
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeByte(48);
            dataOutputStream.writeByte(i + i4 + 4);
            dataOutputStream.writeByte(2);
            dataOutputStream.writeByte(i);
            if (i > 20) {
                dataOutputStream.writeByte(0);
            }
            dataOutputStream.write(bArr, i2, 20 - i2);
            dataOutputStream.writeByte(2);
            dataOutputStream.writeByte(i4);
            if (i4 > 20) {
                dataOutputStream.writeByte(0);
            }
            dataOutputStream.write(bArr2, i3, 20 - i3);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new DnssecValidationFailedException.DataMalformedException(e, rrsig.getSignature());
        }
    }

    @Override // org.minidns.dnssec.algorithms.JavaSecSignatureVerifier
    protected PublicKey getPublicKey(DNSKEY dnskey) throws DnssecValidationFailedException.DnssecInvalidKeySpecException, DnssecValidationFailedException.DataMalformedException {
        DataInputStream keyAsDataInputStream = dnskey.getKeyAsDataInputStream();
        try {
            int unsignedByte = keyAsDataInputStream.readUnsignedByte();
            byte[] bArr = new byte[20];
            keyAsDataInputStream.readFully(bArr);
            BigInteger bigInteger = new BigInteger(1, bArr);
            int i = (unsignedByte * 8) + 64;
            byte[] bArr2 = new byte[i];
            keyAsDataInputStream.readFully(bArr2);
            BigInteger bigInteger2 = new BigInteger(1, bArr2);
            byte[] bArr3 = new byte[i];
            keyAsDataInputStream.readFully(bArr3);
            BigInteger bigInteger3 = new BigInteger(1, bArr3);
            byte[] bArr4 = new byte[i];
            keyAsDataInputStream.readFully(bArr4);
            try {
                return getKeyFactory().generatePublic(new DSAPublicKeySpec(new BigInteger(1, bArr4), bigInteger2, bigInteger, bigInteger3));
            } catch (InvalidKeySpecException e) {
                throw new DnssecValidationFailedException.DnssecInvalidKeySpecException(e);
            }
        } catch (IOException e2) {
            throw new DnssecValidationFailedException.DataMalformedException(e2, dnskey.getKey());
        }
    }
}
