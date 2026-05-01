package com.tencent.cos.xml.model.tag.audit.bean;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class AuditEncryption$$XmlAdapter extends IXmlAdapter<AuditEncryption> {
    private HashMap<String, ChildElementBinder<AuditEncryption>> childElementBinders;

    public AuditEncryption$$XmlAdapter() {
        HashMap<String, ChildElementBinder<AuditEncryption>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Algorithm", new ChildElementBinder<AuditEncryption>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditEncryption$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditEncryption auditEncryption, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditEncryption.algorithm = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Key", new ChildElementBinder<AuditEncryption>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditEncryption$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditEncryption auditEncryption, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditEncryption.key = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("IV", new ChildElementBinder<AuditEncryption>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditEncryption$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditEncryption auditEncryption, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditEncryption.f1844iV = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("KeyId", new ChildElementBinder<AuditEncryption>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditEncryption$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditEncryption auditEncryption, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditEncryption.keyId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("KeyType", new ChildElementBinder<AuditEncryption>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditEncryption$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditEncryption auditEncryption, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditEncryption.keyType = Integer.parseInt(xmlPullParser.getText());
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public AuditEncryption fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        AuditEncryption auditEncryption = new AuditEncryption();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<AuditEncryption> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, auditEncryption, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Encryption" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return auditEncryption;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return auditEncryption;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, AuditEncryption auditEncryption, String str) throws XmlPullParserException, IOException {
        if (auditEncryption == null) {
            return;
        }
        if (str == null) {
            str = "Encryption";
        }
        xmlSerializer.startTag("", str);
        if (auditEncryption.algorithm != null) {
            xmlSerializer.startTag("", "Algorithm");
            xmlSerializer.text(String.valueOf(auditEncryption.algorithm));
            xmlSerializer.endTag("", "Algorithm");
        }
        if (auditEncryption.key != null) {
            xmlSerializer.startTag("", "Key");
            xmlSerializer.text(String.valueOf(auditEncryption.key));
            xmlSerializer.endTag("", "Key");
        }
        if (auditEncryption.f1844iV != null) {
            xmlSerializer.startTag("", "IV");
            xmlSerializer.text(String.valueOf(auditEncryption.f1844iV));
            xmlSerializer.endTag("", "IV");
        }
        if (auditEncryption.keyId != null) {
            xmlSerializer.startTag("", "KeyId");
            xmlSerializer.text(String.valueOf(auditEncryption.keyId));
            xmlSerializer.endTag("", "KeyId");
        }
        xmlSerializer.startTag("", "KeyType");
        xmlSerializer.text(String.valueOf(auditEncryption.keyType));
        xmlSerializer.endTag("", "KeyType");
        xmlSerializer.endTag("", str);
    }
}
