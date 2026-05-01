package com.lzy.okgo.utils;

import android.text.TextUtils;
import com.github.houbb.heaven.constant.PunctuationConst;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.request.BaseRequest;
import java.util.Locale;
import java.util.StringTokenizer;
import okhttp3.Headers;

/* JADX INFO: loaded from: classes4.dex */
public class HeaderParser {
    public static <T> CacheEntity<T> createCacheEntity(Headers headers, T t, CacheMode cacheMode, String str) {
        long jCurrentTimeMillis;
        long j;
        if (cacheMode == CacheMode.DEFAULT) {
            long date = HttpHeaders.getDate(headers.get("Date"));
            jCurrentTimeMillis = HttpHeaders.getExpiration(headers.get("Expires"));
            String cacheControl = HttpHeaders.getCacheControl(headers.get("Cache-Control"), headers.get(HttpHeaders.HEAD_KEY_PRAGMA));
            if (TextUtils.isEmpty(cacheControl) && jCurrentTimeMillis <= 0) {
                return null;
            }
            if (TextUtils.isEmpty(cacheControl)) {
                j = 0;
            } else {
                StringTokenizer stringTokenizer = new StringTokenizer(cacheControl, PunctuationConst.COMMA);
                j = 0;
                while (stringTokenizer.hasMoreTokens()) {
                    String lowerCase = stringTokenizer.nextToken().trim().toLowerCase(Locale.getDefault());
                    if (lowerCase.equals("no-cache") || lowerCase.equals("no-store")) {
                        return null;
                    }
                    if (lowerCase.startsWith("max-age=")) {
                        try {
                            j = Long.parseLong(lowerCase.substring(8));
                            if (j <= 0) {
                                return null;
                            }
                        } catch (Exception e) {
                            OkLogger.m864e(e);
                        }
                    }
                }
            }
            long jCurrentTimeMillis2 = System.currentTimeMillis();
            if (date <= 0) {
                date = jCurrentTimeMillis2;
            }
            if (j > 0) {
                jCurrentTimeMillis = date + (j * 1000);
            } else if (jCurrentTimeMillis < 0) {
                jCurrentTimeMillis = 0;
            }
        } else {
            jCurrentTimeMillis = System.currentTimeMillis();
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        for (String str2 : headers.names()) {
            httpHeaders.put(str2, headers.get(str2));
        }
        CacheEntity<T> cacheEntity = new CacheEntity<>();
        cacheEntity.setKey(str);
        cacheEntity.setData(t);
        cacheEntity.setLocalExpire(jCurrentTimeMillis);
        cacheEntity.setResponseHeaders(httpHeaders);
        return cacheEntity;
    }

    public static <T> void addCacheHeaders(BaseRequest baseRequest, CacheEntity<T> cacheEntity, CacheMode cacheMode) {
        HttpHeaders responseHeaders;
        if (cacheEntity == null || cacheMode != CacheMode.DEFAULT || (responseHeaders = cacheEntity.getResponseHeaders()) == null) {
            return;
        }
        String str = responseHeaders.get("ETag");
        if (str != null) {
            baseRequest.headers("If-None-Match", str);
        }
        long lastModified = HttpHeaders.getLastModified(responseHeaders.get("Last-Modified"));
        if (lastModified > 0) {
            baseRequest.headers("If-Modified-Since", HttpHeaders.formatMillisToGMT(lastModified));
        }
    }
}
