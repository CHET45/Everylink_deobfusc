package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.AIIDCardOCRResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class AIIDCardOCRResponse$$XmlAdapter extends IXmlAdapter<AIIDCardOCRResponse> {
    private HashMap<String, ChildElementBinder<AIIDCardOCRResponse>> childElementBinders;

    public AIIDCardOCRResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<AIIDCardOCRResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("IdInfo", new ChildElementBinder<AIIDCardOCRResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.AIIDCardOCRResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIIDCardOCRResponse aIIDCardOCRResponse, String str) throws XmlPullParserException, IOException {
                aIIDCardOCRResponse.idInfo = (AIIDCardOCRResponse.AIIDCardOCRResponseIdInfo) QCloudXml.fromXml(xmlPullParser, AIIDCardOCRResponse.AIIDCardOCRResponseIdInfo.class, "IdInfo");
            }
        });
        this.childElementBinders.put("AdvancedInfo", new ChildElementBinder<AIIDCardOCRResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.AIIDCardOCRResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIIDCardOCRResponse aIIDCardOCRResponse, String str) throws XmlPullParserException, IOException {
                aIIDCardOCRResponse.advancedInfo = (AIIDCardOCRResponse.AIIDCardOCRResponseAdvancedInfo) QCloudXml.fromXml(xmlPullParser, AIIDCardOCRResponse.AIIDCardOCRResponseAdvancedInfo.class, "AdvancedInfo");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public AIIDCardOCRResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        AIIDCardOCRResponse aIIDCardOCRResponse = new AIIDCardOCRResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<AIIDCardOCRResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, aIIDCardOCRResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return aIIDCardOCRResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return aIIDCardOCRResponse;
    }
}
