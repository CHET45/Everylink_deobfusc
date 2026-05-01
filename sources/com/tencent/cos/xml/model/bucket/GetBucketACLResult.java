package com.tencent.cos.xml.model.bucket;

import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.tag.AccessControlPolicy;
import com.tencent.cos.xml.transfer.XmlParser;
import com.tencent.qcloud.core.http.HttpResponse;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public final class GetBucketACLResult extends CosXmlResult {
    public AccessControlPolicy accessControlPolicy = new AccessControlPolicy();

    @Override // com.tencent.cos.xml.model.CosXmlResult
    protected void xmlParser(HttpResponse httpResponse) throws XmlPullParserException, IOException {
        XmlParser.parseAccessControlPolicy(httpResponse.byteStream(), this.accessControlPolicy);
    }

    @Override // com.tencent.cos.xml.model.CosXmlResult
    public String printResult() {
        AccessControlPolicy accessControlPolicy = this.accessControlPolicy;
        return accessControlPolicy != null ? accessControlPolicy.toString() : super.printResult();
    }
}
