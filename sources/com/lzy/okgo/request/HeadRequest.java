package com.lzy.okgo.request;

import com.lzy.okgo.utils.HttpUtils;
import okhttp3.Request;
import okhttp3.RequestBody;

/* JADX INFO: loaded from: classes4.dex */
public class HeadRequest extends BaseRequest<HeadRequest> {
    @Override // com.lzy.okgo.request.BaseRequest
    public RequestBody generateRequestBody() {
        return null;
    }

    public HeadRequest(String str) {
        super(str);
        this.method = "HEAD";
    }

    @Override // com.lzy.okgo.request.BaseRequest
    public Request generateRequest(RequestBody requestBody) {
        Request.Builder builderAppendHeaders = HttpUtils.appendHeaders(this.headers);
        this.url = HttpUtils.createUrlFromParams(this.baseUrl, this.params.urlParamsMap);
        return builderAppendHeaders.head().url(this.url).tag(this.tag).build();
    }
}
