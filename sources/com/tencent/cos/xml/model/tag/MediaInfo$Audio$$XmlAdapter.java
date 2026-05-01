package com.tencent.cos.xml.model.tag;

import com.tencent.cos.xml.model.tag.MediaInfo;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class MediaInfo$Audio$$XmlAdapter extends IXmlAdapter<MediaInfo.Audio> {
    private HashMap<String, ChildElementBinder<MediaInfo.Audio>> childElementBinders;

    public MediaInfo$Audio$$XmlAdapter() {
        HashMap<String, ChildElementBinder<MediaInfo.Audio>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Index", new ChildElementBinder<MediaInfo.Audio>() { // from class: com.tencent.cos.xml.model.tag.MediaInfo$Audio$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfo.Audio audio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audio.index = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("CodecName", new ChildElementBinder<MediaInfo.Audio>() { // from class: com.tencent.cos.xml.model.tag.MediaInfo$Audio$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfo.Audio audio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audio.codecName = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CodecLongName", new ChildElementBinder<MediaInfo.Audio>() { // from class: com.tencent.cos.xml.model.tag.MediaInfo$Audio$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfo.Audio audio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audio.codecLongName = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CodecTimeBase", new ChildElementBinder<MediaInfo.Audio>() { // from class: com.tencent.cos.xml.model.tag.MediaInfo$Audio$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfo.Audio audio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audio.codecTimeBase = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CodecTagString", new ChildElementBinder<MediaInfo.Audio>() { // from class: com.tencent.cos.xml.model.tag.MediaInfo$Audio$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfo.Audio audio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audio.codecTagString = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CodecTag", new ChildElementBinder<MediaInfo.Audio>() { // from class: com.tencent.cos.xml.model.tag.MediaInfo$Audio$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfo.Audio audio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audio.codecTag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SampleFmt", new ChildElementBinder<MediaInfo.Audio>() { // from class: com.tencent.cos.xml.model.tag.MediaInfo$Audio$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfo.Audio audio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audio.sampleFmt = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SampleRate", new ChildElementBinder<MediaInfo.Audio>() { // from class: com.tencent.cos.xml.model.tag.MediaInfo$Audio$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfo.Audio audio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audio.sampleRate = Float.parseFloat(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Channel", new ChildElementBinder<MediaInfo.Audio>() { // from class: com.tencent.cos.xml.model.tag.MediaInfo$Audio$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfo.Audio audio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audio.channel = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("ChannelLayout", new ChildElementBinder<MediaInfo.Audio>() { // from class: com.tencent.cos.xml.model.tag.MediaInfo$Audio$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfo.Audio audio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audio.channelLayout = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Timebase", new ChildElementBinder<MediaInfo.Audio>() { // from class: com.tencent.cos.xml.model.tag.MediaInfo$Audio$$XmlAdapter.11
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfo.Audio audio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audio.timebase = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("StartTime", new ChildElementBinder<MediaInfo.Audio>() { // from class: com.tencent.cos.xml.model.tag.MediaInfo$Audio$$XmlAdapter.12
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfo.Audio audio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audio.startTime = Float.parseFloat(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Duration", new ChildElementBinder<MediaInfo.Audio>() { // from class: com.tencent.cos.xml.model.tag.MediaInfo$Audio$$XmlAdapter.13
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfo.Audio audio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audio.duration = Float.parseFloat(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Bitrate", new ChildElementBinder<MediaInfo.Audio>() { // from class: com.tencent.cos.xml.model.tag.MediaInfo$Audio$$XmlAdapter.14
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfo.Audio audio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audio.bitrate = Float.parseFloat(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Language", new ChildElementBinder<MediaInfo.Audio>() { // from class: com.tencent.cos.xml.model.tag.MediaInfo$Audio$$XmlAdapter.15
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfo.Audio audio, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audio.language = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public MediaInfo.Audio fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        MediaInfo.Audio audio = new MediaInfo.Audio();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<MediaInfo.Audio> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, audio, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Audio" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return audio;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return audio;
    }
}
