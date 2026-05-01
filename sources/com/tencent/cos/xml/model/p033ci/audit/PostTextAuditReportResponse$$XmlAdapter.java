package com.tencent.cos.xml.model.p033ci.audit;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class PostTextAuditReportResponse$$XmlAdapter extends IXmlAdapter<PostTextAuditReportResponse> {
    private HashMap<String, ChildElementBinder<PostTextAuditReportResponse>> childElementBinders;

    public PostTextAuditReportResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<PostTextAuditReportResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("RequestId", new ChildElementBinder<PostTextAuditReportResponse>() { // from class: com.tencent.cos.xml.model.ci.audit.PostTextAuditReportResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostTextAuditReportResponse postTextAuditReportResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postTextAuditReportResponse.requestId = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostTextAuditReportResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostTextAuditReportResponse postTextAuditReportResponse = new PostTextAuditReportResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostTextAuditReportResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postTextAuditReportResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postTextAuditReportResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postTextAuditReportResponse;
    }
}
