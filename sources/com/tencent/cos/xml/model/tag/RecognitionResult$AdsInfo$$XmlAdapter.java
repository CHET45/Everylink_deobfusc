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
public class RecognitionResult$AdsInfo$$XmlAdapter extends IXmlAdapter<RecognitionResult.AdsInfo> {
    private HashMap<String, ChildElementBinder<RecognitionResult.AdsInfo>> childElementBinders;

    public RecognitionResult$AdsInfo$$XmlAdapter() {
        HashMap<String, ChildElementBinder<RecognitionResult.AdsInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.ERROR_CODE, new ChildElementBinder<RecognitionResult.AdsInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$AdsInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.AdsInfo adsInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                adsInfo.code = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Msg", new ChildElementBinder<RecognitionResult.AdsInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$AdsInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.AdsInfo adsInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                adsInfo.msg = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("HitFlag", new ChildElementBinder<RecognitionResult.AdsInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$AdsInfo$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.AdsInfo adsInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                adsInfo.hitFlag = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Score", new ChildElementBinder<RecognitionResult.AdsInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$AdsInfo$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.AdsInfo adsInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                adsInfo.score = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Label", new ChildElementBinder<RecognitionResult.AdsInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$AdsInfo$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.AdsInfo adsInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                adsInfo.label = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Category", new ChildElementBinder<RecognitionResult.AdsInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$AdsInfo$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.AdsInfo adsInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                adsInfo.category = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SubLabel", new ChildElementBinder<RecognitionResult.AdsInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$AdsInfo$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.AdsInfo adsInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                adsInfo.subLabel = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("OcrResults", new ChildElementBinder<RecognitionResult.AdsInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$AdsInfo$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.AdsInfo adsInfo, String str) throws XmlPullParserException, IOException {
                if (adsInfo.ocrResults == null) {
                    adsInfo.ocrResults = new ArrayList();
                }
                adsInfo.ocrResults.add((AuditOcrResults) QCloudXml.fromXml(xmlPullParser, AuditOcrResults.class, "OcrResults"));
            }
        });
        this.childElementBinders.put("LibResults", new ChildElementBinder<RecognitionResult.AdsInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$AdsInfo$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.AdsInfo adsInfo, String str) throws XmlPullParserException, IOException {
                if (adsInfo.libResults == null) {
                    adsInfo.libResults = new ArrayList();
                }
                adsInfo.libResults.add((AuditLibResults) QCloudXml.fromXml(xmlPullParser, AuditLibResults.class, "LibResults"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public RecognitionResult.AdsInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        RecognitionResult.AdsInfo adsInfo = new RecognitionResult.AdsInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<RecognitionResult.AdsInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, adsInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "AdsInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return adsInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return adsInfo;
    }
}
