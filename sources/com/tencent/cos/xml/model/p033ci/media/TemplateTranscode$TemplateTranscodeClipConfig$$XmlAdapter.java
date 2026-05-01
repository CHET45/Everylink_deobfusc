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
public class TemplateTranscode$TemplateTranscodeClipConfig$$XmlAdapter extends IXmlAdapter<TemplateTranscode.TemplateTranscodeClipConfig> {
    private HashMap<String, ChildElementBinder<TemplateTranscode.TemplateTranscodeClipConfig>> childElementBinders;

    public TemplateTranscode$TemplateTranscodeClipConfig$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TemplateTranscode.TemplateTranscodeClipConfig>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Duration", new ChildElementBinder<TemplateTranscode.TemplateTranscodeClipConfig>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeClipConfig$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeClipConfig templateTranscodeClipConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeClipConfig.duration = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateTranscode.TemplateTranscodeClipConfig fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateTranscode.TemplateTranscodeClipConfig templateTranscodeClipConfig = new TemplateTranscode.TemplateTranscodeClipConfig();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateTranscode.TemplateTranscodeClipConfig> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templateTranscodeClipConfig, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "ClipConfig" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templateTranscodeClipConfig;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templateTranscodeClipConfig;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, TemplateTranscode.TemplateTranscodeClipConfig templateTranscodeClipConfig, String str) throws XmlPullParserException, IOException {
        if (templateTranscodeClipConfig == null) {
            return;
        }
        if (str == null) {
            str = "ClipConfig";
        }
        xmlSerializer.startTag("", str);
        if (templateTranscodeClipConfig.duration != null) {
            xmlSerializer.startTag("", "Duration");
            xmlSerializer.text(String.valueOf(templateTranscodeClipConfig.duration));
            xmlSerializer.endTag("", "Duration");
        }
        xmlSerializer.endTag("", str);
    }
}
