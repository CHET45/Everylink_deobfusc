package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.media.TemplateVoiceSeparateResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.TemplateVoiceSeparateResponse$TemplateVoiceSeparateResponseTemplate$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3840xc1966f86 extends IXmlAdapter<TemplateVoiceSeparateResponse.TemplateVoiceSeparateResponseTemplate> {
    private HashMap<String, ChildElementBinder<TemplateVoiceSeparateResponse.TemplateVoiceSeparateResponseTemplate>> childElementBinders;

    public C3840xc1966f86() {
        HashMap<String, ChildElementBinder<TemplateVoiceSeparateResponse.TemplateVoiceSeparateResponseTemplate>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("TemplateId", new ChildElementBinder<TemplateVoiceSeparateResponse.TemplateVoiceSeparateResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVoiceSeparateResponse$TemplateVoiceSeparateResponseTemplate$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVoiceSeparateResponse.TemplateVoiceSeparateResponseTemplate templateVoiceSeparateResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateVoiceSeparateResponseTemplate.templateId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.NAME_ELEMENT, new ChildElementBinder<TemplateVoiceSeparateResponse.TemplateVoiceSeparateResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVoiceSeparateResponse$TemplateVoiceSeparateResponseTemplate$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVoiceSeparateResponse.TemplateVoiceSeparateResponseTemplate templateVoiceSeparateResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateVoiceSeparateResponseTemplate.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BucketId", new ChildElementBinder<TemplateVoiceSeparateResponse.TemplateVoiceSeparateResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVoiceSeparateResponse$TemplateVoiceSeparateResponseTemplate$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVoiceSeparateResponse.TemplateVoiceSeparateResponseTemplate templateVoiceSeparateResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateVoiceSeparateResponseTemplate.bucketId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Category", new ChildElementBinder<TemplateVoiceSeparateResponse.TemplateVoiceSeparateResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVoiceSeparateResponse$TemplateVoiceSeparateResponseTemplate$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVoiceSeparateResponse.TemplateVoiceSeparateResponseTemplate templateVoiceSeparateResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateVoiceSeparateResponseTemplate.category = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Tag", new ChildElementBinder<TemplateVoiceSeparateResponse.TemplateVoiceSeparateResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVoiceSeparateResponse$TemplateVoiceSeparateResponseTemplate$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVoiceSeparateResponse.TemplateVoiceSeparateResponseTemplate templateVoiceSeparateResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateVoiceSeparateResponseTemplate.tag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("UpdateTime", new ChildElementBinder<TemplateVoiceSeparateResponse.TemplateVoiceSeparateResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVoiceSeparateResponse$TemplateVoiceSeparateResponseTemplate$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVoiceSeparateResponse.TemplateVoiceSeparateResponseTemplate templateVoiceSeparateResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateVoiceSeparateResponseTemplate.updateTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreateTime", new ChildElementBinder<TemplateVoiceSeparateResponse.TemplateVoiceSeparateResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVoiceSeparateResponse$TemplateVoiceSeparateResponseTemplate$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVoiceSeparateResponse.TemplateVoiceSeparateResponseTemplate templateVoiceSeparateResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateVoiceSeparateResponseTemplate.createTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("VoiceSeparate", new ChildElementBinder<TemplateVoiceSeparateResponse.TemplateVoiceSeparateResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVoiceSeparateResponse$TemplateVoiceSeparateResponseTemplate$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVoiceSeparateResponse.TemplateVoiceSeparateResponseTemplate templateVoiceSeparateResponseTemplate, String str) throws XmlPullParserException, IOException {
                templateVoiceSeparateResponseTemplate.voiceSeparate = (TemplateVoiceSeparateResponse.TemplateVoiceSeparateResponseVoiceSeparate) QCloudXml.fromXml(xmlPullParser, TemplateVoiceSeparateResponse.TemplateVoiceSeparateResponseVoiceSeparate.class, "VoiceSeparate");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateVoiceSeparateResponse.TemplateVoiceSeparateResponseTemplate fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateVoiceSeparateResponse.TemplateVoiceSeparateResponseTemplate templateVoiceSeparateResponseTemplate = new TemplateVoiceSeparateResponse.TemplateVoiceSeparateResponseTemplate();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateVoiceSeparateResponse.TemplateVoiceSeparateResponseTemplate> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templateVoiceSeparateResponseTemplate, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Template" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templateVoiceSeparateResponseTemplate;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templateVoiceSeparateResponseTemplate;
    }
}
