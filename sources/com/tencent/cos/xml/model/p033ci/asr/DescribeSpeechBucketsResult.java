package com.tencent.cos.xml.model.p033ci.asr;

import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.p033ci.asr.bean.DescribeSpeechBucketsResponse;
import com.tencent.cos.xml.utils.QCloudXmlUtils;
import com.tencent.qcloud.core.http.HttpResponse;

/* JADX INFO: loaded from: classes4.dex */
public final class DescribeSpeechBucketsResult extends CosXmlResult {
    public DescribeSpeechBucketsResponse describeSpeechBucketsResponse;

    @Override // com.tencent.cos.xml.model.CosXmlResult
    public void parseResponseBody(HttpResponse httpResponse) throws CosXmlServiceException, CosXmlClientException {
        super.parseResponseBody(httpResponse);
        this.describeSpeechBucketsResponse = (DescribeSpeechBucketsResponse) QCloudXmlUtils.fromXml(httpResponse.byteStream(), DescribeSpeechBucketsResponse.class);
    }
}
