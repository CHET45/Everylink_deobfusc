package com.tencent.cos.xml.model.p033ci.p034ai;

import android.text.TextUtils;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.bucket.BucketRequest;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class GetSearchImageRequest extends BucketRequest {
    public String action;
    public String ciProcess;
    public String filter;
    public int limit;
    public int matchThreshold;
    protected final String objectKey;
    public int offset;

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public RequestBodySerializer getRequestBody() throws CosXmlClientException {
        return null;
    }

    public GetSearchImageRequest(String str, String str2) {
        super(str);
        this.ciProcess = "ImageSearch";
        this.action = "SearchImage";
        this.objectKey = str2;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public Map<String, String> getQueryString() {
        this.queryParameters.put("ci-process", this.ciProcess);
        this.queryParameters.put("action", this.action);
        this.queryParameters.put("MatchThreshold", String.valueOf(this.matchThreshold));
        this.queryParameters.put("Offset", String.valueOf(this.offset));
        this.queryParameters.put("Limit", String.valueOf(this.limit));
        if (!TextUtils.isEmpty(this.filter)) {
            this.queryParameters.put("Filter", this.filter);
        }
        return super.getQueryString();
    }

    @Override // com.tencent.cos.xml.model.bucket.BucketRequest, com.tencent.cos.xml.model.CosXmlRequest
    public String getPath(CosXmlServiceConfig cosXmlServiceConfig) {
        return "/" + this.objectKey;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "GET";
    }
}
