package com.tencent.cos.xml.model.p033ci.p034ai.bean;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.p034ai.bean.WordsGeneralizeJobDetail;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class WordsGeneralizeJobDetail$$XmlAdapter extends IXmlAdapter<WordsGeneralizeJobDetail> {
    private HashMap<String, ChildElementBinder<WordsGeneralizeJobDetail>> childElementBinders;

    public WordsGeneralizeJobDetail$$XmlAdapter() {
        HashMap<String, ChildElementBinder<WordsGeneralizeJobDetail>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("JobId", new ChildElementBinder<WordsGeneralizeJobDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.bean.WordsGeneralizeJobDetail$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, WordsGeneralizeJobDetail wordsGeneralizeJobDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                wordsGeneralizeJobDetail.jobId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<WordsGeneralizeJobDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.bean.WordsGeneralizeJobDetail$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, WordsGeneralizeJobDetail wordsGeneralizeJobDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                wordsGeneralizeJobDetail.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreationTime", new ChildElementBinder<WordsGeneralizeJobDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.bean.WordsGeneralizeJobDetail$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, WordsGeneralizeJobDetail wordsGeneralizeJobDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                wordsGeneralizeJobDetail.creationTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("StartTime", new ChildElementBinder<WordsGeneralizeJobDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.bean.WordsGeneralizeJobDetail$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, WordsGeneralizeJobDetail wordsGeneralizeJobDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                wordsGeneralizeJobDetail.startTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("EndTime", new ChildElementBinder<WordsGeneralizeJobDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.bean.WordsGeneralizeJobDetail$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, WordsGeneralizeJobDetail wordsGeneralizeJobDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                wordsGeneralizeJobDetail.endTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.ERROR_CODE, new ChildElementBinder<WordsGeneralizeJobDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.bean.WordsGeneralizeJobDetail$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, WordsGeneralizeJobDetail wordsGeneralizeJobDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                wordsGeneralizeJobDetail.code = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.ERROR_MESSAGE, new ChildElementBinder<WordsGeneralizeJobDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.bean.WordsGeneralizeJobDetail$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, WordsGeneralizeJobDetail wordsGeneralizeJobDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                wordsGeneralizeJobDetail.message = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Tag", new ChildElementBinder<WordsGeneralizeJobDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.bean.WordsGeneralizeJobDetail$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, WordsGeneralizeJobDetail wordsGeneralizeJobDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                wordsGeneralizeJobDetail.tag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("QueueId", new ChildElementBinder<WordsGeneralizeJobDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.bean.WordsGeneralizeJobDetail$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, WordsGeneralizeJobDetail wordsGeneralizeJobDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                wordsGeneralizeJobDetail.queueId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Input", new ChildElementBinder<WordsGeneralizeJobDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.bean.WordsGeneralizeJobDetail$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, WordsGeneralizeJobDetail wordsGeneralizeJobDetail, String str) throws XmlPullParserException, IOException {
                wordsGeneralizeJobDetail.input = (WordsGeneralizeJobInput) QCloudXml.fromXml(xmlPullParser, WordsGeneralizeJobInput.class, "Input");
            }
        });
        this.childElementBinders.put("Operation", new ChildElementBinder<WordsGeneralizeJobDetail>() { // from class: com.tencent.cos.xml.model.ci.ai.bean.WordsGeneralizeJobDetail$$XmlAdapter.11
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, WordsGeneralizeJobDetail wordsGeneralizeJobDetail, String str) throws XmlPullParserException, IOException {
                wordsGeneralizeJobDetail.operation = (WordsGeneralizeJobDetail.WordsGeneralizeJobDetailOperation) QCloudXml.fromXml(xmlPullParser, WordsGeneralizeJobDetail.WordsGeneralizeJobDetailOperation.class, "Operation");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public WordsGeneralizeJobDetail fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        WordsGeneralizeJobDetail wordsGeneralizeJobDetail = new WordsGeneralizeJobDetail();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<WordsGeneralizeJobDetail> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, wordsGeneralizeJobDetail, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "JobsDetail" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return wordsGeneralizeJobDetail;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return wordsGeneralizeJobDetail;
    }
}
