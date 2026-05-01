package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.VocalScore;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class VocalScore$VocalScoreVocalScore$$XmlAdapter extends IXmlAdapter<VocalScore.VocalScoreVocalScore> {
    private HashMap<String, ChildElementBinder<VocalScore.VocalScoreVocalScore>> childElementBinders;

    public VocalScore$VocalScoreVocalScore$$XmlAdapter() {
        HashMap<String, ChildElementBinder<VocalScore.VocalScoreVocalScore>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("StandardObject", new ChildElementBinder<VocalScore.VocalScoreVocalScore>() { // from class: com.tencent.cos.xml.model.ci.ai.VocalScore$VocalScoreVocalScore$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VocalScore.VocalScoreVocalScore vocalScoreVocalScore, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                vocalScoreVocalScore.standardObject = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public VocalScore.VocalScoreVocalScore fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        VocalScore.VocalScoreVocalScore vocalScoreVocalScore = new VocalScore.VocalScoreVocalScore();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<VocalScore.VocalScoreVocalScore> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, vocalScoreVocalScore, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Operation" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return vocalScoreVocalScore;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return vocalScoreVocalScore;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, VocalScore.VocalScoreVocalScore vocalScoreVocalScore, String str) throws XmlPullParserException, IOException {
        if (vocalScoreVocalScore == null) {
            return;
        }
        if (str == null) {
            str = "Operation";
        }
        xmlSerializer.startTag("", str);
        if (vocalScoreVocalScore.standardObject != null) {
            xmlSerializer.startTag("", "StandardObject");
            xmlSerializer.text(String.valueOf(vocalScoreVocalScore.standardObject));
            xmlSerializer.endTag("", "StandardObject");
        }
        xmlSerializer.endTag("", str);
    }
}
