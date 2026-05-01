package com.tencent.cos.xml.model.tag.audit.get;

import com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetDocumentAuditJobResponse$Labels$$XmlAdapter extends IXmlAdapter<GetDocumentAuditJobResponse.Labels> {
    private HashMap<String, ChildElementBinder<GetDocumentAuditJobResponse.Labels>> childElementBinders;

    public GetDocumentAuditJobResponse$Labels$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetDocumentAuditJobResponse.Labels>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("PornInfo", new ChildElementBinder<GetDocumentAuditJobResponse.Labels>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$Labels$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.Labels labels, String str) throws XmlPullParserException, IOException {
                labels.pornInfo = (GetDocumentAuditJobResponse.DocumentAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, GetDocumentAuditJobResponse.DocumentAuditScenarioInfo.class, "PornInfo");
            }
        });
        this.childElementBinders.put("TerrorismInfo", new ChildElementBinder<GetDocumentAuditJobResponse.Labels>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$Labels$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.Labels labels, String str) throws XmlPullParserException, IOException {
                labels.terrorismInfo = (GetDocumentAuditJobResponse.DocumentAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, GetDocumentAuditJobResponse.DocumentAuditScenarioInfo.class, "TerrorismInfo");
            }
        });
        this.childElementBinders.put("PoliticsInfo", new ChildElementBinder<GetDocumentAuditJobResponse.Labels>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$Labels$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.Labels labels, String str) throws XmlPullParserException, IOException {
                labels.politicsInfo = (GetDocumentAuditJobResponse.DocumentAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, GetDocumentAuditJobResponse.DocumentAuditScenarioInfo.class, "PoliticsInfo");
            }
        });
        this.childElementBinders.put("AdsInfo", new ChildElementBinder<GetDocumentAuditJobResponse.Labels>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$Labels$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.Labels labels, String str) throws XmlPullParserException, IOException {
                labels.adsInfo = (GetDocumentAuditJobResponse.DocumentAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, GetDocumentAuditJobResponse.DocumentAuditScenarioInfo.class, "AdsInfo");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetDocumentAuditJobResponse.Labels fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetDocumentAuditJobResponse.Labels labels = new GetDocumentAuditJobResponse.Labels();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetDocumentAuditJobResponse.Labels> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, labels, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Labels" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return labels;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return labels;
    }
}
