package com.tencent.cos.xml.model.tag.audit.get;

import com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetDocumentAuditJobResponse$PageSegment$$XmlAdapter extends IXmlAdapter<GetDocumentAuditJobResponse.PageSegment> {
    private HashMap<String, ChildElementBinder<GetDocumentAuditJobResponse.PageSegment>> childElementBinders;

    public GetDocumentAuditJobResponse$PageSegment$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetDocumentAuditJobResponse.PageSegment>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Results", new ChildElementBinder<GetDocumentAuditJobResponse.PageSegment>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetDocumentAuditJobResponse$PageSegment$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetDocumentAuditJobResponse.PageSegment pageSegment, String str) throws XmlPullParserException, IOException {
                if (pageSegment.results == null) {
                    pageSegment.results = new ArrayList();
                }
                pageSegment.results.add((GetDocumentAuditJobResponse.Results) QCloudXml.fromXml(xmlPullParser, GetDocumentAuditJobResponse.Results.class, "Results"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetDocumentAuditJobResponse.PageSegment fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetDocumentAuditJobResponse.PageSegment pageSegment = new GetDocumentAuditJobResponse.PageSegment();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetDocumentAuditJobResponse.PageSegment> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, pageSegment, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "PageSegment" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return pageSegment;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return pageSegment;
    }
}
