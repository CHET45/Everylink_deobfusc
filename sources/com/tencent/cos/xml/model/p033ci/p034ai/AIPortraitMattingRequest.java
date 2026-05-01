package com.tencent.cos.xml.model.p033ci.p034ai;

import android.text.TextUtils;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.p033ci.CiSaveLocalRequest;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class AIPortraitMattingRequest extends CiSaveLocalRequest {
    public boolean centerLayout;
    public String ciProcess = "AIPortraitMatting";
    public String detectUrl;
    protected final String objectKey;
    public String paddingLayout;

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public RequestBodySerializer getRequestBody() throws CosXmlClientException {
        return null;
    }

    public AIPortraitMattingRequest(String str, String str2) {
        this.bucket = str;
        this.objectKey = str2;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public Map<String, String> getQueryString() {
        this.queryParameters.put("ci-process", this.ciProcess);
        if (!TextUtils.isEmpty(this.detectUrl)) {
            this.queryParameters.put("detect-url", this.detectUrl);
        }
        this.queryParameters.put("center-layout", this.centerLayout ? "1" : "0");
        if (!TextUtils.isEmpty(this.paddingLayout)) {
            this.queryParameters.put("padding-layout", this.paddingLayout);
        }
        return super.getQueryString();
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getPath(CosXmlServiceConfig cosXmlServiceConfig) {
        return (!TextUtils.isEmpty(this.detectUrl) || TextUtils.isEmpty(this.objectKey)) ? "/" : "/" + this.objectKey;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "GET";
    }
}
