package com.tencent.cos.xml.model.p033ci.audit;

import com.tencent.cos.xml.model.p033ci.audit.PostLiveVideoAuditResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.audit.PostLiveVideoAuditResponse$PostLiveVideoAuditResponseJobsDetail$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3316x740339c3 extends IXmlAdapter<PostLiveVideoAuditResponse.PostLiveVideoAuditResponseJobsDetail> {
    private HashMap<String, ChildElementBinder<PostLiveVideoAuditResponse.PostLiveVideoAuditResponseJobsDetail>> childElementBinders;

    public C3316x740339c3() {
        HashMap<String, ChildElementBinder<PostLiveVideoAuditResponse.PostLiveVideoAuditResponseJobsDetail>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("DataId", new ChildElementBinder<PostLiveVideoAuditResponse.PostLiveVideoAuditResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.audit.PostLiveVideoAuditResponse$PostLiveVideoAuditResponseJobsDetail$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostLiveVideoAuditResponse.PostLiveVideoAuditResponseJobsDetail postLiveVideoAuditResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postLiveVideoAuditResponseJobsDetail.dataId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobId", new ChildElementBinder<PostLiveVideoAuditResponse.PostLiveVideoAuditResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.audit.PostLiveVideoAuditResponse$PostLiveVideoAuditResponseJobsDetail$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostLiveVideoAuditResponse.PostLiveVideoAuditResponseJobsDetail postLiveVideoAuditResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postLiveVideoAuditResponseJobsDetail.jobId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<PostLiveVideoAuditResponse.PostLiveVideoAuditResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.audit.PostLiveVideoAuditResponse$PostLiveVideoAuditResponseJobsDetail$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostLiveVideoAuditResponse.PostLiveVideoAuditResponseJobsDetail postLiveVideoAuditResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postLiveVideoAuditResponseJobsDetail.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreationTime", new ChildElementBinder<PostLiveVideoAuditResponse.PostLiveVideoAuditResponseJobsDetail>() { // from class: com.tencent.cos.xml.model.ci.audit.PostLiveVideoAuditResponse$PostLiveVideoAuditResponseJobsDetail$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostLiveVideoAuditResponse.PostLiveVideoAuditResponseJobsDetail postLiveVideoAuditResponseJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postLiveVideoAuditResponseJobsDetail.creationTime = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostLiveVideoAuditResponse.PostLiveVideoAuditResponseJobsDetail fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostLiveVideoAuditResponse.PostLiveVideoAuditResponseJobsDetail postLiveVideoAuditResponseJobsDetail = new PostLiveVideoAuditResponse.PostLiveVideoAuditResponseJobsDetail();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostLiveVideoAuditResponse.PostLiveVideoAuditResponseJobsDetail> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postLiveVideoAuditResponseJobsDetail, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "JobsDetail" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postLiveVideoAuditResponseJobsDetail;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postLiveVideoAuditResponseJobsDetail;
    }
}
