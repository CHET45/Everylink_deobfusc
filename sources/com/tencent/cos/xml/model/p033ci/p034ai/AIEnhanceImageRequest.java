package com.tencent.cos.xml.model.p033ci.p034ai;

import android.text.TextUtils;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.p033ci.CiSaveLocalRequest;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class AIEnhanceImageRequest extends CiSaveLocalRequest {
    public String ciProcess = "AIEnhanceImage";
    public int denoise;
    public String detectUrl;
    public int ignoreError;
    public String objectKey;
    public int sharpen;

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public RequestBodySerializer getRequestBody() throws CosXmlClientException {
        return null;
    }

    public AIEnhanceImageRequest(String str) {
        this.bucket = str;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public Map<String, String> getQueryString() {
        this.queryParameters.put("ci-process", this.ciProcess);
        this.queryParameters.put("denoise", String.valueOf(this.denoise));
        this.queryParameters.put("sharpen", String.valueOf(this.sharpen));
        if (!TextUtils.isEmpty(this.detectUrl)) {
            this.queryParameters.put("detect-url", this.detectUrl);
        }
        this.queryParameters.put("ignore-error", String.valueOf(this.ignoreError));
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
