package com.tencent.cos.xml.model.p033ci.common;

import com.microsoft.azure.storage.Constants;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class NotifyConfig$$XmlAdapter extends IXmlAdapter<NotifyConfig> {
    private HashMap<String, ChildElementBinder<NotifyConfig>> childElementBinders;

    public NotifyConfig$$XmlAdapter() {
        HashMap<String, ChildElementBinder<NotifyConfig>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("State", new ChildElementBinder<NotifyConfig>() { // from class: com.tencent.cos.xml.model.ci.common.NotifyConfig$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, NotifyConfig notifyConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                notifyConfig.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Event", new ChildElementBinder<NotifyConfig>() { // from class: com.tencent.cos.xml.model.ci.common.NotifyConfig$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, NotifyConfig notifyConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                notifyConfig.event = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("ResultFormat", new ChildElementBinder<NotifyConfig>() { // from class: com.tencent.cos.xml.model.ci.common.NotifyConfig$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, NotifyConfig notifyConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                notifyConfig.resultFormat = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Type", new ChildElementBinder<NotifyConfig>() { // from class: com.tencent.cos.xml.model.ci.common.NotifyConfig$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, NotifyConfig notifyConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                notifyConfig.type = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.URL_ELEMENT, new ChildElementBinder<NotifyConfig>() { // from class: com.tencent.cos.xml.model.ci.common.NotifyConfig$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, NotifyConfig notifyConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                notifyConfig.url = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("MqMode", new ChildElementBinder<NotifyConfig>() { // from class: com.tencent.cos.xml.model.ci.common.NotifyConfig$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, NotifyConfig notifyConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                notifyConfig.mqMode = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("MqRegion", new ChildElementBinder<NotifyConfig>() { // from class: com.tencent.cos.xml.model.ci.common.NotifyConfig$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, NotifyConfig notifyConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                notifyConfig.mqRegion = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("MqName", new ChildElementBinder<NotifyConfig>() { // from class: com.tencent.cos.xml.model.ci.common.NotifyConfig$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, NotifyConfig notifyConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                notifyConfig.mqName = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public NotifyConfig fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        NotifyConfig notifyConfig = new NotifyConfig();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<NotifyConfig> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, notifyConfig, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "NotifyConfig" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return notifyConfig;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return notifyConfig;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, NotifyConfig notifyConfig, String str) throws XmlPullParserException, IOException {
        if (notifyConfig == null) {
            return;
        }
        if (str == null) {
            str = "NotifyConfig";
        }
        xmlSerializer.startTag("", str);
        if (notifyConfig.state != null) {
            xmlSerializer.startTag("", "State");
            xmlSerializer.text(String.valueOf(notifyConfig.state));
            xmlSerializer.endTag("", "State");
        }
        if (notifyConfig.event != null) {
            xmlSerializer.startTag("", "Event");
            xmlSerializer.text(String.valueOf(notifyConfig.event));
            xmlSerializer.endTag("", "Event");
        }
        if (notifyConfig.resultFormat != null) {
            xmlSerializer.startTag("", "ResultFormat");
            xmlSerializer.text(String.valueOf(notifyConfig.resultFormat));
            xmlSerializer.endTag("", "ResultFormat");
        }
        if (notifyConfig.type != null) {
            xmlSerializer.startTag("", "Type");
            xmlSerializer.text(String.valueOf(notifyConfig.type));
            xmlSerializer.endTag("", "Type");
        }
        if (notifyConfig.url != null) {
            xmlSerializer.startTag("", Constants.URL_ELEMENT);
            xmlSerializer.text(String.valueOf(notifyConfig.url));
            xmlSerializer.endTag("", Constants.URL_ELEMENT);
        }
        if (notifyConfig.mqMode != null) {
            xmlSerializer.startTag("", "MqMode");
            xmlSerializer.text(String.valueOf(notifyConfig.mqMode));
            xmlSerializer.endTag("", "MqMode");
        }
        if (notifyConfig.mqRegion != null) {
            xmlSerializer.startTag("", "MqRegion");
            xmlSerializer.text(String.valueOf(notifyConfig.mqRegion));
            xmlSerializer.endTag("", "MqRegion");
        }
        if (notifyConfig.mqName != null) {
            xmlSerializer.startTag("", "MqName");
            xmlSerializer.text(String.valueOf(notifyConfig.mqName));
            xmlSerializer.endTag("", "MqName");
        }
        xmlSerializer.endTag("", str);
    }
}
