package com.tencent.cos.xml.model.p033ci.audit;

import com.tencent.cos.xml.model.p033ci.audit.GetAuditTextlibListResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetAuditTextlibListResponse$$XmlAdapter extends IXmlAdapter<GetAuditTextlibListResponse> {
    private HashMap<String, ChildElementBinder<GetAuditTextlibListResponse>> childElementBinders;

    public GetAuditTextlibListResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetAuditTextlibListResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("RequestId", new ChildElementBinder<GetAuditTextlibListResponse>() { // from class: com.tencent.cos.xml.model.ci.audit.GetAuditTextlibListResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAuditTextlibListResponse getAuditTextlibListResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getAuditTextlibListResponse.requestId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("TotalCount", new ChildElementBinder<GetAuditTextlibListResponse>() { // from class: com.tencent.cos.xml.model.ci.audit.GetAuditTextlibListResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAuditTextlibListResponse getAuditTextlibListResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getAuditTextlibListResponse.totalCount = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Libs", new ChildElementBinder<GetAuditTextlibListResponse>() { // from class: com.tencent.cos.xml.model.ci.audit.GetAuditTextlibListResponse$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAuditTextlibListResponse getAuditTextlibListResponse, String str) throws XmlPullParserException, IOException {
                if (getAuditTextlibListResponse.libs == null) {
                    getAuditTextlibListResponse.libs = new ArrayList();
                }
                getAuditTextlibListResponse.libs.add((GetAuditTextlibListResponse.Libs) QCloudXml.fromXml(xmlPullParser, GetAuditTextlibListResponse.Libs.class, "Libs"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetAuditTextlibListResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetAuditTextlibListResponse getAuditTextlibListResponse = new GetAuditTextlibListResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetAuditTextlibListResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getAuditTextlibListResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getAuditTextlibListResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getAuditTextlibListResponse;
    }
}
