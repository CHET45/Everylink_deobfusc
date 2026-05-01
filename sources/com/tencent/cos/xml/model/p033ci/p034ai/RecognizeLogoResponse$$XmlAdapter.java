package com.tencent.cos.xml.model.p033ci.p034ai;

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
public class RecognizeLogoResponse$$XmlAdapter extends IXmlAdapter<RecognizeLogoResponse> {
    private HashMap<String, ChildElementBinder<RecognizeLogoResponse>> childElementBinders;

    public RecognizeLogoResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<RecognizeLogoResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("LogoInfo", new ChildElementBinder<RecognizeLogoResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.RecognizeLogoResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognizeLogoResponse recognizeLogoResponse, String str) throws XmlPullParserException, IOException {
                if (recognizeLogoResponse.logoInfo == null) {
                    recognizeLogoResponse.logoInfo = new ArrayList();
                }
                recognizeLogoResponse.logoInfo.add((RecognizeLogoResponse.RecognizeLogoResponseLogoInfo) QCloudXml.fromXml(xmlPullParser, RecognizeLogoResponse.RecognizeLogoResponseLogoInfo.class, "LogoInfo"));
            }
        });
        this.childElementBinders.put("Status", new ChildElementBinder<RecognizeLogoResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.RecognizeLogoResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognizeLogoResponse recognizeLogoResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                recognizeLogoResponse.status = Integer.parseInt(xmlPullParser.getText());
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public RecognizeLogoResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        RecognizeLogoResponse recognizeLogoResponse = new RecognizeLogoResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<RecognizeLogoResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, recognizeLogoResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return recognizeLogoResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return recognizeLogoResponse;
    }
}
