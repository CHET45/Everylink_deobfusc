package com.tencent.cos.xml.model.p033ci.common;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class MediaInfoStreamVideo$$XmlAdapter extends IXmlAdapter<MediaInfoStreamVideo> {
    private HashMap<String, ChildElementBinder<MediaInfoStreamVideo>> childElementBinders;

    public MediaInfoStreamVideo$$XmlAdapter() {
        HashMap<String, ChildElementBinder<MediaInfoStreamVideo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Index", new ChildElementBinder<MediaInfoStreamVideo>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamVideo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamVideo mediaInfoStreamVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamVideo.index = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CodecName", new ChildElementBinder<MediaInfoStreamVideo>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamVideo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamVideo mediaInfoStreamVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamVideo.codecName = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CodecLongName", new ChildElementBinder<MediaInfoStreamVideo>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamVideo$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamVideo mediaInfoStreamVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamVideo.codecLongName = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CodecTimeBase", new ChildElementBinder<MediaInfoStreamVideo>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamVideo$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamVideo mediaInfoStreamVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamVideo.codecTimeBase = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CodecTagString", new ChildElementBinder<MediaInfoStreamVideo>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamVideo$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamVideo mediaInfoStreamVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamVideo.codecTagString = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CodecTag", new ChildElementBinder<MediaInfoStreamVideo>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamVideo$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamVideo mediaInfoStreamVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamVideo.codecTag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("ColorPrimaries", new ChildElementBinder<MediaInfoStreamVideo>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamVideo$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamVideo mediaInfoStreamVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamVideo.colorPrimaries = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("ColorRange", new ChildElementBinder<MediaInfoStreamVideo>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamVideo$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamVideo mediaInfoStreamVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamVideo.colorRange = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("ColorTransfer", new ChildElementBinder<MediaInfoStreamVideo>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamVideo$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamVideo mediaInfoStreamVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamVideo.colorTransfer = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Profile", new ChildElementBinder<MediaInfoStreamVideo>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamVideo$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamVideo mediaInfoStreamVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamVideo.profile = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Height", new ChildElementBinder<MediaInfoStreamVideo>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamVideo$$XmlAdapter.11
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamVideo mediaInfoStreamVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamVideo.height = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Width", new ChildElementBinder<MediaInfoStreamVideo>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamVideo$$XmlAdapter.12
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamVideo mediaInfoStreamVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamVideo.width = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("HasBFrame", new ChildElementBinder<MediaInfoStreamVideo>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamVideo$$XmlAdapter.13
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamVideo mediaInfoStreamVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamVideo.hasBFrame = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("RefFrames", new ChildElementBinder<MediaInfoStreamVideo>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamVideo$$XmlAdapter.14
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamVideo mediaInfoStreamVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamVideo.refFrames = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Sar", new ChildElementBinder<MediaInfoStreamVideo>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamVideo$$XmlAdapter.15
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamVideo mediaInfoStreamVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamVideo.sar = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Dar", new ChildElementBinder<MediaInfoStreamVideo>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamVideo$$XmlAdapter.16
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamVideo mediaInfoStreamVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamVideo.dar = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("PixFormat", new ChildElementBinder<MediaInfoStreamVideo>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamVideo$$XmlAdapter.17
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamVideo mediaInfoStreamVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamVideo.pixFormat = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("FieldOrder", new ChildElementBinder<MediaInfoStreamVideo>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamVideo$$XmlAdapter.18
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamVideo mediaInfoStreamVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamVideo.fieldOrder = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Level", new ChildElementBinder<MediaInfoStreamVideo>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamVideo$$XmlAdapter.19
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamVideo mediaInfoStreamVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamVideo.level = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Fps", new ChildElementBinder<MediaInfoStreamVideo>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamVideo$$XmlAdapter.20
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamVideo mediaInfoStreamVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamVideo.fps = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("AvgFps", new ChildElementBinder<MediaInfoStreamVideo>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamVideo$$XmlAdapter.21
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamVideo mediaInfoStreamVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamVideo.avgFps = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Timebase", new ChildElementBinder<MediaInfoStreamVideo>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamVideo$$XmlAdapter.22
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamVideo mediaInfoStreamVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamVideo.timebase = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("StartTime", new ChildElementBinder<MediaInfoStreamVideo>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamVideo$$XmlAdapter.23
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamVideo mediaInfoStreamVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamVideo.startTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Duration", new ChildElementBinder<MediaInfoStreamVideo>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamVideo$$XmlAdapter.24
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamVideo mediaInfoStreamVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamVideo.duration = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Bitrate", new ChildElementBinder<MediaInfoStreamVideo>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamVideo$$XmlAdapter.25
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamVideo mediaInfoStreamVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamVideo.bitrate = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("NumFrames", new ChildElementBinder<MediaInfoStreamVideo>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamVideo$$XmlAdapter.26
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamVideo mediaInfoStreamVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamVideo.numFrames = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Language", new ChildElementBinder<MediaInfoStreamVideo>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamVideo$$XmlAdapter.27
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamVideo mediaInfoStreamVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamVideo.language = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public MediaInfoStreamVideo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        MediaInfoStreamVideo mediaInfoStreamVideo = new MediaInfoStreamVideo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<MediaInfoStreamVideo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, mediaInfoStreamVideo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "MediaInfoStreamVideo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return mediaInfoStreamVideo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return mediaInfoStreamVideo;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, MediaInfoStreamVideo mediaInfoStreamVideo, String str) throws XmlPullParserException, IOException {
        if (mediaInfoStreamVideo == null) {
            return;
        }
        if (str == null) {
            str = "MediaInfoStreamVideo";
        }
        xmlSerializer.startTag("", str);
        if (mediaInfoStreamVideo.index != null) {
            xmlSerializer.startTag("", "Index");
            xmlSerializer.text(String.valueOf(mediaInfoStreamVideo.index));
            xmlSerializer.endTag("", "Index");
        }
        if (mediaInfoStreamVideo.codecName != null) {
            xmlSerializer.startTag("", "CodecName");
            xmlSerializer.text(String.valueOf(mediaInfoStreamVideo.codecName));
            xmlSerializer.endTag("", "CodecName");
        }
        if (mediaInfoStreamVideo.codecLongName != null) {
            xmlSerializer.startTag("", "CodecLongName");
            xmlSerializer.text(String.valueOf(mediaInfoStreamVideo.codecLongName));
            xmlSerializer.endTag("", "CodecLongName");
        }
        if (mediaInfoStreamVideo.codecTimeBase != null) {
            xmlSerializer.startTag("", "CodecTimeBase");
            xmlSerializer.text(String.valueOf(mediaInfoStreamVideo.codecTimeBase));
            xmlSerializer.endTag("", "CodecTimeBase");
        }
        if (mediaInfoStreamVideo.codecTagString != null) {
            xmlSerializer.startTag("", "CodecTagString");
            xmlSerializer.text(String.valueOf(mediaInfoStreamVideo.codecTagString));
            xmlSerializer.endTag("", "CodecTagString");
        }
        if (mediaInfoStreamVideo.codecTag != null) {
            xmlSerializer.startTag("", "CodecTag");
            xmlSerializer.text(String.valueOf(mediaInfoStreamVideo.codecTag));
            xmlSerializer.endTag("", "CodecTag");
        }
        if (mediaInfoStreamVideo.colorPrimaries != null) {
            xmlSerializer.startTag("", "ColorPrimaries");
            xmlSerializer.text(String.valueOf(mediaInfoStreamVideo.colorPrimaries));
            xmlSerializer.endTag("", "ColorPrimaries");
        }
        if (mediaInfoStreamVideo.colorRange != null) {
            xmlSerializer.startTag("", "ColorRange");
            xmlSerializer.text(String.valueOf(mediaInfoStreamVideo.colorRange));
            xmlSerializer.endTag("", "ColorRange");
        }
        if (mediaInfoStreamVideo.colorTransfer != null) {
            xmlSerializer.startTag("", "ColorTransfer");
            xmlSerializer.text(String.valueOf(mediaInfoStreamVideo.colorTransfer));
            xmlSerializer.endTag("", "ColorTransfer");
        }
        if (mediaInfoStreamVideo.profile != null) {
            xmlSerializer.startTag("", "Profile");
            xmlSerializer.text(String.valueOf(mediaInfoStreamVideo.profile));
            xmlSerializer.endTag("", "Profile");
        }
        if (mediaInfoStreamVideo.height != null) {
            xmlSerializer.startTag("", "Height");
            xmlSerializer.text(String.valueOf(mediaInfoStreamVideo.height));
            xmlSerializer.endTag("", "Height");
        }
        if (mediaInfoStreamVideo.width != null) {
            xmlSerializer.startTag("", "Width");
            xmlSerializer.text(String.valueOf(mediaInfoStreamVideo.width));
            xmlSerializer.endTag("", "Width");
        }
        if (mediaInfoStreamVideo.hasBFrame != null) {
            xmlSerializer.startTag("", "HasBFrame");
            xmlSerializer.text(String.valueOf(mediaInfoStreamVideo.hasBFrame));
            xmlSerializer.endTag("", "HasBFrame");
        }
        if (mediaInfoStreamVideo.refFrames != null) {
            xmlSerializer.startTag("", "RefFrames");
            xmlSerializer.text(String.valueOf(mediaInfoStreamVideo.refFrames));
            xmlSerializer.endTag("", "RefFrames");
        }
        if (mediaInfoStreamVideo.sar != null) {
            xmlSerializer.startTag("", "Sar");
            xmlSerializer.text(String.valueOf(mediaInfoStreamVideo.sar));
            xmlSerializer.endTag("", "Sar");
        }
        if (mediaInfoStreamVideo.dar != null) {
            xmlSerializer.startTag("", "Dar");
            xmlSerializer.text(String.valueOf(mediaInfoStreamVideo.dar));
            xmlSerializer.endTag("", "Dar");
        }
        if (mediaInfoStreamVideo.pixFormat != null) {
            xmlSerializer.startTag("", "PixFormat");
            xmlSerializer.text(String.valueOf(mediaInfoStreamVideo.pixFormat));
            xmlSerializer.endTag("", "PixFormat");
        }
        if (mediaInfoStreamVideo.fieldOrder != null) {
            xmlSerializer.startTag("", "FieldOrder");
            xmlSerializer.text(String.valueOf(mediaInfoStreamVideo.fieldOrder));
            xmlSerializer.endTag("", "FieldOrder");
        }
        if (mediaInfoStreamVideo.level != null) {
            xmlSerializer.startTag("", "Level");
            xmlSerializer.text(String.valueOf(mediaInfoStreamVideo.level));
            xmlSerializer.endTag("", "Level");
        }
        if (mediaInfoStreamVideo.fps != null) {
            xmlSerializer.startTag("", "Fps");
            xmlSerializer.text(String.valueOf(mediaInfoStreamVideo.fps));
            xmlSerializer.endTag("", "Fps");
        }
        if (mediaInfoStreamVideo.avgFps != null) {
            xmlSerializer.startTag("", "AvgFps");
            xmlSerializer.text(String.valueOf(mediaInfoStreamVideo.avgFps));
            xmlSerializer.endTag("", "AvgFps");
        }
        if (mediaInfoStreamVideo.timebase != null) {
            xmlSerializer.startTag("", "Timebase");
            xmlSerializer.text(String.valueOf(mediaInfoStreamVideo.timebase));
            xmlSerializer.endTag("", "Timebase");
        }
        if (mediaInfoStreamVideo.startTime != null) {
            xmlSerializer.startTag("", "StartTime");
            xmlSerializer.text(String.valueOf(mediaInfoStreamVideo.startTime));
            xmlSerializer.endTag("", "StartTime");
        }
        if (mediaInfoStreamVideo.duration != null) {
            xmlSerializer.startTag("", "Duration");
            xmlSerializer.text(String.valueOf(mediaInfoStreamVideo.duration));
            xmlSerializer.endTag("", "Duration");
        }
        if (mediaInfoStreamVideo.bitrate != null) {
            xmlSerializer.startTag("", "Bitrate");
            xmlSerializer.text(String.valueOf(mediaInfoStreamVideo.bitrate));
            xmlSerializer.endTag("", "Bitrate");
        }
        if (mediaInfoStreamVideo.numFrames != null) {
            xmlSerializer.startTag("", "NumFrames");
            xmlSerializer.text(String.valueOf(mediaInfoStreamVideo.numFrames));
            xmlSerializer.endTag("", "NumFrames");
        }
        if (mediaInfoStreamVideo.language != null) {
            xmlSerializer.startTag("", "Language");
            xmlSerializer.text(String.valueOf(mediaInfoStreamVideo.language));
            xmlSerializer.endTag("", "Language");
        }
        xmlSerializer.endTag("", str);
    }
}
