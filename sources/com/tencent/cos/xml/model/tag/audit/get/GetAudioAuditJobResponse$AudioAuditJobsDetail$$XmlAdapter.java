package com.tencent.cos.xml.model.tag.audit.get;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.tag.audit.bean.AuditListInfo;
import com.tencent.cos.xml.model.tag.audit.bean.AuditUserInfo;
import com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetAudioAuditJobResponse$AudioAuditJobsDetail$$XmlAdapter extends IXmlAdapter<GetAudioAuditJobResponse.AudioAuditJobsDetail> {
    private HashMap<String, ChildElementBinder<GetAudioAuditJobResponse.AudioAuditJobsDetail>> childElementBinders;

    public GetAudioAuditJobResponse$AudioAuditJobsDetail$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetAudioAuditJobResponse.AudioAuditJobsDetail>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Object", new ChildElementBinder<GetAudioAuditJobResponse.AudioAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioAuditJobsDetail$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioAuditJobsDetail audioAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioAuditJobsDetail.object = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.URL_ELEMENT, new ChildElementBinder<GetAudioAuditJobResponse.AudioAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioAuditJobsDetail$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioAuditJobsDetail audioAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioAuditJobsDetail.url = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("AudioText", new ChildElementBinder<GetAudioAuditJobResponse.AudioAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioAuditJobsDetail$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioAuditJobsDetail audioAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioAuditJobsDetail.audioText = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("PornInfo", new ChildElementBinder<GetAudioAuditJobResponse.AudioAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioAuditJobsDetail$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioAuditJobsDetail audioAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                audioAuditJobsDetail.pornInfo = (GetAudioAuditJobResponse.AudioAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, GetAudioAuditJobResponse.AudioAuditScenarioInfo.class, "PornInfo");
            }
        });
        this.childElementBinders.put("TerrorismInfo", new ChildElementBinder<GetAudioAuditJobResponse.AudioAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioAuditJobsDetail$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioAuditJobsDetail audioAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                audioAuditJobsDetail.terrorismInfo = (GetAudioAuditJobResponse.AudioAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, GetAudioAuditJobResponse.AudioAuditScenarioInfo.class, "TerrorismInfo");
            }
        });
        this.childElementBinders.put("PoliticsInfo", new ChildElementBinder<GetAudioAuditJobResponse.AudioAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioAuditJobsDetail$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioAuditJobsDetail audioAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                audioAuditJobsDetail.politicsInfo = (GetAudioAuditJobResponse.AudioAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, GetAudioAuditJobResponse.AudioAuditScenarioInfo.class, "PoliticsInfo");
            }
        });
        this.childElementBinders.put("AdsInfo", new ChildElementBinder<GetAudioAuditJobResponse.AudioAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioAuditJobsDetail$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioAuditJobsDetail audioAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                audioAuditJobsDetail.adsInfo = (GetAudioAuditJobResponse.AudioAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, GetAudioAuditJobResponse.AudioAuditScenarioInfo.class, "AdsInfo");
            }
        });
        this.childElementBinders.put("Section", new ChildElementBinder<GetAudioAuditJobResponse.AudioAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioAuditJobsDetail$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioAuditJobsDetail audioAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                if (audioAuditJobsDetail.section == null) {
                    audioAuditJobsDetail.section = new ArrayList();
                }
                audioAuditJobsDetail.section.add((GetAudioAuditJobResponse.AudioSection) QCloudXml.fromXml(xmlPullParser, GetAudioAuditJobResponse.AudioSection.class, "Section"));
            }
        });
        this.childElementBinders.put("BucketId", new ChildElementBinder<GetAudioAuditJobResponse.AudioAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioAuditJobsDetail$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioAuditJobsDetail audioAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioAuditJobsDetail.bucketId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Region", new ChildElementBinder<GetAudioAuditJobResponse.AudioAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioAuditJobsDetail$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioAuditJobsDetail audioAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioAuditJobsDetail.region = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CosHeaders", new ChildElementBinder<GetAudioAuditJobResponse.AudioAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioAuditJobsDetail$$XmlAdapter.11
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioAuditJobsDetail audioAuditJobsDetail, String str) throws XmlPullParserException, IOException {
            }
        });
        this.childElementBinders.put(Constants.ERROR_CODE, new ChildElementBinder<GetAudioAuditJobResponse.AudioAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioAuditJobsDetail$$XmlAdapter.12
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioAuditJobsDetail audioAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioAuditJobsDetail.code = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.ERROR_MESSAGE, new ChildElementBinder<GetAudioAuditJobResponse.AudioAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioAuditJobsDetail$$XmlAdapter.13
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioAuditJobsDetail audioAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioAuditJobsDetail.message = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Result", new ChildElementBinder<GetAudioAuditJobResponse.AudioAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioAuditJobsDetail$$XmlAdapter.14
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioAuditJobsDetail audioAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioAuditJobsDetail.result = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Label", new ChildElementBinder<GetAudioAuditJobResponse.AudioAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioAuditJobsDetail$$XmlAdapter.15
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioAuditJobsDetail audioAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioAuditJobsDetail.label = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SubLabel", new ChildElementBinder<GetAudioAuditJobResponse.AudioAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioAuditJobsDetail$$XmlAdapter.16
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioAuditJobsDetail audioAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioAuditJobsDetail.subLabel = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Category", new ChildElementBinder<GetAudioAuditJobResponse.AudioAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioAuditJobsDetail$$XmlAdapter.17
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioAuditJobsDetail audioAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioAuditJobsDetail.category = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("UserInfo", new ChildElementBinder<GetAudioAuditJobResponse.AudioAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioAuditJobsDetail$$XmlAdapter.18
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioAuditJobsDetail audioAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                audioAuditJobsDetail.userInfo = (AuditUserInfo) QCloudXml.fromXml(xmlPullParser, AuditUserInfo.class, "UserInfo");
            }
        });
        this.childElementBinders.put("ListInfo", new ChildElementBinder<GetAudioAuditJobResponse.AudioAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioAuditJobsDetail$$XmlAdapter.19
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioAuditJobsDetail audioAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                audioAuditJobsDetail.listInfo = (AuditListInfo) QCloudXml.fromXml(xmlPullParser, AuditListInfo.class, "ListInfo");
            }
        });
        this.childElementBinders.put("ForbidState", new ChildElementBinder<GetAudioAuditJobResponse.AudioAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioAuditJobsDetail$$XmlAdapter.20
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioAuditJobsDetail audioAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioAuditJobsDetail.forbidState = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("JobId", new ChildElementBinder<GetAudioAuditJobResponse.AudioAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioAuditJobsDetail$$XmlAdapter.21
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioAuditJobsDetail audioAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioAuditJobsDetail.jobId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<GetAudioAuditJobResponse.AudioAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioAuditJobsDetail$$XmlAdapter.22
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioAuditJobsDetail audioAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioAuditJobsDetail.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreationTime", new ChildElementBinder<GetAudioAuditJobResponse.AudioAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioAuditJobsDetail$$XmlAdapter.23
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioAuditJobsDetail audioAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioAuditJobsDetail.creationTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("DataId", new ChildElementBinder<GetAudioAuditJobResponse.AudioAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioAuditJobsDetail$$XmlAdapter.24
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioAuditJobsDetail audioAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioAuditJobsDetail.dataId = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetAudioAuditJobResponse.AudioAuditJobsDetail fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetAudioAuditJobResponse.AudioAuditJobsDetail audioAuditJobsDetail = new GetAudioAuditJobResponse.AudioAuditJobsDetail();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetAudioAuditJobResponse.AudioAuditJobsDetail> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, audioAuditJobsDetail, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "JobsDetail" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return audioAuditJobsDetail;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return audioAuditJobsDetail;
    }
}
