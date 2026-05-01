package com.tencent.cos.xml.model.tag.audit.bean;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class AuditSection$$XmlAdapter extends IXmlAdapter<AuditSection> {
    private HashMap<String, ChildElementBinder<AuditSection>> childElementBinders;

    public AuditSection$$XmlAdapter() {
        HashMap<String, ChildElementBinder<AuditSection>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("PornInfo", new ChildElementBinder<AuditSection>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditSection$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditSection auditSection, String str) throws XmlPullParserException, IOException {
                auditSection.pornInfo = (TextAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, TextAuditScenarioInfo.class, "PornInfo");
            }
        });
        this.childElementBinders.put("TerrorismInfo", new ChildElementBinder<AuditSection>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditSection$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditSection auditSection, String str) throws XmlPullParserException, IOException {
                auditSection.terrorismInfo = (TextAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, TextAuditScenarioInfo.class, "TerrorismInfo");
            }
        });
        this.childElementBinders.put("PoliticsInfo", new ChildElementBinder<AuditSection>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditSection$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditSection auditSection, String str) throws XmlPullParserException, IOException {
                auditSection.politicsInfo = (TextAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, TextAuditScenarioInfo.class, "PoliticsInfo");
            }
        });
        this.childElementBinders.put("AdsInfo", new ChildElementBinder<AuditSection>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditSection$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditSection auditSection, String str) throws XmlPullParserException, IOException {
                auditSection.adsInfo = (TextAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, TextAuditScenarioInfo.class, "AdsInfo");
            }
        });
        this.childElementBinders.put("TeenagerInfo", new ChildElementBinder<AuditSection>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditSection$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditSection auditSection, String str) throws XmlPullParserException, IOException {
                auditSection.teenagerInfo = (TextAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, TextAuditScenarioInfo.class, "TeenagerInfo");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public AuditSection fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        AuditSection auditSection = new AuditSection();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<AuditSection> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, auditSection, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "AuditSection" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return auditSection;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return auditSection;
    }
}
