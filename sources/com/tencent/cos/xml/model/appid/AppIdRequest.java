package com.tencent.cos.xml.model.appid;

import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.common.ClientErrorCode;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.CosXmlRequest;

/* JADX INFO: loaded from: classes4.dex */
public abstract class AppIdRequest extends CosXmlRequest {
    public String appid;

    public AppIdRequest(String str) {
        this.appid = str;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getRequestHost(CosXmlServiceConfig cosXmlServiceConfig) {
        return cosXmlServiceConfig.getRequestHostByAppId(this.region, this.appid, CosXmlServiceConfig.CI_APPID_HOST_FORMAT);
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public void checkParameters() throws CosXmlClientException {
        if (this.requestURL == null && this.appid == null) {
            throw new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), "appid must not be null");
        }
    }
}
