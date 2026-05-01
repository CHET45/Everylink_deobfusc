package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.COSOCRResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class COSOCRResponse$COSOCRResponseWords$$XmlAdapter extends IXmlAdapter<COSOCRResponse.COSOCRResponseWords> {
    private HashMap<String, ChildElementBinder<COSOCRResponse.COSOCRResponseWords>> childElementBinders;

    public COSOCRResponse$COSOCRResponseWords$$XmlAdapter() {
        HashMap<String, ChildElementBinder<COSOCRResponse.COSOCRResponseWords>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Confidence", new ChildElementBinder<COSOCRResponse.COSOCRResponseWords>() { // from class: com.tencent.cos.xml.model.ci.ai.COSOCRResponse$COSOCRResponseWords$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, COSOCRResponse.COSOCRResponseWords cOSOCRResponseWords, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                cOSOCRResponseWords.confidence = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Character", new ChildElementBinder<COSOCRResponse.COSOCRResponseWords>() { // from class: com.tencent.cos.xml.model.ci.ai.COSOCRResponse$COSOCRResponseWords$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, COSOCRResponse.COSOCRResponseWords cOSOCRResponseWords, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                cOSOCRResponseWords.character = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("WordCoordPoint", new ChildElementBinder<COSOCRResponse.COSOCRResponseWords>() { // from class: com.tencent.cos.xml.model.ci.ai.COSOCRResponse$COSOCRResponseWords$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, COSOCRResponse.COSOCRResponseWords cOSOCRResponseWords, String str) throws XmlPullParserException, IOException {
                cOSOCRResponseWords.wordCoordPoint = (COSOCRResponse.COSOCRResponseWordCoordPoint) QCloudXml.fromXml(xmlPullParser, COSOCRResponse.COSOCRResponseWordCoordPoint.class, "WordCoordPoint");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public COSOCRResponse.COSOCRResponseWords fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        COSOCRResponse.COSOCRResponseWords cOSOCRResponseWords = new COSOCRResponse.COSOCRResponseWords();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<COSOCRResponse.COSOCRResponseWords> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, cOSOCRResponseWords, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Words" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return cOSOCRResponseWords;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return cOSOCRResponseWords;
    }
}
