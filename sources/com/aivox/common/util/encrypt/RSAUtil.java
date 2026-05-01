package com.aivox.common.util.encrypt;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/* JADX INFO: loaded from: classes.dex */
public class RSAUtil {
    public static final int DEFAULT_BUFFERSIZE = 245;
    public static final int DEFAULT_KEY_SIZE = 2048;
    public static final byte[] DEFAULT_SPLIT = "#PART#".getBytes();
    public static final String ECB_NO_PADDING = "RSA/None/NoPadding";
    public static final String ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";
    public static final String RSA = "RSA";

    /* JADX WARN: Removed duplicated region for block: B:6:0x0008  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte asc_to_bcd(byte r2) {
        /*
            r0 = 48
            if (r2 < r0) goto Lb
            r1 = 57
            if (r2 > r1) goto Lb
        L8:
            int r2 = r2 - r0
        L9:
            byte r2 = (byte) r2
            goto L21
        Lb:
            r1 = 65
            if (r2 < r1) goto L16
            r1 = 70
            if (r2 > r1) goto L16
            int r2 = r2 + (-55)
            goto L9
        L16:
            r1 = 97
            if (r2 < r1) goto L8
            r1 = 102(0x66, float:1.43E-43)
            if (r2 > r1) goto L8
            int r2 = r2 + (-87)
            goto L9
        L21:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aivox.common.util.encrypt.RSAUtil.asc_to_bcd(byte):byte");
    }

    public static KeyPair generateRSAKeyPair(int i) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
            keyPairGenerator.initialize(i);
            return keyPairGenerator.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] encryptByPublicKey(String str, byte[] bArr, byte[] bArr2) throws Exception {
        PublicKey publicKeyGeneratePublic = KeyFactory.getInstance(RSA).generatePublic(new X509EncodedKeySpec(bArr2));
        if (str.equalsIgnoreCase("java")) {
            Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
            cipher.init(1, publicKeyGeneratePublic);
            return cipher.doFinal(bArr);
        }
        Cipher cipher2 = Cipher.getInstance(ECB_NO_PADDING);
        cipher2.init(1, publicKeyGeneratePublic);
        return cipher2.doFinal(bArr);
    }

    public static byte[] decryptByPublicKey(String str, byte[] bArr, byte[] bArr2) throws Exception {
        PublicKey publicKeyGeneratePublic = KeyFactory.getInstance(RSA).generatePublic(new X509EncodedKeySpec(bArr2));
        if (str.equalsIgnoreCase("java")) {
            Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
            cipher.init(2, publicKeyGeneratePublic);
            return cipher.doFinal(bArr);
        }
        Cipher cipher2 = Cipher.getInstance(RSA);
        cipher2.init(2, publicKeyGeneratePublic);
        return cipher2.doFinal(bArr);
    }

    public static byte[] encryptByPrivateKey(String str, byte[] bArr, byte[] bArr2) throws Exception {
        PrivateKey privateKeyGeneratePrivate = KeyFactory.getInstance(RSA).generatePrivate(new PKCS8EncodedKeySpec(bArr2));
        if (str.equalsIgnoreCase("java")) {
            Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
            cipher.init(1, privateKeyGeneratePrivate);
            return cipher.doFinal(bArr);
        }
        Cipher cipher2 = Cipher.getInstance(ECB_NO_PADDING);
        cipher2.init(1, privateKeyGeneratePrivate);
        return cipher2.doFinal(bArr);
    }

    public static byte[] decryptByPrivateKey(String str, byte[] bArr, byte[] bArr2) throws Exception {
        PrivateKey privateKeyGeneratePrivate = KeyFactory.getInstance(RSA).generatePrivate(new PKCS8EncodedKeySpec(bArr2));
        if (str.equalsIgnoreCase("java")) {
            Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
            cipher.init(2, privateKeyGeneratePrivate);
            return cipher.doFinal(bArr);
        }
        Cipher cipher2 = Cipher.getInstance(RSA);
        cipher2.init(2, privateKeyGeneratePrivate);
        return cipher2.doFinal(bArr);
    }

    public static String decryptByPrivateKey(String str, RSAPrivateKey rSAPrivateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(2, rSAPrivateKey);
        int iBitLength = rSAPrivateKey.getModulus().bitLength() / 8;
        byte[] bytes = str.getBytes();
        byte[] bArrASCII_To_BCD = ASCII_To_BCD(bytes, bytes.length);
        System.err.println(bArrASCII_To_BCD.length);
        String str2 = "";
        for (byte[] bArr : splitArray(bArrASCII_To_BCD, iBitLength)) {
            str2 = str2 + new String(cipher.doFinal(bArr));
        }
        return str2;
    }

    public static byte[] ASCII_To_BCD(byte[] bArr, int i) {
        byte bAsc_to_bcd;
        byte[] bArr2 = new byte[i / 2];
        int i2 = 0;
        for (int i3 = 0; i3 < (i + 1) / 2; i3++) {
            int i4 = i2 + 1;
            bArr2[i3] = asc_to_bcd(bArr[i2]);
            if (i4 >= i) {
                i2 = i4;
                bAsc_to_bcd = 0;
            } else {
                i2 += 2;
                bAsc_to_bcd = asc_to_bcd(bArr[i4]);
            }
            bArr2[i3] = (byte) (bAsc_to_bcd + (bArr2[i3] << 4));
        }
        return bArr2;
    }

    public static String bcd2Str(byte[] bArr) {
        char[] cArr = new char[bArr.length * 2];
        for (int i = 0; i < bArr.length; i++) {
            byte b = bArr[i];
            char c = (char) (((b & 240) >> 4) & 15);
            int i2 = i * 2;
            cArr[i2] = (char) (c > '\t' ? c + '7' : c + '0');
            char c2 = (char) (b & 15);
            cArr[i2 + 1] = (char) (c2 > '\t' ? c2 + '7' : c2 + '0');
        }
        return new String(cArr);
    }

    public static String[] splitString(String str, int i) {
        String strSubstring;
        int length = str.length() / i;
        int length2 = str.length() % i;
        int i2 = length + (length2 != 0 ? 1 : 0);
        String[] strArr = new String[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            if (i3 == i2 - 1 && length2 != 0) {
                int i4 = i3 * i;
                strSubstring = str.substring(i4, i4 + length2);
            } else {
                int i5 = i3 * i;
                strSubstring = str.substring(i5, i5 + i);
            }
            strArr[i3] = strSubstring;
        }
        return strArr;
    }

    public static byte[][] splitArray(byte[] bArr, int i) {
        int length = bArr.length / i;
        int length2 = bArr.length % i;
        int i2 = length + (length2 != 0 ? 1 : 0);
        byte[][] bArr2 = new byte[i2][];
        for (int i3 = 0; i3 < i2; i3++) {
            byte[] bArr3 = new byte[i];
            if (i3 == i2 - 1 && length2 != 0) {
                System.arraycopy(bArr, i3 * i, bArr3, 0, length2);
            } else {
                System.arraycopy(bArr, i3 * i, bArr3, 0, i);
            }
            bArr2[i3] = bArr3;
        }
        return bArr2;
    }
}
