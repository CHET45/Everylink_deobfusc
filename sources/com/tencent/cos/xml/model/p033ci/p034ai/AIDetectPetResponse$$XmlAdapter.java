package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.AIDetectPetResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class AIDetectPetResponse$$XmlAdapter extends IXmlAdapter<AIDetectPetResponse> {
    private HashMap<String, ChildElementBinder<AIDetectPetResponse>> childElementBinders;

    public AIDetectPetResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<AIDetectPetResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("ResultInfo", new ChildElementBinder<AIDetectPetResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.AIDetectPetResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIDetectPetResponse aIDetectPetResponse, String str) throws XmlPullParserException, IOException {
                if (aIDetectPetResponse.resultInfo == null) {
                    aIDetectPetResponse.resultInfo = new ArrayList();
                }
                aIDetectPetResponse.resultInfo.add((AIDetectPetResponse.AIDetectPetResponseResultInfo) QCloudXml.fromXml(xmlPullParser, AIDetectPetResponse.AIDetectPetResponseResultInfo.class, "ResultInfo"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public AIDetectPetResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        AIDetectPetResponse aIDetectPetResponse = new AIDetectPetResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<AIDetectPetResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, aIDetectPetResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return aIDetectPetResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return aIDetectPetResponse;
    }
}
