package com.tencent.cos.xml.model.tag.audit.get;

import com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetWebPageAuditJobResponse$WebPageAuditScenarioInfo$$XmlAdapter extends IXmlAdapter<GetWebPageAuditJobResponse.WebPageAuditScenarioInfo> {
    private HashMap<String, ChildElementBinder<GetWebPageAuditJobResponse.WebPageAuditScenarioInfo>> childElementBinders;

    public GetWebPageAuditJobResponse$WebPageAuditScenarioInfo$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetWebPageAuditJobResponse.WebPageAuditScenarioInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("HitFlag", new ChildElementBinder<GetWebPageAuditJobResponse.WebPageAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$WebPageAuditScenarioInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.WebPageAuditScenarioInfo webPageAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                webPageAuditScenarioInfo.hitFlag = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Score", new ChildElementBinder<GetWebPageAuditJobResponse.WebPageAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$WebPageAuditScenarioInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.WebPageAuditScenarioInfo webPageAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                webPageAuditScenarioInfo.score = Integer.parseInt(xmlPullParser.getText());
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetWebPageAuditJobResponse.WebPageAuditScenarioInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetWebPageAuditJobResponse.WebPageAuditScenarioInfo webPageAuditScenarioInfo = new GetWebPageAuditJobResponse.WebPageAuditScenarioInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetWebPageAuditJobResponse.WebPageAuditScenarioInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, webPageAuditScenarioInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "WebPageAuditScenarioInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return webPageAuditScenarioInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return webPageAuditScenarioInfo;
    }
}
