package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.TemplateTranscode;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateTranscode$TemplateTranscodeVideo$$XmlAdapter extends IXmlAdapter<TemplateTranscode.TemplateTranscodeVideo> {
    private HashMap<String, ChildElementBinder<TemplateTranscode.TemplateTranscodeVideo>> childElementBinders;

    public TemplateTranscode$TemplateTranscodeVideo$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TemplateTranscode.TemplateTranscodeVideo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Codec", new ChildElementBinder<TemplateTranscode.TemplateTranscodeVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeVideo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeVideo templateTranscodeVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeVideo.codec = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Width", new ChildElementBinder<TemplateTranscode.TemplateTranscodeVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeVideo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeVideo templateTranscodeVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeVideo.width = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Height", new ChildElementBinder<TemplateTranscode.TemplateTranscodeVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeVideo$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeVideo templateTranscodeVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeVideo.height = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Fps", new ChildElementBinder<TemplateTranscode.TemplateTranscodeVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeVideo$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeVideo templateTranscodeVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeVideo.fps = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Remove", new ChildElementBinder<TemplateTranscode.TemplateTranscodeVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeVideo$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeVideo templateTranscodeVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeVideo.remove = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Profile", new ChildElementBinder<TemplateTranscode.TemplateTranscodeVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeVideo$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeVideo templateTranscodeVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeVideo.profile = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Bitrate", new ChildElementBinder<TemplateTranscode.TemplateTranscodeVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeVideo$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeVideo templateTranscodeVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeVideo.bitrate = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Crf", new ChildElementBinder<TemplateTranscode.TemplateTranscodeVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeVideo$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeVideo templateTranscodeVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeVideo.crf = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Gop", new ChildElementBinder<TemplateTranscode.TemplateTranscodeVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeVideo$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeVideo templateTranscodeVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeVideo.gop = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Preset", new ChildElementBinder<TemplateTranscode.TemplateTranscodeVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeVideo$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeVideo templateTranscodeVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeVideo.preset = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Bufsize", new ChildElementBinder<TemplateTranscode.TemplateTranscodeVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeVideo$$XmlAdapter.11
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeVideo templateTranscodeVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeVideo.bufsize = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Maxrate", new ChildElementBinder<TemplateTranscode.TemplateTranscodeVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeVideo$$XmlAdapter.12
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeVideo templateTranscodeVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeVideo.maxrate = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Pixfmt", new ChildElementBinder<TemplateTranscode.TemplateTranscodeVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeVideo$$XmlAdapter.13
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeVideo templateTranscodeVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeVideo.pixfmt = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("LongShortMode", new ChildElementBinder<TemplateTranscode.TemplateTranscodeVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeVideo$$XmlAdapter.14
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeVideo templateTranscodeVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeVideo.longShortMode = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Rotate", new ChildElementBinder<TemplateTranscode.TemplateTranscodeVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeVideo$$XmlAdapter.15
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeVideo templateTranscodeVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeVideo.rotate = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Roi", new ChildElementBinder<TemplateTranscode.TemplateTranscodeVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeVideo$$XmlAdapter.16
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeVideo templateTranscodeVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeVideo.roi = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Crop", new ChildElementBinder<TemplateTranscode.TemplateTranscodeVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeVideo$$XmlAdapter.17
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeVideo templateTranscodeVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeVideo.crop = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Interlaced", new ChildElementBinder<TemplateTranscode.TemplateTranscodeVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeVideo$$XmlAdapter.18
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeVideo templateTranscodeVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeVideo.interlaced = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateTranscode.TemplateTranscodeVideo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateTranscode.TemplateTranscodeVideo templateTranscodeVideo = new TemplateTranscode.TemplateTranscodeVideo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateTranscode.TemplateTranscodeVideo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templateTranscodeVideo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Video" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templateTranscodeVideo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templateTranscodeVideo;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, TemplateTranscode.TemplateTranscodeVideo templateTranscodeVideo, String str) throws XmlPullParserException, IOException {
        if (templateTranscodeVideo == null) {
            return;
        }
        if (str == null) {
            str = "Video";
        }
        xmlSerializer.startTag("", str);
        if (templateTranscodeVideo.codec != null) {
            xmlSerializer.startTag("", "Codec");
            xmlSerializer.text(String.valueOf(templateTranscodeVideo.codec));
            xmlSerializer.endTag("", "Codec");
        }
        if (templateTranscodeVideo.width != null) {
            xmlSerializer.startTag("", "Width");
            xmlSerializer.text(String.valueOf(templateTranscodeVideo.width));
            xmlSerializer.endTag("", "Width");
        }
        if (templateTranscodeVideo.height != null) {
            xmlSerializer.startTag("", "Height");
            xmlSerializer.text(String.valueOf(templateTranscodeVideo.height));
            xmlSerializer.endTag("", "Height");
        }
        if (templateTranscodeVideo.fps != null) {
            xmlSerializer.startTag("", "Fps");
            xmlSerializer.text(String.valueOf(templateTranscodeVideo.fps));
            xmlSerializer.endTag("", "Fps");
        }
        if (templateTranscodeVideo.remove != null) {
            xmlSerializer.startTag("", "Remove");
            xmlSerializer.text(String.valueOf(templateTranscodeVideo.remove));
            xmlSerializer.endTag("", "Remove");
        }
        if (templateTranscodeVideo.profile != null) {
            xmlSerializer.startTag("", "Profile");
            xmlSerializer.text(String.valueOf(templateTranscodeVideo.profile));
            xmlSerializer.endTag("", "Profile");
        }
        if (templateTranscodeVideo.bitrate != null) {
            xmlSerializer.startTag("", "Bitrate");
            xmlSerializer.text(String.valueOf(templateTranscodeVideo.bitrate));
            xmlSerializer.endTag("", "Bitrate");
        }
        if (templateTranscodeVideo.crf != null) {
            xmlSerializer.startTag("", "Crf");
            xmlSerializer.text(String.valueOf(templateTranscodeVideo.crf));
            xmlSerializer.endTag("", "Crf");
        }
        if (templateTranscodeVideo.gop != null) {
            xmlSerializer.startTag("", "Gop");
            xmlSerializer.text(String.valueOf(templateTranscodeVideo.gop));
            xmlSerializer.endTag("", "Gop");
        }
        if (templateTranscodeVideo.preset != null) {
            xmlSerializer.startTag("", "Preset");
            xmlSerializer.text(String.valueOf(templateTranscodeVideo.preset));
            xmlSerializer.endTag("", "Preset");
        }
        if (templateTranscodeVideo.bufsize != null) {
            xmlSerializer.startTag("", "Bufsize");
            xmlSerializer.text(String.valueOf(templateTranscodeVideo.bufsize));
            xmlSerializer.endTag("", "Bufsize");
        }
        if (templateTranscodeVideo.maxrate != null) {
            xmlSerializer.startTag("", "Maxrate");
            xmlSerializer.text(String.valueOf(templateTranscodeVideo.maxrate));
            xmlSerializer.endTag("", "Maxrate");
        }
        if (templateTranscodeVideo.pixfmt != null) {
            xmlSerializer.startTag("", "Pixfmt");
            xmlSerializer.text(String.valueOf(templateTranscodeVideo.pixfmt));
            xmlSerializer.endTag("", "Pixfmt");
        }
        if (templateTranscodeVideo.longShortMode != null) {
            xmlSerializer.startTag("", "LongShortMode");
            xmlSerializer.text(String.valueOf(templateTranscodeVideo.longShortMode));
            xmlSerializer.endTag("", "LongShortMode");
        }
        if (templateTranscodeVideo.rotate != null) {
            xmlSerializer.startTag("", "Rotate");
            xmlSerializer.text(String.valueOf(templateTranscodeVideo.rotate));
            xmlSerializer.endTag("", "Rotate");
        }
        if (templateTranscodeVideo.roi != null) {
            xmlSerializer.startTag("", "Roi");
            xmlSerializer.text(String.valueOf(templateTranscodeVideo.roi));
            xmlSerializer.endTag("", "Roi");
        }
        if (templateTranscodeVideo.crop != null) {
            xmlSerializer.startTag("", "Crop");
            xmlSerializer.text(String.valueOf(templateTranscodeVideo.crop));
            xmlSerializer.endTag("", "Crop");
        }
        if (templateTranscodeVideo.interlaced != null) {
            xmlSerializer.startTag("", "Interlaced");
            xmlSerializer.text(String.valueOf(templateTranscodeVideo.interlaced));
            xmlSerializer.endTag("", "Interlaced");
        }
        xmlSerializer.endTag("", str);
    }
}
