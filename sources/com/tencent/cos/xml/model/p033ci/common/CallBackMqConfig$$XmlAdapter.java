package com.tencent.cos.xml.model.p033ci.common;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class CallBackMqConfig$$XmlAdapter extends IXmlAdapter<CallBackMqConfig> {
    private HashMap<String, ChildElementBinder<CallBackMqConfig>> childElementBinders;

    public CallBackMqConfig$$XmlAdapter() {
        HashMap<String, ChildElementBinder<CallBackMqConfig>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("MqRegion", new ChildElementBinder<CallBackMqConfig>() { // from class: com.tencent.cos.xml.model.ci.common.CallBackMqConfig$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CallBackMqConfig callBackMqConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                callBackMqConfig.mqRegion = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("MqMode", new ChildElementBinder<CallBackMqConfig>() { // from class: com.tencent.cos.xml.model.ci.common.CallBackMqConfig$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CallBackMqConfig callBackMqConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                callBackMqConfig.mqMode = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("MqName", new ChildElementBinder<CallBackMqConfig>() { // from class: com.tencent.cos.xml.model.ci.common.CallBackMqConfig$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CallBackMqConfig callBackMqConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                callBackMqConfig.mqName = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public CallBackMqConfig fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        CallBackMqConfig callBackMqConfig = new CallBackMqConfig();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<CallBackMqConfig> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, callBackMqConfig, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "CallBackMqConfig" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return callBackMqConfig;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return callBackMqConfig;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, CallBackMqConfig callBackMqConfig, String str) throws XmlPullParserException, IOException {
        if (callBackMqConfig == null) {
            return;
        }
        if (str == null) {
            str = "CallBackMqConfig";
        }
        xmlSerializer.startTag("", str);
        if (callBackMqConfig.mqRegion != null) {
            xmlSerializer.startTag("", "MqRegion");
            xmlSerializer.text(String.valueOf(callBackMqConfig.mqRegion));
            xmlSerializer.endTag("", "MqRegion");
        }
        if (callBackMqConfig.mqMode != null) {
            xmlSerializer.startTag("", "MqMode");
            xmlSerializer.text(String.valueOf(callBackMqConfig.mqMode));
            xmlSerializer.endTag("", "MqMode");
        }
        if (callBackMqConfig.mqName != null) {
            xmlSerializer.startTag("", "MqName");
            xmlSerializer.text(String.valueOf(callBackMqConfig.mqName));
            xmlSerializer.endTag("", "MqName");
        }
        xmlSerializer.endTag("", str);
    }
}
