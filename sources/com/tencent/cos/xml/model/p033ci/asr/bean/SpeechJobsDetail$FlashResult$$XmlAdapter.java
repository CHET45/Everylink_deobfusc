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
public class SpeechJobsDetail$FlashResult$$XmlAdapter extends IXmlAdapter<SpeechJobsDetail.FlashResult> {
    private HashMap<String, ChildElementBinder<SpeechJobsDetail.FlashResult>> childElementBinders;

    public SpeechJobsDetail$FlashResult$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SpeechJobsDetail.FlashResult>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("channel_id", new ChildElementBinder<SpeechJobsDetail.FlashResult>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$FlashResult$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail.FlashResult flashResult, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                flashResult.channelId = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("text", new ChildElementBinder<SpeechJobsDetail.FlashResult>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$FlashResult$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail.FlashResult flashResult, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                flashResult.text = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("sentence_list", new ChildElementBinder<SpeechJobsDetail.FlashResult>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$FlashResult$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail.FlashResult flashResult, String str) throws XmlPullParserException, IOException {
                if (flashResult.sentenceList == null) {
                    flashResult.sentenceList = new ArrayList();
                }
                flashResult.sentenceList.add((SpeechJobsDetail.FlashSentence) QCloudXml.fromXml(xmlPullParser, SpeechJobsDetail.FlashSentence.class, "Sentence_list"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SpeechJobsDetail.FlashResult fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SpeechJobsDetail.FlashResult flashResult = new SpeechJobsDetail.FlashResult();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SpeechJobsDetail.FlashResult> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, flashResult, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "FlashResult" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return flashResult;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return flashResult;
    }
}
