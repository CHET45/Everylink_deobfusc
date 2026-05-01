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
public class AIDetectCarResponse$AIDetectCarResponsePlateContent$$XmlAdapter extends IXmlAdapter<AIDetectCarResponse.AIDetectCarResponsePlateContent> {
    private HashMap<String, ChildElementBinder<AIDetectCarResponse.AIDetectCarResponsePlateContent>> childElementBinders;

    public AIDetectCarResponse$AIDetectCarResponsePlateContent$$XmlAdapter() {
        HashMap<String, ChildElementBinder<AIDetectCarResponse.AIDetectCarResponsePlateContent>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Plate", new ChildElementBinder<AIDetectCarResponse.AIDetectCarResponsePlateContent>() { // from class: com.tencent.cos.xml.model.ci.ai.AIDetectCarResponse$AIDetectCarResponsePlateContent$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIDetectCarResponse.AIDetectCarResponsePlateContent aIDetectCarResponsePlateContent, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIDetectCarResponsePlateContent.plate = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Color", new ChildElementBinder<AIDetectCarResponse.AIDetectCarResponsePlateContent>() { // from class: com.tencent.cos.xml.model.ci.ai.AIDetectCarResponse$AIDetectCarResponsePlateContent$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIDetectCarResponse.AIDetectCarResponsePlateContent aIDetectCarResponsePlateContent, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIDetectCarResponsePlateContent.color = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Type", new ChildElementBinder<AIDetectCarResponse.AIDetectCarResponsePlateContent>() { // from class: com.tencent.cos.xml.model.ci.ai.AIDetectCarResponse$AIDetectCarResponsePlateContent$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIDetectCarResponse.AIDetectCarResponsePlateContent aIDetectCarResponsePlateContent, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIDetectCarResponsePlateContent.type = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("PlateLocation", new ChildElementBinder<AIDetectCarResponse.AIDetectCarResponsePlateContent>() { // from class: com.tencent.cos.xml.model.ci.ai.AIDetectCarResponse$AIDetectCarResponsePlateContent$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIDetectCarResponse.AIDetectCarResponsePlateContent aIDetectCarResponsePlateContent, String str) throws XmlPullParserException, IOException {
                if (aIDetectCarResponsePlateContent.plateLocation == null) {
                    aIDetectCarResponsePlateContent.plateLocation = new ArrayList();
                }
                aIDetectCarResponsePlateContent.plateLocation.add((AIDetectCarResponse.AIDetectCarResponsePlateLocation) QCloudXml.fromXml(xmlPullParser, AIDetectCarResponse.AIDetectCarResponsePlateLocation.class, "PlateLocation"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public AIDetectCarResponse.AIDetectCarResponsePlateContent fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        AIDetectCarResponse.AIDetectCarResponsePlateContent aIDetectCarResponsePlateContent = new AIDetectCarResponse.AIDetectCarResponsePlateContent();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<AIDetectCarResponse.AIDetectCarResponsePlateContent> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, aIDetectCarResponsePlateContent, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "PlateContent" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return aIDetectCarResponsePlateContent;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return aIDetectCarResponsePlateContent;
    }
}
