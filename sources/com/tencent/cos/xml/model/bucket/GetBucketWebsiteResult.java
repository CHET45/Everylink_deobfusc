package com.tencent.cos.xml.model.bucket;

import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.tag.WebsiteConfiguration;
import com.tencent.cos.xml.transfer.XmlParser;
import com.tencent.qcloud.core.http.HttpResponse;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetBucketWebsiteResult extends CosXmlResult {
    public WebsiteConfiguration websiteConfiguration = new WebsiteConfiguration();

    @Override // com.tencent.cos.xml.model.CosXmlResult
    protected void xmlParser(HttpResponse httpResponse) throws XmlPullParserException, IOException {
        XmlParser.parseWebsiteConfig(httpResponse.byteStream(), this.websiteConfiguration);
    }

    @Override // com.tencent.cos.xml.model.CosXmlResult
    public String printResult() {
        WebsiteConfiguration websiteConfiguration = this.websiteConfiguration;
        return websiteConfiguration != null ? websiteConfiguration.toString() : super.printResult();
    }
}
