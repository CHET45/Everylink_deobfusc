package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitVideoTagJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitVideoTagJobResponse$SubmitVideoTagJobResponseDetailPerSecond$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3640x93fac42e extends IXmlAdapter<SubmitVideoTagJobResponse.SubmitVideoTagJobResponseDetailPerSecond> {
    private HashMap<String, ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponseDetailPerSecond>> childElementBinders;

    public C3640x93fac42e() {
        HashMap<String, ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponseDetailPerSecond>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("TimeStamp", new ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponseDetailPerSecond>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoTagJobResponse$SubmitVideoTagJobResponseDetailPerSecond$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponseDetailPerSecond submitVideoTagJobResponseDetailPerSecond, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVideoTagJobResponseDetailPerSecond.timeStamp = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Confidence", new ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponseDetailPerSecond>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoTagJobResponse$SubmitVideoTagJobResponseDetailPerSecond$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponseDetailPerSecond submitVideoTagJobResponseDetailPerSecond, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVideoTagJobResponseDetailPerSecond.confidence = Float.parseFloat(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("BBox", new ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponseDetailPerSecond>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoTagJobResponse$SubmitVideoTagJobResponseDetailPerSecond$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponseDetailPerSecond submitVideoTagJobResponseDetailPerSecond, String str) throws XmlPullParserException, IOException {
                if (submitVideoTagJobResponseDetailPerSecond.bBox == null) {
                    submitVideoTagJobResponseDetailPerSecond.bBox = new ArrayList();
                }
                submitVideoTagJobResponseDetailPerSecond.bBox.add((SubmitVideoTagJobResponse.SubmitVideoTagJobResponseBBox) QCloudXml.fromXml(xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponseBBox.class, "BBox"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitVideoTagJobResponse.SubmitVideoTagJobResponseDetailPerSecond fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitVideoTagJobResponse.SubmitVideoTagJobResponseDetailPerSecond submitVideoTagJobResponseDetailPerSecond = new SubmitVideoTagJobResponse.SubmitVideoTagJobResponseDetailPerSecond();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponseDetailPerSecond> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitVideoTagJobResponseDetailPerSecond, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "DetailPerSecond" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitVideoTagJobResponseDetailPerSecond;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitVideoTagJobResponseDetailPerSecond;
    }
}
