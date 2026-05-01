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
public class VoiceSeparateTempleteResponseVoiceSeparate$$XmlAdapter extends IXmlAdapter<VoiceSeparateTempleteResponseVoiceSeparate> {
    private HashMap<String, ChildElementBinder<VoiceSeparateTempleteResponseVoiceSeparate>> childElementBinders;

    public VoiceSeparateTempleteResponseVoiceSeparate$$XmlAdapter() {
        HashMap<String, ChildElementBinder<VoiceSeparateTempleteResponseVoiceSeparate>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("AudioMode", new ChildElementBinder<VoiceSeparateTempleteResponseVoiceSeparate>() { // from class: com.tencent.cos.xml.model.ci.common.VoiceSeparateTempleteResponseVoiceSeparate$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VoiceSeparateTempleteResponseVoiceSeparate voiceSeparateTempleteResponseVoiceSeparate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                voiceSeparateTempleteResponseVoiceSeparate.audioMode = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("AudioConfig", new ChildElementBinder<VoiceSeparateTempleteResponseVoiceSeparate>() { // from class: com.tencent.cos.xml.model.ci.common.VoiceSeparateTempleteResponseVoiceSeparate$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VoiceSeparateTempleteResponseVoiceSeparate voiceSeparateTempleteResponseVoiceSeparate, String str) throws XmlPullParserException, IOException {
                voiceSeparateTempleteResponseVoiceSeparate.audioConfig = (AudioConfig) QCloudXml.fromXml(xmlPullParser, AudioConfig.class, "AudioConfig");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public VoiceSeparateTempleteResponseVoiceSeparate fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        VoiceSeparateTempleteResponseVoiceSeparate voiceSeparateTempleteResponseVoiceSeparate = new VoiceSeparateTempleteResponseVoiceSeparate();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<VoiceSeparateTempleteResponseVoiceSeparate> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, voiceSeparateTempleteResponseVoiceSeparate, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "VoiceSeparateTempleteResponseVoiceSeparate" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return voiceSeparateTempleteResponseVoiceSeparate;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return voiceSeparateTempleteResponseVoiceSeparate;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, VoiceSeparateTempleteResponseVoiceSeparate voiceSeparateTempleteResponseVoiceSeparate, String str) throws XmlPullParserException, IOException {
        if (voiceSeparateTempleteResponseVoiceSeparate == null) {
            return;
        }
        if (str == null) {
            str = "VoiceSeparateTempleteResponseVoiceSeparate";
        }
        xmlSerializer.startTag("", str);
        if (voiceSeparateTempleteResponseVoiceSeparate.audioMode != null) {
            xmlSerializer.startTag("", "AudioMode");
            xmlSerializer.text(String.valueOf(voiceSeparateTempleteResponseVoiceSeparate.audioMode));
            xmlSerializer.endTag("", "AudioMode");
        }
        if (voiceSeparateTempleteResponseVoiceSeparate.audioConfig != null) {
            QCloudXml.toXml(xmlSerializer, voiceSeparateTempleteResponseVoiceSeparate.audioConfig, "AudioConfig");
        }
        xmlSerializer.endTag("", str);
    }
}
