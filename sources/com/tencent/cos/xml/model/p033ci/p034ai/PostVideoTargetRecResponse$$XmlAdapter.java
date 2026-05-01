package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.PostVideoTargetRecResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class PostVideoTargetRecResponse$$XmlAdapter extends IXmlAdapter<PostVideoTargetRecResponse> {
    private HashMap<String, ChildElementBinder<PostVideoTargetRecResponse>> childElementBinders;

    public PostVideoTargetRecResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<PostVideoTargetRecResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("JobsDetail", new ChildElementBinder<PostVideoTargetRecResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVideoTargetRecResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVideoTargetRecResponse postVideoTargetRecResponse, String str) throws XmlPullParserException, IOException {
                if (postVideoTargetRecResponse.jobsDetail == null) {
                    postVideoTargetRecResponse.jobsDetail = new ArrayList();
                }
                postVideoTargetRecResponse.jobsDetail.add((PostVideoTargetRecResponse.PostVideoTargetRecResponseJobsDetail) QCloudXml.fromXml(xmlPullParser, PostVideoTargetRecResponse.PostVideoTargetRecResponseJobsDetail.class, "JobsDetail"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostVideoTargetRecResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostVideoTargetRecResponse postVideoTargetRecResponse = new PostVideoTargetRecResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostVideoTargetRecResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postVideoTargetRecResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postVideoTargetRecResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postVideoTargetRecResponse;
    }
}
