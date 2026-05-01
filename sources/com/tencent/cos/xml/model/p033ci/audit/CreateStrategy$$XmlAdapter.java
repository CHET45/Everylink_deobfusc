package com.tencent.cos.xml.model.p033ci.audit;

import com.microsoft.azure.storage.Constants;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class CreateStrategy$$XmlAdapter extends IXmlAdapter<CreateStrategy> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, CreateStrategy createStrategy, String str) throws XmlPullParserException, IOException {
        if (createStrategy == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (createStrategy.name != null) {
            xmlSerializer.startTag("", Constants.NAME_ELEMENT);
            xmlSerializer.text(String.valueOf(createStrategy.name));
            xmlSerializer.endTag("", Constants.NAME_ELEMENT);
        }
        if (createStrategy.labels != null) {
            QCloudXml.toXml(xmlSerializer, createStrategy.labels, "Labels");
        }
        if (createStrategy.textLibs != null) {
            for (int i = 0; i < createStrategy.textLibs.size(); i++) {
                xmlSerializer.startTag("", "TextLibs");
                xmlSerializer.text(String.valueOf(createStrategy.textLibs.get(i)));
                xmlSerializer.endTag("", "TextLibs");
            }
        }
        xmlSerializer.endTag("", str);
    }
}
