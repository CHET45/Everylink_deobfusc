package com.tencent.cos.xml.model.tag.audit.post;

import com.tencent.cos.xml.model.tag.audit.bean.BaseAuditJobsDetail;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class PostAuditJobResponse$$XmlAdapter extends IXmlAdapter<PostAuditJobResponse> {
    private HashMap<String, ChildElementBinder<PostAuditJobResponse>> childElementBinders;

    public PostAuditJobResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<PostAuditJobResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("JobsDetail", new ChildElementBinder<PostAuditJobResponse>() { // from class: com.tencent.cos.xml.model.tag.audit.post.PostAuditJobResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostAuditJobResponse postAuditJobResponse, String str) throws XmlPullParserException, IOException {
                postAuditJobResponse.jobsDetail = (BaseAuditJobsDetail) QCloudXml.fromXml(xmlPullParser, BaseAuditJobsDetail.class, "JobsDetail");
            }
        });
        this.childElementBinders.put("RequestId", new ChildElementBinder<PostAuditJobResponse>() { // from class: com.tencent.cos.xml.model.tag.audit.post.PostAuditJobResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostAuditJobResponse postAuditJobResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postAuditJobResponse.requestId = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostAuditJobResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostAuditJobResponse postAuditJobResponse = new PostAuditJobResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostAuditJobResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postAuditJobResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postAuditJobResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postAuditJobResponse;
    }
}
