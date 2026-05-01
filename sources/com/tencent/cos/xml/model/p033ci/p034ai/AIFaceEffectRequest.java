package com.tencent.cos.xml.model.p033ci.p034ai;

import android.text.TextUtils;
import com.microsoft.azure.storage.table.TableConstants;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.bucket.BucketRequest;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class AIFaceEffectRequest extends BucketRequest {
    public int age;
    public String ciProcess;
    public String detectUrl;
    public int eyeEnlarging;
    public int faceLifting;
    public int gender;
    public String objectKey;
    public int smoothing;
    public String type;
    public int whitening;

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public RequestBodySerializer getRequestBody() throws CosXmlClientException {
        return null;
    }

    public AIFaceEffectRequest(String str) {
        super(str);
        this.ciProcess = "face-effect";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public Map<String, String> getQueryString() {
        this.queryParameters.put("ci-process", this.ciProcess);
        if (!TextUtils.isEmpty(this.detectUrl)) {
            this.queryParameters.put("detect-url", this.detectUrl);
        }
        this.queryParameters.put(TableConstants.ErrorConstants.ERROR_EXCEPTION_TYPE, this.type);
        this.queryParameters.put("whitening", String.valueOf(this.whitening));
        this.queryParameters.put("smoothing", String.valueOf(this.smoothing));
        this.queryParameters.put("faceLifting", String.valueOf(this.faceLifting));
        this.queryParameters.put("eyeEnlarging", String.valueOf(this.eyeEnlarging));
        this.queryParameters.put("gender", String.valueOf(this.gender));
        this.queryParameters.put("age", String.valueOf(this.age));
        return super.getQueryString();
    }

    @Override // com.tencent.cos.xml.model.bucket.BucketRequest, com.tencent.cos.xml.model.CosXmlRequest
    public String getPath(CosXmlServiceConfig cosXmlServiceConfig) {
        return TextUtils.isEmpty(this.objectKey) ? "/" : "/" + this.objectKey;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "GET";
    }
}
