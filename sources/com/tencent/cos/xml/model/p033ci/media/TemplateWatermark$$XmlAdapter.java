package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateWatermark$$XmlAdapter extends IXmlAdapter<TemplateWatermark> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, TemplateWatermark templateWatermark, String str) throws XmlPullParserException, IOException {
        if (templateWatermark == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (templateWatermark.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(templateWatermark.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (templateWatermark.name != null) {
            xmlSerializer.startTag("", Constants.NAME_ELEMENT);
            xmlSerializer.text(String.valueOf(templateWatermark.name));
            xmlSerializer.endTag("", Constants.NAME_ELEMENT);
        }
        if (templateWatermark.watermark != null) {
            QCloudXml.toXml(xmlSerializer, templateWatermark.watermark, "Watermark");
        }
        xmlSerializer.endTag("", str);
    }
}
