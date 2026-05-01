package com.tencent.cos.xml.model.p033ci.common;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class MediaResultMd5Info$$XmlAdapter extends IXmlAdapter<MediaResultMd5Info> {
    private HashMap<String, ChildElementBinder<MediaResultMd5Info>> childElementBinders;

    public MediaResultMd5Info$$XmlAdapter() {
        HashMap<String, ChildElementBinder<MediaResultMd5Info>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("ObjectName", new ChildElementBinder<MediaResultMd5Info>() { // from class: com.tencent.cos.xml.model.ci.common.MediaResultMd5Info$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaResultMd5Info mediaResultMd5Info, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaResultMd5Info.objectName = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Md5", new ChildElementBinder<MediaResultMd5Info>() { // from class: com.tencent.cos.xml.model.ci.common.MediaResultMd5Info$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaResultMd5Info mediaResultMd5Info, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaResultMd5Info.md5 = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public MediaResultMd5Info fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        MediaResultMd5Info mediaResultMd5Info = new MediaResultMd5Info();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<MediaResultMd5Info> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, mediaResultMd5Info, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "MediaResultMd5Info" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return mediaResultMd5Info;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return mediaResultMd5Info;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, MediaResultMd5Info mediaResultMd5Info, String str) throws XmlPullParserException, IOException {
        if (mediaResultMd5Info == null) {
            return;
        }
        if (str == null) {
            str = "MediaResultMd5Info";
        }
        xmlSerializer.startTag("", str);
        if (mediaResultMd5Info.objectName != null) {
            xmlSerializer.startTag("", "ObjectName");
            xmlSerializer.text(String.valueOf(mediaResultMd5Info.objectName));
            xmlSerializer.endTag("", "ObjectName");
        }
        if (mediaResultMd5Info.md5 != null) {
            xmlSerializer.startTag("", "Md5");
            xmlSerializer.text(String.valueOf(mediaResultMd5Info.md5));
            xmlSerializer.endTag("", "Md5");
        }
        xmlSerializer.endTag("", str);
    }
}
