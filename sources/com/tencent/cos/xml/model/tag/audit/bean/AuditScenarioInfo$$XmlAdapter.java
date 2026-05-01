package com.tencent.cos.xml.model.tag.audit.bean;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class AuditScenarioInfo$$XmlAdapter extends IXmlAdapter<AuditScenarioInfo> {
    private HashMap<String, ChildElementBinder<AuditScenarioInfo>> childElementBinders;

    public AuditScenarioInfo$$XmlAdapter() {
        HashMap<String, ChildElementBinder<AuditScenarioInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("HitFlag", new ChildElementBinder<AuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditScenarioInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditScenarioInfo auditScenarioInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditScenarioInfo.hitFlag = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Count", new ChildElementBinder<AuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditScenarioInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditScenarioInfo auditScenarioInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditScenarioInfo.count = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Score", new ChildElementBinder<AuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditScenarioInfo$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditScenarioInfo auditScenarioInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditScenarioInfo.score = Integer.parseInt(xmlPullParser.getText());
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public AuditScenarioInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        AuditScenarioInfo auditScenarioInfo = new AuditScenarioInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<AuditScenarioInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, auditScenarioInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "AuditScenarioInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return auditScenarioInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return auditScenarioInfo;
    }
}
