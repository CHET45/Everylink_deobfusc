package com.tencent.cos.xml.model.tag.audit.bean;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class AuditOcrResults$$XmlAdapter extends IXmlAdapter<AuditOcrResults> {
    private HashMap<String, ChildElementBinder<AuditOcrResults>> childElementBinders;

    public AuditOcrResults$$XmlAdapter() {
        HashMap<String, ChildElementBinder<AuditOcrResults>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Text", new ChildElementBinder<AuditOcrResults>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditOcrResults$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditOcrResults auditOcrResults, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditOcrResults.text = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Keywords", new ChildElementBinder<AuditOcrResults>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditOcrResults$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditOcrResults auditOcrResults, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditOcrResults.keywords = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SubLabel", new ChildElementBinder<AuditOcrResults>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditOcrResults$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditOcrResults auditOcrResults, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                auditOcrResults.subLabel = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Location", new ChildElementBinder<AuditOcrResults>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.AuditOcrResults$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AuditOcrResults auditOcrResults, String str) throws XmlPullParserException, IOException {
                auditOcrResults.location = (AuditOcrLocation) QCloudXml.fromXml(xmlPullParser, AuditOcrLocation.class, "Location");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public AuditOcrResults fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        AuditOcrResults auditOcrResults = new AuditOcrResults();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<AuditOcrResults> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, auditOcrResults, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "OcrResults" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return auditOcrResults;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return auditOcrResults;
    }
}
