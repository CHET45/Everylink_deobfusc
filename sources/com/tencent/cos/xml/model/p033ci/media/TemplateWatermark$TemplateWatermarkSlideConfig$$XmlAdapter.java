package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.TemplateWatermark;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateWatermark$TemplateWatermarkSlideConfig$$XmlAdapter extends IXmlAdapter<TemplateWatermark.TemplateWatermarkSlideConfig> {
    private HashMap<String, ChildElementBinder<TemplateWatermark.TemplateWatermarkSlideConfig>> childElementBinders;

    public TemplateWatermark$TemplateWatermarkSlideConfig$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TemplateWatermark.TemplateWatermarkSlideConfig>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("SlideMode", new ChildElementBinder<TemplateWatermark.TemplateWatermarkSlideConfig>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateWatermark$TemplateWatermarkSlideConfig$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateWatermark.TemplateWatermarkSlideConfig templateWatermarkSlideConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateWatermarkSlideConfig.slideMode = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("XSlideSpeed", new ChildElementBinder<TemplateWatermark.TemplateWatermarkSlideConfig>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateWatermark$TemplateWatermarkSlideConfig$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateWatermark.TemplateWatermarkSlideConfig templateWatermarkSlideConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateWatermarkSlideConfig.xSlideSpeed = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("YSlideSpeed", new ChildElementBinder<TemplateWatermark.TemplateWatermarkSlideConfig>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateWatermark$TemplateWatermarkSlideConfig$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateWatermark.TemplateWatermarkSlideConfig templateWatermarkSlideConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateWatermarkSlideConfig.ySlideSpeed = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateWatermark.TemplateWatermarkSlideConfig fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateWatermark.TemplateWatermarkSlideConfig templateWatermarkSlideConfig = new TemplateWatermark.TemplateWatermarkSlideConfig();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateWatermark.TemplateWatermarkSlideConfig> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templateWatermarkSlideConfig, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "SlideConfig" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templateWatermarkSlideConfig;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templateWatermarkSlideConfig;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, TemplateWatermark.TemplateWatermarkSlideConfig templateWatermarkSlideConfig, String str) throws XmlPullParserException, IOException {
        if (templateWatermarkSlideConfig == null) {
            return;
        }
        if (str == null) {
            str = "SlideConfig";
        }
        xmlSerializer.startTag("", str);
        if (templateWatermarkSlideConfig.slideMode != null) {
            xmlSerializer.startTag("", "SlideMode");
            xmlSerializer.text(String.valueOf(templateWatermarkSlideConfig.slideMode));
            xmlSerializer.endTag("", "SlideMode");
        }
        if (templateWatermarkSlideConfig.xSlideSpeed != null) {
            xmlSerializer.startTag("", "XSlideSpeed");
            xmlSerializer.text(String.valueOf(templateWatermarkSlideConfig.xSlideSpeed));
            xmlSerializer.endTag("", "XSlideSpeed");
        }
        if (templateWatermarkSlideConfig.ySlideSpeed != null) {
            xmlSerializer.startTag("", "YSlideSpeed");
            xmlSerializer.text(String.valueOf(templateWatermarkSlideConfig.ySlideSpeed));
            xmlSerializer.endTag("", "YSlideSpeed");
        }
        xmlSerializer.endTag("", str);
    }
}
