package com.tencent.cos.xml.model.p033ci;

import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.tag.BucketDocumentPreviewState;
import com.tencent.cos.xml.model.tag.PutBucketDPState;
import com.tencent.cos.xml.utils.QCloudXmlUtils;
import com.tencent.qcloud.core.http.HttpResponse;

/* JADX INFO: loaded from: classes4.dex */
public class PutBucketDPStateResult extends CosXmlResult {
    private BucketDocumentPreviewState bucketDocumentPreviewState;

    @Override // com.tencent.cos.xml.model.CosXmlResult
    public void parseResponseBody(HttpResponse httpResponse) throws CosXmlServiceException, CosXmlClientException {
        super.parseResponseBody(httpResponse);
        this.bucketDocumentPreviewState = ((PutBucketDPState) QCloudXmlUtils.fromXml(httpResponse.byteStream(), PutBucketDPState.class)).DocBucket;
    }

    public BucketDocumentPreviewState getDocumentPreviewState() {
        return this.bucketDocumentPreviewState;
    }
}
