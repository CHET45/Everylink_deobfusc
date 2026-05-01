package com.tencent.cos.xml.model.bucket;

import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.tag.ListInventoryConfiguration;
import com.tencent.cos.xml.transfer.XmlParser;
import com.tencent.qcloud.core.http.HttpResponse;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class ListBucketInventoryResult extends CosXmlResult {
    public ListInventoryConfiguration listInventoryConfiguration = new ListInventoryConfiguration();

    @Override // com.tencent.cos.xml.model.CosXmlResult
    protected void xmlParser(HttpResponse httpResponse) throws XmlPullParserException, IOException {
        XmlParser.parseListInventoryConfiguration(httpResponse.byteStream(), this.listInventoryConfiguration);
    }

    @Override // com.tencent.cos.xml.model.CosXmlResult
    public String printResult() {
        ListInventoryConfiguration listInventoryConfiguration = this.listInventoryConfiguration;
        return listInventoryConfiguration != null ? listInventoryConfiguration.toString() : super.printResult();
    }
}
