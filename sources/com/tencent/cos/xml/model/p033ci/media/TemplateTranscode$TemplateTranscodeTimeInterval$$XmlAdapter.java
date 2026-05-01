package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.TemplateTranscode;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateTranscode$TemplateTranscodeTimeInterval$$XmlAdapter extends IXmlAdapter<TemplateTranscode.TemplateTranscodeTimeInterval> {
    private HashMap<String, ChildElementBinder<TemplateTranscode.TemplateTranscodeTimeInterval>> childElementBinders;

    public TemplateTranscode$TemplateTranscodeTimeInterval$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TemplateTranscode.TemplateTranscodeTimeInterval>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Start", new ChildElementBinder<TemplateTranscode.TemplateTranscodeTimeInterval>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeTimeInterval$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeTimeInterval templateTranscodeTimeInterval, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeTimeInterval.start = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Duration", new ChildElementBinder<TemplateTranscode.TemplateTranscodeTimeInterval>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeTimeInterval$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeTimeInterval templateTranscodeTimeInterval, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeTimeInterval.duration = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateTranscode.TemplateTranscodeTimeInterval fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateTranscode.TemplateTranscodeTimeInterval templateTranscodeTimeInterval = new TemplateTranscode.TemplateTranscodeTimeInterval();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateTranscode.TemplateTranscodeTimeInterval> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templateTranscodeTimeInterval, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "TimeInterval" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templateTranscodeTimeInterval;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templateTranscodeTimeInterval;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, TemplateTranscode.TemplateTranscodeTimeInterval templateTranscodeTimeInterval, String str) throws XmlPullParserException, IOException {
        if (templateTranscodeTimeInterval == null) {
            return;
        }
        if (str == null) {
            str = "TimeInterval";
        }
        xmlSerializer.startTag("", str);
        if (templateTranscodeTimeInterval.start != null) {
            xmlSerializer.startTag("", "Start");
            xmlSerializer.text(String.valueOf(templateTranscodeTimeInterval.start));
            xmlSerializer.endTag("", "Start");
        }
        if (templateTranscodeTimeInterval.duration != null) {
            xmlSerializer.startTag("", "Duration");
            xmlSerializer.text(String.valueOf(templateTranscodeTimeInterval.duration));
            xmlSerializer.endTag("", "Duration");
        }
        xmlSerializer.endTag("", str);
    }
}
