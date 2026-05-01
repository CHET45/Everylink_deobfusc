package com.tencent.cos.xml.model.p033ci.audit;

import com.microsoft.azure.storage.Constants;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostImageAuditReport$$XmlAdapter extends IXmlAdapter<PostImageAuditReport> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostImageAuditReport postImageAuditReport, String str) throws XmlPullParserException, IOException {
        if (postImageAuditReport == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        xmlSerializer.startTag("", "ContentType");
        xmlSerializer.text(String.valueOf(postImageAuditReport.contentType));
        xmlSerializer.endTag("", "ContentType");
        if (postImageAuditReport.url != null) {
            xmlSerializer.startTag("", Constants.URL_ELEMENT);
            xmlSerializer.text(String.valueOf(postImageAuditReport.url));
            xmlSerializer.endTag("", Constants.URL_ELEMENT);
        }
        if (postImageAuditReport.label != null) {
            xmlSerializer.startTag("", "Label");
            xmlSerializer.text(String.valueOf(postImageAuditReport.label));
            xmlSerializer.endTag("", "Label");
        }
        if (postImageAuditReport.suggestedLabel != null) {
            xmlSerializer.startTag("", "SuggestedLabel");
            xmlSerializer.text(String.valueOf(postImageAuditReport.suggestedLabel));
            xmlSerializer.endTag("", "SuggestedLabel");
        }
        if (postImageAuditReport.jobId != null) {
            xmlSerializer.startTag("", "JobId");
            xmlSerializer.text(String.valueOf(postImageAuditReport.jobId));
            xmlSerializer.endTag("", "JobId");
        }
        if (postImageAuditReport.moderationTime != null) {
            xmlSerializer.startTag("", "ModerationTime");
            xmlSerializer.text(String.valueOf(postImageAuditReport.moderationTime));
            xmlSerializer.endTag("", "ModerationTime");
        }
        xmlSerializer.endTag("", str);
    }
}
