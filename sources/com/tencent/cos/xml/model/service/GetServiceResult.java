package com.tencent.cos.xml.model.service;

import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.tag.ListAllMyBuckets;
import com.tencent.cos.xml.utils.QCloudXmlUtils;
import com.tencent.qcloud.core.http.HttpResponse;

/* JADX INFO: loaded from: classes4.dex */
public final class GetServiceResult extends CosXmlResult {
    public ListAllMyBuckets listAllMyBuckets;

    @Override // com.tencent.cos.xml.model.CosXmlResult
    public void parseResponseBody(HttpResponse httpResponse) throws CosXmlServiceException, CosXmlClientException {
        super.parseResponseBody(httpResponse);
        this.listAllMyBuckets = (ListAllMyBuckets) QCloudXmlUtils.fromXml(httpResponse.byteStream(), ListAllMyBuckets.class);
    }

    @Override // com.tencent.cos.xml.model.CosXmlResult
    public String printResult() {
        ListAllMyBuckets listAllMyBuckets = this.listAllMyBuckets;
        return listAllMyBuckets != null ? listAllMyBuckets.toString() : super.printResult();
    }
}
