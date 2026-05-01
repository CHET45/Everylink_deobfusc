package com.tencent.cos.xml.model.bucket;

import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.tag.VersioningConfiguration;
import com.tencent.cos.xml.transfer.XmlParser;
import com.tencent.qcloud.core.http.HttpResponse;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetBucketVersioningResult extends CosXmlResult {
    public VersioningConfiguration versioningConfiguration = new VersioningConfiguration();

    @Override // com.tencent.cos.xml.model.CosXmlResult
    protected void xmlParser(HttpResponse httpResponse) throws XmlPullParserException, IOException {
        XmlParser.parseVersioningConfiguration(httpResponse.byteStream(), this.versioningConfiguration);
    }

    @Override // com.tencent.cos.xml.model.CosXmlResult
    public String printResult() {
        VersioningConfiguration versioningConfiguration = this.versioningConfiguration;
        return versioningConfiguration != null ? versioningConfiguration.toString() : super.printResult();
    }
}
