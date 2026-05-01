package com.tencent.cos.xml.model.p033ci.p034ai;

import android.text.TextUtils;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.bucket.BucketRequest;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class AILicenseRecRequest extends BucketRequest {
    public String cardType;
    public String ciProcess;
    public String detectUrl;
    protected final String objectKey;

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public RequestBodySerializer getRequestBody() throws CosXmlClientException {
        return null;
    }

    public AILicenseRecRequest(String str, String str2) {
        super(str);
        this.ciProcess = "AILicenseRec";
        this.objectKey = str2;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public Map<String, String> getQueryString() {
        this.queryParameters.put("ci-process", this.ciProcess);
        this.queryParameters.put("detect-url", this.detectUrl);
        this.queryParameters.put("CardType", this.cardType);
        return super.getQueryString();
    }

    @Override // com.tencent.cos.xml.model.bucket.BucketRequest, com.tencent.cos.xml.model.CosXmlRequest
    public String getPath(CosXmlServiceConfig cosXmlServiceConfig) {
        return (!TextUtils.isEmpty(this.detectUrl) || TextUtils.isEmpty(this.objectKey)) ? "/" : "/" + this.objectKey;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "GET";
    }
}
