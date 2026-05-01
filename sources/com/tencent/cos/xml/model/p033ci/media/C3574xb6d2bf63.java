package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.media.SubmitMediaInfoJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitMediaInfoJobResponse$SubmitMediaInfoJobResponseJobsDetail$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3574xb6d2bf63 extends IXmlAdapter<SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseJobsDetail> {
    private HashMap<String, ChildElementBinder<SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseJobsDetail>> childElementBinders;

    public C3574xb6d2bf63() {
        HashMap<String, ChildElementBinder<SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseJobsDetail>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.ERROR_CODE, new ChildElementBinder<SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaInfoJobResponse$SubmitMediaInfoJobResponseJobsDetail$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseJobsDetail submitMediaInfoJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaInfoJobResponseJobsDetail.code = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.ERROR_MESSAGE, new ChildElementBinder<SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaInfoJobResponse$SubmitMediaInfoJobResponseJobsDetail$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseJobsDetail submitMediaInfoJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaInfoJobResponseJobsDetail.message = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobId", new ChildElementBinder<SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaInfoJobResponse$SubmitMediaInfoJobResponseJobsDetail$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseJobsDetail submitMediaInfoJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaInfoJobResponseJobsDetail.jobId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Tag", new ChildElementBinder<SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaInfoJobResponse$SubmitMediaInfoJobResponseJobsDetail$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseJobsDetail submitMediaInfoJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaInfoJobResponseJobsDetail.tag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaInfoJobResponse$SubmitMediaInfoJobResponseJobsDetail$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseJobsDetail submitMediaInfoJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaInfoJobResponseJobsDetail.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreationTime", new ChildElementBinder<SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaInfoJobResponse$SubmitMediaInfoJobResponseJobsDetail$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseJobsDetail submitMediaInfoJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaInfoJobResponseJobsDetail.creationTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("StartTime", new ChildElementBinder<SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaInfoJobResponse$SubmitMediaInfoJobResponseJobsDetail$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseJobsDetail submitMediaInfoJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaInfoJobResponseJobsDetail.startTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("EndTime", new ChildElementBinder<SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaInfoJobResponse$SubmitMediaInfoJobResponseJobsDetail$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseJobsDetail submitMediaInfoJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaInfoJobResponseJobsDetail.endTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("QueueId", new ChildElementBinder<SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaInfoJobResponse$SubmitMediaInfoJobResponseJobsDetail$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseJobsDetail submitMediaInfoJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaInfoJobResponseJobsDetail.queueId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Input", new ChildElementBinder<SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaInfoJobResponse$SubmitMediaInfoJobResponseJobsDetail$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseJobsDetail submitMediaInfoJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                submitMediaInfoJobResponseJobsDetail.input = (SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseInput) QCloudXml.fromXml(xmlPullParser, SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseInput.class, "Input");
            }
        });
        this.childElementBinders.put("Operation", new ChildElementBinder<SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaInfoJobResponse$SubmitMediaInfoJobResponseJobsDetail$$XmlAdapter.11
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseJobsDetail submitMediaInfoJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                submitMediaInfoJobResponseJobsDetail.operation = (SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseOperation) QCloudXml.fromXml(xmlPullParser, SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseOperation.class, "Operation");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseJobsDetail fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseJobsDetail submitMediaInfoJobResponseJobsDetail = new SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseJobsDetail();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseJobsDetail> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitMediaInfoJobResponseJobsDetail, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "JobsDetail" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitMediaInfoJobResponseJobsDetail;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitMediaInfoJobResponseJobsDetail;
    }
}
