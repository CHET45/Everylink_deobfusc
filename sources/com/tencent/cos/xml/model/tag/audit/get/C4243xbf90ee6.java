package com.tencent.cos.xml.model.tag.audit.get;

import com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$DocumentAuditScenarioInfo$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C4243xbf90ee6 extends IXmlAdapter<GetDocumentAuditJobResponse.DocumentAuditScenarioInfo> {
    private HashMap<String, ChildElementBinder<GetDocumentAuditJobResponse.DocumentAuditScenarioInfo>> childElementBinders;

    public C4243xbf90ee6() {
        HashMap<String, ChildElementBinder<GetDocumentAuditJobResponse.DocumentAuditScenarioInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("HitFlag", new ChildElementBinder<GetDocumentAuditJobResponse.DocumentAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$DocumentAuditScenarioInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.DocumentAuditScenarioInfo documentAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                documentAuditScenarioInfo.hitFlag = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Score", new ChildElementBinder<GetDocumentAuditJobResponse.DocumentAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$DocumentAuditScenarioInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.DocumentAuditScenarioInfo documentAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                documentAuditScenarioInfo.score = Integer.parseInt(xmlPullParser.getText());
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetDocumentAuditJobResponse.DocumentAuditScenarioInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetDocumentAuditJobResponse.DocumentAuditScenarioInfo documentAuditScenarioInfo = new GetDocumentAuditJobResponse.DocumentAuditScenarioInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetDocumentAuditJobResponse.DocumentAuditScenarioInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, documentAuditScenarioInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "DocumentAuditScenarioInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return documentAuditScenarioInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return documentAuditScenarioInfo;
    }
}
