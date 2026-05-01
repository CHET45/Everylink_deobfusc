package com.tencent.cos.xml.model.bucket;

import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.tag.ReplicationConfiguration;
import com.tencent.cos.xml.transfer.XmlParser;
import com.tencent.qcloud.core.http.HttpResponse;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetBucketReplicationResult extends CosXmlResult {
    public ReplicationConfiguration replicationConfiguration = new ReplicationConfiguration();

    @Override // com.tencent.cos.xml.model.CosXmlResult
    protected void xmlParser(HttpResponse httpResponse) throws XmlPullParserException, IOException {
        XmlParser.parseReplicationConfiguration(httpResponse.byteStream(), this.replicationConfiguration);
    }

    @Override // com.tencent.cos.xml.model.CosXmlResult
    public String printResult() {
        ReplicationConfiguration replicationConfiguration = this.replicationConfiguration;
        return replicationConfiguration != null ? replicationConfiguration.toString() : super.printResult();
    }
}
