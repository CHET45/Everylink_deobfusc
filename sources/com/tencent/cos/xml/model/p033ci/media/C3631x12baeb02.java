package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.blob.BlobConstants;
import com.tencent.cos.xml.model.p033ci.common.AudioMix;
import com.tencent.cos.xml.model.p033ci.media.SubmitVideoMontageJob;
import com.tencent.cos.xml.model.p033ci.media.TemplateTranscode;
import com.tencent.cos.xml.model.p033ci.media.TemplateVideoMontage;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitVideoMontageJob$SubmitVideoMontageJobVideoMontage$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3631x12baeb02 extends IXmlAdapter<SubmitVideoMontageJob.SubmitVideoMontageJobVideoMontage> {
    private HashMap<String, ChildElementBinder<SubmitVideoMontageJob.SubmitVideoMontageJobVideoMontage>> childElementBinders;

    public C3631x12baeb02() {
        HashMap<String, ChildElementBinder<SubmitVideoMontageJob.SubmitVideoMontageJobVideoMontage>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(BlobConstants.CONTAINER_ELEMENT, new ChildElementBinder<SubmitVideoMontageJob.SubmitVideoMontageJobVideoMontage>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoMontageJob$SubmitVideoMontageJobVideoMontage$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoMontageJob.SubmitVideoMontageJobVideoMontage submitVideoMontageJobVideoMontage, String str) throws XmlPullParserException, IOException {
                submitVideoMontageJobVideoMontage.container = (TemplateVideoMontage.TemplateVideoMontageContainer) QCloudXml.fromXml(xmlPullParser, TemplateVideoMontage.TemplateVideoMontageContainer.class, BlobConstants.CONTAINER_ELEMENT);
            }
        });
        this.childElementBinders.put("Video", new ChildElementBinder<SubmitVideoMontageJob.SubmitVideoMontageJobVideoMontage>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoMontageJob$SubmitVideoMontageJobVideoMontage$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoMontageJob.SubmitVideoMontageJobVideoMontage submitVideoMontageJobVideoMontage, String str) throws XmlPullParserException, IOException {
                submitVideoMontageJobVideoMontage.video = (TemplateVideoMontage.TemplateVideoMontageVideo) QCloudXml.fromXml(xmlPullParser, TemplateVideoMontage.TemplateVideoMontageVideo.class, "Video");
            }
        });
        this.childElementBinders.put("Audio", new ChildElementBinder<SubmitVideoMontageJob.SubmitVideoMontageJobVideoMontage>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoMontageJob$SubmitVideoMontageJobVideoMontage$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoMontageJob.SubmitVideoMontageJobVideoMontage submitVideoMontageJobVideoMontage, String str) throws XmlPullParserException, IOException {
                submitVideoMontageJobVideoMontage.audio = (TemplateVideoMontage.TemplateVideoMontageAudio) QCloudXml.fromXml(xmlPullParser, TemplateVideoMontage.TemplateVideoMontageAudio.class, "Audio");
            }
        });
        this.childElementBinders.put("TransConfig", new ChildElementBinder<SubmitVideoMontageJob.SubmitVideoMontageJobVideoMontage>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoMontageJob$SubmitVideoMontageJobVideoMontage$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoMontageJob.SubmitVideoMontageJobVideoMontage submitVideoMontageJobVideoMontage, String str) throws XmlPullParserException, IOException {
                submitVideoMontageJobVideoMontage.transConfig = (TemplateTranscode.TemplateTranscodeTransConfig) QCloudXml.fromXml(xmlPullParser, TemplateTranscode.TemplateTranscodeTransConfig.class, "TransConfig");
            }
        });
        this.childElementBinders.put("AudioMix", new ChildElementBinder<SubmitVideoMontageJob.SubmitVideoMontageJobVideoMontage>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoMontageJob$SubmitVideoMontageJobVideoMontage$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoMontageJob.SubmitVideoMontageJobVideoMontage submitVideoMontageJobVideoMontage, String str) throws XmlPullParserException, IOException {
                submitVideoMontageJobVideoMontage.audioMix = (AudioMix) QCloudXml.fromXml(xmlPullParser, AudioMix.class, "AudioMix");
            }
        });
        this.childElementBinders.put("AudioMixArray", new ChildElementBinder<SubmitVideoMontageJob.SubmitVideoMontageJobVideoMontage>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoMontageJob$SubmitVideoMontageJobVideoMontage$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoMontageJob.SubmitVideoMontageJobVideoMontage submitVideoMontageJobVideoMontage, String str) throws XmlPullParserException, IOException {
                if (submitVideoMontageJobVideoMontage.audioMixArray == null) {
                    submitVideoMontageJobVideoMontage.audioMixArray = new ArrayList();
                }
                submitVideoMontageJobVideoMontage.audioMixArray.add((AudioMix) QCloudXml.fromXml(xmlPullParser, AudioMix.class, "AudioMixArray"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitVideoMontageJob.SubmitVideoMontageJobVideoMontage fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitVideoMontageJob.SubmitVideoMontageJobVideoMontage submitVideoMontageJobVideoMontage = new SubmitVideoMontageJob.SubmitVideoMontageJobVideoMontage();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitVideoMontageJob.SubmitVideoMontageJobVideoMontage> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitVideoMontageJobVideoMontage, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Transcode" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitVideoMontageJobVideoMontage;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitVideoMontageJobVideoMontage;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitVideoMontageJob.SubmitVideoMontageJobVideoMontage submitVideoMontageJobVideoMontage, String str) throws XmlPullParserException, IOException {
        if (submitVideoMontageJobVideoMontage == null) {
            return;
        }
        if (str == null) {
            str = "Transcode";
        }
        xmlSerializer.startTag("", str);
        if (submitVideoMontageJobVideoMontage.container != null) {
            QCloudXml.toXml(xmlSerializer, submitVideoMontageJobVideoMontage.container, BlobConstants.CONTAINER_ELEMENT);
        }
        if (submitVideoMontageJobVideoMontage.video != null) {
            QCloudXml.toXml(xmlSerializer, submitVideoMontageJobVideoMontage.video, "Video");
        }
        if (submitVideoMontageJobVideoMontage.audio != null) {
            QCloudXml.toXml(xmlSerializer, submitVideoMontageJobVideoMontage.audio, "Audio");
        }
        if (submitVideoMontageJobVideoMontage.transConfig != null) {
            QCloudXml.toXml(xmlSerializer, submitVideoMontageJobVideoMontage.transConfig, "TransConfig");
        }
        if (submitVideoMontageJobVideoMontage.audioMix != null) {
            QCloudXml.toXml(xmlSerializer, submitVideoMontageJobVideoMontage.audioMix, "AudioMix");
        }
        if (submitVideoMontageJobVideoMontage.audioMixArray != null) {
            for (int i = 0; i < submitVideoMontageJobVideoMontage.audioMixArray.size(); i++) {
                QCloudXml.toXml(xmlSerializer, submitVideoMontageJobVideoMontage.audioMixArray.get(i), "AudioMix");
            }
        }
        xmlSerializer.endTag("", str);
    }
}
