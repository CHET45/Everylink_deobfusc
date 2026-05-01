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
public class AIDetectCarResponse$$XmlAdapter extends IXmlAdapter<AIDetectCarResponse> {
    private HashMap<String, ChildElementBinder<AIDetectCarResponse>> childElementBinders;

    public AIDetectCarResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<AIDetectCarResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("RequestId", new ChildElementBinder<AIDetectCarResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.AIDetectCarResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIDetectCarResponse aIDetectCarResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIDetectCarResponse.requestId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CarTags", new ChildElementBinder<AIDetectCarResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.AIDetectCarResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIDetectCarResponse aIDetectCarResponse, String str) throws XmlPullParserException, IOException {
                if (aIDetectCarResponse.carTags == null) {
                    aIDetectCarResponse.carTags = new ArrayList();
                }
                aIDetectCarResponse.carTags.add((AIDetectCarResponse.AIDetectCarResponseCarTags) QCloudXml.fromXml(xmlPullParser, AIDetectCarResponse.AIDetectCarResponseCarTags.class, "CarTags"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public AIDetectCarResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        AIDetectCarResponse aIDetectCarResponse = new AIDetectCarResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<AIDetectCarResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, aIDetectCarResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return aIDetectCarResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return aIDetectCarResponse;
    }
}
