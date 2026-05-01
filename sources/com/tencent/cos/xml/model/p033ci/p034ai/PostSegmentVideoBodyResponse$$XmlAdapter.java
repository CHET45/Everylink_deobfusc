package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.PostSegmentVideoBodyResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class PostSegmentVideoBodyResponse$$XmlAdapter extends IXmlAdapter<PostSegmentVideoBodyResponse> {
    private HashMap<String, ChildElementBinder<PostSegmentVideoBodyResponse>> childElementBinders;

    public PostSegmentVideoBodyResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<PostSegmentVideoBodyResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("JobsDetail", new ChildElementBinder<PostSegmentVideoBodyResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSegmentVideoBodyResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSegmentVideoBodyResponse postSegmentVideoBodyResponse, String str) throws XmlPullParserException, IOException {
                if (postSegmentVideoBodyResponse.jobsDetail == null) {
                    postSegmentVideoBodyResponse.jobsDetail = new ArrayList();
                }
                postSegmentVideoBodyResponse.jobsDetail.add((PostSegmentVideoBodyResponse.PostSegmentVideoBodyResponseJobsDetail) QCloudXml.fromXml(xmlPullParser, PostSegmentVideoBodyResponse.PostSegmentVideoBodyResponseJobsDetail.class, "JobsDetail"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostSegmentVideoBodyResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostSegmentVideoBodyResponse postSegmentVideoBodyResponse = new PostSegmentVideoBodyResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostSegmentVideoBodyResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postSegmentVideoBodyResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postSegmentVideoBodyResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postSegmentVideoBodyResponse;
    }
}
