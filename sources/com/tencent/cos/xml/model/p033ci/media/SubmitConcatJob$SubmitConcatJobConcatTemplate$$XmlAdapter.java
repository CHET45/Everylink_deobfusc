package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.blob.BlobConstants;
import com.tencent.cos.xml.model.p033ci.common.AudioMix;
import com.tencent.cos.xml.model.p033ci.media.SubmitConcatJob;
import com.tencent.cos.xml.model.p033ci.media.TemplateConcat;
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
public class SubmitConcatJob$SubmitConcatJobConcatTemplate$$XmlAdapter extends IXmlAdapter<SubmitConcatJob.SubmitConcatJobConcatTemplate> {
    private HashMap<String, ChildElementBinder<SubmitConcatJob.SubmitConcatJobConcatTemplate>> childElementBinders;

    public SubmitConcatJob$SubmitConcatJobConcatTemplate$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SubmitConcatJob.SubmitConcatJobConcatTemplate>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("ConcatFragment", new ChildElementBinder<SubmitConcatJob.SubmitConcatJobConcatTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitConcatJob$SubmitConcatJobConcatTemplate$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitConcatJob.SubmitConcatJobConcatTemplate submitConcatJobConcatTemplate, String str) throws XmlPullParserException, IOException {
                if (submitConcatJobConcatTemplate.concatFragment == null) {
                    submitConcatJobConcatTemplate.concatFragment = new ArrayList();
                }
                submitConcatJobConcatTemplate.concatFragment.add((SubmitConcatJob.SubmitConcatJobConcatFragment) QCloudXml.fromXml(xmlPullParser, SubmitConcatJob.SubmitConcatJobConcatFragment.class, "ConcatFragment"));
            }
        });
        this.childElementBinders.put("Audio", new ChildElementBinder<SubmitConcatJob.SubmitConcatJobConcatTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitConcatJob$SubmitConcatJobConcatTemplate$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitConcatJob.SubmitConcatJobConcatTemplate submitConcatJobConcatTemplate, String str) throws XmlPullParserException, IOException {
                submitConcatJobConcatTemplate.audio = (TemplateConcat.TemplateConcatAudio) QCloudXml.fromXml(xmlPullParser, TemplateConcat.TemplateConcatAudio.class, "Audio");
            }
        });
        this.childElementBinders.put("Video", new ChildElementBinder<SubmitConcatJob.SubmitConcatJobConcatTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitConcatJob$SubmitConcatJobConcatTemplate$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitConcatJob.SubmitConcatJobConcatTemplate submitConcatJobConcatTemplate, String str) throws XmlPullParserException, IOException {
                submitConcatJobConcatTemplate.video = (TemplateConcat.TemplateConcatVideo) QCloudXml.fromXml(xmlPullParser, TemplateConcat.TemplateConcatVideo.class, "Video");
            }
        });
        this.childElementBinders.put(BlobConstants.CONTAINER_ELEMENT, new ChildElementBinder<SubmitConcatJob.SubmitConcatJobConcatTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitConcatJob$SubmitConcatJobConcatTemplate$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitConcatJob.SubmitConcatJobConcatTemplate submitConcatJobConcatTemplate, String str) throws XmlPullParserException, IOException {
                submitConcatJobConcatTemplate.container = (TemplateConcat.TemplateConcatContainer) QCloudXml.fromXml(xmlPullParser, TemplateConcat.TemplateConcatContainer.class, BlobConstants.CONTAINER_ELEMENT);
            }
        });
        this.childElementBinders.put("AudioMix", new ChildElementBinder<SubmitConcatJob.SubmitConcatJobConcatTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitConcatJob$SubmitConcatJobConcatTemplate$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitConcatJob.SubmitConcatJobConcatTemplate submitConcatJobConcatTemplate, String str) throws XmlPullParserException, IOException {
                submitConcatJobConcatTemplate.audioMix = (AudioMix) QCloudXml.fromXml(xmlPullParser, AudioMix.class, "AudioMix");
            }
        });
        this.childElementBinders.put("AudioMixArray", new ChildElementBinder<SubmitConcatJob.SubmitConcatJobConcatTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitConcatJob$SubmitConcatJobConcatTemplate$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitConcatJob.SubmitConcatJobConcatTemplate submitConcatJobConcatTemplate, String str) throws XmlPullParserException, IOException {
                if (submitConcatJobConcatTemplate.audioMixArray == null) {
                    submitConcatJobConcatTemplate.audioMixArray = new ArrayList();
                }
                submitConcatJobConcatTemplate.audioMixArray.add((AudioMix) QCloudXml.fromXml(xmlPullParser, AudioMix.class, "AudioMixArray"));
            }
        });
        this.childElementBinders.put("Index", new ChildElementBinder<SubmitConcatJob.SubmitConcatJobConcatTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitConcatJob$SubmitConcatJobConcatTemplate$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitConcatJob.SubmitConcatJobConcatTemplate submitConcatJobConcatTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitConcatJobConcatTemplate.index = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("DirectConcat", new ChildElementBinder<SubmitConcatJob.SubmitConcatJobConcatTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitConcatJob$SubmitConcatJobConcatTemplate$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitConcatJob.SubmitConcatJobConcatTemplate submitConcatJobConcatTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitConcatJobConcatTemplate.directConcat = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitConcatJob.SubmitConcatJobConcatTemplate fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitConcatJob.SubmitConcatJobConcatTemplate submitConcatJobConcatTemplate = new SubmitConcatJob.SubmitConcatJobConcatTemplate();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitConcatJob.SubmitConcatJobConcatTemplate> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitConcatJobConcatTemplate, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "ConcatTemplate" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitConcatJobConcatTemplate;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitConcatJobConcatTemplate;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitConcatJob.SubmitConcatJobConcatTemplate submitConcatJobConcatTemplate, String str) throws XmlPullParserException, IOException {
        if (submitConcatJobConcatTemplate == null) {
            return;
        }
        if (str == null) {
            str = "ConcatTemplate";
        }
        xmlSerializer.startTag("", str);
        if (submitConcatJobConcatTemplate.concatFragment != null) {
            for (int i = 0; i < submitConcatJobConcatTemplate.concatFragment.size(); i++) {
                QCloudXml.toXml(xmlSerializer, submitConcatJobConcatTemplate.concatFragment.get(i), "ConcatFragment");
            }
        }
        if (submitConcatJobConcatTemplate.audio != null) {
            QCloudXml.toXml(xmlSerializer, submitConcatJobConcatTemplate.audio, "Audio");
        }
        if (submitConcatJobConcatTemplate.video != null) {
            QCloudXml.toXml(xmlSerializer, submitConcatJobConcatTemplate.video, "Video");
        }
        if (submitConcatJobConcatTemplate.container != null) {
            QCloudXml.toXml(xmlSerializer, submitConcatJobConcatTemplate.container, BlobConstants.CONTAINER_ELEMENT);
        }
        if (submitConcatJobConcatTemplate.audioMix != null) {
            QCloudXml.toXml(xmlSerializer, submitConcatJobConcatTemplate.audioMix, "AudioMix");
        }
        if (submitConcatJobConcatTemplate.audioMixArray != null) {
            for (int i2 = 0; i2 < submitConcatJobConcatTemplate.audioMixArray.size(); i2++) {
                QCloudXml.toXml(xmlSerializer, submitConcatJobConcatTemplate.audioMixArray.get(i2), "AudioMix");
            }
        }
        if (submitConcatJobConcatTemplate.index != null) {
            xmlSerializer.startTag("", "Index");
            xmlSerializer.text(String.valueOf(submitConcatJobConcatTemplate.index));
            xmlSerializer.endTag("", "Index");
        }
        if (submitConcatJobConcatTemplate.directConcat != null) {
            xmlSerializer.startTag("", "DirectConcat");
            xmlSerializer.text(String.valueOf(submitConcatJobConcatTemplate.directConcat));
            xmlSerializer.endTag("", "DirectConcat");
        }
        xmlSerializer.endTag("", str);
    }
}
