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
public class TemplateVideoMontage$TemplateVideoMontageVideo$$XmlAdapter extends IXmlAdapter<TemplateVideoMontage.TemplateVideoMontageVideo> {
    private HashMap<String, ChildElementBinder<TemplateVideoMontage.TemplateVideoMontageVideo>> childElementBinders;

    public TemplateVideoMontage$TemplateVideoMontageVideo$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TemplateVideoMontage.TemplateVideoMontageVideo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Codec", new ChildElementBinder<TemplateVideoMontage.TemplateVideoMontageVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVideoMontage$TemplateVideoMontageVideo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVideoMontage.TemplateVideoMontageVideo templateVideoMontageVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateVideoMontageVideo.codec = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Width", new ChildElementBinder<TemplateVideoMontage.TemplateVideoMontageVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVideoMontage$TemplateVideoMontageVideo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVideoMontage.TemplateVideoMontageVideo templateVideoMontageVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateVideoMontageVideo.width = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Height", new ChildElementBinder<TemplateVideoMontage.TemplateVideoMontageVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVideoMontage$TemplateVideoMontageVideo$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVideoMontage.TemplateVideoMontageVideo templateVideoMontageVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateVideoMontageVideo.height = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Fps", new ChildElementBinder<TemplateVideoMontage.TemplateVideoMontageVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVideoMontage$TemplateVideoMontageVideo$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVideoMontage.TemplateVideoMontageVideo templateVideoMontageVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateVideoMontageVideo.fps = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Bitrate", new ChildElementBinder<TemplateVideoMontage.TemplateVideoMontageVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVideoMontage$TemplateVideoMontageVideo$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVideoMontage.TemplateVideoMontageVideo templateVideoMontageVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateVideoMontageVideo.bitrate = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Crf", new ChildElementBinder<TemplateVideoMontage.TemplateVideoMontageVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVideoMontage$TemplateVideoMontageVideo$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVideoMontage.TemplateVideoMontageVideo templateVideoMontageVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateVideoMontageVideo.crf = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Rotate", new ChildElementBinder<TemplateVideoMontage.TemplateVideoMontageVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVideoMontage$TemplateVideoMontageVideo$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVideoMontage.TemplateVideoMontageVideo templateVideoMontageVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateVideoMontageVideo.rotate = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateVideoMontage.TemplateVideoMontageVideo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateVideoMontage.TemplateVideoMontageVideo templateVideoMontageVideo = new TemplateVideoMontage.TemplateVideoMontageVideo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateVideoMontage.TemplateVideoMontageVideo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templateVideoMontageVideo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Video" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templateVideoMontageVideo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templateVideoMontageVideo;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, TemplateVideoMontage.TemplateVideoMontageVideo templateVideoMontageVideo, String str) throws XmlPullParserException, IOException {
        if (templateVideoMontageVideo == null) {
            return;
        }
        if (str == null) {
            str = "Video";
        }
        xmlSerializer.startTag("", str);
        if (templateVideoMontageVideo.codec != null) {
            xmlSerializer.startTag("", "Codec");
            xmlSerializer.text(String.valueOf(templateVideoMontageVideo.codec));
            xmlSerializer.endTag("", "Codec");
        }
        if (templateVideoMontageVideo.width != null) {
            xmlSerializer.startTag("", "Width");
            xmlSerializer.text(String.valueOf(templateVideoMontageVideo.width));
            xmlSerializer.endTag("", "Width");
        }
        if (templateVideoMontageVideo.height != null) {
            xmlSerializer.startTag("", "Height");
            xmlSerializer.text(String.valueOf(templateVideoMontageVideo.height));
            xmlSerializer.endTag("", "Height");
        }
        if (templateVideoMontageVideo.fps != null) {
            xmlSerializer.startTag("", "Fps");
            xmlSerializer.text(String.valueOf(templateVideoMontageVideo.fps));
            xmlSerializer.endTag("", "Fps");
        }
        if (templateVideoMontageVideo.bitrate != null) {
            xmlSerializer.startTag("", "Bitrate");
            xmlSerializer.text(String.valueOf(templateVideoMontageVideo.bitrate));
            xmlSerializer.endTag("", "Bitrate");
        }
        if (templateVideoMontageVideo.crf != null) {
            xmlSerializer.startTag("", "Crf");
            xmlSerializer.text(String.valueOf(templateVideoMontageVideo.crf));
            xmlSerializer.endTag("", "Crf");
        }
        if (templateVideoMontageVideo.rotate != null) {
            xmlSerializer.startTag("", "Rotate");
            xmlSerializer.text(String.valueOf(templateVideoMontageVideo.rotate));
            xmlSerializer.endTag("", "Rotate");
        }
        xmlSerializer.endTag("", str);
    }
}
