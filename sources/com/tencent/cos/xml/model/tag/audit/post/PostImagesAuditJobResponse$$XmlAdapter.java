package com.tencent.cos.xml.model.tag.audit.post;

import com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class PostImagesAuditJobResponse$$XmlAdapter extends IXmlAdapter<PostImagesAuditJobResponse> {
    private HashMap<String, ChildElementBinder<PostImagesAuditJobResponse>> childElementBinders;

    public PostImagesAuditJobResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<PostImagesAuditJobResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("JobsDetail", new ChildElementBinder<PostImagesAuditJobResponse>() { // from class: com.tencent.cos.xml.model.tag.audit.post.PostImagesAuditJobResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostImagesAuditJobResponse postImagesAuditJobResponse, String str) throws XmlPullParserException, IOException {
                if (postImagesAuditJobResponse.jobsDetail == null) {
                    postImagesAuditJobResponse.jobsDetail = new ArrayList();
                }
                postImagesAuditJobResponse.jobsDetail.add((ImageAuditJobsDetail) QCloudXml.fromXml(xmlPullParser, ImageAuditJobsDetail.class, "JobsDetail"));
            }
        });
        this.childElementBinders.put("RequestId", new ChildElementBinder<PostImagesAuditJobResponse>() { // from class: com.tencent.cos.xml.model.tag.audit.post.PostImagesAuditJobResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostImagesAuditJobResponse postImagesAuditJobResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postImagesAuditJobResponse.requestId = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostImagesAuditJobResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostImagesAuditJobResponse postImagesAuditJobResponse = new PostImagesAuditJobResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostImagesAuditJobResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postImagesAuditJobResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postImagesAuditJobResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postImagesAuditJobResponse;
    }
}
