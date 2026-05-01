package com.tencent.cos.xml.model.tag.audit.post;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.tag.audit.post.PostImagesAudit;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostImagesAudit$ImagesAuditInput$$XmlAdapter extends IXmlAdapter<PostImagesAudit.ImagesAuditInput> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostImagesAudit.ImagesAuditInput imagesAuditInput, String str) throws XmlPullParserException, IOException {
        if (imagesAuditInput == null) {
            return;
        }
        if (str == null) {
            str = "Input";
        }
        xmlSerializer.startTag("", str);
        if (imagesAuditInput.content != null) {
            xmlSerializer.startTag("", "Content");
            xmlSerializer.text(String.valueOf(imagesAuditInput.content));
            xmlSerializer.endTag("", "Content");
        }
        if (imagesAuditInput.interval != 0) {
            xmlSerializer.startTag("", "Interval");
            xmlSerializer.text(String.valueOf(imagesAuditInput.interval));
            xmlSerializer.endTag("", "Interval");
        }
        if (imagesAuditInput.maxFrames != 0) {
            xmlSerializer.startTag("", "MaxFrames");
            xmlSerializer.text(String.valueOf(imagesAuditInput.maxFrames));
            xmlSerializer.endTag("", "MaxFrames");
        }
        xmlSerializer.startTag("", "LargeImageDetect");
        xmlSerializer.text(String.valueOf(imagesAuditInput.largeImageDetect));
        xmlSerializer.endTag("", "LargeImageDetect");
        if (imagesAuditInput.encryption != null) {
            QCloudXml.toXml(xmlSerializer, imagesAuditInput.encryption, "Encryption");
        }
        if (imagesAuditInput.object != null) {
            xmlSerializer.startTag("", "Object");
            xmlSerializer.text(String.valueOf(imagesAuditInput.object));
            xmlSerializer.endTag("", "Object");
        }
        if (imagesAuditInput.url != null) {
            xmlSerializer.startTag("", Constants.URL_ELEMENT);
            xmlSerializer.text(String.valueOf(imagesAuditInput.url));
            xmlSerializer.endTag("", Constants.URL_ELEMENT);
        }
        if (imagesAuditInput.dataId != null) {
            xmlSerializer.startTag("", "DataId");
            xmlSerializer.text(String.valueOf(imagesAuditInput.dataId));
            xmlSerializer.endTag("", "DataId");
        }
        if (imagesAuditInput.userInfo != null) {
            QCloudXml.toXml(xmlSerializer, imagesAuditInput.userInfo, "UserInfo");
        }
        xmlSerializer.endTag("", str);
    }
}
