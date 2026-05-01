package com.tencent.cos.xml.model.p033ci.p034ai;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.p034ai.VocalScore;
import com.tencent.cos.xml.model.p033ci.p034ai.VocalScoreResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class VocalScoreResponse$VocalScoreResponseJobsDetail$$XmlAdapter extends IXmlAdapter<VocalScoreResponse.VocalScoreResponseJobsDetail> {
    private HashMap<String, ChildElementBinder<VocalScoreResponse.VocalScoreResponseJobsDetail>> childElementBinders;

    public VocalScoreResponse$VocalScoreResponseJobsDetail$$XmlAdapter() {
        HashMap<String, ChildElementBinder<VocalScoreResponse.VocalScoreResponseJobsDetail>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.ERROR_CODE, new ChildElementBinder<VocalScoreResponse.VocalScoreResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.VocalScoreResponse$VocalScoreResponseJobsDetail$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VocalScoreResponse.VocalScoreResponseJobsDetail vocalScoreResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                vocalScoreResponseJobsDetail.code = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.ERROR_MESSAGE, new ChildElementBinder<VocalScoreResponse.VocalScoreResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.VocalScoreResponse$VocalScoreResponseJobsDetail$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VocalScoreResponse.VocalScoreResponseJobsDetail vocalScoreResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                vocalScoreResponseJobsDetail.message = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobId", new ChildElementBinder<VocalScoreResponse.VocalScoreResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.VocalScoreResponse$VocalScoreResponseJobsDetail$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VocalScoreResponse.VocalScoreResponseJobsDetail vocalScoreResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                vocalScoreResponseJobsDetail.jobId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Tag", new ChildElementBinder<VocalScoreResponse.VocalScoreResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.VocalScoreResponse$VocalScoreResponseJobsDetail$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VocalScoreResponse.VocalScoreResponseJobsDetail vocalScoreResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                vocalScoreResponseJobsDetail.tag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<VocalScoreResponse.VocalScoreResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.VocalScoreResponse$VocalScoreResponseJobsDetail$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VocalScoreResponse.VocalScoreResponseJobsDetail vocalScoreResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                vocalScoreResponseJobsDetail.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreationTime", new ChildElementBinder<VocalScoreResponse.VocalScoreResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.VocalScoreResponse$VocalScoreResponseJobsDetail$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VocalScoreResponse.VocalScoreResponseJobsDetail vocalScoreResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                vocalScoreResponseJobsDetail.creationTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("StartTime", new ChildElementBinder<VocalScoreResponse.VocalScoreResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.VocalScoreResponse$VocalScoreResponseJobsDetail$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VocalScoreResponse.VocalScoreResponseJobsDetail vocalScoreResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                vocalScoreResponseJobsDetail.startTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("EndTime", new ChildElementBinder<VocalScoreResponse.VocalScoreResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.VocalScoreResponse$VocalScoreResponseJobsDetail$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VocalScoreResponse.VocalScoreResponseJobsDetail vocalScoreResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                vocalScoreResponseJobsDetail.endTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("QueueId", new ChildElementBinder<VocalScoreResponse.VocalScoreResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.VocalScoreResponse$VocalScoreResponseJobsDetail$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VocalScoreResponse.VocalScoreResponseJobsDetail vocalScoreResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                vocalScoreResponseJobsDetail.queueId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Input", new ChildElementBinder<VocalScoreResponse.VocalScoreResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.VocalScoreResponse$VocalScoreResponseJobsDetail$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VocalScoreResponse.VocalScoreResponseJobsDetail vocalScoreResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                vocalScoreResponseJobsDetail.input = (VocalScore.VocalScoreInput) QCloudXml.fromXml(xmlPullParser, VocalScore.VocalScoreInput.class, "Input");
            }
        });
        this.childElementBinders.put("Operation", new ChildElementBinder<VocalScoreResponse.VocalScoreResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.VocalScoreResponse$VocalScoreResponseJobsDetail$$XmlAdapter.11
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VocalScoreResponse.VocalScoreResponseJobsDetail vocalScoreResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                vocalScoreResponseJobsDetail.operation = (VocalScoreResponse.VocalScoreResponseOperation) QCloudXml.fromXml(xmlPullParser, VocalScoreResponse.VocalScoreResponseOperation.class, "Operation");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public VocalScoreResponse.VocalScoreResponseJobsDetail fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        VocalScoreResponse.VocalScoreResponseJobsDetail vocalScoreResponseJobsDetail = new VocalScoreResponse.VocalScoreResponseJobsDetail();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<VocalScoreResponse.VocalScoreResponseJobsDetail> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, vocalScoreResponseJobsDetail, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "JobsDetail" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return vocalScoreResponseJobsDetail;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return vocalScoreResponseJobsDetail;
    }
}
