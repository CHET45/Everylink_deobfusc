package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.VocalScoreResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class VocalScoreResponse$VocalScoreResponsePitchScore$$XmlAdapter extends IXmlAdapter<VocalScoreResponse.VocalScoreResponsePitchScore> {
    private HashMap<String, ChildElementBinder<VocalScoreResponse.VocalScoreResponsePitchScore>> childElementBinders;

    public VocalScoreResponse$VocalScoreResponsePitchScore$$XmlAdapter() {
        HashMap<String, ChildElementBinder<VocalScoreResponse.VocalScoreResponsePitchScore>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("SentenceScores", new ChildElementBinder<VocalScoreResponse.VocalScoreResponsePitchScore>() { // from class: com.tencent.cos.xml.model.ci.ai.VocalScoreResponse$VocalScoreResponsePitchScore$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VocalScoreResponse.VocalScoreResponsePitchScore vocalScoreResponsePitchScore, String str) throws XmlPullParserException, IOException {
                if (vocalScoreResponsePitchScore.sentenceScores == null) {
                    vocalScoreResponsePitchScore.sentenceScores = new ArrayList();
                }
                vocalScoreResponsePitchScore.sentenceScores.add((VocalScoreResponse.VocalScoreResponseSentenceScores) QCloudXml.fromXml(xmlPullParser, VocalScoreResponse.VocalScoreResponseSentenceScores.class, "SentenceScores"));
            }
        });
        this.childElementBinders.put("TotalScore", new ChildElementBinder<VocalScoreResponse.VocalScoreResponsePitchScore>() { // from class: com.tencent.cos.xml.model.ci.ai.VocalScoreResponse$VocalScoreResponsePitchScore$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VocalScoreResponse.VocalScoreResponsePitchScore vocalScoreResponsePitchScore, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                vocalScoreResponsePitchScore.totalScore = Integer.parseInt(xmlPullParser.getText());
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public VocalScoreResponse.VocalScoreResponsePitchScore fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        VocalScoreResponse.VocalScoreResponsePitchScore vocalScoreResponsePitchScore = new VocalScoreResponse.VocalScoreResponsePitchScore();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<VocalScoreResponse.VocalScoreResponsePitchScore> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, vocalScoreResponsePitchScore, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "PitchScore" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return vocalScoreResponsePitchScore;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return vocalScoreResponsePitchScore;
    }
}
