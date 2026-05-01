package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.blob.BlobConstants;
import com.tencent.cos.xml.model.p033ci.common.AudioMix;
import com.tencent.cos.xml.model.p033ci.media.TemplateVideoMontage;
import com.tencent.cos.xml.model.p033ci.media.TemplateVideoMontageResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.TemplateVideoMontageResponse$TemplateVideoMontageResponseVideoMontage$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3833x43364b98 extends IXmlAdapter<TemplateVideoMontageResponse.TemplateVideoMontageResponseVideoMontage> {
    private HashMap<String, ChildElementBinder<TemplateVideoMontageResponse.TemplateVideoMontageResponseVideoMontage>> childElementBinders;

    public C3833x43364b98() {
        HashMap<String, ChildElementBinder<TemplateVideoMontageResponse.TemplateVideoMontageResponseVideoMontage>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Duration", new ChildElementBinder<TemplateVideoMontageResponse.TemplateVideoMontageResponseVideoMontage>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVideoMontageResponse$TemplateVideoMontageResponseVideoMontage$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVideoMontageResponse.TemplateVideoMontageResponseVideoMontage templateVideoMontageResponseVideoMontage, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateVideoMontageResponseVideoMontage.duration = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(BlobConstants.CONTAINER_ELEMENT, new ChildElementBinder<TemplateVideoMontageResponse.TemplateVideoMontageResponseVideoMontage>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVideoMontageResponse$TemplateVideoMontageResponseVideoMontage$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVideoMontageResponse.TemplateVideoMontageResponseVideoMontage templateVideoMontageResponseVideoMontage, String str) throws XmlPullParserException, IOException {
                templateVideoMontageResponseVideoMontage.container = (TemplateVideoMontage.TemplateVideoMontageContainer) QCloudXml.fromXml(xmlPullParser, TemplateVideoMontage.TemplateVideoMontageContainer.class, BlobConstants.CONTAINER_ELEMENT);
            }
        });
        this.childElementBinders.put("Video", new ChildElementBinder<TemplateVideoMontageResponse.TemplateVideoMontageResponseVideoMontage>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVideoMontageResponse$TemplateVideoMontageResponseVideoMontage$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVideoMontageResponse.TemplateVideoMontageResponseVideoMontage templateVideoMontageResponseVideoMontage, String str) throws XmlPullParserException, IOException {
                templateVideoMontageResponseVideoMontage.video = (TemplateVideoMontage.TemplateVideoMontageVideo) QCloudXml.fromXml(xmlPullParser, TemplateVideoMontage.TemplateVideoMontageVideo.class, "Video");
            }
        });
        this.childElementBinders.put("Audio", new ChildElementBinder<TemplateVideoMontageResponse.TemplateVideoMontageResponseVideoMontage>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVideoMontageResponse$TemplateVideoMontageResponseVideoMontage$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVideoMontageResponse.TemplateVideoMontageResponseVideoMontage templateVideoMontageResponseVideoMontage, String str) throws XmlPullParserException, IOException {
                templateVideoMontageResponseVideoMontage.audio = (TemplateVideoMontage.TemplateVideoMontageAudio) QCloudXml.fromXml(xmlPullParser, TemplateVideoMontage.TemplateVideoMontageAudio.class, "Audio");
            }
        });
        this.childElementBinders.put("Scene", new ChildElementBinder<TemplateVideoMontageResponse.TemplateVideoMontageResponseVideoMontage>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVideoMontageResponse$TemplateVideoMontageResponseVideoMontage$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVideoMontageResponse.TemplateVideoMontageResponseVideoMontage templateVideoMontageResponseVideoMontage, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateVideoMontageResponseVideoMontage.scene = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("AudioMix", new ChildElementBinder<TemplateVideoMontageResponse.TemplateVideoMontageResponseVideoMontage>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVideoMontageResponse$TemplateVideoMontageResponseVideoMontage$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVideoMontageResponse.TemplateVideoMontageResponseVideoMontage templateVideoMontageResponseVideoMontage, String str) throws XmlPullParserException, IOException {
                templateVideoMontageResponseVideoMontage.audioMix = (AudioMix) QCloudXml.fromXml(xmlPullParser, AudioMix.class, "AudioMix");
            }
        });
        this.childElementBinders.put("AudioMixArray", new ChildElementBinder<TemplateVideoMontageResponse.TemplateVideoMontageResponseVideoMontage>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVideoMontageResponse$TemplateVideoMontageResponseVideoMontage$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVideoMontageResponse.TemplateVideoMontageResponseVideoMontage templateVideoMontageResponseVideoMontage, String str) throws XmlPullParserException, IOException {
                if (templateVideoMontageResponseVideoMontage.audioMixArray == null) {
                    templateVideoMontageResponseVideoMontage.audioMixArray = new ArrayList();
                }
                templateVideoMontageResponseVideoMontage.audioMixArray.add((AudioMix) QCloudXml.fromXml(xmlPullParser, AudioMix.class, "AudioMixArray"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateVideoMontageResponse.TemplateVideoMontageResponseVideoMontage fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateVideoMontageResponse.TemplateVideoMontageResponseVideoMontage templateVideoMontageResponseVideoMontage = new TemplateVideoMontageResponse.TemplateVideoMontageResponseVideoMontage();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateVideoMontageResponse.TemplateVideoMontageResponseVideoMontage> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templateVideoMontageResponseVideoMontage, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "VideoMontage" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templateVideoMontageResponseVideoMontage;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templateVideoMontageResponseVideoMontage;
    }
}
