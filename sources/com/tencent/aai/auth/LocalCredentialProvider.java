package com.tencent.aai.auth;

import android.util.Base64;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes4.dex */
public class LocalCredentialProvider implements AbsCredentialProvider {
    private final String secretKey;

    public LocalCredentialProvider(String str) {
        this.secretKey = str;
    }

    private String getSign(String str, String str2) {
        byte[] bArrHmacSha1 = hmacSha1(str, str2);
        return bArrHmacSha1 == null ? "" : Base64.encodeToString(bArrHmacSha1, 0).replaceAll("\n", "");
    }

    private byte[] hmacSha1(String str, String str2) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes("utf-8"), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(secretKeySpec);
            return mac.doFinal(str.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e2) {
            e2.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e3) {
            e3.printStackTrace();
            return null;
        } catch (Exception e4) {
            e4.printStackTrace();
            return null;
        }
    }

    @Override // com.tencent.aai.auth.AbsCredentialProvider
    public String getAudioRecognizeSign(String str) {
        return getSign(str, this.secretKey);
    }
}
