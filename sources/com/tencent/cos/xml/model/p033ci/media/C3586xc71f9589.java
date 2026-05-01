package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.media.SubmitMediaSegmentJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitMediaSegmentJobResponse$SubmitMediaSegmentJobResponseJobsDetail$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3586xc71f9589 extends IXmlAdapter<SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseJobsDetail> {
    private HashMap<String, ChildElementBinder<SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseJobsDetail>> childElementBinders;

    public C3586xc71f9589() {
        HashMap<String, ChildElementBinder<SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseJobsDetail>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.ERROR_CODE, new ChildElementBinder<SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaSegmentJobResponse$SubmitMediaSegmentJobResponseJobsDetail$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseJobsDetail submitMediaSegmentJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaSegmentJobResponseJobsDetail.code = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.ERROR_MESSAGE, new ChildElementBinder<SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaSegmentJobResponse$SubmitMediaSegmentJobResponseJobsDetail$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseJobsDetail submitMediaSegmentJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaSegmentJobResponseJobsDetail.message = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobId", new ChildElementBinder<SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaSegmentJobResponse$SubmitMediaSegmentJobResponseJobsDetail$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseJobsDetail submitMediaSegmentJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaSegmentJobResponseJobsDetail.jobId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Tag", new ChildElementBinder<SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaSegmentJobResponse$SubmitMediaSegmentJobResponseJobsDetail$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseJobsDetail submitMediaSegmentJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaSegmentJobResponseJobsDetail.tag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaSegmentJobResponse$SubmitMediaSegmentJobResponseJobsDetail$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseJobsDetail submitMediaSegmentJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaSegmentJobResponseJobsDetail.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreationTime", new ChildElementBinder<SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaSegmentJobResponse$SubmitMediaSegmentJobResponseJobsDetail$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseJobsDetail submitMediaSegmentJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaSegmentJobResponseJobsDetail.creationTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("StartTime", new ChildElementBinder<SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaSegmentJobResponse$SubmitMediaSegmentJobResponseJobsDetail$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseJobsDetail submitMediaSegmentJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaSegmentJobResponseJobsDetail.startTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("EndTime", new ChildElementBinder<SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaSegmentJobResponse$SubmitMediaSegmentJobResponseJobsDetail$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseJobsDetail submitMediaSegmentJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaSegmentJobResponseJobsDetail.endTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("QueueId", new ChildElementBinder<SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaSegmentJobResponse$SubmitMediaSegmentJobResponseJobsDetail$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseJobsDetail submitMediaSegmentJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaSegmentJobResponseJobsDetail.queueId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Input", new ChildElementBinder<SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaSegmentJobResponse$SubmitMediaSegmentJobResponseJobsDetail$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseJobsDetail submitMediaSegmentJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                submitMediaSegmentJobResponseJobsDetail.input = (SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseInput) QCloudXml.fromXml(xmlPullParser, SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseInput.class, "Input");
            }
        });
        this.childElementBinders.put("Operation", new ChildElementBinder<SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaSegmentJobResponse$SubmitMediaSegmentJobResponseJobsDetail$$XmlAdapter.11
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseJobsDetail submitMediaSegmentJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                submitMediaSegmentJobResponseJobsDetail.operation = (SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseOperation) QCloudXml.fromXml(xmlPullParser, SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseOperation.class, "Operation");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseJobsDetail fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseJobsDetail submitMediaSegmentJobResponseJobsDetail = new SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseJobsDetail();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseJobsDetail> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitMediaSegmentJobResponseJobsDetail, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "JobsDetail" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitMediaSegmentJobResponseJobsDetail;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitMediaSegmentJobResponseJobsDetail;
    }
}
