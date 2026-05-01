package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitPicProcessJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitPicProcessJobResponse$$XmlAdapter extends IXmlAdapter<SubmitPicProcessJobResponse> {
    private HashMap<String, ChildElementBinder<SubmitPicProcessJobResponse>> childElementBinders;

    public SubmitPicProcessJobResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SubmitPicProcessJobResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("JobsDetail", new ChildElementBinder<SubmitPicProcessJobResponse>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitPicProcessJobResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitPicProcessJobResponse submitPicProcessJobResponse, String str) throws XmlPullParserException, IOException {
                submitPicProcessJobResponse.jobsDetail = (SubmitPicProcessJobResponse.SubmitPicProcessJobResponseJobsDetail) QCloudXml.fromXml(xmlPullParser, SubmitPicProcessJobResponse.SubmitPicProcessJobResponseJobsDetail.class, "JobsDetail");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitPicProcessJobResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitPicProcessJobResponse submitPicProcessJobResponse = new SubmitPicProcessJobResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitPicProcessJobResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitPicProcessJobResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitPicProcessJobResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitPicProcessJobResponse;
    }
}
