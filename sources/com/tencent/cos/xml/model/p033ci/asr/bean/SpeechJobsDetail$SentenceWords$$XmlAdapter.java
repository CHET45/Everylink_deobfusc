package com.tencent.cos.xml.model.p033ci.asr.bean;

import com.tencent.cos.xml.model.p033ci.asr.bean.SpeechJobsDetail;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class SpeechJobsDetail$SentenceWords$$XmlAdapter extends IXmlAdapter<SpeechJobsDetail.SentenceWords> {
    private HashMap<String, ChildElementBinder<SpeechJobsDetail.SentenceWords>> childElementBinders;

    public SpeechJobsDetail$SentenceWords$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SpeechJobsDetail.SentenceWords>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("OffsetStartMs", new ChildElementBinder<SpeechJobsDetail.SentenceWords>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$SentenceWords$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail.SentenceWords sentenceWords, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                sentenceWords.offsetStartMs = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("OffsetEndMs", new ChildElementBinder<SpeechJobsDetail.SentenceWords>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$SentenceWords$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail.SentenceWords sentenceWords, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                sentenceWords.offsetEndMs = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("VoiceType", new ChildElementBinder<SpeechJobsDetail.SentenceWords>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$SentenceWords$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail.SentenceWords sentenceWords, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                sentenceWords.voiceType = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Word", new ChildElementBinder<SpeechJobsDetail.SentenceWords>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$SentenceWords$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail.SentenceWords sentenceWords, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                sentenceWords.word = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SpeechJobsDetail.SentenceWords fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SpeechJobsDetail.SentenceWords sentenceWords = new SpeechJobsDetail.SentenceWords();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SpeechJobsDetail.SentenceWords> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, sentenceWords, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "SentenceWords" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return sentenceWords;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return sentenceWords;
    }
}
