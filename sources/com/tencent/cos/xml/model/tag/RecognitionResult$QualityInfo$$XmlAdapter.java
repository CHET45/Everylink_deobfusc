package com.tencent.cos.xml.model.tag;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.tag.RecognitionResult;
import com.tencent.cos.xml.model.tag.audit.bean.AuditLibResults;
import com.tencent.cos.xml.model.tag.audit.bean.AuditOcrResults;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class RecognitionResult$QualityInfo$$XmlAdapter extends IXmlAdapter<RecognitionResult.QualityInfo> {
    private HashMap<String, ChildElementBinder<RecognitionResult.QualityInfo>> childElementBinders;

    public RecognitionResult$QualityInfo$$XmlAdapter() {
        HashMap<String, ChildElementBinder<RecognitionResult.QualityInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.ERROR_CODE, new ChildElementBinder<RecognitionResult.QualityInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$QualityInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.QualityInfo qualityInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                qualityInfo.code = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Msg", new ChildElementBinder<RecognitionResult.QualityInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$QualityInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.QualityInfo qualityInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                qualityInfo.msg = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("HitFlag", new ChildElementBinder<RecognitionResult.QualityInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$QualityInfo$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.QualityInfo qualityInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                qualityInfo.hitFlag = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Score", new ChildElementBinder<RecognitionResult.QualityInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$QualityInfo$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.QualityInfo qualityInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                qualityInfo.score = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Label", new ChildElementBinder<RecognitionResult.QualityInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$QualityInfo$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.QualityInfo qualityInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                qualityInfo.label = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Category", new ChildElementBinder<RecognitionResult.QualityInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$QualityInfo$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.QualityInfo qualityInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                qualityInfo.category = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SubLabel", new ChildElementBinder<RecognitionResult.QualityInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$QualityInfo$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.QualityInfo qualityInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                qualityInfo.subLabel = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("OcrResults", new ChildElementBinder<RecognitionResult.QualityInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$QualityInfo$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.QualityInfo qualityInfo, String str) throws XmlPullParserException, IOException {
                if (qualityInfo.ocrResults == null) {
                    qualityInfo.ocrResults = new ArrayList();
                }
                qualityInfo.ocrResults.add((AuditOcrResults) QCloudXml.fromXml(xmlPullParser, AuditOcrResults.class, "OcrResults"));
            }
        });
        this.childElementBinders.put("LibResults", new ChildElementBinder<RecognitionResult.QualityInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$QualityInfo$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.QualityInfo qualityInfo, String str) throws XmlPullParserException, IOException {
                if (qualityInfo.libResults == null) {
                    qualityInfo.libResults = new ArrayList();
                }
                qualityInfo.libResults.add((AuditLibResults) QCloudXml.fromXml(xmlPullParser, AuditLibResults.class, "LibResults"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public RecognitionResult.QualityInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        RecognitionResult.QualityInfo qualityInfo = new RecognitionResult.QualityInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<RecognitionResult.QualityInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, qualityInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "QualityInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return qualityInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return qualityInfo;
    }
}
