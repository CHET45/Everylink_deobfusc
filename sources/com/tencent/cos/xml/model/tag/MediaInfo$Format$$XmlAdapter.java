package com.tencent.cos.xml.model.tag;

import com.microsoft.azure.storage.blob.BlobConstants;
import com.tencent.cos.xml.model.tag.MediaInfo;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class MediaInfo$Format$$XmlAdapter extends IXmlAdapter<MediaInfo.Format> {
    private HashMap<String, ChildElementBinder<MediaInfo.Format>> childElementBinders;

    public MediaInfo$Format$$XmlAdapter() {
        HashMap<String, ChildElementBinder<MediaInfo.Format>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("NumStream", new ChildElementBinder<MediaInfo.Format>() { // from class: com.tencent.cos.xml.model.tag.MediaInfo$Format$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfo.Format format, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                format.numStream = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("NumProgram", new ChildElementBinder<MediaInfo.Format>() { // from class: com.tencent.cos.xml.model.tag.MediaInfo$Format$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfo.Format format, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                format.numProgram = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("FormatName", new ChildElementBinder<MediaInfo.Format>() { // from class: com.tencent.cos.xml.model.tag.MediaInfo$Format$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfo.Format format, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                format.formatName = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("FormatLongName", new ChildElementBinder<MediaInfo.Format>() { // from class: com.tencent.cos.xml.model.tag.MediaInfo$Format$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfo.Format format, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                format.formatLongName = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("StartTime", new ChildElementBinder<MediaInfo.Format>() { // from class: com.tencent.cos.xml.model.tag.MediaInfo$Format$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfo.Format format, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                format.startTime = Float.parseFloat(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Duration", new ChildElementBinder<MediaInfo.Format>() { // from class: com.tencent.cos.xml.model.tag.MediaInfo$Format$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfo.Format format, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                format.duration = Float.parseFloat(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Bitrate", new ChildElementBinder<MediaInfo.Format>() { // from class: com.tencent.cos.xml.model.tag.MediaInfo$Format$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfo.Format format, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                format.bitrate = Float.parseFloat(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put(BlobConstants.SIZE_ELEMENT, new ChildElementBinder<MediaInfo.Format>() { // from class: com.tencent.cos.xml.model.tag.MediaInfo$Format$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfo.Format format, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                format.size = Float.parseFloat(xmlPullParser.getText());
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public MediaInfo.Format fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        MediaInfo.Format format = new MediaInfo.Format();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<MediaInfo.Format> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, format, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Format" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return format;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return format;
    }
}
