package com.tencent.beacon.base.net.p021b;

import android.content.Context;
import android.util.Base64;
import com.aivox.common.util.encrypt.RSAUtil;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.p028d.C2710b;
import com.tencent.cos.xml.crypto.JceEncryptionConstants;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.b.c */
/* JADX INFO: compiled from: EncryUtil.java */
/* JADX INFO: loaded from: classes4.dex */
public final class C2668c {
    /* JADX INFO: renamed from: a */
    public static byte[] m1327a(int i, String str, byte[] bArr) throws Exception {
        if (i == 3) {
            return m1329a(str, bArr);
        }
        return null;
    }

    /* JADX INFO: renamed from: b */
    public static byte[] m1330b(int i, String str, byte[] bArr) throws Exception {
        if (i == 3) {
            return m1331b(str, bArr);
        }
        return null;
    }

    /* JADX INFO: renamed from: a */
    private static byte[] m1329a(String str, byte[] bArr) throws Exception {
        if (str == null || bArr == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(str);
        for (int length = sb.length(); length < 16; length++) {
            sb.append("0");
        }
        String strSubstring = sb.toString().substring(0, 16);
        SecretKeySpec secretKeySpec = new SecretKeySpec(strSubstring.getBytes(Charset.forName("UTF-8")), JceEncryptionConstants.SYMMETRIC_KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(JceEncryptionConstants.SYMMETRIC_CIPHER_METHOD);
        cipher.init(2, secretKeySpec, new IvParameterSpec(strSubstring.getBytes(Charset.forName("UTF-8"))));
        return cipher.doFinal(bArr);
    }

    /* JADX INFO: renamed from: b */
    private static byte[] m1331b(String str, byte[] bArr) throws Exception {
        if (str == null || bArr == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(str);
        for (int length = sb.length(); length < 16; length++) {
            sb.append("0");
        }
        String strSubstring = sb.toString().substring(0, 16);
        SecretKeySpec secretKeySpec = new SecretKeySpec(strSubstring.getBytes(Charset.forName("UTF-8")), JceEncryptionConstants.SYMMETRIC_KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(JceEncryptionConstants.SYMMETRIC_CIPHER_METHOD);
        cipher.init(1, secretKeySpec, new IvParameterSpec(strSubstring.getBytes(Charset.forName("UTF-8"))));
        return cipher.doFinal(bArr);
    }

    /* JADX INFO: renamed from: a */
    public static byte[] m1328a(Context context, String str) {
        KeyFactory keyFactory;
        if (str == null) {
            return null;
        }
        String str2 = C2710b.m1518d().m1559m() == 0 ? "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCsAxNCSLyNUCOP1QqYStE8ZeiU\nv4afaMqEmoLCKb0mUZYvYOoVN7LPMi2IVY2MRaFJvuND3glVw1RDm2VJJtjQkwUd\n3kpR9TrHAf7UQOVTpNo3Vi7pXTOqZ6bh3ZA/fs56jDCCKV6+wT/pCeu8N6vVnPrD\nz3SdHIeNeWb/woazCwIDAQAB" : "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqZduQqLHzI6vPj4e0ILNDGmW7oSFfr+Rjf5H0XX6uzsrrEijPqO+CuSrm+hEcHf8QQ/Dyn5zyM7CIpr9jgtpo6Azl4/sHHamF8HYdQHjzgB5xtBWaSweRHmPVc2atZUWVGg8xL41Jv+SJBExBXk7QatoXHZgLsyEIU9oK31w6XSYcZxEinjw7VEDnVA4J0d32a/XnVdVqog+V8yS7I74k/RN/QnTFRXMx4/hdXVwM7glzjSAE4gFrU6gmAWNbyLEn9OS8mmrjyJlafeEDxycuRgwVnZwGjeO9OO5Qbb0biVHsGvI7COYw5kqilOIHouTrrdyWjDA1Vud5cly1Cy5uwIDAQAB";
        C2695c.m1464a(String.format("use rsa public key length: %s", C2710b.m1518d().m1559m() == 0 ? "1024" : "2048"), new Object[0]);
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decode(str2, 0));
            if (context.getApplicationInfo().targetSdkVersion >= 28) {
                keyFactory = KeyFactory.getInstance(RSAUtil.RSA);
            } else {
                keyFactory = KeyFactory.getInstance(RSAUtil.RSA, "BC");
            }
            RSAPublicKey rSAPublicKey = (RSAPublicKey) keyFactory.generatePublic(x509EncodedKeySpec);
            Cipher cipher = Cipher.getInstance(RSAUtil.ECB_PKCS1_PADDING);
            cipher.init(1, rSAPublicKey);
            return cipher.doFinal(str.getBytes(Charset.forName("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
