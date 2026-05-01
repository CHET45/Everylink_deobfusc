package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitMediaSegmentJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitMediaSegmentJobResponse$$XmlAdapter extends IXmlAdapter<SubmitMediaSegmentJobResponse> {
    private HashMap<String, ChildElementBinder<SubmitMediaSegmentJobResponse>> childElementBinders;

    public SubmitMediaSegmentJobResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SubmitMediaSegmentJobResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("JobsDetail", new ChildElementBinder<SubmitMediaSegmentJobResponse>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaSegmentJobResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaSegmentJobResponse submitMediaSegmentJobResponse, String str) throws XmlPullParserException, IOException {
                submitMediaSegmentJobResponse.jobsDetail = (SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseJobsDetail) QCloudXml.fromXml(xmlPullParser, SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseJobsDetail.class, "JobsDetail");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitMediaSegmentJobResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitMediaSegmentJobResponse submitMediaSegmentJobResponse = new SubmitMediaSegmentJobResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitMediaSegmentJobResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitMediaSegmentJobResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitMediaSegmentJobResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitMediaSegmentJobResponse;
    }
}
