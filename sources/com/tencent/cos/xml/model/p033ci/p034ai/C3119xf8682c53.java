package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.VocalScoreResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.ai.VocalScoreResponse$VocalScoreResponseRhythemScoreSentenceScores$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3119xf8682c53 extends IXmlAdapter<VocalScoreResponse.VocalScoreResponseRhythemScoreSentenceScores> {
    private HashMap<String, ChildElementBinder<VocalScoreResponse.VocalScoreResponseRhythemScoreSentenceScores>> childElementBinders;

    public C3119xf8682c53() {
        HashMap<String, ChildElementBinder<VocalScoreResponse.VocalScoreResponseRhythemScoreSentenceScores>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("StartTime", new ChildElementBinder<VocalScoreResponse.VocalScoreResponseRhythemScoreSentenceScores>() { // from class: com.tencent.cos.xml.model.ci.ai.VocalScoreResponse$VocalScoreResponseRhythemScoreSentenceScores$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VocalScoreResponse.VocalScoreResponseRhythemScoreSentenceScores vocalScoreResponseRhythemScoreSentenceScores, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                vocalScoreResponseRhythemScoreSentenceScores.startTime = Float.parseFloat(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("EndTime", new ChildElementBinder<VocalScoreResponse.VocalScoreResponseRhythemScoreSentenceScores>() { // from class: com.tencent.cos.xml.model.ci.ai.VocalScoreResponse$VocalScoreResponseRhythemScoreSentenceScores$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VocalScoreResponse.VocalScoreResponseRhythemScoreSentenceScores vocalScoreResponseRhythemScoreSentenceScores, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                vocalScoreResponseRhythemScoreSentenceScores.endTime = Float.parseFloat(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Score", new ChildElementBinder<VocalScoreResponse.VocalScoreResponseRhythemScoreSentenceScores>() { // from class: com.tencent.cos.xml.model.ci.ai.VocalScoreResponse$VocalScoreResponseRhythemScoreSentenceScores$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VocalScoreResponse.VocalScoreResponseRhythemScoreSentenceScores vocalScoreResponseRhythemScoreSentenceScores, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                vocalScoreResponseRhythemScoreSentenceScores.score = Integer.parseInt(xmlPullParser.getText());
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public VocalScoreResponse.VocalScoreResponseRhythemScoreSentenceScores fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        VocalScoreResponse.VocalScoreResponseRhythemScoreSentenceScores vocalScoreResponseRhythemScoreSentenceScores = new VocalScoreResponse.VocalScoreResponseRhythemScoreSentenceScores();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<VocalScoreResponse.VocalScoreResponseRhythemScoreSentenceScores> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, vocalScoreResponseRhythemScoreSentenceScores, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "SentenceScores" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return vocalScoreResponseRhythemScoreSentenceScores;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return vocalScoreResponseRhythemScoreSentenceScores;
    }
}
