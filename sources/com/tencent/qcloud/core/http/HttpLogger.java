package com.tencent.qcloud.core.http;

import com.tencent.qcloud.core.http.HttpLoggingInterceptor;
import com.tencent.qcloud.core.logger.COSLogger;
import okhttp3.Response;

/* JADX INFO: loaded from: classes4.dex */
public class HttpLogger implements HttpLoggingInterceptor.Logger {
    private String tag;

    public HttpLogger(String str) {
        this.tag = str;
    }

    HttpLogger() {
        this(QCloudHttpClient.HTTP_LOG_TAG);
    }

    public void setTag(String str) {
        this.tag = str;
    }

    @Override // com.tencent.qcloud.core.http.HttpLoggingInterceptor.Logger
    public void logRequest(String str) {
        COSLogger.iNetwork(this.tag, str);
    }

    @Override // com.tencent.qcloud.core.http.HttpLoggingInterceptor.Logger
    public void logResponse(Response response, String str) {
        COSLogger.iNetwork(this.tag, str);
    }

    @Override // com.tencent.qcloud.core.http.HttpLoggingInterceptor.Logger
    public void logException(Exception exc, String str) {
        COSLogger.iNetwork(this.tag, str);
    }
}
