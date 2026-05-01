package com.tencent.qcloud.core.http;

import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.OkHttpClient;

/* JADX INFO: loaded from: classes4.dex */
public class OkHttpClientImpl extends NetworkClient {
    private EventListener.Factory mEventListenerFactory = new EventListener.Factory() { // from class: com.tencent.qcloud.core.http.OkHttpClientImpl.1
        @Override // okhttp3.EventListener.Factory
        public EventListener create(Call call) {
            return new CallMetricsListener(call);
        }
    };
    private OkHttpClient okHttpClient;

    /* JADX WARN: Removed duplicated region for block: B:32:0x00a7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // com.tencent.qcloud.core.http.NetworkClient
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void init(com.tencent.qcloud.core.http.QCloudHttpClient.Builder r9, javax.net.ssl.HostnameVerifier r10, okhttp3.Dns r11, com.tencent.qcloud.core.http.HttpLogger r12) {
        /*
            Method dump skipped, instruction units count: 276
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qcloud.core.http.OkHttpClientImpl.init(com.tencent.qcloud.core.http.QCloudHttpClient$Builder, javax.net.ssl.HostnameVerifier, okhttp3.Dns, com.tencent.qcloud.core.http.HttpLogger):void");
    }

    @Override // com.tencent.qcloud.core.http.NetworkClient
    public NetworkProxy getNetworkProxy() {
        return new OkHttpProxy(this.okHttpClient);
    }
}
