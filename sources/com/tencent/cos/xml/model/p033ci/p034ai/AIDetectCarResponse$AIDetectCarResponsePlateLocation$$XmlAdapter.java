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
public class AIDetectCarResponse$AIDetectCarResponsePlateLocation$$XmlAdapter extends IXmlAdapter<AIDetectCarResponse.AIDetectCarResponsePlateLocation> {
    private HashMap<String, ChildElementBinder<AIDetectCarResponse.AIDetectCarResponsePlateLocation>> childElementBinders;

    public AIDetectCarResponse$AIDetectCarResponsePlateLocation$$XmlAdapter() {
        HashMap<String, ChildElementBinder<AIDetectCarResponse.AIDetectCarResponsePlateLocation>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("X", new ChildElementBinder<AIDetectCarResponse.AIDetectCarResponsePlateLocation>() { // from class: com.tencent.cos.xml.model.ci.ai.AIDetectCarResponse$AIDetectCarResponsePlateLocation$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIDetectCarResponse.AIDetectCarResponsePlateLocation aIDetectCarResponsePlateLocation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIDetectCarResponsePlateLocation.f1805x = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put(BoolUtil.f541Y, new ChildElementBinder<AIDetectCarResponse.AIDetectCarResponsePlateLocation>() { // from class: com.tencent.cos.xml.model.ci.ai.AIDetectCarResponse$AIDetectCarResponsePlateLocation$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIDetectCarResponse.AIDetectCarResponsePlateLocation aIDetectCarResponsePlateLocation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIDetectCarResponsePlateLocation.f1806y = Integer.parseInt(xmlPullParser.getText());
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public AIDetectCarResponse.AIDetectCarResponsePlateLocation fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        AIDetectCarResponse.AIDetectCarResponsePlateLocation aIDetectCarResponsePlateLocation = new AIDetectCarResponse.AIDetectCarResponsePlateLocation();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<AIDetectCarResponse.AIDetectCarResponsePlateLocation> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, aIDetectCarResponsePlateLocation, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "PlateLocation" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return aIDetectCarResponsePlateLocation;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return aIDetectCarResponsePlateLocation;
    }
}
