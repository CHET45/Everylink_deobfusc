package com.tencent.cos.xml.model.tag.audit.bean;

import com.microsoft.azure.storage.Constants;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class AuditInput$$XmlAdapter extends IXmlAdapter<AuditInput> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, AuditInput auditInput, String str) throws XmlPullParserException, IOException {
        if (auditInput == null) {
            return;
        }
        if (str == null) {
            str = "Input";
        }
        xmlSerializer.startTag("", str);
        if (auditInput.object != null) {
            xmlSerializer.startTag("", "Object");
            xmlSerializer.text(String.valueOf(auditInput.object));
            xmlSerializer.endTag("", "Object");
        }
        if (auditInput.url != null) {
            xmlSerializer.startTag("", Constants.URL_ELEMENT);
            xmlSerializer.text(String.valueOf(auditInput.url));
            xmlSerializer.endTag("", Constants.URL_ELEMENT);
        }
        if (auditInput.dataId != null) {
            xmlSerializer.startTag("", "DataId");
            xmlSerializer.text(String.valueOf(auditInput.dataId));
            xmlSerializer.endTag("", "DataId");
        }
        if (auditInput.userInfo != null) {
            QCloudXml.toXml(xmlSerializer, auditInput.userInfo, "UserInfo");
        }
        xmlSerializer.endTag("", str);
    }
}
