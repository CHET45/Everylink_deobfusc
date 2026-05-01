package com.tencent.cos.xml.model.object;

import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public final class GetObjectTaggingRequest extends ObjectRequest {
    private String versionId;

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public RequestBodySerializer getRequestBody() {
        return null;
    }

    public GetObjectTaggingRequest(String str, String str2) {
        super(str, str2);
    }

    public void setVersionId(String str) {
        this.versionId = str;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "GET";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public Map<String, String> getQueryString() {
        this.queryParameters.put("tagging", null);
        if (this.versionId != null) {
            this.queryParameters.put("versionId", this.versionId);
        }
        return super.getQueryString();
    }
}
