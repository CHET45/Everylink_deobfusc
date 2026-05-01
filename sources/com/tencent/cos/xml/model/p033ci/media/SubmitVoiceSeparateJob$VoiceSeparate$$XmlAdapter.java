package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitVoiceSeparateJob;
import com.tencent.cos.xml.model.p033ci.media.TemplateVoiceSeparate;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitVoiceSeparateJob$VoiceSeparate$$XmlAdapter extends IXmlAdapter<SubmitVoiceSeparateJob.VoiceSeparate> {
    private HashMap<String, ChildElementBinder<SubmitVoiceSeparateJob.VoiceSeparate>> childElementBinders;

    public SubmitVoiceSeparateJob$VoiceSeparate$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SubmitVoiceSeparateJob.VoiceSeparate>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("AudioMode", new ChildElementBinder<SubmitVoiceSeparateJob.VoiceSeparate>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVoiceSeparateJob$VoiceSeparate$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVoiceSeparateJob.VoiceSeparate voiceSeparate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                voiceSeparate.audioMode = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("AudioConfig", new ChildElementBinder<SubmitVoiceSeparateJob.VoiceSeparate>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVoiceSeparateJob$VoiceSeparate$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVoiceSeparateJob.VoiceSeparate voiceSeparate, String str) throws XmlPullParserException, IOException {
                voiceSeparate.audioConfig = (TemplateVoiceSeparate.AudioConfig) QCloudXml.fromXml(xmlPullParser, TemplateVoiceSeparate.AudioConfig.class, "AudioConfig");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitVoiceSeparateJob.VoiceSeparate fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitVoiceSeparateJob.VoiceSeparate voiceSeparate = new SubmitVoiceSeparateJob.VoiceSeparate();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitVoiceSeparateJob.VoiceSeparate> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, voiceSeparate, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "VoiceSeparate" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return voiceSeparate;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return voiceSeparate;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitVoiceSeparateJob.VoiceSeparate voiceSeparate, String str) throws XmlPullParserException, IOException {
        if (voiceSeparate == null) {
            return;
        }
        if (str == null) {
            str = "VoiceSeparate";
        }
        xmlSerializer.startTag("", str);
        if (voiceSeparate.audioMode != null) {
            xmlSerializer.startTag("", "AudioMode");
            xmlSerializer.text(String.valueOf(voiceSeparate.audioMode));
            xmlSerializer.endTag("", "AudioMode");
        }
        if (voiceSeparate.audioConfig != null) {
            QCloudXml.toXml(xmlSerializer, voiceSeparate.audioConfig, "AudioConfig");
        }
        xmlSerializer.endTag("", str);
    }
}
