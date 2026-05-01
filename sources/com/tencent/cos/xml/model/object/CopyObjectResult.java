package com.tencent.cos.xml.model.object;

import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.tag.CopyObject;
import com.tencent.cos.xml.transfer.XmlSlimParser;
import com.tencent.qcloud.core.http.HttpResponse;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class CopyObjectResult extends CosXmlResult {
    public CopyObject copyObject;

    @Override // com.tencent.cos.xml.model.CosXmlResult
    protected void xmlParser(HttpResponse httpResponse) throws XmlPullParserException, IOException {
        this.copyObject = new CopyObject();
        XmlSlimParser.parseCopyObjectResult(httpResponse.byteStream(), this.copyObject);
    }

    @Override // com.tencent.cos.xml.model.CosXmlResult
    public String printResult() {
        CopyObject copyObject = this.copyObject;
        return copyObject != null ? copyObject.toString() : super.printResult();
    }
}
