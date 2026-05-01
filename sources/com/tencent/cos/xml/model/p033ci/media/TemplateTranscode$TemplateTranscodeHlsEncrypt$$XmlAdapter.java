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
public class TemplateTranscode$TemplateTranscodeHlsEncrypt$$XmlAdapter extends IXmlAdapter<TemplateTranscode.TemplateTranscodeHlsEncrypt> {
    private HashMap<String, ChildElementBinder<TemplateTranscode.TemplateTranscodeHlsEncrypt>> childElementBinders;

    public TemplateTranscode$TemplateTranscodeHlsEncrypt$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TemplateTranscode.TemplateTranscodeHlsEncrypt>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("IsHlsEncrypt", new ChildElementBinder<TemplateTranscode.TemplateTranscodeHlsEncrypt>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeHlsEncrypt$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeHlsEncrypt templateTranscodeHlsEncrypt, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeHlsEncrypt.isHlsEncrypt = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("UriKey", new ChildElementBinder<TemplateTranscode.TemplateTranscodeHlsEncrypt>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeHlsEncrypt$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeHlsEncrypt templateTranscodeHlsEncrypt, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeHlsEncrypt.uriKey = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateTranscode.TemplateTranscodeHlsEncrypt fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateTranscode.TemplateTranscodeHlsEncrypt templateTranscodeHlsEncrypt = new TemplateTranscode.TemplateTranscodeHlsEncrypt();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateTranscode.TemplateTranscodeHlsEncrypt> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templateTranscodeHlsEncrypt, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "HlsEncrypt" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templateTranscodeHlsEncrypt;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templateTranscodeHlsEncrypt;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, TemplateTranscode.TemplateTranscodeHlsEncrypt templateTranscodeHlsEncrypt, String str) throws XmlPullParserException, IOException {
        if (templateTranscodeHlsEncrypt == null) {
            return;
        }
        if (str == null) {
            str = "HlsEncrypt";
        }
        xmlSerializer.startTag("", str);
        if (templateTranscodeHlsEncrypt.isHlsEncrypt != null) {
            xmlSerializer.startTag("", "IsHlsEncrypt");
            xmlSerializer.text(String.valueOf(templateTranscodeHlsEncrypt.isHlsEncrypt));
            xmlSerializer.endTag("", "IsHlsEncrypt");
        }
        if (templateTranscodeHlsEncrypt.uriKey != null) {
            xmlSerializer.startTag("", "UriKey");
            xmlSerializer.text(String.valueOf(templateTranscodeHlsEncrypt.uriKey));
            xmlSerializer.endTag("", "UriKey");
        }
        xmlSerializer.endTag("", str);
    }
}
