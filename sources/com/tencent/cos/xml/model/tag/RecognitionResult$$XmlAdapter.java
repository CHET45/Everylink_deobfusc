package com.tencent.cos.xml.model.tag;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.tag.RecognitionResult;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class RecognitionResult$$XmlAdapter extends IXmlAdapter<RecognitionResult> {
    private HashMap<String, ChildElementBinder<RecognitionResult>> childElementBinders;

    public RecognitionResult$$XmlAdapter() {
        HashMap<String, ChildElementBinder<RecognitionResult>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("DataId", new ChildElementBinder<RecognitionResult>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult recognitionResult, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                recognitionResult.dataId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobId", new ChildElementBinder<RecognitionResult>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult recognitionResult, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                recognitionResult.jobId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<RecognitionResult>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult recognitionResult, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                recognitionResult.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Object", new ChildElementBinder<RecognitionResult>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult recognitionResult, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                recognitionResult.object = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.URL_ELEMENT, new ChildElementBinder<RecognitionResult>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult recognitionResult, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                recognitionResult.url = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Result", new ChildElementBinder<RecognitionResult>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult recognitionResult, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                recognitionResult.result = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Label", new ChildElementBinder<RecognitionResult>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult recognitionResult, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                recognitionResult.label = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Category", new ChildElementBinder<RecognitionResult>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult recognitionResult, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                recognitionResult.category = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SubLabel", new ChildElementBinder<RecognitionResult>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult recognitionResult, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                recognitionResult.subLabel = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Score", new ChildElementBinder<RecognitionResult>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult recognitionResult, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                recognitionResult.score = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Text", new ChildElementBinder<RecognitionResult>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$$XmlAdapter.11
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult recognitionResult, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                recognitionResult.text = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CompressionResult", new ChildElementBinder<RecognitionResult>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$$XmlAdapter.12
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult recognitionResult, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                recognitionResult.compressionResult = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("PornInfo", new ChildElementBinder<RecognitionResult>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$$XmlAdapter.13
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult recognitionResult, String str) throws XmlPullParserException, IOException {
                recognitionResult.pornInfo = (RecognitionResult.PornInfo) QCloudXml.fromXml(xmlPullParser, RecognitionResult.PornInfo.class, "PornInfo");
            }
        });
        this.childElementBinders.put("PoliticsInfo", new ChildElementBinder<RecognitionResult>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$$XmlAdapter.14
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult recognitionResult, String str) throws XmlPullParserException, IOException {
                recognitionResult.politicsInfo = (RecognitionResult.PoliticsInfo) QCloudXml.fromXml(xmlPullParser, RecognitionResult.PoliticsInfo.class, "PoliticsInfo");
            }
        });
        this.childElementBinders.put("TerroristInfo", new ChildElementBinder<RecognitionResult>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$$XmlAdapter.15
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult recognitionResult, String str) throws XmlPullParserException, IOException {
                recognitionResult.terroristInfo = (RecognitionResult.TerroristInfo) QCloudXml.fromXml(xmlPullParser, RecognitionResult.TerroristInfo.class, "TerroristInfo");
            }
        });
        this.childElementBinders.put("AdsInfo", new ChildElementBinder<RecognitionResult>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$$XmlAdapter.16
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult recognitionResult, String str) throws XmlPullParserException, IOException {
                recognitionResult.adsInfo = (RecognitionResult.AdsInfo) QCloudXml.fromXml(xmlPullParser, RecognitionResult.AdsInfo.class, "AdsInfo");
            }
        });
        this.childElementBinders.put("QualityInfo", new ChildElementBinder<RecognitionResult>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$$XmlAdapter.17
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult recognitionResult, String str) throws XmlPullParserException, IOException {
                recognitionResult.qualityInfo = (RecognitionResult.QualityInfo) QCloudXml.fromXml(xmlPullParser, RecognitionResult.QualityInfo.class, "QualityInfo");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public RecognitionResult fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        RecognitionResult recognitionResult = new RecognitionResult();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<RecognitionResult> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, recognitionResult, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "RecognitionResult" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return recognitionResult;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return recognitionResult;
    }
}
