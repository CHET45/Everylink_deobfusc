package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.media.SubmitPicProcessJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitPicProcessJobResponse$SubmitPicProcessJobResponseJobsDetail$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3593xa6fcbf1d extends IXmlAdapter<SubmitPicProcessJobResponse.SubmitPicProcessJobResponseJobsDetail> {
    private HashMap<String, ChildElementBinder<SubmitPicProcessJobResponse.SubmitPicProcessJobResponseJobsDetail>> childElementBinders;

    public C3593xa6fcbf1d() {
        HashMap<String, ChildElementBinder<SubmitPicProcessJobResponse.SubmitPicProcessJobResponseJobsDetail>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.ERROR_CODE, new ChildElementBinder<SubmitPicProcessJobResponse.SubmitPicProcessJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitPicProcessJobResponse$SubmitPicProcessJobResponseJobsDetail$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitPicProcessJobResponse.SubmitPicProcessJobResponseJobsDetail submitPicProcessJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitPicProcessJobResponseJobsDetail.code = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.ERROR_MESSAGE, new ChildElementBinder<SubmitPicProcessJobResponse.SubmitPicProcessJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitPicProcessJobResponse$SubmitPicProcessJobResponseJobsDetail$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitPicProcessJobResponse.SubmitPicProcessJobResponseJobsDetail submitPicProcessJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitPicProcessJobResponseJobsDetail.message = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobId", new ChildElementBinder<SubmitPicProcessJobResponse.SubmitPicProcessJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitPicProcessJobResponse$SubmitPicProcessJobResponseJobsDetail$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitPicProcessJobResponse.SubmitPicProcessJobResponseJobsDetail submitPicProcessJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitPicProcessJobResponseJobsDetail.jobId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Tag", new ChildElementBinder<SubmitPicProcessJobResponse.SubmitPicProcessJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitPicProcessJobResponse$SubmitPicProcessJobResponseJobsDetail$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitPicProcessJobResponse.SubmitPicProcessJobResponseJobsDetail submitPicProcessJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitPicProcessJobResponseJobsDetail.tag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<SubmitPicProcessJobResponse.SubmitPicProcessJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitPicProcessJobResponse$SubmitPicProcessJobResponseJobsDetail$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitPicProcessJobResponse.SubmitPicProcessJobResponseJobsDetail submitPicProcessJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitPicProcessJobResponseJobsDetail.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreationTime", new ChildElementBinder<SubmitPicProcessJobResponse.SubmitPicProcessJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitPicProcessJobResponse$SubmitPicProcessJobResponseJobsDetail$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitPicProcessJobResponse.SubmitPicProcessJobResponseJobsDetail submitPicProcessJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitPicProcessJobResponseJobsDetail.creationTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("StartTime", new ChildElementBinder<SubmitPicProcessJobResponse.SubmitPicProcessJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitPicProcessJobResponse$SubmitPicProcessJobResponseJobsDetail$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitPicProcessJobResponse.SubmitPicProcessJobResponseJobsDetail submitPicProcessJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitPicProcessJobResponseJobsDetail.startTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("EndTime", new ChildElementBinder<SubmitPicProcessJobResponse.SubmitPicProcessJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitPicProcessJobResponse$SubmitPicProcessJobResponseJobsDetail$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitPicProcessJobResponse.SubmitPicProcessJobResponseJobsDetail submitPicProcessJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitPicProcessJobResponseJobsDetail.endTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("QueueId", new ChildElementBinder<SubmitPicProcessJobResponse.SubmitPicProcessJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitPicProcessJobResponse$SubmitPicProcessJobResponseJobsDetail$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitPicProcessJobResponse.SubmitPicProcessJobResponseJobsDetail submitPicProcessJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitPicProcessJobResponseJobsDetail.queueId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Input", new ChildElementBinder<SubmitPicProcessJobResponse.SubmitPicProcessJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitPicProcessJobResponse$SubmitPicProcessJobResponseJobsDetail$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitPicProcessJobResponse.SubmitPicProcessJobResponseJobsDetail submitPicProcessJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                submitPicProcessJobResponseJobsDetail.input = (SubmitPicProcessJobResponse.SubmitPicProcessJobResponseInput) QCloudXml.fromXml(xmlPullParser, SubmitPicProcessJobResponse.SubmitPicProcessJobResponseInput.class, "Input");
            }
        });
        this.childElementBinders.put("Operation", new ChildElementBinder<SubmitPicProcessJobResponse.SubmitPicProcessJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitPicProcessJobResponse$SubmitPicProcessJobResponseJobsDetail$$XmlAdapter.11
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitPicProcessJobResponse.SubmitPicProcessJobResponseJobsDetail submitPicProcessJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                submitPicProcessJobResponseJobsDetail.operation = (SubmitPicProcessJobResponse.SubmitPicProcessJobResponseOperation) QCloudXml.fromXml(xmlPullParser, SubmitPicProcessJobResponse.SubmitPicProcessJobResponseOperation.class, "Operation");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitPicProcessJobResponse.SubmitPicProcessJobResponseJobsDetail fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitPicProcessJobResponse.SubmitPicProcessJobResponseJobsDetail submitPicProcessJobResponseJobsDetail = new SubmitPicProcessJobResponse.SubmitPicProcessJobResponseJobsDetail();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitPicProcessJobResponse.SubmitPicProcessJobResponseJobsDetail> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitPicProcessJobResponseJobsDetail, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "JobsDetail" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitPicProcessJobResponseJobsDetail;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitPicProcessJobResponseJobsDetail;
    }
}
