package com.tencent.cos.xml.model.p033ci.common;

import com.microsoft.azure.storage.Constants;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SpeechRecognitionTempleteResponseTemplate$$XmlAdapter extends IXmlAdapter<SpeechRecognitionTempleteResponseTemplate> {
    private HashMap<String, ChildElementBinder<SpeechRecognitionTempleteResponseTemplate>> childElementBinders;

    public SpeechRecognitionTempleteResponseTemplate$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SpeechRecognitionTempleteResponseTemplate>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("TemplateId", new ChildElementBinder<SpeechRecognitionTempleteResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.common.SpeechRecognitionTempleteResponseTemplate$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechRecognitionTempleteResponseTemplate speechRecognitionTempleteResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechRecognitionTempleteResponseTemplate.templateId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.NAME_ELEMENT, new ChildElementBinder<SpeechRecognitionTempleteResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.common.SpeechRecognitionTempleteResponseTemplate$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechRecognitionTempleteResponseTemplate speechRecognitionTempleteResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechRecognitionTempleteResponseTemplate.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BucketId", new ChildElementBinder<SpeechRecognitionTempleteResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.common.SpeechRecognitionTempleteResponseTemplate$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechRecognitionTempleteResponseTemplate speechRecognitionTempleteResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechRecognitionTempleteResponseTemplate.bucketId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Category", new ChildElementBinder<SpeechRecognitionTempleteResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.common.SpeechRecognitionTempleteResponseTemplate$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechRecognitionTempleteResponseTemplate speechRecognitionTempleteResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechRecognitionTempleteResponseTemplate.category = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Tag", new ChildElementBinder<SpeechRecognitionTempleteResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.common.SpeechRecognitionTempleteResponseTemplate$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechRecognitionTempleteResponseTemplate speechRecognitionTempleteResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechRecognitionTempleteResponseTemplate.tag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("UpdateTime", new ChildElementBinder<SpeechRecognitionTempleteResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.common.SpeechRecognitionTempleteResponseTemplate$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechRecognitionTempleteResponseTemplate speechRecognitionTempleteResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechRecognitionTempleteResponseTemplate.updateTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreateTime", new ChildElementBinder<SpeechRecognitionTempleteResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.common.SpeechRecognitionTempleteResponseTemplate$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechRecognitionTempleteResponseTemplate speechRecognitionTempleteResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechRecognitionTempleteResponseTemplate.createTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SpeechRecognition", new ChildElementBinder<SpeechRecognitionTempleteResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.common.SpeechRecognitionTempleteResponseTemplate$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechRecognitionTempleteResponseTemplate speechRecognitionTempleteResponseTemplate, String str) throws XmlPullParserException, IOException {
                speechRecognitionTempleteResponseTemplate.speechRecognition = (SpeechRecognition) QCloudXml.fromXml(xmlPullParser, SpeechRecognition.class, "SpeechRecognition");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SpeechRecognitionTempleteResponseTemplate fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SpeechRecognitionTempleteResponseTemplate speechRecognitionTempleteResponseTemplate = new SpeechRecognitionTempleteResponseTemplate();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SpeechRecognitionTempleteResponseTemplate> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, speechRecognitionTempleteResponseTemplate, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "SpeechRecognitionTempleteResponseTemplate" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return speechRecognitionTempleteResponseTemplate;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return speechRecognitionTempleteResponseTemplate;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SpeechRecognitionTempleteResponseTemplate speechRecognitionTempleteResponseTemplate, String str) throws XmlPullParserException, IOException {
        if (speechRecognitionTempleteResponseTemplate == null) {
            return;
        }
        if (str == null) {
            str = "SpeechRecognitionTempleteResponseTemplate";
        }
        xmlSerializer.startTag("", str);
        if (speechRecognitionTempleteResponseTemplate.templateId != null) {
            xmlSerializer.startTag("", "TemplateId");
            xmlSerializer.text(String.valueOf(speechRecognitionTempleteResponseTemplate.templateId));
            xmlSerializer.endTag("", "TemplateId");
        }
        if (speechRecognitionTempleteResponseTemplate.name != null) {
            xmlSerializer.startTag("", Constants.NAME_ELEMENT);
            xmlSerializer.text(String.valueOf(speechRecognitionTempleteResponseTemplate.name));
            xmlSerializer.endTag("", Constants.NAME_ELEMENT);
        }
        if (speechRecognitionTempleteResponseTemplate.bucketId != null) {
            xmlSerializer.startTag("", "BucketId");
            xmlSerializer.text(String.valueOf(speechRecognitionTempleteResponseTemplate.bucketId));
            xmlSerializer.endTag("", "BucketId");
        }
        if (speechRecognitionTempleteResponseTemplate.category != null) {
            xmlSerializer.startTag("", "Category");
            xmlSerializer.text(String.valueOf(speechRecognitionTempleteResponseTemplate.category));
            xmlSerializer.endTag("", "Category");
        }
        if (speechRecognitionTempleteResponseTemplate.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(speechRecognitionTempleteResponseTemplate.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (speechRecognitionTempleteResponseTemplate.updateTime != null) {
            xmlSerializer.startTag("", "UpdateTime");
            xmlSerializer.text(String.valueOf(speechRecognitionTempleteResponseTemplate.updateTime));
            xmlSerializer.endTag("", "UpdateTime");
        }
        if (speechRecognitionTempleteResponseTemplate.createTime != null) {
            xmlSerializer.startTag("", "CreateTime");
            xmlSerializer.text(String.valueOf(speechRecognitionTempleteResponseTemplate.createTime));
            xmlSerializer.endTag("", "CreateTime");
        }
        if (speechRecognitionTempleteResponseTemplate.speechRecognition != null) {
            QCloudXml.toXml(xmlSerializer, speechRecognitionTempleteResponseTemplate.speechRecognition, "SpeechRecognition");
        }
        xmlSerializer.endTag("", str);
    }
}
