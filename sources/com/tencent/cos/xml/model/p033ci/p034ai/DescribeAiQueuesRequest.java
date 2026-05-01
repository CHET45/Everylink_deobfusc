package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.model.p033ci.BaseDescribeQueuesRequest;

/* JADX INFO: loaded from: classes4.dex */
public final class DescribeAiQueuesRequest extends BaseDescribeQueuesRequest {
    public DescribeAiQueuesRequest(String str) {
        super(str);
    }

    public DescribeAiQueuesRequest(String str, String str2) {
        super(str);
        this.region = str2;
    }

    @Override // com.tencent.cos.xml.model.bucket.BucketRequest, com.tencent.cos.xml.model.CosXmlRequest
    public String getPath(CosXmlServiceConfig cosXmlServiceConfig) {
        return "/ai_queue";
    }
}
