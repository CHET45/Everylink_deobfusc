package com.tencent.cos.xml.model.p033ci.asr.bean;

import com.tencent.cos.xml.model.p033ci.asr.bean.SpeechJobsDetail;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class SpeechJobsDetail$FlashSentenceWord$$XmlAdapter extends IXmlAdapter<SpeechJobsDetail.FlashSentenceWord> {
    private HashMap<String, ChildElementBinder<SpeechJobsDetail.FlashSentenceWord>> childElementBinders;

    public SpeechJobsDetail$FlashSentenceWord$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SpeechJobsDetail.FlashSentenceWord>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("word", new ChildElementBinder<SpeechJobsDetail.FlashSentenceWord>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$FlashSentenceWord$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail.FlashSentenceWord flashSentenceWord, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                flashSentenceWord.word = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("start_time", new ChildElementBinder<SpeechJobsDetail.FlashSentenceWord>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$FlashSentenceWord$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail.FlashSentenceWord flashSentenceWord, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                flashSentenceWord.startTime = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("end_time", new ChildElementBinder<SpeechJobsDetail.FlashSentenceWord>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$FlashSentenceWord$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail.FlashSentenceWord flashSentenceWord, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                flashSentenceWord.endTime = Integer.parseInt(xmlPullParser.getText());
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SpeechJobsDetail.FlashSentenceWord fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SpeechJobsDetail.FlashSentenceWord flashSentenceWord = new SpeechJobsDetail.FlashSentenceWord();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SpeechJobsDetail.FlashSentenceWord> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, flashSentenceWord, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "FlashSentenceWord" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return flashSentenceWord;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return flashSentenceWord;
    }
}
