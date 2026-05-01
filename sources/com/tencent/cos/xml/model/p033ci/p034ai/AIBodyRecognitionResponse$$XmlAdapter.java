package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.AIBodyRecognitionResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class AIBodyRecognitionResponse$$XmlAdapter extends IXmlAdapter<AIBodyRecognitionResponse> {
    private HashMap<String, ChildElementBinder<AIBodyRecognitionResponse>> childElementBinders;

    public AIBodyRecognitionResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<AIBodyRecognitionResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Status", new ChildElementBinder<AIBodyRecognitionResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.AIBodyRecognitionResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIBodyRecognitionResponse aIBodyRecognitionResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIBodyRecognitionResponse.status = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("PedestrianInfo", new ChildElementBinder<AIBodyRecognitionResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.AIBodyRecognitionResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIBodyRecognitionResponse aIBodyRecognitionResponse, String str) throws XmlPullParserException, IOException {
                if (aIBodyRecognitionResponse.pedestrianInfo == null) {
                    aIBodyRecognitionResponse.pedestrianInfo = new ArrayList();
                }
                aIBodyRecognitionResponse.pedestrianInfo.add((AIBodyRecognitionResponse.AIBodyRecognitionPedestrianInfo) QCloudXml.fromXml(xmlPullParser, AIBodyRecognitionResponse.AIBodyRecognitionPedestrianInfo.class, "PedestrianInfo"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public AIBodyRecognitionResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        AIBodyRecognitionResponse aIBodyRecognitionResponse = new AIBodyRecognitionResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<AIBodyRecognitionResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, aIBodyRecognitionResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return aIBodyRecognitionResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return aIBodyRecognitionResponse;
    }
}
