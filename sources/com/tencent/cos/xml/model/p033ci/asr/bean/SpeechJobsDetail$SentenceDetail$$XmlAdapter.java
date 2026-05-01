package com.tencent.cos.xml.model.p033ci.asr.bean;

import com.tencent.cos.xml.model.p033ci.asr.bean.SpeechJobsDetail;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class SpeechJobsDetail$SentenceDetail$$XmlAdapter extends IXmlAdapter<SpeechJobsDetail.SentenceDetail> {
    private HashMap<String, ChildElementBinder<SpeechJobsDetail.SentenceDetail>> childElementBinders;

    public SpeechJobsDetail$SentenceDetail$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SpeechJobsDetail.SentenceDetail>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("StartMs", new ChildElementBinder<SpeechJobsDetail.SentenceDetail>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$SentenceDetail$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail.SentenceDetail sentenceDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                sentenceDetail.startMs = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("EndMs", new ChildElementBinder<SpeechJobsDetail.SentenceDetail>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$SentenceDetail$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail.SentenceDetail sentenceDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                sentenceDetail.endMs = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("FinalSentence", new ChildElementBinder<SpeechJobsDetail.SentenceDetail>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$SentenceDetail$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail.SentenceDetail sentenceDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                sentenceDetail.finalSentence = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SliceSentence", new ChildElementBinder<SpeechJobsDetail.SentenceDetail>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$SentenceDetail$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail.SentenceDetail sentenceDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                sentenceDetail.sliceSentence = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SpeakerId", new ChildElementBinder<SpeechJobsDetail.SentenceDetail>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$SentenceDetail$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail.SentenceDetail sentenceDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                sentenceDetail.speakerId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SpeechSpeed", new ChildElementBinder<SpeechJobsDetail.SentenceDetail>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$SentenceDetail$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail.SentenceDetail sentenceDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                sentenceDetail.speechSpeed = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("WordsNum", new ChildElementBinder<SpeechJobsDetail.SentenceDetail>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$SentenceDetail$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail.SentenceDetail sentenceDetail, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                sentenceDetail.wordsNum = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Words", new ChildElementBinder<SpeechJobsDetail.SentenceDetail>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$SentenceDetail$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail.SentenceDetail sentenceDetail, String str) throws XmlPullParserException, IOException {
                if (sentenceDetail.words == null) {
                    sentenceDetail.words = new ArrayList();
                }
                sentenceDetail.words.add((SpeechJobsDetail.SentenceWords) QCloudXml.fromXml(xmlPullParser, SpeechJobsDetail.SentenceWords.class, "Words"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SpeechJobsDetail.SentenceDetail fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SpeechJobsDetail.SentenceDetail sentenceDetail = new SpeechJobsDetail.SentenceDetail();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SpeechJobsDetail.SentenceDetail> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, sentenceDetail, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "SentenceDetail" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return sentenceDetail;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return sentenceDetail;
    }
}
