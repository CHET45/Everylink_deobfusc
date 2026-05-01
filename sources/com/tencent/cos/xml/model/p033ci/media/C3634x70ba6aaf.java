package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.media.SubmitVideoMontageJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitVideoMontageJobResponse$SubmitVideoMontageJobResponseJobsDetail$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3634x70ba6aaf extends IXmlAdapter<SubmitVideoMontageJobResponse.SubmitVideoMontageJobResponseJobsDetail> {
    private HashMap<String, ChildElementBinder<SubmitVideoMontageJobResponse.SubmitVideoMontageJobResponseJobsDetail>> childElementBinders;

    public C3634x70ba6aaf() {
        HashMap<String, ChildElementBinder<SubmitVideoMontageJobResponse.SubmitVideoMontageJobResponseJobsDetail>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.ERROR_CODE, new ChildElementBinder<SubmitVideoMontageJobResponse.SubmitVideoMontageJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoMontageJobResponse$SubmitVideoMontageJobResponseJobsDetail$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoMontageJobResponse.SubmitVideoMontageJobResponseJobsDetail submitVideoMontageJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVideoMontageJobResponseJobsDetail.code = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.ERROR_MESSAGE, new ChildElementBinder<SubmitVideoMontageJobResponse.SubmitVideoMontageJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoMontageJobResponse$SubmitVideoMontageJobResponseJobsDetail$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoMontageJobResponse.SubmitVideoMontageJobResponseJobsDetail submitVideoMontageJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVideoMontageJobResponseJobsDetail.message = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobId", new ChildElementBinder<SubmitVideoMontageJobResponse.SubmitVideoMontageJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoMontageJobResponse$SubmitVideoMontageJobResponseJobsDetail$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoMontageJobResponse.SubmitVideoMontageJobResponseJobsDetail submitVideoMontageJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVideoMontageJobResponseJobsDetail.jobId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Tag", new ChildElementBinder<SubmitVideoMontageJobResponse.SubmitVideoMontageJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoMontageJobResponse$SubmitVideoMontageJobResponseJobsDetail$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoMontageJobResponse.SubmitVideoMontageJobResponseJobsDetail submitVideoMontageJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVideoMontageJobResponseJobsDetail.tag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<SubmitVideoMontageJobResponse.SubmitVideoMontageJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoMontageJobResponse$SubmitVideoMontageJobResponseJobsDetail$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoMontageJobResponse.SubmitVideoMontageJobResponseJobsDetail submitVideoMontageJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVideoMontageJobResponseJobsDetail.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreationTime", new ChildElementBinder<SubmitVideoMontageJobResponse.SubmitVideoMontageJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoMontageJobResponse$SubmitVideoMontageJobResponseJobsDetail$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoMontageJobResponse.SubmitVideoMontageJobResponseJobsDetail submitVideoMontageJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVideoMontageJobResponseJobsDetail.creationTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("StartTime", new ChildElementBinder<SubmitVideoMontageJobResponse.SubmitVideoMontageJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoMontageJobResponse$SubmitVideoMontageJobResponseJobsDetail$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoMontageJobResponse.SubmitVideoMontageJobResponseJobsDetail submitVideoMontageJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVideoMontageJobResponseJobsDetail.startTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("EndTime", new ChildElementBinder<SubmitVideoMontageJobResponse.SubmitVideoMontageJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoMontageJobResponse$SubmitVideoMontageJobResponseJobsDetail$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoMontageJobResponse.SubmitVideoMontageJobResponseJobsDetail submitVideoMontageJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVideoMontageJobResponseJobsDetail.endTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("QueueId", new ChildElementBinder<SubmitVideoMontageJobResponse.SubmitVideoMontageJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoMontageJobResponse$SubmitVideoMontageJobResponseJobsDetail$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoMontageJobResponse.SubmitVideoMontageJobResponseJobsDetail submitVideoMontageJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVideoMontageJobResponseJobsDetail.queueId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Input", new ChildElementBinder<SubmitVideoMontageJobResponse.SubmitVideoMontageJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoMontageJobResponse$SubmitVideoMontageJobResponseJobsDetail$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoMontageJobResponse.SubmitVideoMontageJobResponseJobsDetail submitVideoMontageJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                submitVideoMontageJobResponseJobsDetail.input = (SubmitVideoMontageJobResponse.SubmitVideoMontageJobResponseInput) QCloudXml.fromXml(xmlPullParser, SubmitVideoMontageJobResponse.SubmitVideoMontageJobResponseInput.class, "Input");
            }
        });
        this.childElementBinders.put("Operation", new ChildElementBinder<SubmitVideoMontageJobResponse.SubmitVideoMontageJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoMontageJobResponse$SubmitVideoMontageJobResponseJobsDetail$$XmlAdapter.11
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoMontageJobResponse.SubmitVideoMontageJobResponseJobsDetail submitVideoMontageJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                submitVideoMontageJobResponseJobsDetail.operation = (SubmitVideoMontageJobResponse.SubmitVideoMontageJobResponseOperation) QCloudXml.fromXml(xmlPullParser, SubmitVideoMontageJobResponse.SubmitVideoMontageJobResponseOperation.class, "Operation");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitVideoMontageJobResponse.SubmitVideoMontageJobResponseJobsDetail fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitVideoMontageJobResponse.SubmitVideoMontageJobResponseJobsDetail submitVideoMontageJobResponseJobsDetail = new SubmitVideoMontageJobResponse.SubmitVideoMontageJobResponseJobsDetail();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitVideoMontageJobResponse.SubmitVideoMontageJobResponseJobsDetail> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitVideoMontageJobResponseJobsDetail, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "JobsDetail" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitVideoMontageJobResponseJobsDetail;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitVideoMontageJobResponseJobsDetail;
    }
}
