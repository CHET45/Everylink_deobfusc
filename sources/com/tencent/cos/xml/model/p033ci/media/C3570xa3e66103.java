package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.media.SubmitExtractDigitalWatermarkJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitExtractDigitalWatermarkJobResponse$SubmitExtractDigitalWatermarkJobResponseJobsDetail$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3570xa3e66103 extends IXmlAdapter<SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseJobsDetail> {
    private HashMap<String, ChildElementBinder<SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseJobsDetail>> childElementBinders;

    public C3570xa3e66103() {
        HashMap<String, ChildElementBinder<SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseJobsDetail>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.ERROR_CODE, new ChildElementBinder<SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitExtractDigitalWatermarkJobResponse$SubmitExtractDigitalWatermarkJobResponseJobsDetail$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseJobsDetail submitExtractDigitalWatermarkJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitExtractDigitalWatermarkJobResponseJobsDetail.code = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.ERROR_MESSAGE, new ChildElementBinder<SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitExtractDigitalWatermarkJobResponse$SubmitExtractDigitalWatermarkJobResponseJobsDetail$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseJobsDetail submitExtractDigitalWatermarkJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitExtractDigitalWatermarkJobResponseJobsDetail.message = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobId", new ChildElementBinder<SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitExtractDigitalWatermarkJobResponse$SubmitExtractDigitalWatermarkJobResponseJobsDetail$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseJobsDetail submitExtractDigitalWatermarkJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitExtractDigitalWatermarkJobResponseJobsDetail.jobId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Tag", new ChildElementBinder<SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitExtractDigitalWatermarkJobResponse$SubmitExtractDigitalWatermarkJobResponseJobsDetail$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseJobsDetail submitExtractDigitalWatermarkJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitExtractDigitalWatermarkJobResponseJobsDetail.tag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitExtractDigitalWatermarkJobResponse$SubmitExtractDigitalWatermarkJobResponseJobsDetail$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseJobsDetail submitExtractDigitalWatermarkJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitExtractDigitalWatermarkJobResponseJobsDetail.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreationTime", new ChildElementBinder<SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitExtractDigitalWatermarkJobResponse$SubmitExtractDigitalWatermarkJobResponseJobsDetail$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseJobsDetail submitExtractDigitalWatermarkJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitExtractDigitalWatermarkJobResponseJobsDetail.creationTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("EndTime", new ChildElementBinder<SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitExtractDigitalWatermarkJobResponse$SubmitExtractDigitalWatermarkJobResponseJobsDetail$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseJobsDetail submitExtractDigitalWatermarkJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitExtractDigitalWatermarkJobResponseJobsDetail.endTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("QueueId", new ChildElementBinder<SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitExtractDigitalWatermarkJobResponse$SubmitExtractDigitalWatermarkJobResponseJobsDetail$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseJobsDetail submitExtractDigitalWatermarkJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitExtractDigitalWatermarkJobResponseJobsDetail.queueId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Input", new ChildElementBinder<SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitExtractDigitalWatermarkJobResponse$SubmitExtractDigitalWatermarkJobResponseJobsDetail$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseJobsDetail submitExtractDigitalWatermarkJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                submitExtractDigitalWatermarkJobResponseJobsDetail.input = (SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseInput) QCloudXml.fromXml(xmlPullParser, SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseInput.class, "Input");
            }
        });
        this.childElementBinders.put("Operation", new ChildElementBinder<SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitExtractDigitalWatermarkJobResponse$SubmitExtractDigitalWatermarkJobResponseJobsDetail$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseJobsDetail submitExtractDigitalWatermarkJobResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                submitExtractDigitalWatermarkJobResponseJobsDetail.operation = (SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseOperation) QCloudXml.fromXml(xmlPullParser, SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseOperation.class, "Operation");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseJobsDetail fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseJobsDetail submitExtractDigitalWatermarkJobResponseJobsDetail = new SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseJobsDetail();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseJobsDetail> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitExtractDigitalWatermarkJobResponseJobsDetail, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "JobsDetail" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitExtractDigitalWatermarkJobResponseJobsDetail;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitExtractDigitalWatermarkJobResponseJobsDetail;
    }
}
