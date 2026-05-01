package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.microsoft.azure.storage.blob.BlobConstants;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateVideoMontage$$XmlAdapter extends IXmlAdapter<TemplateVideoMontage> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, TemplateVideoMontage templateVideoMontage, String str) throws XmlPullParserException, IOException {
        if (templateVideoMontage == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (templateVideoMontage.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(templateVideoMontage.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (templateVideoMontage.name != null) {
            xmlSerializer.startTag("", Constants.NAME_ELEMENT);
            xmlSerializer.text(String.valueOf(templateVideoMontage.name));
            xmlSerializer.endTag("", Constants.NAME_ELEMENT);
        }
        if (templateVideoMontage.duration != null) {
            xmlSerializer.startTag("", "Duration");
            xmlSerializer.text(String.valueOf(templateVideoMontage.duration));
            xmlSerializer.endTag("", "Duration");
        }
        if (templateVideoMontage.container != null) {
            QCloudXml.toXml(xmlSerializer, templateVideoMontage.container, BlobConstants.CONTAINER_ELEMENT);
        }
        if (templateVideoMontage.video != null) {
            QCloudXml.toXml(xmlSerializer, templateVideoMontage.video, "Video");
        }
        if (templateVideoMontage.audio != null) {
            QCloudXml.toXml(xmlSerializer, templateVideoMontage.audio, "Audio");
        }
        if (templateVideoMontage.scene != null) {
            xmlSerializer.startTag("", "Scene");
            xmlSerializer.text(String.valueOf(templateVideoMontage.scene));
            xmlSerializer.endTag("", "Scene");
        }
        if (templateVideoMontage.audioMix != null) {
            QCloudXml.toXml(xmlSerializer, templateVideoMontage.audioMix, "AudioMix");
        }
        if (templateVideoMontage.audioMixArray != null) {
            for (int i = 0; i < templateVideoMontage.audioMixArray.size(); i++) {
                QCloudXml.toXml(xmlSerializer, templateVideoMontage.audioMixArray.get(i), "AudioMix");
            }
        }
        xmlSerializer.endTag("", str);
    }
}
