package com.tencent.qcloud.core.http;

import com.tencent.qcloud.core.common.QCloudClientException;
import com.tencent.qcloud.core.common.QCloudProgressListener;
import com.tencent.qcloud.core.common.QCloudServiceException;
import com.tencent.qcloud.core.http.interceptor.QCloudRetryInterceptor;
import java.io.IOException;
import okhttp3.Request;
import okhttp3.Response;

/* JADX INFO: loaded from: classes4.dex */
public abstract class NetworkProxy<T> {
    protected String identifier;
    protected QCloudProgressListener mProgressListener;
    private QCloudRetryInterceptor mRetryInterceptor;
    protected HttpTaskMetrics metrics;

    public abstract Response callHttpRequest(Request request) throws IOException;

    protected abstract void cancel();

    protected void disconnect() {
    }

    public void setRetryInterceptor(QCloudRetryInterceptor qCloudRetryInterceptor) {
        this.mRetryInterceptor = qCloudRetryInterceptor;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(8:16|22|(1:24)(2:26|(5:28|(1:33)|58|34|35)(2:29|30))|25|(1:33)|58|34|35) */
    /* JADX WARN: Not initialized variable reg: 2, insn: 0x007d: MOVE (r1 I:??[OBJECT, ARRAY]) = (r2 I:??[OBJECT, ARRAY]), block:B:42:0x007d */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected com.tencent.qcloud.core.http.HttpResult<T> executeHttpRequest(com.tencent.qcloud.core.http.HttpRequest<T> r6) throws java.lang.Throwable {
        /*
            r5 = this;
            com.tencent.qcloud.core.http.ResponseBodyConverter r0 = r6.getResponseBodyConverter()
            boolean r0 = r0 instanceof com.tencent.qcloud.core.http.SelfCloseConverter
            r1 = 0
            java.lang.String r2 = r5.identifier     // Catch: java.lang.Throwable -> L3b java.io.IOException -> L3d
            r6.setOkHttpRequestTag(r2)     // Catch: java.lang.Throwable -> L3b java.io.IOException -> L3d
            okhttp3.Request r2 = r6.buildRealRequest()     // Catch: java.lang.Throwable -> L3b java.io.IOException -> L3d
            com.tencent.qcloud.core.http.interceptor.QCloudRetryInterceptor r3 = r5.mRetryInterceptor     // Catch: java.lang.Throwable -> L3b java.io.IOException -> L3d
            if (r3 == 0) goto L19
            okhttp3.Response r2 = r3.intercept(r5, r2)     // Catch: java.lang.Throwable -> L3b java.io.IOException -> L3d
            goto L1d
        L19:
            okhttp3.Response r2 = r5.callHttpRequest(r2)     // Catch: java.lang.Throwable -> L3b java.io.IOException -> L3d
        L1d:
            if (r2 == 0) goto L26
            com.tencent.qcloud.core.http.HttpResult r6 = r5.convertResponse(r6, r2)     // Catch: java.io.IOException -> L39 java.lang.Throwable -> L7c
            r3 = r6
            r6 = r1
            goto L2e
        L26:
            com.tencent.qcloud.core.common.QCloudServiceException r6 = new com.tencent.qcloud.core.common.QCloudServiceException     // Catch: java.io.IOException -> L39 java.lang.Throwable -> L7c
            java.lang.String r3 = "http response is null"
            r6.<init>(r3)     // Catch: java.io.IOException -> L39 java.lang.Throwable -> L7c
            r3 = r1
        L2e:
            if (r2 == 0) goto L35
            if (r0 != 0) goto L35
            com.tencent.qcloud.core.util.OkhttpInternalUtils.closeQuietly(r2)
        L35:
            r5.disconnect()     // Catch: java.lang.Exception -> L75
            goto L75
        L39:
            r6 = move-exception
            goto L3f
        L3b:
            r6 = move-exception
            goto L7e
        L3d:
            r6 = move-exception
            r2 = r1
        L3f:
            java.lang.Throwable r3 = r6.getCause()     // Catch: java.lang.Throwable -> L7c
            boolean r3 = r3 instanceof com.tencent.qcloud.core.common.QCloudClientException     // Catch: java.lang.Throwable -> L7c
            if (r3 == 0) goto L4f
            java.lang.Throwable r6 = r6.getCause()     // Catch: java.lang.Throwable -> L7c
            com.tencent.qcloud.core.common.QCloudClientException r6 = (com.tencent.qcloud.core.common.QCloudClientException) r6     // Catch: java.lang.Throwable -> L7c
        L4d:
            r3 = r1
            goto L67
        L4f:
            java.lang.Throwable r3 = r6.getCause()     // Catch: java.lang.Throwable -> L7c
            boolean r3 = r3 instanceof com.tencent.qcloud.core.common.QCloudServiceException     // Catch: java.lang.Throwable -> L7c
            if (r3 == 0) goto L60
            java.lang.Throwable r6 = r6.getCause()     // Catch: java.lang.Throwable -> L7c
            com.tencent.qcloud.core.common.QCloudServiceException r6 = (com.tencent.qcloud.core.common.QCloudServiceException) r6     // Catch: java.lang.Throwable -> L7c
            r3 = r6
            r6 = r1
            goto L67
        L60:
            com.tencent.qcloud.core.common.QCloudClientException r3 = new com.tencent.qcloud.core.common.QCloudClientException     // Catch: java.lang.Throwable -> L7c
            r3.<init>(r6)     // Catch: java.lang.Throwable -> L7c
            r6 = r3
            goto L4d
        L67:
            if (r2 == 0) goto L6e
            if (r0 != 0) goto L6e
            com.tencent.qcloud.core.util.OkhttpInternalUtils.closeQuietly(r2)
        L6e:
            r5.disconnect()     // Catch: java.lang.Exception -> L71
        L71:
            r4 = r1
            r1 = r6
            r6 = r3
            r3 = r4
        L75:
            if (r1 != 0) goto L7b
            if (r6 != 0) goto L7a
            return r3
        L7a:
            throw r6
        L7b:
            throw r1
        L7c:
            r6 = move-exception
            r1 = r2
        L7e:
            if (r1 == 0) goto L85
            if (r0 != 0) goto L85
            com.tencent.qcloud.core.util.OkhttpInternalUtils.closeQuietly(r1)
        L85:
            r5.disconnect()     // Catch: java.lang.Exception -> L88
        L88:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qcloud.core.http.NetworkProxy.executeHttpRequest(com.tencent.qcloud.core.http.HttpRequest):com.tencent.qcloud.core.http.HttpResult");
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected HttpResult<T> convertResponse(HttpRequest<T> httpRequest, Response response) throws QCloudServiceException, QCloudClientException {
        HttpResponse httpResponse = new HttpResponse(httpRequest, response);
        ResponseBodyConverter<T> responseBodyConverter = httpRequest.getResponseBodyConverter();
        if (responseBodyConverter instanceof ProgressBody) {
            ((ProgressBody) responseBodyConverter).setProgressListener(this.mProgressListener);
        }
        return new HttpResult<>(httpResponse, responseBodyConverter.convert(httpResponse));
    }
}
