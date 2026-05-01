package com.tencent.cos.xml.model.object;

import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.tag.DeleteResult;
import com.tencent.cos.xml.transfer.XmlParser;
import com.tencent.qcloud.core.http.HttpResponse;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public final class DeleteMultiObjectResult extends CosXmlResult {
    public DeleteResult deleteResult = new DeleteResult();

    @Override // com.tencent.cos.xml.model.CosXmlResult
    protected void xmlParser(HttpResponse httpResponse) throws XmlPullParserException, IOException {
        XmlParser.parseDeleteResult(httpResponse.byteStream(), this.deleteResult);
    }

    @Override // com.tencent.cos.xml.model.CosXmlResult
    public String printResult() {
        DeleteResult deleteResult = this.deleteResult;
        return deleteResult != null ? deleteResult.toString() : super.printResult();
    }
}
