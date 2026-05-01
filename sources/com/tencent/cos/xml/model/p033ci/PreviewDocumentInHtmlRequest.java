package com.tencent.cos.xml.model.p033ci;

import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.object.GetObjectRequest;
import com.tencent.cos.xml.utils.DigestUtils;
import com.tencent.cos.xml.utils.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class PreviewDocumentInHtmlRequest extends GetObjectRequest {
    public PreviewDocumentInHtmlRequest(String str, String str2, String str3) {
        this(str, str2, str3, StringUtils.extractNameNoSuffix(str2));
    }

    public PreviewDocumentInHtmlRequest(String str, String str2, String str3, String str4) {
        super(str, str2, str3, str4);
        this.queryParameters.put("ci-process", "doc-preview");
        this.queryParameters.put("dstType", "html");
    }

    public PreviewDocumentInHtmlRequest setSrcType(String str) {
        this.queryParameters.put("srcType", str);
        return this;
    }

    public PreviewDocumentInHtmlRequest setCopyable(boolean z) {
        this.queryParameters.put("copyable", z ? "1" : "0");
        return this;
    }

    public PreviewDocumentInHtmlRequest setHtmlParams(String str) {
        this.queryParameters.put("htmlParams", str);
        return this;
    }

    public PreviewDocumentInHtmlRequest setWatermark(String str) throws CosXmlClientException {
        this.queryParameters.put("htmlwaterword", DigestUtils.getSecurityBase64(str));
        return this;
    }

    public PreviewDocumentInHtmlRequest setWatermarkColor(String str) throws CosXmlClientException {
        this.queryParameters.put("htmlfillstyle", DigestUtils.getSecurityBase64(str));
        return this;
    }

    public PreviewDocumentInHtmlRequest setWatermarkFont(String str) throws CosXmlClientException {
        this.queryParameters.put("htmlfront", DigestUtils.getSecurityBase64(str));
        return this;
    }

    public PreviewDocumentInHtmlRequest setWatermarkRotate(int i) {
        this.queryParameters.put("htmlrotate", String.valueOf(i));
        return this;
    }

    public PreviewDocumentInHtmlRequest setWatermarkHorizontal(int i) {
        this.queryParameters.put("htmlhorizontal", String.valueOf(i));
        return this;
    }

    public PreviewDocumentInHtmlRequest setWatermarkVertical(int i) {
        this.queryParameters.put("htmlvertical", String.valueOf(i));
        return this;
    }

    @Override // com.tencent.cos.xml.model.object.GetObjectRequest, com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "GET";
    }
}
