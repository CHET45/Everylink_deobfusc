package com.tencent.cos.xml.model.object;

import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.tag.pic.PicUploadResult;
import com.tencent.cos.xml.utils.QCloudXmlUtils;
import com.tencent.qcloud.core.http.HttpResponse;

/* JADX INFO: loaded from: classes4.dex */
public class ImageProcessResult extends CosXmlResult {
    public String eTag;
    public PicUploadResult picUploadResult;

    @Override // com.tencent.cos.xml.model.CosXmlResult
    public void parseResponseBody(HttpResponse httpResponse) throws CosXmlServiceException, CosXmlClientException {
        super.parseResponseBody(httpResponse);
        this.eTag = httpResponse.header("ETag");
        this.picUploadResult = (PicUploadResult) QCloudXmlUtils.fromXml(httpResponse.byteStream(), PicUploadResult.class);
    }

    public PicUploadResult getPicUploadResult() {
        return this.picUploadResult;
    }
}
