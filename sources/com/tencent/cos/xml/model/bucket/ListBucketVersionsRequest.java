package com.tencent.cos.xml.model.bucket;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
@Deprecated
public class ListBucketVersionsRequest extends BucketRequest {
    private String delimiter;
    private String encodingType;
    private String keyMarker;
    private String maxKeys;
    private String prefix;
    private String versionIdMarker;

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public RequestBodySerializer getRequestBody() throws CosXmlClientException {
        return null;
    }

    public ListBucketVersionsRequest(String str) {
        super(str);
        this.maxKeys = "1000";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "GET";
    }

    public void setPrefix(String str) {
        if (str != null) {
            this.prefix = str;
        }
    }

    public void setKeyMarker(String str) {
        if (str != null) {
            this.keyMarker = str;
        }
    }

    public void setVersionIdMarker(String str) {
        if (str != null) {
            this.versionIdMarker = str;
        }
    }

    public void setDelimiter(String str) {
        this.delimiter = str;
    }

    public void setEncodingType(String str) {
        this.encodingType = str;
    }

    public void setMaxKeys(int i) {
        this.maxKeys = String.valueOf(i);
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public Map<String, String> getQueryString() {
        this.queryParameters.put("versions", null);
        if (this.prefix != null) {
            this.queryParameters.put(Constants.QueryConstants.PREFIX, this.prefix);
        }
        if (this.keyMarker != null) {
            this.queryParameters.put("key-marker", this.keyMarker);
        }
        if (this.versionIdMarker != null) {
            this.queryParameters.put("version-id-marker", this.versionIdMarker);
        }
        if (this.delimiter != null) {
            this.queryParameters.put(Constants.QueryConstants.DELIMITER, this.delimiter);
        }
        if (this.encodingType != null) {
            this.queryParameters.put("encoding-type", this.encodingType);
        }
        if (!this.maxKeys.equals("1000")) {
            this.queryParameters.put("max-keys", this.maxKeys);
        }
        return super.getQueryString();
    }
}
