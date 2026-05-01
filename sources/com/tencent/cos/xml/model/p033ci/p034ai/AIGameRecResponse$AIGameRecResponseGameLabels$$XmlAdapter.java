package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.AIGameRecResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class AIGameRecResponse$AIGameRecResponseGameLabels$$XmlAdapter extends IXmlAdapter<AIGameRecResponse.AIGameRecResponseGameLabels> {
    private HashMap<String, ChildElementBinder<AIGameRecResponse.AIGameRecResponseGameLabels>> childElementBinders;

    public AIGameRecResponse$AIGameRecResponseGameLabels$$XmlAdapter() {
        HashMap<String, ChildElementBinder<AIGameRecResponse.AIGameRecResponseGameLabels>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Confidence", new ChildElementBinder<AIGameRecResponse.AIGameRecResponseGameLabels>() { // from class: com.tencent.cos.xml.model.ci.ai.AIGameRecResponse$AIGameRecResponseGameLabels$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIGameRecResponse.AIGameRecResponseGameLabels aIGameRecResponseGameLabels, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIGameRecResponseGameLabels.confidence = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("FirstCategory", new ChildElementBinder<AIGameRecResponse.AIGameRecResponseGameLabels>() { // from class: com.tencent.cos.xml.model.ci.ai.AIGameRecResponse$AIGameRecResponseGameLabels$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIGameRecResponse.AIGameRecResponseGameLabels aIGameRecResponseGameLabels, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIGameRecResponseGameLabels.firstCategory = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SecondCategory", new ChildElementBinder<AIGameRecResponse.AIGameRecResponseGameLabels>() { // from class: com.tencent.cos.xml.model.ci.ai.AIGameRecResponse$AIGameRecResponseGameLabels$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIGameRecResponse.AIGameRecResponseGameLabels aIGameRecResponseGameLabels, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIGameRecResponseGameLabels.secondCategory = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("GameName", new ChildElementBinder<AIGameRecResponse.AIGameRecResponseGameLabels>() { // from class: com.tencent.cos.xml.model.ci.ai.AIGameRecResponse$AIGameRecResponseGameLabels$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIGameRecResponse.AIGameRecResponseGameLabels aIGameRecResponseGameLabels, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIGameRecResponseGameLabels.gameName = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public AIGameRecResponse.AIGameRecResponseGameLabels fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        AIGameRecResponse.AIGameRecResponseGameLabels aIGameRecResponseGameLabels = new AIGameRecResponse.AIGameRecResponseGameLabels();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<AIGameRecResponse.AIGameRecResponseGameLabels> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, aIGameRecResponseGameLabels, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "GameLabels" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return aIGameRecResponseGameLabels;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return aIGameRecResponseGameLabels;
    }
}
