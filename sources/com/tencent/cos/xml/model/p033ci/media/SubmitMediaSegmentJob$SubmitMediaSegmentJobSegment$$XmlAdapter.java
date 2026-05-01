package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitMediaSegmentJob;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitMediaSegmentJob$SubmitMediaSegmentJobSegment$$XmlAdapter extends IXmlAdapter<SubmitMediaSegmentJob.SubmitMediaSegmentJobSegment> {
    private HashMap<String, ChildElementBinder<SubmitMediaSegmentJob.SubmitMediaSegmentJobSegment>> childElementBinders;

    public SubmitMediaSegmentJob$SubmitMediaSegmentJobSegment$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SubmitMediaSegmentJob.SubmitMediaSegmentJobSegment>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Format", new ChildElementBinder<SubmitMediaSegmentJob.SubmitMediaSegmentJobSegment>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaSegmentJob$SubmitMediaSegmentJobSegment$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaSegmentJob.SubmitMediaSegmentJobSegment submitMediaSegmentJobSegment, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaSegmentJobSegment.format = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Duration", new ChildElementBinder<SubmitMediaSegmentJob.SubmitMediaSegmentJobSegment>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaSegmentJob$SubmitMediaSegmentJobSegment$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaSegmentJob.SubmitMediaSegmentJobSegment submitMediaSegmentJobSegment, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaSegmentJobSegment.duration = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("TranscodeIndex", new ChildElementBinder<SubmitMediaSegmentJob.SubmitMediaSegmentJobSegment>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaSegmentJob$SubmitMediaSegmentJobSegment$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaSegmentJob.SubmitMediaSegmentJobSegment submitMediaSegmentJobSegment, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaSegmentJobSegment.transcodeIndex = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("HlsEncrypt", new ChildElementBinder<SubmitMediaSegmentJob.SubmitMediaSegmentJobSegment>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaSegmentJob$SubmitMediaSegmentJobSegment$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaSegmentJob.SubmitMediaSegmentJobSegment submitMediaSegmentJobSegment, String str) throws XmlPullParserException, IOException {
                submitMediaSegmentJobSegment.hlsEncrypt = (SubmitMediaSegmentJob.SubmitMediaSegmentJobHlsEncrypt) QCloudXml.fromXml(xmlPullParser, SubmitMediaSegmentJob.SubmitMediaSegmentJobHlsEncrypt.class, "HlsEncrypt");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitMediaSegmentJob.SubmitMediaSegmentJobSegment fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitMediaSegmentJob.SubmitMediaSegmentJobSegment submitMediaSegmentJobSegment = new SubmitMediaSegmentJob.SubmitMediaSegmentJobSegment();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitMediaSegmentJob.SubmitMediaSegmentJobSegment> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitMediaSegmentJobSegment, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Segment" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitMediaSegmentJobSegment;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitMediaSegmentJobSegment;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitMediaSegmentJob.SubmitMediaSegmentJobSegment submitMediaSegmentJobSegment, String str) throws XmlPullParserException, IOException {
        if (submitMediaSegmentJobSegment == null) {
            return;
        }
        if (str == null) {
            str = "Segment";
        }
        xmlSerializer.startTag("", str);
        if (submitMediaSegmentJobSegment.format != null) {
            xmlSerializer.startTag("", "Format");
            xmlSerializer.text(String.valueOf(submitMediaSegmentJobSegment.format));
            xmlSerializer.endTag("", "Format");
        }
        if (submitMediaSegmentJobSegment.duration != null) {
            xmlSerializer.startTag("", "Duration");
            xmlSerializer.text(String.valueOf(submitMediaSegmentJobSegment.duration));
            xmlSerializer.endTag("", "Duration");
        }
        if (submitMediaSegmentJobSegment.transcodeIndex != null) {
            xmlSerializer.startTag("", "TranscodeIndex");
            xmlSerializer.text(String.valueOf(submitMediaSegmentJobSegment.transcodeIndex));
            xmlSerializer.endTag("", "TranscodeIndex");
        }
        if (submitMediaSegmentJobSegment.hlsEncrypt != null) {
            QCloudXml.toXml(xmlSerializer, submitMediaSegmentJobSegment.hlsEncrypt, "HlsEncrypt");
        }
        xmlSerializer.endTag("", str);
    }
}
