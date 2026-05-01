package com.tencent.qcloud.core.http;

import okhttp3.Request;
import okhttp3.Response;

/* JADX INFO: loaded from: classes4.dex */
public abstract class QCloudHttpRetryHandler {
    public static QCloudHttpRetryHandler DEFAULT = new QCloudHttpRetryHandler() { // from class: com.tencent.qcloud.core.http.QCloudHttpRetryHandler.1
        @Override // com.tencent.qcloud.core.http.QCloudHttpRetryHandler
        public boolean shouldRetry(Request request, Response response, Exception exc) {
            return true;
        }
    };

    public abstract boolean shouldRetry(Request request, Response response, Exception exc);
}
