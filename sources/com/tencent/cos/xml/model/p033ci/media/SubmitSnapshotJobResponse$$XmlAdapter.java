package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitSnapshotJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitSnapshotJobResponse$$XmlAdapter extends IXmlAdapter<SubmitSnapshotJobResponse> {
    private HashMap<String, ChildElementBinder<SubmitSnapshotJobResponse>> childElementBinders;

    public SubmitSnapshotJobResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SubmitSnapshotJobResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("JobsDetail", new ChildElementBinder<SubmitSnapshotJobResponse>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSnapshotJobResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSnapshotJobResponse submitSnapshotJobResponse, String str) throws XmlPullParserException, IOException {
                submitSnapshotJobResponse.jobsDetail = (SubmitSnapshotJobResponse.SubmitSnapshotJobResponseJobsDetail) QCloudXml.fromXml(xmlPullParser, SubmitSnapshotJobResponse.SubmitSnapshotJobResponseJobsDetail.class, "JobsDetail");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitSnapshotJobResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitSnapshotJobResponse submitSnapshotJobResponse = new SubmitSnapshotJobResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitSnapshotJobResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitSnapshotJobResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitSnapshotJobResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitSnapshotJobResponse;
    }
}
