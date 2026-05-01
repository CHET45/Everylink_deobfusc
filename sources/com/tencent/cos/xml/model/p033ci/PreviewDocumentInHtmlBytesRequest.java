package com.tencent.cos.xml.model.p033ci;

import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.object.ObjectRequest;
import com.tencent.cos.xml.utils.DigestUtils;
import com.tencent.qcloud.core.http.RequestBodySerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PreviewDocumentInHtmlBytesRequest extends ObjectRequest {
    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public RequestBodySerializer getRequestBody() throws CosXmlClientException {
        return null;
    }

    public PreviewDocumentInHtmlBytesRequest(String str, String str2) {
        super(str, str2);
        this.queryParameters.put("ci-process", "doc-preview");
        this.queryParameters.put("dstType", "html");
    }

    public PreviewDocumentInHtmlBytesRequest setSrcType(String str) {
        this.queryParameters.put("srcType", str);
        return this;
    }

    public PreviewDocumentInHtmlBytesRequest setCopyable(boolean z) {
        this.queryParameters.put("copyable", z ? "1" : "0");
        return this;
    }

    public PreviewDocumentInHtmlBytesRequest setHtmlParams(String str) {
        this.queryParameters.put("htmlParams", str);
        return this;
    }

    public PreviewDocumentInHtmlBytesRequest setWatermark(String str) throws CosXmlClientException {
        this.queryParameters.put("htmlwaterword", DigestUtils.getSecurityBase64(str));
        return this;
    }

    public PreviewDocumentInHtmlBytesRequest setWatermarkColor(String str) throws CosXmlClientException {
        this.queryParameters.put("htmlfillstyle", DigestUtils.getSecurityBase64(str));
        return this;
    }

    public PreviewDocumentInHtmlBytesRequest setWatermarkFont(String str) throws CosXmlClientException {
        this.queryParameters.put("htmlfront", DigestUtils.getSecurityBase64(str));
        return this;
    }

    public PreviewDocumentInHtmlBytesRequest setWatermarkRotate(int i) {
        this.queryParameters.put("htmlrotate", String.valueOf(i));
        return this;
    }

    public PreviewDocumentInHtmlBytesRequest setWatermarkHorizontal(int i) {
        this.queryParameters.put("htmlhorizontal", String.valueOf(i));
        return this;
    }

    public PreviewDocumentInHtmlBytesRequest setWatermarkVertical(int i) {
        this.queryParameters.put("htmlvertical", String.valueOf(i));
        return this;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "GET";
    }
}
