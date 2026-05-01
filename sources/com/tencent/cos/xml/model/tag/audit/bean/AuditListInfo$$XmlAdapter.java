package com.tencent.cos.xml.model.tag.audit.bean;

import com.tencent.cos.xml.model.tag.audit.bean.AuditListInfo;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class AuditListInfo$$XmlAdapter extends IXmlAdapter<AuditListInfo> {
    private HashMap<String, ChildElementBinder<AuditListInfo>> childElementBinders;

    public AuditListInfo$$XmlAdapter() {
        HashMap<String, ChildElementBinder<AuditListInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("ListResults", new ChildElementBinder<AuditListInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditListInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditListInfo auditListInfo, String str) throws XmlPullParserException, IOException {
                if (auditListInfo.listResults == null) {
                    auditListInfo.listResults = new ArrayList();
                }
                auditListInfo.listResults.add((AuditListInfo.ListResults) QCloudXml.fromXml(xmlPullParser, AuditListInfo.ListResults.class, "ListResults"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public AuditListInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        AuditListInfo auditListInfo = new AuditListInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<AuditListInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, auditListInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "ListInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return auditListInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return auditListInfo;
    }
}
