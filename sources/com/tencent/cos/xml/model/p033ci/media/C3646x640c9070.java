package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.media.SubmitVideoTagJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitVideoTagJobResponse$SubmitVideoTagJobResponsePersonTags$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3646x640c9070 extends IXmlAdapter<SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePersonTags> {
    private HashMap<String, ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePersonTags>> childElementBinders;

    public C3646x640c9070() {
        HashMap<String, ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePersonTags>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.NAME_ELEMENT, new ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePersonTags>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoTagJobResponse$SubmitVideoTagJobResponsePersonTags$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePersonTags submitVideoTagJobResponsePersonTags, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVideoTagJobResponsePersonTags.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Confidence", new ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePersonTags>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoTagJobResponse$SubmitVideoTagJobResponsePersonTags$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePersonTags submitVideoTagJobResponsePersonTags, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVideoTagJobResponsePersonTags.confidence = Float.parseFloat(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Count", new ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePersonTags>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoTagJobResponse$SubmitVideoTagJobResponsePersonTags$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePersonTags submitVideoTagJobResponsePersonTags, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVideoTagJobResponsePersonTags.count = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("DetailPerSecond", new ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePersonTags>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoTagJobResponse$SubmitVideoTagJobResponsePersonTags$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePersonTags submitVideoTagJobResponsePersonTags, String str) throws XmlPullParserException, IOException {
                if (submitVideoTagJobResponsePersonTags.detailPerSecond == null) {
                    submitVideoTagJobResponsePersonTags.detailPerSecond = new ArrayList();
                }
                submitVideoTagJobResponsePersonTags.detailPerSecond.add((SubmitVideoTagJobResponse.SubmitVideoTagJobResponseDetailPerSecond) QCloudXml.fromXml(xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponseDetailPerSecond.class, "DetailPerSecond"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePersonTags fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePersonTags submitVideoTagJobResponsePersonTags = new SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePersonTags();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePersonTags> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitVideoTagJobResponsePersonTags, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "PersonTags" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitVideoTagJobResponsePersonTags;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitVideoTagJobResponsePersonTags;
    }
}
