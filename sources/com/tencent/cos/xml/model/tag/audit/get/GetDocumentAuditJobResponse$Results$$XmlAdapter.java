package com.tencent.cos.xml.model.tag.audit.get;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.tag.audit.bean.ImageAuditScenarioInfo;
import com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetDocumentAuditJobResponse$Results$$XmlAdapter extends IXmlAdapter<GetDocumentAuditJobResponse.Results> {
    private HashMap<String, ChildElementBinder<GetDocumentAuditJobResponse.Results>> childElementBinders;

    public GetDocumentAuditJobResponse$Results$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetDocumentAuditJobResponse.Results>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.URL_ELEMENT, new ChildElementBinder<GetDocumentAuditJobResponse.Results>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$Results$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.Results results, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                results.url = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Text", new ChildElementBinder<GetDocumentAuditJobResponse.Results>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$Results$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.Results results, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                results.text = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("PageNumber", new ChildElementBinder<GetDocumentAuditJobResponse.Results>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$Results$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.Results results, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                results.pageNumber = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("SheetNumber", new ChildElementBinder<GetDocumentAuditJobResponse.Results>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$Results$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.Results results, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                results.sheetNumber = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Label", new ChildElementBinder<GetDocumentAuditJobResponse.Results>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$Results$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.Results results, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                results.label = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Suggestion", new ChildElementBinder<GetDocumentAuditJobResponse.Results>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$Results$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.Results results, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                results.suggestion = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("PornInfo", new ChildElementBinder<GetDocumentAuditJobResponse.Results>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$Results$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.Results results, String str) throws XmlPullParserException, IOException {
                results.pornInfo = (ImageAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, ImageAuditScenarioInfo.class, "PornInfo");
            }
        });
        this.childElementBinders.put("TerrorismInfo", new ChildElementBinder<GetDocumentAuditJobResponse.Results>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$Results$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.Results results, String str) throws XmlPullParserException, IOException {
                results.terrorismInfo = (ImageAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, ImageAuditScenarioInfo.class, "TerrorismInfo");
            }
        });
        this.childElementBinders.put("PoliticsInfo", new ChildElementBinder<GetDocumentAuditJobResponse.Results>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$Results$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.Results results, String str) throws XmlPullParserException, IOException {
                results.politicsInfo = (ImageAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, ImageAuditScenarioInfo.class, "PoliticsInfo");
            }
        });
        this.childElementBinders.put("AdsInfo", new ChildElementBinder<GetDocumentAuditJobResponse.Results>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$Results$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.Results results, String str) throws XmlPullParserException, IOException {
                results.adsInfo = (ImageAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, ImageAuditScenarioInfo.class, "AdsInfo");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetDocumentAuditJobResponse.Results fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetDocumentAuditJobResponse.Results results = new GetDocumentAuditJobResponse.Results();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetDocumentAuditJobResponse.Results> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, results, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Results" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return results;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return results;
    }
}
