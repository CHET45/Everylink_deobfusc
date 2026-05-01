package com.tencent.cos.xml.model.p033ci.p034ai.bean;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class WordsGeneralizeJobInput$$XmlAdapter extends IXmlAdapter<WordsGeneralizeJobInput> {
    private HashMap<String, ChildElementBinder<WordsGeneralizeJobInput>> childElementBinders;

    public WordsGeneralizeJobInput$$XmlAdapter() {
        HashMap<String, ChildElementBinder<WordsGeneralizeJobInput>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Region", new ChildElementBinder<WordsGeneralizeJobInput>() { // from class: com.tencent.cos.xml.model.ci.ai.bean.WordsGeneralizeJobInput$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, WordsGeneralizeJobInput wordsGeneralizeJobInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                wordsGeneralizeJobInput.region = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BucketId", new ChildElementBinder<WordsGeneralizeJobInput>() { // from class: com.tencent.cos.xml.model.ci.ai.bean.WordsGeneralizeJobInput$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, WordsGeneralizeJobInput wordsGeneralizeJobInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                wordsGeneralizeJobInput.bucketId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Object", new ChildElementBinder<WordsGeneralizeJobInput>() { // from class: com.tencent.cos.xml.model.ci.ai.bean.WordsGeneralizeJobInput$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, WordsGeneralizeJobInput wordsGeneralizeJobInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                wordsGeneralizeJobInput.object = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public WordsGeneralizeJobInput fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        WordsGeneralizeJobInput wordsGeneralizeJobInput = new WordsGeneralizeJobInput();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<WordsGeneralizeJobInput> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, wordsGeneralizeJobInput, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "WordsGeneralizeJobInput" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return wordsGeneralizeJobInput;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return wordsGeneralizeJobInput;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, WordsGeneralizeJobInput wordsGeneralizeJobInput, String str) throws XmlPullParserException, IOException {
        if (wordsGeneralizeJobInput == null) {
            return;
        }
        if (str == null) {
            str = "WordsGeneralizeJobInput";
        }
        xmlSerializer.startTag("", str);
        if (wordsGeneralizeJobInput.region != null) {
            xmlSerializer.startTag("", "Region");
            xmlSerializer.text(String.valueOf(wordsGeneralizeJobInput.region));
            xmlSerializer.endTag("", "Region");
        }
        if (wordsGeneralizeJobInput.bucketId != null) {
            xmlSerializer.startTag("", "BucketId");
            xmlSerializer.text(String.valueOf(wordsGeneralizeJobInput.bucketId));
            xmlSerializer.endTag("", "BucketId");
        }
        if (wordsGeneralizeJobInput.object != null) {
            xmlSerializer.startTag("", "Object");
            xmlSerializer.text(String.valueOf(wordsGeneralizeJobInput.object));
            xmlSerializer.endTag("", "Object");
        }
        xmlSerializer.endTag("", str);
    }
}
