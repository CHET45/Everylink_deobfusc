package com.tencent.cos.xml.model.p033ci.common;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class VideoTargetRec$$XmlAdapter extends IXmlAdapter<VideoTargetRec> {
    private HashMap<String, ChildElementBinder<VideoTargetRec>> childElementBinders;

    public VideoTargetRec$$XmlAdapter() {
        HashMap<String, ChildElementBinder<VideoTargetRec>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Body", new ChildElementBinder<VideoTargetRec>() { // from class: com.tencent.cos.xml.model.ci.common.VideoTargetRec$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VideoTargetRec videoTargetRec, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                videoTargetRec.body = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Pet", new ChildElementBinder<VideoTargetRec>() { // from class: com.tencent.cos.xml.model.ci.common.VideoTargetRec$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VideoTargetRec videoTargetRec, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                videoTargetRec.pet = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Car", new ChildElementBinder<VideoTargetRec>() { // from class: com.tencent.cos.xml.model.ci.common.VideoTargetRec$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VideoTargetRec videoTargetRec, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                videoTargetRec.car = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public VideoTargetRec fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        VideoTargetRec videoTargetRec = new VideoTargetRec();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<VideoTargetRec> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, videoTargetRec, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "VideoTargetRec" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return videoTargetRec;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return videoTargetRec;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, VideoTargetRec videoTargetRec, String str) throws XmlPullParserException, IOException {
        if (videoTargetRec == null) {
            return;
        }
        if (str == null) {
            str = "VideoTargetRec";
        }
        xmlSerializer.startTag("", str);
        if (videoTargetRec.body != null) {
            xmlSerializer.startTag("", "Body");
            xmlSerializer.text(String.valueOf(videoTargetRec.body));
            xmlSerializer.endTag("", "Body");
        }
        if (videoTargetRec.pet != null) {
            xmlSerializer.startTag("", "Pet");
            xmlSerializer.text(String.valueOf(videoTargetRec.pet));
            xmlSerializer.endTag("", "Pet");
        }
        if (videoTargetRec.car != null) {
            xmlSerializer.startTag("", "Car");
            xmlSerializer.text(String.valueOf(videoTargetRec.car));
            xmlSerializer.endTag("", "Car");
        }
        xmlSerializer.endTag("", str);
    }
}
