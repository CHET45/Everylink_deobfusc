package com.tencent.cos.xml.model.tag.audit.post;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostWebPageAudit$$XmlAdapter extends IXmlAdapter<PostWebPageAudit> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostWebPageAudit postWebPageAudit, String str) throws XmlPullParserException, IOException {
        if (postWebPageAudit == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (postWebPageAudit.input != null) {
            QCloudXml.toXml(xmlSerializer, postWebPageAudit.input, "Input");
        }
        if (postWebPageAudit.conf != null) {
            QCloudXml.toXml(xmlSerializer, postWebPageAudit.conf, "Conf");
        }
        xmlSerializer.endTag("", str);
    }
}
