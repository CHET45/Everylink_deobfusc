package com.tencent.cos.xml.model.p033ci.p034ai;

import com.github.houbb.heaven.util.lang.BoolUtil;
import com.tencent.cos.xml.model.p033ci.p034ai.AIDetectCarResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class AIDetectCarResponse$AIDetectCarResponseCarLocation$$XmlAdapter extends IXmlAdapter<AIDetectCarResponse.AIDetectCarResponseCarLocation> {
    private HashMap<String, ChildElementBinder<AIDetectCarResponse.AIDetectCarResponseCarLocation>> childElementBinders;

    public AIDetectCarResponse$AIDetectCarResponseCarLocation$$XmlAdapter() {
        HashMap<String, ChildElementBinder<AIDetectCarResponse.AIDetectCarResponseCarLocation>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("X", new ChildElementBinder<AIDetectCarResponse.AIDetectCarResponseCarLocation>() { // from class: com.tencent.cos.xml.model.ci.ai.AIDetectCarResponse$AIDetectCarResponseCarLocation$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIDetectCarResponse.AIDetectCarResponseCarLocation aIDetectCarResponseCarLocation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIDetectCarResponseCarLocation.f1803x = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put(BoolUtil.f541Y, new ChildElementBinder<AIDetectCarResponse.AIDetectCarResponseCarLocation>() { // from class: com.tencent.cos.xml.model.ci.ai.AIDetectCarResponse$AIDetectCarResponseCarLocation$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIDetectCarResponse.AIDetectCarResponseCarLocation aIDetectCarResponseCarLocation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIDetectCarResponseCarLocation.f1804y = Integer.parseInt(xmlPullParser.getText());
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public AIDetectCarResponse.AIDetectCarResponseCarLocation fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        AIDetectCarResponse.AIDetectCarResponseCarLocation aIDetectCarResponseCarLocation = new AIDetectCarResponse.AIDetectCarResponseCarLocation();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<AIDetectCarResponse.AIDetectCarResponseCarLocation> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, aIDetectCarResponseCarLocation, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "CarLocation" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return aIDetectCarResponseCarLocation;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return aIDetectCarResponseCarLocation;
    }
}
