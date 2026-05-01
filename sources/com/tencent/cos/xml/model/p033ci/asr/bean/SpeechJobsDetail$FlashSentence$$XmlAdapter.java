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
public class SpeechJobsDetail$FlashSentence$$XmlAdapter extends IXmlAdapter<SpeechJobsDetail.FlashSentence> {
    private HashMap<String, ChildElementBinder<SpeechJobsDetail.FlashSentence>> childElementBinders;

    public SpeechJobsDetail$FlashSentence$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SpeechJobsDetail.FlashSentence>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("text", new ChildElementBinder<SpeechJobsDetail.FlashSentence>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$FlashSentence$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail.FlashSentence flashSentence, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                flashSentence.text = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("start_time", new ChildElementBinder<SpeechJobsDetail.FlashSentence>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$FlashSentence$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail.FlashSentence flashSentence, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                flashSentence.startTime = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("end_time", new ChildElementBinder<SpeechJobsDetail.FlashSentence>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$FlashSentence$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail.FlashSentence flashSentence, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                flashSentence.endTime = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("speaker_id", new ChildElementBinder<SpeechJobsDetail.FlashSentence>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$FlashSentence$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail.FlashSentence flashSentence, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                flashSentence.speakerId = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("word_list", new ChildElementBinder<SpeechJobsDetail.FlashSentence>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$FlashSentence$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail.FlashSentence flashSentence, String str) throws XmlPullParserException, IOException {
                if (flashSentence.wordList == null) {
                    flashSentence.wordList = new ArrayList();
                }
                flashSentence.wordList.add((SpeechJobsDetail.FlashSentenceWord) QCloudXml.fromXml(xmlPullParser, SpeechJobsDetail.FlashSentenceWord.class, "Word_list"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SpeechJobsDetail.FlashSentence fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SpeechJobsDetail.FlashSentence flashSentence = new SpeechJobsDetail.FlashSentence();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SpeechJobsDetail.FlashSentence> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, flashSentence, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "FlashSentence" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return flashSentence;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return flashSentence;
    }
}
