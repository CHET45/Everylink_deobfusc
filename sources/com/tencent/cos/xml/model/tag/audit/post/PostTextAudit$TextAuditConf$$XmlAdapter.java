package com.tencent.cos.xml.model.tag.audit.post;

import com.tencent.cos.xml.model.tag.audit.post.PostTextAudit;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostTextAudit$TextAuditConf$$XmlAdapter extends IXmlAdapter<PostTextAudit.TextAuditConf> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostTextAudit.TextAuditConf textAuditConf, String str) throws XmlPullParserException, IOException {
        if (textAuditConf == null) {
            return;
        }
        if (str == null) {
            str = "Conf";
        }
        xmlSerializer.startTag("", str);
        if (textAuditConf.callbackVersion != null) {
            xmlSerializer.startTag("", "CallbackVersion");
            xmlSerializer.text(String.valueOf(textAuditConf.callbackVersion));
            xmlSerializer.endTag("", "CallbackVersion");
        }
        if (textAuditConf.detectType != null) {
            xmlSerializer.startTag("", "DetectType");
            xmlSerializer.text(String.valueOf(textAuditConf.detectType));
            xmlSerializer.endTag("", "DetectType");
        }
        xmlSerializer.startTag("", "Async");
        xmlSerializer.text(String.valueOf(textAuditConf.async));
        xmlSerializer.endTag("", "Async");
        if (textAuditConf.callback != null) {
            xmlSerializer.startTag("", "Callback");
            xmlSerializer.text(String.valueOf(textAuditConf.callback));
            xmlSerializer.endTag("", "Callback");
        }
        if (textAuditConf.callbackType != 0) {
            xmlSerializer.startTag("", "CallbackType");
            xmlSerializer.text(String.valueOf(textAuditConf.callbackType));
            xmlSerializer.endTag("", "CallbackType");
        }
        if (textAuditConf.bizType != null) {
            xmlSerializer.startTag("", "BizType");
            xmlSerializer.text(String.valueOf(textAuditConf.bizType));
            xmlSerializer.endTag("", "BizType");
        }
        if (textAuditConf.freeze != null) {
            QCloudXml.toXml(xmlSerializer, textAuditConf.freeze, "Freeze");
        }
        xmlSerializer.endTag("", str);
    }
}
