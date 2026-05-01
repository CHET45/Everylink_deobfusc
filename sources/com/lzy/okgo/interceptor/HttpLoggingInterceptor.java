package com.lzy.okgo.interceptor;

import com.lzy.okgo.utils.OkLogger;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;

/* JADX INFO: loaded from: classes4.dex */
public class HttpLoggingInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private java.util.logging.Level colorLevel;
    private Logger logger;
    private volatile Level printLevel = Level.NONE;

    public enum Level {
        NONE,
        BASIC,
        HEADERS,
        BODY
    }

    public HttpLoggingInterceptor(String str) {
        this.logger = Logger.getLogger(str);
    }

    public void setPrintLevel(Level level) {
        this.printLevel = level;
    }

    public void setColorLevel(java.util.logging.Level level) {
        this.colorLevel = level;
    }

    public void log(String str) {
        this.logger.log(this.colorLevel, str);
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws Exception {
        Request request = chain.request();
        if (this.printLevel == Level.NONE) {
            return chain.proceed(request);
        }
        logForRequest(request, chain.connection());
        try {
            return logForResponse(chain.proceed(request), TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - System.nanoTime()));
        } catch (Exception e) {
            log("<-- HTTP FAILED: " + e);
            throw e;
        }
    }

    private void logForRequest(Request request, Connection connection) throws IOException {
        StringBuilder sb;
        boolean z = this.printLevel == Level.BODY;
        boolean z2 = this.printLevel == Level.BODY || this.printLevel == Level.HEADERS;
        RequestBody requestBodyBody = request.body();
        boolean z3 = requestBodyBody != null;
        try {
            try {
                log("--> " + request.method() + ' ' + request.url() + ' ' + (connection != null ? connection.protocol() : Protocol.HTTP_1_1));
                if (z2) {
                    Headers headers = request.headers();
                    int size = headers.size();
                    for (int i = 0; i < size; i++) {
                        log("\t" + headers.name(i) + ": " + headers.value(i));
                    }
                    log(" ");
                    if (z && z3) {
                        if (isPlaintext(requestBodyBody.contentType())) {
                            bodyToString(request);
                        } else {
                            log("\tbody: maybe [file part] , too large too print , ignored!");
                        }
                    }
                }
                sb = new StringBuilder("--> END ");
            } catch (Exception e) {
                OkLogger.m864e(e);
                sb = new StringBuilder("--> END ");
            }
            log(sb.append(request.method()).toString());
        } catch (Throwable th) {
            log("--> END " + request.method());
            throw th;
        }
    }

    private Response logForResponse(Response response, long j) {
        Response responseBuild = response.newBuilder().build();
        ResponseBody responseBodyBody = responseBuild.body();
        boolean z = true;
        boolean z2 = this.printLevel == Level.BODY;
        if (this.printLevel != Level.BODY && this.printLevel != Level.HEADERS) {
            z = false;
        }
        try {
            try {
                log("<-- " + responseBuild.code() + ' ' + responseBuild.message() + ' ' + responseBuild.request().url() + " (" + j + "ms）");
                if (z) {
                    Headers headers = responseBuild.headers();
                    int size = headers.size();
                    for (int i = 0; i < size; i++) {
                        log("\t" + headers.name(i) + ": " + headers.value(i));
                    }
                    log(" ");
                    if (z2 && HttpHeaders.hasBody(responseBuild)) {
                        if (isPlaintext(responseBodyBody.contentType())) {
                            String strString = responseBodyBody.string();
                            log("\tbody:" + strString);
                            return response.newBuilder().body(ResponseBody.create(responseBodyBody.contentType(), strString)).build();
                        }
                        log("\tbody: maybe [file part] , too large too print , ignored!");
                    }
                }
            } catch (Exception e) {
                OkLogger.m864e(e);
            }
            return response;
        } finally {
            log("<-- END HTTP");
        }
    }

    private static boolean isPlaintext(MediaType mediaType) {
        if (mediaType == null) {
            return false;
        }
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        String strSubtype = mediaType.subtype();
        if (strSubtype != null) {
            String lowerCase = strSubtype.toLowerCase();
            if (lowerCase.contains("x-www-form-urlencoded") || lowerCase.contains("json") || lowerCase.contains("xml") || lowerCase.contains("html")) {
                return true;
            }
        }
        return false;
    }

    private void bodyToString(Request request) {
        try {
            Request requestBuild = request.newBuilder().build();
            Buffer buffer = new Buffer();
            requestBuild.body().writeTo(buffer);
            Charset charset = UTF8;
            MediaType mediaTypeContentType = requestBuild.body().contentType();
            if (mediaTypeContentType != null) {
                charset = mediaTypeContentType.charset(charset);
            }
            log("\tbody:" + buffer.readString(charset));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
