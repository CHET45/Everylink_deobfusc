package com.tencent.cos.xml.model.tag;

import com.tencent.cos.xml.model.tag.MediaInfo;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class MediaInfo$Subtitle$$XmlAdapter extends IXmlAdapter<MediaInfo.Subtitle> {
    private HashMap<String, ChildElementBinder<MediaInfo.Subtitle>> childElementBinders;

    public MediaInfo$Subtitle$$XmlAdapter() {
        HashMap<String, ChildElementBinder<MediaInfo.Subtitle>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Index", new ChildElementBinder<MediaInfo.Subtitle>() { // from class: com.tencent.cos.xml.model.tag.MediaInfo$Subtitle$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfo.Subtitle subtitle, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                subtitle.index = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Language", new ChildElementBinder<MediaInfo.Subtitle>() { // from class: com.tencent.cos.xml.model.tag.MediaInfo$Subtitle$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfo.Subtitle subtitle, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                subtitle.language = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public MediaInfo.Subtitle fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        MediaInfo.Subtitle subtitle = new MediaInfo.Subtitle();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<MediaInfo.Subtitle> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, subtitle, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Subtitle" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return subtitle;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return subtitle;
    }
}
