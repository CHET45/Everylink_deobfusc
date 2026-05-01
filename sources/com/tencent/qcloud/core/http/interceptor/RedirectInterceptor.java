package com.tencent.qcloud.core.http.interceptor;

import android.text.TextUtils;
import com.aivox.base.common.Constant;
import com.tencent.cos.xml.crypto.Headers;
import com.tencent.qcloud.core.common.DomainSwitchException;
import com.tencent.qcloud.core.http.HttpConstants;
import com.tencent.qcloud.core.http.HttpTask;
import com.tencent.qcloud.core.task.TaskManager;
import com.tencent.qcloud.core.util.DomainSwitchUtils;
import com.tencent.qcloud.core.util.OkhttpInternalUtils;
import java.io.IOException;
import java.net.ProtocolException;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/* JADX INFO: loaded from: classes4.dex */
public class RedirectInterceptor implements Interceptor {
    private static final int MAX_FOLLOW_UPS = 20;
    private OkHttpClient client;

    public void setClient(OkHttpClient okHttpClient) {
        this.client = okHttpClient;
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        HttpTask httpTask = (HttpTask) TaskManager.getInstance().get((String) request.tag());
        int i = 0;
        Response response = null;
        while (httpTask != null && !httpTask.isCanceled()) {
            Response responseProceed = chain.proceed(request);
            if (response != null) {
                responseProceed = responseProceed.newBuilder().priorResponse(response.newBuilder().body(null).build()).build();
            }
            response = responseProceed;
            request = followUpRequest(response, httpTask.isDomainSwitch(), httpTask.isSelfSigner());
            if (request == null) {
                return response;
            }
            OkhttpInternalUtils.closeQuietly(response.body());
            i++;
            if (i > 20) {
                throw new ProtocolException("Too many follow-up requests: " + i);
            }
        }
        throw new IOException("CANCELED");
    }

    private Request followUpRequest(Response response, boolean z, boolean z2) throws DomainSwitchException {
        HttpUrl httpUrlResolve;
        if (response == null) {
            throw new IllegalStateException();
        }
        int iCode = response.code();
        String strMethod = response.request().method();
        if (iCode != 307 && iCode != 308) {
            switch (iCode) {
                case Constant.EVENT.BLE_CONNECTED /* 300 */:
                case 301:
                case 302:
                case 303:
                    break;
                default:
                    return null;
            }
        } else if (!strMethod.equals("GET") && !strMethod.equals("HEAD")) {
            return null;
        }
        if ((iCode == 301 || iCode == 302 || iCode == 307) && z && !z2 && DomainSwitchUtils.isMyqcloudUrl(response.request().url().host()) && TextUtils.isEmpty(response.header(Headers.REQUEST_ID)) && TextUtils.isEmpty(response.header(Headers.CI_REQUEST_ID))) {
            throw new DomainSwitchException();
        }
        String strHeader = response.header("Location");
        if (strHeader == null || (httpUrlResolve = response.request().url().resolve(strHeader)) == null) {
            return null;
        }
        if (!httpUrlResolve.scheme().equals(response.request().url().scheme()) && !this.client.followSslRedirects()) {
            return null;
        }
        Request.Builder builderNewBuilder = response.request().newBuilder();
        if (OkhttpInternalUtils.permitsRequestBody(strMethod)) {
            boolean zRedirectsWithBody = OkhttpInternalUtils.redirectsWithBody(strMethod);
            if (OkhttpInternalUtils.redirectsToGet(strMethod)) {
                builderNewBuilder.method("GET", null);
            } else {
                builderNewBuilder.method(strMethod, zRedirectsWithBody ? response.request().body() : null);
            }
            if (!zRedirectsWithBody) {
                builderNewBuilder.removeHeader(HttpConstants.Header.TRANSFER_ENCODING);
                builderNewBuilder.removeHeader("Content-Length");
                builderNewBuilder.removeHeader("Content-Type");
            }
        }
        if (!sameConnection(response, httpUrlResolve)) {
            builderNewBuilder.removeHeader("Authorization");
        }
        builderNewBuilder.removeHeader("Host");
        return builderNewBuilder.url(httpUrlResolve).build();
    }

    private boolean sameConnection(Response response, HttpUrl httpUrl) {
        HttpUrl httpUrlUrl = response.request().url();
        return httpUrlUrl.host().equals(httpUrl.host()) && httpUrlUrl.port() == httpUrl.port() && httpUrlUrl.scheme().equals(httpUrl.scheme());
    }
}
