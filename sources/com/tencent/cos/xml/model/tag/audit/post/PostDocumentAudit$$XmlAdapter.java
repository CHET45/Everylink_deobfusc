package com.tencent.cos.xml.model.tag.audit.post;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostDocumentAudit$$XmlAdapter extends IXmlAdapter<PostDocumentAudit> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostDocumentAudit postDocumentAudit, String str) throws XmlPullParserException, IOException {
        if (postDocumentAudit == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (postDocumentAudit.input != null) {
            QCloudXml.toXml(xmlSerializer, postDocumentAudit.input, "Input");
        }
        if (postDocumentAudit.conf != null) {
            QCloudXml.toXml(xmlSerializer, postDocumentAudit.conf, "Conf");
        }
        xmlSerializer.endTag("", str);
    }
}
