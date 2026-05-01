package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateVoiceSeparate$$XmlAdapter extends IXmlAdapter<TemplateVoiceSeparate> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, TemplateVoiceSeparate templateVoiceSeparate, String str) throws XmlPullParserException, IOException {
        if (templateVoiceSeparate == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (templateVoiceSeparate.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(templateVoiceSeparate.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (templateVoiceSeparate.name != null) {
            xmlSerializer.startTag("", Constants.NAME_ELEMENT);
            xmlSerializer.text(String.valueOf(templateVoiceSeparate.name));
            xmlSerializer.endTag("", Constants.NAME_ELEMENT);
        }
        if (templateVoiceSeparate.audioMode != null) {
            xmlSerializer.startTag("", "AudioMode");
            xmlSerializer.text(String.valueOf(templateVoiceSeparate.audioMode));
            xmlSerializer.endTag("", "AudioMode");
        }
        if (templateVoiceSeparate.audioConfig != null) {
            QCloudXml.toXml(xmlSerializer, templateVoiceSeparate.audioConfig, "AudioConfig");
        }
        xmlSerializer.endTag("", str);
    }
}
