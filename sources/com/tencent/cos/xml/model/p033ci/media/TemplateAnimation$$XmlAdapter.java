package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.microsoft.azure.storage.blob.BlobConstants;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateAnimation$$XmlAdapter extends IXmlAdapter<TemplateAnimation> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, TemplateAnimation templateAnimation, String str) throws XmlPullParserException, IOException {
        if (templateAnimation == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (templateAnimation.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(templateAnimation.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (templateAnimation.name != null) {
            xmlSerializer.startTag("", Constants.NAME_ELEMENT);
            xmlSerializer.text(String.valueOf(templateAnimation.name));
            xmlSerializer.endTag("", Constants.NAME_ELEMENT);
        }
        if (templateAnimation.container != null) {
            QCloudXml.toXml(xmlSerializer, templateAnimation.container, BlobConstants.CONTAINER_ELEMENT);
        }
        if (templateAnimation.video != null) {
            QCloudXml.toXml(xmlSerializer, templateAnimation.video, "Video");
        }
        if (templateAnimation.timeInterval != null) {
            QCloudXml.toXml(xmlSerializer, templateAnimation.timeInterval, "TimeInterval");
        }
        xmlSerializer.endTag("", str);
    }
}
