package com.tencent.cos.xml.model.bucket;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class GetBucketObjectVersionsRequest extends BucketRequest {
    private String delimiter;
    private String encodingType;
    private String keyMarker;
    private int maxKeys;
    private String prefix;
    private String versionIdMarker;

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public RequestBodySerializer getRequestBody() throws CosXmlClientException {
        return null;
    }

    public GetBucketObjectVersionsRequest(String str) {
        super(str);
        this.maxKeys = 1000;
    }

    public GetBucketObjectVersionsRequest(String str, String str2, String str3, String str4, String str5) {
        super(str);
        this.maxKeys = 1000;
        this.prefix = str2;
        this.delimiter = str3;
        this.keyMarker = str4;
        this.versionIdMarker = str5;
    }

    public GetBucketObjectVersionsRequest(String str, String str2, String str3, String str4) {
        this(str, "", str2, str3, str4);
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "GET";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public Map<String, String> getQueryString() {
        this.queryParameters.put("versions", null);
        addQuery(Constants.QueryConstants.PREFIX, this.prefix);
        addQuery(Constants.QueryConstants.DELIMITER, this.delimiter);
        addQuery("encoding-type", this.encodingType);
        addQuery("key-marker", this.keyMarker);
        addQuery("version-id-marker", this.versionIdMarker);
        addQuery("max-keys", String.valueOf(this.maxKeys));
        return super.getQueryString();
    }

    public void setPrefix(String str) {
        this.prefix = str;
    }

    public void setDelimiter(String str) {
        this.delimiter = str;
    }

    public void setEncodingType(String str) {
        this.encodingType = str;
    }

    public void setKeyMarker(String str) {
        this.keyMarker = str;
    }

    public void setVersionIdMarker(String str) {
        this.versionIdMarker = str;
    }

    public void setMaxKeys(int i) {
        this.maxKeys = i;
    }

    public String getDelimiter() {
        return this.delimiter;
    }

    public String getKeyMarker() {
        return this.keyMarker;
    }

    public String getVersionIdMarker() {
        return this.versionIdMarker;
    }
}
