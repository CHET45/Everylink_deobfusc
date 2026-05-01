package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.media.TemplateSmartCover;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateSmartCoverResponse$$XmlAdapter extends IXmlAdapter<TemplateSmartCoverResponse> {
    private HashMap<String, ChildElementBinder<TemplateSmartCoverResponse>> childElementBinders;

    public TemplateSmartCoverResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TemplateSmartCoverResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Tag", new ChildElementBinder<TemplateSmartCoverResponse>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSmartCoverResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSmartCoverResponse templateSmartCoverResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSmartCoverResponse.tag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.NAME_ELEMENT, new ChildElementBinder<TemplateSmartCoverResponse>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSmartCoverResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSmartCoverResponse templateSmartCoverResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSmartCoverResponse.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BucketId", new ChildElementBinder<TemplateSmartCoverResponse>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSmartCoverResponse$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSmartCoverResponse templateSmartCoverResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSmartCoverResponse.bucketId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Category", new ChildElementBinder<TemplateSmartCoverResponse>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSmartCoverResponse$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSmartCoverResponse templateSmartCoverResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSmartCoverResponse.category = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("TemplateId", new ChildElementBinder<TemplateSmartCoverResponse>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSmartCoverResponse$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSmartCoverResponse templateSmartCoverResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSmartCoverResponse.templateId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("UpdateTime", new ChildElementBinder<TemplateSmartCoverResponse>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSmartCoverResponse$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSmartCoverResponse templateSmartCoverResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSmartCoverResponse.updateTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreateTime", new ChildElementBinder<TemplateSmartCoverResponse>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSmartCoverResponse$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSmartCoverResponse templateSmartCoverResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSmartCoverResponse.createTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SmartCover", new ChildElementBinder<TemplateSmartCoverResponse>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSmartCoverResponse$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSmartCoverResponse templateSmartCoverResponse, String str) throws XmlPullParserException, IOException {
                templateSmartCoverResponse.smartCover = (TemplateSmartCover.TemplateSmartCoverSmartCover) QCloudXml.fromXml(xmlPullParser, TemplateSmartCover.TemplateSmartCoverSmartCover.class, "SmartCover");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateSmartCoverResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateSmartCoverResponse templateSmartCoverResponse = new TemplateSmartCoverResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateSmartCoverResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templateSmartCoverResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templateSmartCoverResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templateSmartCoverResponse;
    }
}
