package com.tencent.qcloud.core.http;

import android.support.v4.media.session.PlaybackStateCompat;
import com.tencent.qcloud.core.http.HttpLoggingInterceptor;
import com.tencent.qcloud.core.util.OkhttpInternalUtils;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Map;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/* JADX INFO: loaded from: classes4.dex */
public class OkHttpLoggingUtils {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    private static boolean isContentLengthTooLarge(long j) {
        return j > PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;
    }

    public static void logResponse(Response response, long j, HttpLoggingInterceptor.Level level, HttpLoggingInterceptor.Logger logger) {
        boolean z = level == HttpLoggingInterceptor.Level.BODY;
        boolean z2 = z || level == HttpLoggingInterceptor.Level.HEADERS;
        ResponseBody responseBodyBody = response.body();
        boolean z3 = responseBodyBody != null;
        long jContentLength = z3 ? responseBodyBody.contentLength() : 0L;
        logger.logResponse(response, "<-- " + response.code() + ' ' + response.message() + ' ' + response.request().url() + " (" + j + "ms" + (!z2 ? ", " + (jContentLength != -1 ? jContentLength + "-byte" : "unknown-length") + " body" : "") + ')');
        if (z2) {
            Headers headers = response.headers();
            int size = headers.size();
            for (int i = 0; i < size; i++) {
                logger.logResponse(response, headers.name(i) + ": " + headers.value(i));
            }
            if (!z || !OkhttpInternalUtils.hasBody(response) || !z3 || isContentLengthTooLarge(jContentLength)) {
                logger.logResponse(response, "<-- END HTTP");
                return;
            }
            if (bodyEncoded(response.headers())) {
                logger.logResponse(response, "<-- END HTTP (encoded body omitted)");
                return;
            }
            try {
                BufferedSource bufferedSourceSource = responseBodyBody.source();
                bufferedSourceSource.request(Long.MAX_VALUE);
                Buffer buffer = bufferedSourceSource.buffer();
                Charset charset = UTF8;
                MediaType mediaTypeContentType = responseBodyBody.contentType();
                if (mediaTypeContentType != null) {
                    try {
                        charset = mediaTypeContentType.charset(charset);
                    } catch (UnsupportedCharsetException unused) {
                        logger.logResponse(response, "");
                        logger.logResponse(response, "Couldn't decode the response body; charset is likely malformed.");
                        logger.logResponse(response, "<-- END HTTP");
                        return;
                    }
                }
                if (!isPlaintext(buffer)) {
                    logger.logResponse(response, "");
                    logger.logResponse(response, "<-- END HTTP (binary " + buffer.size() + "-byte body omitted)");
                } else {
                    if (jContentLength != 0) {
                        logger.logResponse(response, "");
                        logger.logResponse(response, buffer.clone().readString(charset));
                    }
                    logger.logResponse(response, "<-- END HTTP (" + buffer.size() + "-byte body)");
                }
            } catch (Exception unused2) {
                logger.logResponse(response, "<-- END HTTP");
            }
        }
    }

    public static void logQuicRequestHeaders(Map<String, String> map, HttpLoggingInterceptor.Logger logger) {
        if (map == null) {
            return;
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            logger.logRequest(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void logMessage(String str, HttpLoggingInterceptor.Logger logger) {
        logger.logRequest(str);
    }

    public static void logRequest(Request request, Protocol protocol, HttpLoggingInterceptor.Level level, HttpLoggingInterceptor.Logger logger) throws IOException {
        boolean z = level == HttpLoggingInterceptor.Level.BODY;
        boolean z2 = z || level == HttpLoggingInterceptor.Level.HEADERS;
        RequestBody requestBodyBody = request.body();
        boolean z3 = requestBodyBody != null;
        String str = "--> " + request.method() + ' ' + request.url() + ' ' + protocol;
        if (!z2 && z3) {
            str = str + " (" + requestBodyBody.contentLength() + "-byte body)";
        }
        logger.logRequest(str);
        if (z2) {
            if (z3) {
                if (requestBodyBody.contentType() != null) {
                    logger.logRequest("Content-Type: " + requestBodyBody.contentType());
                }
                if (requestBodyBody.contentLength() != -1) {
                    logger.logRequest("Content-Length: " + requestBodyBody.contentLength());
                }
            }
            Headers headers = request.headers();
            int size = headers.size();
            for (int i = 0; i < size; i++) {
                String strName = headers.name(i);
                if (!"Content-Type".equalsIgnoreCase(strName) && !"Content-Length".equalsIgnoreCase(strName)) {
                    logger.logRequest(strName + ": " + headers.value(i));
                }
            }
            if (!z || !z3 || isContentLengthTooLarge(requestBodyBody.contentLength())) {
                logger.logRequest("--> END " + request.method());
                return;
            }
            if (bodyEncoded(request.headers())) {
                logger.logRequest("--> END " + request.method() + " (encoded body omitted)");
                return;
            }
            try {
                Buffer buffer = new Buffer();
                requestBodyBody.writeTo(buffer);
                Charset charset = UTF8;
                MediaType mediaTypeContentType = requestBodyBody.contentType();
                if (mediaTypeContentType != null) {
                    charset = mediaTypeContentType.charset(charset);
                }
                logger.logRequest("");
                if (isPlaintext(buffer)) {
                    logger.logRequest(buffer.readString(charset));
                    logger.logRequest("--> END " + request.method() + " (" + requestBodyBody.contentLength() + "-byte body)");
                } else {
                    logger.logRequest("--> END " + request.method() + " (binary " + requestBodyBody.contentLength() + "-byte body omitted)");
                }
            } catch (Exception unused) {
                logger.logRequest("--> END " + request.method());
            }
        }
    }

    private static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer buffer2 = new Buffer();
            buffer.copyTo(buffer2, 0L, buffer.size() < 64 ? buffer.size() : 64L);
            for (int i = 0; i < 16; i++) {
                if (buffer2.exhausted()) {
                    return true;
                }
                int utf8CodePoint = buffer2.readUtf8CodePoint();
                if (Character.isISOControl(utf8CodePoint) && !Character.isWhitespace(utf8CodePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException unused) {
            return false;
        }
    }

    private static boolean bodyEncoded(Headers headers) {
        String str = headers.get("Content-Encoding");
        return (str == null || str.equalsIgnoreCase("identity")) ? false : true;
    }
}
