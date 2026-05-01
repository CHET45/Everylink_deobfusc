package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitMediaInfoJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitMediaInfoJobResponse$$XmlAdapter extends IXmlAdapter<SubmitMediaInfoJobResponse> {
    private HashMap<String, ChildElementBinder<SubmitMediaInfoJobResponse>> childElementBinders;

    public SubmitMediaInfoJobResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SubmitMediaInfoJobResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("JobsDetail", new ChildElementBinder<SubmitMediaInfoJobResponse>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaInfoJobResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaInfoJobResponse submitMediaInfoJobResponse, String str) throws XmlPullParserException, IOException {
                submitMediaInfoJobResponse.jobsDetail = (SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseJobsDetail) QCloudXml.fromXml(xmlPullParser, SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseJobsDetail.class, "JobsDetail");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitMediaInfoJobResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitMediaInfoJobResponse submitMediaInfoJobResponse = new SubmitMediaInfoJobResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitMediaInfoJobResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitMediaInfoJobResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitMediaInfoJobResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitMediaInfoJobResponse;
    }
}
