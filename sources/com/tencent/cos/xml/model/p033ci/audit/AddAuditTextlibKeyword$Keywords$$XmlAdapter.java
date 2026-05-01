package com.tencent.cos.xml.model.p033ci.audit;

import com.tencent.cos.xml.model.p033ci.audit.AddAuditTextlibKeyword;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class AddAuditTextlibKeyword$Keywords$$XmlAdapter extends IXmlAdapter<AddAuditTextlibKeyword.Keywords> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, AddAuditTextlibKeyword.Keywords keywords, String str) throws XmlPullParserException, IOException {
        if (keywords == null) {
            return;
        }
        if (str == null) {
            str = "Keywords";
        }
        xmlSerializer.startTag("", str);
        if (keywords.content != null) {
            xmlSerializer.startTag("", "Content");
            xmlSerializer.text(String.valueOf(keywords.content));
            xmlSerializer.endTag("", "Content");
        }
        if (keywords.label != null) {
            xmlSerializer.startTag("", "Label");
            xmlSerializer.text(String.valueOf(keywords.label));
            xmlSerializer.endTag("", "Label");
        }
        if (keywords.remark != null) {
            xmlSerializer.startTag("", "Remark");
            xmlSerializer.text(String.valueOf(keywords.remark));
            xmlSerializer.endTag("", "Remark");
        }
        xmlSerializer.endTag("", str);
    }
}
