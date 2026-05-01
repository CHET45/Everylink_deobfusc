package com.tencent.cos.xml.model.object;

import com.tencent.cos.xml.common.COSRequestHeaderKey;
import com.tencent.cos.xml.common.ClientErrorCode;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public final class OptionObjectRequest extends ObjectRequest {
    private String accessControlHeaders;
    private String accessControlMethod;
    private String origin;

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public RequestBodySerializer getRequestBody() {
        return null;
    }

    public OptionObjectRequest(String str, String str2, String str3, String str4) {
        super(str, str2);
        this.origin = str3;
        this.accessControlMethod = str4;
        setOrigin(str3);
        setAccessControlMethod(str4);
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "OPTIONS";
    }

    @Override // com.tencent.cos.xml.model.object.ObjectRequest, com.tencent.cos.xml.model.CosXmlRequest
    public void checkParameters() throws CosXmlClientException {
        super.checkParameters();
        if (this.origin == null) {
            throw new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), "option request origin must not be null");
        }
        if (this.accessControlMethod == null) {
            throw new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), "option request accessControlMethod must not be null");
        }
    }

    public void setOrigin(String str) {
        this.origin = str;
        if (str != null) {
            addHeader(COSRequestHeaderKey.ORIGIN, str);
        }
    }

    public String getOrigin() {
        return this.origin;
    }

    public void setAccessControlMethod(String str) {
        if (str != null) {
            String upperCase = str.toUpperCase(Locale.ROOT);
            this.accessControlMethod = upperCase;
            addHeader(COSRequestHeaderKey.ACCESS_CONTROL_REQUEST_METHOD, upperCase);
        }
    }

    public String getAccessControlMethod() {
        return this.accessControlMethod;
    }

    public void setAccessControlHeaders(String str) {
        this.accessControlHeaders = str;
        if (str != null) {
            addHeader(COSRequestHeaderKey.ACCESS_CONTROL_REQUEST_HEADERS, str);
        }
    }

    public String getAccessControlHeaders() {
        return this.accessControlHeaders;
    }
}
