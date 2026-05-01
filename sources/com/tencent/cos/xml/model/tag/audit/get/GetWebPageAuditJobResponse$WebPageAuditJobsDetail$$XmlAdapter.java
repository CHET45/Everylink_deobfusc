package com.tencent.cos.xml.model.tag.audit.get;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.tag.audit.bean.AuditListInfo;
import com.tencent.cos.xml.model.tag.audit.bean.AuditUserInfo;
import com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetWebPageAuditJobResponse$WebPageAuditJobsDetail$$XmlAdapter extends IXmlAdapter<GetWebPageAuditJobResponse.WebPageAuditJobsDetail> {
    private HashMap<String, ChildElementBinder<GetWebPageAuditJobResponse.WebPageAuditJobsDetail>> childElementBinders;

    public GetWebPageAuditJobResponse$WebPageAuditJobsDetail$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetWebPageAuditJobResponse.WebPageAuditJobsDetail>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Suggestion", new ChildElementBinder<GetWebPageAuditJobResponse.WebPageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$WebPageAuditJobsDetail$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.WebPageAuditJobsDetail webPageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                webPageAuditJobsDetail.suggestion = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put(Constants.URL_ELEMENT, new ChildElementBinder<GetWebPageAuditJobResponse.WebPageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$WebPageAuditJobsDetail$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.WebPageAuditJobsDetail webPageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                webPageAuditJobsDetail.url = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("PageCount", new ChildElementBinder<GetWebPageAuditJobResponse.WebPageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$WebPageAuditJobsDetail$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.WebPageAuditJobsDetail webPageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                webPageAuditJobsDetail.pageCount = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("HighlightHtml", new ChildElementBinder<GetWebPageAuditJobResponse.WebPageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$WebPageAuditJobsDetail$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.WebPageAuditJobsDetail webPageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                webPageAuditJobsDetail.highlightHtml = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Labels", new ChildElementBinder<GetWebPageAuditJobResponse.WebPageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$WebPageAuditJobsDetail$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.WebPageAuditJobsDetail webPageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                webPageAuditJobsDetail.labels = (GetWebPageAuditJobResponse.Labels) QCloudXml.fromXml(xmlPullParser, GetWebPageAuditJobResponse.Labels.class, "Labels");
            }
        });
        this.childElementBinders.put("ImageResults", new ChildElementBinder<GetWebPageAuditJobResponse.WebPageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$WebPageAuditJobsDetail$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.WebPageAuditJobsDetail webPageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                webPageAuditJobsDetail.imageResults = (GetWebPageAuditJobResponse.ImageResults) QCloudXml.fromXml(xmlPullParser, GetWebPageAuditJobResponse.ImageResults.class, "ImageResults");
            }
        });
        this.childElementBinders.put("TextResults", new ChildElementBinder<GetWebPageAuditJobResponse.WebPageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$WebPageAuditJobsDetail$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.WebPageAuditJobsDetail webPageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                webPageAuditJobsDetail.textResults = (GetWebPageAuditJobResponse.TextResults) QCloudXml.fromXml(xmlPullParser, GetWebPageAuditJobResponse.TextResults.class, "TextResults");
            }
        });
        this.childElementBinders.put(Constants.ERROR_CODE, new ChildElementBinder<GetWebPageAuditJobResponse.WebPageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$WebPageAuditJobsDetail$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.WebPageAuditJobsDetail webPageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                webPageAuditJobsDetail.code = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.ERROR_MESSAGE, new ChildElementBinder<GetWebPageAuditJobResponse.WebPageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$WebPageAuditJobsDetail$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.WebPageAuditJobsDetail webPageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                webPageAuditJobsDetail.message = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Result", new ChildElementBinder<GetWebPageAuditJobResponse.WebPageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$WebPageAuditJobsDetail$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.WebPageAuditJobsDetail webPageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                webPageAuditJobsDetail.result = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Label", new ChildElementBinder<GetWebPageAuditJobResponse.WebPageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$WebPageAuditJobsDetail$$XmlAdapter.11
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.WebPageAuditJobsDetail webPageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                webPageAuditJobsDetail.label = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SubLabel", new ChildElementBinder<GetWebPageAuditJobResponse.WebPageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$WebPageAuditJobsDetail$$XmlAdapter.12
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.WebPageAuditJobsDetail webPageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                webPageAuditJobsDetail.subLabel = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Category", new ChildElementBinder<GetWebPageAuditJobResponse.WebPageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$WebPageAuditJobsDetail$$XmlAdapter.13
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.WebPageAuditJobsDetail webPageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                webPageAuditJobsDetail.category = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("UserInfo", new ChildElementBinder<GetWebPageAuditJobResponse.WebPageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$WebPageAuditJobsDetail$$XmlAdapter.14
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.WebPageAuditJobsDetail webPageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                webPageAuditJobsDetail.userInfo = (AuditUserInfo) QCloudXml.fromXml(xmlPullParser, AuditUserInfo.class, "UserInfo");
            }
        });
        this.childElementBinders.put("ListInfo", new ChildElementBinder<GetWebPageAuditJobResponse.WebPageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$WebPageAuditJobsDetail$$XmlAdapter.15
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.WebPageAuditJobsDetail webPageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                webPageAuditJobsDetail.listInfo = (AuditListInfo) QCloudXml.fromXml(xmlPullParser, AuditListInfo.class, "ListInfo");
            }
        });
        this.childElementBinders.put("ForbidState", new ChildElementBinder<GetWebPageAuditJobResponse.WebPageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$WebPageAuditJobsDetail$$XmlAdapter.16
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.WebPageAuditJobsDetail webPageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                webPageAuditJobsDetail.forbidState = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("JobId", new ChildElementBinder<GetWebPageAuditJobResponse.WebPageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$WebPageAuditJobsDetail$$XmlAdapter.17
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.WebPageAuditJobsDetail webPageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                webPageAuditJobsDetail.jobId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<GetWebPageAuditJobResponse.WebPageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$WebPageAuditJobsDetail$$XmlAdapter.18
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.WebPageAuditJobsDetail webPageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                webPageAuditJobsDetail.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreationTime", new ChildElementBinder<GetWebPageAuditJobResponse.WebPageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$WebPageAuditJobsDetail$$XmlAdapter.19
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.WebPageAuditJobsDetail webPageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                webPageAuditJobsDetail.creationTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("DataId", new ChildElementBinder<GetWebPageAuditJobResponse.WebPageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$WebPageAuditJobsDetail$$XmlAdapter.20
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.WebPageAuditJobsDetail webPageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                webPageAuditJobsDetail.dataId = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetWebPageAuditJobResponse.WebPageAuditJobsDetail fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetWebPageAuditJobResponse.WebPageAuditJobsDetail webPageAuditJobsDetail = new GetWebPageAuditJobResponse.WebPageAuditJobsDetail();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetWebPageAuditJobResponse.WebPageAuditJobsDetail> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, webPageAuditJobsDetail, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "JobsDetail" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return webPageAuditJobsDetail;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return webPageAuditJobsDetail;
    }
}
