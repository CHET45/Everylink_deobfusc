package com.tencent.cos.xml.model.object;

import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.tag.InitiateMultipartUpload;
import com.tencent.cos.xml.transfer.XmlSlimParser;
import com.tencent.qcloud.core.http.HttpResponse;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public final class InitMultipartUploadResult extends CosXmlResult {
    public InitiateMultipartUpload initMultipartUpload = new InitiateMultipartUpload();

    @Override // com.tencent.cos.xml.model.CosXmlResult
    protected void xmlParser(HttpResponse httpResponse) throws XmlPullParserException, IOException {
        XmlSlimParser.parseInitiateMultipartUploadResult(httpResponse.byteStream(), this.initMultipartUpload);
    }

    @Override // com.tencent.cos.xml.model.CosXmlResult
    public String printResult() {
        InitiateMultipartUpload initiateMultipartUpload = this.initMultipartUpload;
        return initiateMultipartUpload != null ? initiateMultipartUpload.toString() : super.printResult();
    }
}
