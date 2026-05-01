package com.tencent.cos.xml.model.p033ci.asr;

import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.model.p033ci.BaseDescribeQueuesRequest;

/* JADX INFO: loaded from: classes4.dex */
public final class DescribeSpeechQueuesRequest extends BaseDescribeQueuesRequest {
    public DescribeSpeechQueuesRequest(String str) {
        super(str);
    }

    public DescribeSpeechQueuesRequest(String str, String str2) {
        super(str, str2);
    }

    @Override // com.tencent.cos.xml.model.bucket.BucketRequest, com.tencent.cos.xml.model.CosXmlRequest
    public String getPath(CosXmlServiceConfig cosXmlServiceConfig) {
        return "/asrqueue";
    }
}
