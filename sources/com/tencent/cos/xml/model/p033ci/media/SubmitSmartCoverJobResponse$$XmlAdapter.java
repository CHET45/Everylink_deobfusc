package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitSmartCoverJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitSmartCoverJobResponse$$XmlAdapter extends IXmlAdapter<SubmitSmartCoverJobResponse> {
    private HashMap<String, ChildElementBinder<SubmitSmartCoverJobResponse>> childElementBinders;

    public SubmitSmartCoverJobResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SubmitSmartCoverJobResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("JobsDetail", new ChildElementBinder<SubmitSmartCoverJobResponse>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSmartCoverJobResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSmartCoverJobResponse submitSmartCoverJobResponse, String str) throws XmlPullParserException, IOException {
                submitSmartCoverJobResponse.jobsDetail = (SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseJobsDetail) QCloudXml.fromXml(xmlPullParser, SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseJobsDetail.class, "JobsDetail");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitSmartCoverJobResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitSmartCoverJobResponse submitSmartCoverJobResponse = new SubmitSmartCoverJobResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitSmartCoverJobResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitSmartCoverJobResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitSmartCoverJobResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitSmartCoverJobResponse;
    }
}
