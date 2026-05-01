package com.tencent.cos.xml.model.object;

import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.tag.ListParts;
import com.tencent.cos.xml.transfer.XmlSlimParser;
import com.tencent.qcloud.core.http.HttpResponse;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public final class ListPartsResult extends CosXmlResult {
    public ListParts listParts = new ListParts();

    @Override // com.tencent.cos.xml.model.CosXmlResult
    protected void xmlParser(HttpResponse httpResponse) throws XmlPullParserException, IOException {
        XmlSlimParser.parseListPartsResult(httpResponse.byteStream(), this.listParts);
    }

    @Override // com.tencent.cos.xml.model.CosXmlResult
    public String printResult() {
        ListParts listParts = this.listParts;
        return listParts != null ? listParts.toString() : super.printResult();
    }
}
