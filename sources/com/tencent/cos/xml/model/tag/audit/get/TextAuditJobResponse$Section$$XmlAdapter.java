package com.tencent.cos.xml.model.tag.audit.get;

import com.tencent.cos.xml.model.tag.audit.bean.TextAuditScenarioInfo;
import com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class TextAuditJobResponse$Section$$XmlAdapter extends IXmlAdapter<TextAuditJobResponse.Section> {
    private HashMap<String, ChildElementBinder<TextAuditJobResponse.Section>> childElementBinders;

    public TextAuditJobResponse$Section$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TextAuditJobResponse.Section>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("StartByte", new ChildElementBinder<TextAuditJobResponse.Section>() { // from class: com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse$Section$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditJobResponse.Section section, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                section.startByte = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Label", new ChildElementBinder<TextAuditJobResponse.Section>() { // from class: com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse$Section$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditJobResponse.Section section, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                section.label = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Result", new ChildElementBinder<TextAuditJobResponse.Section>() { // from class: com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse$Section$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditJobResponse.Section section, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                section.result = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("IllegalInfo", new ChildElementBinder<TextAuditJobResponse.Section>() { // from class: com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse$Section$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditJobResponse.Section section, String str) throws XmlPullParserException, IOException {
                section.illegalInfo = (TextAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, TextAuditScenarioInfo.class, "IllegalInfo");
            }
        });
        this.childElementBinders.put("AbuseInfo", new ChildElementBinder<TextAuditJobResponse.Section>() { // from class: com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse$Section$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditJobResponse.Section section, String str) throws XmlPullParserException, IOException {
                section.abuseInfo = (TextAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, TextAuditScenarioInfo.class, "AbuseInfo");
            }
        });
        this.childElementBinders.put("PornInfo", new ChildElementBinder<TextAuditJobResponse.Section>() { // from class: com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse$Section$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditJobResponse.Section section, String str) throws XmlPullParserException, IOException {
                section.pornInfo = (TextAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, TextAuditScenarioInfo.class, "PornInfo");
            }
        });
        this.childElementBinders.put("TerrorismInfo", new ChildElementBinder<TextAuditJobResponse.Section>() { // from class: com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse$Section$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditJobResponse.Section section, String str) throws XmlPullParserException, IOException {
                section.terrorismInfo = (TextAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, TextAuditScenarioInfo.class, "TerrorismInfo");
            }
        });
        this.childElementBinders.put("PoliticsInfo", new ChildElementBinder<TextAuditJobResponse.Section>() { // from class: com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse$Section$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditJobResponse.Section section, String str) throws XmlPullParserException, IOException {
                section.politicsInfo = (TextAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, TextAuditScenarioInfo.class, "PoliticsInfo");
            }
        });
        this.childElementBinders.put("AdsInfo", new ChildElementBinder<TextAuditJobResponse.Section>() { // from class: com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse$Section$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditJobResponse.Section section, String str) throws XmlPullParserException, IOException {
                section.adsInfo = (TextAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, TextAuditScenarioInfo.class, "AdsInfo");
            }
        });
        this.childElementBinders.put("TeenagerInfo", new ChildElementBinder<TextAuditJobResponse.Section>() { // from class: com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse$Section$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditJobResponse.Section section, String str) throws XmlPullParserException, IOException {
                section.teenagerInfo = (TextAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, TextAuditScenarioInfo.class, "TeenagerInfo");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TextAuditJobResponse.Section fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TextAuditJobResponse.Section section = new TextAuditJobResponse.Section();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TextAuditJobResponse.Section> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, section, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Section" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return section;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return section;
    }
}
