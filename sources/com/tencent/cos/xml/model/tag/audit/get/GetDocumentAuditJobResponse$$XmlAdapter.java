package com.tencent.cos.xml.model.tag.audit.get;

import com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetDocumentAuditJobResponse$$XmlAdapter extends IXmlAdapter<GetDocumentAuditJobResponse> {
    private HashMap<String, ChildElementBinder<GetDocumentAuditJobResponse>> childElementBinders;

    public GetDocumentAuditJobResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetDocumentAuditJobResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("JobsDetail", new ChildElementBinder<GetDocumentAuditJobResponse>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse getDocumentAuditJobResponse, String str) throws XmlPullParserException, IOException {
                getDocumentAuditJobResponse.jobsDetail = (GetDocumentAuditJobResponse.DocumentAuditJobsDetail) QCloudXml.fromXml(xmlPullParser, GetDocumentAuditJobResponse.DocumentAuditJobsDetail.class, "JobsDetail");
            }
        });
        this.childElementBinders.put("RequestId", new ChildElementBinder<GetDocumentAuditJobResponse>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse getDocumentAuditJobResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getDocumentAuditJobResponse.requestId = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetDocumentAuditJobResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetDocumentAuditJobResponse getDocumentAuditJobResponse = new GetDocumentAuditJobResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetDocumentAuditJobResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getDocumentAuditJobResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getDocumentAuditJobResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getDocumentAuditJobResponse;
    }
}
