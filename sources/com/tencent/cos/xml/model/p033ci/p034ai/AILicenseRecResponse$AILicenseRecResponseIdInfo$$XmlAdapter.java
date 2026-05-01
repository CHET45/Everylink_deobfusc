package com.tencent.cos.xml.model.p033ci.p034ai;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.p034ai.AILicenseRecResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class AILicenseRecResponse$AILicenseRecResponseIdInfo$$XmlAdapter extends IXmlAdapter<AILicenseRecResponse.AILicenseRecResponseIdInfo> {
    private HashMap<String, ChildElementBinder<AILicenseRecResponse.AILicenseRecResponseIdInfo>> childElementBinders;

    public AILicenseRecResponse$AILicenseRecResponseIdInfo$$XmlAdapter() {
        HashMap<String, ChildElementBinder<AILicenseRecResponse.AILicenseRecResponseIdInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.NAME_ELEMENT, new ChildElementBinder<AILicenseRecResponse.AILicenseRecResponseIdInfo>() { // from class: com.tencent.cos.xml.model.ci.ai.AILicenseRecResponse$AILicenseRecResponseIdInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AILicenseRecResponse.AILicenseRecResponseIdInfo aILicenseRecResponseIdInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aILicenseRecResponseIdInfo.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("DetectedText", new ChildElementBinder<AILicenseRecResponse.AILicenseRecResponseIdInfo>() { // from class: com.tencent.cos.xml.model.ci.ai.AILicenseRecResponse$AILicenseRecResponseIdInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AILicenseRecResponse.AILicenseRecResponseIdInfo aILicenseRecResponseIdInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aILicenseRecResponseIdInfo.detectedText = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Score", new ChildElementBinder<AILicenseRecResponse.AILicenseRecResponseIdInfo>() { // from class: com.tencent.cos.xml.model.ci.ai.AILicenseRecResponse$AILicenseRecResponseIdInfo$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AILicenseRecResponse.AILicenseRecResponseIdInfo aILicenseRecResponseIdInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aILicenseRecResponseIdInfo.score = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Location", new ChildElementBinder<AILicenseRecResponse.AILicenseRecResponseIdInfo>() { // from class: com.tencent.cos.xml.model.ci.ai.AILicenseRecResponse$AILicenseRecResponseIdInfo$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AILicenseRecResponse.AILicenseRecResponseIdInfo aILicenseRecResponseIdInfo, String str) throws XmlPullParserException, IOException {
                if (aILicenseRecResponseIdInfo.location == null) {
                    aILicenseRecResponseIdInfo.location = new ArrayList();
                }
                aILicenseRecResponseIdInfo.location.add((AILicenseRecResponse.AILicenseRecResponseLocation) QCloudXml.fromXml(xmlPullParser, AILicenseRecResponse.AILicenseRecResponseLocation.class, "Location"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public AILicenseRecResponse.AILicenseRecResponseIdInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        AILicenseRecResponse.AILicenseRecResponseIdInfo aILicenseRecResponseIdInfo = new AILicenseRecResponse.AILicenseRecResponseIdInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<AILicenseRecResponse.AILicenseRecResponseIdInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, aILicenseRecResponseIdInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "IdInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return aILicenseRecResponseIdInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return aILicenseRecResponseIdInfo;
    }
}
