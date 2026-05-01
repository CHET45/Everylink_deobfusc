package com.tencent.cos.xml.model.p033ci.common;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class MediaInfo$$XmlAdapter extends IXmlAdapter<MediaInfo> {
    private HashMap<String, ChildElementBinder<MediaInfo>> childElementBinders;

    public MediaInfo$$XmlAdapter() {
        HashMap<String, ChildElementBinder<MediaInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Format", new ChildElementBinder<MediaInfo>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfo mediaInfo, String str) throws XmlPullParserException, IOException {
                mediaInfo.format = (MediaInfoFormat) QCloudXml.fromXml(xmlPullParser, MediaInfoFormat.class, "Format");
            }
        });
        this.childElementBinders.put("Stream", new ChildElementBinder<MediaInfo>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfo mediaInfo, String str) throws XmlPullParserException, IOException {
                mediaInfo.stream = (MediaInfoStream) QCloudXml.fromXml(xmlPullParser, MediaInfoStream.class, "Stream");
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

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, MediaInfo mediaInfo, String str) throws XmlPullParserException, IOException {
        if (mediaInfo == null) {
            return;
        }
        if (str == null) {
            str = "MediaInfo";
        }
        xmlSerializer.startTag("", str);
        if (mediaInfo.format != null) {
            QCloudXml.toXml(xmlSerializer, mediaInfo.format, "Format");
        }
        if (mediaInfo.stream != null) {
            QCloudXml.toXml(xmlSerializer, mediaInfo.stream, "Stream");
        }
        xmlSerializer.endTag("", str);
    }
}
