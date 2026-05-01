package com.tencent.cos.xml.model.p033ci;

import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.tag.DescribeDocProcessBuckets;
import com.tencent.cos.xml.utils.QCloudXmlUtils;
import com.tencent.qcloud.core.http.HttpResponse;

/* JADX INFO: loaded from: classes4.dex */
public class DescribeDocProcessBucketsResult extends CosXmlResult {
    public DescribeDocProcessBuckets describeDocProcessBuckets;

    @Override // com.tencent.cos.xml.model.CosXmlResult
    public void parseResponseBody(HttpResponse httpResponse) throws CosXmlServiceException, CosXmlClientException {
        super.parseResponseBody(httpResponse);
        this.describeDocProcessBuckets = (DescribeDocProcessBuckets) QCloudXmlUtils.fromXml(httpResponse.byteStream(), DescribeDocProcessBuckets.class);
    }
}
