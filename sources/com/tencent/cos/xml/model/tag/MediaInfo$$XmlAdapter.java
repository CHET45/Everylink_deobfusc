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
public class MediaInfo$$XmlAdapter extends IXmlAdapter<MediaInfo> {
    private HashMap<String, ChildElementBinder<MediaInfo>> childElementBinders;

    public MediaInfo$$XmlAdapter() {
        HashMap<String, ChildElementBinder<MediaInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Stream", new ChildElementBinder<MediaInfo>() { // from class: com.tencent.cos.xml.model.tag.MediaInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfo mediaInfo, String str) throws XmlPullParserException, IOException {
                mediaInfo.stream = (MediaInfo.Stream) QCloudXml.fromXml(xmlPullParser, MediaInfo.Stream.class, "Stream");
            }
        });
        this.childElementBinders.put("Format", new ChildElementBinder<MediaInfo>() { // from class: com.tencent.cos.xml.model.tag.MediaInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfo mediaInfo, String str) throws XmlPullParserException, IOException {
                mediaInfo.format = (MediaInfo.Format) QCloudXml.fromXml(xmlPullParser, MediaInfo.Format.class, "Format");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public MediaInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        MediaInfo mediaInfo = new MediaInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<MediaInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, mediaInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "MediaInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return mediaInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return mediaInfo;
    }
}
