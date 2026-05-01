package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateSmartCover$$XmlAdapter extends IXmlAdapter<TemplateSmartCover> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, TemplateSmartCover templateSmartCover, String str) throws XmlPullParserException, IOException {
        if (templateSmartCover == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (templateSmartCover.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(templateSmartCover.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (templateSmartCover.name != null) {
            xmlSerializer.startTag("", Constants.NAME_ELEMENT);
            xmlSerializer.text(String.valueOf(templateSmartCover.name));
            xmlSerializer.endTag("", Constants.NAME_ELEMENT);
        }
        if (templateSmartCover.smartCover != null) {
            QCloudXml.toXml(xmlSerializer, templateSmartCover.smartCover, "SmartCover");
        }
        xmlSerializer.endTag("", str);
    }
}
