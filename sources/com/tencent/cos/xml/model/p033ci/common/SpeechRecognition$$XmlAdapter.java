package com.tencent.cos.xml.model.p033ci.common;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SpeechRecognition$$XmlAdapter extends IXmlAdapter<SpeechRecognition> {
    private HashMap<String, ChildElementBinder<SpeechRecognition>> childElementBinders;

    public SpeechRecognition$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SpeechRecognition>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("FlashAsr", new ChildElementBinder<SpeechRecognition>() { // from class: com.tencent.cos.xml.model.ci.common.SpeechRecognition$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechRecognition speechRecognition, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechRecognition.flashAsr = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("EngineModelType", new ChildElementBinder<SpeechRecognition>() { // from class: com.tencent.cos.xml.model.ci.common.SpeechRecognition$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechRecognition speechRecognition, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechRecognition.engineModelType = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("ChannelNum", new ChildElementBinder<SpeechRecognition>() { // from class: com.tencent.cos.xml.model.ci.common.SpeechRecognition$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechRecognition speechRecognition, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechRecognition.channelNum = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("ResTextFormat", new ChildElementBinder<SpeechRecognition>() { // from class: com.tencent.cos.xml.model.ci.common.SpeechRecognition$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechRecognition speechRecognition, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechRecognition.resTextFormat = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("FilterDirty", new ChildElementBinder<SpeechRecognition>() { // from class: com.tencent.cos.xml.model.ci.common.SpeechRecognition$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechRecognition speechRecognition, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechRecognition.filterDirty = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("FilterModal", new ChildElementBinder<SpeechRecognition>() { // from class: com.tencent.cos.xml.model.ci.common.SpeechRecognition$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechRecognition speechRecognition, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechRecognition.filterModal = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("ConvertNumMode", new ChildElementBinder<SpeechRecognition>() { // from class: com.tencent.cos.xml.model.ci.common.SpeechRecognition$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechRecognition speechRecognition, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechRecognition.convertNumMode = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SpeakerDiarization", new ChildElementBinder<SpeechRecognition>() { // from class: com.tencent.cos.xml.model.ci.common.SpeechRecognition$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechRecognition speechRecognition, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechRecognition.speakerDiarization = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SpeakerNumber", new ChildElementBinder<SpeechRecognition>() { // from class: com.tencent.cos.xml.model.ci.common.SpeechRecognition$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechRecognition speechRecognition, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechRecognition.speakerNumber = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("FilterPunc", new ChildElementBinder<SpeechRecognition>() { // from class: com.tencent.cos.xml.model.ci.common.SpeechRecognition$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechRecognition speechRecognition, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechRecognition.filterPunc = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("OutputFileType", new ChildElementBinder<SpeechRecognition>() { // from class: com.tencent.cos.xml.model.ci.common.SpeechRecognition$$XmlAdapter.11
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechRecognition speechRecognition, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechRecognition.outputFileType = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Format", new ChildElementBinder<SpeechRecognition>() { // from class: com.tencent.cos.xml.model.ci.common.SpeechRecognition$$XmlAdapter.12
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechRecognition speechRecognition, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechRecognition.format = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("FirstChannelOnly", new ChildElementBinder<SpeechRecognition>() { // from class: com.tencent.cos.xml.model.ci.common.SpeechRecognition$$XmlAdapter.13
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechRecognition speechRecognition, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechRecognition.firstChannelOnly = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("WordInfo", new ChildElementBinder<SpeechRecognition>() { // from class: com.tencent.cos.xml.model.ci.common.SpeechRecognition$$XmlAdapter.14
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechRecognition speechRecognition, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechRecognition.wordInfo = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SentenceMaxLength", new ChildElementBinder<SpeechRecognition>() { // from class: com.tencent.cos.xml.model.ci.common.SpeechRecognition$$XmlAdapter.15
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechRecognition speechRecognition, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechRecognition.sentenceMaxLength = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SpeechRecognition fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SpeechRecognition speechRecognition = new SpeechRecognition();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SpeechRecognition> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, speechRecognition, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "SpeechRecognition" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return speechRecognition;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return speechRecognition;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SpeechRecognition speechRecognition, String str) throws XmlPullParserException, IOException {
        if (speechRecognition == null) {
            return;
        }
        if (str == null) {
            str = "SpeechRecognition";
        }
        xmlSerializer.startTag("", str);
        if (speechRecognition.flashAsr != null) {
            xmlSerializer.startTag("", "FlashAsr");
            xmlSerializer.text(String.valueOf(speechRecognition.flashAsr));
            xmlSerializer.endTag("", "FlashAsr");
        }
        if (speechRecognition.engineModelType != null) {
            xmlSerializer.startTag("", "EngineModelType");
            xmlSerializer.text(String.valueOf(speechRecognition.engineModelType));
            xmlSerializer.endTag("", "EngineModelType");
        }
        if (speechRecognition.channelNum != null) {
            xmlSerializer.startTag("", "ChannelNum");
            xmlSerializer.text(String.valueOf(speechRecognition.channelNum));
            xmlSerializer.endTag("", "ChannelNum");
        }
        if (speechRecognition.resTextFormat != null) {
            xmlSerializer.startTag("", "ResTextFormat");
            xmlSerializer.text(String.valueOf(speechRecognition.resTextFormat));
            xmlSerializer.endTag("", "ResTextFormat");
        }
        if (speechRecognition.filterDirty != null) {
            xmlSerializer.startTag("", "FilterDirty");
            xmlSerializer.text(String.valueOf(speechRecognition.filterDirty));
            xmlSerializer.endTag("", "FilterDirty");
        }
        if (speechRecognition.filterModal != null) {
            xmlSerializer.startTag("", "FilterModal");
            xmlSerializer.text(String.valueOf(speechRecognition.filterModal));
            xmlSerializer.endTag("", "FilterModal");
        }
        if (speechRecognition.convertNumMode != null) {
            xmlSerializer.startTag("", "ConvertNumMode");
            xmlSerializer.text(String.valueOf(speechRecognition.convertNumMode));
            xmlSerializer.endTag("", "ConvertNumMode");
        }
        if (speechRecognition.speakerDiarization != null) {
            xmlSerializer.startTag("", "SpeakerDiarization");
            xmlSerializer.text(String.valueOf(speechRecognition.speakerDiarization));
            xmlSerializer.endTag("", "SpeakerDiarization");
        }
        if (speechRecognition.speakerNumber != null) {
            xmlSerializer.startTag("", "SpeakerNumber");
            xmlSerializer.text(String.valueOf(speechRecognition.speakerNumber));
            xmlSerializer.endTag("", "SpeakerNumber");
        }
        if (speechRecognition.filterPunc != null) {
            xmlSerializer.startTag("", "FilterPunc");
            xmlSerializer.text(String.valueOf(speechRecognition.filterPunc));
            xmlSerializer.endTag("", "FilterPunc");
        }
        if (speechRecognition.outputFileType != null) {
            xmlSerializer.startTag("", "OutputFileType");
            xmlSerializer.text(String.valueOf(speechRecognition.outputFileType));
            xmlSerializer.endTag("", "OutputFileType");
        }
        if (speechRecognition.format != null) {
            xmlSerializer.startTag("", "Format");
            xmlSerializer.text(String.valueOf(speechRecognition.format));
            xmlSerializer.endTag("", "Format");
        }
        if (speechRecognition.firstChannelOnly != null) {
            xmlSerializer.startTag("", "FirstChannelOnly");
            xmlSerializer.text(String.valueOf(speechRecognition.firstChannelOnly));
            xmlSerializer.endTag("", "FirstChannelOnly");
        }
        if (speechRecognition.wordInfo != null) {
            xmlSerializer.startTag("", "WordInfo");
            xmlSerializer.text(String.valueOf(speechRecognition.wordInfo));
            xmlSerializer.endTag("", "WordInfo");
        }
        if (speechRecognition.sentenceMaxLength != null) {
            xmlSerializer.startTag("", "SentenceMaxLength");
            xmlSerializer.text(String.valueOf(speechRecognition.sentenceMaxLength));
            xmlSerializer.endTag("", "SentenceMaxLength");
        }
        xmlSerializer.endTag("", str);
    }
}
