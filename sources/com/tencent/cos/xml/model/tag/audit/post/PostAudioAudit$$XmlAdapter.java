package com.tencent.cos.xml.model.tag.audit.post;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostAudioAudit$$XmlAdapter extends IXmlAdapter<PostAudioAudit> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostAudioAudit postAudioAudit, String str) throws XmlPullParserException, IOException {
        if (postAudioAudit == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (postAudioAudit.input != null) {
            QCloudXml.toXml(xmlSerializer, postAudioAudit.input, "Input");
        }
        if (postAudioAudit.conf != null) {
            QCloudXml.toXml(xmlSerializer, postAudioAudit.conf, "Conf");
        }
        xmlSerializer.endTag("", str);
    }
}
