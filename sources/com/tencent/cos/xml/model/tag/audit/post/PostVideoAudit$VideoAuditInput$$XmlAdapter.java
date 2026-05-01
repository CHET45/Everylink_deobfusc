package com.tencent.cos.xml.model.tag.audit.post;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.tag.audit.post.PostVideoAudit;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostVideoAudit$VideoAuditInput$$XmlAdapter extends IXmlAdapter<PostVideoAudit.VideoAuditInput> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostVideoAudit.VideoAuditInput videoAuditInput, String str) throws XmlPullParserException, IOException {
        if (videoAuditInput == null) {
            return;
        }
        if (str == null) {
            str = "Input";
        }
        xmlSerializer.startTag("", str);
        if (videoAuditInput.encryption != null) {
            QCloudXml.toXml(xmlSerializer, videoAuditInput.encryption, "Encryption");
        }
        if (videoAuditInput.object != null) {
            xmlSerializer.startTag("", "Object");
            xmlSerializer.text(String.valueOf(videoAuditInput.object));
            xmlSerializer.endTag("", "Object");
        }
        if (videoAuditInput.url != null) {
            xmlSerializer.startTag("", Constants.URL_ELEMENT);
            xmlSerializer.text(String.valueOf(videoAuditInput.url));
            xmlSerializer.endTag("", Constants.URL_ELEMENT);
        }
        if (videoAuditInput.dataId != null) {
            xmlSerializer.startTag("", "DataId");
            xmlSerializer.text(String.valueOf(videoAuditInput.dataId));
            xmlSerializer.endTag("", "DataId");
        }
        if (videoAuditInput.userInfo != null) {
            QCloudXml.toXml(xmlSerializer, videoAuditInput.userInfo, "UserInfo");
        }
        xmlSerializer.endTag("", str);
    }
}
