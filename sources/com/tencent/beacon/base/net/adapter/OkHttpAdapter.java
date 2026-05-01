package com.tencent.beacon.base.net.adapter;

import com.tencent.beacon.base.net.BResponse;
import com.tencent.beacon.base.net.BodyType;
import com.tencent.beacon.base.net.call.Callback;
import com.tencent.beacon.base.net.call.HttpRequestEntity;
import com.tencent.beacon.base.net.call.JceRequestEntity;
import com.tencent.beacon.base.net.p021b.C2669d;
import com.tencent.qcloud.core.http.HttpConstants;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/* JADX INFO: loaded from: classes4.dex */
public class OkHttpAdapter extends AbstractNetAdapter {
    private OkHttpClient client;
    private int failCount;

    private OkHttpAdapter(OkHttpClient okHttpClient) {
        this.client = okHttpClient;
    }

    static /* synthetic */ int access$008(OkHttpAdapter okHttpAdapter) {
        int i = okHttpAdapter.failCount;
        okHttpAdapter.failCount = i + 1;
        return i;
    }

    private RequestBody buildBody(HttpRequestEntity httpRequestEntity) {
        BodyType bodyType = httpRequestEntity.bodyType();
        int i = C2649e.f1133a[bodyType.ordinal()];
        if (i == 1) {
            return RequestBody.create(MediaType.parse(bodyType.httpType), C2669d.m1339b(httpRequestEntity.formParams()));
        }
        if (i == 2) {
            return RequestBody.create(MediaType.parse(bodyType.httpType), httpRequestEntity.json());
        }
        if (i != 3) {
            return null;
        }
        return RequestBody.create(MediaType.parse(HttpConstants.ContentType.MULTIPART_FORM_DATA), httpRequestEntity.content());
    }

    public static AbstractNetAdapter create(OkHttpClient okHttpClient) {
        return okHttpClient != null ? new OkHttpAdapter(okHttpClient) : new OkHttpAdapter();
    }

    private Headers mapToHeaders(Map<String, String> map) {
        Headers.Builder builder = new Headers.Builder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue() != null) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }

    @Override // com.tencent.beacon.base.net.adapter.AbstractNetAdapter
    public void request(JceRequestEntity jceRequestEntity, Callback<byte[]> callback) {
        RequestBody requestBodyCreate = RequestBody.create(MediaType.parse("jce"), jceRequestEntity.getContent());
        Headers headersMapToHeaders = mapToHeaders(jceRequestEntity.getHeader());
        String strName = jceRequestEntity.getType().name();
        this.client.newCall(new Request.Builder().url(jceRequestEntity.getUrl()).tag(strName).post(requestBodyCreate).headers(headersMapToHeaders).build()).enqueue(new C2647c(this, callback, strName));
    }

    private OkHttpAdapter() {
        this.client = new OkHttpClient.Builder().connectTimeout(30000L, TimeUnit.MILLISECONDS).readTimeout(10000L, TimeUnit.MILLISECONDS).build();
    }

    @Override // com.tencent.beacon.base.net.adapter.AbstractNetAdapter
    public void request(HttpRequestEntity httpRequestEntity, Callback<BResponse> callback) {
        String strTag = httpRequestEntity.tag();
        this.client.newCall(new Request.Builder().url(httpRequestEntity.url()).method(httpRequestEntity.method().name(), buildBody(httpRequestEntity)).headers(mapToHeaders(httpRequestEntity.headers())).tag(strTag == null ? "beacon" : strTag).build()).enqueue(new C2648d(this, callback, strTag));
    }
}
