package com.tencent.cos.xml.model.tag.audit.post;

import com.tencent.cos.xml.model.tag.audit.post.PostWebPageAudit;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostWebPageAudit$WebPageAuditConf$$XmlAdapter extends IXmlAdapter<PostWebPageAudit.WebPageAuditConf> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostWebPageAudit.WebPageAuditConf webPageAuditConf, String str) throws XmlPullParserException, IOException {
        if (webPageAuditConf == null) {
            return;
        }
        if (str == null) {
            str = "Conf";
        }
        xmlSerializer.startTag("", str);
        xmlSerializer.startTag("", "ReturnHighlightHtml");
        xmlSerializer.text(String.valueOf(webPageAuditConf.returnHighlightHtml));
        xmlSerializer.endTag("", "ReturnHighlightHtml");
        if (webPageAuditConf.detectType != null) {
            xmlSerializer.startTag("", "DetectType");
            xmlSerializer.text(String.valueOf(webPageAuditConf.detectType));
            xmlSerializer.endTag("", "DetectType");
        }
        xmlSerializer.startTag("", "Async");
        xmlSerializer.text(String.valueOf(webPageAuditConf.async));
        xmlSerializer.endTag("", "Async");
        if (webPageAuditConf.callback != null) {
            xmlSerializer.startTag("", "Callback");
            xmlSerializer.text(String.valueOf(webPageAuditConf.callback));
            xmlSerializer.endTag("", "Callback");
        }
        if (webPageAuditConf.callbackType != 0) {
            xmlSerializer.startTag("", "CallbackType");
            xmlSerializer.text(String.valueOf(webPageAuditConf.callbackType));
            xmlSerializer.endTag("", "CallbackType");
        }
        if (webPageAuditConf.bizType != null) {
            xmlSerializer.startTag("", "BizType");
            xmlSerializer.text(String.valueOf(webPageAuditConf.bizType));
            xmlSerializer.endTag("", "BizType");
        }
        if (webPageAuditConf.freeze != null) {
            QCloudXml.toXml(xmlSerializer, webPageAuditConf.freeze, "Freeze");
        }
        xmlSerializer.endTag("", str);
    }
}
