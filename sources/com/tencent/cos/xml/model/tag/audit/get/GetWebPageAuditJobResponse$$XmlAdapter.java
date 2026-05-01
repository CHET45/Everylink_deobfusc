package com.tencent.cos.xml.model.tag.audit.get;

import com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetWebPageAuditJobResponse$$XmlAdapter extends IXmlAdapter<GetWebPageAuditJobResponse> {
    private HashMap<String, ChildElementBinder<GetWebPageAuditJobResponse>> childElementBinders;

    public GetWebPageAuditJobResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetWebPageAuditJobResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("JobsDetail", new ChildElementBinder<GetWebPageAuditJobResponse>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse getWebPageAuditJobResponse, String str) throws XmlPullParserException, IOException {
                getWebPageAuditJobResponse.jobsDetail = (GetWebPageAuditJobResponse.WebPageAuditJobsDetail) QCloudXml.fromXml(xmlPullParser, GetWebPageAuditJobResponse.WebPageAuditJobsDetail.class, "JobsDetail");
            }
        });
        this.childElementBinders.put("RequestId", new ChildElementBinder<GetWebPageAuditJobResponse>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse getWebPageAuditJobResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWebPageAuditJobResponse.requestId = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetWebPageAuditJobResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetWebPageAuditJobResponse getWebPageAuditJobResponse = new GetWebPageAuditJobResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetWebPageAuditJobResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getWebPageAuditJobResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getWebPageAuditJobResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getWebPageAuditJobResponse;
    }
}
