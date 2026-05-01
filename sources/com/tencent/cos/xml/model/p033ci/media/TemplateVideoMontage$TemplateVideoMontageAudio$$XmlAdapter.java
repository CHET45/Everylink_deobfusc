package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.TemplateVideoMontage;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateVideoMontage$TemplateVideoMontageAudio$$XmlAdapter extends IXmlAdapter<TemplateVideoMontage.TemplateVideoMontageAudio> {
    private HashMap<String, ChildElementBinder<TemplateVideoMontage.TemplateVideoMontageAudio>> childElementBinders;

    public TemplateVideoMontage$TemplateVideoMontageAudio$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TemplateVideoMontage.TemplateVideoMontageAudio>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Codec", new ChildElementBinder<TemplateVideoMontage.TemplateVideoMontageAudio>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVideoMontage$TemplateVideoMontageAudio$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVideoMontage.TemplateVideoMontageAudio templateVideoMontageAudio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateVideoMontageAudio.codec = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Samplerate", new ChildElementBinder<TemplateVideoMontage.TemplateVideoMontageAudio>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVideoMontage$TemplateVideoMontageAudio$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVideoMontage.TemplateVideoMontageAudio templateVideoMontageAudio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateVideoMontageAudio.samplerate = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Bitrate", new ChildElementBinder<TemplateVideoMontage.TemplateVideoMontageAudio>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVideoMontage$TemplateVideoMontageAudio$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVideoMontage.TemplateVideoMontageAudio templateVideoMontageAudio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateVideoMontageAudio.bitrate = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Channels", new ChildElementBinder<TemplateVideoMontage.TemplateVideoMontageAudio>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVideoMontage$TemplateVideoMontageAudio$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVideoMontage.TemplateVideoMontageAudio templateVideoMontageAudio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateVideoMontageAudio.channels = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Remove", new ChildElementBinder<TemplateVideoMontage.TemplateVideoMontageAudio>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVideoMontage$TemplateVideoMontageAudio$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVideoMontage.TemplateVideoMontageAudio templateVideoMontageAudio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateVideoMontageAudio.remove = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateVideoMontage.TemplateVideoMontageAudio fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateVideoMontage.TemplateVideoMontageAudio templateVideoMontageAudio = new TemplateVideoMontage.TemplateVideoMontageAudio();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateVideoMontage.TemplateVideoMontageAudio> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templateVideoMontageAudio, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Audio" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templateVideoMontageAudio;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templateVideoMontageAudio;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, TemplateVideoMontage.TemplateVideoMontageAudio templateVideoMontageAudio, String str) throws XmlPullParserException, IOException {
        if (templateVideoMontageAudio == null) {
            return;
        }
        if (str == null) {
            str = "Audio";
        }
        xmlSerializer.startTag("", str);
        if (templateVideoMontageAudio.codec != null) {
            xmlSerializer.startTag("", "Codec");
            xmlSerializer.text(String.valueOf(templateVideoMontageAudio.codec));
            xmlSerializer.endTag("", "Codec");
        }
        if (templateVideoMontageAudio.samplerate != null) {
            xmlSerializer.startTag("", "Samplerate");
            xmlSerializer.text(String.valueOf(templateVideoMontageAudio.samplerate));
            xmlSerializer.endTag("", "Samplerate");
        }
        if (templateVideoMontageAudio.bitrate != null) {
            xmlSerializer.startTag("", "Bitrate");
            xmlSerializer.text(String.valueOf(templateVideoMontageAudio.bitrate));
            xmlSerializer.endTag("", "Bitrate");
        }
        if (templateVideoMontageAudio.channels != null) {
            xmlSerializer.startTag("", "Channels");
            xmlSerializer.text(String.valueOf(templateVideoMontageAudio.channels));
            xmlSerializer.endTag("", "Channels");
        }
        if (templateVideoMontageAudio.remove != null) {
            xmlSerializer.startTag("", "Remove");
            xmlSerializer.text(String.valueOf(templateVideoMontageAudio.remove));
            xmlSerializer.endTag("", "Remove");
        }
        xmlSerializer.endTag("", str);
    }
}
