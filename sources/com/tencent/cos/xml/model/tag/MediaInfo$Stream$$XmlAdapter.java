package com.tencent.cos.xml.model.tag;

import com.tencent.cos.xml.model.tag.MediaInfo;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class MediaInfo$Stream$$XmlAdapter extends IXmlAdapter<MediaInfo.Stream> {
    private HashMap<String, ChildElementBinder<MediaInfo.Stream>> childElementBinders;

    public MediaInfo$Stream$$XmlAdapter() {
        HashMap<String, ChildElementBinder<MediaInfo.Stream>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Video", new ChildElementBinder<MediaInfo.Stream>() { // from class: com.tencent.cos.xml.model.tag.MediaInfo$Stream$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfo.Stream stream, String str) throws XmlPullParserException, IOException {
                stream.video = (MediaInfo.Video) QCloudXml.fromXml(xmlPullParser, MediaInfo.Video.class, "Video");
            }
        });
        this.childElementBinders.put("Audio", new ChildElementBinder<MediaInfo.Stream>() { // from class: com.tencent.cos.xml.model.tag.MediaInfo$Stream$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfo.Stream stream, String str) throws XmlPullParserException, IOException {
                stream.audio = (MediaInfo.Audio) QCloudXml.fromXml(xmlPullParser, MediaInfo.Audio.class, "Audio");
            }
        });
        this.childElementBinders.put("Subtitle", new ChildElementBinder<MediaInfo.Stream>() { // from class: com.tencent.cos.xml.model.tag.MediaInfo$Stream$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfo.Stream stream, String str) throws XmlPullParserException, IOException {
                stream.subtitle = (MediaInfo.Subtitle) QCloudXml.fromXml(xmlPullParser, MediaInfo.Subtitle.class, "Subtitle");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public MediaInfo.Stream fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        MediaInfo.Stream stream = new MediaInfo.Stream();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<MediaInfo.Stream> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, stream, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Stream" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return stream;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return stream;
    }
}
