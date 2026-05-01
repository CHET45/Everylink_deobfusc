package com.tencent.cos.xml.model.bucket;

import com.microsoft.azure.storage.Constants;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public final class ListMultiUploadsRequest extends BucketRequest {
    private String delimiter;
    private String encodingType;
    private String keyMarker;
    private String maxUploads;
    private String prefix;
    private String uploadIdMarker;

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public RequestBodySerializer getRequestBody() {
        return null;
    }

    public ListMultiUploadsRequest(String str) {
        super(str);
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "GET";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public Map<String, String> getQueryString() {
        this.queryParameters.put("uploads", null);
        if (this.delimiter != null) {
            this.queryParameters.put(Constants.QueryConstants.DELIMITER, this.delimiter);
        }
        if (this.encodingType != null) {
            this.queryParameters.put("encoding-type", this.encodingType);
        }
        if (this.prefix != null) {
            this.queryParameters.put(Constants.QueryConstants.PREFIX, this.prefix);
        }
        if (this.maxUploads != null) {
            this.queryParameters.put("max-uploads", this.maxUploads);
        }
        if (this.keyMarker != null) {
            this.queryParameters.put("key-marker", this.keyMarker);
        }
        if (this.uploadIdMarker != null) {
            this.queryParameters.put("upload-id-marker", this.uploadIdMarker);
        }
        return super.getQueryString();
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

    public void setKeyMarker(String str) {
        this.keyMarker = str;
    }

    public String getKeyMarker() {
        return this.keyMarker;
    }

    public void setMaxUploads(String str) {
        this.maxUploads = str;
    }

    public String getMaxUploads() {
        return this.maxUploads;
    }

    public void setPrefix(String str) {
        this.prefix = str;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setUploadIdMarker(String str) {
        this.uploadIdMarker = str;
    }

    public String getUploadIdMarker() {
        return this.uploadIdMarker;
    }
}
