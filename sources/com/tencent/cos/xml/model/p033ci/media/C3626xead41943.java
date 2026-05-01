package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.media.SubmitTranscodeJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJobResponse$SubmitTranscodeJobResponseJobsDetail$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3626xead41943 extends IXmlAdapter<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseJobsDetail> {
    private HashMap<String, ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseJobsDetail>> childElementBinders;

    public C3626xead41943() {
        HashMap<String, ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseJobsDetail>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.ERROR_CODE, new ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJobResponse$SubmitTranscodeJobResponseJobsDetail$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJobResponse.SubmitTranscodeJobResponseJobsDetail submitTranscodeJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitTranscodeJobResponseJobsDetail.code = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.ERROR_MESSAGE, new ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJobResponse$SubmitTranscodeJobResponseJobsDetail$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJobResponse.SubmitTranscodeJobResponseJobsDetail submitTranscodeJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitTranscodeJobResponseJobsDetail.message = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobId", new ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJobResponse$SubmitTranscodeJobResponseJobsDetail$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJobResponse.SubmitTranscodeJobResponseJobsDetail submitTranscodeJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitTranscodeJobResponseJobsDetail.jobId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Tag", new ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJobResponse$SubmitTranscodeJobResponseJobsDetail$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJobResponse.SubmitTranscodeJobResponseJobsDetail submitTranscodeJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitTranscodeJobResponseJobsDetail.tag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJobResponse$SubmitTranscodeJobResponseJobsDetail$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJobResponse.SubmitTranscodeJobResponseJobsDetail submitTranscodeJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitTranscodeJobResponseJobsDetail.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Progress", new ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJobResponse$SubmitTranscodeJobResponseJobsDetail$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJobResponse.SubmitTranscodeJobResponseJobsDetail submitTranscodeJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitTranscodeJobResponseJobsDetail.progress = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreationTime", new ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJobResponse$SubmitTranscodeJobResponseJobsDetail$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJobResponse.SubmitTranscodeJobResponseJobsDetail submitTranscodeJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitTranscodeJobResponseJobsDetail.creationTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("StartTime", new ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJobResponse$SubmitTranscodeJobResponseJobsDetail$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJobResponse.SubmitTranscodeJobResponseJobsDetail submitTranscodeJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitTranscodeJobResponseJobsDetail.startTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("EndTime", new ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJobResponse$SubmitTranscodeJobResponseJobsDetail$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJobResponse.SubmitTranscodeJobResponseJobsDetail submitTranscodeJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitTranscodeJobResponseJobsDetail.endTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("QueueId", new ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJobResponse$SubmitTranscodeJobResponseJobsDetail$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJobResponse.SubmitTranscodeJobResponseJobsDetail submitTranscodeJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitTranscodeJobResponseJobsDetail.queueId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Input", new ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJobResponse$SubmitTranscodeJobResponseJobsDetail$$XmlAdapter.11
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJobResponse.SubmitTranscodeJobResponseJobsDetail submitTranscodeJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                submitTranscodeJobResponseJobsDetail.input = (SubmitTranscodeJobResponse.SubmitTranscodeJobResponseInput) QCloudXml.fromXml(xmlPullParser, SubmitTranscodeJobResponse.SubmitTranscodeJobResponseInput.class, "Input");
            }
        });
        this.childElementBinders.put("Operation", new ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJobResponse$SubmitTranscodeJobResponseJobsDetail$$XmlAdapter.12
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJobResponse.SubmitTranscodeJobResponseJobsDetail submitTranscodeJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                submitTranscodeJobResponseJobsDetail.operation = (SubmitTranscodeJobResponse.SubmitTranscodeJobResponseOperation) QCloudXml.fromXml(xmlPullParser, SubmitTranscodeJobResponse.SubmitTranscodeJobResponseOperation.class, "Operation");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitTranscodeJobResponse.SubmitTranscodeJobResponseJobsDetail fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitTranscodeJobResponse.SubmitTranscodeJobResponseJobsDetail submitTranscodeJobResponseJobsDetail = new SubmitTranscodeJobResponse.SubmitTranscodeJobResponseJobsDetail();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseJobsDetail> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitTranscodeJobResponseJobsDetail, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "JobsDetail" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitTranscodeJobResponseJobsDetail;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitTranscodeJobResponseJobsDetail;
    }
}
