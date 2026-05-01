package com.tencent.cos.xml.model.p033ci;

import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.model.object.GetObjectResult;
import com.tencent.qcloud.core.http.HttpResponse;

/* JADX INFO: loaded from: classes4.dex */
public class PreviewDocumentResult extends GetObjectResult {
    private String contentType;
    private String errNo;
    private String previewFilePath;
    private String sheetName;
    private int totalPage;
    private int totalSheet;

    public PreviewDocumentResult(String str) {
        this.previewFilePath = str;
    }

    @Override // com.tencent.cos.xml.model.object.GetObjectResult, com.tencent.cos.xml.model.CosXmlResult
    public void parseResponseBody(HttpResponse httpResponse) throws CosXmlServiceException, CosXmlClientException {
        super.parseResponseBody(httpResponse);
        String strHeader = httpResponse.header("X-Total-Page");
        if (strHeader != null) {
            try {
                this.totalPage = Integer.parseInt(strHeader);
            } catch (Error unused) {
            }
        }
        this.contentType = httpResponse.header("Content-Type");
        this.errNo = httpResponse.header("X-ErrNo\t");
        String strHeader2 = httpResponse.header("X-Total-Sheet");
        if (strHeader2 != null) {
            try {
                this.totalSheet = Integer.parseInt(strHeader2);
            } catch (Error unused2) {
            }
        }
        this.sheetName = httpResponse.header("X-Sheet-Name");
    }

    public int getTotalPage() {
        return this.totalPage;
    }

    public String getContentType() {
        return this.contentType;
    }

    public String getErrNo() {
        return this.errNo;
    }

    public int getTotalSheet() {
        return this.totalSheet;
    }

    public String getSheetName() {
        return this.sheetName;
    }

    public String getPreviewFilePath() {
        return this.previewFilePath;
    }
}
