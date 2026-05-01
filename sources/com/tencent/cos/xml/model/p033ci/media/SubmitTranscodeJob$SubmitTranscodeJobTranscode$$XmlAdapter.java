package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.blob.BlobConstants;
import com.tencent.cos.xml.model.p033ci.common.AudioMix;
import com.tencent.cos.xml.model.p033ci.media.SubmitTranscodeJob;
import com.tencent.cos.xml.model.p033ci.media.TemplateTranscode;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitTranscodeJob$SubmitTranscodeJobTranscode$$XmlAdapter extends IXmlAdapter<SubmitTranscodeJob.SubmitTranscodeJobTranscode> {
    private HashMap<String, ChildElementBinder<SubmitTranscodeJob.SubmitTranscodeJobTranscode>> childElementBinders;

    public SubmitTranscodeJob$SubmitTranscodeJobTranscode$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SubmitTranscodeJob.SubmitTranscodeJobTranscode>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("TimeInterval", new ChildElementBinder<SubmitTranscodeJob.SubmitTranscodeJobTranscode>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJob$SubmitTranscodeJobTranscode$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJob.SubmitTranscodeJobTranscode submitTranscodeJobTranscode, String str) throws XmlPullParserException, IOException {
                submitTranscodeJobTranscode.timeInterval = (TemplateTranscode.TemplateTranscodeTimeInterval) QCloudXml.fromXml(xmlPullParser, TemplateTranscode.TemplateTranscodeTimeInterval.class, "TimeInterval");
            }
        });
        this.childElementBinders.put(BlobConstants.CONTAINER_ELEMENT, new ChildElementBinder<SubmitTranscodeJob.SubmitTranscodeJobTranscode>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJob$SubmitTranscodeJobTranscode$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJob.SubmitTranscodeJobTranscode submitTranscodeJobTranscode, String str) throws XmlPullParserException, IOException {
                submitTranscodeJobTranscode.container = (TemplateTranscode.TemplateTranscodeContainer) QCloudXml.fromXml(xmlPullParser, TemplateTranscode.TemplateTranscodeContainer.class, BlobConstants.CONTAINER_ELEMENT);
            }
        });
        this.childElementBinders.put("Video", new ChildElementBinder<SubmitTranscodeJob.SubmitTranscodeJobTranscode>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJob$SubmitTranscodeJobTranscode$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJob.SubmitTranscodeJobTranscode submitTranscodeJobTranscode, String str) throws XmlPullParserException, IOException {
                submitTranscodeJobTranscode.video = (TemplateTranscode.TemplateTranscodeVideo) QCloudXml.fromXml(xmlPullParser, TemplateTranscode.TemplateTranscodeVideo.class, "Video");
            }
        });
        this.childElementBinders.put("Audio", new ChildElementBinder<SubmitTranscodeJob.SubmitTranscodeJobTranscode>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJob$SubmitTranscodeJobTranscode$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJob.SubmitTranscodeJobTranscode submitTranscodeJobTranscode, String str) throws XmlPullParserException, IOException {
                submitTranscodeJobTranscode.audio = (TemplateTranscode.TemplateTranscodeAudio) QCloudXml.fromXml(xmlPullParser, TemplateTranscode.TemplateTranscodeAudio.class, "Audio");
            }
        });
        this.childElementBinders.put("TransConfig", new ChildElementBinder<SubmitTranscodeJob.SubmitTranscodeJobTranscode>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJob$SubmitTranscodeJobTranscode$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJob.SubmitTranscodeJobTranscode submitTranscodeJobTranscode, String str) throws XmlPullParserException, IOException {
                submitTranscodeJobTranscode.transConfig = (TemplateTranscode.TemplateTranscodeTransConfig) QCloudXml.fromXml(xmlPullParser, TemplateTranscode.TemplateTranscodeTransConfig.class, "TransConfig");
            }
        });
        this.childElementBinders.put("AudioMix", new ChildElementBinder<SubmitTranscodeJob.SubmitTranscodeJobTranscode>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJob$SubmitTranscodeJobTranscode$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJob.SubmitTranscodeJobTranscode submitTranscodeJobTranscode, String str) throws XmlPullParserException, IOException {
                submitTranscodeJobTranscode.audioMix = (AudioMix) QCloudXml.fromXml(xmlPullParser, AudioMix.class, "AudioMix");
            }
        });
        this.childElementBinders.put("AudioMixArray", new ChildElementBinder<SubmitTranscodeJob.SubmitTranscodeJobTranscode>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJob$SubmitTranscodeJobTranscode$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJob.SubmitTranscodeJobTranscode submitTranscodeJobTranscode, String str) throws XmlPullParserException, IOException {
                if (submitTranscodeJobTranscode.audioMixArray == null) {
                    submitTranscodeJobTranscode.audioMixArray = new ArrayList();
                }
                submitTranscodeJobTranscode.audioMixArray.add((AudioMix) QCloudXml.fromXml(xmlPullParser, AudioMix.class, "AudioMixArray"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitTranscodeJob.SubmitTranscodeJobTranscode fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitTranscodeJob.SubmitTranscodeJobTranscode submitTranscodeJobTranscode = new SubmitTranscodeJob.SubmitTranscodeJobTranscode();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitTranscodeJob.SubmitTranscodeJobTranscode> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitTranscodeJobTranscode, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Transcode" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitTranscodeJobTranscode;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitTranscodeJobTranscode;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitTranscodeJob.SubmitTranscodeJobTranscode submitTranscodeJobTranscode, String str) throws XmlPullParserException, IOException {
        if (submitTranscodeJobTranscode == null) {
            return;
        }
        if (str == null) {
            str = "Transcode";
        }
        xmlSerializer.startTag("", str);
        if (submitTranscodeJobTranscode.timeInterval != null) {
            QCloudXml.toXml(xmlSerializer, submitTranscodeJobTranscode.timeInterval, "TimeInterval");
        }
        if (submitTranscodeJobTranscode.container != null) {
            QCloudXml.toXml(xmlSerializer, submitTranscodeJobTranscode.container, BlobConstants.CONTAINER_ELEMENT);
        }
        if (submitTranscodeJobTranscode.video != null) {
            QCloudXml.toXml(xmlSerializer, submitTranscodeJobTranscode.video, "Video");
        }
        if (submitTranscodeJobTranscode.audio != null) {
            QCloudXml.toXml(xmlSerializer, submitTranscodeJobTranscode.audio, "Audio");
        }
        if (submitTranscodeJobTranscode.transConfig != null) {
            QCloudXml.toXml(xmlSerializer, submitTranscodeJobTranscode.transConfig, "TransConfig");
        }
        if (submitTranscodeJobTranscode.audioMix != null) {
            QCloudXml.toXml(xmlSerializer, submitTranscodeJobTranscode.audioMix, "AudioMix");
        }
        if (submitTranscodeJobTranscode.audioMixArray != null) {
            for (int i = 0; i < submitTranscodeJobTranscode.audioMixArray.size(); i++) {
                QCloudXml.toXml(xmlSerializer, submitTranscodeJobTranscode.audioMixArray.get(i), "AudioMix");
            }
        }
        xmlSerializer.endTag("", str);
    }
}
