package com.tencent.cos.xml.model.tag.audit.post;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.tag.audit.post.PostDocumentAudit;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostDocumentAudit$DocumentAuditInput$$XmlAdapter extends IXmlAdapter<PostDocumentAudit.DocumentAuditInput> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostDocumentAudit.DocumentAuditInput documentAuditInput, String str) throws XmlPullParserException, IOException {
        if (documentAuditInput == null) {
            return;
        }
        if (str == null) {
            str = "Input";
        }
        xmlSerializer.startTag("", str);
        if (documentAuditInput.type != null) {
            xmlSerializer.startTag("", "Type");
            xmlSerializer.text(String.valueOf(documentAuditInput.type));
            xmlSerializer.endTag("", "Type");
        }
        if (documentAuditInput.object != null) {
            xmlSerializer.startTag("", "Object");
            xmlSerializer.text(String.valueOf(documentAuditInput.object));
            xmlSerializer.endTag("", "Object");
        }
        if (documentAuditInput.url != null) {
            xmlSerializer.startTag("", Constants.URL_ELEMENT);
            xmlSerializer.text(String.valueOf(documentAuditInput.url));
            xmlSerializer.endTag("", Constants.URL_ELEMENT);
        }
        if (documentAuditInput.dataId != null) {
            xmlSerializer.startTag("", "DataId");
            xmlSerializer.text(String.valueOf(documentAuditInput.dataId));
            xmlSerializer.endTag("", "DataId");
        }
        if (documentAuditInput.userInfo != null) {
            QCloudXml.toXml(xmlSerializer, documentAuditInput.userInfo, "UserInfo");
        }
        xmlSerializer.endTag("", str);
    }
}
