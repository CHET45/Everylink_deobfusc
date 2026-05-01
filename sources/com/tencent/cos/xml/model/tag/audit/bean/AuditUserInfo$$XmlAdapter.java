package com.tencent.cos.xml.model.tag.audit.bean;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class AuditUserInfo$$XmlAdapter extends IXmlAdapter<AuditUserInfo> {
    private HashMap<String, ChildElementBinder<AuditUserInfo>> childElementBinders;

    public AuditUserInfo$$XmlAdapter() {
        HashMap<String, ChildElementBinder<AuditUserInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("TokenId", new ChildElementBinder<AuditUserInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditUserInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditUserInfo auditUserInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditUserInfo.tokenId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Nickname", new ChildElementBinder<AuditUserInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditUserInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditUserInfo auditUserInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditUserInfo.nickname = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("DeviceId", new ChildElementBinder<AuditUserInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditUserInfo$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditUserInfo auditUserInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditUserInfo.deviceId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("AppId", new ChildElementBinder<AuditUserInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditUserInfo$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditUserInfo auditUserInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditUserInfo.appId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Room", new ChildElementBinder<AuditUserInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditUserInfo$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditUserInfo auditUserInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditUserInfo.room = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("IP", new ChildElementBinder<AuditUserInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditUserInfo$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditUserInfo auditUserInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditUserInfo.f1847iP = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Type", new ChildElementBinder<AuditUserInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditUserInfo$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditUserInfo auditUserInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditUserInfo.type = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("ReceiveTokenId", new ChildElementBinder<AuditUserInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditUserInfo$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditUserInfo auditUserInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditUserInfo.receiveTokenId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Gender", new ChildElementBinder<AuditUserInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditUserInfo$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditUserInfo auditUserInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditUserInfo.gender = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Level", new ChildElementBinder<AuditUserInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditUserInfo$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditUserInfo auditUserInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditUserInfo.level = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Role", new ChildElementBinder<AuditUserInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditUserInfo$$XmlAdapter.11
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditUserInfo auditUserInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditUserInfo.role = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public AuditUserInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        AuditUserInfo auditUserInfo = new AuditUserInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<AuditUserInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, auditUserInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "UserInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return auditUserInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return auditUserInfo;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, AuditUserInfo auditUserInfo, String str) throws XmlPullParserException, IOException {
        if (auditUserInfo == null) {
            return;
        }
        if (str == null) {
            str = "UserInfo";
        }
        xmlSerializer.startTag("", str);
        if (auditUserInfo.tokenId != null) {
            xmlSerializer.startTag("", "TokenId");
            xmlSerializer.text(String.valueOf(auditUserInfo.tokenId));
            xmlSerializer.endTag("", "TokenId");
        }
        if (auditUserInfo.nickname != null) {
            xmlSerializer.startTag("", "Nickname");
            xmlSerializer.text(String.valueOf(auditUserInfo.nickname));
            xmlSerializer.endTag("", "Nickname");
        }
        if (auditUserInfo.deviceId != null) {
            xmlSerializer.startTag("", "DeviceId");
            xmlSerializer.text(String.valueOf(auditUserInfo.deviceId));
            xmlSerializer.endTag("", "DeviceId");
        }
        if (auditUserInfo.appId != null) {
            xmlSerializer.startTag("", "AppId");
            xmlSerializer.text(String.valueOf(auditUserInfo.appId));
            xmlSerializer.endTag("", "AppId");
        }
        if (auditUserInfo.room != null) {
            xmlSerializer.startTag("", "Room");
            xmlSerializer.text(String.valueOf(auditUserInfo.room));
            xmlSerializer.endTag("", "Room");
        }
        if (auditUserInfo.f1847iP != null) {
            xmlSerializer.startTag("", "IP");
            xmlSerializer.text(String.valueOf(auditUserInfo.f1847iP));
            xmlSerializer.endTag("", "IP");
        }
        if (auditUserInfo.type != null) {
            xmlSerializer.startTag("", "Type");
            xmlSerializer.text(String.valueOf(auditUserInfo.type));
            xmlSerializer.endTag("", "Type");
        }
        if (auditUserInfo.receiveTokenId != null) {
            xmlSerializer.startTag("", "ReceiveTokenId");
            xmlSerializer.text(String.valueOf(auditUserInfo.receiveTokenId));
            xmlSerializer.endTag("", "ReceiveTokenId");
        }
        if (auditUserInfo.gender != null) {
            xmlSerializer.startTag("", "Gender");
            xmlSerializer.text(String.valueOf(auditUserInfo.gender));
            xmlSerializer.endTag("", "Gender");
        }
        if (auditUserInfo.level != null) {
            xmlSerializer.startTag("", "Level");
            xmlSerializer.text(String.valueOf(auditUserInfo.level));
            xmlSerializer.endTag("", "Level");
        }
        if (auditUserInfo.role != null) {
            xmlSerializer.startTag("", "Role");
            xmlSerializer.text(String.valueOf(auditUserInfo.role));
            xmlSerializer.endTag("", "Role");
        }
        xmlSerializer.endTag("", str);
    }
}
