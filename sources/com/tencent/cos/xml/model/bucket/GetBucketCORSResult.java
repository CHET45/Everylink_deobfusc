package com.tencent.cos.xml.model.bucket;

import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.tag.CORSConfiguration;
import com.tencent.cos.xml.transfer.XmlParser;
import com.tencent.qcloud.core.http.HttpResponse;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public final class GetBucketCORSResult extends CosXmlResult {
    public CORSConfiguration corsConfiguration = new CORSConfiguration();

    @Override // com.tencent.cos.xml.model.CosXmlResult
    protected void xmlParser(HttpResponse httpResponse) throws XmlPullParserException, IOException {
        XmlParser.parseCORSConfiguration(httpResponse.byteStream(), this.corsConfiguration);
    }

    @Override // com.tencent.cos.xml.model.CosXmlResult
    public String printResult() {
        CORSConfiguration cORSConfiguration = this.corsConfiguration;
        return cORSConfiguration != null ? cORSConfiguration.toString() : super.printResult();
    }
}
