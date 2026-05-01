package com.tencent.cos.xml.model.p033ci.p034ai;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.p034ai.PostVoiceSynthesisResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.ai.PostVoiceSynthesisResponse$PostVoiceSynthesisResponseJobsDetail$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3050xac554403 extends IXmlAdapter<PostVoiceSynthesisResponse.PostVoiceSynthesisResponseJobsDetail> {
    private HashMap<String, ChildElementBinder<PostVoiceSynthesisResponse.PostVoiceSynthesisResponseJobsDetail>> childElementBinders;

    public C3050xac554403() {
        HashMap<String, ChildElementBinder<PostVoiceSynthesisResponse.PostVoiceSynthesisResponseJobsDetail>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.ERROR_CODE, new ChildElementBinder<PostVoiceSynthesisResponse.PostVoiceSynthesisResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVoiceSynthesisResponse$PostVoiceSynthesisResponseJobsDetail$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVoiceSynthesisResponse.PostVoiceSynthesisResponseJobsDetail postVoiceSynthesisResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postVoiceSynthesisResponseJobsDetail.code = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.ERROR_MESSAGE, new ChildElementBinder<PostVoiceSynthesisResponse.PostVoiceSynthesisResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVoiceSynthesisResponse$PostVoiceSynthesisResponseJobsDetail$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVoiceSynthesisResponse.PostVoiceSynthesisResponseJobsDetail postVoiceSynthesisResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postVoiceSynthesisResponseJobsDetail.message = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobId", new ChildElementBinder<PostVoiceSynthesisResponse.PostVoiceSynthesisResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVoiceSynthesisResponse$PostVoiceSynthesisResponseJobsDetail$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVoiceSynthesisResponse.PostVoiceSynthesisResponseJobsDetail postVoiceSynthesisResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postVoiceSynthesisResponseJobsDetail.jobId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Tag", new ChildElementBinder<PostVoiceSynthesisResponse.PostVoiceSynthesisResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVoiceSynthesisResponse$PostVoiceSynthesisResponseJobsDetail$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVoiceSynthesisResponse.PostVoiceSynthesisResponseJobsDetail postVoiceSynthesisResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postVoiceSynthesisResponseJobsDetail.tag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<PostVoiceSynthesisResponse.PostVoiceSynthesisResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVoiceSynthesisResponse$PostVoiceSynthesisResponseJobsDetail$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVoiceSynthesisResponse.PostVoiceSynthesisResponseJobsDetail postVoiceSynthesisResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postVoiceSynthesisResponseJobsDetail.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreationTime", new ChildElementBinder<PostVoiceSynthesisResponse.PostVoiceSynthesisResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVoiceSynthesisResponse$PostVoiceSynthesisResponseJobsDetail$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVoiceSynthesisResponse.PostVoiceSynthesisResponseJobsDetail postVoiceSynthesisResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postVoiceSynthesisResponseJobsDetail.creationTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("StartTime", new ChildElementBinder<PostVoiceSynthesisResponse.PostVoiceSynthesisResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVoiceSynthesisResponse$PostVoiceSynthesisResponseJobsDetail$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVoiceSynthesisResponse.PostVoiceSynthesisResponseJobsDetail postVoiceSynthesisResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postVoiceSynthesisResponseJobsDetail.startTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("EndTime", new ChildElementBinder<PostVoiceSynthesisResponse.PostVoiceSynthesisResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVoiceSynthesisResponse$PostVoiceSynthesisResponseJobsDetail$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVoiceSynthesisResponse.PostVoiceSynthesisResponseJobsDetail postVoiceSynthesisResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postVoiceSynthesisResponseJobsDetail.endTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("QueueId", new ChildElementBinder<PostVoiceSynthesisResponse.PostVoiceSynthesisResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVoiceSynthesisResponse$PostVoiceSynthesisResponseJobsDetail$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVoiceSynthesisResponse.PostVoiceSynthesisResponseJobsDetail postVoiceSynthesisResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postVoiceSynthesisResponseJobsDetail.queueId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Operation", new ChildElementBinder<PostVoiceSynthesisResponse.PostVoiceSynthesisResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVoiceSynthesisResponse$PostVoiceSynthesisResponseJobsDetail$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVoiceSynthesisResponse.PostVoiceSynthesisResponseJobsDetail postVoiceSynthesisResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                postVoiceSynthesisResponseJobsDetail.operation = (PostVoiceSynthesisResponse.PostVoiceSynthesisResponseOperation) QCloudXml.fromXml(xmlPullParser, PostVoiceSynthesisResponse.PostVoiceSynthesisResponseOperation.class, "Operation");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostVoiceSynthesisResponse.PostVoiceSynthesisResponseJobsDetail fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostVoiceSynthesisResponse.PostVoiceSynthesisResponseJobsDetail postVoiceSynthesisResponseJobsDetail = new PostVoiceSynthesisResponse.PostVoiceSynthesisResponseJobsDetail();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostVoiceSynthesisResponse.PostVoiceSynthesisResponseJobsDetail> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postVoiceSynthesisResponseJobsDetail, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "JobsDetail" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postVoiceSynthesisResponseJobsDetail;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postVoiceSynthesisResponseJobsDetail;
    }
}
