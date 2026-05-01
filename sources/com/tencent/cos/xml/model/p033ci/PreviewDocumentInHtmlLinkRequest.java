package com.tencent.cos.xml.model.p033ci;

import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.qcloud.core.http.RequestBodySerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PreviewDocumentInHtmlLinkRequest extends PreviewDocumentInHtmlBytesRequest {
    @Override // com.tencent.cos.xml.model.p033ci.PreviewDocumentInHtmlBytesRequest, com.tencent.cos.xml.model.CosXmlRequest
    public RequestBodySerializer getRequestBody() throws CosXmlClientException {
        return null;
    }

    public PreviewDocumentInHtmlLinkRequest(String str, String str2) {
        super(str, str2);
        this.queryParameters.put("weboffice_url", "1");
    }

    @Override // com.tencent.cos.xml.model.p033ci.PreviewDocumentInHtmlBytesRequest, com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "GET";
    }
}
