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
public class MediaResult$$XmlAdapter extends IXmlAdapter<MediaResult> {
    private HashMap<String, ChildElementBinder<MediaResult>> childElementBinders;

    public MediaResult$$XmlAdapter() {
        HashMap<String, ChildElementBinder<MediaResult>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("OutputFile", new ChildElementBinder<MediaResult>() { // from class: com.tencent.cos.xml.model.ci.common.MediaResult$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, MediaResult mediaResult, String str) throws XmlPullParserException, IOException {
                mediaResult.outputFile = (MediaResultOutputFile) QCloudXml.fromXml(xmlPullParser, MediaResultOutputFile.class, "OutputFile");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public MediaResult fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        MediaResult mediaResult = new MediaResult();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<MediaResult> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, mediaResult, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "MediaResult" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return mediaResult;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return mediaResult;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, MediaResult mediaResult, String str) throws XmlPullParserException, IOException {
        if (mediaResult == null) {
            return;
        }
        if (str == null) {
            str = "MediaResult";
        }
        xmlSerializer.startTag("", str);
        if (mediaResult.outputFile != null) {
            QCloudXml.toXml(xmlSerializer, mediaResult.outputFile, "OutputFile");
        }
        xmlSerializer.endTag("", str);
    }
}
