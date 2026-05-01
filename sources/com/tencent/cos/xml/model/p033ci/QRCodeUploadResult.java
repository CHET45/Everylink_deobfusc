package com.tencent.cos.xml.model.p033ci;

import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.model.tag.pic.PicObject;
import com.tencent.cos.xml.model.tag.pic.PicOriginalInfo;
import com.tencent.cos.xml.model.tag.pic.QRCodeInfo;
import com.tencent.cos.xml.utils.QCloudXmlUtils;
import com.tencent.qcloud.core.http.HttpResponse;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class QRCodeUploadResult extends ImageUploadResult {
    public PicUploadResult picUploadResult;

    public static class PicUploadResult {
        public PicOriginalInfo originalInfo;
        public List<QrPicObject> processResults;
    }

    public static class QrPicObject extends PicObject {
        public int codeStatus;
        public List<QRCodeInfo> qrCodeInfo;
    }

    @Override // com.tencent.cos.xml.model.p033ci.ImageUploadResult, com.tencent.cos.xml.model.CosXmlResult
    public void parseResponseBody(HttpResponse httpResponse) throws CosXmlServiceException, CosXmlClientException {
        super.parseResponseBody(httpResponse);
        this.picUploadResult = (PicUploadResult) QCloudXmlUtils.fromXml(httpResponse.byteStream(), PicUploadResult.class);
    }

    public PicUploadResult getPicUploadResult() {
        return this.picUploadResult;
    }
}
