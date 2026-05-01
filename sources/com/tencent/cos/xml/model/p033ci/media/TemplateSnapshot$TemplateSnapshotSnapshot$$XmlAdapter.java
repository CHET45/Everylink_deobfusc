package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.blob.BlobConstants;
import com.tencent.cos.xml.model.p033ci.media.TemplateSnapshot;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateSnapshot$TemplateSnapshotSnapshot$$XmlAdapter extends IXmlAdapter<TemplateSnapshot.TemplateSnapshotSnapshot> {
    private HashMap<String, ChildElementBinder<TemplateSnapshot.TemplateSnapshotSnapshot>> childElementBinders;

    public TemplateSnapshot$TemplateSnapshotSnapshot$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TemplateSnapshot.TemplateSnapshotSnapshot>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Mode", new ChildElementBinder<TemplateSnapshot.TemplateSnapshotSnapshot>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSnapshot$TemplateSnapshotSnapshot$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSnapshot.TemplateSnapshotSnapshot templateSnapshotSnapshot, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSnapshotSnapshot.mode = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Start", new ChildElementBinder<TemplateSnapshot.TemplateSnapshotSnapshot>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSnapshot$TemplateSnapshotSnapshot$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSnapshot.TemplateSnapshotSnapshot templateSnapshotSnapshot, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSnapshotSnapshot.start = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("TimeInterval", new ChildElementBinder<TemplateSnapshot.TemplateSnapshotSnapshot>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSnapshot$TemplateSnapshotSnapshot$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSnapshot.TemplateSnapshotSnapshot templateSnapshotSnapshot, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSnapshotSnapshot.timeInterval = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Count", new ChildElementBinder<TemplateSnapshot.TemplateSnapshotSnapshot>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSnapshot$TemplateSnapshotSnapshot$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSnapshot.TemplateSnapshotSnapshot templateSnapshotSnapshot, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSnapshotSnapshot.count = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Width", new ChildElementBinder<TemplateSnapshot.TemplateSnapshotSnapshot>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSnapshot$TemplateSnapshotSnapshot$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSnapshot.TemplateSnapshotSnapshot templateSnapshotSnapshot, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSnapshotSnapshot.width = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Height", new ChildElementBinder<TemplateSnapshot.TemplateSnapshotSnapshot>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSnapshot$TemplateSnapshotSnapshot$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSnapshot.TemplateSnapshotSnapshot templateSnapshotSnapshot, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSnapshotSnapshot.height = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CIParam", new ChildElementBinder<TemplateSnapshot.TemplateSnapshotSnapshot>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSnapshot$TemplateSnapshotSnapshot$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSnapshot.TemplateSnapshotSnapshot templateSnapshotSnapshot, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSnapshotSnapshot.cIParam = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("IsCheckCount", new ChildElementBinder<TemplateSnapshot.TemplateSnapshotSnapshot>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSnapshot$TemplateSnapshotSnapshot$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSnapshot.TemplateSnapshotSnapshot templateSnapshotSnapshot, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSnapshotSnapshot.isCheckCount = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("IsCheckBlack", new ChildElementBinder<TemplateSnapshot.TemplateSnapshotSnapshot>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSnapshot$TemplateSnapshotSnapshot$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSnapshot.TemplateSnapshotSnapshot templateSnapshotSnapshot, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSnapshotSnapshot.isCheckBlack = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BlackLevel", new ChildElementBinder<TemplateSnapshot.TemplateSnapshotSnapshot>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSnapshot$TemplateSnapshotSnapshot$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSnapshot.TemplateSnapshotSnapshot templateSnapshotSnapshot, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSnapshotSnapshot.blackLevel = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("PixelBlackThreshold", new ChildElementBinder<TemplateSnapshot.TemplateSnapshotSnapshot>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSnapshot$TemplateSnapshotSnapshot$$XmlAdapter.11
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSnapshot.TemplateSnapshotSnapshot templateSnapshotSnapshot, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSnapshotSnapshot.pixelBlackThreshold = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SnapshotOutMode", new ChildElementBinder<TemplateSnapshot.TemplateSnapshotSnapshot>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSnapshot$TemplateSnapshotSnapshot$$XmlAdapter.12
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSnapshot.TemplateSnapshotSnapshot templateSnapshotSnapshot, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSnapshotSnapshot.snapshotOutMode = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SpriteSnapshotConfig", new ChildElementBinder<TemplateSnapshot.TemplateSnapshotSnapshot>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSnapshot$TemplateSnapshotSnapshot$$XmlAdapter.13
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSnapshot.TemplateSnapshotSnapshot templateSnapshotSnapshot, String str) throws XmlPullParserException, IOException {
                templateSnapshotSnapshot.spriteSnapshotConfig = (TemplateSnapshot.TemplateSnapshotSpriteSnapshotConfig) QCloudXml.fromXml(xmlPullParser, TemplateSnapshot.TemplateSnapshotSpriteSnapshotConfig.class, "SpriteSnapshotConfig");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateSnapshot.TemplateSnapshotSnapshot fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateSnapshot.TemplateSnapshotSnapshot templateSnapshotSnapshot = new TemplateSnapshot.TemplateSnapshotSnapshot();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateSnapshot.TemplateSnapshotSnapshot> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templateSnapshotSnapshot, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? BlobConstants.SNAPSHOT_ELEMENT : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templateSnapshotSnapshot;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templateSnapshotSnapshot;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, TemplateSnapshot.TemplateSnapshotSnapshot templateSnapshotSnapshot, String str) throws XmlPullParserException, IOException {
        if (templateSnapshotSnapshot == null) {
            return;
        }
        if (str == null) {
            str = BlobConstants.SNAPSHOT_ELEMENT;
        }
        xmlSerializer.startTag("", str);
        if (templateSnapshotSnapshot.mode != null) {
            xmlSerializer.startTag("", "Mode");
            xmlSerializer.text(String.valueOf(templateSnapshotSnapshot.mode));
            xmlSerializer.endTag("", "Mode");
        }
        if (templateSnapshotSnapshot.start != null) {
            xmlSerializer.startTag("", "Start");
            xmlSerializer.text(String.valueOf(templateSnapshotSnapshot.start));
            xmlSerializer.endTag("", "Start");
        }
        if (templateSnapshotSnapshot.timeInterval != null) {
            xmlSerializer.startTag("", "TimeInterval");
            xmlSerializer.text(String.valueOf(templateSnapshotSnapshot.timeInterval));
            xmlSerializer.endTag("", "TimeInterval");
        }
        if (templateSnapshotSnapshot.count != null) {
            xmlSerializer.startTag("", "Count");
            xmlSerializer.text(String.valueOf(templateSnapshotSnapshot.count));
            xmlSerializer.endTag("", "Count");
        }
        if (templateSnapshotSnapshot.width != null) {
            xmlSerializer.startTag("", "Width");
            xmlSerializer.text(String.valueOf(templateSnapshotSnapshot.width));
            xmlSerializer.endTag("", "Width");
        }
        if (templateSnapshotSnapshot.height != null) {
            xmlSerializer.startTag("", "Height");
            xmlSerializer.text(String.valueOf(templateSnapshotSnapshot.height));
            xmlSerializer.endTag("", "Height");
        }
        if (templateSnapshotSnapshot.cIParam != null) {
            xmlSerializer.startTag("", "CIParam");
            xmlSerializer.text(String.valueOf(templateSnapshotSnapshot.cIParam));
            xmlSerializer.endTag("", "CIParam");
        }
        if (templateSnapshotSnapshot.isCheckCount != null) {
            xmlSerializer.startTag("", "IsCheckCount");
            xmlSerializer.text(String.valueOf(templateSnapshotSnapshot.isCheckCount));
            xmlSerializer.endTag("", "IsCheckCount");
        }
        if (templateSnapshotSnapshot.isCheckBlack != null) {
            xmlSerializer.startTag("", "IsCheckBlack");
            xmlSerializer.text(String.valueOf(templateSnapshotSnapshot.isCheckBlack));
            xmlSerializer.endTag("", "IsCheckBlack");
        }
        if (templateSnapshotSnapshot.blackLevel != null) {
            xmlSerializer.startTag("", "BlackLevel");
            xmlSerializer.text(String.valueOf(templateSnapshotSnapshot.blackLevel));
            xmlSerializer.endTag("", "BlackLevel");
        }
        if (templateSnapshotSnapshot.pixelBlackThreshold != null) {
            xmlSerializer.startTag("", "PixelBlackThreshold");
            xmlSerializer.text(String.valueOf(templateSnapshotSnapshot.pixelBlackThreshold));
            xmlSerializer.endTag("", "PixelBlackThreshold");
        }
        if (templateSnapshotSnapshot.snapshotOutMode != null) {
            xmlSerializer.startTag("", "SnapshotOutMode");
            xmlSerializer.text(String.valueOf(templateSnapshotSnapshot.snapshotOutMode));
            xmlSerializer.endTag("", "SnapshotOutMode");
        }
        if (templateSnapshotSnapshot.spriteSnapshotConfig != null) {
            QCloudXml.toXml(xmlSerializer, templateSnapshotSnapshot.spriteSnapshotConfig, "SpriteSnapshotConfig");
        }
        xmlSerializer.endTag("", str);
    }
}
