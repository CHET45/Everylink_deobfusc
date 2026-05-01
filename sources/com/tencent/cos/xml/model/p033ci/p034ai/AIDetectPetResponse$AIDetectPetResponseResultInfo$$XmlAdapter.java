package com.tencent.cos.xml.model.p033ci.p034ai;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.p034ai.AIDetectPetResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class AIDetectPetResponse$AIDetectPetResponseResultInfo$$XmlAdapter extends IXmlAdapter<AIDetectPetResponse.AIDetectPetResponseResultInfo> {
    private HashMap<String, ChildElementBinder<AIDetectPetResponse.AIDetectPetResponseResultInfo>> childElementBinders;

    public AIDetectPetResponse$AIDetectPetResponseResultInfo$$XmlAdapter() {
        HashMap<String, ChildElementBinder<AIDetectPetResponse.AIDetectPetResponseResultInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Score", new ChildElementBinder<AIDetectPetResponse.AIDetectPetResponseResultInfo>() { // from class: com.tencent.cos.xml.model.ci.ai.AIDetectPetResponse$AIDetectPetResponseResultInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIDetectPetResponse.AIDetectPetResponseResultInfo aIDetectPetResponseResultInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIDetectPetResponseResultInfo.score = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put(Constants.NAME_ELEMENT, new ChildElementBinder<AIDetectPetResponse.AIDetectPetResponseResultInfo>() { // from class: com.tencent.cos.xml.model.ci.ai.AIDetectPetResponse$AIDetectPetResponseResultInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIDetectPetResponse.AIDetectPetResponseResultInfo aIDetectPetResponseResultInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIDetectPetResponseResultInfo.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Location", new ChildElementBinder<AIDetectPetResponse.AIDetectPetResponseResultInfo>() { // from class: com.tencent.cos.xml.model.ci.ai.AIDetectPetResponse$AIDetectPetResponseResultInfo$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIDetectPetResponse.AIDetectPetResponseResultInfo aIDetectPetResponseResultInfo, String str) throws XmlPullParserException, IOException {
                aIDetectPetResponseResultInfo.location = (AIDetectPetResponse.AIDetectPetResponseLocation) QCloudXml.fromXml(xmlPullParser, AIDetectPetResponse.AIDetectPetResponseLocation.class, "Location");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public AIDetectPetResponse.AIDetectPetResponseResultInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        AIDetectPetResponse.AIDetectPetResponseResultInfo aIDetectPetResponseResultInfo = new AIDetectPetResponse.AIDetectPetResponseResultInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<AIDetectPetResponse.AIDetectPetResponseResultInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, aIDetectPetResponseResultInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "ResultInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return aIDetectPetResponseResultInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return aIDetectPetResponseResultInfo;
    }
}
