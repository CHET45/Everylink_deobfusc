package com.tencent.cos.xml.model.tag.audit.bean;

import com.tencent.cos.xml.model.tag.audit.bean.ImageAuditScenarioInfo;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class ImageAuditScenarioInfo$$XmlAdapter extends IXmlAdapter<ImageAuditScenarioInfo> {
    private HashMap<String, ChildElementBinder<ImageAuditScenarioInfo>> childElementBinders;

    public ImageAuditScenarioInfo$$XmlAdapter() {
        HashMap<String, ChildElementBinder<ImageAuditScenarioInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("HitFlag", new ChildElementBinder<ImageAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditScenarioInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditScenarioInfo imageAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                imageAuditScenarioInfo.hitFlag = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Score", new ChildElementBinder<ImageAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditScenarioInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditScenarioInfo imageAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                imageAuditScenarioInfo.score = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("SubLabel", new ChildElementBinder<ImageAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditScenarioInfo$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditScenarioInfo imageAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                imageAuditScenarioInfo.subLabel = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Category", new ChildElementBinder<ImageAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditScenarioInfo$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditScenarioInfo imageAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                imageAuditScenarioInfo.category = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("OcrResults", new ChildElementBinder<ImageAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditScenarioInfo$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditScenarioInfo imageAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                if (imageAuditScenarioInfo.ocrResults == null) {
                    imageAuditScenarioInfo.ocrResults = new ArrayList();
                }
                imageAuditScenarioInfo.ocrResults.add((AuditOcrResults) QCloudXml.fromXml(xmlPullParser, AuditOcrResults.class, "OcrResults"));
            }
        });
        this.childElementBinders.put("ObjectResults", new ChildElementBinder<ImageAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditScenarioInfo$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditScenarioInfo imageAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                if (imageAuditScenarioInfo.objectResults == null) {
                    imageAuditScenarioInfo.objectResults = new ArrayList();
                }
                imageAuditScenarioInfo.objectResults.add((ImageAuditScenarioInfo.ObjectResults) QCloudXml.fromXml(xmlPullParser, ImageAuditScenarioInfo.ObjectResults.class, "ObjectResults"));
            }
        });
        this.childElementBinders.put("LibResults", new ChildElementBinder<ImageAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditScenarioInfo$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditScenarioInfo imageAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                if (imageAuditScenarioInfo.libResults == null) {
                    imageAuditScenarioInfo.libResults = new ArrayList();
                }
                imageAuditScenarioInfo.libResults.add((ImageAuditScenarioInfo.LibResults) QCloudXml.fromXml(xmlPullParser, ImageAuditScenarioInfo.LibResults.class, "LibResults"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public ImageAuditScenarioInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        ImageAuditScenarioInfo imageAuditScenarioInfo = new ImageAuditScenarioInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<ImageAuditScenarioInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, imageAuditScenarioInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "ImageAuditScenarioInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return imageAuditScenarioInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return imageAuditScenarioInfo;
    }
}
