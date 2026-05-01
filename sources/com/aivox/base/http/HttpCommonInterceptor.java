package com.aivox.base.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/* JADX INFO: loaded from: classes.dex */
public class HttpCommonInterceptor implements Interceptor {
    private final Map<String, String> mHeaderParamsMap = new HashMap();

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builderNewBuilder = request.newBuilder();
        builderNewBuilder.method(request.method(), request.body());
        if (!this.mHeaderParamsMap.isEmpty()) {
            for (Map.Entry<String, String> entry : this.mHeaderParamsMap.entrySet()) {
                builderNewBuilder.header(entry.getKey(), entry.getValue());
            }
        }
        return chain.proceed(builderNewBuilder.build());
    }

    public static class Builder {
        HttpCommonInterceptor mHttpCommonInterceptor = new HttpCommonInterceptor();

        public Builder addHeaderParams(String str, Object obj) {
            this.mHttpCommonInterceptor.mHeaderParamsMap.put(str, String.valueOf(obj));
            return this;
        }

        public HttpCommonInterceptor build() {
            return this.mHttpCommonInterceptor;
        }
    }
}
