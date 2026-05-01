package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class AssessQualityResponse$$XmlAdapter extends IXmlAdapter<AssessQualityResponse> {
    private HashMap<String, ChildElementBinder<AssessQualityResponse>> childElementBinders;

    public AssessQualityResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<AssessQualityResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("LongImage", new ChildElementBinder<AssessQualityResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.AssessQualityResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AssessQualityResponse assessQualityResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                assessQualityResponse.longImage = Boolean.parseBoolean(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("BlackAndWhite", new ChildElementBinder<AssessQualityResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.AssessQualityResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AssessQualityResponse assessQualityResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                assessQualityResponse.blackAndWhite = Boolean.parseBoolean(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("SmallImage", new ChildElementBinder<AssessQualityResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.AssessQualityResponse$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AssessQualityResponse assessQualityResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                assessQualityResponse.smallImage = Boolean.parseBoolean(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("BigImage", new ChildElementBinder<AssessQualityResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.AssessQualityResponse$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AssessQualityResponse assessQualityResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                assessQualityResponse.bigImage = Boolean.parseBoolean(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("PureImage", new ChildElementBinder<AssessQualityResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.AssessQualityResponse$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AssessQualityResponse assessQualityResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                assessQualityResponse.pureImage = Boolean.parseBoolean(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("ClarityScore", new ChildElementBinder<AssessQualityResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.AssessQualityResponse$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AssessQualityResponse assessQualityResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                assessQualityResponse.clarityScore = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("AestheticScore", new ChildElementBinder<AssessQualityResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.AssessQualityResponse$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AssessQualityResponse assessQualityResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                assessQualityResponse.aestheticScore = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("LowQualityScore", new ChildElementBinder<AssessQualityResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.AssessQualityResponse$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AssessQualityResponse assessQualityResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                assessQualityResponse.lowQualityScore = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("RequestId", new ChildElementBinder<AssessQualityResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.AssessQualityResponse$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AssessQualityResponse assessQualityResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                assessQualityResponse.requestId = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public AssessQualityResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        AssessQualityResponse assessQualityResponse = new AssessQualityResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<AssessQualityResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, assessQualityResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return assessQualityResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return assessQualityResponse;
    }
}
