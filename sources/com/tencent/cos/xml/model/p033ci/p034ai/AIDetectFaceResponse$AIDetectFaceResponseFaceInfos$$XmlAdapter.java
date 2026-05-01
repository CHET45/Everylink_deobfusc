package com.tencent.cos.xml.model.p033ci.p034ai;

import com.github.houbb.heaven.util.lang.BoolUtil;
import com.tencent.cos.xml.model.p033ci.p034ai.AIDetectFaceResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class AIDetectFaceResponse$AIDetectFaceResponseFaceInfos$$XmlAdapter extends IXmlAdapter<AIDetectFaceResponse.AIDetectFaceResponseFaceInfos> {
    private HashMap<String, ChildElementBinder<AIDetectFaceResponse.AIDetectFaceResponseFaceInfos>> childElementBinders;

    public AIDetectFaceResponse$AIDetectFaceResponseFaceInfos$$XmlAdapter() {
        HashMap<String, ChildElementBinder<AIDetectFaceResponse.AIDetectFaceResponseFaceInfos>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("X", new ChildElementBinder<AIDetectFaceResponse.AIDetectFaceResponseFaceInfos>() { // from class: com.tencent.cos.xml.model.ci.ai.AIDetectFaceResponse$AIDetectFaceResponseFaceInfos$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIDetectFaceResponse.AIDetectFaceResponseFaceInfos aIDetectFaceResponseFaceInfos, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIDetectFaceResponseFaceInfos.f1807x = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put(BoolUtil.f541Y, new ChildElementBinder<AIDetectFaceResponse.AIDetectFaceResponseFaceInfos>() { // from class: com.tencent.cos.xml.model.ci.ai.AIDetectFaceResponse$AIDetectFaceResponseFaceInfos$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIDetectFaceResponse.AIDetectFaceResponseFaceInfos aIDetectFaceResponseFaceInfos, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIDetectFaceResponseFaceInfos.f1808y = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Width", new ChildElementBinder<AIDetectFaceResponse.AIDetectFaceResponseFaceInfos>() { // from class: com.tencent.cos.xml.model.ci.ai.AIDetectFaceResponse$AIDetectFaceResponseFaceInfos$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIDetectFaceResponse.AIDetectFaceResponseFaceInfos aIDetectFaceResponseFaceInfos, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIDetectFaceResponseFaceInfos.width = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Height", new ChildElementBinder<AIDetectFaceResponse.AIDetectFaceResponseFaceInfos>() { // from class: com.tencent.cos.xml.model.ci.ai.AIDetectFaceResponse$AIDetectFaceResponseFaceInfos$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIDetectFaceResponse.AIDetectFaceResponseFaceInfos aIDetectFaceResponseFaceInfos, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIDetectFaceResponseFaceInfos.height = Integer.parseInt(xmlPullParser.getText());
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public AIDetectFaceResponse.AIDetectFaceResponseFaceInfos fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        AIDetectFaceResponse.AIDetectFaceResponseFaceInfos aIDetectFaceResponseFaceInfos = new AIDetectFaceResponse.AIDetectFaceResponseFaceInfos();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<AIDetectFaceResponse.AIDetectFaceResponseFaceInfos> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, aIDetectFaceResponseFaceInfos, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "FaceInfos" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return aIDetectFaceResponseFaceInfos;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return aIDetectFaceResponseFaceInfos;
    }
}
