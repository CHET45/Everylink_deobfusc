package com.tencent.cos.xml.model.p033ci.common;

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
public class MediaInfoStream$$XmlAdapter extends IXmlAdapter<MediaInfoStream> {
    private HashMap<String, ChildElementBinder<MediaInfoStream>> childElementBinders;

    public MediaInfoStream$$XmlAdapter() {
        HashMap<String, ChildElementBinder<MediaInfoStream>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Video", new ChildElementBinder<MediaInfoStream>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStream$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStream mediaInfoStream, String str) throws XmlPullParserException, IOException {
                if (mediaInfoStream.video == null) {
                    mediaInfoStream.video = new ArrayList();
                }
                mediaInfoStream.video.add((MediaInfoStreamVideo) QCloudXml.fromXml(xmlPullParser, MediaInfoStreamVideo.class, "Video"));
            }
        });
        this.childElementBinders.put("Audio", new ChildElementBinder<MediaInfoStream>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStream$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStream mediaInfoStream, String str) throws XmlPullParserException, IOException {
                if (mediaInfoStream.audio == null) {
                    mediaInfoStream.audio = new ArrayList();
                }
                mediaInfoStream.audio.add((MediaInfoStreamAudio) QCloudXml.fromXml(xmlPullParser, MediaInfoStreamAudio.class, "Audio"));
            }
        });
        this.childElementBinders.put("Subtitle", new ChildElementBinder<MediaInfoStream>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoStream$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoStream mediaInfoStream, String str) throws XmlPullParserException, IOException {
                if (mediaInfoStream.subtitle == null) {
                    mediaInfoStream.subtitle = new ArrayList();
                }
                mediaInfoStream.subtitle.add((MediaInfoStreamSubtitle) QCloudXml.fromXml(xmlPullParser, MediaInfoStreamSubtitle.class, "Subtitle"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public MediaInfoStream fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        MediaInfoStream mediaInfoStream = new MediaInfoStream();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<MediaInfoStream> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, mediaInfoStream, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "MediaInfoStream" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return mediaInfoStream;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return mediaInfoStream;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, MediaInfoStream mediaInfoStream, String str) throws XmlPullParserException, IOException {
        if (mediaInfoStream == null) {
            return;
        }
        if (str == null) {
            str = "MediaInfoStream";
        }
        xmlSerializer.startTag("", str);
        if (mediaInfoStream.video != null) {
            for (int i = 0; i < mediaInfoStream.video.size(); i++) {
                QCloudXml.toXml(xmlSerializer, mediaInfoStream.video.get(i), "MediaInfoStreamVideo");
            }
        }
        if (mediaInfoStream.audio != null) {
            for (int i2 = 0; i2 < mediaInfoStream.audio.size(); i2++) {
                QCloudXml.toXml(xmlSerializer, mediaInfoStream.audio.get(i2), "MediaInfoStreamAudio");
            }
        }
        if (mediaInfoStream.subtitle != null) {
            for (int i3 = 0; i3 < mediaInfoStream.subtitle.size(); i3++) {
                QCloudXml.toXml(xmlSerializer, mediaInfoStream.subtitle.get(i3), "MediaInfoStreamSubtitle");
            }
        }
        xmlSerializer.endTag("", str);
    }
}
