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
public class TemplateWatermark$TemplateWatermarkText$$XmlAdapter extends IXmlAdapter<TemplateWatermark.TemplateWatermarkText> {
    private HashMap<String, ChildElementBinder<TemplateWatermark.TemplateWatermarkText>> childElementBinders;

    public TemplateWatermark$TemplateWatermarkText$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TemplateWatermark.TemplateWatermarkText>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("FontSize", new ChildElementBinder<TemplateWatermark.TemplateWatermarkText>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateWatermark$TemplateWatermarkText$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateWatermark.TemplateWatermarkText templateWatermarkText, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateWatermarkText.fontSize = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("FontType", new ChildElementBinder<TemplateWatermark.TemplateWatermarkText>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateWatermark$TemplateWatermarkText$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateWatermark.TemplateWatermarkText templateWatermarkText, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateWatermarkText.fontType = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("FontColor", new ChildElementBinder<TemplateWatermark.TemplateWatermarkText>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateWatermark$TemplateWatermarkText$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateWatermark.TemplateWatermarkText templateWatermarkText, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateWatermarkText.fontColor = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Transparency", new ChildElementBinder<TemplateWatermark.TemplateWatermarkText>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateWatermark$TemplateWatermarkText$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateWatermark.TemplateWatermarkText templateWatermarkText, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateWatermarkText.transparency = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Text", new ChildElementBinder<TemplateWatermark.TemplateWatermarkText>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateWatermark$TemplateWatermarkText$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateWatermark.TemplateWatermarkText templateWatermarkText, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateWatermarkText.text = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateWatermark.TemplateWatermarkText fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateWatermark.TemplateWatermarkText templateWatermarkText = new TemplateWatermark.TemplateWatermarkText();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateWatermark.TemplateWatermarkText> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templateWatermarkText, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Text" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templateWatermarkText;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templateWatermarkText;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, TemplateWatermark.TemplateWatermarkText templateWatermarkText, String str) throws XmlPullParserException, IOException {
        if (templateWatermarkText == null) {
            return;
        }
        if (str == null) {
            str = "Text";
        }
        xmlSerializer.startTag("", str);
        if (templateWatermarkText.fontSize != null) {
            xmlSerializer.startTag("", "FontSize");
            xmlSerializer.text(String.valueOf(templateWatermarkText.fontSize));
            xmlSerializer.endTag("", "FontSize");
        }
        if (templateWatermarkText.fontType != null) {
            xmlSerializer.startTag("", "FontType");
            xmlSerializer.text(String.valueOf(templateWatermarkText.fontType));
            xmlSerializer.endTag("", "FontType");
        }
        if (templateWatermarkText.fontColor != null) {
            xmlSerializer.startTag("", "FontColor");
            xmlSerializer.text(String.valueOf(templateWatermarkText.fontColor));
            xmlSerializer.endTag("", "FontColor");
        }
        if (templateWatermarkText.transparency != null) {
            xmlSerializer.startTag("", "Transparency");
            xmlSerializer.text(String.valueOf(templateWatermarkText.transparency));
            xmlSerializer.endTag("", "Transparency");
        }
        if (templateWatermarkText.text != null) {
            xmlSerializer.startTag("", "Text");
            xmlSerializer.text(String.valueOf(templateWatermarkText.text));
            xmlSerializer.endTag("", "Text");
        }
        xmlSerializer.endTag("", str);
    }
}
