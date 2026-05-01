package com.tencent.cos.xml.model.tag.audit.get;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.tag.audit.bean.ImageAuditScenarioInfo;
import com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetWebPageAuditJobResponse$ImageResult$$XmlAdapter extends IXmlAdapter<GetWebPageAuditJobResponse.ImageResult> {
    private HashMap<String, ChildElementBinder<GetWebPageAuditJobResponse.ImageResult>> childElementBinders;

    public GetWebPageAuditJobResponse$ImageResult$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetWebPageAuditJobResponse.ImageResult>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.URL_ELEMENT, new ChildElementBinder<GetWebPageAuditJobResponse.ImageResult>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$ImageResult$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.ImageResult imageResult, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                imageResult.url = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Text", new ChildElementBinder<GetWebPageAuditJobResponse.ImageResult>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$ImageResult$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.ImageResult imageResult, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                imageResult.text = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Label", new ChildElementBinder<GetWebPageAuditJobResponse.ImageResult>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$ImageResult$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.ImageResult imageResult, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                imageResult.label = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Suggestion", new ChildElementBinder<GetWebPageAuditJobResponse.ImageResult>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$ImageResult$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.ImageResult imageResult, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                imageResult.suggestion = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("PornInfo", new ChildElementBinder<GetWebPageAuditJobResponse.ImageResult>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$ImageResult$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.ImageResult imageResult, String str) throws XmlPullParserException, IOException {
                imageResult.pornInfo = (ImageAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, ImageAuditScenarioInfo.class, "PornInfo");
            }
        });
        this.childElementBinders.put("TerrorismInfo", new ChildElementBinder<GetWebPageAuditJobResponse.ImageResult>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$ImageResult$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.ImageResult imageResult, String str) throws XmlPullParserException, IOException {
                imageResult.terrorismInfo = (ImageAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, ImageAuditScenarioInfo.class, "TerrorismInfo");
            }
        });
        this.childElementBinders.put("PoliticsInfo", new ChildElementBinder<GetWebPageAuditJobResponse.ImageResult>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$ImageResult$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.ImageResult imageResult, String str) throws XmlPullParserException, IOException {
                imageResult.politicsInfo = (ImageAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, ImageAuditScenarioInfo.class, "PoliticsInfo");
            }
        });
        this.childElementBinders.put("AdsInfo", new ChildElementBinder<GetWebPageAuditJobResponse.ImageResult>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$ImageResult$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.ImageResult imageResult, String str) throws XmlPullParserException, IOException {
                imageResult.adsInfo = (ImageAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, ImageAuditScenarioInfo.class, "AdsInfo");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetWebPageAuditJobResponse.ImageResult fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetWebPageAuditJobResponse.ImageResult imageResult = new GetWebPageAuditJobResponse.ImageResult();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetWebPageAuditJobResponse.ImageResult> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, imageResult, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "ImageResult" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return imageResult;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return imageResult;
    }
}
