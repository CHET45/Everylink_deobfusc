package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.media.SubmitSnapshotJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitSnapshotJobResponse$SubmitSnapshotJobResponseJobsDetail$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3608xbe49c65f extends IXmlAdapter<SubmitSnapshotJobResponse.SubmitSnapshotJobResponseJobsDetail> {
    private HashMap<String, ChildElementBinder<SubmitSnapshotJobResponse.SubmitSnapshotJobResponseJobsDetail>> childElementBinders;

    public C3608xbe49c65f() {
        HashMap<String, ChildElementBinder<SubmitSnapshotJobResponse.SubmitSnapshotJobResponseJobsDetail>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.ERROR_CODE, new ChildElementBinder<SubmitSnapshotJobResponse.SubmitSnapshotJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSnapshotJobResponse$SubmitSnapshotJobResponseJobsDetail$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSnapshotJobResponse.SubmitSnapshotJobResponseJobsDetail submitSnapshotJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSnapshotJobResponseJobsDetail.code = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.ERROR_MESSAGE, new ChildElementBinder<SubmitSnapshotJobResponse.SubmitSnapshotJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSnapshotJobResponse$SubmitSnapshotJobResponseJobsDetail$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSnapshotJobResponse.SubmitSnapshotJobResponseJobsDetail submitSnapshotJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSnapshotJobResponseJobsDetail.message = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobId", new ChildElementBinder<SubmitSnapshotJobResponse.SubmitSnapshotJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSnapshotJobResponse$SubmitSnapshotJobResponseJobsDetail$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSnapshotJobResponse.SubmitSnapshotJobResponseJobsDetail submitSnapshotJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSnapshotJobResponseJobsDetail.jobId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Tag", new ChildElementBinder<SubmitSnapshotJobResponse.SubmitSnapshotJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSnapshotJobResponse$SubmitSnapshotJobResponseJobsDetail$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSnapshotJobResponse.SubmitSnapshotJobResponseJobsDetail submitSnapshotJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSnapshotJobResponseJobsDetail.tag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<SubmitSnapshotJobResponse.SubmitSnapshotJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSnapshotJobResponse$SubmitSnapshotJobResponseJobsDetail$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSnapshotJobResponse.SubmitSnapshotJobResponseJobsDetail submitSnapshotJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSnapshotJobResponseJobsDetail.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreationTime", new ChildElementBinder<SubmitSnapshotJobResponse.SubmitSnapshotJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSnapshotJobResponse$SubmitSnapshotJobResponseJobsDetail$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSnapshotJobResponse.SubmitSnapshotJobResponseJobsDetail submitSnapshotJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSnapshotJobResponseJobsDetail.creationTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("StartTime", new ChildElementBinder<SubmitSnapshotJobResponse.SubmitSnapshotJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSnapshotJobResponse$SubmitSnapshotJobResponseJobsDetail$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSnapshotJobResponse.SubmitSnapshotJobResponseJobsDetail submitSnapshotJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSnapshotJobResponseJobsDetail.startTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("EndTime", new ChildElementBinder<SubmitSnapshotJobResponse.SubmitSnapshotJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSnapshotJobResponse$SubmitSnapshotJobResponseJobsDetail$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSnapshotJobResponse.SubmitSnapshotJobResponseJobsDetail submitSnapshotJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSnapshotJobResponseJobsDetail.endTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("QueueId", new ChildElementBinder<SubmitSnapshotJobResponse.SubmitSnapshotJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSnapshotJobResponse$SubmitSnapshotJobResponseJobsDetail$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSnapshotJobResponse.SubmitSnapshotJobResponseJobsDetail submitSnapshotJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSnapshotJobResponseJobsDetail.queueId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Input", new ChildElementBinder<SubmitSnapshotJobResponse.SubmitSnapshotJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSnapshotJobResponse$SubmitSnapshotJobResponseJobsDetail$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSnapshotJobResponse.SubmitSnapshotJobResponseJobsDetail submitSnapshotJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                submitSnapshotJobResponseJobsDetail.input = (SubmitSnapshotJobResponse.SubmitSnapshotJobResponseInput) QCloudXml.fromXml(xmlPullParser, SubmitSnapshotJobResponse.SubmitSnapshotJobResponseInput.class, "Input");
            }
        });
        this.childElementBinders.put("Operation", new ChildElementBinder<SubmitSnapshotJobResponse.SubmitSnapshotJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSnapshotJobResponse$SubmitSnapshotJobResponseJobsDetail$$XmlAdapter.11
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSnapshotJobResponse.SubmitSnapshotJobResponseJobsDetail submitSnapshotJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                submitSnapshotJobResponseJobsDetail.operation = (SubmitSnapshotJobResponse.SubmitSnapshotJobResponseOperation) QCloudXml.fromXml(xmlPullParser, SubmitSnapshotJobResponse.SubmitSnapshotJobResponseOperation.class, "Operation");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitSnapshotJobResponse.SubmitSnapshotJobResponseJobsDetail fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitSnapshotJobResponse.SubmitSnapshotJobResponseJobsDetail submitSnapshotJobResponseJobsDetail = new SubmitSnapshotJobResponse.SubmitSnapshotJobResponseJobsDetail();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitSnapshotJobResponse.SubmitSnapshotJobResponseJobsDetail> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitSnapshotJobResponseJobsDetail, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "JobsDetail" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitSnapshotJobResponseJobsDetail;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitSnapshotJobResponseJobsDetail;
    }
}
