package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.VocalScoreResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class VocalScoreResponse$VocalScoreResponseSentenceScores$$XmlAdapter extends IXmlAdapter<VocalScoreResponse.VocalScoreResponseSentenceScores> {
    private HashMap<String, ChildElementBinder<VocalScoreResponse.VocalScoreResponseSentenceScores>> childElementBinders;

    public VocalScoreResponse$VocalScoreResponseSentenceScores$$XmlAdapter() {
        HashMap<String, ChildElementBinder<VocalScoreResponse.VocalScoreResponseSentenceScores>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("StartTime", new ChildElementBinder<VocalScoreResponse.VocalScoreResponseSentenceScores>() { // from class: com.tencent.cos.xml.model.ci.ai.VocalScoreResponse$VocalScoreResponseSentenceScores$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VocalScoreResponse.VocalScoreResponseSentenceScores vocalScoreResponseSentenceScores, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                vocalScoreResponseSentenceScores.startTime = Float.parseFloat(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("EndTime", new ChildElementBinder<VocalScoreResponse.VocalScoreResponseSentenceScores>() { // from class: com.tencent.cos.xml.model.ci.ai.VocalScoreResponse$VocalScoreResponseSentenceScores$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VocalScoreResponse.VocalScoreResponseSentenceScores vocalScoreResponseSentenceScores, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                vocalScoreResponseSentenceScores.endTime = Float.parseFloat(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Score", new ChildElementBinder<VocalScoreResponse.VocalScoreResponseSentenceScores>() { // from class: com.tencent.cos.xml.model.ci.ai.VocalScoreResponse$VocalScoreResponseSentenceScores$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VocalScoreResponse.VocalScoreResponseSentenceScores vocalScoreResponseSentenceScores, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                vocalScoreResponseSentenceScores.score = Integer.parseInt(xmlPullParser.getText());
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public VocalScoreResponse.VocalScoreResponseSentenceScores fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        VocalScoreResponse.VocalScoreResponseSentenceScores vocalScoreResponseSentenceScores = new VocalScoreResponse.VocalScoreResponseSentenceScores();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<VocalScoreResponse.VocalScoreResponseSentenceScores> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, vocalScoreResponseSentenceScores, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "SentenceScores" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return vocalScoreResponseSentenceScores;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return vocalScoreResponseSentenceScores;
    }
}
