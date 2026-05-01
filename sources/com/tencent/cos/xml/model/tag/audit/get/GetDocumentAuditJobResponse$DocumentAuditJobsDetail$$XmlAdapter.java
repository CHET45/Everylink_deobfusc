package com.tencent.cos.xml.model.tag.audit.get;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.tag.audit.bean.AuditListInfo;
import com.tencent.cos.xml.model.tag.audit.bean.AuditUserInfo;
import com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetDocumentAuditJobResponse$DocumentAuditJobsDetail$$XmlAdapter extends IXmlAdapter<GetDocumentAuditJobResponse.DocumentAuditJobsDetail> {
    private HashMap<String, ChildElementBinder<GetDocumentAuditJobResponse.DocumentAuditJobsDetail>> childElementBinders;

    public GetDocumentAuditJobResponse$DocumentAuditJobsDetail$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetDocumentAuditJobResponse.DocumentAuditJobsDetail>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Suggestion", new ChildElementBinder<GetDocumentAuditJobResponse.DocumentAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$DocumentAuditJobsDetail$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.DocumentAuditJobsDetail documentAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                documentAuditJobsDetail.suggestion = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Object", new ChildElementBinder<GetDocumentAuditJobResponse.DocumentAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$DocumentAuditJobsDetail$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.DocumentAuditJobsDetail documentAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                documentAuditJobsDetail.object = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.URL_ELEMENT, new ChildElementBinder<GetDocumentAuditJobResponse.DocumentAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$DocumentAuditJobsDetail$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.DocumentAuditJobsDetail documentAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                documentAuditJobsDetail.url = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("PageCount", new ChildElementBinder<GetDocumentAuditJobResponse.DocumentAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$DocumentAuditJobsDetail$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.DocumentAuditJobsDetail documentAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                documentAuditJobsDetail.pageCount = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Labels", new ChildElementBinder<GetDocumentAuditJobResponse.DocumentAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$DocumentAuditJobsDetail$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.DocumentAuditJobsDetail documentAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                documentAuditJobsDetail.labels = (GetDocumentAuditJobResponse.Labels) QCloudXml.fromXml(xmlPullParser, GetDocumentAuditJobResponse.Labels.class, "Labels");
            }
        });
        this.childElementBinders.put("PageSegment", new ChildElementBinder<GetDocumentAuditJobResponse.DocumentAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$DocumentAuditJobsDetail$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.DocumentAuditJobsDetail documentAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                documentAuditJobsDetail.pageSegment = (GetDocumentAuditJobResponse.PageSegment) QCloudXml.fromXml(xmlPullParser, GetDocumentAuditJobResponse.PageSegment.class, "PageSegment");
            }
        });
        this.childElementBinders.put(Constants.ERROR_CODE, new ChildElementBinder<GetDocumentAuditJobResponse.DocumentAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$DocumentAuditJobsDetail$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.DocumentAuditJobsDetail documentAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                documentAuditJobsDetail.code = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.ERROR_MESSAGE, new ChildElementBinder<GetDocumentAuditJobResponse.DocumentAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$DocumentAuditJobsDetail$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.DocumentAuditJobsDetail documentAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                documentAuditJobsDetail.message = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Result", new ChildElementBinder<GetDocumentAuditJobResponse.DocumentAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$DocumentAuditJobsDetail$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.DocumentAuditJobsDetail documentAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                documentAuditJobsDetail.result = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Label", new ChildElementBinder<GetDocumentAuditJobResponse.DocumentAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$DocumentAuditJobsDetail$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.DocumentAuditJobsDetail documentAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                documentAuditJobsDetail.label = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SubLabel", new ChildElementBinder<GetDocumentAuditJobResponse.DocumentAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$DocumentAuditJobsDetail$$XmlAdapter.11
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.DocumentAuditJobsDetail documentAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                documentAuditJobsDetail.subLabel = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Category", new ChildElementBinder<GetDocumentAuditJobResponse.DocumentAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$DocumentAuditJobsDetail$$XmlAdapter.12
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.DocumentAuditJobsDetail documentAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                documentAuditJobsDetail.category = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("UserInfo", new ChildElementBinder<GetDocumentAuditJobResponse.DocumentAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$DocumentAuditJobsDetail$$XmlAdapter.13
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.DocumentAuditJobsDetail documentAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                documentAuditJobsDetail.userInfo = (AuditUserInfo) QCloudXml.fromXml(xmlPullParser, AuditUserInfo.class, "UserInfo");
            }
        });
        this.childElementBinders.put("ListInfo", new ChildElementBinder<GetDocumentAuditJobResponse.DocumentAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$DocumentAuditJobsDetail$$XmlAdapter.14
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.DocumentAuditJobsDetail documentAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                documentAuditJobsDetail.listInfo = (AuditListInfo) QCloudXml.fromXml(xmlPullParser, AuditListInfo.class, "ListInfo");
            }
        });
        this.childElementBinders.put("ForbidState", new ChildElementBinder<GetDocumentAuditJobResponse.DocumentAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$DocumentAuditJobsDetail$$XmlAdapter.15
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.DocumentAuditJobsDetail documentAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                documentAuditJobsDetail.forbidState = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("JobId", new ChildElementBinder<GetDocumentAuditJobResponse.DocumentAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$DocumentAuditJobsDetail$$XmlAdapter.16
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.DocumentAuditJobsDetail documentAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                documentAuditJobsDetail.jobId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<GetDocumentAuditJobResponse.DocumentAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$DocumentAuditJobsDetail$$XmlAdapter.17
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.DocumentAuditJobsDetail documentAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                documentAuditJobsDetail.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreationTime", new ChildElementBinder<GetDocumentAuditJobResponse.DocumentAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$DocumentAuditJobsDetail$$XmlAdapter.18
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.DocumentAuditJobsDetail documentAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                documentAuditJobsDetail.creationTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("DataId", new ChildElementBinder<GetDocumentAuditJobResponse.DocumentAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$DocumentAuditJobsDetail$$XmlAdapter.19
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.DocumentAuditJobsDetail documentAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                documentAuditJobsDetail.dataId = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetDocumentAuditJobResponse.DocumentAuditJobsDetail fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetDocumentAuditJobResponse.DocumentAuditJobsDetail documentAuditJobsDetail = new GetDocumentAuditJobResponse.DocumentAuditJobsDetail();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetDocumentAuditJobResponse.DocumentAuditJobsDetail> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, documentAuditJobsDetail, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "JobsDetail" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return documentAuditJobsDetail;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return documentAuditJobsDetail;
    }
}
