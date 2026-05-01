package com.tencent.cos.xml.model.tag.audit.post;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostImagesAudit$$XmlAdapter extends IXmlAdapter<PostImagesAudit> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostImagesAudit postImagesAudit, String str) throws XmlPullParserException, IOException {
        if (postImagesAudit == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (postImagesAudit.input != null) {
            for (int i = 0; i < postImagesAudit.input.size(); i++) {
                QCloudXml.toXml(xmlSerializer, postImagesAudit.input.get(i), "Input");
            }
        }
        if (postImagesAudit.conf != null) {
            QCloudXml.toXml(xmlSerializer, postImagesAudit.conf, "Conf");
        }
        xmlSerializer.endTag("", str);
    }
}
