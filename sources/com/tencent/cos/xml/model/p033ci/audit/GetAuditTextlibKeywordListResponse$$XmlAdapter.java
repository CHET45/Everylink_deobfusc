package com.tencent.cos.xml.model.p033ci.audit;

import com.tencent.cos.xml.model.p033ci.audit.GetAuditTextlibKeywordListResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetAuditTextlibKeywordListResponse$$XmlAdapter extends IXmlAdapter<GetAuditTextlibKeywordListResponse> {
    private HashMap<String, ChildElementBinder<GetAuditTextlibKeywordListResponse>> childElementBinders;

    public GetAuditTextlibKeywordListResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetAuditTextlibKeywordListResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("RequestId", new ChildElementBinder<GetAuditTextlibKeywordListResponse>() { // from class: com.tencent.cos.xml.model.ci.audit.GetAuditTextlibKeywordListResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAuditTextlibKeywordListResponse getAuditTextlibKeywordListResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getAuditTextlibKeywordListResponse.requestId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("TotalCount", new ChildElementBinder<GetAuditTextlibKeywordListResponse>() { // from class: com.tencent.cos.xml.model.ci.audit.GetAuditTextlibKeywordListResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAuditTextlibKeywordListResponse getAuditTextlibKeywordListResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getAuditTextlibKeywordListResponse.totalCount = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Keywords", new ChildElementBinder<GetAuditTextlibKeywordListResponse>() { // from class: com.tencent.cos.xml.model.ci.audit.GetAuditTextlibKeywordListResponse$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAuditTextlibKeywordListResponse getAuditTextlibKeywordListResponse, String str) throws XmlPullParserException, IOException {
                if (getAuditTextlibKeywordListResponse.keywords == null) {
                    getAuditTextlibKeywordListResponse.keywords = new ArrayList();
                }
                getAuditTextlibKeywordListResponse.keywords.add((GetAuditTextlibKeywordListResponse.Keywords) QCloudXml.fromXml(xmlPullParser, GetAuditTextlibKeywordListResponse.Keywords.class, "Keywords"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetAuditTextlibKeywordListResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetAuditTextlibKeywordListResponse getAuditTextlibKeywordListResponse = new GetAuditTextlibKeywordListResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetAuditTextlibKeywordListResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getAuditTextlibKeywordListResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getAuditTextlibKeywordListResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getAuditTextlibKeywordListResponse;
    }
}
