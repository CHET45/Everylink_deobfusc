package com.tencent.cos.xml.model.p033ci.p034ai;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.p034ai.DetectLabelResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class DetectLabelResponse$DetectLabelResponseLabelsItem$$XmlAdapter extends IXmlAdapter<DetectLabelResponse.DetectLabelResponseLabelsItem> {
    private HashMap<String, ChildElementBinder<DetectLabelResponse.DetectLabelResponseLabelsItem>> childElementBinders;

    public DetectLabelResponse$DetectLabelResponseLabelsItem$$XmlAdapter() {
        HashMap<String, ChildElementBinder<DetectLabelResponse.DetectLabelResponseLabelsItem>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Confidence", new ChildElementBinder<DetectLabelResponse.DetectLabelResponseLabelsItem>() { // from class: com.tencent.cos.xml.model.ci.ai.DetectLabelResponse$DetectLabelResponseLabelsItem$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DetectLabelResponse.DetectLabelResponseLabelsItem detectLabelResponseLabelsItem, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                detectLabelResponseLabelsItem.confidence = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put(Constants.NAME_ELEMENT, new ChildElementBinder<DetectLabelResponse.DetectLabelResponseLabelsItem>() { // from class: com.tencent.cos.xml.model.ci.ai.DetectLabelResponse$DetectLabelResponseLabelsItem$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DetectLabelResponse.DetectLabelResponseLabelsItem detectLabelResponseLabelsItem, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                detectLabelResponseLabelsItem.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("FirstCategory", new ChildElementBinder<DetectLabelResponse.DetectLabelResponseLabelsItem>() { // from class: com.tencent.cos.xml.model.ci.ai.DetectLabelResponse$DetectLabelResponseLabelsItem$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DetectLabelResponse.DetectLabelResponseLabelsItem detectLabelResponseLabelsItem, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                detectLabelResponseLabelsItem.firstCategory = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SecondCategory", new ChildElementBinder<DetectLabelResponse.DetectLabelResponseLabelsItem>() { // from class: com.tencent.cos.xml.model.ci.ai.DetectLabelResponse$DetectLabelResponseLabelsItem$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DetectLabelResponse.DetectLabelResponseLabelsItem detectLabelResponseLabelsItem, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                detectLabelResponseLabelsItem.secondCategory = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public DetectLabelResponse.DetectLabelResponseLabelsItem fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        DetectLabelResponse.DetectLabelResponseLabelsItem detectLabelResponseLabelsItem = new DetectLabelResponse.DetectLabelResponseLabelsItem();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<DetectLabelResponse.DetectLabelResponseLabelsItem> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, detectLabelResponseLabelsItem, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "undefined" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return detectLabelResponseLabelsItem;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return detectLabelResponseLabelsItem;
    }
}
