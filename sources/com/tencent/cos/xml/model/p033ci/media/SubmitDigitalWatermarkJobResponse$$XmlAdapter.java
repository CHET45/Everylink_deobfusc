package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitDigitalWatermarkJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitDigitalWatermarkJobResponse$$XmlAdapter extends IXmlAdapter<SubmitDigitalWatermarkJobResponse> {
    private HashMap<String, ChildElementBinder<SubmitDigitalWatermarkJobResponse>> childElementBinders;

    public SubmitDigitalWatermarkJobResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SubmitDigitalWatermarkJobResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("JobsDetail", new ChildElementBinder<SubmitDigitalWatermarkJobResponse>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitDigitalWatermarkJobResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitDigitalWatermarkJobResponse submitDigitalWatermarkJobResponse, String str) throws XmlPullParserException, IOException {
                submitDigitalWatermarkJobResponse.jobsDetail = (SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseJobsDetail) QCloudXml.fromXml(xmlPullParser, SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseJobsDetail.class, "JobsDetail");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitDigitalWatermarkJobResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitDigitalWatermarkJobResponse submitDigitalWatermarkJobResponse = new SubmitDigitalWatermarkJobResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitDigitalWatermarkJobResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitDigitalWatermarkJobResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitDigitalWatermarkJobResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitDigitalWatermarkJobResponse;
    }
}
