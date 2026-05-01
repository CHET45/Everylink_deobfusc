package com.tencent.cos.xml.model.p033ci.p034ai;

import android.text.TextUtils;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.p033ci.CiSaveLocalRequest;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class AISuperResolutionRequest extends CiSaveLocalRequest {
    public String detectUrl;
    public String objectKey;
    public String ciProcess = "AISuperResolution";
    public int magnify = -1;

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public RequestBodySerializer getRequestBody() throws CosXmlClientException {
        return null;
    }

    public AISuperResolutionRequest(String str) {
        this.bucket = str;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public Map<String, String> getQueryString() {
        this.queryParameters.put("ci-process", this.ciProcess);
        if (!TextUtils.isEmpty(this.detectUrl)) {
            this.queryParameters.put("detect-url", this.detectUrl);
        }
        if (this.magnify != -1) {
            this.queryParameters.put("magnify", String.valueOf(this.magnify));
        }
        return super.getQueryString();
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getPath(CosXmlServiceConfig cosXmlServiceConfig) {
        return TextUtils.isEmpty(this.objectKey) ? "/" : "/" + this.objectKey;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "GET";
    }
}
