package com.tencent.cos.xml.model.p033ci;

import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.object.GetObjectRequest;

/* JADX INFO: loaded from: classes4.dex */
public class PreviewDocumentRequest extends GetObjectRequest {
    private int page;

    public PreviewDocumentRequest(String str, String str2, String str3, int i) {
        this(str, str2, str3, i + ".jpg", i);
    }

    public PreviewDocumentRequest(String str, String str2, String str3, String str4, int i) {
        super(str, str2, str3, str4);
        this.queryParameters.put("ci-process", "doc-preview");
        this.page = i;
        this.queryParameters.put("page", String.valueOf(i));
    }

    public PreviewDocumentRequest setSrcType(String str) {
        this.queryParameters.put("srcType", str);
        return this;
    }

    public PreviewDocumentRequest setDstType(String str) {
        this.queryParameters.put("dstType", str);
        return this;
    }

    public PreviewDocumentRequest setPassword(String str) {
        this.queryParameters.put("password", str);
        return this;
    }

    public PreviewDocumentRequest setComment(int i) {
        this.queryParameters.put("comment", String.valueOf(i));
        return this;
    }

    public PreviewDocumentRequest setExcelSheet(int i) {
        this.queryParameters.put("sheet", String.valueOf(i));
        return this;
    }

    public PreviewDocumentRequest setExcelPaperDirection(int i) {
        this.queryParameters.put("excelPaperDirection", String.valueOf(i));
        return this;
    }

    public PreviewDocumentRequest setExcelRow(int i) {
        this.queryParameters.put("excelRow", String.valueOf(i));
        return this;
    }

    public PreviewDocumentRequest setExcelCol(int i) {
        this.queryParameters.put("excelCol", String.valueOf(i));
        return this;
    }

    public PreviewDocumentRequest setExcelPaperSize(int i) {
        this.queryParameters.put("excelPaperSize", String.valueOf(i));
        return this;
    }

    public PreviewDocumentRequest setTxtPagination(boolean z) {
        this.queryParameters.put("txtPagination", String.valueOf(z));
        return this;
    }

    public PreviewDocumentRequest setImageParams(String str) {
        this.queryParameters.put("ImageParams", String.valueOf(str));
        return this;
    }

    public PreviewDocumentRequest setQuality(int i) {
        this.queryParameters.put("quality", String.valueOf(i));
        return this;
    }

    public PreviewDocumentRequest setScale(int i) {
        this.queryParameters.put("scale", String.valueOf(i));
        return this;
    }

    public PreviewDocumentRequest setImageDpi(int i) {
        this.queryParameters.put("imageDpi", String.valueOf(i));
        return this;
    }

    @Override // com.tencent.cos.xml.model.object.GetObjectRequest, com.tencent.cos.xml.model.object.ObjectRequest, com.tencent.cos.xml.model.CosXmlRequest
    public void checkParameters() throws CosXmlClientException {
        super.checkParameters();
    }

    @Override // com.tencent.cos.xml.model.object.GetObjectRequest, com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "GET";
    }
}
