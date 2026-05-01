package com.tencent.cos.xml.model.bucket;

import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.tag.LifecycleConfiguration;
import com.tencent.cos.xml.transfer.XmlParser;
import com.tencent.qcloud.core.http.HttpResponse;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public final class GetBucketLifecycleResult extends CosXmlResult {
    public LifecycleConfiguration lifecycleConfiguration = new LifecycleConfiguration();

    @Override // com.tencent.cos.xml.model.CosXmlResult
    protected void xmlParser(HttpResponse httpResponse) throws XmlPullParserException, IOException {
        XmlParser.parseLifecycleConfiguration(httpResponse.byteStream(), this.lifecycleConfiguration);
    }

    @Override // com.tencent.cos.xml.model.CosXmlResult
    public String printResult() {
        LifecycleConfiguration lifecycleConfiguration = this.lifecycleConfiguration;
        return lifecycleConfiguration != null ? lifecycleConfiguration.toString() : super.printResult();
    }
}
