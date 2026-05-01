package com.tencent.cos.xml.model.tag.audit.bean;

import com.microsoft.azure.storage.Constants;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class AuditJobsDetail$$XmlAdapter extends IXmlAdapter<AuditJobsDetail> {
    private HashMap<String, ChildElementBinder<AuditJobsDetail>> childElementBinders;

    public AuditJobsDetail$$XmlAdapter() {
        HashMap<String, ChildElementBinder<AuditJobsDetail>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.ERROR_CODE, new ChildElementBinder<AuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditJobsDetail$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditJobsDetail auditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditJobsDetail.code = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.ERROR_MESSAGE, new ChildElementBinder<AuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditJobsDetail$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditJobsDetail auditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditJobsDetail.message = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Result", new ChildElementBinder<AuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditJobsDetail$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditJobsDetail auditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditJobsDetail.result = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Label", new ChildElementBinder<AuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditJobsDetail$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditJobsDetail auditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditJobsDetail.label = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SubLabel", new ChildElementBinder<AuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditJobsDetail$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditJobsDetail auditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditJobsDetail.subLabel = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Category", new ChildElementBinder<AuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditJobsDetail$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditJobsDetail auditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditJobsDetail.category = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("UserInfo", new ChildElementBinder<AuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditJobsDetail$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditJobsDetail auditJobsDetail, String str) throws XmlPullParserException, IOException {
                auditJobsDetail.userInfo = (AuditUserInfo) QCloudXml.fromXml(xmlPullParser, AuditUserInfo.class, "UserInfo");
            }
        });
        this.childElementBinders.put("ListInfo", new ChildElementBinder<AuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditJobsDetail$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditJobsDetail auditJobsDetail, String str) throws XmlPullParserException, IOException {
                auditJobsDetail.listInfo = (AuditListInfo) QCloudXml.fromXml(xmlPullParser, AuditListInfo.class, "ListInfo");
            }
        });
        this.childElementBinders.put("ForbidState", new ChildElementBinder<AuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditJobsDetail$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditJobsDetail auditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditJobsDetail.forbidState = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("JobId", new ChildElementBinder<AuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditJobsDetail$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditJobsDetail auditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditJobsDetail.jobId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<AuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditJobsDetail$$XmlAdapter.11
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditJobsDetail auditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditJobsDetail.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreationTime", new ChildElementBinder<AuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditJobsDetail$$XmlAdapter.12
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditJobsDetail auditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditJobsDetail.creationTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("DataId", new ChildElementBinder<AuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditJobsDetail$$XmlAdapter.13
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditJobsDetail auditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditJobsDetail.dataId = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public AuditJobsDetail fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        AuditJobsDetail auditJobsDetail = new AuditJobsDetail();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<AuditJobsDetail> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, auditJobsDetail, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "JobsDetail" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return auditJobsDetail;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return auditJobsDetail;
    }
}
