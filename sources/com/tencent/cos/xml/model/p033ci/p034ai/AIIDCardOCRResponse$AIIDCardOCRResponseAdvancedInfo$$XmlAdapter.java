package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.AIIDCardOCRResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class AIIDCardOCRResponse$AIIDCardOCRResponseAdvancedInfo$$XmlAdapter extends IXmlAdapter<AIIDCardOCRResponse.AIIDCardOCRResponseAdvancedInfo> {
    private HashMap<String, ChildElementBinder<AIIDCardOCRResponse.AIIDCardOCRResponseAdvancedInfo>> childElementBinders;

    public AIIDCardOCRResponse$AIIDCardOCRResponseAdvancedInfo$$XmlAdapter() {
        HashMap<String, ChildElementBinder<AIIDCardOCRResponse.AIIDCardOCRResponseAdvancedInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("IdCard", new ChildElementBinder<AIIDCardOCRResponse.AIIDCardOCRResponseAdvancedInfo>() { // from class: com.tencent.cos.xml.model.ci.ai.AIIDCardOCRResponse$AIIDCardOCRResponseAdvancedInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIIDCardOCRResponse.AIIDCardOCRResponseAdvancedInfo aIIDCardOCRResponseAdvancedInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIIDCardOCRResponseAdvancedInfo.idCard = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Portrait", new ChildElementBinder<AIIDCardOCRResponse.AIIDCardOCRResponseAdvancedInfo>() { // from class: com.tencent.cos.xml.model.ci.ai.AIIDCardOCRResponse$AIIDCardOCRResponseAdvancedInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIIDCardOCRResponse.AIIDCardOCRResponseAdvancedInfo aIIDCardOCRResponseAdvancedInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIIDCardOCRResponseAdvancedInfo.portrait = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Quality", new ChildElementBinder<AIIDCardOCRResponse.AIIDCardOCRResponseAdvancedInfo>() { // from class: com.tencent.cos.xml.model.ci.ai.AIIDCardOCRResponse$AIIDCardOCRResponseAdvancedInfo$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIIDCardOCRResponse.AIIDCardOCRResponseAdvancedInfo aIIDCardOCRResponseAdvancedInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIIDCardOCRResponseAdvancedInfo.quality = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BorderCodeValue", new ChildElementBinder<AIIDCardOCRResponse.AIIDCardOCRResponseAdvancedInfo>() { // from class: com.tencent.cos.xml.model.ci.ai.AIIDCardOCRResponse$AIIDCardOCRResponseAdvancedInfo$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIIDCardOCRResponse.AIIDCardOCRResponseAdvancedInfo aIIDCardOCRResponseAdvancedInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIIDCardOCRResponseAdvancedInfo.borderCodeValue = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("WarnInfos", new ChildElementBinder<AIIDCardOCRResponse.AIIDCardOCRResponseAdvancedInfo>() { // from class: com.tencent.cos.xml.model.ci.ai.AIIDCardOCRResponse$AIIDCardOCRResponseAdvancedInfo$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIIDCardOCRResponse.AIIDCardOCRResponseAdvancedInfo aIIDCardOCRResponseAdvancedInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIIDCardOCRResponseAdvancedInfo.warnInfos = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public AIIDCardOCRResponse.AIIDCardOCRResponseAdvancedInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        AIIDCardOCRResponse.AIIDCardOCRResponseAdvancedInfo aIIDCardOCRResponseAdvancedInfo = new AIIDCardOCRResponse.AIIDCardOCRResponseAdvancedInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<AIIDCardOCRResponse.AIIDCardOCRResponseAdvancedInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, aIIDCardOCRResponseAdvancedInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "AdvancedInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return aIIDCardOCRResponseAdvancedInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return aIIDCardOCRResponseAdvancedInfo;
    }
}
