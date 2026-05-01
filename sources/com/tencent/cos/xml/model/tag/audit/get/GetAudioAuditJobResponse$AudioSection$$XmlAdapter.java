package com.tencent.cos.xml.model.tag.audit.get;

import androidx.exifinterface.media.ExifInterface;
import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.tag.audit.bean.TextAuditScenarioInfo;
import com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetAudioAuditJobResponse$AudioSection$$XmlAdapter extends IXmlAdapter<GetAudioAuditJobResponse.AudioSection> {
    private HashMap<String, ChildElementBinder<GetAudioAuditJobResponse.AudioSection>> childElementBinders;

    public GetAudioAuditJobResponse$AudioSection$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetAudioAuditJobResponse.AudioSection>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.URL_ELEMENT, new ChildElementBinder<GetAudioAuditJobResponse.AudioSection>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioSection$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioSection audioSection, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioSection.url = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Text", new ChildElementBinder<GetAudioAuditJobResponse.AudioSection>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioSection$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioSection audioSection, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioSection.text = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(ExifInterface.TAG_OFFSET_TIME, new ChildElementBinder<GetAudioAuditJobResponse.AudioSection>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioSection$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioSection audioSection, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioSection.offsetTime = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Duration", new ChildElementBinder<GetAudioAuditJobResponse.AudioSection>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioSection$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioSection audioSection, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioSection.duration = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Label", new ChildElementBinder<GetAudioAuditJobResponse.AudioSection>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioSection$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioSection audioSection, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioSection.label = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SubLabel", new ChildElementBinder<GetAudioAuditJobResponse.AudioSection>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioSection$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioSection audioSection, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioSection.subLabel = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Result", new ChildElementBinder<GetAudioAuditJobResponse.AudioSection>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioSection$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioSection audioSection, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioSection.result = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("LanguageResults", new ChildElementBinder<GetAudioAuditJobResponse.AudioSection>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioSection$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioSection audioSection, String str) throws XmlPullParserException, IOException {
                if (audioSection.languageResults == null) {
                    audioSection.languageResults = new ArrayList();
                }
                audioSection.languageResults.add((TextAuditScenarioInfo.Results) QCloudXml.fromXml(xmlPullParser, TextAuditScenarioInfo.Results.class, "LanguageResults"));
            }
        });
        this.childElementBinders.put("PornInfo", new ChildElementBinder<GetAudioAuditJobResponse.AudioSection>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioSection$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioSection audioSection, String str) throws XmlPullParserException, IOException {
                audioSection.pornInfo = (TextAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, TextAuditScenarioInfo.class, "PornInfo");
            }
        });
        this.childElementBinders.put("TerrorismInfo", new ChildElementBinder<GetAudioAuditJobResponse.AudioSection>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioSection$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioSection audioSection, String str) throws XmlPullParserException, IOException {
                audioSection.terrorismInfo = (TextAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, TextAuditScenarioInfo.class, "TerrorismInfo");
            }
        });
        this.childElementBinders.put("PoliticsInfo", new ChildElementBinder<GetAudioAuditJobResponse.AudioSection>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioSection$$XmlAdapter.11
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioSection audioSection, String str) throws XmlPullParserException, IOException {
                audioSection.politicsInfo = (TextAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, TextAuditScenarioInfo.class, "PoliticsInfo");
            }
        });
        this.childElementBinders.put("AdsInfo", new ChildElementBinder<GetAudioAuditJobResponse.AudioSection>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioSection$$XmlAdapter.12
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioSection audioSection, String str) throws XmlPullParserException, IOException {
                audioSection.adsInfo = (TextAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, TextAuditScenarioInfo.class, "AdsInfo");
            }
        });
        this.childElementBinders.put("TeenagerInfo", new ChildElementBinder<GetAudioAuditJobResponse.AudioSection>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioSection$$XmlAdapter.13
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioSection audioSection, String str) throws XmlPullParserException, IOException {
                audioSection.teenagerInfo = (TextAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, TextAuditScenarioInfo.class, "TeenagerInfo");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetAudioAuditJobResponse.AudioSection fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetAudioAuditJobResponse.AudioSection audioSection = new GetAudioAuditJobResponse.AudioSection();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetAudioAuditJobResponse.AudioSection> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, audioSection, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "AudioSection" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return audioSection;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return audioSection;
    }
}
