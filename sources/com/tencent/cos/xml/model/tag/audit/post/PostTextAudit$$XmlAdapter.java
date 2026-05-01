package com.tencent.cos.xml.model.tag.audit.post;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostTextAudit$$XmlAdapter extends IXmlAdapter<PostTextAudit> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostTextAudit postTextAudit, String str) throws XmlPullParserException, IOException {
        if (postTextAudit == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (postTextAudit.input != null) {
            QCloudXml.toXml(xmlSerializer, postTextAudit.input, "Input");
        }
        if (postTextAudit.conf != null) {
            QCloudXml.toXml(xmlSerializer, postTextAudit.conf, "Conf");
        }
        xmlSerializer.endTag("", str);
    }
}
