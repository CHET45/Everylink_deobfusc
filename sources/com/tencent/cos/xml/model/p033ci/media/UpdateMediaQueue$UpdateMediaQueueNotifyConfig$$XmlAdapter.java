package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.media.UpdateMediaQueue;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class UpdateMediaQueue$UpdateMediaQueueNotifyConfig$$XmlAdapter extends IXmlAdapter<UpdateMediaQueue.UpdateMediaQueueNotifyConfig> {
    private HashMap<String, ChildElementBinder<UpdateMediaQueue.UpdateMediaQueueNotifyConfig>> childElementBinders;

    public UpdateMediaQueue$UpdateMediaQueueNotifyConfig$$XmlAdapter() {
        HashMap<String, ChildElementBinder<UpdateMediaQueue.UpdateMediaQueueNotifyConfig>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("State", new ChildElementBinder<UpdateMediaQueue.UpdateMediaQueueNotifyConfig>() { // from class: com.tencent.cos.xml.model.ci.media.UpdateMediaQueue$UpdateMediaQueueNotifyConfig$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateMediaQueue.UpdateMediaQueueNotifyConfig updateMediaQueueNotifyConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                updateMediaQueueNotifyConfig.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Event", new ChildElementBinder<UpdateMediaQueue.UpdateMediaQueueNotifyConfig>() { // from class: com.tencent.cos.xml.model.ci.media.UpdateMediaQueue$UpdateMediaQueueNotifyConfig$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateMediaQueue.UpdateMediaQueueNotifyConfig updateMediaQueueNotifyConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                updateMediaQueueNotifyConfig.event = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("ResultFormat", new ChildElementBinder<UpdateMediaQueue.UpdateMediaQueueNotifyConfig>() { // from class: com.tencent.cos.xml.model.ci.media.UpdateMediaQueue$UpdateMediaQueueNotifyConfig$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateMediaQueue.UpdateMediaQueueNotifyConfig updateMediaQueueNotifyConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                updateMediaQueueNotifyConfig.resultFormat = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Type", new ChildElementBinder<UpdateMediaQueue.UpdateMediaQueueNotifyConfig>() { // from class: com.tencent.cos.xml.model.ci.media.UpdateMediaQueue$UpdateMediaQueueNotifyConfig$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateMediaQueue.UpdateMediaQueueNotifyConfig updateMediaQueueNotifyConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                updateMediaQueueNotifyConfig.type = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.URL_ELEMENT, new ChildElementBinder<UpdateMediaQueue.UpdateMediaQueueNotifyConfig>() { // from class: com.tencent.cos.xml.model.ci.media.UpdateMediaQueue$UpdateMediaQueueNotifyConfig$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateMediaQueue.UpdateMediaQueueNotifyConfig updateMediaQueueNotifyConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                updateMediaQueueNotifyConfig.url = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("MqMode", new ChildElementBinder<UpdateMediaQueue.UpdateMediaQueueNotifyConfig>() { // from class: com.tencent.cos.xml.model.ci.media.UpdateMediaQueue$UpdateMediaQueueNotifyConfig$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateMediaQueue.UpdateMediaQueueNotifyConfig updateMediaQueueNotifyConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                updateMediaQueueNotifyConfig.mqMode = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("MqRegion", new ChildElementBinder<UpdateMediaQueue.UpdateMediaQueueNotifyConfig>() { // from class: com.tencent.cos.xml.model.ci.media.UpdateMediaQueue$UpdateMediaQueueNotifyConfig$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateMediaQueue.UpdateMediaQueueNotifyConfig updateMediaQueueNotifyConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                updateMediaQueueNotifyConfig.mqRegion = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("MqName", new ChildElementBinder<UpdateMediaQueue.UpdateMediaQueueNotifyConfig>() { // from class: com.tencent.cos.xml.model.ci.media.UpdateMediaQueue$UpdateMediaQueueNotifyConfig$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateMediaQueue.UpdateMediaQueueNotifyConfig updateMediaQueueNotifyConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                updateMediaQueueNotifyConfig.mqName = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public UpdateMediaQueue.UpdateMediaQueueNotifyConfig fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        UpdateMediaQueue.UpdateMediaQueueNotifyConfig updateMediaQueueNotifyConfig = new UpdateMediaQueue.UpdateMediaQueueNotifyConfig();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<UpdateMediaQueue.UpdateMediaQueueNotifyConfig> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, updateMediaQueueNotifyConfig, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "NotifyConfig" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return updateMediaQueueNotifyConfig;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return updateMediaQueueNotifyConfig;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, UpdateMediaQueue.UpdateMediaQueueNotifyConfig updateMediaQueueNotifyConfig, String str) throws XmlPullParserException, IOException {
        if (updateMediaQueueNotifyConfig == null) {
            return;
        }
        if (str == null) {
            str = "NotifyConfig";
        }
        xmlSerializer.startTag("", str);
        if (updateMediaQueueNotifyConfig.state != null) {
            xmlSerializer.startTag("", "State");
            xmlSerializer.text(String.valueOf(updateMediaQueueNotifyConfig.state));
            xmlSerializer.endTag("", "State");
        }
        if (updateMediaQueueNotifyConfig.event != null) {
            xmlSerializer.startTag("", "Event");
            xmlSerializer.text(String.valueOf(updateMediaQueueNotifyConfig.event));
            xmlSerializer.endTag("", "Event");
        }
        if (updateMediaQueueNotifyConfig.resultFormat != null) {
            xmlSerializer.startTag("", "ResultFormat");
            xmlSerializer.text(String.valueOf(updateMediaQueueNotifyConfig.resultFormat));
            xmlSerializer.endTag("", "ResultFormat");
        }
        if (updateMediaQueueNotifyConfig.type != null) {
            xmlSerializer.startTag("", "Type");
            xmlSerializer.text(String.valueOf(updateMediaQueueNotifyConfig.type));
            xmlSerializer.endTag("", "Type");
        }
        if (updateMediaQueueNotifyConfig.url != null) {
            xmlSerializer.startTag("", Constants.URL_ELEMENT);
            xmlSerializer.text(String.valueOf(updateMediaQueueNotifyConfig.url));
            xmlSerializer.endTag("", Constants.URL_ELEMENT);
        }
        if (updateMediaQueueNotifyConfig.mqMode != null) {
            xmlSerializer.startTag("", "MqMode");
            xmlSerializer.text(String.valueOf(updateMediaQueueNotifyConfig.mqMode));
            xmlSerializer.endTag("", "MqMode");
        }
        if (updateMediaQueueNotifyConfig.mqRegion != null) {
            xmlSerializer.startTag("", "MqRegion");
            xmlSerializer.text(String.valueOf(updateMediaQueueNotifyConfig.mqRegion));
            xmlSerializer.endTag("", "MqRegion");
        }
        if (updateMediaQueueNotifyConfig.mqName != null) {
            xmlSerializer.startTag("", "MqName");
            xmlSerializer.text(String.valueOf(updateMediaQueueNotifyConfig.mqName));
            xmlSerializer.endTag("", "MqName");
        }
        xmlSerializer.endTag("", str);
    }
}
