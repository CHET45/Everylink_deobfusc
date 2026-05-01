package com.tencent.cos.xml.model.p033ci.audit;

import com.microsoft.azure.storage.Constants;
import com.microsoft.azure.storage.blob.BlobConstants;
import com.tencent.cos.xml.model.p033ci.audit.GetLiveVideoAuditResponse;
import com.tencent.cos.xml.model.tag.audit.bean.AuditListInfo;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseJobsDetail$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3286x7d9d95a1 extends IXmlAdapter<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail> {
    private HashMap<String, ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail>> childElementBinders;

    public C3286x7d9d95a1() {
        HashMap<String, ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.ERROR_CODE, new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseJobsDetail$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail getLiveVideoAuditResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseJobsDetail.code = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.ERROR_MESSAGE, new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseJobsDetail$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail getLiveVideoAuditResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseJobsDetail.message = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("DataId", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseJobsDetail$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail getLiveVideoAuditResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseJobsDetail.dataId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobId", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseJobsDetail$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail getLiveVideoAuditResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseJobsDetail.jobId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseJobsDetail$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail getLiveVideoAuditResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseJobsDetail.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreationTime", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseJobsDetail$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail getLiveVideoAuditResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseJobsDetail.creationTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SnapshotCount", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseJobsDetail$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail getLiveVideoAuditResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseJobsDetail.snapshotCount = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Label", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseJobsDetail$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail getLiveVideoAuditResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseJobsDetail.label = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Result", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseJobsDetail$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail getLiveVideoAuditResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseJobsDetail.result = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("PornInfo", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseJobsDetail$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail getLiveVideoAuditResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                getLiveVideoAuditResponseJobsDetail.pornInfo = (GetLiveVideoAuditResponse.GetLiveVideoAuditResponseInfo) QCloudXml.fromXml(xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseInfo.class, "PornInfo");
            }
        });
        this.childElementBinders.put("AdsInfo", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseJobsDetail$$XmlAdapter.11
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail getLiveVideoAuditResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                getLiveVideoAuditResponseJobsDetail.adsInfo = (GetLiveVideoAuditResponse.GetLiveVideoAuditResponseInfo) QCloudXml.fromXml(xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseInfo.class, "AdsInfo");
            }
        });
        this.childElementBinders.put("MeaninglessInfo", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseJobsDetail$$XmlAdapter.12
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail getLiveVideoAuditResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                getLiveVideoAuditResponseJobsDetail.meaninglessInfo = (GetLiveVideoAuditResponse.GetLiveVideoAuditResponseInfo) QCloudXml.fromXml(xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseInfo.class, "MeaninglessInfo");
            }
        });
        this.childElementBinders.put(BlobConstants.SNAPSHOT_ELEMENT, new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseJobsDetail$$XmlAdapter.13
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail getLiveVideoAuditResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                if (getLiveVideoAuditResponseJobsDetail.snapshot == null) {
                    getLiveVideoAuditResponseJobsDetail.snapshot = new ArrayList();
                }
                getLiveVideoAuditResponseJobsDetail.snapshot.add((GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshot) QCloudXml.fromXml(xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshot.class, BlobConstants.SNAPSHOT_ELEMENT));
            }
        });
        this.childElementBinders.put("AudioSection", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseJobsDetail$$XmlAdapter.14
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail getLiveVideoAuditResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                if (getLiveVideoAuditResponseJobsDetail.audioSection == null) {
                    getLiveVideoAuditResponseJobsDetail.audioSection = new ArrayList();
                }
                getLiveVideoAuditResponseJobsDetail.audioSection.add((GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSection) QCloudXml.fromXml(xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSection.class, "AudioSection"));
            }
        });
        this.childElementBinders.put("UserInfo", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseJobsDetail$$XmlAdapter.15
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail getLiveVideoAuditResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                getLiveVideoAuditResponseJobsDetail.userInfo = (GetLiveVideoAuditResponse.GetLiveVideoAuditResponseUserInfo) QCloudXml.fromXml(xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseUserInfo.class, "UserInfo");
            }
        });
        this.childElementBinders.put("ListInfo", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseJobsDetail$$XmlAdapter.16
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail getLiveVideoAuditResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                getLiveVideoAuditResponseJobsDetail.listInfo = (AuditListInfo) QCloudXml.fromXml(xmlPullParser, AuditListInfo.class, "ListInfo");
            }
        });
        this.childElementBinders.put("Type", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseJobsDetail$$XmlAdapter.17
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail getLiveVideoAuditResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseJobsDetail.type = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail getLiveVideoAuditResponseJobsDetail = new GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseJobsDetail> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getLiveVideoAuditResponseJobsDetail, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "JobsDetail" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getLiveVideoAuditResponseJobsDetail;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getLiveVideoAuditResponseJobsDetail;
    }
}
