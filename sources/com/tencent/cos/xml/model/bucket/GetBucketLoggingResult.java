package com.tencent.cos.xml.model.bucket;

import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.tag.BucketLoggingStatus;
import com.tencent.cos.xml.transfer.XmlParser;
import com.tencent.qcloud.core.http.HttpResponse;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetBucketLoggingResult extends CosXmlResult {
    public BucketLoggingStatus bucketLoggingStatus = new BucketLoggingStatus();

    @Override // com.tencent.cos.xml.model.CosXmlResult
    protected void xmlParser(HttpResponse httpResponse) throws XmlPullParserException, IOException {
        XmlParser.parseBucketLoggingStatus(httpResponse.byteStream(), this.bucketLoggingStatus);
    }

    @Override // com.tencent.cos.xml.model.CosXmlResult
    public String printResult() {
        BucketLoggingStatus bucketLoggingStatus = this.bucketLoggingStatus;
        return bucketLoggingStatus != null ? bucketLoggingStatus.toString() : super.printResult();
    }
}
