package com.tencent.cos.xml.model.tag.audit.bean;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class AuditLibResults$$XmlAdapter extends IXmlAdapter<AuditLibResults> {
    private HashMap<String, ChildElementBinder<AuditLibResults>> childElementBinders;

    public AuditLibResults$$XmlAdapter() {
        HashMap<String, ChildElementBinder<AuditLibResults>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("ImageId", new ChildElementBinder<AuditLibResults>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditLibResults$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditLibResults auditLibResults, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditLibResults.imageId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Score", new ChildElementBinder<AuditLibResults>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditLibResults$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditLibResults auditLibResults, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditLibResults.score = Integer.parseInt(xmlPullParser.getText());
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public AuditLibResults fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        AuditLibResults auditLibResults = new AuditLibResults();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<AuditLibResults> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, auditLibResults, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "LibResults" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return auditLibResults;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return auditLibResults;
    }
}
