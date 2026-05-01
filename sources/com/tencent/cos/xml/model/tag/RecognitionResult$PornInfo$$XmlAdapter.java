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
public class RecognitionResult$PornInfo$$XmlAdapter extends IXmlAdapter<RecognitionResult.PornInfo> {
    private HashMap<String, ChildElementBinder<RecognitionResult.PornInfo>> childElementBinders;

    public RecognitionResult$PornInfo$$XmlAdapter() {
        HashMap<String, ChildElementBinder<RecognitionResult.PornInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.ERROR_CODE, new ChildElementBinder<RecognitionResult.PornInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$PornInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.PornInfo pornInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                pornInfo.code = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Msg", new ChildElementBinder<RecognitionResult.PornInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$PornInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.PornInfo pornInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                pornInfo.msg = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("HitFlag", new ChildElementBinder<RecognitionResult.PornInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$PornInfo$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.PornInfo pornInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                pornInfo.hitFlag = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Score", new ChildElementBinder<RecognitionResult.PornInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$PornInfo$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.PornInfo pornInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                pornInfo.score = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Label", new ChildElementBinder<RecognitionResult.PornInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$PornInfo$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.PornInfo pornInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                pornInfo.label = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Category", new ChildElementBinder<RecognitionResult.PornInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$PornInfo$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.PornInfo pornInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                pornInfo.category = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SubLabel", new ChildElementBinder<RecognitionResult.PornInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$PornInfo$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.PornInfo pornInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                pornInfo.subLabel = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("OcrResults", new ChildElementBinder<RecognitionResult.PornInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$PornInfo$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.PornInfo pornInfo, String str) throws XmlPullParserException, IOException {
                if (pornInfo.ocrResults == null) {
                    pornInfo.ocrResults = new ArrayList();
                }
                pornInfo.ocrResults.add((AuditOcrResults) QCloudXml.fromXml(xmlPullParser, AuditOcrResults.class, "OcrResults"));
            }
        });
        this.childElementBinders.put("LibResults", new ChildElementBinder<RecognitionResult.PornInfo>() { // from class: com.tencent.cos.xml.model.tag.RecognitionResult$PornInfo$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RecognitionResult.PornInfo pornInfo, String str) throws XmlPullParserException, IOException {
                if (pornInfo.libResults == null) {
                    pornInfo.libResults = new ArrayList();
                }
                pornInfo.libResults.add((AuditLibResults) QCloudXml.fromXml(xmlPullParser, AuditLibResults.class, "LibResults"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public RecognitionResult.PornInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        RecognitionResult.PornInfo pornInfo = new RecognitionResult.PornInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<RecognitionResult.PornInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, pornInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "PornInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return pornInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return pornInfo;
    }
}
