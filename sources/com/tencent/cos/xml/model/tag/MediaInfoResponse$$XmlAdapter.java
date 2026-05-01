package com.tencent.cos.xml.model.tag;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class MediaInfoResponse$$XmlAdapter extends IXmlAdapter<MediaInfoResponse> {
    private HashMap<String, ChildElementBinder<MediaInfoResponse>> childElementBinders;

    public MediaInfoResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<MediaInfoResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("MediaInfo", new ChildElementBinder<MediaInfoResponse>() { // from class: com.tencent.cos.xml.model.tag.MediaInfoResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaInfoResponse mediaInfoResponse, String str) throws XmlPullParserException, IOException {
                mediaInfoResponse.mediaInfo = (MediaInfo) QCloudXml.fromXml(xmlPullParser, MediaInfo.class, "MediaInfo");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public MediaInfoResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        MediaInfoResponse mediaInfoResponse = new MediaInfoResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<MediaInfoResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, mediaInfoResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return mediaInfoResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return mediaInfoResponse;
    }
}
