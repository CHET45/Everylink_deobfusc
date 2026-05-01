package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class GetAIBucketRequest extends CosXmlRequest {
    public String bucketName;
    public String bucketNames;
    public int pageNumber;
    public int pageSize;
    public String regions;

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public RequestBodySerializer getRequestBody() throws CosXmlClientException {
        return null;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public Map<String, String> getQueryString() {
        if (this.regions != null) {
            this.queryParameters.put("regions", this.regions);
        }
        if (this.bucketNames != null) {
            this.queryParameters.put("bucketNames", this.bucketNames);
        }
        if (this.bucketName != null) {
            this.queryParameters.put("bucketName", this.bucketName);
        }
        this.queryParameters.put("pageNumber", String.valueOf(this.pageNumber));
        this.queryParameters.put("pageSize", String.valueOf(this.pageSize));
        return super.getQueryString();
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getPath(CosXmlServiceConfig cosXmlServiceConfig) {
        return "/ai_bucket";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "GET";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getRequestHost(CosXmlServiceConfig cosXmlServiceConfig) {
        return cosXmlServiceConfig.getRequestHost(this.region, CosXmlServiceConfig.CI_REGION_HOST_FORMAT);
    }
}
