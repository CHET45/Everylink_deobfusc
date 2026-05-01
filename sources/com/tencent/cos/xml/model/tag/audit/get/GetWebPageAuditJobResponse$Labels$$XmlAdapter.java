package com.tencent.cos.xml.model.tag.audit.get;

import com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetWebPageAuditJobResponse$Labels$$XmlAdapter extends IXmlAdapter<GetWebPageAuditJobResponse.Labels> {
    private HashMap<String, ChildElementBinder<GetWebPageAuditJobResponse.Labels>> childElementBinders;

    public GetWebPageAuditJobResponse$Labels$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetWebPageAuditJobResponse.Labels>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("PornInfo", new ChildElementBinder<GetWebPageAuditJobResponse.Labels>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$Labels$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.Labels labels, String str) throws XmlPullParserException, IOException {
                labels.pornInfo = (GetWebPageAuditJobResponse.WebPageAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, GetWebPageAuditJobResponse.WebPageAuditScenarioInfo.class, "PornInfo");
            }
        });
        this.childElementBinders.put("TerrorismInfo", new ChildElementBinder<GetWebPageAuditJobResponse.Labels>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$Labels$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.Labels labels, String str) throws XmlPullParserException, IOException {
                labels.terrorismInfo = (GetWebPageAuditJobResponse.WebPageAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, GetWebPageAuditJobResponse.WebPageAuditScenarioInfo.class, "TerrorismInfo");
            }
        });
        this.childElementBinders.put("PoliticsInfo", new ChildElementBinder<GetWebPageAuditJobResponse.Labels>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$Labels$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.Labels labels, String str) throws XmlPullParserException, IOException {
                labels.politicsInfo = (GetWebPageAuditJobResponse.WebPageAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, GetWebPageAuditJobResponse.WebPageAuditScenarioInfo.class, "PoliticsInfo");
            }
        });
        this.childElementBinders.put("AdsInfo", new ChildElementBinder<GetWebPageAuditJobResponse.Labels>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$Labels$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.Labels labels, String str) throws XmlPullParserException, IOException {
                labels.adsInfo = (GetWebPageAuditJobResponse.WebPageAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, GetWebPageAuditJobResponse.WebPageAuditScenarioInfo.class, "AdsInfo");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetWebPageAuditJobResponse.Labels fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetWebPageAuditJobResponse.Labels labels = new GetWebPageAuditJobResponse.Labels();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetWebPageAuditJobResponse.Labels> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
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
