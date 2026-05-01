package com.tencent.cos.xml.model.bucket;

import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.tag.LocationConstraint;
import com.tencent.cos.xml.transfer.XmlParser;
import com.tencent.qcloud.core.http.HttpResponse;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public final class GetBucketLocationResult extends CosXmlResult {
    public LocationConstraint locationConstraint = new LocationConstraint();

    @Override // com.tencent.cos.xml.model.CosXmlResult
    protected void xmlParser(HttpResponse httpResponse) throws XmlPullParserException, IOException {
        XmlParser.parseLocationConstraint(httpResponse.byteStream(), this.locationConstraint);
    }

    @Override // com.tencent.cos.xml.model.CosXmlResult
    public String printResult() {
        LocationConstraint locationConstraint = this.locationConstraint;
        return locationConstraint != null ? locationConstraint.toString() : super.printResult();
    }
}
