package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.PostSoundHoundResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class PostSoundHoundResponse$$XmlAdapter extends IXmlAdapter<PostSoundHoundResponse> {
    private HashMap<String, ChildElementBinder<PostSoundHoundResponse>> childElementBinders;

    public PostSoundHoundResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<PostSoundHoundResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("JobsDetail", new ChildElementBinder<PostSoundHoundResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSoundHoundResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSoundHoundResponse postSoundHoundResponse, String str) throws XmlPullParserException, IOException {
                if (postSoundHoundResponse.jobsDetail == null) {
                    postSoundHoundResponse.jobsDetail = new ArrayList();
                }
                postSoundHoundResponse.jobsDetail.add((PostSoundHoundResponse.PostSoundHoundResponseJobsDetail) QCloudXml.fromXml(xmlPullParser, PostSoundHoundResponse.PostSoundHoundResponseJobsDetail.class, "JobsDetail"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostSoundHoundResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostSoundHoundResponse postSoundHoundResponse = new PostSoundHoundResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostSoundHoundResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postSoundHoundResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postSoundHoundResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postSoundHoundResponse;
    }
}
