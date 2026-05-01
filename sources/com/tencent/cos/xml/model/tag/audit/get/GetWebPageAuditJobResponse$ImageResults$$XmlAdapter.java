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
public class GetWebPageAuditJobResponse$ImageResults$$XmlAdapter extends IXmlAdapter<GetWebPageAuditJobResponse.ImageResults> {
    private HashMap<String, ChildElementBinder<GetWebPageAuditJobResponse.ImageResults>> childElementBinders;

    public GetWebPageAuditJobResponse$ImageResults$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetWebPageAuditJobResponse.ImageResults>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Results", new ChildElementBinder<GetWebPageAuditJobResponse.ImageResults>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetWebPageAuditJobResponse$ImageResults$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWebPageAuditJobResponse.ImageResults imageResults, String str) throws XmlPullParserException, IOException {
                if (imageResults.results == null) {
                    imageResults.results = new ArrayList();
                }
                imageResults.results.add((GetWebPageAuditJobResponse.ImageResult) QCloudXml.fromXml(xmlPullParser, GetWebPageAuditJobResponse.ImageResult.class, "Results"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetWebPageAuditJobResponse.ImageResults fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetWebPageAuditJobResponse.ImageResults imageResults = new GetWebPageAuditJobResponse.ImageResults();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetWebPageAuditJobResponse.ImageResults> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, imageResults, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "ImageResults" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return imageResults;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return imageResults;
    }
}
