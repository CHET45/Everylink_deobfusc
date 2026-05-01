package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class ImageSearchBucket$$XmlAdapter extends IXmlAdapter<ImageSearchBucket> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, ImageSearchBucket imageSearchBucket, String str) throws XmlPullParserException, IOException {
        if (imageSearchBucket == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        xmlSerializer.startTag("", "MaxCapacity");
        xmlSerializer.text(String.valueOf(imageSearchBucket.maxCapacity));
        xmlSerializer.endTag("", "MaxCapacity");
        xmlSerializer.startTag("", "MaxQps");
        xmlSerializer.text(String.valueOf(imageSearchBucket.maxQps));
        xmlSerializer.endTag("", "MaxQps");
        xmlSerializer.endTag("", str);
    }
}
