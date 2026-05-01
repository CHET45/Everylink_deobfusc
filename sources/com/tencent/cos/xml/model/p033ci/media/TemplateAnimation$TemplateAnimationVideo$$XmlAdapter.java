package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.TemplateAnimation;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateAnimation$TemplateAnimationVideo$$XmlAdapter extends IXmlAdapter<TemplateAnimation.TemplateAnimationVideo> {
    private HashMap<String, ChildElementBinder<TemplateAnimation.TemplateAnimationVideo>> childElementBinders;

    public TemplateAnimation$TemplateAnimationVideo$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TemplateAnimation.TemplateAnimationVideo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Codec", new ChildElementBinder<TemplateAnimation.TemplateAnimationVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateAnimation$TemplateAnimationVideo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateAnimation.TemplateAnimationVideo templateAnimationVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateAnimationVideo.codec = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Width", new ChildElementBinder<TemplateAnimation.TemplateAnimationVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateAnimation$TemplateAnimationVideo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateAnimation.TemplateAnimationVideo templateAnimationVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateAnimationVideo.width = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Height", new ChildElementBinder<TemplateAnimation.TemplateAnimationVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateAnimation$TemplateAnimationVideo$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateAnimation.TemplateAnimationVideo templateAnimationVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateAnimationVideo.height = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Fps", new ChildElementBinder<TemplateAnimation.TemplateAnimationVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateAnimation$TemplateAnimationVideo$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateAnimation.TemplateAnimationVideo templateAnimationVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateAnimationVideo.fps = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("AnimateOnlyKeepKeyFrame", new ChildElementBinder<TemplateAnimation.TemplateAnimationVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateAnimation$TemplateAnimationVideo$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateAnimation.TemplateAnimationVideo templateAnimationVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateAnimationVideo.animateOnlyKeepKeyFrame = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("AnimateTimeIntervalOfFrame", new ChildElementBinder<TemplateAnimation.TemplateAnimationVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateAnimation$TemplateAnimationVideo$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateAnimation.TemplateAnimationVideo templateAnimationVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateAnimationVideo.animateTimeIntervalOfFrame = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("AnimateFramesPerSecond", new ChildElementBinder<TemplateAnimation.TemplateAnimationVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateAnimation$TemplateAnimationVideo$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateAnimation.TemplateAnimationVideo templateAnimationVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateAnimationVideo.animateFramesPerSecond = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Quality", new ChildElementBinder<TemplateAnimation.TemplateAnimationVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateAnimation$TemplateAnimationVideo$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateAnimation.TemplateAnimationVideo templateAnimationVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateAnimationVideo.quality = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateAnimation.TemplateAnimationVideo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateAnimation.TemplateAnimationVideo templateAnimationVideo = new TemplateAnimation.TemplateAnimationVideo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateAnimation.TemplateAnimationVideo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templateAnimationVideo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Video" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templateAnimationVideo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templateAnimationVideo;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, TemplateAnimation.TemplateAnimationVideo templateAnimationVideo, String str) throws XmlPullParserException, IOException {
        if (templateAnimationVideo == null) {
            return;
        }
        if (str == null) {
            str = "Video";
        }
        xmlSerializer.startTag("", str);
        if (templateAnimationVideo.codec != null) {
            xmlSerializer.startTag("", "Codec");
            xmlSerializer.text(String.valueOf(templateAnimationVideo.codec));
            xmlSerializer.endTag("", "Codec");
        }
        if (templateAnimationVideo.width != null) {
            xmlSerializer.startTag("", "Width");
            xmlSerializer.text(String.valueOf(templateAnimationVideo.width));
            xmlSerializer.endTag("", "Width");
        }
        if (templateAnimationVideo.height != null) {
            xmlSerializer.startTag("", "Height");
            xmlSerializer.text(String.valueOf(templateAnimationVideo.height));
            xmlSerializer.endTag("", "Height");
        }
        if (templateAnimationVideo.fps != null) {
            xmlSerializer.startTag("", "Fps");
            xmlSerializer.text(String.valueOf(templateAnimationVideo.fps));
            xmlSerializer.endTag("", "Fps");
        }
        if (templateAnimationVideo.animateOnlyKeepKeyFrame != null) {
            xmlSerializer.startTag("", "AnimateOnlyKeepKeyFrame");
            xmlSerializer.text(String.valueOf(templateAnimationVideo.animateOnlyKeepKeyFrame));
            xmlSerializer.endTag("", "AnimateOnlyKeepKeyFrame");
        }
        if (templateAnimationVideo.animateTimeIntervalOfFrame != null) {
            xmlSerializer.startTag("", "AnimateTimeIntervalOfFrame");
            xmlSerializer.text(String.valueOf(templateAnimationVideo.animateTimeIntervalOfFrame));
            xmlSerializer.endTag("", "AnimateTimeIntervalOfFrame");
        }
        if (templateAnimationVideo.animateFramesPerSecond != null) {
            xmlSerializer.startTag("", "AnimateFramesPerSecond");
            xmlSerializer.text(String.valueOf(templateAnimationVideo.animateFramesPerSecond));
            xmlSerializer.endTag("", "AnimateFramesPerSecond");
        }
        if (templateAnimationVideo.quality != null) {
            xmlSerializer.startTag("", "Quality");
            xmlSerializer.text(String.valueOf(templateAnimationVideo.quality));
            xmlSerializer.endTag("", "Quality");
        }
        xmlSerializer.endTag("", str);
    }
}
