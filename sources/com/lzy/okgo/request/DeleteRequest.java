package com.lzy.okgo.request;

import com.lzy.okgo.utils.HttpUtils;
import com.lzy.okgo.utils.OkLogger;
import java.io.IOException;
import okhttp3.Request;
import okhttp3.RequestBody;

/* JADX INFO: loaded from: classes4.dex */
public class DeleteRequest extends BaseBodyRequest<DeleteRequest> {
    public DeleteRequest(String str) {
        super(str);
        this.method = "DELETE";
    }

    @Override // com.lzy.okgo.request.BaseRequest
    public Request generateRequest(RequestBody requestBody) {
        try {
            this.headers.put("Content-Length", String.valueOf(requestBody.contentLength()));
        } catch (IOException e) {
            OkLogger.m864e(e);
        }
        return HttpUtils.appendHeaders(this.headers).delete(requestBody).url(this.url).tag(this.tag).build();
    }
}
