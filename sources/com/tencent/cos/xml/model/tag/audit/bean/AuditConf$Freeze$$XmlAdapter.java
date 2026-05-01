package com.tencent.cos.xml.model.tag.audit.bean;

import com.tencent.cos.xml.model.tag.audit.bean.AuditConf;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class AuditConf$Freeze$$XmlAdapter extends IXmlAdapter<AuditConf.Freeze> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, AuditConf.Freeze freeze, String str) throws XmlPullParserException, IOException {
        if (freeze == null) {
            return;
        }
        if (str == null) {
            str = "Freeze";
        }
        xmlSerializer.startTag("", str);
        xmlSerializer.startTag("", "PornScore");
        xmlSerializer.text(String.valueOf(freeze.pornScore));
        xmlSerializer.endTag("", "PornScore");
        xmlSerializer.startTag("", "AdsScore");
        xmlSerializer.text(String.valueOf(freeze.adsScore));
        xmlSerializer.endTag("", "AdsScore");
        xmlSerializer.startTag("", "IllegalScore");
        xmlSerializer.text(String.valueOf(freeze.illegalScore));
        xmlSerializer.endTag("", "IllegalScore");
        xmlSerializer.startTag("", "AbuseScore");
        xmlSerializer.text(String.valueOf(freeze.abuseScore));
        xmlSerializer.endTag("", "AbuseScore");
        xmlSerializer.endTag("", str);
    }
}
