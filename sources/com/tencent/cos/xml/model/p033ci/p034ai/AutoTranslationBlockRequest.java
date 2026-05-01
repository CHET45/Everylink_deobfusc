package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.bucket.BucketRequest;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class AutoTranslationBlockRequest extends BucketRequest {
    public String ciProcess;
    public String inputText;
    public String sourceLang;
    public String targetLang;
    public String textDomain;
    public String textStyle;

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public RequestBodySerializer getRequestBody() throws CosXmlClientException {
        return null;
    }

    public AutoTranslationBlockRequest(String str) {
        super(str);
        this.ciProcess = "AutoTranslationBlock";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public Map<String, String> getQueryString() {
        this.queryParameters.put("ci-process", this.ciProcess);
        this.queryParameters.put("InputText", this.inputText);
        this.queryParameters.put("SourceLang", this.sourceLang);
        this.queryParameters.put("TargetLang", this.targetLang);
        this.queryParameters.put("TextDomain", this.textDomain);
        this.queryParameters.put("TextStyle", this.textStyle);
        return super.getQueryString();
    }

    @Override // com.tencent.cos.xml.model.bucket.BucketRequest, com.tencent.cos.xml.model.CosXmlRequest
    public String getPath(CosXmlServiceConfig cosXmlServiceConfig) {
        return "/";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "GET";
    }
}
