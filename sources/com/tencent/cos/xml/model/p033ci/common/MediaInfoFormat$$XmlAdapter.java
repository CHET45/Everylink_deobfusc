package com.tencent.cos.xml.model.p033ci.common;

import com.microsoft.azure.storage.blob.BlobConstants;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class MediaInfoFormat$$XmlAdapter extends IXmlAdapter<MediaInfoFormat> {
    private HashMap<String, ChildElementBinder<MediaInfoFormat>> childElementBinders;

    public MediaInfoFormat$$XmlAdapter() {
        HashMap<String, ChildElementBinder<MediaInfoFormat>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("NumStream", new ChildElementBinder<MediaInfoFormat>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoFormat$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoFormat mediaInfoFormat, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoFormat.numStream = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("NumProgram", new ChildElementBinder<MediaInfoFormat>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoFormat$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoFormat mediaInfoFormat, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoFormat.numProgram = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("FormatName", new ChildElementBinder<MediaInfoFormat>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoFormat$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoFormat mediaInfoFormat, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoFormat.formatName = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("FormatLongName", new ChildElementBinder<MediaInfoFormat>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoFormat$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoFormat mediaInfoFormat, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoFormat.formatLongName = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("StartTime", new ChildElementBinder<MediaInfoFormat>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoFormat$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoFormat mediaInfoFormat, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoFormat.startTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Duration", new ChildElementBinder<MediaInfoFormat>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoFormat$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoFormat mediaInfoFormat, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoFormat.duration = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Bitrate", new ChildElementBinder<MediaInfoFormat>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoFormat$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoFormat mediaInfoFormat, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoFormat.bitrate = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(BlobConstants.SIZE_ELEMENT, new ChildElementBinder<MediaInfoFormat>() { // from class: com.tencent.cos.xml.model.ci.common.MediaInfoFormat$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoFormat mediaInfoFormat, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaInfoFormat.size = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public MediaInfoFormat fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        MediaInfoFormat mediaInfoFormat = new MediaInfoFormat();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<MediaInfoFormat> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, mediaInfoFormat, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "MediaInfoFormat" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return mediaInfoFormat;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return mediaInfoFormat;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, MediaInfoFormat mediaInfoFormat, String str) throws XmlPullParserException, IOException {
        if (mediaInfoFormat == null) {
            return;
        }
        if (str == null) {
            str = "MediaInfoFormat";
        }
        xmlSerializer.startTag("", str);
        if (mediaInfoFormat.numStream != null) {
            xmlSerializer.startTag("", "NumStream");
            xmlSerializer.text(String.valueOf(mediaInfoFormat.numStream));
            xmlSerializer.endTag("", "NumStream");
        }
        if (mediaInfoFormat.numProgram != null) {
            xmlSerializer.startTag("", "NumProgram");
            xmlSerializer.text(String.valueOf(mediaInfoFormat.numProgram));
            xmlSerializer.endTag("", "NumProgram");
        }
        if (mediaInfoFormat.formatName != null) {
            xmlSerializer.startTag("", "FormatName");
            xmlSerializer.text(String.valueOf(mediaInfoFormat.formatName));
            xmlSerializer.endTag("", "FormatName");
        }
        if (mediaInfoFormat.formatLongName != null) {
            xmlSerializer.startTag("", "FormatLongName");
            xmlSerializer.text(String.valueOf(mediaInfoFormat.formatLongName));
            xmlSerializer.endTag("", "FormatLongName");
        }
        if (mediaInfoFormat.startTime != null) {
            xmlSerializer.startTag("", "StartTime");
            xmlSerializer.text(String.valueOf(mediaInfoFormat.startTime));
            xmlSerializer.endTag("", "StartTime");
        }
        if (mediaInfoFormat.duration != null) {
            xmlSerializer.startTag("", "Duration");
            xmlSerializer.text(String.valueOf(mediaInfoFormat.duration));
            xmlSerializer.endTag("", "Duration");
        }
        if (mediaInfoFormat.bitrate != null) {
            xmlSerializer.startTag("", "Bitrate");
            xmlSerializer.text(String.valueOf(mediaInfoFormat.bitrate));
            xmlSerializer.endTag("", "Bitrate");
        }
        if (mediaInfoFormat.size != null) {
            xmlSerializer.startTag("", BlobConstants.SIZE_ELEMENT);
            xmlSerializer.text(String.valueOf(mediaInfoFormat.size));
            xmlSerializer.endTag("", BlobConstants.SIZE_ELEMENT);
        }
        xmlSerializer.endTag("", str);
    }
}
