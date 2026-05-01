package com.tencent.cos.xml.model;

import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.common.ClientErrorCode;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.qcloud.core.auth.STSCredentialScope;
import com.tencent.qcloud.core.http.RequestBodySerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PresignedUrlRequest extends CosXmlRequest {
    private String cosPath;
    private String requestMethod = "GET";

    public PresignedUrlRequest(String str, String str2) {
        this.cosPath = "/";
        this.bucket = str;
        this.cosPath = str2;
    }

    public void setRequestMethod(String str) {
        this.requestMethod = str;
    }

    public void setCosPath(String str) {
        if (str != null) {
            if (!str.startsWith("/")) {
                this.cosPath = "/" + str;
            } else {
                this.cosPath = str;
            }
        }
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return this.requestMethod;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getPath(CosXmlServiceConfig cosXmlServiceConfig) {
        return cosXmlServiceConfig.getUrlPath(this.bucket, this.cosPath);
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public RequestBodySerializer getRequestBody() throws CosXmlClientException {
        if (this.requestMethod.equals("POST")) {
            return RequestBodySerializer.bytes(null, new byte[0]);
        }
        return null;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public void checkParameters() throws CosXmlClientException {
        if (this.bucket == null || this.bucket.length() < 1) {
            throw new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), "bucket must not be null ");
        }
        String str = this.cosPath;
        if (str == null || str.length() < 1) {
            throw new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), "cosPath must not be null ");
        }
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public STSCredentialScope[] getSTSCredentialScope(CosXmlServiceConfig cosXmlServiceConfig) {
        return new STSCredentialScope("name/cos:".concat("PUT".equals(this.requestMethod) ? "PutObject" : "GetObject"), cosXmlServiceConfig.getBucket(this.bucket), cosXmlServiceConfig.getRegion(), getPath(cosXmlServiceConfig)).toArray();
    }
}
