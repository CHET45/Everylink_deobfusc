package com.tencent.cos.xml.model.p033ci;

import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.qcloud.core.http.RequestBodySerializer;

/* JADX INFO: loaded from: classes4.dex */
public class DescribeDocProcessBucketsRequest extends CosXmlRequest {
    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public RequestBodySerializer getRequestBody() throws CosXmlClientException {
        return null;
    }

    public DescribeDocProcessBucketsRequest() {
    }

    public DescribeDocProcessBucketsRequest(String str) {
        this.region = str;
    }

    public void setRegions(String str) {
        this.queryParameters.put("regions", str);
    }

    public void setBucketNames(String str) {
        this.queryParameters.put("bucketNames", str);
    }

    public void setBucketName(String str) {
        this.queryParameters.put("bucketName", str);
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
    public String getPath(CosXmlServiceConfig cosXmlServiceConfig) {
        return "/docbucket";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getRequestHost(CosXmlServiceConfig cosXmlServiceConfig) {
        return cosXmlServiceConfig.getRequestHost(this.region, CosXmlServiceConfig.CI_REGION_HOST_FORMAT);
    }
}
