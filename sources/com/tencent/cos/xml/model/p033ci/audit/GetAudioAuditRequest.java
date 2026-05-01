package com.tencent.cos.xml.model.p033ci.audit;

import com.tencent.cos.xml.CosXmlServiceConfig;

/* JADX INFO: loaded from: classes4.dex */
public class GetAudioAuditRequest extends GetAuditRequest {
    public GetAudioAuditRequest(String str, String str2) {
        super(str, str2);
    }

    @Override // com.tencent.cos.xml.model.bucket.BucketRequest, com.tencent.cos.xml.model.CosXmlRequest
    public String getPath(CosXmlServiceConfig cosXmlServiceConfig) {
        return "/audio/auditing/" + this.jobId;
    }
}
