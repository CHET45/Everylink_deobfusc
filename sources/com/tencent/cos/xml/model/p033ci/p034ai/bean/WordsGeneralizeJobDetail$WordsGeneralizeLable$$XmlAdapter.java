package com.tencent.cos.xml.model.p033ci.p034ai.bean;

import com.tencent.cos.xml.model.p033ci.p034ai.bean.WordsGeneralizeJobDetail;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class WordsGeneralizeJobDetail$WordsGeneralizeLable$$XmlAdapter extends IXmlAdapter<WordsGeneralizeJobDetail.WordsGeneralizeLable> {
    private HashMap<String, ChildElementBinder<WordsGeneralizeJobDetail.WordsGeneralizeLable>> childElementBinders;

    public WordsGeneralizeJobDetail$WordsGeneralizeLable$$XmlAdapter() {
        HashMap<String, ChildElementBinder<WordsGeneralizeJobDetail.WordsGeneralizeLable>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Category", new ChildElementBinder<WordsGeneralizeJobDetail.WordsGeneralizeLable>() { // from class: com.tencent.cos.xml.model.ci.ai.bean.WordsGeneralizeJobDetail$WordsGeneralizeLable$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, WordsGeneralizeJobDetail.WordsGeneralizeLable wordsGeneralizeLable, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                wordsGeneralizeLable.category = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Word", new ChildElementBinder<WordsGeneralizeJobDetail.WordsGeneralizeLable>() { // from class: com.tencent.cos.xml.model.ci.ai.bean.WordsGeneralizeJobDetail$WordsGeneralizeLable$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, WordsGeneralizeJobDetail.WordsGeneralizeLable wordsGeneralizeLable, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                wordsGeneralizeLable.word = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public WordsGeneralizeJobDetail.WordsGeneralizeLable fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        WordsGeneralizeJobDetail.WordsGeneralizeLable wordsGeneralizeLable = new WordsGeneralizeJobDetail.WordsGeneralizeLable();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<WordsGeneralizeJobDetail.WordsGeneralizeLable> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, wordsGeneralizeLable, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "WordsGeneralizeLable" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return wordsGeneralizeLable;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return wordsGeneralizeLable;
    }
}
