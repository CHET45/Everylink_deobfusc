package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.TemplateSmartCover;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateSmartCover$TemplateSmartCoverSmartCover$$XmlAdapter extends IXmlAdapter<TemplateSmartCover.TemplateSmartCoverSmartCover> {
    private HashMap<String, ChildElementBinder<TemplateSmartCover.TemplateSmartCoverSmartCover>> childElementBinders;

    public TemplateSmartCover$TemplateSmartCoverSmartCover$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TemplateSmartCover.TemplateSmartCoverSmartCover>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Format", new ChildElementBinder<TemplateSmartCover.TemplateSmartCoverSmartCover>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSmartCover$TemplateSmartCoverSmartCover$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSmartCover.TemplateSmartCoverSmartCover templateSmartCoverSmartCover, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSmartCoverSmartCover.format = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Width", new ChildElementBinder<TemplateSmartCover.TemplateSmartCoverSmartCover>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSmartCover$TemplateSmartCoverSmartCover$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSmartCover.TemplateSmartCoverSmartCover templateSmartCoverSmartCover, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSmartCoverSmartCover.width = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Height", new ChildElementBinder<TemplateSmartCover.TemplateSmartCoverSmartCover>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSmartCover$TemplateSmartCoverSmartCover$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSmartCover.TemplateSmartCoverSmartCover templateSmartCoverSmartCover, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSmartCoverSmartCover.height = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Count", new ChildElementBinder<TemplateSmartCover.TemplateSmartCoverSmartCover>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSmartCover$TemplateSmartCoverSmartCover$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSmartCover.TemplateSmartCoverSmartCover templateSmartCoverSmartCover, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSmartCoverSmartCover.count = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("DeleteDuplicates", new ChildElementBinder<TemplateSmartCover.TemplateSmartCoverSmartCover>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSmartCover$TemplateSmartCoverSmartCover$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSmartCover.TemplateSmartCoverSmartCover templateSmartCoverSmartCover, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSmartCoverSmartCover.deleteDuplicates = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateSmartCover.TemplateSmartCoverSmartCover fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateSmartCover.TemplateSmartCoverSmartCover templateSmartCoverSmartCover = new TemplateSmartCover.TemplateSmartCoverSmartCover();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateSmartCover.TemplateSmartCoverSmartCover> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templateSmartCoverSmartCover, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "SmartCover" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templateSmartCoverSmartCover;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templateSmartCoverSmartCover;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, TemplateSmartCover.TemplateSmartCoverSmartCover templateSmartCoverSmartCover, String str) throws XmlPullParserException, IOException {
        if (templateSmartCoverSmartCover == null) {
            return;
        }
        if (str == null) {
            str = "SmartCover";
        }
        xmlSerializer.startTag("", str);
        if (templateSmartCoverSmartCover.format != null) {
            xmlSerializer.startTag("", "Format");
            xmlSerializer.text(String.valueOf(templateSmartCoverSmartCover.format));
            xmlSerializer.endTag("", "Format");
        }
        if (templateSmartCoverSmartCover.width != null) {
            xmlSerializer.startTag("", "Width");
            xmlSerializer.text(String.valueOf(templateSmartCoverSmartCover.width));
            xmlSerializer.endTag("", "Width");
        }
        if (templateSmartCoverSmartCover.height != null) {
            xmlSerializer.startTag("", "Height");
            xmlSerializer.text(String.valueOf(templateSmartCoverSmartCover.height));
            xmlSerializer.endTag("", "Height");
        }
        if (templateSmartCoverSmartCover.count != null) {
            xmlSerializer.startTag("", "Count");
            xmlSerializer.text(String.valueOf(templateSmartCoverSmartCover.count));
            xmlSerializer.endTag("", "Count");
        }
        if (templateSmartCoverSmartCover.deleteDuplicates != null) {
            xmlSerializer.startTag("", "DeleteDuplicates");
            xmlSerializer.text(String.valueOf(templateSmartCoverSmartCover.deleteDuplicates));
            xmlSerializer.endTag("", "DeleteDuplicates");
        }
        xmlSerializer.endTag("", str);
    }
}
