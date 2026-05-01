package com.tencent.cos.xml.model.bucket;

import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class ListBucketInventoryRequest extends BucketRequest {
    private String continuationToken;

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public RequestBodySerializer getRequestBody() throws CosXmlClientException {
        return null;
    }

    public ListBucketInventoryRequest(String str) {
        super(str);
    }

    public void setContinuationToken(String str) {
        this.continuationToken = str;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "GET";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public Map<String, String> getQueryString() {
        this.queryParameters.put("inventory", null);
        if (this.continuationToken != null) {
            this.queryParameters.put("continuation-token", this.continuationToken);
        }
        return super.getQueryString();
    }
}
