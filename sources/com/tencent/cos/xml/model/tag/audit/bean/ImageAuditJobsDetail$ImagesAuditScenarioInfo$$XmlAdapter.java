package com.tencent.cos.xml.model.tag.audit.bean;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail;
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
public class ImageAuditJobsDetail$ImagesAuditScenarioInfo$$XmlAdapter extends IXmlAdapter<ImageAuditJobsDetail.ImagesAuditScenarioInfo> {
    private HashMap<String, ChildElementBinder<ImageAuditJobsDetail.ImagesAuditScenarioInfo>> childElementBinders;

    public ImageAuditJobsDetail$ImagesAuditScenarioInfo$$XmlAdapter() {
        HashMap<String, ChildElementBinder<ImageAuditJobsDetail.ImagesAuditScenarioInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.ERROR_CODE, new ChildElementBinder<ImageAuditJobsDetail.ImagesAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail$ImagesAuditScenarioInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditJobsDetail.ImagesAuditScenarioInfo imagesAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                imagesAuditScenarioInfo.code = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Msg", new ChildElementBinder<ImageAuditJobsDetail.ImagesAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail$ImagesAuditScenarioInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditJobsDetail.ImagesAuditScenarioInfo imagesAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                imagesAuditScenarioInfo.msg = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Label", new ChildElementBinder<ImageAuditJobsDetail.ImagesAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail$ImagesAuditScenarioInfo$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditJobsDetail.ImagesAuditScenarioInfo imagesAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                imagesAuditScenarioInfo.label = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("HitFlag", new ChildElementBinder<ImageAuditJobsDetail.ImagesAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail$ImagesAuditScenarioInfo$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditJobsDetail.ImagesAuditScenarioInfo imagesAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                imagesAuditScenarioInfo.hitFlag = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Score", new ChildElementBinder<ImageAuditJobsDetail.ImagesAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail$ImagesAuditScenarioInfo$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditJobsDetail.ImagesAuditScenarioInfo imagesAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                imagesAuditScenarioInfo.score = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("SubLabel", new ChildElementBinder<ImageAuditJobsDetail.ImagesAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail$ImagesAuditScenarioInfo$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditJobsDetail.ImagesAuditScenarioInfo imagesAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                imagesAuditScenarioInfo.subLabel = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Category", new ChildElementBinder<ImageAuditJobsDetail.ImagesAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail$ImagesAuditScenarioInfo$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditJobsDetail.ImagesAuditScenarioInfo imagesAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                imagesAuditScenarioInfo.category = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("OcrResults", new ChildElementBinder<ImageAuditJobsDetail.ImagesAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail$ImagesAuditScenarioInfo$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditJobsDetail.ImagesAuditScenarioInfo imagesAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                if (imagesAuditScenarioInfo.ocrResults == null) {
                    imagesAuditScenarioInfo.ocrResults = new ArrayList();
                }
                imagesAuditScenarioInfo.ocrResults.add((AuditOcrResults) QCloudXml.fromXml(xmlPullParser, AuditOcrResults.class, "OcrResults"));
            }
        });
        this.childElementBinders.put("ObjectResults", new ChildElementBinder<ImageAuditJobsDetail.ImagesAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail$ImagesAuditScenarioInfo$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditJobsDetail.ImagesAuditScenarioInfo imagesAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                if (imagesAuditScenarioInfo.objectResults == null) {
                    imagesAuditScenarioInfo.objectResults = new ArrayList();
                }
                imagesAuditScenarioInfo.objectResults.add((ImageAuditScenarioInfo.ObjectResults) QCloudXml.fromXml(xmlPullParser, ImageAuditScenarioInfo.ObjectResults.class, "ObjectResults"));
            }
        });
        this.childElementBinders.put("LibResults", new ChildElementBinder<ImageAuditJobsDetail.ImagesAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail$ImagesAuditScenarioInfo$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditJobsDetail.ImagesAuditScenarioInfo imagesAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                if (imagesAuditScenarioInfo.libResults == null) {
                    imagesAuditScenarioInfo.libResults = new ArrayList();
                }
                imagesAuditScenarioInfo.libResults.add((ImageAuditScenarioInfo.LibResults) QCloudXml.fromXml(xmlPullParser, ImageAuditScenarioInfo.LibResults.class, "LibResults"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public ImageAuditJobsDetail.ImagesAuditScenarioInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        ImageAuditJobsDetail.ImagesAuditScenarioInfo imagesAuditScenarioInfo = new ImageAuditJobsDetail.ImagesAuditScenarioInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<ImageAuditJobsDetail.ImagesAuditScenarioInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, imagesAuditScenarioInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "ImagesAuditScenarioInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return imagesAuditScenarioInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return imagesAuditScenarioInfo;
    }
}
