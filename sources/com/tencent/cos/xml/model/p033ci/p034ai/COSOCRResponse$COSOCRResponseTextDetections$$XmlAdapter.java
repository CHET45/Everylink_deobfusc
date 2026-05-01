package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.COSOCRResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class COSOCRResponse$COSOCRResponseTextDetections$$XmlAdapter extends IXmlAdapter<COSOCRResponse.COSOCRResponseTextDetections> {
    private HashMap<String, ChildElementBinder<COSOCRResponse.COSOCRResponseTextDetections>> childElementBinders;

    public COSOCRResponse$COSOCRResponseTextDetections$$XmlAdapter() {
        HashMap<String, ChildElementBinder<COSOCRResponse.COSOCRResponseTextDetections>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("DetectedText", new ChildElementBinder<COSOCRResponse.COSOCRResponseTextDetections>() { // from class: com.tencent.cos.xml.model.ci.ai.COSOCRResponse$COSOCRResponseTextDetections$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, COSOCRResponse.COSOCRResponseTextDetections cOSOCRResponseTextDetections, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                cOSOCRResponseTextDetections.detectedText = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Confidence", new ChildElementBinder<COSOCRResponse.COSOCRResponseTextDetections>() { // from class: com.tencent.cos.xml.model.ci.ai.COSOCRResponse$COSOCRResponseTextDetections$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, COSOCRResponse.COSOCRResponseTextDetections cOSOCRResponseTextDetections, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                cOSOCRResponseTextDetections.confidence = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Polygon", new ChildElementBinder<COSOCRResponse.COSOCRResponseTextDetections>() { // from class: com.tencent.cos.xml.model.ci.ai.COSOCRResponse$COSOCRResponseTextDetections$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, COSOCRResponse.COSOCRResponseTextDetections cOSOCRResponseTextDetections, String str) throws XmlPullParserException, IOException {
                if (cOSOCRResponseTextDetections.polygon == null) {
                    cOSOCRResponseTextDetections.polygon = new ArrayList();
                }
                cOSOCRResponseTextDetections.polygon.add((COSOCRResponse.COSOCRResponsePolygon) QCloudXml.fromXml(xmlPullParser, COSOCRResponse.COSOCRResponsePolygon.class, "Polygon"));
            }
        });
        this.childElementBinders.put("ItemPolygon", new ChildElementBinder<COSOCRResponse.COSOCRResponseTextDetections>() { // from class: com.tencent.cos.xml.model.ci.ai.COSOCRResponse$COSOCRResponseTextDetections$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, COSOCRResponse.COSOCRResponseTextDetections cOSOCRResponseTextDetections, String str) throws XmlPullParserException, IOException {
                if (cOSOCRResponseTextDetections.itemPolygon == null) {
                    cOSOCRResponseTextDetections.itemPolygon = new ArrayList();
                }
                cOSOCRResponseTextDetections.itemPolygon.add((COSOCRResponse.COSOCRResponseItemPolygon) QCloudXml.fromXml(xmlPullParser, COSOCRResponse.COSOCRResponseItemPolygon.class, "ItemPolygon"));
            }
        });
        this.childElementBinders.put("Words", new ChildElementBinder<COSOCRResponse.COSOCRResponseTextDetections>() { // from class: com.tencent.cos.xml.model.ci.ai.COSOCRResponse$COSOCRResponseTextDetections$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, COSOCRResponse.COSOCRResponseTextDetections cOSOCRResponseTextDetections, String str) throws XmlPullParserException, IOException {
                if (cOSOCRResponseTextDetections.words == null) {
                    cOSOCRResponseTextDetections.words = new ArrayList();
                }
                cOSOCRResponseTextDetections.words.add((COSOCRResponse.COSOCRResponseWords) QCloudXml.fromXml(xmlPullParser, COSOCRResponse.COSOCRResponseWords.class, "Words"));
            }
        });
        this.childElementBinders.put("WordPolygon", new ChildElementBinder<COSOCRResponse.COSOCRResponseTextDetections>() { // from class: com.tencent.cos.xml.model.ci.ai.COSOCRResponse$COSOCRResponseTextDetections$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, COSOCRResponse.COSOCRResponseTextDetections cOSOCRResponseTextDetections, String str) throws XmlPullParserException, IOException {
                if (cOSOCRResponseTextDetections.wordPolygon == null) {
                    cOSOCRResponseTextDetections.wordPolygon = new ArrayList();
                }
                cOSOCRResponseTextDetections.wordPolygon.add((COSOCRResponse.COSOCRResponseWordPolygon) QCloudXml.fromXml(xmlPullParser, COSOCRResponse.COSOCRResponseWordPolygon.class, "WordPolygon"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public COSOCRResponse.COSOCRResponseTextDetections fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        COSOCRResponse.COSOCRResponseTextDetections cOSOCRResponseTextDetections = new COSOCRResponse.COSOCRResponseTextDetections();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<COSOCRResponse.COSOCRResponseTextDetections> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, cOSOCRResponseTextDetections, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "TextDetections" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return cOSOCRResponseTextDetections;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return cOSOCRResponseTextDetections;
    }
}
