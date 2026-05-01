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
public class VocalScore$VocalScoreInput$$XmlAdapter extends IXmlAdapter<VocalScore.VocalScoreInput> {
    private HashMap<String, ChildElementBinder<VocalScore.VocalScoreInput>> childElementBinders;

    public VocalScore$VocalScoreInput$$XmlAdapter() {
        HashMap<String, ChildElementBinder<VocalScore.VocalScoreInput>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Object", new ChildElementBinder<VocalScore.VocalScoreInput>() { // from class: com.tencent.cos.xml.model.ci.ai.VocalScore$VocalScoreInput$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VocalScore.VocalScoreInput vocalScoreInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                vocalScoreInput.object = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public VocalScore.VocalScoreInput fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        VocalScore.VocalScoreInput vocalScoreInput = new VocalScore.VocalScoreInput();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<VocalScore.VocalScoreInput> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, vocalScoreInput, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Input" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return vocalScoreInput;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return vocalScoreInput;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, VocalScore.VocalScoreInput vocalScoreInput, String str) throws XmlPullParserException, IOException {
        if (vocalScoreInput == null) {
            return;
        }
        if (str == null) {
            str = "Input";
        }
        xmlSerializer.startTag("", str);
        if (vocalScoreInput.object != null) {
            xmlSerializer.startTag("", "Object");
            xmlSerializer.text(String.valueOf(vocalScoreInput.object));
            xmlSerializer.endTag("", "Object");
        }
        xmlSerializer.endTag("", str);
    }
}
