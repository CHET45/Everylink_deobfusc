package com.tencent.cos.xml.model.bucket;

import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.tag.IntelligentTieringConfiguration;
import com.tencent.cos.xml.utils.QCloudXmlUtils;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class PutBucketIntelligentTieringRequest extends BucketRequest {
    public static final String STATUS_ENABLED = "Enabled";
    public static final String STATUS_SUSPEND = "Suspended";
    private IntelligentTieringConfiguration configuration;

    public PutBucketIntelligentTieringRequest(String str) {
        super(str);
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "PUT";
    }

    public void setConfiguration(IntelligentTieringConfiguration intelligentTieringConfiguration) {
        this.configuration = intelligentTieringConfiguration;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public Map<String, String> getQueryString() {
        this.queryParameters.put("intelligenttiering", null);
        return super.getQueryString();
    }

    @Override // com.tencent.cos.xml.model.bucket.BucketRequest, com.tencent.cos.xml.model.CosXmlRequest
    public void checkParameters() throws CosXmlClientException {
        super.checkParameters();
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public RequestBodySerializer getRequestBody() throws CosXmlClientException {
        return RequestBodySerializer.bytes("text/plain", QCloudXmlUtils.toXml(this.configuration).getBytes(), 0L, r0.length());
    }
}
