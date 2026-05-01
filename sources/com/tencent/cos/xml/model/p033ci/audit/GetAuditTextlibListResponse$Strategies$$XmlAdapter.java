package com.tencent.cos.xml.model.p033ci.audit;

import com.tencent.cos.xml.model.p033ci.audit.GetAuditTextlibListResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetAuditTextlibListResponse$Strategies$$XmlAdapter extends IXmlAdapter<GetAuditTextlibListResponse.Strategies> {
    private HashMap<String, ChildElementBinder<GetAuditTextlibListResponse.Strategies>> childElementBinders;

    public GetAuditTextlibListResponse$Strategies$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetAuditTextlibListResponse.Strategies>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Bucket", new ChildElementBinder<GetAuditTextlibListResponse.Strategies>() { // from class: com.tencent.cos.xml.model.ci.audit.GetAuditTextlibListResponse$Strategies$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAuditTextlibListResponse.Strategies strategies, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                strategies.bucket = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Service", new ChildElementBinder<GetAuditTextlibListResponse.Strategies>() { // from class: com.tencent.cos.xml.model.ci.audit.GetAuditTextlibListResponse$Strategies$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAuditTextlibListResponse.Strategies strategies, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                strategies.service = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BizType", new ChildElementBinder<GetAuditTextlibListResponse.Strategies>() { // from class: com.tencent.cos.xml.model.ci.audit.GetAuditTextlibListResponse$Strategies$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAuditTextlibListResponse.Strategies strategies, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                strategies.bizType = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetAuditTextlibListResponse.Strategies fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetAuditTextlibListResponse.Strategies strategies = new GetAuditTextlibListResponse.Strategies();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetAuditTextlibListResponse.Strategies> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, strategies, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Strategies" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return strategies;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return strategies;
    }
}
