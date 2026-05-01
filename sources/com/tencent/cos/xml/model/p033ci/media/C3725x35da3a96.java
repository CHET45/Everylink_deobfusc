package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.common.PicProcess;
import com.tencent.cos.xml.model.p033ci.media.TemplatePicProcessResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.TemplatePicProcessResponse$TemplatePicProcessResponseTemplate$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3725x35da3a96 extends IXmlAdapter<TemplatePicProcessResponse.TemplatePicProcessResponseTemplate> {
    private HashMap<String, ChildElementBinder<TemplatePicProcessResponse.TemplatePicProcessResponseTemplate>> childElementBinders;

    public C3725x35da3a96() {
        HashMap<String, ChildElementBinder<TemplatePicProcessResponse.TemplatePicProcessResponseTemplate>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("TemplateId", new ChildElementBinder<TemplatePicProcessResponse.TemplatePicProcessResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplatePicProcessResponse$TemplatePicProcessResponseTemplate$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplatePicProcessResponse.TemplatePicProcessResponseTemplate templatePicProcessResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templatePicProcessResponseTemplate.templateId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.NAME_ELEMENT, new ChildElementBinder<TemplatePicProcessResponse.TemplatePicProcessResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplatePicProcessResponse$TemplatePicProcessResponseTemplate$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplatePicProcessResponse.TemplatePicProcessResponseTemplate templatePicProcessResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templatePicProcessResponseTemplate.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BucketId", new ChildElementBinder<TemplatePicProcessResponse.TemplatePicProcessResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplatePicProcessResponse$TemplatePicProcessResponseTemplate$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplatePicProcessResponse.TemplatePicProcessResponseTemplate templatePicProcessResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templatePicProcessResponseTemplate.bucketId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Category", new ChildElementBinder<TemplatePicProcessResponse.TemplatePicProcessResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplatePicProcessResponse$TemplatePicProcessResponseTemplate$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplatePicProcessResponse.TemplatePicProcessResponseTemplate templatePicProcessResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templatePicProcessResponseTemplate.category = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Tag", new ChildElementBinder<TemplatePicProcessResponse.TemplatePicProcessResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplatePicProcessResponse$TemplatePicProcessResponseTemplate$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplatePicProcessResponse.TemplatePicProcessResponseTemplate templatePicProcessResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templatePicProcessResponseTemplate.tag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("UpdateTime", new ChildElementBinder<TemplatePicProcessResponse.TemplatePicProcessResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplatePicProcessResponse$TemplatePicProcessResponseTemplate$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplatePicProcessResponse.TemplatePicProcessResponseTemplate templatePicProcessResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templatePicProcessResponseTemplate.updateTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreateTime", new ChildElementBinder<TemplatePicProcessResponse.TemplatePicProcessResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplatePicProcessResponse$TemplatePicProcessResponseTemplate$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplatePicProcessResponse.TemplatePicProcessResponseTemplate templatePicProcessResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templatePicProcessResponseTemplate.createTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("PicProcess", new ChildElementBinder<TemplatePicProcessResponse.TemplatePicProcessResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplatePicProcessResponse$TemplatePicProcessResponseTemplate$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplatePicProcessResponse.TemplatePicProcessResponseTemplate templatePicProcessResponseTemplate, String str) throws XmlPullParserException, IOException {
                templatePicProcessResponseTemplate.picProcess = (PicProcess) QCloudXml.fromXml(xmlPullParser, PicProcess.class, "PicProcess");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplatePicProcessResponse.TemplatePicProcessResponseTemplate fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplatePicProcessResponse.TemplatePicProcessResponseTemplate templatePicProcessResponseTemplate = new TemplatePicProcessResponse.TemplatePicProcessResponseTemplate();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplatePicProcessResponse.TemplatePicProcessResponseTemplate> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templatePicProcessResponseTemplate, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Template" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templatePicProcessResponseTemplate;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templatePicProcessResponseTemplate;
    }
}
