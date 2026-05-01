package com.tencent.cos.xml.model.object;

import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.tag.Tagging;
import com.tencent.cos.xml.transfer.XmlParser;
import com.tencent.qcloud.core.http.HttpResponse;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public final class GetObjectTaggingResult extends CosXmlResult {
    public Tagging tagging = new Tagging();

    @Override // com.tencent.cos.xml.model.CosXmlResult
    protected void xmlParser(HttpResponse httpResponse) throws XmlPullParserException, IOException {
        XmlParser.parseTagging(httpResponse.byteStream(), this.tagging);
    }
}
