package com.tencent.cos.xml.model.tag.audit.get;

import com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetAudioAuditJobResponse$$XmlAdapter extends IXmlAdapter<GetAudioAuditJobResponse> {
    private HashMap<String, ChildElementBinder<GetAudioAuditJobResponse>> childElementBinders;

    public GetAudioAuditJobResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetAudioAuditJobResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("JobsDetail", new ChildElementBinder<GetAudioAuditJobResponse>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse getAudioAuditJobResponse, String str) throws XmlPullParserException, IOException {
                getAudioAuditJobResponse.jobsDetail = (GetAudioAuditJobResponse.AudioAuditJobsDetail) QCloudXml.fromXml(xmlPullParser, GetAudioAuditJobResponse.AudioAuditJobsDetail.class, "JobsDetail");
            }
        });
        this.childElementBinders.put("RequestId", new ChildElementBinder<GetAudioAuditJobResponse>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse getAudioAuditJobResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getAudioAuditJobResponse.requestId = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetAudioAuditJobResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetAudioAuditJobResponse getAudioAuditJobResponse = new GetAudioAuditJobResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetAudioAuditJobResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getAudioAuditJobResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getAudioAuditJobResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getAudioAuditJobResponse;
    }
}
