package com.tencent.cos.xml.model.tag.audit.bean;

import com.tencent.cos.xml.model.tag.audit.bean.TextAuditScenarioInfo;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class TextAuditScenarioInfo$Results$$XmlAdapter extends IXmlAdapter<TextAuditScenarioInfo.Results> {
    private HashMap<String, ChildElementBinder<TextAuditScenarioInfo.Results>> childElementBinders;

    public TextAuditScenarioInfo$Results$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TextAuditScenarioInfo.Results>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Label", new ChildElementBinder<TextAuditScenarioInfo.Results>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.TextAuditScenarioInfo$Results$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditScenarioInfo.Results results, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                results.label = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Score", new ChildElementBinder<TextAuditScenarioInfo.Results>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.TextAuditScenarioInfo$Results$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditScenarioInfo.Results results, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                results.score = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("StartTime", new ChildElementBinder<TextAuditScenarioInfo.Results>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.TextAuditScenarioInfo$Results$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditScenarioInfo.Results results, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                results.startTime = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("EndTime", new ChildElementBinder<TextAuditScenarioInfo.Results>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.TextAuditScenarioInfo$Results$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TextAuditScenarioInfo.Results results, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                results.endTime = Integer.parseInt(xmlPullParser.getText());
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TextAuditScenarioInfo.Results fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TextAuditScenarioInfo.Results results = new TextAuditScenarioInfo.Results();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TextAuditScenarioInfo.Results> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, results, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Results" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return results;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return results;
    }
}
