package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.TemplateConcat;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateConcat$TemplateConcatAudio$$XmlAdapter extends IXmlAdapter<TemplateConcat.TemplateConcatAudio> {
    private HashMap<String, ChildElementBinder<TemplateConcat.TemplateConcatAudio>> childElementBinders;

    public TemplateConcat$TemplateConcatAudio$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TemplateConcat.TemplateConcatAudio>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Codec", new ChildElementBinder<TemplateConcat.TemplateConcatAudio>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateConcat$TemplateConcatAudio$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateConcat.TemplateConcatAudio templateConcatAudio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateConcatAudio.codec = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Samplerate", new ChildElementBinder<TemplateConcat.TemplateConcatAudio>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateConcat$TemplateConcatAudio$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateConcat.TemplateConcatAudio templateConcatAudio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateConcatAudio.samplerate = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Bitrate", new ChildElementBinder<TemplateConcat.TemplateConcatAudio>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateConcat$TemplateConcatAudio$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateConcat.TemplateConcatAudio templateConcatAudio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateConcatAudio.bitrate = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Channels", new ChildElementBinder<TemplateConcat.TemplateConcatAudio>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateConcat$TemplateConcatAudio$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateConcat.TemplateConcatAudio templateConcatAudio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateConcatAudio.channels = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateConcat.TemplateConcatAudio fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateConcat.TemplateConcatAudio templateConcatAudio = new TemplateConcat.TemplateConcatAudio();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateConcat.TemplateConcatAudio> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templateConcatAudio, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Audio" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templateConcatAudio;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templateConcatAudio;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, TemplateConcat.TemplateConcatAudio templateConcatAudio, String str) throws XmlPullParserException, IOException {
        if (templateConcatAudio == null) {
            return;
        }
        if (str == null) {
            str = "Audio";
        }
        xmlSerializer.startTag("", str);
        if (templateConcatAudio.codec != null) {
            xmlSerializer.startTag("", "Codec");
            xmlSerializer.text(String.valueOf(templateConcatAudio.codec));
            xmlSerializer.endTag("", "Codec");
        }
        if (templateConcatAudio.samplerate != null) {
            xmlSerializer.startTag("", "Samplerate");
            xmlSerializer.text(String.valueOf(templateConcatAudio.samplerate));
            xmlSerializer.endTag("", "Samplerate");
        }
        if (templateConcatAudio.bitrate != null) {
            xmlSerializer.startTag("", "Bitrate");
            xmlSerializer.text(String.valueOf(templateConcatAudio.bitrate));
            xmlSerializer.endTag("", "Bitrate");
        }
        if (templateConcatAudio.channels != null) {
            xmlSerializer.startTag("", "Channels");
            xmlSerializer.text(String.valueOf(templateConcatAudio.channels));
            xmlSerializer.endTag("", "Channels");
        }
        xmlSerializer.endTag("", str);
    }
}
