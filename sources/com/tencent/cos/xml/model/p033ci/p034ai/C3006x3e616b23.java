package com.tencent.cos.xml.model.p033ci.p034ai;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.p034ai.PostSoundHound;
import com.tencent.cos.xml.model.p033ci.p034ai.PostSoundHoundResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.ai.PostSoundHoundResponse$PostSoundHoundResponseJobsDetail$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3006x3e616b23 extends IXmlAdapter<PostSoundHoundResponse.PostSoundHoundResponseJobsDetail> {
    private HashMap<String, ChildElementBinder<PostSoundHoundResponse.PostSoundHoundResponseJobsDetail>> childElementBinders;

    public C3006x3e616b23() {
        HashMap<String, ChildElementBinder<PostSoundHoundResponse.PostSoundHoundResponseJobsDetail>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.ERROR_CODE, new ChildElementBinder<PostSoundHoundResponse.PostSoundHoundResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSoundHoundResponse$PostSoundHoundResponseJobsDetail$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSoundHoundResponse.PostSoundHoundResponseJobsDetail postSoundHoundResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postSoundHoundResponseJobsDetail.code = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.ERROR_MESSAGE, new ChildElementBinder<PostSoundHoundResponse.PostSoundHoundResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSoundHoundResponse$PostSoundHoundResponseJobsDetail$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSoundHoundResponse.PostSoundHoundResponseJobsDetail postSoundHoundResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postSoundHoundResponseJobsDetail.message = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobId", new ChildElementBinder<PostSoundHoundResponse.PostSoundHoundResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSoundHoundResponse$PostSoundHoundResponseJobsDetail$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSoundHoundResponse.PostSoundHoundResponseJobsDetail postSoundHoundResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postSoundHoundResponseJobsDetail.jobId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Tag", new ChildElementBinder<PostSoundHoundResponse.PostSoundHoundResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSoundHoundResponse$PostSoundHoundResponseJobsDetail$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSoundHoundResponse.PostSoundHoundResponseJobsDetail postSoundHoundResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postSoundHoundResponseJobsDetail.tag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<PostSoundHoundResponse.PostSoundHoundResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSoundHoundResponse$PostSoundHoundResponseJobsDetail$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSoundHoundResponse.PostSoundHoundResponseJobsDetail postSoundHoundResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postSoundHoundResponseJobsDetail.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreationTime", new ChildElementBinder<PostSoundHoundResponse.PostSoundHoundResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSoundHoundResponse$PostSoundHoundResponseJobsDetail$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSoundHoundResponse.PostSoundHoundResponseJobsDetail postSoundHoundResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postSoundHoundResponseJobsDetail.creationTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("StartTime", new ChildElementBinder<PostSoundHoundResponse.PostSoundHoundResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSoundHoundResponse$PostSoundHoundResponseJobsDetail$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSoundHoundResponse.PostSoundHoundResponseJobsDetail postSoundHoundResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postSoundHoundResponseJobsDetail.startTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("EndTime", new ChildElementBinder<PostSoundHoundResponse.PostSoundHoundResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSoundHoundResponse$PostSoundHoundResponseJobsDetail$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSoundHoundResponse.PostSoundHoundResponseJobsDetail postSoundHoundResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postSoundHoundResponseJobsDetail.endTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("QueueId", new ChildElementBinder<PostSoundHoundResponse.PostSoundHoundResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSoundHoundResponse$PostSoundHoundResponseJobsDetail$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSoundHoundResponse.PostSoundHoundResponseJobsDetail postSoundHoundResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postSoundHoundResponseJobsDetail.queueId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Input", new ChildElementBinder<PostSoundHoundResponse.PostSoundHoundResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSoundHoundResponse$PostSoundHoundResponseJobsDetail$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSoundHoundResponse.PostSoundHoundResponseJobsDetail postSoundHoundResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                postSoundHoundResponseJobsDetail.input = (PostSoundHound.PostSoundHoundInput) QCloudXml.fromXml(xmlPullParser, PostSoundHound.PostSoundHoundInput.class, "Input");
            }
        });
        this.childElementBinders.put("Operation", new ChildElementBinder<PostSoundHoundResponse.PostSoundHoundResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSoundHoundResponse$PostSoundHoundResponseJobsDetail$$XmlAdapter.11
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSoundHoundResponse.PostSoundHoundResponseJobsDetail postSoundHoundResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                postSoundHoundResponseJobsDetail.operation = (PostSoundHoundResponse.PostSoundHoundResponseOperation) QCloudXml.fromXml(xmlPullParser, PostSoundHoundResponse.PostSoundHoundResponseOperation.class, "Operation");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostSoundHoundResponse.PostSoundHoundResponseJobsDetail fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostSoundHoundResponse.PostSoundHoundResponseJobsDetail postSoundHoundResponseJobsDetail = new PostSoundHoundResponse.PostSoundHoundResponseJobsDetail();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostSoundHoundResponse.PostSoundHoundResponseJobsDetail> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postSoundHoundResponseJobsDetail, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "JobsDetail" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postSoundHoundResponseJobsDetail;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postSoundHoundResponseJobsDetail;
    }
}
