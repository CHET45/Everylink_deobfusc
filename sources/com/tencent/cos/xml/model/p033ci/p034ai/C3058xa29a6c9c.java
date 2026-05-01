package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.RecognitionQRcodeResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.ai.RecognitionQRcodeResponse$RecognitionQRcodeResponseQRcodeInfo$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3058xa29a6c9c extends IXmlAdapter<RecognitionQRcodeResponse.RecognitionQRcodeResponseQRcodeInfo> {
    private HashMap<String, ChildElementBinder<RecognitionQRcodeResponse.RecognitionQRcodeResponseQRcodeInfo>> childElementBinders;

    public C3058xa29a6c9c() {
        HashMap<String, ChildElementBinder<RecognitionQRcodeResponse.RecognitionQRcodeResponseQRcodeInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("CodeUrl", new ChildElementBinder<RecognitionQRcodeResponse.RecognitionQRcodeResponseQRcodeInfo>() { // from class: com.tencent.cos.xml.model.ci.ai.RecognitionQRcodeResponse$RecognitionQRcodeResponseQRcodeInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionQRcodeResponse.RecognitionQRcodeResponseQRcodeInfo recognitionQRcodeResponseQRcodeInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                recognitionQRcodeResponseQRcodeInfo.codeUrl = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CodeLocation", new ChildElementBinder<RecognitionQRcodeResponse.RecognitionQRcodeResponseQRcodeInfo>() { // from class: com.tencent.cos.xml.model.ci.ai.RecognitionQRcodeResponse$RecognitionQRcodeResponseQRcodeInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionQRcodeResponse.RecognitionQRcodeResponseQRcodeInfo recognitionQRcodeResponseQRcodeInfo, String str) throws XmlPullParserException, IOException {
                if (recognitionQRcodeResponseQRcodeInfo.codeLocation == null) {
                    recognitionQRcodeResponseQRcodeInfo.codeLocation = new ArrayList();
                }
                recognitionQRcodeResponseQRcodeInfo.codeLocation.add((RecognitionQRcodeResponse.RecognitionQRcodeResponseCodeLocation) QCloudXml.fromXml(xmlPullParser, RecognitionQRcodeResponse.RecognitionQRcodeResponseCodeLocation.class, "CodeLocation"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public RecognitionQRcodeResponse.RecognitionQRcodeResponseQRcodeInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        RecognitionQRcodeResponse.RecognitionQRcodeResponseQRcodeInfo recognitionQRcodeResponseQRcodeInfo = new RecognitionQRcodeResponse.RecognitionQRcodeResponseQRcodeInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<RecognitionQRcodeResponse.RecognitionQRcodeResponseQRcodeInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, recognitionQRcodeResponseQRcodeInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "QRcodeInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return recognitionQRcodeResponseQRcodeInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return recognitionQRcodeResponseQRcodeInfo;
    }
}
