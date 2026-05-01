package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class AIFaceEffectResponse$$XmlAdapter extends IXmlAdapter<AIFaceEffectResponse> {
    private HashMap<String, ChildElementBinder<AIFaceEffectResponse>> childElementBinders;

    public AIFaceEffectResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<AIFaceEffectResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("ResultImage", new ChildElementBinder<AIFaceEffectResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.AIFaceEffectResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIFaceEffectResponse aIFaceEffectResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIFaceEffectResponse.resultImage = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("ResultMask", new ChildElementBinder<AIFaceEffectResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.AIFaceEffectResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIFaceEffectResponse aIFaceEffectResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIFaceEffectResponse.resultMask = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public AIFaceEffectResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        AIFaceEffectResponse aIFaceEffectResponse = new AIFaceEffectResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<AIFaceEffectResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, aIFaceEffectResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return aIFaceEffectResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return aIFaceEffectResponse;
    }
}
