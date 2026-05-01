package com.tencent.cos.xml.model.p033ci.audit;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostLiveVideoAudit$$XmlAdapter extends IXmlAdapter<PostLiveVideoAudit> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostLiveVideoAudit postLiveVideoAudit, String str) throws XmlPullParserException, IOException {
        if (postLiveVideoAudit == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (postLiveVideoAudit.type != null) {
            xmlSerializer.startTag("", "Type");
            xmlSerializer.text(String.valueOf(postLiveVideoAudit.type));
            xmlSerializer.endTag("", "Type");
        }
        if (postLiveVideoAudit.input != null) {
            QCloudXml.toXml(xmlSerializer, postLiveVideoAudit.input, "Input");
        }
        if (postLiveVideoAudit.conf != null) {
            QCloudXml.toXml(xmlSerializer, postLiveVideoAudit.conf, "Conf");
        }
        if (postLiveVideoAudit.storageConf != null) {
            QCloudXml.toXml(xmlSerializer, postLiveVideoAudit.storageConf, "StorageConf");
        }
        xmlSerializer.endTag("", str);
    }
}
