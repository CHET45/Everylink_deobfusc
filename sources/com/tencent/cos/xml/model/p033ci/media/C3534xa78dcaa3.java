package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.media.SubmitAnimationJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitAnimationJobResponse$SubmitAnimationJobResponseJobsDetail$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3534xa78dcaa3 extends IXmlAdapter<SubmitAnimationJobResponse.SubmitAnimationJobResponseJobsDetail> {
    private HashMap<String, ChildElementBinder<SubmitAnimationJobResponse.SubmitAnimationJobResponseJobsDetail>> childElementBinders;

    public C3534xa78dcaa3() {
        HashMap<String, ChildElementBinder<SubmitAnimationJobResponse.SubmitAnimationJobResponseJobsDetail>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.ERROR_CODE, new ChildElementBinder<SubmitAnimationJobResponse.SubmitAnimationJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitAnimationJobResponse$SubmitAnimationJobResponseJobsDetail$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitAnimationJobResponse.SubmitAnimationJobResponseJobsDetail submitAnimationJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitAnimationJobResponseJobsDetail.code = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.ERROR_MESSAGE, new ChildElementBinder<SubmitAnimationJobResponse.SubmitAnimationJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitAnimationJobResponse$SubmitAnimationJobResponseJobsDetail$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitAnimationJobResponse.SubmitAnimationJobResponseJobsDetail submitAnimationJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitAnimationJobResponseJobsDetail.message = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobId", new ChildElementBinder<SubmitAnimationJobResponse.SubmitAnimationJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitAnimationJobResponse$SubmitAnimationJobResponseJobsDetail$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitAnimationJobResponse.SubmitAnimationJobResponseJobsDetail submitAnimationJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitAnimationJobResponseJobsDetail.jobId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Tag", new ChildElementBinder<SubmitAnimationJobResponse.SubmitAnimationJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitAnimationJobResponse$SubmitAnimationJobResponseJobsDetail$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitAnimationJobResponse.SubmitAnimationJobResponseJobsDetail submitAnimationJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitAnimationJobResponseJobsDetail.tag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<SubmitAnimationJobResponse.SubmitAnimationJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitAnimationJobResponse$SubmitAnimationJobResponseJobsDetail$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitAnimationJobResponse.SubmitAnimationJobResponseJobsDetail submitAnimationJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitAnimationJobResponseJobsDetail.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreationTime", new ChildElementBinder<SubmitAnimationJobResponse.SubmitAnimationJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitAnimationJobResponse$SubmitAnimationJobResponseJobsDetail$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitAnimationJobResponse.SubmitAnimationJobResponseJobsDetail submitAnimationJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitAnimationJobResponseJobsDetail.creationTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("StartTime", new ChildElementBinder<SubmitAnimationJobResponse.SubmitAnimationJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitAnimationJobResponse$SubmitAnimationJobResponseJobsDetail$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitAnimationJobResponse.SubmitAnimationJobResponseJobsDetail submitAnimationJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitAnimationJobResponseJobsDetail.startTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("EndTime", new ChildElementBinder<SubmitAnimationJobResponse.SubmitAnimationJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitAnimationJobResponse$SubmitAnimationJobResponseJobsDetail$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitAnimationJobResponse.SubmitAnimationJobResponseJobsDetail submitAnimationJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitAnimationJobResponseJobsDetail.endTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("QueueId", new ChildElementBinder<SubmitAnimationJobResponse.SubmitAnimationJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitAnimationJobResponse$SubmitAnimationJobResponseJobsDetail$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitAnimationJobResponse.SubmitAnimationJobResponseJobsDetail submitAnimationJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitAnimationJobResponseJobsDetail.queueId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Input", new ChildElementBinder<SubmitAnimationJobResponse.SubmitAnimationJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitAnimationJobResponse$SubmitAnimationJobResponseJobsDetail$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitAnimationJobResponse.SubmitAnimationJobResponseJobsDetail submitAnimationJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                submitAnimationJobResponseJobsDetail.input = (SubmitAnimationJobResponse.SubmitAnimationJobResponseInput) QCloudXml.fromXml(xmlPullParser, SubmitAnimationJobResponse.SubmitAnimationJobResponseInput.class, "Input");
            }
        });
        this.childElementBinders.put("Operation", new ChildElementBinder<SubmitAnimationJobResponse.SubmitAnimationJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitAnimationJobResponse$SubmitAnimationJobResponseJobsDetail$$XmlAdapter.11
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitAnimationJobResponse.SubmitAnimationJobResponseJobsDetail submitAnimationJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                submitAnimationJobResponseJobsDetail.operation = (SubmitAnimationJobResponse.SubmitAnimationJobResponseOperation) QCloudXml.fromXml(xmlPullParser, SubmitAnimationJobResponse.SubmitAnimationJobResponseOperation.class, "Operation");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitAnimationJobResponse.SubmitAnimationJobResponseJobsDetail fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitAnimationJobResponse.SubmitAnimationJobResponseJobsDetail submitAnimationJobResponseJobsDetail = new SubmitAnimationJobResponse.SubmitAnimationJobResponseJobsDetail();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitAnimationJobResponse.SubmitAnimationJobResponseJobsDetail> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitAnimationJobResponseJobsDetail, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "JobsDetail" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitAnimationJobResponseJobsDetail;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitAnimationJobResponseJobsDetail;
    }
}
