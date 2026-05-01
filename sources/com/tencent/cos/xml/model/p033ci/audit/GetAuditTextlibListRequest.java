package com.tencent.cos.xml.model.p033ci.audit;

import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.bucket.BucketRequest;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class GetAuditTextlibListRequest extends BucketRequest {
    public String libid;
    public int limit;
    public int offset;

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public RequestBodySerializer getRequestBody() throws CosXmlClientException {
        return null;
    }

    public GetAuditTextlibListRequest(String str) {
        super(str);
        this.offset = -1;
        this.limit = -1;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public Map<String, String> getQueryString() {
        if (!TextUtils.isEmpty(this.libid)) {
            this.queryParameters.put("libid", this.libid);
        }
        if (this.offset != -1) {
            this.queryParameters.put(TypedValues.CycleType.S_WAVE_OFFSET, String.valueOf(this.offset));
        }
        if (this.limit != -1) {
            this.queryParameters.put("limit", String.valueOf(this.limit));
        }
        return super.getQueryString();
    }

    @Override // com.tencent.cos.xml.model.bucket.BucketRequest, com.tencent.cos.xml.model.CosXmlRequest
    public String getPath(CosXmlServiceConfig cosXmlServiceConfig) {
        return "/audit/textlib";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "GET";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getRequestHost(CosXmlServiceConfig cosXmlServiceConfig) {
        return cosXmlServiceConfig.getRequestHost(this.region, this.bucket, CosXmlServiceConfig.CI_HOST_FORMAT);
    }
}
