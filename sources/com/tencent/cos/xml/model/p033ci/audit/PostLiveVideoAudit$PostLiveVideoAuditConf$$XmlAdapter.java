package com.tencent.cos.xml.model.p033ci.audit;

import com.tencent.cos.xml.model.p033ci.audit.PostLiveVideoAudit;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostLiveVideoAudit$PostLiveVideoAuditConf$$XmlAdapter extends IXmlAdapter<PostLiveVideoAudit.PostLiveVideoAuditConf> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostLiveVideoAudit.PostLiveVideoAuditConf postLiveVideoAuditConf, String str) throws XmlPullParserException, IOException {
        if (postLiveVideoAuditConf == null) {
            return;
        }
        if (str == null) {
            str = "Conf";
        }
        xmlSerializer.startTag("", str);
        if (postLiveVideoAuditConf.bizType != null) {
            xmlSerializer.startTag("", "BizType");
            xmlSerializer.text(String.valueOf(postLiveVideoAuditConf.bizType));
            xmlSerializer.endTag("", "BizType");
        }
        if (postLiveVideoAuditConf.callback != null) {
            xmlSerializer.startTag("", "Callback");
            xmlSerializer.text(String.valueOf(postLiveVideoAuditConf.callback));
            xmlSerializer.endTag("", "Callback");
        }
        xmlSerializer.startTag("", "CallbackType");
        xmlSerializer.text(String.valueOf(postLiveVideoAuditConf.callbackType));
        xmlSerializer.endTag("", "CallbackType");
        xmlSerializer.endTag("", str);
    }
}
