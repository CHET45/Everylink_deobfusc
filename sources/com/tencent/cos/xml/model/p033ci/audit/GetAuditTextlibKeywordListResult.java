package com.tencent.cos.xml.model.p033ci.audit;

import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.utils.QCloudXmlUtils;
import com.tencent.qcloud.core.http.HttpResponse;

/* JADX INFO: loaded from: classes4.dex */
public final class GetAuditTextlibKeywordListResult extends CosXmlResult {
    public GetAuditTextlibKeywordListResponse response;

    @Override // com.tencent.cos.xml.model.CosXmlResult
    public void parseResponseBody(HttpResponse httpResponse) throws CosXmlServiceException, CosXmlClientException {
        super.parseResponseBody(httpResponse);
        this.response = (GetAuditTextlibKeywordListResponse) QCloudXmlUtils.fromXml(httpResponse.byteStream(), GetAuditTextlibKeywordListResponse.class);
    }
}
