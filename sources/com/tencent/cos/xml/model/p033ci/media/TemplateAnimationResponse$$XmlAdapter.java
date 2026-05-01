package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.media.TemplateAnimationResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateAnimationResponse$$XmlAdapter extends IXmlAdapter<TemplateAnimationResponse> {
    private HashMap<String, ChildElementBinder<TemplateAnimationResponse>> childElementBinders;

    public TemplateAnimationResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TemplateAnimationResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("TemplateId", new ChildElementBinder<TemplateAnimationResponse>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateAnimationResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateAnimationResponse templateAnimationResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateAnimationResponse.templateId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.NAME_ELEMENT, new ChildElementBinder<TemplateAnimationResponse>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateAnimationResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateAnimationResponse templateAnimationResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateAnimationResponse.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BucketId", new ChildElementBinder<TemplateAnimationResponse>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateAnimationResponse$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateAnimationResponse templateAnimationResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateAnimationResponse.bucketId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Category", new ChildElementBinder<TemplateAnimationResponse>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateAnimationResponse$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateAnimationResponse templateAnimationResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateAnimationResponse.category = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Tag", new ChildElementBinder<TemplateAnimationResponse>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateAnimationResponse$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateAnimationResponse templateAnimationResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateAnimationResponse.tag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("UpdateTime", new ChildElementBinder<TemplateAnimationResponse>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateAnimationResponse$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateAnimationResponse templateAnimationResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateAnimationResponse.updateTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreateTime", new ChildElementBinder<TemplateAnimationResponse>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateAnimationResponse$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateAnimationResponse templateAnimationResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateAnimationResponse.createTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("TransTpl", new ChildElementBinder<TemplateAnimationResponse>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateAnimationResponse$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateAnimationResponse templateAnimationResponse, String str) throws XmlPullParserException, IOException {
                templateAnimationResponse.transTpl = (TemplateAnimationResponse.TemplateAnimationResponseTransTpl) QCloudXml.fromXml(xmlPullParser, TemplateAnimationResponse.TemplateAnimationResponseTransTpl.class, "TransTpl");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateAnimationResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateAnimationResponse templateAnimationResponse = new TemplateAnimationResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateAnimationResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templateAnimationResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templateAnimationResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templateAnimationResponse;
    }
}
