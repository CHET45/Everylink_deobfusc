package com.tencent.cos.xml.model.p033ci.common;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class MediaInfoStreamAudio$$XmlAdapter extends IXmlAdapter<MediaInfoStreamAudio> {
    private HashMap<String, ChildElementBinder<MediaInfoStreamAudio>> childElementBinders;

    public MediaInfoStreamAudio$$XmlAdapter() {
        HashMap<String, ChildElementBinder<MediaInfoStreamAudio>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Index", new ChildElementBinder<MediaInfoStreamAudio>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamAudio$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamAudio mediaInfoStreamAudio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamAudio.index = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CodecName", new ChildElementBinder<MediaInfoStreamAudio>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamAudio$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamAudio mediaInfoStreamAudio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamAudio.codecName = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CodecLongName", new ChildElementBinder<MediaInfoStreamAudio>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamAudio$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamAudio mediaInfoStreamAudio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamAudio.codecLongName = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CodecTimeBase", new ChildElementBinder<MediaInfoStreamAudio>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamAudio$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamAudio mediaInfoStreamAudio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamAudio.codecTimeBase = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CodecTagString", new ChildElementBinder<MediaInfoStreamAudio>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamAudio$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamAudio mediaInfoStreamAudio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamAudio.codecTagString = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CodecTag", new ChildElementBinder<MediaInfoStreamAudio>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamAudio$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamAudio mediaInfoStreamAudio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamAudio.codecTag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SampleFmt", new ChildElementBinder<MediaInfoStreamAudio>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamAudio$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamAudio mediaInfoStreamAudio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamAudio.sampleFmt = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SampleRate", new ChildElementBinder<MediaInfoStreamAudio>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamAudio$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamAudio mediaInfoStreamAudio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamAudio.sampleRate = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Channel", new ChildElementBinder<MediaInfoStreamAudio>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamAudio$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamAudio mediaInfoStreamAudio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamAudio.channel = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("ChannelLayout", new ChildElementBinder<MediaInfoStreamAudio>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamAudio$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamAudio mediaInfoStreamAudio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamAudio.channelLayout = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Timebase", new ChildElementBinder<MediaInfoStreamAudio>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamAudio$$XmlAdapter.11
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamAudio mediaInfoStreamAudio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamAudio.timebase = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("StartTime", new ChildElementBinder<MediaInfoStreamAudio>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamAudio$$XmlAdapter.12
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamAudio mediaInfoStreamAudio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamAudio.startTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Duration", new ChildElementBinder<MediaInfoStreamAudio>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamAudio$$XmlAdapter.13
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamAudio mediaInfoStreamAudio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamAudio.duration = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Bitrate", new ChildElementBinder<MediaInfoStreamAudio>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamAudio$$XmlAdapter.14
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamAudio mediaInfoStreamAudio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamAudio.bitrate = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Language", new ChildElementBinder<MediaInfoStreamAudio>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStreamAudio$$XmlAdapter.15
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStreamAudio mediaInfoStreamAudio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoStreamAudio.language = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public MediaInfoStreamAudio fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        MediaInfoStreamAudio mediaInfoStreamAudio = new MediaInfoStreamAudio();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<MediaInfoStreamAudio> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, mediaInfoStreamAudio, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "MediaInfoStreamAudio" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return mediaInfoStreamAudio;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return mediaInfoStreamAudio;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, MediaInfoStreamAudio mediaInfoStreamAudio, String str) throws XmlPullParserException, IOException {
        if (mediaInfoStreamAudio == null) {
            return;
        }
        if (str == null) {
            str = "MediaInfoStreamAudio";
        }
        xmlSerializer.startTag("", str);
        if (mediaInfoStreamAudio.index != null) {
            xmlSerializer.startTag("", "Index");
            xmlSerializer.text(String.valueOf(mediaInfoStreamAudio.index));
            xmlSerializer.endTag("", "Index");
        }
        if (mediaInfoStreamAudio.codecName != null) {
            xmlSerializer.startTag("", "CodecName");
            xmlSerializer.text(String.valueOf(mediaInfoStreamAudio.codecName));
            xmlSerializer.endTag("", "CodecName");
        }
        if (mediaInfoStreamAudio.codecLongName != null) {
            xmlSerializer.startTag("", "CodecLongName");
            xmlSerializer.text(String.valueOf(mediaInfoStreamAudio.codecLongName));
            xmlSerializer.endTag("", "CodecLongName");
        }
        if (mediaInfoStreamAudio.codecTimeBase != null) {
            xmlSerializer.startTag("", "CodecTimeBase");
            xmlSerializer.text(String.valueOf(mediaInfoStreamAudio.codecTimeBase));
            xmlSerializer.endTag("", "CodecTimeBase");
        }
        if (mediaInfoStreamAudio.codecTagString != null) {
            xmlSerializer.startTag("", "CodecTagString");
            xmlSerializer.text(String.valueOf(mediaInfoStreamAudio.codecTagString));
            xmlSerializer.endTag("", "CodecTagString");
        }
        if (mediaInfoStreamAudio.codecTag != null) {
            xmlSerializer.startTag("", "CodecTag");
            xmlSerializer.text(String.valueOf(mediaInfoStreamAudio.codecTag));
            xmlSerializer.endTag("", "CodecTag");
        }
        if (mediaInfoStreamAudio.sampleFmt != null) {
            xmlSerializer.startTag("", "SampleFmt");
            xmlSerializer.text(String.valueOf(mediaInfoStreamAudio.sampleFmt));
            xmlSerializer.endTag("", "SampleFmt");
        }
        if (mediaInfoStreamAudio.sampleRate != null) {
            xmlSerializer.startTag("", "SampleRate");
            xmlSerializer.text(String.valueOf(mediaInfoStreamAudio.sampleRate));
            xmlSerializer.endTag("", "SampleRate");
        }
        if (mediaInfoStreamAudio.channel != null) {
            xmlSerializer.startTag("", "Channel");
            xmlSerializer.text(String.valueOf(mediaInfoStreamAudio.channel));
            xmlSerializer.endTag("", "Channel");
        }
        if (mediaInfoStreamAudio.channelLayout != null) {
            xmlSerializer.startTag("", "ChannelLayout");
            xmlSerializer.text(String.valueOf(mediaInfoStreamAudio.channelLayout));
            xmlSerializer.endTag("", "ChannelLayout");
        }
        if (mediaInfoStreamAudio.timebase != null) {
            xmlSerializer.startTag("", "Timebase");
            xmlSerializer.text(String.valueOf(mediaInfoStreamAudio.timebase));
            xmlSerializer.endTag("", "Timebase");
        }
        if (mediaInfoStreamAudio.startTime != null) {
            xmlSerializer.startTag("", "StartTime");
            xmlSerializer.text(String.valueOf(mediaInfoStreamAudio.startTime));
            xmlSerializer.endTag("", "StartTime");
        }
        if (mediaInfoStreamAudio.duration != null) {
            xmlSerializer.startTag("", "Duration");
            xmlSerializer.text(String.valueOf(mediaInfoStreamAudio.duration));
            xmlSerializer.endTag("", "Duration");
        }
        if (mediaInfoStreamAudio.bitrate != null) {
            xmlSerializer.startTag("", "Bitrate");
            xmlSerializer.text(String.valueOf(mediaInfoStreamAudio.bitrate));
            xmlSerializer.endTag("", "Bitrate");
        }
        if (mediaInfoStreamAudio.language != null) {
            xmlSerializer.startTag("", "Language");
            xmlSerializer.text(String.valueOf(mediaInfoStreamAudio.language));
            xmlSerializer.endTag("", "Language");
        }
        xmlSerializer.endTag("", str);
    }
}
