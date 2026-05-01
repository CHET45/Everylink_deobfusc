package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.microsoft.azure.storage.blob.BlobConstants;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateTranscode$$XmlAdapter extends IXmlAdapter<TemplateTranscode> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, TemplateTranscode templateTranscode, String str) throws XmlPullParserException, IOException {
        if (templateTranscode == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (templateTranscode.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(templateTranscode.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (templateTranscode.name != null) {
            xmlSerializer.startTag("", Constants.NAME_ELEMENT);
            xmlSerializer.text(String.valueOf(templateTranscode.name));
            xmlSerializer.endTag("", Constants.NAME_ELEMENT);
        }
        if (templateTranscode.container != null) {
            QCloudXml.toXml(xmlSerializer, templateTranscode.container, BlobConstants.CONTAINER_ELEMENT);
        }
        if (templateTranscode.video != null) {
            QCloudXml.toXml(xmlSerializer, templateTranscode.video, "Video");
        }
        if (templateTranscode.timeInterval != null) {
            QCloudXml.toXml(xmlSerializer, templateTranscode.timeInterval, "TimeInterval");
        }
        if (templateTranscode.audio != null) {
            QCloudXml.toXml(xmlSerializer, templateTranscode.audio, "Audio");
        }
        if (templateTranscode.transConfig != null) {
            QCloudXml.toXml(xmlSerializer, templateTranscode.transConfig, "TransConfig");
        }
        if (templateTranscode.audioMix != null) {
            QCloudXml.toXml(xmlSerializer, templateTranscode.audioMix, "AudioMix");
        }
        if (templateTranscode.audioMixArray != null) {
            for (int i = 0; i < templateTranscode.audioMixArray.size(); i++) {
                QCloudXml.toXml(xmlSerializer, templateTranscode.audioMixArray.get(i), "AudioMix");
            }
        }
        xmlSerializer.endTag("", str);
    }
}
