package com.tencent.qcloud.core.http;

import com.tencent.qcloud.core.http.QCloudHttpClient;
import com.tencent.qcloud.core.http.interceptor.QCloudRetryInterceptor;
import com.tencent.qcloud.core.http.interceptor.QCloudTrafficControlInterceptor;
import com.tencent.qcloud.core.task.RetryStrategy;
import javax.net.ssl.HostnameVerifier;
import okhttp3.Dns;

/* JADX INFO: loaded from: classes4.dex */
public abstract class NetworkClient {
    protected Dns dns;
    protected HttpLogger httpLogger;
    private QCloudRetryInterceptor retryInterceptor;
    protected RetryStrategy retryStrategy;

    public abstract NetworkProxy getNetworkProxy();

    public void enableQCloudInterceptor() {
        this.retryInterceptor = new QCloudRetryInterceptor(this.retryStrategy, new QCloudTrafficControlInterceptor());
    }

    public void init(QCloudHttpClient.Builder builder, HostnameVerifier hostnameVerifier, Dns dns, HttpLogger httpLogger) {
        this.retryStrategy = builder.retryStrategy;
        this.httpLogger = httpLogger;
        this.dns = dns;
    }

    public NetworkProxy getNetworkProxyWrapper() {
        NetworkProxy networkProxy = getNetworkProxy();
        networkProxy.setRetryInterceptor(this.retryInterceptor);
        return networkProxy;
    }
}
