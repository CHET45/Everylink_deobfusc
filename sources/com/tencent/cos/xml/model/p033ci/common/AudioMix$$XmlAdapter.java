package com.tencent.cos.xml.model.p033ci.common;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class AudioMix$$XmlAdapter extends IXmlAdapter<AudioMix> {
    private HashMap<String, ChildElementBinder<AudioMix>> childElementBinders;

    public AudioMix$$XmlAdapter() {
        HashMap<String, ChildElementBinder<AudioMix>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("AudioSource", new ChildElementBinder<AudioMix>() { // from class: com.tencent.cos.xml.model.ci.common.AudioMix$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AudioMix audioMix, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioMix.audioSource = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("MixMode", new ChildElementBinder<AudioMix>() { // from class: com.tencent.cos.xml.model.ci.common.AudioMix$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AudioMix audioMix, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioMix.mixMode = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Replace", new ChildElementBinder<AudioMix>() { // from class: com.tencent.cos.xml.model.ci.common.AudioMix$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AudioMix audioMix, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioMix.replace = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("EffectConfig", new ChildElementBinder<AudioMix>() { // from class: com.tencent.cos.xml.model.ci.common.AudioMix$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AudioMix audioMix, String str) throws XmlPullParserException, IOException {
                audioMix.effectConfig = (EffectConfig) QCloudXml.fromXml(xmlPullParser, EffectConfig.class, "EffectConfig");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public AudioMix fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        AudioMix audioMix = new AudioMix();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<AudioMix> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, audioMix, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "AudioMix" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return audioMix;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return audioMix;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, AudioMix audioMix, String str) throws XmlPullParserException, IOException {
        if (audioMix == null) {
            return;
        }
        if (str == null) {
            str = "AudioMix";
        }
        xmlSerializer.startTag("", str);
        if (audioMix.audioSource != null) {
            xmlSerializer.startTag("", "AudioSource");
            xmlSerializer.text(String.valueOf(audioMix.audioSource));
            xmlSerializer.endTag("", "AudioSource");
        }
        if (audioMix.mixMode != null) {
            xmlSerializer.startTag("", "MixMode");
            xmlSerializer.text(String.valueOf(audioMix.mixMode));
            xmlSerializer.endTag("", "MixMode");
        }
        if (audioMix.replace != null) {
            xmlSerializer.startTag("", "Replace");
            xmlSerializer.text(String.valueOf(audioMix.replace));
            xmlSerializer.endTag("", "Replace");
        }
        if (audioMix.effectConfig != null) {
            QCloudXml.toXml(xmlSerializer, audioMix.effectConfig, "EffectConfig");
        }
        xmlSerializer.endTag("", str);
    }
}
