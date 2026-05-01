package com.tencent.cos.xml.model.p033ci.audit;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class AddAuditTextlibKeyword$$XmlAdapter extends IXmlAdapter<AddAuditTextlibKeyword> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, AddAuditTextlibKeyword addAuditTextlibKeyword, String str) throws XmlPullParserException, IOException {
        if (addAuditTextlibKeyword == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (addAuditTextlibKeyword.keywords != null) {
            for (int i = 0; i < addAuditTextlibKeyword.keywords.size(); i++) {
                QCloudXml.toXml(xmlSerializer, addAuditTextlibKeyword.keywords.get(i), "Keywords");
            }
        }
        xmlSerializer.endTag("", str);
    }
}
