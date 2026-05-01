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
public class DigitalWatermark$$XmlAdapter extends IXmlAdapter<DigitalWatermark> {
    private HashMap<String, ChildElementBinder<DigitalWatermark>> childElementBinders;

    public DigitalWatermark$$XmlAdapter() {
        HashMap<String, ChildElementBinder<DigitalWatermark>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.ERROR_MESSAGE, new ChildElementBinder<DigitalWatermark>() { // from class: com.tencent.cos.xml.model.ci.common.DigitalWatermark$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DigitalWatermark digitalWatermark, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                digitalWatermark.message = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Type", new ChildElementBinder<DigitalWatermark>() { // from class: com.tencent.cos.xml.model.ci.common.DigitalWatermark$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DigitalWatermark digitalWatermark, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                digitalWatermark.type = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.AnalyticsConstants.VERSION_ELEMENT, new ChildElementBinder<DigitalWatermark>() { // from class: com.tencent.cos.xml.model.ci.common.DigitalWatermark$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DigitalWatermark digitalWatermark, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                digitalWatermark.version = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("IgnoreError", new ChildElementBinder<DigitalWatermark>() { // from class: com.tencent.cos.xml.model.ci.common.DigitalWatermark$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DigitalWatermark digitalWatermark, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                digitalWatermark.ignoreError = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<DigitalWatermark>() { // from class: com.tencent.cos.xml.model.ci.common.DigitalWatermark$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DigitalWatermark digitalWatermark, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                digitalWatermark.state = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public DigitalWatermark fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        DigitalWatermark digitalWatermark = new DigitalWatermark();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<DigitalWatermark> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, digitalWatermark, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "DigitalWatermark" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return digitalWatermark;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return digitalWatermark;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, DigitalWatermark digitalWatermark, String str) throws XmlPullParserException, IOException {
        if (digitalWatermark == null) {
            return;
        }
        if (str == null) {
            str = "DigitalWatermark";
        }
        xmlSerializer.startTag("", str);
        if (digitalWatermark.message != null) {
            xmlSerializer.startTag("", Constants.ERROR_MESSAGE);
            xmlSerializer.text(String.valueOf(digitalWatermark.message));
            xmlSerializer.endTag("", Constants.ERROR_MESSAGE);
        }
        if (digitalWatermark.type != null) {
            xmlSerializer.startTag("", "Type");
            xmlSerializer.text(String.valueOf(digitalWatermark.type));
            xmlSerializer.endTag("", "Type");
        }
        if (digitalWatermark.version != null) {
            xmlSerializer.startTag("", Constants.AnalyticsConstants.VERSION_ELEMENT);
            xmlSerializer.text(String.valueOf(digitalWatermark.version));
            xmlSerializer.endTag("", Constants.AnalyticsConstants.VERSION_ELEMENT);
        }
        if (digitalWatermark.ignoreError != null) {
            xmlSerializer.startTag("", "IgnoreError");
            xmlSerializer.text(String.valueOf(digitalWatermark.ignoreError));
            xmlSerializer.endTag("", "IgnoreError");
        }
        if (digitalWatermark.state != null) {
            xmlSerializer.startTag("", "State");
            xmlSerializer.text(String.valueOf(digitalWatermark.state));
            xmlSerializer.endTag("", "State");
        }
        xmlSerializer.endTag("", str);
    }
}
