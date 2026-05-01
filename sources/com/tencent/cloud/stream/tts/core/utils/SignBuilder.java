package com.tencent.cloud.stream.tts.core.utils;

import android.util.Base64;
import java.util.HashSet;
import java.util.Set;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes4.dex */
public class SignBuilder {
    private static final String REQUEST_ENCODE = "UTF-8";
    private static final String SIGN_TYPE = "HmacSHA1";
    private static final Set<String> VALIDE_REQUEST_TYPES;

    static {
        HashSet hashSet = new HashSet();
        VALIDE_REQUEST_TYPES = hashSet;
        hashSet.add("GET");
        hashSet.add("POST");
    }

    public static String base64_hmac_sha1(String originalText, String secretKey) {
        try {
            Mac mac = Mac.getInstance(SIGN_TYPE);
            mac.init(new SecretKeySpec(secretKey.getBytes("UTF-8"), SIGN_TYPE));
            return Base64.encodeToString(mac.doFinal(originalText.getBytes("UTF-8")), 2);
        } catch (Exception unused) {
            AAILogger.m1854e("SignBuilder", "签名编码失败");
            return "";
        }
    }
}
