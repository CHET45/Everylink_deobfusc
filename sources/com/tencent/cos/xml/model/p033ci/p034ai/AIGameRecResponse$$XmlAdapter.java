package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.AIGameRecResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class AIGameRecResponse$$XmlAdapter extends IXmlAdapter<AIGameRecResponse> {
    private HashMap<String, ChildElementBinder<AIGameRecResponse>> childElementBinders;

    public AIGameRecResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<AIGameRecResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("GameLabels", new ChildElementBinder<AIGameRecResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.AIGameRecResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIGameRecResponse aIGameRecResponse, String str) throws XmlPullParserException, IOException {
                aIGameRecResponse.gameLabels = (AIGameRecResponse.AIGameRecResponseGameLabels) QCloudXml.fromXml(xmlPullParser, AIGameRecResponse.AIGameRecResponseGameLabels.class, "GameLabels");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public AIGameRecResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        AIGameRecResponse aIGameRecResponse = new AIGameRecResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<AIGameRecResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, aIGameRecResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return aIGameRecResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return aIGameRecResponse;
    }
}
