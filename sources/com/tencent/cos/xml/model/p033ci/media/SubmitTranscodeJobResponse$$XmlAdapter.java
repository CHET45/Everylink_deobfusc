package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitTranscodeJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitTranscodeJobResponse$$XmlAdapter extends IXmlAdapter<SubmitTranscodeJobResponse> {
    private HashMap<String, ChildElementBinder<SubmitTranscodeJobResponse>> childElementBinders;

    public SubmitTranscodeJobResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SubmitTranscodeJobResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("JobsDetail", new ChildElementBinder<SubmitTranscodeJobResponse>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJobResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJobResponse submitTranscodeJobResponse, String str) throws XmlPullParserException, IOException {
                submitTranscodeJobResponse.jobsDetail = (SubmitTranscodeJobResponse.SubmitTranscodeJobResponseJobsDetail) QCloudXml.fromXml(xmlPullParser, SubmitTranscodeJobResponse.SubmitTranscodeJobResponseJobsDetail.class, "JobsDetail");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitTranscodeJobResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitTranscodeJobResponse submitTranscodeJobResponse = new SubmitTranscodeJobResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitTranscodeJobResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitTranscodeJobResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitTranscodeJobResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitTranscodeJobResponse;
    }
}
