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
public class TemplateTranscode$TemplateTranscodeDashEncrypt$$XmlAdapter extends IXmlAdapter<TemplateTranscode.TemplateTranscodeDashEncrypt> {
    private HashMap<String, ChildElementBinder<TemplateTranscode.TemplateTranscodeDashEncrypt>> childElementBinders;

    public TemplateTranscode$TemplateTranscodeDashEncrypt$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TemplateTranscode.TemplateTranscodeDashEncrypt>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("IsEncrypt", new ChildElementBinder<TemplateTranscode.TemplateTranscodeDashEncrypt>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeDashEncrypt$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeDashEncrypt templateTranscodeDashEncrypt, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeDashEncrypt.isEncrypt = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("UriKey", new ChildElementBinder<TemplateTranscode.TemplateTranscodeDashEncrypt>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeDashEncrypt$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeDashEncrypt templateTranscodeDashEncrypt, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeDashEncrypt.uriKey = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateTranscode.TemplateTranscodeDashEncrypt fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateTranscode.TemplateTranscodeDashEncrypt templateTranscodeDashEncrypt = new TemplateTranscode.TemplateTranscodeDashEncrypt();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateTranscode.TemplateTranscodeDashEncrypt> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templateTranscodeDashEncrypt, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "DashEncrypt" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templateTranscodeDashEncrypt;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templateTranscodeDashEncrypt;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, TemplateTranscode.TemplateTranscodeDashEncrypt templateTranscodeDashEncrypt, String str) throws XmlPullParserException, IOException {
        if (templateTranscodeDashEncrypt == null) {
            return;
        }
        if (str == null) {
            str = "DashEncrypt";
        }
        xmlSerializer.startTag("", str);
        if (templateTranscodeDashEncrypt.isEncrypt != null) {
            xmlSerializer.startTag("", "IsEncrypt");
            xmlSerializer.text(String.valueOf(templateTranscodeDashEncrypt.isEncrypt));
            xmlSerializer.endTag("", "IsEncrypt");
        }
        if (templateTranscodeDashEncrypt.uriKey != null) {
            xmlSerializer.startTag("", "UriKey");
            xmlSerializer.text(String.valueOf(templateTranscodeDashEncrypt.uriKey));
            xmlSerializer.endTag("", "UriKey");
        }
        xmlSerializer.endTag("", str);
    }
}
