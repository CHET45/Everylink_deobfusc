package com.tencent.cos.xml.model.p033ci.p034ai.bean;

import com.tencent.cos.xml.model.p033ci.p034ai.bean.WordsGeneralizeJobDetail;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class WordsGeneralizeJobDetail$WordsGeneralizeToken$$XmlAdapter extends IXmlAdapter<WordsGeneralizeJobDetail.WordsGeneralizeToken> {
    private HashMap<String, ChildElementBinder<WordsGeneralizeJobDetail.WordsGeneralizeToken>> childElementBinders;

    public WordsGeneralizeJobDetail$WordsGeneralizeToken$$XmlAdapter() {
        HashMap<String, ChildElementBinder<WordsGeneralizeJobDetail.WordsGeneralizeToken>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Word", new ChildElementBinder<WordsGeneralizeJobDetail.WordsGeneralizeToken>() { // from class: com.tencent.cos.xml.model.ci.ai.bean.WordsGeneralizeJobDetail$WordsGeneralizeToken$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, WordsGeneralizeJobDetail.WordsGeneralizeToken wordsGeneralizeToken, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                wordsGeneralizeToken.word = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Offset", new ChildElementBinder<WordsGeneralizeJobDetail.WordsGeneralizeToken>() { // from class: com.tencent.cos.xml.model.ci.ai.bean.WordsGeneralizeJobDetail$WordsGeneralizeToken$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, WordsGeneralizeJobDetail.WordsGeneralizeToken wordsGeneralizeToken, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                wordsGeneralizeToken.offset = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Length", new ChildElementBinder<WordsGeneralizeJobDetail.WordsGeneralizeToken>() { // from class: com.tencent.cos.xml.model.ci.ai.bean.WordsGeneralizeJobDetail$WordsGeneralizeToken$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, WordsGeneralizeJobDetail.WordsGeneralizeToken wordsGeneralizeToken, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                wordsGeneralizeToken.length = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Pos", new ChildElementBinder<WordsGeneralizeJobDetail.WordsGeneralizeToken>() { // from class: com.tencent.cos.xml.model.ci.ai.bean.WordsGeneralizeJobDetail$WordsGeneralizeToken$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, WordsGeneralizeJobDetail.WordsGeneralizeToken wordsGeneralizeToken, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                wordsGeneralizeToken.pos = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public WordsGeneralizeJobDetail.WordsGeneralizeToken fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        WordsGeneralizeJobDetail.WordsGeneralizeToken wordsGeneralizeToken = new WordsGeneralizeJobDetail.WordsGeneralizeToken();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<WordsGeneralizeJobDetail.WordsGeneralizeToken> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, wordsGeneralizeToken, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "WordsGeneralizeToken" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return wordsGeneralizeToken;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return wordsGeneralizeToken;
    }
}
