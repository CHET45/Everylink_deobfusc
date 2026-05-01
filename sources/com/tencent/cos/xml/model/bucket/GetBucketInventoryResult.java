package com.tencent.cos.xml.model.bucket;

import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.tag.InventoryConfiguration;
import com.tencent.cos.xml.transfer.XmlParser;
import com.tencent.qcloud.core.http.HttpResponse;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetBucketInventoryResult extends CosXmlResult {
    public InventoryConfiguration inventoryConfiguration = new InventoryConfiguration();

    @Override // com.tencent.cos.xml.model.CosXmlResult
    protected void xmlParser(HttpResponse httpResponse) throws XmlPullParserException, IOException {
        XmlParser.parseInventoryConfiguration(httpResponse.byteStream(), this.inventoryConfiguration);
    }

    @Override // com.tencent.cos.xml.model.CosXmlResult
    public String printResult() {
        InventoryConfiguration inventoryConfiguration = this.inventoryConfiguration;
        return inventoryConfiguration != null ? inventoryConfiguration.toString() : super.printResult();
    }
}
