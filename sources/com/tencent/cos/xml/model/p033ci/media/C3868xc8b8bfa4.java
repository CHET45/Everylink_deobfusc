package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.media.TemplateWatermark;
import com.tencent.cos.xml.model.p033ci.media.TemplateWatermarkResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.TemplateWatermarkResponse$TemplateWatermarkResponseTemplate$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3868xc8b8bfa4 extends IXmlAdapter<TemplateWatermarkResponse.TemplateWatermarkResponseTemplate> {
    private HashMap<String, ChildElementBinder<TemplateWatermarkResponse.TemplateWatermarkResponseTemplate>> childElementBinders;

    public C3868xc8b8bfa4() {
        HashMap<String, ChildElementBinder<TemplateWatermarkResponse.TemplateWatermarkResponseTemplate>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("TemplateId", new ChildElementBinder<TemplateWatermarkResponse.TemplateWatermarkResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateWatermarkResponse$TemplateWatermarkResponseTemplate$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateWatermarkResponse.TemplateWatermarkResponseTemplate templateWatermarkResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateWatermarkResponseTemplate.templateId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.NAME_ELEMENT, new ChildElementBinder<TemplateWatermarkResponse.TemplateWatermarkResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateWatermarkResponse$TemplateWatermarkResponseTemplate$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateWatermarkResponse.TemplateWatermarkResponseTemplate templateWatermarkResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateWatermarkResponseTemplate.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BucketId", new ChildElementBinder<TemplateWatermarkResponse.TemplateWatermarkResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateWatermarkResponse$TemplateWatermarkResponseTemplate$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateWatermarkResponse.TemplateWatermarkResponseTemplate templateWatermarkResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateWatermarkResponseTemplate.bucketId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Category", new ChildElementBinder<TemplateWatermarkResponse.TemplateWatermarkResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateWatermarkResponse$TemplateWatermarkResponseTemplate$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateWatermarkResponse.TemplateWatermarkResponseTemplate templateWatermarkResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateWatermarkResponseTemplate.category = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Tag", new ChildElementBinder<TemplateWatermarkResponse.TemplateWatermarkResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateWatermarkResponse$TemplateWatermarkResponseTemplate$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateWatermarkResponse.TemplateWatermarkResponseTemplate templateWatermarkResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateWatermarkResponseTemplate.tag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("UpdateTime", new ChildElementBinder<TemplateWatermarkResponse.TemplateWatermarkResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateWatermarkResponse$TemplateWatermarkResponseTemplate$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateWatermarkResponse.TemplateWatermarkResponseTemplate templateWatermarkResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateWatermarkResponseTemplate.updateTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreateTime", new ChildElementBinder<TemplateWatermarkResponse.TemplateWatermarkResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateWatermarkResponse$TemplateWatermarkResponseTemplate$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateWatermarkResponse.TemplateWatermarkResponseTemplate templateWatermarkResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateWatermarkResponseTemplate.createTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Watermark", new ChildElementBinder<TemplateWatermarkResponse.TemplateWatermarkResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateWatermarkResponse$TemplateWatermarkResponseTemplate$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateWatermarkResponse.TemplateWatermarkResponseTemplate templateWatermarkResponseTemplate, String str) throws XmlPullParserException, IOException {
                templateWatermarkResponseTemplate.watermark = (TemplateWatermark.Watermark) QCloudXml.fromXml(xmlPullParser, TemplateWatermark.Watermark.class, "Watermark");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateWatermarkResponse.TemplateWatermarkResponseTemplate fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateWatermarkResponse.TemplateWatermarkResponseTemplate templateWatermarkResponseTemplate = new TemplateWatermarkResponse.TemplateWatermarkResponseTemplate();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateWatermarkResponse.TemplateWatermarkResponseTemplate> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templateWatermarkResponseTemplate, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Template" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templateWatermarkResponseTemplate;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templateWatermarkResponseTemplate;
    }
}
