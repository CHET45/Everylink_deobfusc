package com.lzy.okgo.request;

import com.lzy.okgo.utils.HttpUtils;
import okhttp3.Request;
import okhttp3.RequestBody;

/* JADX INFO: loaded from: classes4.dex */
public class GetRequest extends BaseRequest<GetRequest> {
    @Override // com.lzy.okgo.request.BaseRequest
    public RequestBody generateRequestBody() {
        return null;
    }

    public GetRequest(String str) {
        super(str);
        this.method = "GET";
    }

    @Override // com.lzy.okgo.request.BaseRequest
    public Request generateRequest(RequestBody requestBody) {
        Request.Builder builderAppendHeaders = HttpUtils.appendHeaders(this.headers);
        this.url = HttpUtils.createUrlFromParams(this.baseUrl, this.params.urlParamsMap);
        return builderAppendHeaders.get().url(this.url).tag(this.tag).build();
    }
}
