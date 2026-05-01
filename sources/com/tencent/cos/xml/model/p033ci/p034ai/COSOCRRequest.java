package com.tencent.cos.xml.model.p033ci.p034ai;

import com.microsoft.azure.storage.table.TableConstants;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.bucket.BucketRequest;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class COSOCRRequest extends BucketRequest {
    public String ciProcess;
    public String detectUrl;
    public boolean enableWordPolygon;
    public boolean ispdf;
    public boolean isword;
    public String languageType;
    protected final String objectKey;
    public int pdfPagenumber;
    public String type;

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public RequestBodySerializer getRequestBody() throws CosXmlClientException {
        return null;
    }

    public COSOCRRequest(String str, String str2) {
        super(str);
        this.ciProcess = "OCR";
        this.objectKey = str2;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public Map<String, String> getQueryString() {
        this.queryParameters.put("ci-process", this.ciProcess);
        this.queryParameters.put("detect-url", this.detectUrl);
        this.queryParameters.put(TableConstants.ErrorConstants.ERROR_EXCEPTION_TYPE, this.type);
        this.queryParameters.put("language-type", this.languageType);
        this.queryParameters.put("ispdf", String.valueOf(this.ispdf));
        this.queryParameters.put("pdf-pagenumber", String.valueOf(this.pdfPagenumber));
        this.queryParameters.put("isword", String.valueOf(this.isword));
        this.queryParameters.put("enable-word-polygon", String.valueOf(this.enableWordPolygon));
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
