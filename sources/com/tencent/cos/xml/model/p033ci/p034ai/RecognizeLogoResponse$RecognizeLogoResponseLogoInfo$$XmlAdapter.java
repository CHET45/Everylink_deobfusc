package com.tencent.cos.xml.model.p033ci.p034ai;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.p034ai.RecognizeLogoResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class RecognizeLogoResponse$RecognizeLogoResponseLogoInfo$$XmlAdapter extends IXmlAdapter<RecognizeLogoResponse.RecognizeLogoResponseLogoInfo> {
    private HashMap<String, ChildElementBinder<RecognizeLogoResponse.RecognizeLogoResponseLogoInfo>> childElementBinders;

    public RecognizeLogoResponse$RecognizeLogoResponseLogoInfo$$XmlAdapter() {
        HashMap<String, ChildElementBinder<RecognizeLogoResponse.RecognizeLogoResponseLogoInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.NAME_ELEMENT, new ChildElementBinder<RecognizeLogoResponse.RecognizeLogoResponseLogoInfo>() { // from class: com.tencent.cos.xml.model.ci.ai.RecognizeLogoResponse$RecognizeLogoResponseLogoInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognizeLogoResponse.RecognizeLogoResponseLogoInfo recognizeLogoResponseLogoInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                recognizeLogoResponseLogoInfo.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Score", new ChildElementBinder<RecognizeLogoResponse.RecognizeLogoResponseLogoInfo>() { // from class: com.tencent.cos.xml.model.ci.ai.RecognizeLogoResponse$RecognizeLogoResponseLogoInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognizeLogoResponse.RecognizeLogoResponseLogoInfo recognizeLogoResponseLogoInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                recognizeLogoResponseLogoInfo.score = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Location", new ChildElementBinder<RecognizeLogoResponse.RecognizeLogoResponseLogoInfo>() { // from class: com.tencent.cos.xml.model.ci.ai.RecognizeLogoResponse$RecognizeLogoResponseLogoInfo$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognizeLogoResponse.RecognizeLogoResponseLogoInfo recognizeLogoResponseLogoInfo, String str) throws XmlPullParserException, IOException {
                if (recognizeLogoResponseLogoInfo.location == null) {
                    recognizeLogoResponseLogoInfo.location = new ArrayList();
                }
                recognizeLogoResponseLogoInfo.location.add((RecognizeLogoResponse.RecognizeLogoResponseLocation) QCloudXml.fromXml(xmlPullParser, RecognizeLogoResponse.RecognizeLogoResponseLocation.class, "Location"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public RecognizeLogoResponse.RecognizeLogoResponseLogoInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        RecognizeLogoResponse.RecognizeLogoResponseLogoInfo recognizeLogoResponseLogoInfo = new RecognizeLogoResponse.RecognizeLogoResponseLogoInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<RecognizeLogoResponse.RecognizeLogoResponseLogoInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, recognizeLogoResponseLogoInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "LogoInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return recognizeLogoResponseLogoInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return recognizeLogoResponseLogoInfo;
    }
}
