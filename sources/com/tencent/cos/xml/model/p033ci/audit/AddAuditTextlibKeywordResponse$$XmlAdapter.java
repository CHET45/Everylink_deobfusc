package com.tencent.cos.xml.model.p033ci.audit;

import com.tencent.cos.xml.model.p033ci.audit.AddAuditTextlibKeywordResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class AddAuditTextlibKeywordResponse$$XmlAdapter extends IXmlAdapter<AddAuditTextlibKeywordResponse> {
    private HashMap<String, ChildElementBinder<AddAuditTextlibKeywordResponse>> childElementBinders;

    public AddAuditTextlibKeywordResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<AddAuditTextlibKeywordResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("RequestId", new ChildElementBinder<AddAuditTextlibKeywordResponse>() { // from class: com.tencent.cos.xml.model.ci.audit.AddAuditTextlibKeywordResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AddAuditTextlibKeywordResponse addAuditTextlibKeywordResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                addAuditTextlibKeywordResponse.requestId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Results", new ChildElementBinder<AddAuditTextlibKeywordResponse>() { // from class: com.tencent.cos.xml.model.ci.audit.AddAuditTextlibKeywordResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AddAuditTextlibKeywordResponse addAuditTextlibKeywordResponse, String str) throws XmlPullParserException, IOException {
                if (addAuditTextlibKeywordResponse.results == null) {
                    addAuditTextlibKeywordResponse.results = new ArrayList();
                }
                addAuditTextlibKeywordResponse.results.add((AddAuditTextlibKeywordResponse.Results) QCloudXml.fromXml(xmlPullParser, AddAuditTextlibKeywordResponse.Results.class, "Results"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public AddAuditTextlibKeywordResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        AddAuditTextlibKeywordResponse addAuditTextlibKeywordResponse = new AddAuditTextlibKeywordResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<AddAuditTextlibKeywordResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, addAuditTextlibKeywordResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return addAuditTextlibKeywordResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return addAuditTextlibKeywordResponse;
    }
}
