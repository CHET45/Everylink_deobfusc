package com.tencent.cos.xml.model.tag.audit.bean;

import com.tencent.cos.xml.model.tag.audit.bean.TextAuditScenarioInfo;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class TextAuditScenarioInfo$$XmlAdapter extends IXmlAdapter<TextAuditScenarioInfo> {
    private HashMap<String, ChildElementBinder<TextAuditScenarioInfo>> childElementBinders;

    public TextAuditScenarioInfo$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TextAuditScenarioInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("HitFlag", new ChildElementBinder<TextAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.TextAuditScenarioInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditScenarioInfo textAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                textAuditScenarioInfo.hitFlag = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Score", new ChildElementBinder<TextAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.TextAuditScenarioInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditScenarioInfo textAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                textAuditScenarioInfo.score = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Keywords", new ChildElementBinder<TextAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.TextAuditScenarioInfo$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditScenarioInfo textAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                textAuditScenarioInfo.keywords = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Category", new ChildElementBinder<TextAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.TextAuditScenarioInfo$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditScenarioInfo textAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                textAuditScenarioInfo.category = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SubLabel", new ChildElementBinder<TextAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.TextAuditScenarioInfo$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditScenarioInfo textAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                textAuditScenarioInfo.subLabel = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("LibResults", new ChildElementBinder<TextAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.TextAuditScenarioInfo$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditScenarioInfo textAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                if (textAuditScenarioInfo.libResults == null) {
                    textAuditScenarioInfo.libResults = new ArrayList();
                }
                textAuditScenarioInfo.libResults.add((TextAuditScenarioInfo.LibResults) QCloudXml.fromXml(xmlPullParser, TextAuditScenarioInfo.LibResults.class, "LibResults"));
            }
        });
        this.childElementBinders.put("SpeakerResults", new ChildElementBinder<TextAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.TextAuditScenarioInfo$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditScenarioInfo textAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                if (textAuditScenarioInfo.speakerResults == null) {
                    textAuditScenarioInfo.speakerResults = new ArrayList();
                }
                textAuditScenarioInfo.speakerResults.add((TextAuditScenarioInfo.Results) QCloudXml.fromXml(xmlPullParser, TextAuditScenarioInfo.Results.class, "SpeakerResults"));
            }
        });
        this.childElementBinders.put("RecognitionResults", new ChildElementBinder<TextAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.TextAuditScenarioInfo$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditScenarioInfo textAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                if (textAuditScenarioInfo.recognitionResults == null) {
                    textAuditScenarioInfo.recognitionResults = new ArrayList();
                }
                textAuditScenarioInfo.recognitionResults.add((TextAuditScenarioInfo.Results) QCloudXml.fromXml(xmlPullParser, TextAuditScenarioInfo.Results.class, "RecognitionResults"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TextAuditScenarioInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TextAuditScenarioInfo textAuditScenarioInfo = new TextAuditScenarioInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TextAuditScenarioInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, textAuditScenarioInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "TextAuditScenarioInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return textAuditScenarioInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return textAuditScenarioInfo;
    }
}
