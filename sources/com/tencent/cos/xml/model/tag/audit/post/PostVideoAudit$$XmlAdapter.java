package com.tencent.cos.xml.model.tag.audit.post;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostVideoAudit$$XmlAdapter extends IXmlAdapter<PostVideoAudit> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostVideoAudit postVideoAudit, String str) throws XmlPullParserException, IOException {
        if (postVideoAudit == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (postVideoAudit.input != null) {
            QCloudXml.toXml(xmlSerializer, postVideoAudit.input, "Input");
        }
        if (postVideoAudit.conf != null) {
            QCloudXml.toXml(xmlSerializer, postVideoAudit.conf, "Conf");
        }
        xmlSerializer.endTag("", str);
    }
}
