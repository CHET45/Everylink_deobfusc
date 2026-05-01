package com.tencent.cos.xml.model.p033ci;

import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.bucket.BucketRequest;
import com.tencent.qcloud.core.http.RequestBodySerializer;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BaseDescribeQueuesRequest extends BucketRequest {
    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public RequestBodySerializer getRequestBody() throws CosXmlClientException {
        return null;
    }

    public BaseDescribeQueuesRequest(String str) {
        super(str);
    }

    public BaseDescribeQueuesRequest(String str, String str2) {
        super(str);
        this.region = str2;
    }

    public void setQueueIds(String str) {
        this.queryParameters.put("queueIds", str);
    }

    public void setState(String str) {
        this.queryParameters.put("state", str);
    }

    public void setPageNumber(int i) {
        this.queryParameters.put("pageNumber", String.valueOf(i));
    }

    public void setPageSize(int i) {
        this.queryParameters.put("pageSize", String.valueOf(i));
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "GET";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getRequestHost(CosXmlServiceConfig cosXmlServiceConfig) {
        return cosXmlServiceConfig.getRequestHost(this.region, this.bucket, CosXmlServiceConfig.CI_HOST_FORMAT);
    }
}
