package com.tencent.cos.xml.model.p033ci.audit;

import com.tencent.cos.xml.model.p033ci.audit.PostLiveVideoAudit;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostLiveVideoAudit$PostLiveVideoAuditStorageConf$$XmlAdapter extends IXmlAdapter<PostLiveVideoAudit.PostLiveVideoAuditStorageConf> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostLiveVideoAudit.PostLiveVideoAuditStorageConf postLiveVideoAuditStorageConf, String str) throws XmlPullParserException, IOException {
        if (postLiveVideoAuditStorageConf == null) {
            return;
        }
        if (str == null) {
            str = "StorageConf";
        }
        xmlSerializer.startTag("", str);
        if (postLiveVideoAuditStorageConf.path != null) {
            xmlSerializer.startTag("", "Path");
            xmlSerializer.text(String.valueOf(postLiveVideoAuditStorageConf.path));
            xmlSerializer.endTag("", "Path");
        }
        xmlSerializer.endTag("", str);
    }
}
