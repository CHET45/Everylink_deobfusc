package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.media.SubmitSmartCoverJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitSmartCoverJobResponse$SubmitSmartCoverJobResponseJobsDetail$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3600x979cbd0b extends IXmlAdapter<SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseJobsDetail> {
    private HashMap<String, ChildElementBinder<SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseJobsDetail>> childElementBinders;

    public C3600x979cbd0b() {
        HashMap<String, ChildElementBinder<SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseJobsDetail>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.ERROR_CODE, new ChildElementBinder<SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSmartCoverJobResponse$SubmitSmartCoverJobResponseJobsDetail$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseJobsDetail submitSmartCoverJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSmartCoverJobResponseJobsDetail.code = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.ERROR_MESSAGE, new ChildElementBinder<SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSmartCoverJobResponse$SubmitSmartCoverJobResponseJobsDetail$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseJobsDetail submitSmartCoverJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSmartCoverJobResponseJobsDetail.message = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobId", new ChildElementBinder<SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSmartCoverJobResponse$SubmitSmartCoverJobResponseJobsDetail$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseJobsDetail submitSmartCoverJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSmartCoverJobResponseJobsDetail.jobId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Tag", new ChildElementBinder<SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSmartCoverJobResponse$SubmitSmartCoverJobResponseJobsDetail$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseJobsDetail submitSmartCoverJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSmartCoverJobResponseJobsDetail.tag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSmartCoverJobResponse$SubmitSmartCoverJobResponseJobsDetail$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseJobsDetail submitSmartCoverJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSmartCoverJobResponseJobsDetail.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreationTime", new ChildElementBinder<SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSmartCoverJobResponse$SubmitSmartCoverJobResponseJobsDetail$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseJobsDetail submitSmartCoverJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSmartCoverJobResponseJobsDetail.creationTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("EndTime", new ChildElementBinder<SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSmartCoverJobResponse$SubmitSmartCoverJobResponseJobsDetail$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseJobsDetail submitSmartCoverJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSmartCoverJobResponseJobsDetail.endTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("QueueId", new ChildElementBinder<SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSmartCoverJobResponse$SubmitSmartCoverJobResponseJobsDetail$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseJobsDetail submitSmartCoverJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSmartCoverJobResponseJobsDetail.queueId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Input", new ChildElementBinder<SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSmartCoverJobResponse$SubmitSmartCoverJobResponseJobsDetail$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseJobsDetail submitSmartCoverJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                submitSmartCoverJobResponseJobsDetail.input = (SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseInput) QCloudXml.fromXml(xmlPullParser, SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseInput.class, "Input");
            }
        });
        this.childElementBinders.put("Operation", new ChildElementBinder<SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSmartCoverJobResponse$SubmitSmartCoverJobResponseJobsDetail$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseJobsDetail submitSmartCoverJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                submitSmartCoverJobResponseJobsDetail.operation = (SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseOperation) QCloudXml.fromXml(xmlPullParser, SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseOperation.class, "Operation");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseJobsDetail fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseJobsDetail submitSmartCoverJobResponseJobsDetail = new SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseJobsDetail();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseJobsDetail> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitSmartCoverJobResponseJobsDetail, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "JobsDetail" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitSmartCoverJobResponseJobsDetail;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitSmartCoverJobResponseJobsDetail;
    }
}
