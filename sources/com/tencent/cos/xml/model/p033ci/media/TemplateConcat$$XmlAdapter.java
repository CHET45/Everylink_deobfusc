package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateConcat$$XmlAdapter extends IXmlAdapter<TemplateConcat> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, TemplateConcat templateConcat, String str) throws XmlPullParserException, IOException {
        if (templateConcat == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (templateConcat.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(templateConcat.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (templateConcat.name != null) {
            xmlSerializer.startTag("", Constants.NAME_ELEMENT);
            xmlSerializer.text(String.valueOf(templateConcat.name));
            xmlSerializer.endTag("", Constants.NAME_ELEMENT);
        }
        if (templateConcat.concatTemplate != null) {
            QCloudXml.toXml(xmlSerializer, templateConcat.concatTemplate, "ConcatTemplate");
        }
        xmlSerializer.endTag("", str);
    }
}
