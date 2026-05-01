package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.media.TemplateTranscodeResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.TemplateTranscodeResponse$TemplateTranscodeResponseTemplate$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3810xad215c6 extends IXmlAdapter<TemplateTranscodeResponse.TemplateTranscodeResponseTemplate> {
    private HashMap<String, ChildElementBinder<TemplateTranscodeResponse.TemplateTranscodeResponseTemplate>> childElementBinders;

    public C3810xad215c6() {
        HashMap<String, ChildElementBinder<TemplateTranscodeResponse.TemplateTranscodeResponseTemplate>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("TemplateId", new ChildElementBinder<TemplateTranscodeResponse.TemplateTranscodeResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscodeResponse$TemplateTranscodeResponseTemplate$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscodeResponse.TemplateTranscodeResponseTemplate templateTranscodeResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeResponseTemplate.templateId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.NAME_ELEMENT, new ChildElementBinder<TemplateTranscodeResponse.TemplateTranscodeResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscodeResponse$TemplateTranscodeResponseTemplate$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscodeResponse.TemplateTranscodeResponseTemplate templateTranscodeResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeResponseTemplate.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BucketId", new ChildElementBinder<TemplateTranscodeResponse.TemplateTranscodeResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscodeResponse$TemplateTranscodeResponseTemplate$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscodeResponse.TemplateTranscodeResponseTemplate templateTranscodeResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeResponseTemplate.bucketId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Category", new ChildElementBinder<TemplateTranscodeResponse.TemplateTranscodeResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscodeResponse$TemplateTranscodeResponseTemplate$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscodeResponse.TemplateTranscodeResponseTemplate templateTranscodeResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeResponseTemplate.category = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Tag", new ChildElementBinder<TemplateTranscodeResponse.TemplateTranscodeResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscodeResponse$TemplateTranscodeResponseTemplate$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscodeResponse.TemplateTranscodeResponseTemplate templateTranscodeResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeResponseTemplate.tag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("UpdateTime", new ChildElementBinder<TemplateTranscodeResponse.TemplateTranscodeResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscodeResponse$TemplateTranscodeResponseTemplate$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscodeResponse.TemplateTranscodeResponseTemplate templateTranscodeResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeResponseTemplate.updateTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreateTime", new ChildElementBinder<TemplateTranscodeResponse.TemplateTranscodeResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscodeResponse$TemplateTranscodeResponseTemplate$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscodeResponse.TemplateTranscodeResponseTemplate templateTranscodeResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeResponseTemplate.createTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("TransTpl", new ChildElementBinder<TemplateTranscodeResponse.TemplateTranscodeResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscodeResponse$TemplateTranscodeResponseTemplate$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscodeResponse.TemplateTranscodeResponseTemplate templateTranscodeResponseTemplate, String str) throws XmlPullParserException, IOException {
                templateTranscodeResponseTemplate.transTpl = (TemplateTranscodeResponse.TemplateTranscodeResponseTransTpl) QCloudXml.fromXml(xmlPullParser, TemplateTranscodeResponse.TemplateTranscodeResponseTransTpl.class, "TransTpl");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateTranscodeResponse.TemplateTranscodeResponseTemplate fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateTranscodeResponse.TemplateTranscodeResponseTemplate templateTranscodeResponseTemplate = new TemplateTranscodeResponse.TemplateTranscodeResponseTemplate();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateTranscodeResponse.TemplateTranscodeResponseTemplate> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templateTranscodeResponseTemplate, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Template" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templateTranscodeResponseTemplate;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templateTranscodeResponseTemplate;
    }
}
