package com.tencent.cos.xml.model.tag.audit.bean;

import com.tencent.cos.xml.model.tag.audit.bean.AuditListInfo;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class AuditListInfo$ListResults$$XmlAdapter extends IXmlAdapter<AuditListInfo.ListResults> {
    private HashMap<String, ChildElementBinder<AuditListInfo.ListResults>> childElementBinders;

    public AuditListInfo$ListResults$$XmlAdapter() {
        HashMap<String, ChildElementBinder<AuditListInfo.ListResults>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("ListType", new ChildElementBinder<AuditListInfo.ListResults>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditListInfo$ListResults$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditListInfo.ListResults listResults, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                listResults.listType = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("ListName", new ChildElementBinder<AuditListInfo.ListResults>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditListInfo$ListResults$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditListInfo.ListResults listResults, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                listResults.listName = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Entity", new ChildElementBinder<AuditListInfo.ListResults>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditListInfo$ListResults$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditListInfo.ListResults listResults, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                listResults.entity = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public AuditListInfo.ListResults fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        AuditListInfo.ListResults listResults = new AuditListInfo.ListResults();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<AuditListInfo.ListResults> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, listResults, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "ListResults" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return listResults;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return listResults;
    }
}
