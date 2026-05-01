package com.aivox.common.util.encrypt;

import android.util.Base64;
import com.aivox.base.util.LogUtil;
import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/* JADX INFO: loaded from: classes.dex */
public class EncryptUtils {
    private static final String GBK = "GBK";
    private static final String ISO8859 = "ISO-8859-1";
    private static final String USASCII = "US-ASCII";
    private static final String UTF8 = "UTF-8";
    private static final String type = "android";

    public static void setBase64() {
        try {
            Base64Util.decodeWord(Base64Util.encodeWord("大家要注意身体，不要熬夜写代码"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void setRSA() {
        KeyPair keyPairGenerateRSAKeyPair = RSAUtil.generateRSAKeyPair(2048);
        RSAPublicKey rSAPublicKey = (RSAPublicKey) keyPairGenerateRSAKeyPair.getPublic();
        RSAPrivateKey rSAPrivateKey = (RSAPrivateKey) keyPairGenerateRSAKeyPair.getPrivate();
        byte[] encoded = rSAPublicKey.getEncoded();
        byte[] encoded2 = rSAPrivateKey.getEncoded();
        try {
            LogUtil.m338i("RSA公钥:".concat(new String(Base64.encode(encoded, 0), "GBK")));
            LogUtil.m338i("RSA私钥:".concat(new String(Base64.encode(encoded2, 0), "UTF-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            byte[] bArrEncryptByPublicKey = RSAUtil.encryptByPublicKey(type, "123".getBytes(), encoded);
            LogUtil.m334d("RSA123公钥加密:" + Base64Util.encodeWord(new String(bArrEncryptByPublicKey, "UTF-8")));
            LogUtil.m334d("RSA123私钥解密文件: ".concat(new String(RSAUtil.decryptByPrivateKey(type, bArrEncryptByPublicKey, encoded2))));
        } catch (Exception e2) {
            LogUtil.m338i("RSA私钥解密报错：" + e2.getLocalizedMessage());
        }
        System.out.println("");
        try {
            byte[] bArrEncryptByPrivateKey = RSAUtil.encryptByPrivateKey(type, "456".getBytes(), encoded2);
            LogUtil.m334d("RSA456私钥加密文件: " + Base64Util.encodeWord(new String(bArrEncryptByPrivateKey, "UTF-8")));
            LogUtil.m334d("RSA456公钥解密文件: ".concat(new String(RSAUtil.decryptByPublicKey(type, bArrEncryptByPrivateKey, encoded))));
        } catch (Exception e3) {
            LogUtil.m338i("RSA公钥解密报错：" + e3.getLocalizedMessage());
        }
    }

    public static void setDES() {
        try {
            String strDesEncrypt = DESUtil.desEncrypt("欧拉蕾", "2012j214");
            LogUtil.m334d("DES静态加密: " + strDesEncrypt);
            LogUtil.m334d("DES静态解密: " + DESUtil.desDecrypt(strDesEncrypt, "2012j214"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String strGenerateKey = DESUtil.generateKey();
        try {
            String strEncode = DESUtil.encode(strGenerateKey, "风里来，雨里去");
            LogUtil.m334d("DES动态加密: " + strEncode);
            LogUtil.m334d("DES动态解密: " + DESUtil.decode(strGenerateKey, strEncode));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void setAES() {
        String strGenerateKey = AESUtil.generateKey();
        try {
            String strEncrypt = AESUtil.encrypt(strGenerateKey, "人有悲欢离合，月有阴晴圆缺");
            LogUtil.m334d("AES加密: " + strEncrypt);
            LogUtil.m334d("AES解密: " + AESUtil.decrypt(strGenerateKey, strEncrypt));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setDesAndAes() {
        String strGenerateKey = DESUtil.generateKey();
        String strGenerateKey2 = AESUtil.generateKey();
        try {
            String strEncode = DESUtil.encode(strGenerateKey, "风里来，雨里去");
            LogUtil.m334d("DES加密: " + strEncode);
            String strEncrypt = AESUtil.encrypt(strGenerateKey2, strEncode);
            LogUtil.m334d("AES加密: " + strEncrypt);
            String strDecrypt = AESUtil.decrypt(strGenerateKey2, strEncrypt);
            LogUtil.m334d("AES解密: " + strDecrypt);
            LogUtil.m334d("DES解密: " + DESUtil.decode(strGenerateKey, strDecrypt));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
