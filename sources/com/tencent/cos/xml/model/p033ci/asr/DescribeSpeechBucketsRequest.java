package com.tencent.cos.xml.model.p033ci.asr;

import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.qcloud.core.http.RequestBodySerializer;

/* JADX INFO: loaded from: classes4.dex */
public final class DescribeSpeechBucketsRequest extends CosXmlRequest {
    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public RequestBodySerializer getRequestBody() {
        return null;
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
        return "/asrbucket";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getRequestHost(CosXmlServiceConfig cosXmlServiceConfig) {
        return String.format("ci.%s.myqcloud.com", cosXmlServiceConfig.getRegion());
    }
}
