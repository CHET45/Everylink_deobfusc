package com.tencent.cos.xml.model.bucket;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.qcloud.core.auth.STSCredentialScope;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public final class GetBucketRequest extends BucketRequest {
    private String delimiter;
    private String encodingType;
    private String marker;
    private String maxKeys;
    private String prefix;

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public RequestBodySerializer getRequestBody() {
        return null;
    }

    public GetBucketRequest(String str) {
        super(str);
        this.prefix = null;
        this.delimiter = null;
        this.marker = null;
        this.maxKeys = "1000";
    }

    public GetBucketRequest(String str, String str2, String str3) {
        super(str2);
        this.prefix = null;
        this.delimiter = null;
        this.marker = null;
        this.maxKeys = "1000";
        this.region = str;
        this.prefix = str3;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "GET";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public Map<String, String> getQueryString() {
        if (this.prefix != null) {
            this.queryParameters.put(Constants.QueryConstants.PREFIX, this.prefix);
        }
        if (this.delimiter != null) {
            this.queryParameters.put(Constants.QueryConstants.DELIMITER, this.delimiter);
        }
        if (this.encodingType != null) {
            this.queryParameters.put("encoding-type", this.encodingType);
        }
        if (this.marker != null) {
            this.queryParameters.put(Constants.QueryConstants.MARKER, this.marker);
        }
        if (this.maxKeys != null) {
            this.queryParameters.put("max-keys", this.maxKeys);
        }
        if (this.prefix != null) {
            this.queryParameters.put(Constants.QueryConstants.PREFIX, this.prefix);
        }
        return super.getQueryString();
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public STSCredentialScope[] getSTSCredentialScope(CosXmlServiceConfig cosXmlServiceConfig) {
        return new STSCredentialScope("name/cos:" + getClass().getSimpleName().replace("Request", ""), cosXmlServiceConfig.getBucket(this.bucket), cosXmlServiceConfig.getRegion(), this.prefix == null ? null : cosXmlServiceConfig.getUrlPath(this.bucket, this.prefix)).toArray();
    }

    public void setPrefix(String str) {
        this.prefix = str;
    }

    public String getPrefix() {
        return this.prefix;
    }

    @Deprecated
    public void setDelimiter(char c) {
        setDelimiter(String.valueOf(c));
    }

    public void setDelimiter(String str) {
        this.delimiter = str;
    }

    public String getDelimiter() {
        return this.delimiter;
    }

    public void setEncodingType(String str) {
        this.encodingType = str;
    }

    public String getEncodingType() {
        return this.encodingType;
    }

    public void setMarker(String str) {
        this.marker = str;
    }

    public String getMarker() {
        return this.marker;
    }

    public void setMaxKeys(long j) {
        this.maxKeys = String.valueOf(j);
    }

    public long getMaxKeys() {
        return Long.parseLong(this.maxKeys);
    }
}
