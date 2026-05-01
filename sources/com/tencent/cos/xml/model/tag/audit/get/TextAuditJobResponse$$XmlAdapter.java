package com.tencent.cos.xml.model.tag.audit.get;

import com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class TextAuditJobResponse$$XmlAdapter extends IXmlAdapter<TextAuditJobResponse> {
    private HashMap<String, ChildElementBinder<TextAuditJobResponse>> childElementBinders;

    public TextAuditJobResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TextAuditJobResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("JobsDetail", new ChildElementBinder<TextAuditJobResponse>() { // from class: com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditJobResponse textAuditJobResponse, String str) throws XmlPullParserException, IOException {
                textAuditJobResponse.jobsDetail = (TextAuditJobResponse.TextAuditJobsDetail) QCloudXml.fromXml(xmlPullParser, TextAuditJobResponse.TextAuditJobsDetail.class, "JobsDetail");
            }
        });
        this.childElementBinders.put("RequestId", new ChildElementBinder<TextAuditJobResponse>() { // from class: com.tencent.cos.xml.model.tag.audit.get.TextAuditJobResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditJobResponse textAuditJobResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                textAuditJobResponse.requestId = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TextAuditJobResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TextAuditJobResponse textAuditJobResponse = new TextAuditJobResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TextAuditJobResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, textAuditJobResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return textAuditJobResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return textAuditJobResponse;
    }
}
