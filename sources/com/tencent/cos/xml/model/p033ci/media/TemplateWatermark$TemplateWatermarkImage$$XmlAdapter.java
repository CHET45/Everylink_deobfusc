package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.media.TemplateWatermark;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateWatermark$TemplateWatermarkImage$$XmlAdapter extends IXmlAdapter<TemplateWatermark.TemplateWatermarkImage> {
    private HashMap<String, ChildElementBinder<TemplateWatermark.TemplateWatermarkImage>> childElementBinders;

    public TemplateWatermark$TemplateWatermarkImage$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TemplateWatermark.TemplateWatermarkImage>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.URL_ELEMENT, new ChildElementBinder<TemplateWatermark.TemplateWatermarkImage>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateWatermark$TemplateWatermarkImage$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateWatermark.TemplateWatermarkImage templateWatermarkImage, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateWatermarkImage.url = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Mode", new ChildElementBinder<TemplateWatermark.TemplateWatermarkImage>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateWatermark$TemplateWatermarkImage$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateWatermark.TemplateWatermarkImage templateWatermarkImage, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateWatermarkImage.mode = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Width", new ChildElementBinder<TemplateWatermark.TemplateWatermarkImage>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateWatermark$TemplateWatermarkImage$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateWatermark.TemplateWatermarkImage templateWatermarkImage, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateWatermarkImage.width = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Height", new ChildElementBinder<TemplateWatermark.TemplateWatermarkImage>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateWatermark$TemplateWatermarkImage$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateWatermark.TemplateWatermarkImage templateWatermarkImage, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateWatermarkImage.height = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Transparency", new ChildElementBinder<TemplateWatermark.TemplateWatermarkImage>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateWatermark$TemplateWatermarkImage$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateWatermark.TemplateWatermarkImage templateWatermarkImage, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateWatermarkImage.transparency = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Background", new ChildElementBinder<TemplateWatermark.TemplateWatermarkImage>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateWatermark$TemplateWatermarkImage$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateWatermark.TemplateWatermarkImage templateWatermarkImage, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateWatermarkImage.background = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateWatermark.TemplateWatermarkImage fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateWatermark.TemplateWatermarkImage templateWatermarkImage = new TemplateWatermark.TemplateWatermarkImage();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateWatermark.TemplateWatermarkImage> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templateWatermarkImage, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Image" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templateWatermarkImage;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templateWatermarkImage;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, TemplateWatermark.TemplateWatermarkImage templateWatermarkImage, String str) throws XmlPullParserException, IOException {
        if (templateWatermarkImage == null) {
            return;
        }
        if (str == null) {
            str = "Image";
        }
        xmlSerializer.startTag("", str);
        if (templateWatermarkImage.url != null) {
            xmlSerializer.startTag("", Constants.URL_ELEMENT);
            xmlSerializer.text(String.valueOf(templateWatermarkImage.url));
            xmlSerializer.endTag("", Constants.URL_ELEMENT);
        }
        if (templateWatermarkImage.mode != null) {
            xmlSerializer.startTag("", "Mode");
            xmlSerializer.text(String.valueOf(templateWatermarkImage.mode));
            xmlSerializer.endTag("", "Mode");
        }
        if (templateWatermarkImage.width != null) {
            xmlSerializer.startTag("", "Width");
            xmlSerializer.text(String.valueOf(templateWatermarkImage.width));
            xmlSerializer.endTag("", "Width");
        }
        if (templateWatermarkImage.height != null) {
            xmlSerializer.startTag("", "Height");
            xmlSerializer.text(String.valueOf(templateWatermarkImage.height));
            xmlSerializer.endTag("", "Height");
        }
        if (templateWatermarkImage.transparency != null) {
            xmlSerializer.startTag("", "Transparency");
            xmlSerializer.text(String.valueOf(templateWatermarkImage.transparency));
            xmlSerializer.endTag("", "Transparency");
        }
        if (templateWatermarkImage.background != null) {
            xmlSerializer.startTag("", "Background");
            xmlSerializer.text(String.valueOf(templateWatermarkImage.background));
            xmlSerializer.endTag("", "Background");
        }
        xmlSerializer.endTag("", str);
    }
}
