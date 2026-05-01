package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.AIDetectCarResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class AIDetectCarResponse$AIDetectCarResponseCarTags$$XmlAdapter extends IXmlAdapter<AIDetectCarResponse.AIDetectCarResponseCarTags> {
    private HashMap<String, ChildElementBinder<AIDetectCarResponse.AIDetectCarResponseCarTags>> childElementBinders;

    public AIDetectCarResponse$AIDetectCarResponseCarTags$$XmlAdapter() {
        HashMap<String, ChildElementBinder<AIDetectCarResponse.AIDetectCarResponseCarTags>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Serial", new ChildElementBinder<AIDetectCarResponse.AIDetectCarResponseCarTags>() { // from class: com.tencent.cos.xml.model.ci.ai.AIDetectCarResponse$AIDetectCarResponseCarTags$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIDetectCarResponse.AIDetectCarResponseCarTags aIDetectCarResponseCarTags, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIDetectCarResponseCarTags.serial = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Brand", new ChildElementBinder<AIDetectCarResponse.AIDetectCarResponseCarTags>() { // from class: com.tencent.cos.xml.model.ci.ai.AIDetectCarResponse$AIDetectCarResponseCarTags$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIDetectCarResponse.AIDetectCarResponseCarTags aIDetectCarResponseCarTags, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIDetectCarResponseCarTags.brand = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Type", new ChildElementBinder<AIDetectCarResponse.AIDetectCarResponseCarTags>() { // from class: com.tencent.cos.xml.model.ci.ai.AIDetectCarResponse$AIDetectCarResponseCarTags$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIDetectCarResponse.AIDetectCarResponseCarTags aIDetectCarResponseCarTags, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIDetectCarResponseCarTags.type = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Color", new ChildElementBinder<AIDetectCarResponse.AIDetectCarResponseCarTags>() { // from class: com.tencent.cos.xml.model.ci.ai.AIDetectCarResponse$AIDetectCarResponseCarTags$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIDetectCarResponse.AIDetectCarResponseCarTags aIDetectCarResponseCarTags, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIDetectCarResponseCarTags.color = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Confidence", new ChildElementBinder<AIDetectCarResponse.AIDetectCarResponseCarTags>() { // from class: com.tencent.cos.xml.model.ci.ai.AIDetectCarResponse$AIDetectCarResponseCarTags$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIDetectCarResponse.AIDetectCarResponseCarTags aIDetectCarResponseCarTags, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIDetectCarResponseCarTags.confidence = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Year", new ChildElementBinder<AIDetectCarResponse.AIDetectCarResponseCarTags>() { // from class: com.tencent.cos.xml.model.ci.ai.AIDetectCarResponse$AIDetectCarResponseCarTags$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIDetectCarResponse.AIDetectCarResponseCarTags aIDetectCarResponseCarTags, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIDetectCarResponseCarTags.year = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("CarLocation", new ChildElementBinder<AIDetectCarResponse.AIDetectCarResponseCarTags>() { // from class: com.tencent.cos.xml.model.ci.ai.AIDetectCarResponse$AIDetectCarResponseCarTags$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIDetectCarResponse.AIDetectCarResponseCarTags aIDetectCarResponseCarTags, String str) throws XmlPullParserException, IOException {
                if (aIDetectCarResponseCarTags.carLocation == null) {
                    aIDetectCarResponseCarTags.carLocation = new ArrayList();
                }
                aIDetectCarResponseCarTags.carLocation.add((AIDetectCarResponse.AIDetectCarResponseCarLocation) QCloudXml.fromXml(xmlPullParser, AIDetectCarResponse.AIDetectCarResponseCarLocation.class, "CarLocation"));
            }
        });
        this.childElementBinders.put("PlateContent", new ChildElementBinder<AIDetectCarResponse.AIDetectCarResponseCarTags>() { // from class: com.tencent.cos.xml.model.ci.ai.AIDetectCarResponse$AIDetectCarResponseCarTags$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIDetectCarResponse.AIDetectCarResponseCarTags aIDetectCarResponseCarTags, String str) throws XmlPullParserException, IOException {
                if (aIDetectCarResponseCarTags.plateContent == null) {
                    aIDetectCarResponseCarTags.plateContent = new ArrayList();
                }
                aIDetectCarResponseCarTags.plateContent.add((AIDetectCarResponse.AIDetectCarResponsePlateContent) QCloudXml.fromXml(xmlPullParser, AIDetectCarResponse.AIDetectCarResponsePlateContent.class, "PlateContent"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public AIDetectCarResponse.AIDetectCarResponseCarTags fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        AIDetectCarResponse.AIDetectCarResponseCarTags aIDetectCarResponseCarTags = new AIDetectCarResponse.AIDetectCarResponseCarTags();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<AIDetectCarResponse.AIDetectCarResponseCarTags> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, aIDetectCarResponseCarTags, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "CarTags" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return aIDetectCarResponseCarTags;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return aIDetectCarResponseCarTags;
    }
}
