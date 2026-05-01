package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.media.TemplateVideoMontageResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateVideoMontageResponse$$XmlAdapter extends IXmlAdapter<TemplateVideoMontageResponse> {
    private HashMap<String, ChildElementBinder<TemplateVideoMontageResponse>> childElementBinders;

    public TemplateVideoMontageResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TemplateVideoMontageResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("TemplateId", new ChildElementBinder<TemplateVideoMontageResponse>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVideoMontageResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVideoMontageResponse templateVideoMontageResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateVideoMontageResponse.templateId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.NAME_ELEMENT, new ChildElementBinder<TemplateVideoMontageResponse>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVideoMontageResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVideoMontageResponse templateVideoMontageResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateVideoMontageResponse.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BucketId", new ChildElementBinder<TemplateVideoMontageResponse>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVideoMontageResponse$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVideoMontageResponse templateVideoMontageResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateVideoMontageResponse.bucketId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Category", new ChildElementBinder<TemplateVideoMontageResponse>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVideoMontageResponse$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVideoMontageResponse templateVideoMontageResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateVideoMontageResponse.category = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Tag", new ChildElementBinder<TemplateVideoMontageResponse>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVideoMontageResponse$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVideoMontageResponse templateVideoMontageResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateVideoMontageResponse.tag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("UpdateTime", new ChildElementBinder<TemplateVideoMontageResponse>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVideoMontageResponse$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVideoMontageResponse templateVideoMontageResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateVideoMontageResponse.updateTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreateTime", new ChildElementBinder<TemplateVideoMontageResponse>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVideoMontageResponse$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVideoMontageResponse templateVideoMontageResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateVideoMontageResponse.createTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("VideoMontage", new ChildElementBinder<TemplateVideoMontageResponse>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVideoMontageResponse$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVideoMontageResponse templateVideoMontageResponse, String str) throws XmlPullParserException, IOException {
                templateVideoMontageResponse.videoMontage = (TemplateVideoMontageResponse.TemplateVideoMontageResponseVideoMontage) QCloudXml.fromXml(xmlPullParser, TemplateVideoMontageResponse.TemplateVideoMontageResponseVideoMontage.class, "VideoMontage");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateVideoMontageResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateVideoMontageResponse templateVideoMontageResponse = new TemplateVideoMontageResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateVideoMontageResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templateVideoMontageResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templateVideoMontageResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templateVideoMontageResponse;
    }
}
