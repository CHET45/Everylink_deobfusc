package com.tencent.cos.xml.model.p033ci.audit;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class UpdateStrategy$$XmlAdapter extends IXmlAdapter<UpdateStrategy> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, UpdateStrategy updateStrategy, String str) throws XmlPullParserException, IOException {
        if (updateStrategy == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (updateStrategy.labels != null) {
            QCloudXml.toXml(xmlSerializer, updateStrategy.labels, "Labels");
        }
        if (updateStrategy.textLibs != null) {
            for (int i = 0; i < updateStrategy.textLibs.size(); i++) {
                xmlSerializer.startTag("", "TextLibs");
                xmlSerializer.text(String.valueOf(updateStrategy.textLibs.get(i)));
                xmlSerializer.endTag("", "TextLibs");
            }
        }
        xmlSerializer.endTag("", str);
    }
}
