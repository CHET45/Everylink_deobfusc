package com.aivox.common.util.encrypt;

import android.util.Base64;
import com.aivox.common.util.encrypt.AESUtil;
import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class DESUtil {
    private static final String ALGORITHM = "DES";
    private static final String HEX = "0123456789ABCDEF";
    private static final String IVPARAMETERSPEC = "01020304";
    private static final String SHA1PRNG = "SHA1PRNG";
    private static final String TRANSFORMATION = "DES/CBC/PKCS5Padding";

    public static String desEncrypt(String str, String str2) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(1, SecretKeyFactory.getInstance(ALGORITHM).generateSecret(new DESKeySpec(str2.getBytes("UTF-8"))), new IvParameterSpec(str2.getBytes("UTF-8")));
        return new String(Base64.encode(cipher.doFinal(str.getBytes()), 0)).trim();
    }

    public static String desDecrypt(String str, String str2) throws Exception {
        byte[] bArrDecode = Base64.decode(str.getBytes(), 0);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(2, SecretKeyFactory.getInstance(ALGORITHM).generateSecret(new DESKeySpec(str2.getBytes("UTF-8"))), new IvParameterSpec(str2.getBytes("UTF-8")));
        return new String(cipher.doFinal(bArrDecode));
    }

    public static String generateKey() {
        try {
            byte[] bArr = new byte[20];
            SecureRandom.getInstance(SHA1PRNG).nextBytes(bArr);
            return toHex(bArr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Key getRawKey(String str) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        SecureRandom secureRandom = SecureRandom.getInstance(SHA1PRNG, new AESUtil.CryptoProvider());
        secureRandom.setSeed(str.getBytes());
        keyGenerator.init(64, secureRandom);
        return new SecretKeySpec(keyGenerator.generateKey().getEncoded(), ALGORITHM);
    }

    private static Key getRawKey2(String str) throws Exception {
        return SecretKeyFactory.getInstance(ALGORITHM).generateSecret(new DESKeySpec(str.getBytes()));
    }

    public static String toHex(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        for (byte b : bArr) {
            appendHex(stringBuffer, b);
        }
        return stringBuffer.toString();
    }

    private static void appendHex(StringBuffer stringBuffer, byte b) {
        stringBuffer.append(HEX.charAt((b >> 4) & 15)).append(HEX.charAt(b & 15));
    }

    public static String encode(String str, String str2) {
        return encode(str, str2.getBytes());
    }

    public static String encode(String str, byte[] bArr) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(1, getRawKey(str), new IvParameterSpec(IVPARAMETERSPEC.getBytes()));
            return Base64.encodeToString(cipher.doFinal(bArr), 0);
        } catch (Exception unused) {
            return null;
        }
    }

    public static String decode(String str, String str2) {
        return decode(str, Base64.decode(str2, 0));
    }

    public static String decode(String str, byte[] bArr) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(2, getRawKey(str), new IvParameterSpec(IVPARAMETERSPEC.getBytes()));
            return new String(cipher.doFinal(bArr));
        } catch (Exception unused) {
            return null;
        }
    }
}
