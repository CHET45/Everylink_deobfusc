package com.tencent.cos.xml.model.p033ci.asr.bean;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.asr.bean.SpeechJobsDetail;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class SpeechJobsDetail$$XmlAdapter extends IXmlAdapter<SpeechJobsDetail> {
    private HashMap<String, ChildElementBinder<SpeechJobsDetail>> childElementBinders;

    public SpeechJobsDetail$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SpeechJobsDetail>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("JobId", new ChildElementBinder<SpeechJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail speechJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechJobsDetail.jobId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<SpeechJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail speechJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechJobsDetail.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreationTime", new ChildElementBinder<SpeechJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail speechJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechJobsDetail.creationTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("StartTime", new ChildElementBinder<SpeechJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail speechJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechJobsDetail.startTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("EndTime", new ChildElementBinder<SpeechJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail speechJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechJobsDetail.endTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.ERROR_CODE, new ChildElementBinder<SpeechJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail speechJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechJobsDetail.code = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.ERROR_MESSAGE, new ChildElementBinder<SpeechJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail speechJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechJobsDetail.message = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Tag", new ChildElementBinder<SpeechJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail speechJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechJobsDetail.tag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("QueueId", new ChildElementBinder<SpeechJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail speechJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechJobsDetail.queueId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Input", new ChildElementBinder<SpeechJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail speechJobsDetail, String str) throws XmlPullParserException, IOException {
                speechJobsDetail.input = (SpeechJobsDetail.SpeechJobsDetailInput) QCloudXml.fromXml(xmlPullParser, SpeechJobsDetail.SpeechJobsDetailInput.class, "Input");
            }
        });
        this.childElementBinders.put("Operation", new ChildElementBinder<SpeechJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$$XmlAdapter.11
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail speechJobsDetail, String str) throws XmlPullParserException, IOException {
                speechJobsDetail.operation = (SpeechJobsDetail.SpeechJobsDetailOperation) QCloudXml.fromXml(xmlPullParser, SpeechJobsDetail.SpeechJobsDetailOperation.class, "Operation");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SpeechJobsDetail fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SpeechJobsDetail speechJobsDetail = new SpeechJobsDetail();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SpeechJobsDetail> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, speechJobsDetail, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "JobsDetail" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return speechJobsDetail;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return speechJobsDetail;
    }
}
