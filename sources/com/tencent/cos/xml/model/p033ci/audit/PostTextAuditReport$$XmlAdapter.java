package com.tencent.cos.xml.model.p033ci.audit;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostTextAuditReport$$XmlAdapter extends IXmlAdapter<PostTextAuditReport> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostTextAuditReport postTextAuditReport, String str) throws XmlPullParserException, IOException {
        if (postTextAuditReport == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        xmlSerializer.startTag("", "ContentType");
        xmlSerializer.text(String.valueOf(postTextAuditReport.contentType));
        xmlSerializer.endTag("", "ContentType");
        if (postTextAuditReport.text != null) {
            xmlSerializer.startTag("", "Text");
            xmlSerializer.text(String.valueOf(postTextAuditReport.text));
            xmlSerializer.endTag("", "Text");
        }
        if (postTextAuditReport.label != null) {
            xmlSerializer.startTag("", "Label");
            xmlSerializer.text(String.valueOf(postTextAuditReport.label));
            xmlSerializer.endTag("", "Label");
        }
        if (postTextAuditReport.suggestedLabel != null) {
            xmlSerializer.startTag("", "SuggestedLabel");
            xmlSerializer.text(String.valueOf(postTextAuditReport.suggestedLabel));
            xmlSerializer.endTag("", "SuggestedLabel");
        }
        if (postTextAuditReport.jobId != null) {
            xmlSerializer.startTag("", "JobId");
            xmlSerializer.text(String.valueOf(postTextAuditReport.jobId));
            xmlSerializer.endTag("", "JobId");
        }
        if (postTextAuditReport.moderationTime != null) {
            xmlSerializer.startTag("", "ModerationTime");
            xmlSerializer.text(String.valueOf(postTextAuditReport.moderationTime));
            xmlSerializer.endTag("", "ModerationTime");
        }
        xmlSerializer.endTag("", str);
    }
}
