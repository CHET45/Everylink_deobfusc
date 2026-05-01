package com.aivox.app.test.denoise;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: classes.dex */
public class UrlUtil {
    public static String getFileNameFromUrl(String str) throws Exception {
        if (!str.contains("/")) {
            throw new Exception("url contains no '/' :" + str);
        }
        String strUrlDecoded = urlDecoded(str);
        return strUrlDecoded.substring(strUrlDecoded.lastIndexOf("/") + 1);
    }

    public static String urlEncoded(String str) throws UnsupportedEncodingException {
        return URLEncoder.encode(new String(str.getBytes(), StandardCharsets.UTF_8), "UTF-8");
    }

    public static String urlDecoded(String str) throws UnsupportedEncodingException {
        return URLDecoder.decode(new String(str.getBytes(), StandardCharsets.UTF_8), "UTF-8");
    }
}
