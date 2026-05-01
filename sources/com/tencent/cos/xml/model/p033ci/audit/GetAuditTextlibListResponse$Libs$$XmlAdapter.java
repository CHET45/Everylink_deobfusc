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
public class GetAuditTextlibListResponse$Libs$$XmlAdapter extends IXmlAdapter<GetAuditTextlibListResponse.Libs> {
    private HashMap<String, ChildElementBinder<GetAuditTextlibListResponse.Libs>> childElementBinders;

    public GetAuditTextlibListResponse$Libs$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetAuditTextlibListResponse.Libs>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("LibID", new ChildElementBinder<GetAuditTextlibListResponse.Libs>() { // from class: com.tencent.cos.xml.model.ci.audit.GetAuditTextlibListResponse$Libs$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAuditTextlibListResponse.Libs libs, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                libs.libID = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("LibName", new ChildElementBinder<GetAuditTextlibListResponse.Libs>() { // from class: com.tencent.cos.xml.model.ci.audit.GetAuditTextlibListResponse$Libs$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAuditTextlibListResponse.Libs libs, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                libs.libName = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Suggestion", new ChildElementBinder<GetAuditTextlibListResponse.Libs>() { // from class: com.tencent.cos.xml.model.ci.audit.GetAuditTextlibListResponse$Libs$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAuditTextlibListResponse.Libs libs, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                libs.suggestion = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Strategies", new ChildElementBinder<GetAuditTextlibListResponse.Libs>() { // from class: com.tencent.cos.xml.model.ci.audit.GetAuditTextlibListResponse$Libs$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAuditTextlibListResponse.Libs libs, String str) throws XmlPullParserException, IOException {
                if (libs.strategies == null) {
                    libs.strategies = new ArrayList();
                }
                libs.strategies.add((GetAuditTextlibListResponse.Strategies) QCloudXml.fromXml(xmlPullParser, GetAuditTextlibListResponse.Strategies.class, "Strategies"));
            }
        });
        this.childElementBinders.put("MatchType", new ChildElementBinder<GetAuditTextlibListResponse.Libs>() { // from class: com.tencent.cos.xml.model.ci.audit.GetAuditTextlibListResponse$Libs$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAuditTextlibListResponse.Libs libs, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                libs.matchType = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreateTime", new ChildElementBinder<GetAuditTextlibListResponse.Libs>() { // from class: com.tencent.cos.xml.model.ci.audit.GetAuditTextlibListResponse$Libs$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAuditTextlibListResponse.Libs libs, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                libs.createTime = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetAuditTextlibListResponse.Libs fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetAuditTextlibListResponse.Libs libs = new GetAuditTextlibListResponse.Libs();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetAuditTextlibListResponse.Libs> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, libs, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Libs" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return libs;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return libs;
    }
}
