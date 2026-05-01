package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.RecognizeLogoResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class RecognizeLogoResponse$RecognizeLogoResponseLocation$$XmlAdapter extends IXmlAdapter<RecognizeLogoResponse.RecognizeLogoResponseLocation> {
    private HashMap<String, ChildElementBinder<RecognizeLogoResponse.RecognizeLogoResponseLocation>> childElementBinders;

    public RecognizeLogoResponse$RecognizeLogoResponseLocation$$XmlAdapter() {
        HashMap<String, ChildElementBinder<RecognizeLogoResponse.RecognizeLogoResponseLocation>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Point", new ChildElementBinder<RecognizeLogoResponse.RecognizeLogoResponseLocation>() { // from class: com.tencent.cos.xml.model.ci.ai.RecognizeLogoResponse$RecognizeLogoResponseLocation$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognizeLogoResponse.RecognizeLogoResponseLocation recognizeLogoResponseLocation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                recognizeLogoResponseLocation.point = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public RecognizeLogoResponse.RecognizeLogoResponseLocation fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        RecognizeLogoResponse.RecognizeLogoResponseLocation recognizeLogoResponseLocation = new RecognizeLogoResponse.RecognizeLogoResponseLocation();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<RecognizeLogoResponse.RecognizeLogoResponseLocation> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, recognizeLogoResponseLocation, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Location" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return recognizeLogoResponseLocation;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return recognizeLogoResponseLocation;
    }
}
