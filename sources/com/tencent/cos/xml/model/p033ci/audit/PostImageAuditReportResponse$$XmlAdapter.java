package com.tencent.cos.xml.model.p033ci.audit;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class PostImageAuditReportResponse$$XmlAdapter extends IXmlAdapter<PostImageAuditReportResponse> {
    private HashMap<String, ChildElementBinder<PostImageAuditReportResponse>> childElementBinders;

    public PostImageAuditReportResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<PostImageAuditReportResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("RequestId", new ChildElementBinder<PostImageAuditReportResponse>() { // from class: com.tencent.cos.xml.model.ci.audit.PostImageAuditReportResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostImageAuditReportResponse postImageAuditReportResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postImageAuditReportResponse.requestId = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostImageAuditReportResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostImageAuditReportResponse postImageAuditReportResponse = new PostImageAuditReportResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostImageAuditReportResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postImageAuditReportResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postImageAuditReportResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postImageAuditReportResponse;
    }
}
