package com.tencent.cos.xml.model.p033ci.asr;

import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.p033ci.BaseDescribeQueuesResponse;
import com.tencent.cos.xml.utils.QCloudXmlUtils;
import com.tencent.qcloud.core.http.HttpResponse;

/* JADX INFO: loaded from: classes4.dex */
public final class DescribeSpeechQueuesResult extends CosXmlResult {
    public BaseDescribeQueuesResponse describeSpeechQueuesResponse;

    @Override // com.tencent.cos.xml.model.CosXmlResult
    public void parseResponseBody(HttpResponse httpResponse) throws CosXmlServiceException, CosXmlClientException {
        super.parseResponseBody(httpResponse);
        this.describeSpeechQueuesResponse = (BaseDescribeQueuesResponse) QCloudXmlUtils.fromXml(httpResponse.byteStream(), BaseDescribeQueuesResponse.class);
    }
}
