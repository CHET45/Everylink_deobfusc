package com.tencent.cos.xml.model.p033ci.common;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class NoiseReduction$$XmlAdapter extends IXmlAdapter<NoiseReduction> {
    private HashMap<String, ChildElementBinder<NoiseReduction>> childElementBinders;

    public NoiseReduction$$XmlAdapter() {
        HashMap<String, ChildElementBinder<NoiseReduction>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Format", new ChildElementBinder<NoiseReduction>() { // from class: com.tencent.cos.xml.model.ci.common.NoiseReduction$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, NoiseReduction noiseReduction, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                noiseReduction.format = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Samplerate", new ChildElementBinder<NoiseReduction>() { // from class: com.tencent.cos.xml.model.ci.common.NoiseReduction$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, NoiseReduction noiseReduction, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                noiseReduction.samplerate = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public NoiseReduction fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        NoiseReduction noiseReduction = new NoiseReduction();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<NoiseReduction> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, noiseReduction, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "NoiseReduction" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return noiseReduction;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return noiseReduction;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, NoiseReduction noiseReduction, String str) throws XmlPullParserException, IOException {
        if (noiseReduction == null) {
            return;
        }
        if (str == null) {
            str = "NoiseReduction";
        }
        xmlSerializer.startTag("", str);
        if (noiseReduction.format != null) {
            xmlSerializer.startTag("", "Format");
            xmlSerializer.text(String.valueOf(noiseReduction.format));
            xmlSerializer.endTag("", "Format");
        }
        if (noiseReduction.samplerate != null) {
            xmlSerializer.startTag("", "Samplerate");
            xmlSerializer.text(String.valueOf(noiseReduction.samplerate));
            xmlSerializer.endTag("", "Samplerate");
        }
        xmlSerializer.endTag("", str);
    }
}
