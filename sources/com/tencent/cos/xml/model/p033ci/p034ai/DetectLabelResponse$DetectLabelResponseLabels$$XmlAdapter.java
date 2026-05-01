package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.DetectLabelResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class DetectLabelResponse$DetectLabelResponseLabels$$XmlAdapter extends IXmlAdapter<DetectLabelResponse.DetectLabelResponseLabels> {
    private HashMap<String, ChildElementBinder<DetectLabelResponse.DetectLabelResponseLabels>> childElementBinders;

    public DetectLabelResponse$DetectLabelResponseLabels$$XmlAdapter() {
        HashMap<String, ChildElementBinder<DetectLabelResponse.DetectLabelResponseLabels>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Labels", new ChildElementBinder<DetectLabelResponse.DetectLabelResponseLabels>() { // from class: com.tencent.cos.xml.model.ci.ai.DetectLabelResponse$DetectLabelResponseLabels$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DetectLabelResponse.DetectLabelResponseLabels detectLabelResponseLabels, String str) throws XmlPullParserException, IOException {
                if (detectLabelResponseLabels.labels == null) {
                    detectLabelResponseLabels.labels = new ArrayList();
                }
                detectLabelResponseLabels.labels.add((DetectLabelResponse.DetectLabelResponseLabelsItem) QCloudXml.fromXml(xmlPullParser, DetectLabelResponse.DetectLabelResponseLabelsItem.class, "Labels"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public DetectLabelResponse.DetectLabelResponseLabels fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        DetectLabelResponse.DetectLabelResponseLabels detectLabelResponseLabels = new DetectLabelResponse.DetectLabelResponseLabels();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<DetectLabelResponse.DetectLabelResponseLabels> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, detectLabelResponseLabels, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Labels" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return detectLabelResponseLabels;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return detectLabelResponseLabels;
    }
}
