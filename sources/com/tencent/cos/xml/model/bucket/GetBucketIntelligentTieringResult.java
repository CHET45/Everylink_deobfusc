package com.tencent.cos.xml.model.bucket;

import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.tag.IntelligentTieringConfiguration;
import com.tencent.cos.xml.utils.QCloudXmlUtils;
import com.tencent.qcloud.core.http.HttpResponse;

/* JADX INFO: loaded from: classes4.dex */
public class GetBucketIntelligentTieringResult extends CosXmlResult {
    private IntelligentTieringConfiguration configuration;

    public IntelligentTieringConfiguration getConfiguration() {
        return this.configuration;
    }

    @Override // com.tencent.cos.xml.model.CosXmlResult
    public void parseResponseBody(HttpResponse httpResponse) throws CosXmlServiceException, CosXmlClientException {
        super.parseResponseBody(httpResponse);
        this.configuration = (IntelligentTieringConfiguration) QCloudXmlUtils.fromXml(httpResponse.byteStream(), IntelligentTieringConfiguration.class);
    }
}
