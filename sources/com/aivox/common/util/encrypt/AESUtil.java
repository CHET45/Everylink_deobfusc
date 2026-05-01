package com.aivox.common.util.encrypt;

import android.text.TextUtils;
import android.util.Base64;
import androidx.exifinterface.media.ExifInterface;
import com.aivox.base.util.LogUtil;
import java.security.Provider;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class AESUtil {
    private static final String AES = "AES";
    private static final String CBC_PKCS5_PADDING = "AES/ECB/PKCS5Padding";
    private static final String HEX = "0123456789ABCDEF";
    private static final String SHA1PRNG = "SHA1PRNG";

    private static class HOLDER {
        private static AESUtil instance = new AESUtil();

        private HOLDER() {
        }
    }

    public static AESUtil getInstance() {
        return HOLDER.instance;
    }

    public static String generateKey() {
        try {
            byte[] bArr = new byte[16];
            SecureRandom.getInstance(SHA1PRNG).nextBytes(bArr);
            String hex = toHex(bArr);
            LogUtil.m338i("AES秘钥：" + hex);
            return hex;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] getRawKey(byte[] bArr) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance(SHA1PRNG, new CryptoProvider());
        secureRandom.setSeed(bArr);
        keyGenerator.init(128, secureRandom);
        return keyGenerator.generateKey().getEncoded();
    }

    public static String encrypt(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return str2;
        }
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
            cipher.init(1, secretKeySpec);
            return new String(Base64.encode(cipher.doFinal(str2.getBytes("UTF-8")), 2));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decrypt(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return str2;
        }
        try {
            return new String(decrypt(str, Base64.decode(str2, 0)), "UTF-8");
        } catch (Exception e) {
            LogUtil.m338i("AES_EX:" + e.getLocalizedMessage());
            return null;
        }
    }

    private static byte[] decrypt(String str, byte[] bArr) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(str.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
        cipher.init(2, secretKeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        return cipher.doFinal(bArr);
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

    public static class CryptoProvider extends Provider {
        public CryptoProvider() {
            super("Crypto", 1.0d, "HARMONY (SHA1 digest; SecureRandom; SHA1withDSA signature)");
            put("SecureRandom.SHA1PRNG", "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl");
            put("SecureRandom.SHA1PRNG ImplementedIn", ExifInterface.TAG_SOFTWARE);
        }
    }
}
