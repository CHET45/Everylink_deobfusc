package com.tencent.cos.xml.model.tag.audit.get;

import com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetWebPageAuditJobResponse$TextResults$$XmlAdapter extends IXmlAdapter<GetWebPageAuditJobResponse.TextResults> {
    private HashMap<String, ChildElementBinder<GetWebPageAuditJobResponse.TextResults>> childElementBinders;

    public GetWebPageAuditJobResponse$TextResults$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetWebPageAuditJobResponse.TextResults>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Results", new ChildElementBinder<GetWebPageAuditJobResponse.TextResults>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$TextResults$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.TextResults textResults, String str) throws XmlPullParserException, IOException {
                if (textResults.results == null) {
                    textResults.results = new ArrayList();
                }
                textResults.results.add((GetWebPageAuditJobResponse.TextResult) QCloudXml.fromXml(xmlPullParser, GetWebPageAuditJobResponse.TextResult.class, "Results"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetWebPageAuditJobResponse.TextResults fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetWebPageAuditJobResponse.TextResults textResults = new GetWebPageAuditJobResponse.TextResults();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetWebPageAuditJobResponse.TextResults> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, textResults, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "TextResults" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return textResults;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return textResults;
    }
}
