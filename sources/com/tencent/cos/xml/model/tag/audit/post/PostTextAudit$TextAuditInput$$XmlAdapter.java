package com.tencent.cos.xml.model.tag.audit.post;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.tag.audit.post.PostTextAudit;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostTextAudit$TextAuditInput$$XmlAdapter extends IXmlAdapter<PostTextAudit.TextAuditInput> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostTextAudit.TextAuditInput textAuditInput, String str) throws XmlPullParserException, IOException {
        if (textAuditInput == null) {
            return;
        }
        if (str == null) {
            str = "Input";
        }
        xmlSerializer.startTag("", str);
        if (textAuditInput.content != null) {
            xmlSerializer.startTag("", "Content");
            xmlSerializer.text(String.valueOf(textAuditInput.content));
            xmlSerializer.endTag("", "Content");
        }
        if (textAuditInput.object != null) {
            xmlSerializer.startTag("", "Object");
            xmlSerializer.text(String.valueOf(textAuditInput.object));
            xmlSerializer.endTag("", "Object");
        }
        if (textAuditInput.url != null) {
            xmlSerializer.startTag("", Constants.URL_ELEMENT);
            xmlSerializer.text(String.valueOf(textAuditInput.url));
            xmlSerializer.endTag("", Constants.URL_ELEMENT);
        }
        if (textAuditInput.dataId != null) {
            xmlSerializer.startTag("", "DataId");
            xmlSerializer.text(String.valueOf(textAuditInput.dataId));
            xmlSerializer.endTag("", "DataId");
        }
        if (textAuditInput.userInfo != null) {
            QCloudXml.toXml(xmlSerializer, textAuditInput.userInfo, "UserInfo");
        }
        xmlSerializer.endTag("", str);
    }
}
