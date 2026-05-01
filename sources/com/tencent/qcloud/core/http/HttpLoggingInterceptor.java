package com.tencent.qcloud.core.http;

import java.util.concurrent.TimeUnit;
import okhttp3.Connection;
import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

/* JADX INFO: loaded from: classes4.dex */
public final class HttpLoggingInterceptor implements Interceptor {
    private volatile Level level = Level.NONE;
    private final Logger logger;

    public enum Level {
        NONE,
        BASIC,
        HEADERS,
        BODY
    }

    public interface Logger {
        void logException(Exception exc, String str);

        void logRequest(String str);

        void logResponse(Response response, String str);
    }

    public HttpLoggingInterceptor(Logger logger) {
        this.logger = logger;
    }

    public HttpLoggingInterceptor setLevel(Level level) {
        if (level == null) {
            throw new NullPointerException("level == null. Use Level.NONE instead.");
        }
        this.level = level;
        return this;
    }

    public Level getLevel() {
        return this.level;
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws Exception {
        Level level = this.level;
        Request request = chain.request();
        if (level == Level.NONE) {
            return chain.proceed(request);
        }
        Connection connection = chain.connection();
        OkHttpLoggingUtils.logRequest(request, connection != null ? connection.protocol() : Protocol.HTTP_1_1, level, this.logger);
        long jNanoTime = System.nanoTime();
        try {
            Response responseProceed = chain.proceed(request);
            OkHttpLoggingUtils.logResponse(responseProceed, TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - jNanoTime), level, this.logger);
            return responseProceed;
        } catch (Exception e) {
            this.logger.logException(e, "<-- HTTP FAILED: " + e);
            throw e;
        }
    }
}
