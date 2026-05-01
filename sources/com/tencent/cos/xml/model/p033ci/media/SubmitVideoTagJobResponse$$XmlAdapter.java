package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitVideoTagJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitVideoTagJobResponse$$XmlAdapter extends IXmlAdapter<SubmitVideoTagJobResponse> {
    private HashMap<String, ChildElementBinder<SubmitVideoTagJobResponse>> childElementBinders;

    public SubmitVideoTagJobResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SubmitVideoTagJobResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("JobsDetail", new ChildElementBinder<SubmitVideoTagJobResponse>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoTagJobResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoTagJobResponse submitVideoTagJobResponse, String str) throws XmlPullParserException, IOException {
                submitVideoTagJobResponse.jobsDetail = (SubmitVideoTagJobResponse.SubmitVideoTagJobResponseJobsDetail) QCloudXml.fromXml(xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponseJobsDetail.class, "JobsDetail");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitVideoTagJobResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitVideoTagJobResponse submitVideoTagJobResponse = new SubmitVideoTagJobResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitVideoTagJobResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitVideoTagJobResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitVideoTagJobResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitVideoTagJobResponse;
    }
}
