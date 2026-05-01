package com.tencent.cos.xml.model.p033ci.common;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class AudioConfig$$XmlAdapter extends IXmlAdapter<AudioConfig> {
    private HashMap<String, ChildElementBinder<AudioConfig>> childElementBinders;

    public AudioConfig$$XmlAdapter() {
        HashMap<String, ChildElementBinder<AudioConfig>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Codec", new ChildElementBinder<AudioConfig>() { // from class: com.tencent.cos.xml.model.ci.common.AudioConfig$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AudioConfig audioConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioConfig.codec = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Samplerate", new ChildElementBinder<AudioConfig>() { // from class: com.tencent.cos.xml.model.ci.common.AudioConfig$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AudioConfig audioConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioConfig.samplerate = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Bitrate", new ChildElementBinder<AudioConfig>() { // from class: com.tencent.cos.xml.model.ci.common.AudioConfig$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AudioConfig audioConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioConfig.bitrate = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Channels", new ChildElementBinder<AudioConfig>() { // from class: com.tencent.cos.xml.model.ci.common.AudioConfig$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AudioConfig audioConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioConfig.channels = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public AudioConfig fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        AudioConfig audioConfig = new AudioConfig();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<AudioConfig> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, audioConfig, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "AudioConfig" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return audioConfig;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return audioConfig;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, AudioConfig audioConfig, String str) throws XmlPullParserException, IOException {
        if (audioConfig == null) {
            return;
        }
        if (str == null) {
            str = "AudioConfig";
        }
        xmlSerializer.startTag("", str);
        if (audioConfig.codec != null) {
            xmlSerializer.startTag("", "Codec");
            xmlSerializer.text(String.valueOf(audioConfig.codec));
            xmlSerializer.endTag("", "Codec");
        }
        if (audioConfig.samplerate != null) {
            xmlSerializer.startTag("", "Samplerate");
            xmlSerializer.text(String.valueOf(audioConfig.samplerate));
            xmlSerializer.endTag("", "Samplerate");
        }
        if (audioConfig.bitrate != null) {
            xmlSerializer.startTag("", "Bitrate");
            xmlSerializer.text(String.valueOf(audioConfig.bitrate));
            xmlSerializer.endTag("", "Bitrate");
        }
        if (audioConfig.channels != null) {
            xmlSerializer.startTag("", "Channels");
            xmlSerializer.text(String.valueOf(audioConfig.channels));
            xmlSerializer.endTag("", "Channels");
        }
        xmlSerializer.endTag("", str);
    }
}
