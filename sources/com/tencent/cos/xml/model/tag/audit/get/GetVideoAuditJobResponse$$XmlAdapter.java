package com.tencent.cos.xml.model.tag.audit.get;

import com.tencent.cos.xml.model.tag.audit.get.GetVideoAuditJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetVideoAuditJobResponse$$XmlAdapter extends IXmlAdapter<GetVideoAuditJobResponse> {
    private HashMap<String, ChildElementBinder<GetVideoAuditJobResponse>> childElementBinders;

    public GetVideoAuditJobResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetVideoAuditJobResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("RequestId", new ChildElementBinder<GetVideoAuditJobResponse>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetVideoAuditJobResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetVideoAuditJobResponse getVideoAuditJobResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getVideoAuditJobResponse.requestId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobsDetail", new ChildElementBinder<GetVideoAuditJobResponse>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetVideoAuditJobResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetVideoAuditJobResponse getVideoAuditJobResponse, String str) throws XmlPullParserException, IOException {
                getVideoAuditJobResponse.jobsDetail = (GetVideoAuditJobResponse.VideoAuditJobsDetail) QCloudXml.fromXml(xmlPullParser, GetVideoAuditJobResponse.VideoAuditJobsDetail.class, "JobsDetail");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetVideoAuditJobResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetVideoAuditJobResponse getVideoAuditJobResponse = new GetVideoAuditJobResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetVideoAuditJobResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getVideoAuditJobResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getVideoAuditJobResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getVideoAuditJobResponse;
    }
}
