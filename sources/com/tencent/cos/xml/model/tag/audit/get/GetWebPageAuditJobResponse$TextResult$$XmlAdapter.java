package com.tencent.cos.xml.model.tag.audit.get;

import com.tencent.cos.xml.model.tag.audit.bean.TextAuditScenarioInfo;
import com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetWebPageAuditJobResponse$TextResult$$XmlAdapter extends IXmlAdapter<GetWebPageAuditJobResponse.TextResult> {
    private HashMap<String, ChildElementBinder<GetWebPageAuditJobResponse.TextResult>> childElementBinders;

    public GetWebPageAuditJobResponse$TextResult$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetWebPageAuditJobResponse.TextResult>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Text", new ChildElementBinder<GetWebPageAuditJobResponse.TextResult>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$TextResult$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.TextResult textResult, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                textResult.text = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Label", new ChildElementBinder<GetWebPageAuditJobResponse.TextResult>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$TextResult$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.TextResult textResult, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                textResult.label = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Suggestion", new ChildElementBinder<GetWebPageAuditJobResponse.TextResult>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$TextResult$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.TextResult textResult, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                textResult.suggestion = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("PornInfo", new ChildElementBinder<GetWebPageAuditJobResponse.TextResult>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$TextResult$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.TextResult textResult, String str) throws XmlPullParserException, IOException {
                textResult.pornInfo = (TextAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, TextAuditScenarioInfo.class, "PornInfo");
            }
        });
        this.childElementBinders.put("TerrorismInfo", new ChildElementBinder<GetWebPageAuditJobResponse.TextResult>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$TextResult$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.TextResult textResult, String str) throws XmlPullParserException, IOException {
                textResult.terrorismInfo = (TextAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, TextAuditScenarioInfo.class, "TerrorismInfo");
            }
        });
        this.childElementBinders.put("PoliticsInfo", new ChildElementBinder<GetWebPageAuditJobResponse.TextResult>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$TextResult$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.TextResult textResult, String str) throws XmlPullParserException, IOException {
                textResult.politicsInfo = (TextAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, TextAuditScenarioInfo.class, "PoliticsInfo");
            }
        });
        this.childElementBinders.put("AdsInfo", new ChildElementBinder<GetWebPageAuditJobResponse.TextResult>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$TextResult$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.TextResult textResult, String str) throws XmlPullParserException, IOException {
                textResult.adsInfo = (TextAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, TextAuditScenarioInfo.class, "AdsInfo");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetWebPageAuditJobResponse.TextResult fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetWebPageAuditJobResponse.TextResult textResult = new GetWebPageAuditJobResponse.TextResult();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetWebPageAuditJobResponse.TextResult> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, textResult, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "TextResult" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return textResult;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return textResult;
    }
}
