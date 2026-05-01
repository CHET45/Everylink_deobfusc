package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitAnimationJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitAnimationJobResponse$$XmlAdapter extends IXmlAdapter<SubmitAnimationJobResponse> {
    private HashMap<String, ChildElementBinder<SubmitAnimationJobResponse>> childElementBinders;

    public SubmitAnimationJobResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SubmitAnimationJobResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("JobsDetail", new ChildElementBinder<SubmitAnimationJobResponse>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitAnimationJobResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitAnimationJobResponse submitAnimationJobResponse, String str) throws XmlPullParserException, IOException {
                submitAnimationJobResponse.jobsDetail = (SubmitAnimationJobResponse.SubmitAnimationJobResponseJobsDetail) QCloudXml.fromXml(xmlPullParser, SubmitAnimationJobResponse.SubmitAnimationJobResponseJobsDetail.class, "JobsDetail");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitAnimationJobResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitAnimationJobResponse submitAnimationJobResponse = new SubmitAnimationJobResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitAnimationJobResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitAnimationJobResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitAnimationJobResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitAnimationJobResponse;
    }
}
