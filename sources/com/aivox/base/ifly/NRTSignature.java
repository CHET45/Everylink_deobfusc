package com.aivox.base.ifly;

import android.util.Base64;
import com.aivox.base.util.BaseAppUtils;
import com.github.houbb.heaven.constant.PunctuationConst;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class NRTSignature {
    private static final String ALGORITHM = "UTF-8";
    private static final String CHARSET_UTF8 = "UTF-8";

    public static String gernerateSignature(Map<String, Object> map, String str) throws Exception {
        try {
            return newStringByBase64(hmacSHA1Signature(str, formUrlEncodedParameters(map)));
        } catch (UnsupportedEncodingException e) {
            BaseAppUtils.printErrorMsg(e);
            return "";
        }
    }

    public static byte[] hmacSHA1Signature(String str, String str2) throws Exception {
        if (isEmpty(str)) {
            throw new IOException("secret can not be empty");
        }
        if (isEmpty(str2)) {
            return null;
        }
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(new SecretKeySpec(str.getBytes("UTF-8"), "UTF-8"));
        return mac.doFinal(str2.getBytes("UTF-8"));
    }

    private static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static String newStringByBase64(byte[] bArr) throws UnsupportedEncodingException {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        return Base64.encodeToString(bArr, 2);
    }

    private static String formUrlEncodedParameters(Map<String, Object> map) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != null) {
                sb.append(URLEncoder.encode(entry.getKey(), "UTF-8")).append(PunctuationConst.EQUAL).append(URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8")).append(PunctuationConst.AND);
            }
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    public static String formUrlEncodedValueParameters(Map<String, Object> map) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != null) {
                sb.append(entry.getKey()).append(PunctuationConst.EQUAL).append(URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8")).append(PunctuationConst.AND);
            }
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }
}
