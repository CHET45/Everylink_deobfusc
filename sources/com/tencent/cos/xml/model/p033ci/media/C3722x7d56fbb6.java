package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.media.TemplateConcat;
import com.tencent.cos.xml.model.p033ci.media.TemplateConcatResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.TemplateConcatResponse$TemplateConcatResponseTemplate$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3722x7d56fbb6 extends IXmlAdapter<TemplateConcatResponse.TemplateConcatResponseTemplate> {
    private HashMap<String, ChildElementBinder<TemplateConcatResponse.TemplateConcatResponseTemplate>> childElementBinders;

    public C3722x7d56fbb6() {
        HashMap<String, ChildElementBinder<TemplateConcatResponse.TemplateConcatResponseTemplate>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("TemplateId", new ChildElementBinder<TemplateConcatResponse.TemplateConcatResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateConcatResponse$TemplateConcatResponseTemplate$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateConcatResponse.TemplateConcatResponseTemplate templateConcatResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateConcatResponseTemplate.templateId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.NAME_ELEMENT, new ChildElementBinder<TemplateConcatResponse.TemplateConcatResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateConcatResponse$TemplateConcatResponseTemplate$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateConcatResponse.TemplateConcatResponseTemplate templateConcatResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateConcatResponseTemplate.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BucketId", new ChildElementBinder<TemplateConcatResponse.TemplateConcatResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateConcatResponse$TemplateConcatResponseTemplate$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateConcatResponse.TemplateConcatResponseTemplate templateConcatResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateConcatResponseTemplate.bucketId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Category", new ChildElementBinder<TemplateConcatResponse.TemplateConcatResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateConcatResponse$TemplateConcatResponseTemplate$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateConcatResponse.TemplateConcatResponseTemplate templateConcatResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateConcatResponseTemplate.category = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Tag", new ChildElementBinder<TemplateConcatResponse.TemplateConcatResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateConcatResponse$TemplateConcatResponseTemplate$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateConcatResponse.TemplateConcatResponseTemplate templateConcatResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateConcatResponseTemplate.tag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("UpdateTime", new ChildElementBinder<TemplateConcatResponse.TemplateConcatResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateConcatResponse$TemplateConcatResponseTemplate$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateConcatResponse.TemplateConcatResponseTemplate templateConcatResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateConcatResponseTemplate.updateTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreateTime", new ChildElementBinder<TemplateConcatResponse.TemplateConcatResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateConcatResponse$TemplateConcatResponseTemplate$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateConcatResponse.TemplateConcatResponseTemplate templateConcatResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateConcatResponseTemplate.createTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("ConcatTemplate", new ChildElementBinder<TemplateConcatResponse.TemplateConcatResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateConcatResponse$TemplateConcatResponseTemplate$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateConcatResponse.TemplateConcatResponseTemplate templateConcatResponseTemplate, String str) throws XmlPullParserException, IOException {
                templateConcatResponseTemplate.concatTemplate = (TemplateConcat.TemplateConcatConcatTemplate) QCloudXml.fromXml(xmlPullParser, TemplateConcat.TemplateConcatConcatTemplate.class, "ConcatTemplate");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateConcatResponse.TemplateConcatResponseTemplate fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateConcatResponse.TemplateConcatResponseTemplate templateConcatResponseTemplate = new TemplateConcatResponse.TemplateConcatResponseTemplate();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateConcatResponse.TemplateConcatResponseTemplate> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templateConcatResponseTemplate, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Template" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templateConcatResponseTemplate;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templateConcatResponseTemplate;
    }
}
