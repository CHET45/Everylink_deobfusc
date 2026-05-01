package com.tencent.cos.xml.model.object;

import android.text.TextUtils;
import com.tencent.cos.xml.common.ClientErrorCode;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.tag.CallbackResult;
import com.tencent.cos.xml.model.tag.pic.PicUploadResult;
import com.tencent.cos.xml.utils.QCloudXmlUtils;
import com.tencent.qcloud.core.http.HttpResponse;
import java.io.IOException;

/* JADX INFO: loaded from: classes4.dex */
public final class PutObjectResult extends BasePutObjectResult {
    public CallbackResult callbackResult;
    public PicUploadResult picUploadResult;

    @Override // com.tencent.cos.xml.model.object.BasePutObjectResult, com.tencent.cos.xml.model.CosXmlResult
    public void parseResponseBody(HttpResponse httpResponse) throws Exception {
        super.parseResponseBody(httpResponse);
        try {
            if ("application/xml".equalsIgnoreCase(httpResponse.header("Content-Type"))) {
                if (this.httpCode == 203) {
                    CallbackResult.Error error = (CallbackResult.Error) QCloudXmlUtils.fromXml(httpResponse.byteStream(), CallbackResult.Error.class);
                    CallbackResult callbackResult = new CallbackResult();
                    this.callbackResult = callbackResult;
                    callbackResult.status = "203";
                    this.callbackResult.error = error;
                    return;
                }
                this.picUploadResult = (PicUploadResult) QCloudXmlUtils.fromXml(httpResponse.byteStream(), PicUploadResult.class);
                return;
            }
            try {
                String strString = httpResponse.string();
                if (TextUtils.isEmpty(strString)) {
                    return;
                }
                CallbackResult callbackResult2 = new CallbackResult();
                this.callbackResult = callbackResult2;
                callbackResult2.status = "200";
                this.callbackResult.callbackBody = strString;
                this.callbackResult.callbackBodyNotBase64 = true;
            } catch (IOException e) {
                throw new CosXmlClientException(ClientErrorCode.POOR_NETWORK.getCode(), e);
            }
        } catch (Exception e2) {
            if (e2 instanceof CosXmlClientException) {
                throw e2;
            }
            throw new CosXmlClientException(ClientErrorCode.SERVERERROR.getCode(), e2);
        }
    }

    public PicUploadResult picUploadResult() {
        return this.picUploadResult;
    }
}
