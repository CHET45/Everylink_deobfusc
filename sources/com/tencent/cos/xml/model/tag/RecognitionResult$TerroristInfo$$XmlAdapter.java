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
public class RecognitionResult$TerroristInfo$$XmlAdapter extends IXmlAdapter<RecognitionResult.TerroristInfo> {
    private HashMap<String, ChildElementBinder<RecognitionResult.TerroristInfo>> childElementBinders;

    public RecognitionResult$TerroristInfo$$XmlAdapter() {
        HashMap<String, ChildElementBinder<RecognitionResult.TerroristInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.ERROR_CODE, new ChildElementBinder<RecognitionResult.TerroristInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$TerroristInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.TerroristInfo terroristInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                terroristInfo.code = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Msg", new ChildElementBinder<RecognitionResult.TerroristInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$TerroristInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.TerroristInfo terroristInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                terroristInfo.msg = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("HitFlag", new ChildElementBinder<RecognitionResult.TerroristInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$TerroristInfo$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.TerroristInfo terroristInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                terroristInfo.hitFlag = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Score", new ChildElementBinder<RecognitionResult.TerroristInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$TerroristInfo$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.TerroristInfo terroristInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                terroristInfo.score = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Label", new ChildElementBinder<RecognitionResult.TerroristInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$TerroristInfo$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.TerroristInfo terroristInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                terroristInfo.label = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Category", new ChildElementBinder<RecognitionResult.TerroristInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$TerroristInfo$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.TerroristInfo terroristInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                terroristInfo.category = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SubLabel", new ChildElementBinder<RecognitionResult.TerroristInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$TerroristInfo$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.TerroristInfo terroristInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                terroristInfo.subLabel = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("OcrResults", new ChildElementBinder<RecognitionResult.TerroristInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$TerroristInfo$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.TerroristInfo terroristInfo, String str) throws XmlPullParserException, IOException {
                if (terroristInfo.ocrResults == null) {
                    terroristInfo.ocrResults = new ArrayList();
                }
                terroristInfo.ocrResults.add((AuditOcrResults) QCloudXml.fromXml(xmlPullParser, AuditOcrResults.class, "OcrResults"));
            }
        });
        this.childElementBinders.put("LibResults", new ChildElementBinder<RecognitionResult.TerroristInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$TerroristInfo$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.TerroristInfo terroristInfo, String str) throws XmlPullParserException, IOException {
                if (terroristInfo.libResults == null) {
                    terroristInfo.libResults = new ArrayList();
                }
                terroristInfo.libResults.add((AuditLibResults) QCloudXml.fromXml(xmlPullParser, AuditLibResults.class, "LibResults"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public RecognitionResult.TerroristInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        RecognitionResult.TerroristInfo terroristInfo = new RecognitionResult.TerroristInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<RecognitionResult.TerroristInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, terroristInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "TerroristInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return terroristInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return terroristInfo;
    }
}
