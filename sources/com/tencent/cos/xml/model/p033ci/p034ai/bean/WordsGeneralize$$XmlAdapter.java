package com.tencent.cos.xml.model.p033ci.p034ai.bean;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class WordsGeneralize$$XmlAdapter extends IXmlAdapter<WordsGeneralize> {
    private HashMap<String, ChildElementBinder<WordsGeneralize>> childElementBinders;

    public WordsGeneralize$$XmlAdapter() {
        HashMap<String, ChildElementBinder<WordsGeneralize>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("NerMethod", new ChildElementBinder<WordsGeneralize>() { // from class: com.tencent.cos.xml.model.ci.ai.bean.WordsGeneralize$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, WordsGeneralize wordsGeneralize, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                wordsGeneralize.nerMethod = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SegMethod", new ChildElementBinder<WordsGeneralize>() { // from class: com.tencent.cos.xml.model.ci.ai.bean.WordsGeneralize$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, WordsGeneralize wordsGeneralize, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                wordsGeneralize.segMethod = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public WordsGeneralize fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        WordsGeneralize wordsGeneralize = new WordsGeneralize();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<WordsGeneralize> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, wordsGeneralize, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "WordsGeneralize" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return wordsGeneralize;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return wordsGeneralize;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, WordsGeneralize wordsGeneralize, String str) throws XmlPullParserException, IOException {
        if (wordsGeneralize == null) {
            return;
        }
        if (str == null) {
            str = "WordsGeneralize";
        }
        xmlSerializer.startTag("", str);
        if (wordsGeneralize.nerMethod != null) {
            xmlSerializer.startTag("", "NerMethod");
            xmlSerializer.text(String.valueOf(wordsGeneralize.nerMethod));
            xmlSerializer.endTag("", "NerMethod");
        }
        if (wordsGeneralize.segMethod != null) {
            xmlSerializer.startTag("", "SegMethod");
            xmlSerializer.text(String.valueOf(wordsGeneralize.segMethod));
            xmlSerializer.endTag("", "SegMethod");
        }
        xmlSerializer.endTag("", str);
    }
}
