package com.aivox.base.util;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import kotlin.UByte;

/* JADX INFO: loaded from: classes.dex */
public class DESCrypt {
    static final String CIPHER = "DES/CBC/PKCS5Padding";
    private static Cache IvParamSpecs = null;
    private static Cache SecretKeySpecs = null;
    private static final String UTF8 = "UTF-8";
    private static SecretKeyFactory secretKeyFactory;

    static {
        try {
            secretKeyFactory = SecretKeyFactory.getInstance("DES");
        } catch (NoSuchAlgorithmException e) {
            BaseAppUtils.printErrorMsg(e);
        }
        SecretKeySpecs = new Cache() { // from class: com.aivox.base.util.DESCrypt.1
            @Override // com.aivox.base.util.DESCrypt.Cache
            protected Object createValue(Object obj) throws Exception {
                try {
                    return DESCrypt.secretKeyFactory.generateSecret(new DESKeySpec(DESCrypt._convertKeyIv((String) obj)));
                } catch (Exception e2) {
                    BaseAppUtils.printErrorMsg(e2);
                    return null;
                }
            }
        };
        IvParamSpecs = new Cache() { // from class: com.aivox.base.util.DESCrypt.2
            @Override // com.aivox.base.util.DESCrypt.Cache
            protected Object createValue(Object obj) throws Exception {
                return new IvParameterSpec(DESCrypt._convertKeyIv((String) obj));
            }
        };
    }

    static abstract class Cache {
        private final Map<Object, Object> innerCache = new HashMap();

        protected abstract Object createValue(Object obj) throws Exception;

        Cache() {
        }

        public Object get(Object obj) throws Exception {
            Object creationPlaceholder;
            Object obj2;
            synchronized (this.innerCache) {
                creationPlaceholder = this.innerCache.get(obj);
                if (creationPlaceholder == null) {
                    creationPlaceholder = new CreationPlaceholder();
                    this.innerCache.put(obj, creationPlaceholder);
                }
            }
            if (!(creationPlaceholder instanceof CreationPlaceholder)) {
                return creationPlaceholder;
            }
            synchronized (creationPlaceholder) {
                CreationPlaceholder creationPlaceholder2 = (CreationPlaceholder) creationPlaceholder;
                if (creationPlaceholder2.value == null) {
                    creationPlaceholder2.value = createValue(obj);
                    synchronized (this.innerCache) {
                        this.innerCache.put(obj, creationPlaceholder2.value);
                    }
                }
                obj2 = creationPlaceholder2.value;
            }
            return obj2;
        }

        static final class CreationPlaceholder {
            Object value;

            CreationPlaceholder() {
            }
        }
    }

    public static byte[] stringToHex(String str) {
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) Integer.parseInt(str.substring(i2, i2 + 2), 16);
        }
        return bArr;
    }

    public static String hexToString(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & UByte.MAX_VALUE);
            if (hexString.length() < 2) {
                sb.append("0");
            }
            sb.append(hexString);
        }
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] _convertKeyIv(String str) throws IOException {
        if (str.length() == 8) {
            return str.getBytes("UTF-8");
        }
        if (str.startsWith("0x") && str.length() == 32) {
            byte[] bArr = new byte[8];
            int i = 0;
            while (i < str.length()) {
                int i2 = i + 1;
                if (str.charAt(i) == '0') {
                    int i3 = i + 2;
                    if (str.charAt(i2) == 'x') {
                        try {
                            bArr[i3 / 4] = (byte) Integer.parseInt(str.substring(i3, i + 4), 16);
                        } catch (Exception e) {
                            BaseAppUtils.printErrorMsg(e);
                            throw new IOException("TXT '" + str + "' is invalid!");
                        }
                    }
                    i2 = i3;
                }
                i = i2 + 2;
            }
            return bArr;
        }
        throw new IOException("TXT '" + str + "' is invalid!");
    }

    private static String encrypt(String str, String str2, String str3) {
        SecretKey secretKey;
        IvParameterSpec ivParameterSpec;
        byte[] bytes;
        byte[] bArrEncrypt = null;
        try {
            secretKey = (SecretKey) SecretKeySpecs.get(str2);
            try {
                ivParameterSpec = (IvParameterSpec) IvParamSpecs.get(str3);
            } catch (Exception e) {
                e = e;
                BaseAppUtils.printErrorMsg(e);
                ivParameterSpec = null;
            }
        } catch (Exception e2) {
            e = e2;
            secretKey = null;
        }
        try {
            bytes = str.getBytes("UTF-8");
        } catch (Exception e3) {
            BaseAppUtils.printErrorMsg(e3);
            bytes = null;
        }
        try {
            bArrEncrypt = encrypt(bytes, secretKey, ivParameterSpec);
        } catch (Exception e4) {
            BaseAppUtils.printErrorMsg(e4);
        }
        return hexToString(bArrEncrypt);
    }

    private static byte[] encrypt(byte[] bArr, SecretKey secretKey, IvParameterSpec ivParameterSpec) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER);
        cipher.init(1, secretKey, ivParameterSpec);
        return cipher.doFinal(bArr);
    }

    private static String decrypt(String str, String str2, String str3) throws Exception {
        SecretKey secretKey;
        IvParameterSpec ivParameterSpec = null;
        try {
            secretKey = (SecretKey) SecretKeySpecs.get(str2);
            try {
                ivParameterSpec = (IvParameterSpec) IvParamSpecs.get(str3);
            } catch (Exception e) {
                e = e;
                BaseAppUtils.printErrorMsg(e);
            }
        } catch (Exception e2) {
            e = e2;
            secretKey = null;
        }
        return decrypt(stringToHex(str), secretKey, ivParameterSpec);
    }

    private static String decrypt(byte[] bArr, SecretKey secretKey, IvParameterSpec ivParameterSpec) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER);
        cipher.init(2, secretKey, ivParameterSpec);
        return new String(cipher.doFinal(bArr));
    }

    public static void main(String[] strArr) throws Exception {
        System.currentTimeMillis();
        decrypt(encrypt("{\n    \"couponActivityId\": \"100\", \n    \"couponId\": 1001,  \n    \"openId\": \"oq1Gkt-0GjGTdRcvvS0TP70IKUWA\", \n    \"type\": 1, \n    \"channelId\": 1\n}", "w8f3k9c2", "w8f3k9c4"), "w8f3k9c2", "w8f3k9c4");
        System.currentTimeMillis();
    }
}
