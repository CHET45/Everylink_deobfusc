package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.DetectLabelResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class DetectLabelResponse$$XmlAdapter extends IXmlAdapter<DetectLabelResponse> {
    private HashMap<String, ChildElementBinder<DetectLabelResponse>> childElementBinders;

    public DetectLabelResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<DetectLabelResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("WebLabels", new ChildElementBinder<DetectLabelResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.DetectLabelResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DetectLabelResponse detectLabelResponse, String str) throws XmlPullParserException, IOException {
                detectLabelResponse.webLabels = (DetectLabelResponse.DetectLabelResponseLabels) QCloudXml.fromXml(xmlPullParser, DetectLabelResponse.DetectLabelResponseLabels.class, "WebLabels");
            }
        });
        this.childElementBinders.put("CameraLabels", new ChildElementBinder<DetectLabelResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.DetectLabelResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DetectLabelResponse detectLabelResponse, String str) throws XmlPullParserException, IOException {
                detectLabelResponse.cameraLabels = (DetectLabelResponse.DetectLabelResponseLabels) QCloudXml.fromXml(xmlPullParser, DetectLabelResponse.DetectLabelResponseLabels.class, "CameraLabels");
            }
        });
        this.childElementBinders.put("AlbumLabels", new ChildElementBinder<DetectLabelResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.DetectLabelResponse$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DetectLabelResponse detectLabelResponse, String str) throws XmlPullParserException, IOException {
                detectLabelResponse.albumLabels = (DetectLabelResponse.DetectLabelResponseLabels) QCloudXml.fromXml(xmlPullParser, DetectLabelResponse.DetectLabelResponseLabels.class, "AlbumLabels");
            }
        });
        this.childElementBinders.put("NewsLabels", new ChildElementBinder<DetectLabelResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.DetectLabelResponse$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DetectLabelResponse detectLabelResponse, String str) throws XmlPullParserException, IOException {
                detectLabelResponse.newsLabels = (DetectLabelResponse.DetectLabelResponseLabels) QCloudXml.fromXml(xmlPullParser, DetectLabelResponse.DetectLabelResponseLabels.class, "NewsLabels");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public DetectLabelResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        DetectLabelResponse detectLabelResponse = new DetectLabelResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<DetectLabelResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, detectLabelResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return detectLabelResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return detectLabelResponse;
    }
}
