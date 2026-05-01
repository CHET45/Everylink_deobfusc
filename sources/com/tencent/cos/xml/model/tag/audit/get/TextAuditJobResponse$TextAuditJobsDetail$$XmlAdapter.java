package com.tencent.cos.xml.model.tag.audit.get;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.tag.audit.bean.AuditListInfo;
import com.tencent.cos.xml.model.tag.audit.bean.AuditScenarioInfo;
import com.tencent.cos.xml.model.tag.audit.bean.AuditUserInfo;
import com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class TextAuditJobResponse$TextAuditJobsDetail$$XmlAdapter extends IXmlAdapter<TextAuditJobResponse.TextAuditJobsDetail> {
    private HashMap<String, ChildElementBinder<TextAuditJobResponse.TextAuditJobsDetail>> childElementBinders;

    public TextAuditJobResponse$TextAuditJobsDetail$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TextAuditJobResponse.TextAuditJobsDetail>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Content", new ChildElementBinder<TextAuditJobResponse.TextAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse$TextAuditJobsDetail$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditJobResponse.TextAuditJobsDetail textAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                textAuditJobsDetail.content = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SectionCount", new ChildElementBinder<TextAuditJobResponse.TextAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse$TextAuditJobsDetail$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditJobResponse.TextAuditJobsDetail textAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                textAuditJobsDetail.sectionCount = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("PornInfo", new ChildElementBinder<TextAuditJobResponse.TextAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse$TextAuditJobsDetail$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditJobResponse.TextAuditJobsDetail textAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                textAuditJobsDetail.pornInfo = (AuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, AuditScenarioInfo.class, "PornInfo");
            }
        });
        this.childElementBinders.put("TerrorismInfo", new ChildElementBinder<TextAuditJobResponse.TextAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse$TextAuditJobsDetail$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditJobResponse.TextAuditJobsDetail textAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                textAuditJobsDetail.terrorismInfo = (AuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, AuditScenarioInfo.class, "TerrorismInfo");
            }
        });
        this.childElementBinders.put("PoliticsInfo", new ChildElementBinder<TextAuditJobResponse.TextAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse$TextAuditJobsDetail$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditJobResponse.TextAuditJobsDetail textAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                textAuditJobsDetail.politicsInfo = (AuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, AuditScenarioInfo.class, "PoliticsInfo");
            }
        });
        this.childElementBinders.put("AdsInfo", new ChildElementBinder<TextAuditJobResponse.TextAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse$TextAuditJobsDetail$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditJobResponse.TextAuditJobsDetail textAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                textAuditJobsDetail.adsInfo = (AuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, AuditScenarioInfo.class, "AdsInfo");
            }
        });
        this.childElementBinders.put("IllegalInfo", new ChildElementBinder<TextAuditJobResponse.TextAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse$TextAuditJobsDetail$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditJobResponse.TextAuditJobsDetail textAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                textAuditJobsDetail.illegalInfo = (AuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, AuditScenarioInfo.class, "IllegalInfo");
            }
        });
        this.childElementBinders.put("AbuseInfo", new ChildElementBinder<TextAuditJobResponse.TextAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse$TextAuditJobsDetail$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditJobResponse.TextAuditJobsDetail textAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                textAuditJobsDetail.abuseInfo = (AuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, AuditScenarioInfo.class, "AbuseInfo");
            }
        });
        this.childElementBinders.put("Section", new ChildElementBinder<TextAuditJobResponse.TextAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse$TextAuditJobsDetail$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditJobResponse.TextAuditJobsDetail textAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                if (textAuditJobsDetail.section == null) {
                    textAuditJobsDetail.section = new ArrayList();
                }
                textAuditJobsDetail.section.add((TextAuditJobResponse.Section) QCloudXml.fromXml(xmlPullParser, TextAuditJobResponse.Section.class, "Section"));
            }
        });
        this.childElementBinders.put(Constants.ERROR_CODE, new ChildElementBinder<TextAuditJobResponse.TextAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse$TextAuditJobsDetail$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditJobResponse.TextAuditJobsDetail textAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                textAuditJobsDetail.code = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.ERROR_MESSAGE, new ChildElementBinder<TextAuditJobResponse.TextAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse$TextAuditJobsDetail$$XmlAdapter.11
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditJobResponse.TextAuditJobsDetail textAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                textAuditJobsDetail.message = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Result", new ChildElementBinder<TextAuditJobResponse.TextAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse$TextAuditJobsDetail$$XmlAdapter.12
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditJobResponse.TextAuditJobsDetail textAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                textAuditJobsDetail.result = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Label", new ChildElementBinder<TextAuditJobResponse.TextAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse$TextAuditJobsDetail$$XmlAdapter.13
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditJobResponse.TextAuditJobsDetail textAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                textAuditJobsDetail.label = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SubLabel", new ChildElementBinder<TextAuditJobResponse.TextAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse$TextAuditJobsDetail$$XmlAdapter.14
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditJobResponse.TextAuditJobsDetail textAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                textAuditJobsDetail.subLabel = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Category", new ChildElementBinder<TextAuditJobResponse.TextAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse$TextAuditJobsDetail$$XmlAdapter.15
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditJobResponse.TextAuditJobsDetail textAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                textAuditJobsDetail.category = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("UserInfo", new ChildElementBinder<TextAuditJobResponse.TextAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse$TextAuditJobsDetail$$XmlAdapter.16
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditJobResponse.TextAuditJobsDetail textAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                textAuditJobsDetail.userInfo = (AuditUserInfo) QCloudXml.fromXml(xmlPullParser, AuditUserInfo.class, "UserInfo");
            }
        });
        this.childElementBinders.put("ListInfo", new ChildElementBinder<TextAuditJobResponse.TextAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse$TextAuditJobsDetail$$XmlAdapter.17
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditJobResponse.TextAuditJobsDetail textAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                textAuditJobsDetail.listInfo = (AuditListInfo) QCloudXml.fromXml(xmlPullParser, AuditListInfo.class, "ListInfo");
            }
        });
        this.childElementBinders.put("ForbidState", new ChildElementBinder<TextAuditJobResponse.TextAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse$TextAuditJobsDetail$$XmlAdapter.18
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditJobResponse.TextAuditJobsDetail textAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                textAuditJobsDetail.forbidState = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("JobId", new ChildElementBinder<TextAuditJobResponse.TextAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse$TextAuditJobsDetail$$XmlAdapter.19
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditJobResponse.TextAuditJobsDetail textAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                textAuditJobsDetail.jobId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<TextAuditJobResponse.TextAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse$TextAuditJobsDetail$$XmlAdapter.20
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditJobResponse.TextAuditJobsDetail textAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                textAuditJobsDetail.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreationTime", new ChildElementBinder<TextAuditJobResponse.TextAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse$TextAuditJobsDetail$$XmlAdapter.21
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditJobResponse.TextAuditJobsDetail textAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                textAuditJobsDetail.creationTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("DataId", new ChildElementBinder<TextAuditJobResponse.TextAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse$TextAuditJobsDetail$$XmlAdapter.22
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditJobResponse.TextAuditJobsDetail textAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                textAuditJobsDetail.dataId = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TextAuditJobResponse.TextAuditJobsDetail fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TextAuditJobResponse.TextAuditJobsDetail textAuditJobsDetail = new TextAuditJobResponse.TextAuditJobsDetail();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TextAuditJobResponse.TextAuditJobsDetail> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, textAuditJobsDetail, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "JobsDetail" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return textAuditJobsDetail;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return textAuditJobsDetail;
    }
}
