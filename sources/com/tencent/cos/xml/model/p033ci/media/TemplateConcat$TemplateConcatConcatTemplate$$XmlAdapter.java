package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.blob.BlobConstants;
import com.tencent.cos.xml.model.p033ci.common.AudioMix;
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
public class TemplateConcat$TemplateConcatConcatTemplate$$XmlAdapter extends IXmlAdapter<TemplateConcat.TemplateConcatConcatTemplate> {
    private HashMap<String, ChildElementBinder<TemplateConcat.TemplateConcatConcatTemplate>> childElementBinders;

    public TemplateConcat$TemplateConcatConcatTemplate$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TemplateConcat.TemplateConcatConcatTemplate>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("ConcatFragment", new ChildElementBinder<TemplateConcat.TemplateConcatConcatTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateConcat$TemplateConcatConcatTemplate$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateConcat.TemplateConcatConcatTemplate templateConcatConcatTemplate, String str) throws XmlPullParserException, IOException {
                if (templateConcatConcatTemplate.concatFragment == null) {
                    templateConcatConcatTemplate.concatFragment = new ArrayList();
                }
                templateConcatConcatTemplate.concatFragment.add((TemplateConcat.TemplateConcatConcatFragment) QCloudXml.fromXml(xmlPullParser, TemplateConcat.TemplateConcatConcatFragment.class, "ConcatFragment"));
            }
        });
        this.childElementBinders.put("Audio", new ChildElementBinder<TemplateConcat.TemplateConcatConcatTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateConcat$TemplateConcatConcatTemplate$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateConcat.TemplateConcatConcatTemplate templateConcatConcatTemplate, String str) throws XmlPullParserException, IOException {
                templateConcatConcatTemplate.audio = (TemplateConcat.TemplateConcatAudio) QCloudXml.fromXml(xmlPullParser, TemplateConcat.TemplateConcatAudio.class, "Audio");
            }
        });
        this.childElementBinders.put("Video", new ChildElementBinder<TemplateConcat.TemplateConcatConcatTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateConcat$TemplateConcatConcatTemplate$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateConcat.TemplateConcatConcatTemplate templateConcatConcatTemplate, String str) throws XmlPullParserException, IOException {
                templateConcatConcatTemplate.video = (TemplateConcat.TemplateConcatVideo) QCloudXml.fromXml(xmlPullParser, TemplateConcat.TemplateConcatVideo.class, "Video");
            }
        });
        this.childElementBinders.put(BlobConstants.CONTAINER_ELEMENT, new ChildElementBinder<TemplateConcat.TemplateConcatConcatTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateConcat$TemplateConcatConcatTemplate$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateConcat.TemplateConcatConcatTemplate templateConcatConcatTemplate, String str) throws XmlPullParserException, IOException {
                templateConcatConcatTemplate.container = (TemplateConcat.TemplateConcatContainer) QCloudXml.fromXml(xmlPullParser, TemplateConcat.TemplateConcatContainer.class, BlobConstants.CONTAINER_ELEMENT);
            }
        });
        this.childElementBinders.put("DirectConcat", new ChildElementBinder<TemplateConcat.TemplateConcatConcatTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateConcat$TemplateConcatConcatTemplate$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateConcat.TemplateConcatConcatTemplate templateConcatConcatTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateConcatConcatTemplate.directConcat = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SceneChangeInfo", new ChildElementBinder<TemplateConcat.TemplateConcatConcatTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateConcat$TemplateConcatConcatTemplate$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateConcat.TemplateConcatConcatTemplate templateConcatConcatTemplate, String str) throws XmlPullParserException, IOException {
                templateConcatConcatTemplate.sceneChangeInfo = (TemplateConcat.TemplateConcatSceneChangeInfo) QCloudXml.fromXml(xmlPullParser, TemplateConcat.TemplateConcatSceneChangeInfo.class, "SceneChangeInfo");
            }
        });
        this.childElementBinders.put("AudioMix", new ChildElementBinder<TemplateConcat.TemplateConcatConcatTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateConcat$TemplateConcatConcatTemplate$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateConcat.TemplateConcatConcatTemplate templateConcatConcatTemplate, String str) throws XmlPullParserException, IOException {
                templateConcatConcatTemplate.audioMix = (AudioMix) QCloudXml.fromXml(xmlPullParser, AudioMix.class, "AudioMix");
            }
        });
        this.childElementBinders.put("AudioMixArray", new ChildElementBinder<TemplateConcat.TemplateConcatConcatTemplate>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateConcat$TemplateConcatConcatTemplate$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateConcat.TemplateConcatConcatTemplate templateConcatConcatTemplate, String str) throws XmlPullParserException, IOException {
                if (templateConcatConcatTemplate.audioMixArray == null) {
                    templateConcatConcatTemplate.audioMixArray = new ArrayList();
                }
                templateConcatConcatTemplate.audioMixArray.add((AudioMix) QCloudXml.fromXml(xmlPullParser, AudioMix.class, "AudioMixArray"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateConcat.TemplateConcatConcatTemplate fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateConcat.TemplateConcatConcatTemplate templateConcatConcatTemplate = new TemplateConcat.TemplateConcatConcatTemplate();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateConcat.TemplateConcatConcatTemplate> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templateConcatConcatTemplate, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "ConcatTemplate" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templateConcatConcatTemplate;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templateConcatConcatTemplate;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, TemplateConcat.TemplateConcatConcatTemplate templateConcatConcatTemplate, String str) throws XmlPullParserException, IOException {
        if (templateConcatConcatTemplate == null) {
            return;
        }
        if (str == null) {
            str = "ConcatTemplate";
        }
        xmlSerializer.startTag("", str);
        if (templateConcatConcatTemplate.concatFragment != null) {
            for (int i = 0; i < templateConcatConcatTemplate.concatFragment.size(); i++) {
                QCloudXml.toXml(xmlSerializer, templateConcatConcatTemplate.concatFragment.get(i), "ConcatFragment");
            }
        }
        if (templateConcatConcatTemplate.audio != null) {
            QCloudXml.toXml(xmlSerializer, templateConcatConcatTemplate.audio, "Audio");
        }
        if (templateConcatConcatTemplate.video != null) {
            QCloudXml.toXml(xmlSerializer, templateConcatConcatTemplate.video, "Video");
        }
        if (templateConcatConcatTemplate.container != null) {
            QCloudXml.toXml(xmlSerializer, templateConcatConcatTemplate.container, BlobConstants.CONTAINER_ELEMENT);
        }
        if (templateConcatConcatTemplate.directConcat != null) {
            xmlSerializer.startTag("", "DirectConcat");
            xmlSerializer.text(String.valueOf(templateConcatConcatTemplate.directConcat));
            xmlSerializer.endTag("", "DirectConcat");
        }
        if (templateConcatConcatTemplate.sceneChangeInfo != null) {
            QCloudXml.toXml(xmlSerializer, templateConcatConcatTemplate.sceneChangeInfo, "SceneChangeInfo");
        }
        if (templateConcatConcatTemplate.audioMix != null) {
            QCloudXml.toXml(xmlSerializer, templateConcatConcatTemplate.audioMix, "AudioMix");
        }
        if (templateConcatConcatTemplate.audioMixArray != null) {
            for (int i2 = 0; i2 < templateConcatConcatTemplate.audioMixArray.size(); i2++) {
                QCloudXml.toXml(xmlSerializer, templateConcatConcatTemplate.audioMixArray.get(i2), "AudioMix");
            }
        }
        xmlSerializer.endTag("", str);
    }
}
