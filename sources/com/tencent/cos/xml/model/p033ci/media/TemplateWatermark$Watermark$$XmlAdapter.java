package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.TemplateWatermark;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateWatermark$Watermark$$XmlAdapter extends IXmlAdapter<TemplateWatermark.Watermark> {
    private HashMap<String, ChildElementBinder<TemplateWatermark.Watermark>> childElementBinders;

    public TemplateWatermark$Watermark$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TemplateWatermark.Watermark>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Type", new ChildElementBinder<TemplateWatermark.Watermark>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateWatermark$Watermark$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateWatermark.Watermark watermark, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                watermark.type = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Pos", new ChildElementBinder<TemplateWatermark.Watermark>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateWatermark$Watermark$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateWatermark.Watermark watermark, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                watermark.pos = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("LocMode", new ChildElementBinder<TemplateWatermark.Watermark>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateWatermark$Watermark$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateWatermark.Watermark watermark, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                watermark.locMode = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Dx", new ChildElementBinder<TemplateWatermark.Watermark>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateWatermark$Watermark$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateWatermark.Watermark watermark, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                watermark.f1829dx = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Dy", new ChildElementBinder<TemplateWatermark.Watermark>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateWatermark$Watermark$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateWatermark.Watermark watermark, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                watermark.f1830dy = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("StartTime", new ChildElementBinder<TemplateWatermark.Watermark>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateWatermark$Watermark$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateWatermark.Watermark watermark, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                watermark.startTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("EndTime", new ChildElementBinder<TemplateWatermark.Watermark>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateWatermark$Watermark$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateWatermark.Watermark watermark, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                watermark.endTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SlideConfig", new ChildElementBinder<TemplateWatermark.Watermark>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateWatermark$Watermark$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateWatermark.Watermark watermark, String str) throws XmlPullParserException, IOException {
                watermark.slideConfig = (TemplateWatermark.TemplateWatermarkSlideConfig) QCloudXml.fromXml(xmlPullParser, TemplateWatermark.TemplateWatermarkSlideConfig.class, "SlideConfig");
            }
        });
        this.childElementBinders.put("Image", new ChildElementBinder<TemplateWatermark.Watermark>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateWatermark$Watermark$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateWatermark.Watermark watermark, String str) throws XmlPullParserException, IOException {
                watermark.image = (TemplateWatermark.TemplateWatermarkImage) QCloudXml.fromXml(xmlPullParser, TemplateWatermark.TemplateWatermarkImage.class, "Image");
            }
        });
        this.childElementBinders.put("Text", new ChildElementBinder<TemplateWatermark.Watermark>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateWatermark$Watermark$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateWatermark.Watermark watermark, String str) throws XmlPullParserException, IOException {
                watermark.text = (TemplateWatermark.TemplateWatermarkText) QCloudXml.fromXml(xmlPullParser, TemplateWatermark.TemplateWatermarkText.class, "Text");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateWatermark.Watermark fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateWatermark.Watermark watermark = new TemplateWatermark.Watermark();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateWatermark.Watermark> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, watermark, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Watermark" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return watermark;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return watermark;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, TemplateWatermark.Watermark watermark, String str) throws XmlPullParserException, IOException {
        if (watermark == null) {
            return;
        }
        if (str == null) {
            str = "Watermark";
        }
        xmlSerializer.startTag("", str);
        if (watermark.type != null) {
            xmlSerializer.startTag("", "Type");
            xmlSerializer.text(String.valueOf(watermark.type));
            xmlSerializer.endTag("", "Type");
        }
        if (watermark.pos != null) {
            xmlSerializer.startTag("", "Pos");
            xmlSerializer.text(String.valueOf(watermark.pos));
            xmlSerializer.endTag("", "Pos");
        }
        if (watermark.locMode != null) {
            xmlSerializer.startTag("", "LocMode");
            xmlSerializer.text(String.valueOf(watermark.locMode));
            xmlSerializer.endTag("", "LocMode");
        }
        if (watermark.f1829dx != null) {
            xmlSerializer.startTag("", "Dx");
            xmlSerializer.text(String.valueOf(watermark.f1829dx));
            xmlSerializer.endTag("", "Dx");
        }
        if (watermark.f1830dy != null) {
            xmlSerializer.startTag("", "Dy");
            xmlSerializer.text(String.valueOf(watermark.f1830dy));
            xmlSerializer.endTag("", "Dy");
        }
        if (watermark.startTime != null) {
            xmlSerializer.startTag("", "StartTime");
            xmlSerializer.text(String.valueOf(watermark.startTime));
            xmlSerializer.endTag("", "StartTime");
        }
        if (watermark.endTime != null) {
            xmlSerializer.startTag("", "EndTime");
            xmlSerializer.text(String.valueOf(watermark.endTime));
            xmlSerializer.endTag("", "EndTime");
        }
        if (watermark.slideConfig != null) {
            QCloudXml.toXml(xmlSerializer, watermark.slideConfig, "SlideConfig");
        }
        if (watermark.image != null) {
            QCloudXml.toXml(xmlSerializer, watermark.image, "Image");
        }
        if (watermark.text != null) {
            QCloudXml.toXml(xmlSerializer, watermark.text, "Text");
        }
        xmlSerializer.endTag("", str);
    }
}
