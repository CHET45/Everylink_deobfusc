package com.tencent.cos.xml.model.p033ci.audit;

import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.app.NotificationCompat;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.bucket.BucketRequest;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class GetStrategyListRequest extends BucketRequest {
    public int limit;
    public int offset;
    public String service;

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public RequestBodySerializer getRequestBody() throws CosXmlClientException {
        return null;
    }

    public GetStrategyListRequest(String str) {
        super(str);
        this.offset = -1;
        this.limit = -1;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public Map<String, String> getQueryString() {
        if (!TextUtils.isEmpty(this.service)) {
            this.queryParameters.put(NotificationCompat.CATEGORY_SERVICE, this.service);
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
        return "/audit/strategy";
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
