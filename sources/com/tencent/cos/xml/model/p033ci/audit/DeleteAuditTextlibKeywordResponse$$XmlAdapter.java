package com.tencent.cos.xml.model.p033ci.audit;

import com.tencent.cos.xml.model.p033ci.audit.DeleteAuditTextlibKeywordResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class DeleteAuditTextlibKeywordResponse$$XmlAdapter extends IXmlAdapter<DeleteAuditTextlibKeywordResponse> {
    private HashMap<String, ChildElementBinder<DeleteAuditTextlibKeywordResponse>> childElementBinders;

    public DeleteAuditTextlibKeywordResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<DeleteAuditTextlibKeywordResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("RequestId", new ChildElementBinder<DeleteAuditTextlibKeywordResponse>() { // from class: com.tencent.cos.xml.model.ci.audit.DeleteAuditTextlibKeywordResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DeleteAuditTextlibKeywordResponse deleteAuditTextlibKeywordResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                deleteAuditTextlibKeywordResponse.requestId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Results", new ChildElementBinder<DeleteAuditTextlibKeywordResponse>() { // from class: com.tencent.cos.xml.model.ci.audit.DeleteAuditTextlibKeywordResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DeleteAuditTextlibKeywordResponse deleteAuditTextlibKeywordResponse, String str) throws XmlPullParserException, IOException {
                if (deleteAuditTextlibKeywordResponse.results == null) {
                    deleteAuditTextlibKeywordResponse.results = new ArrayList();
                }
                deleteAuditTextlibKeywordResponse.results.add((DeleteAuditTextlibKeywordResponse.Results) QCloudXml.fromXml(xmlPullParser, DeleteAuditTextlibKeywordResponse.Results.class, "Results"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public DeleteAuditTextlibKeywordResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        DeleteAuditTextlibKeywordResponse deleteAuditTextlibKeywordResponse = new DeleteAuditTextlibKeywordResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<DeleteAuditTextlibKeywordResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, deleteAuditTextlibKeywordResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return deleteAuditTextlibKeywordResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return deleteAuditTextlibKeywordResponse;
    }
}
