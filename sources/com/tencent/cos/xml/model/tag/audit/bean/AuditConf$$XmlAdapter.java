package com.tencent.cos.xml.model.tag.audit.bean;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class AuditConf$$XmlAdapter extends IXmlAdapter<AuditConf> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, AuditConf auditConf, String str) throws XmlPullParserException, IOException {
        if (auditConf == null) {
            return;
        }
        if (str == null) {
            str = "Conf";
        }
        xmlSerializer.startTag("", str);
        if (auditConf.detectType != null) {
            xmlSerializer.startTag("", "DetectType");
            xmlSerializer.text(String.valueOf(auditConf.detectType));
            xmlSerializer.endTag("", "DetectType");
        }
        xmlSerializer.startTag("", "Async");
        xmlSerializer.text(String.valueOf(auditConf.async));
        xmlSerializer.endTag("", "Async");
        if (auditConf.callback != null) {
            xmlSerializer.startTag("", "Callback");
            xmlSerializer.text(String.valueOf(auditConf.callback));
            xmlSerializer.endTag("", "Callback");
        }
        if (auditConf.callbackType != 0) {
            xmlSerializer.startTag("", "CallbackType");
            xmlSerializer.text(String.valueOf(auditConf.callbackType));
            xmlSerializer.endTag("", "CallbackType");
        }
        if (auditConf.bizType != null) {
            xmlSerializer.startTag("", "BizType");
            xmlSerializer.text(String.valueOf(auditConf.bizType));
            xmlSerializer.endTag("", "BizType");
        }
        if (auditConf.freeze != null) {
            QCloudXml.toXml(xmlSerializer, auditConf.freeze, "Freeze");
        }
        xmlSerializer.endTag("", str);
    }
}
