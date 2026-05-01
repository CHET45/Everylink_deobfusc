package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.media.SubmitVoiceSeparateJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitVoiceSeparateJobResponse$SubmitVoiceSeparateJobResponseJobsDetail$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3660x49934bc3 extends IXmlAdapter<SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseJobsDetail> {
    private HashMap<String, ChildElementBinder<SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseJobsDetail>> childElementBinders;

    public C3660x49934bc3() {
        HashMap<String, ChildElementBinder<SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseJobsDetail>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.ERROR_CODE, new ChildElementBinder<SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVoiceSeparateJobResponse$SubmitVoiceSeparateJobResponseJobsDetail$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseJobsDetail submitVoiceSeparateJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVoiceSeparateJobResponseJobsDetail.code = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.ERROR_MESSAGE, new ChildElementBinder<SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVoiceSeparateJobResponse$SubmitVoiceSeparateJobResponseJobsDetail$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseJobsDetail submitVoiceSeparateJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVoiceSeparateJobResponseJobsDetail.message = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobId", new ChildElementBinder<SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVoiceSeparateJobResponse$SubmitVoiceSeparateJobResponseJobsDetail$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseJobsDetail submitVoiceSeparateJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVoiceSeparateJobResponseJobsDetail.jobId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Tag", new ChildElementBinder<SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVoiceSeparateJobResponse$SubmitVoiceSeparateJobResponseJobsDetail$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseJobsDetail submitVoiceSeparateJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVoiceSeparateJobResponseJobsDetail.tag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVoiceSeparateJobResponse$SubmitVoiceSeparateJobResponseJobsDetail$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseJobsDetail submitVoiceSeparateJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVoiceSeparateJobResponseJobsDetail.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreationTime", new ChildElementBinder<SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVoiceSeparateJobResponse$SubmitVoiceSeparateJobResponseJobsDetail$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseJobsDetail submitVoiceSeparateJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVoiceSeparateJobResponseJobsDetail.creationTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("StartTime", new ChildElementBinder<SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVoiceSeparateJobResponse$SubmitVoiceSeparateJobResponseJobsDetail$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseJobsDetail submitVoiceSeparateJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVoiceSeparateJobResponseJobsDetail.startTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("EndTime", new ChildElementBinder<SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVoiceSeparateJobResponse$SubmitVoiceSeparateJobResponseJobsDetail$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseJobsDetail submitVoiceSeparateJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVoiceSeparateJobResponseJobsDetail.endTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("QueueId", new ChildElementBinder<SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVoiceSeparateJobResponse$SubmitVoiceSeparateJobResponseJobsDetail$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseJobsDetail submitVoiceSeparateJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVoiceSeparateJobResponseJobsDetail.queueId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Input", new ChildElementBinder<SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVoiceSeparateJobResponse$SubmitVoiceSeparateJobResponseJobsDetail$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseJobsDetail submitVoiceSeparateJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                submitVoiceSeparateJobResponseJobsDetail.input = (SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseInput) QCloudXml.fromXml(xmlPullParser, SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseInput.class, "Input");
            }
        });
        this.childElementBinders.put("Operation", new ChildElementBinder<SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVoiceSeparateJobResponse$SubmitVoiceSeparateJobResponseJobsDetail$$XmlAdapter.11
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseJobsDetail submitVoiceSeparateJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                submitVoiceSeparateJobResponseJobsDetail.operation = (SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseOperation) QCloudXml.fromXml(xmlPullParser, SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseOperation.class, "Operation");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseJobsDetail fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseJobsDetail submitVoiceSeparateJobResponseJobsDetail = new SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseJobsDetail();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseJobsDetail> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitVoiceSeparateJobResponseJobsDetail, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "JobsDetail" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitVoiceSeparateJobResponseJobsDetail;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitVoiceSeparateJobResponseJobsDetail;
    }
}
