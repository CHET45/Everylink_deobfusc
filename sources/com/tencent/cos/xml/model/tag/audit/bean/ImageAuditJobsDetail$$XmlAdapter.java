package com.tencent.cos.xml.model.tag.audit.bean;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class ImageAuditJobsDetail$$XmlAdapter extends IXmlAdapter<ImageAuditJobsDetail> {
    private HashMap<String, ChildElementBinder<ImageAuditJobsDetail>> childElementBinders;

    public ImageAuditJobsDetail$$XmlAdapter() {
        HashMap<String, ChildElementBinder<ImageAuditJobsDetail>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Score", new ChildElementBinder<ImageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditJobsDetail imageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                imageAuditJobsDetail.score = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Text", new ChildElementBinder<ImageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditJobsDetail imageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                imageAuditJobsDetail.text = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.URL_ELEMENT, new ChildElementBinder<ImageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditJobsDetail imageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                imageAuditJobsDetail.url = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Object", new ChildElementBinder<ImageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditJobsDetail imageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                imageAuditJobsDetail.object = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CompressionResult", new ChildElementBinder<ImageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditJobsDetail imageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                imageAuditJobsDetail.compressionResult = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("PornInfo", new ChildElementBinder<ImageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditJobsDetail imageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                imageAuditJobsDetail.pornInfo = (ImageAuditJobsDetail.ImagesAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, ImageAuditJobsDetail.ImagesAuditScenarioInfo.class, "PornInfo");
            }
        });
        this.childElementBinders.put("TerrorismInfo", new ChildElementBinder<ImageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditJobsDetail imageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                imageAuditJobsDetail.terrorismInfo = (ImageAuditJobsDetail.ImagesAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, ImageAuditJobsDetail.ImagesAuditScenarioInfo.class, "TerrorismInfo");
            }
        });
        this.childElementBinders.put("PoliticsInfo", new ChildElementBinder<ImageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditJobsDetail imageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                imageAuditJobsDetail.politicsInfo = (ImageAuditJobsDetail.ImagesAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, ImageAuditJobsDetail.ImagesAuditScenarioInfo.class, "PoliticsInfo");
            }
        });
        this.childElementBinders.put("AdsInfo", new ChildElementBinder<ImageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditJobsDetail imageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                imageAuditJobsDetail.adsInfo = (ImageAuditJobsDetail.ImagesAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, ImageAuditJobsDetail.ImagesAuditScenarioInfo.class, "AdsInfo");
            }
        });
        this.childElementBinders.put("QualityInfo", new ChildElementBinder<ImageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditJobsDetail imageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                imageAuditJobsDetail.qualityInfo = (ImageAuditJobsDetail.ImagesAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, ImageAuditJobsDetail.ImagesAuditScenarioInfo.class, "QualityInfo");
            }
        });
        this.childElementBinders.put(Constants.ERROR_CODE, new ChildElementBinder<ImageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail$$XmlAdapter.11
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditJobsDetail imageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                imageAuditJobsDetail.code = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.ERROR_MESSAGE, new ChildElementBinder<ImageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail$$XmlAdapter.12
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditJobsDetail imageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                imageAuditJobsDetail.message = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Result", new ChildElementBinder<ImageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail$$XmlAdapter.13
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditJobsDetail imageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                imageAuditJobsDetail.result = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Label", new ChildElementBinder<ImageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail$$XmlAdapter.14
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditJobsDetail imageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                imageAuditJobsDetail.label = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SubLabel", new ChildElementBinder<ImageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail$$XmlAdapter.15
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditJobsDetail imageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                imageAuditJobsDetail.subLabel = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Category", new ChildElementBinder<ImageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail$$XmlAdapter.16
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditJobsDetail imageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                imageAuditJobsDetail.category = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("UserInfo", new ChildElementBinder<ImageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail$$XmlAdapter.17
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditJobsDetail imageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                imageAuditJobsDetail.userInfo = (AuditUserInfo) QCloudXml.fromXml(xmlPullParser, AuditUserInfo.class, "UserInfo");
            }
        });
        this.childElementBinders.put("ListInfo", new ChildElementBinder<ImageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail$$XmlAdapter.18
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditJobsDetail imageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                imageAuditJobsDetail.listInfo = (AuditListInfo) QCloudXml.fromXml(xmlPullParser, AuditListInfo.class, "ListInfo");
            }
        });
        this.childElementBinders.put("ForbidState", new ChildElementBinder<ImageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail$$XmlAdapter.19
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditJobsDetail imageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                imageAuditJobsDetail.forbidState = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("JobId", new ChildElementBinder<ImageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail$$XmlAdapter.20
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditJobsDetail imageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                imageAuditJobsDetail.jobId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<ImageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail$$XmlAdapter.21
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditJobsDetail imageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                imageAuditJobsDetail.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreationTime", new ChildElementBinder<ImageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail$$XmlAdapter.22
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditJobsDetail imageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                imageAuditJobsDetail.creationTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("DataId", new ChildElementBinder<ImageAuditJobsDetail>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditJobsDetail$$XmlAdapter.23
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditJobsDetail imageAuditJobsDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                imageAuditJobsDetail.dataId = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public ImageAuditJobsDetail fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        ImageAuditJobsDetail imageAuditJobsDetail = new ImageAuditJobsDetail();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<ImageAuditJobsDetail> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, imageAuditJobsDetail, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "JobsDetail" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return imageAuditJobsDetail;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return imageAuditJobsDetail;
    }
}
