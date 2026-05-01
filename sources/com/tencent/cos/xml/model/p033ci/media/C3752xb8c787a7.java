package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.TemplateSnapshot;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.TemplateSnapshot$TemplateSnapshotSpriteSnapshotConfig$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3752xb8c787a7 extends IXmlAdapter<TemplateSnapshot.TemplateSnapshotSpriteSnapshotConfig> {
    private HashMap<String, ChildElementBinder<TemplateSnapshot.TemplateSnapshotSpriteSnapshotConfig>> childElementBinders;

    public C3752xb8c787a7() {
        HashMap<String, ChildElementBinder<TemplateSnapshot.TemplateSnapshotSpriteSnapshotConfig>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("CellWidth", new ChildElementBinder<TemplateSnapshot.TemplateSnapshotSpriteSnapshotConfig>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSnapshot$TemplateSnapshotSpriteSnapshotConfig$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSnapshot.TemplateSnapshotSpriteSnapshotConfig templateSnapshotSpriteSnapshotConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSnapshotSpriteSnapshotConfig.cellWidth = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CellHeight", new ChildElementBinder<TemplateSnapshot.TemplateSnapshotSpriteSnapshotConfig>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSnapshot$TemplateSnapshotSpriteSnapshotConfig$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSnapshot.TemplateSnapshotSpriteSnapshotConfig templateSnapshotSpriteSnapshotConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSnapshotSpriteSnapshotConfig.cellHeight = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Padding", new ChildElementBinder<TemplateSnapshot.TemplateSnapshotSpriteSnapshotConfig>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSnapshot$TemplateSnapshotSpriteSnapshotConfig$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSnapshot.TemplateSnapshotSpriteSnapshotConfig templateSnapshotSpriteSnapshotConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSnapshotSpriteSnapshotConfig.padding = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Margin", new ChildElementBinder<TemplateSnapshot.TemplateSnapshotSpriteSnapshotConfig>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSnapshot$TemplateSnapshotSpriteSnapshotConfig$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSnapshot.TemplateSnapshotSpriteSnapshotConfig templateSnapshotSpriteSnapshotConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSnapshotSpriteSnapshotConfig.margin = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Color", new ChildElementBinder<TemplateSnapshot.TemplateSnapshotSpriteSnapshotConfig>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSnapshot$TemplateSnapshotSpriteSnapshotConfig$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSnapshot.TemplateSnapshotSpriteSnapshotConfig templateSnapshotSpriteSnapshotConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSnapshotSpriteSnapshotConfig.color = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Columns", new ChildElementBinder<TemplateSnapshot.TemplateSnapshotSpriteSnapshotConfig>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSnapshot$TemplateSnapshotSpriteSnapshotConfig$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSnapshot.TemplateSnapshotSpriteSnapshotConfig templateSnapshotSpriteSnapshotConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSnapshotSpriteSnapshotConfig.columns = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Lines", new ChildElementBinder<TemplateSnapshot.TemplateSnapshotSpriteSnapshotConfig>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSnapshot$TemplateSnapshotSpriteSnapshotConfig$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSnapshot.TemplateSnapshotSpriteSnapshotConfig templateSnapshotSpriteSnapshotConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSnapshotSpriteSnapshotConfig.lines = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("ScaleMethod", new ChildElementBinder<TemplateSnapshot.TemplateSnapshotSpriteSnapshotConfig>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSnapshot$TemplateSnapshotSpriteSnapshotConfig$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSnapshot.TemplateSnapshotSpriteSnapshotConfig templateSnapshotSpriteSnapshotConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSnapshotSpriteSnapshotConfig.scaleMethod = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateSnapshot.TemplateSnapshotSpriteSnapshotConfig fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateSnapshot.TemplateSnapshotSpriteSnapshotConfig templateSnapshotSpriteSnapshotConfig = new TemplateSnapshot.TemplateSnapshotSpriteSnapshotConfig();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateSnapshot.TemplateSnapshotSpriteSnapshotConfig> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templateSnapshotSpriteSnapshotConfig, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "SpriteSnapshotConfig" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templateSnapshotSpriteSnapshotConfig;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templateSnapshotSpriteSnapshotConfig;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, TemplateSnapshot.TemplateSnapshotSpriteSnapshotConfig templateSnapshotSpriteSnapshotConfig, String str) throws XmlPullParserException, IOException {
        if (templateSnapshotSpriteSnapshotConfig == null) {
            return;
        }
        if (str == null) {
            str = "SpriteSnapshotConfig";
        }
        xmlSerializer.startTag("", str);
        if (templateSnapshotSpriteSnapshotConfig.cellWidth != null) {
            xmlSerializer.startTag("", "CellWidth");
            xmlSerializer.text(String.valueOf(templateSnapshotSpriteSnapshotConfig.cellWidth));
            xmlSerializer.endTag("", "CellWidth");
        }
        if (templateSnapshotSpriteSnapshotConfig.cellHeight != null) {
            xmlSerializer.startTag("", "CellHeight");
            xmlSerializer.text(String.valueOf(templateSnapshotSpriteSnapshotConfig.cellHeight));
            xmlSerializer.endTag("", "CellHeight");
        }
        if (templateSnapshotSpriteSnapshotConfig.padding != null) {
            xmlSerializer.startTag("", "Padding");
            xmlSerializer.text(String.valueOf(templateSnapshotSpriteSnapshotConfig.padding));
            xmlSerializer.endTag("", "Padding");
        }
        if (templateSnapshotSpriteSnapshotConfig.margin != null) {
            xmlSerializer.startTag("", "Margin");
            xmlSerializer.text(String.valueOf(templateSnapshotSpriteSnapshotConfig.margin));
            xmlSerializer.endTag("", "Margin");
        }
        if (templateSnapshotSpriteSnapshotConfig.color != null) {
            xmlSerializer.startTag("", "Color");
            xmlSerializer.text(String.valueOf(templateSnapshotSpriteSnapshotConfig.color));
            xmlSerializer.endTag("", "Color");
        }
        if (templateSnapshotSpriteSnapshotConfig.columns != null) {
            xmlSerializer.startTag("", "Columns");
            xmlSerializer.text(String.valueOf(templateSnapshotSpriteSnapshotConfig.columns));
            xmlSerializer.endTag("", "Columns");
        }
        if (templateSnapshotSpriteSnapshotConfig.lines != null) {
            xmlSerializer.startTag("", "Lines");
            xmlSerializer.text(String.valueOf(templateSnapshotSpriteSnapshotConfig.lines));
            xmlSerializer.endTag("", "Lines");
        }
        if (templateSnapshotSpriteSnapshotConfig.scaleMethod != null) {
            xmlSerializer.startTag("", "ScaleMethod");
            xmlSerializer.text(String.valueOf(templateSnapshotSpriteSnapshotConfig.scaleMethod));
            xmlSerializer.endTag("", "ScaleMethod");
        }
        xmlSerializer.endTag("", str);
    }
}
