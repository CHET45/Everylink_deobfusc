package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.media.SubmitDigitalWatermarkJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitDigitalWatermarkJobResponse$SubmitDigitalWatermarkJobResponseJobsDetail$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3562x1a1b4d8f extends IXmlAdapter<SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseJobsDetail> {
    private HashMap<String, ChildElementBinder<SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseJobsDetail>> childElementBinders;

    public C3562x1a1b4d8f() {
        HashMap<String, ChildElementBinder<SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseJobsDetail>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.ERROR_CODE, new ChildElementBinder<SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitDigitalWatermarkJobResponse$SubmitDigitalWatermarkJobResponseJobsDetail$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseJobsDetail submitDigitalWatermarkJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitDigitalWatermarkJobResponseJobsDetail.code = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.ERROR_MESSAGE, new ChildElementBinder<SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitDigitalWatermarkJobResponse$SubmitDigitalWatermarkJobResponseJobsDetail$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseJobsDetail submitDigitalWatermarkJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitDigitalWatermarkJobResponseJobsDetail.message = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobId", new ChildElementBinder<SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitDigitalWatermarkJobResponse$SubmitDigitalWatermarkJobResponseJobsDetail$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseJobsDetail submitDigitalWatermarkJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitDigitalWatermarkJobResponseJobsDetail.jobId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Tag", new ChildElementBinder<SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitDigitalWatermarkJobResponse$SubmitDigitalWatermarkJobResponseJobsDetail$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseJobsDetail submitDigitalWatermarkJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitDigitalWatermarkJobResponseJobsDetail.tag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitDigitalWatermarkJobResponse$SubmitDigitalWatermarkJobResponseJobsDetail$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseJobsDetail submitDigitalWatermarkJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitDigitalWatermarkJobResponseJobsDetail.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreationTime", new ChildElementBinder<SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitDigitalWatermarkJobResponse$SubmitDigitalWatermarkJobResponseJobsDetail$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseJobsDetail submitDigitalWatermarkJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitDigitalWatermarkJobResponseJobsDetail.creationTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("EndTime", new ChildElementBinder<SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitDigitalWatermarkJobResponse$SubmitDigitalWatermarkJobResponseJobsDetail$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseJobsDetail submitDigitalWatermarkJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitDigitalWatermarkJobResponseJobsDetail.endTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("QueueId", new ChildElementBinder<SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitDigitalWatermarkJobResponse$SubmitDigitalWatermarkJobResponseJobsDetail$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseJobsDetail submitDigitalWatermarkJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitDigitalWatermarkJobResponseJobsDetail.queueId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Input", new ChildElementBinder<SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitDigitalWatermarkJobResponse$SubmitDigitalWatermarkJobResponseJobsDetail$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseJobsDetail submitDigitalWatermarkJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                submitDigitalWatermarkJobResponseJobsDetail.input = (SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseInput) QCloudXml.fromXml(xmlPullParser, SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseInput.class, "Input");
            }
        });
        this.childElementBinders.put("Operation", new ChildElementBinder<SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitDigitalWatermarkJobResponse$SubmitDigitalWatermarkJobResponseJobsDetail$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseJobsDetail submitDigitalWatermarkJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                submitDigitalWatermarkJobResponseJobsDetail.operation = (SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseOperation) QCloudXml.fromXml(xmlPullParser, SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseOperation.class, "Operation");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseJobsDetail fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseJobsDetail submitDigitalWatermarkJobResponseJobsDetail = new SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseJobsDetail();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseJobsDetail> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitDigitalWatermarkJobResponseJobsDetail, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "JobsDetail" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitDigitalWatermarkJobResponseJobsDetail;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitDigitalWatermarkJobResponseJobsDetail;
    }
}
