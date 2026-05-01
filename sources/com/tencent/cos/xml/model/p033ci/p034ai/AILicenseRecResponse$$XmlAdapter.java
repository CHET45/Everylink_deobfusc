package com.tencent.cos.xml.model.p033ci.p034ai;

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
public class AILicenseRecResponse$$XmlAdapter extends IXmlAdapter<AILicenseRecResponse> {
    private HashMap<String, ChildElementBinder<AILicenseRecResponse>> childElementBinders;

    public AILicenseRecResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<AILicenseRecResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Status", new ChildElementBinder<AILicenseRecResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.AILicenseRecResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AILicenseRecResponse aILicenseRecResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aILicenseRecResponse.status = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("IdInfo", new ChildElementBinder<AILicenseRecResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.AILicenseRecResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AILicenseRecResponse aILicenseRecResponse, String str) throws XmlPullParserException, IOException {
                if (aILicenseRecResponse.idInfo == null) {
                    aILicenseRecResponse.idInfo = new ArrayList();
                }
                aILicenseRecResponse.idInfo.add((AILicenseRecResponse.AILicenseRecResponseIdInfo) QCloudXml.fromXml(xmlPullParser, AILicenseRecResponse.AILicenseRecResponseIdInfo.class, "IdInfo"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public AILicenseRecResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        AILicenseRecResponse aILicenseRecResponse = new AILicenseRecResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<AILicenseRecResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, aILicenseRecResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return aILicenseRecResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return aILicenseRecResponse;
    }
}
