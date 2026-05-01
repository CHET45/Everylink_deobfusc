package com.tencent.cos.xml.model.p033ci.p034ai.bean;

import com.tencent.cos.xml.model.p033ci.p034ai.bean.WordsGeneralizeJobDetail;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class WordsGeneralizeJobDetail$WordsGeneralizeResult$$XmlAdapter extends IXmlAdapter<WordsGeneralizeJobDetail.WordsGeneralizeResult> {
    private HashMap<String, ChildElementBinder<WordsGeneralizeJobDetail.WordsGeneralizeResult>> childElementBinders;

    public WordsGeneralizeJobDetail$WordsGeneralizeResult$$XmlAdapter() {
        HashMap<String, ChildElementBinder<WordsGeneralizeJobDetail.WordsGeneralizeResult>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("WordsGeneralizeLable", new ChildElementBinder<WordsGeneralizeJobDetail.WordsGeneralizeResult>() { // from class: com.tencent.cos.xml.model.ci.ai.bean.WordsGeneralizeJobDetail$WordsGeneralizeResult$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, WordsGeneralizeJobDetail.WordsGeneralizeResult wordsGeneralizeResult, String str) throws XmlPullParserException, IOException {
                if (wordsGeneralizeResult.wordsGeneralizeLable == null) {
                    wordsGeneralizeResult.wordsGeneralizeLable = new ArrayList();
                }
                wordsGeneralizeResult.wordsGeneralizeLable.add((WordsGeneralizeJobDetail.WordsGeneralizeLable) QCloudXml.fromXml(xmlPullParser, WordsGeneralizeJobDetail.WordsGeneralizeLable.class, "WordsGeneralizeLable"));
            }
        });
        this.childElementBinders.put("WordsGeneralizeToken", new ChildElementBinder<WordsGeneralizeJobDetail.WordsGeneralizeResult>() { // from class: com.tencent.cos.xml.model.ci.ai.bean.WordsGeneralizeJobDetail$WordsGeneralizeResult$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, WordsGeneralizeJobDetail.WordsGeneralizeResult wordsGeneralizeResult, String str) throws XmlPullParserException, IOException {
                if (wordsGeneralizeResult.wordsGeneralizeToken == null) {
                    wordsGeneralizeResult.wordsGeneralizeToken = new ArrayList();
                }
                wordsGeneralizeResult.wordsGeneralizeToken.add((WordsGeneralizeJobDetail.WordsGeneralizeToken) QCloudXml.fromXml(xmlPullParser, WordsGeneralizeJobDetail.WordsGeneralizeToken.class, "WordsGeneralizeToken"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public WordsGeneralizeJobDetail.WordsGeneralizeResult fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        WordsGeneralizeJobDetail.WordsGeneralizeResult wordsGeneralizeResult = new WordsGeneralizeJobDetail.WordsGeneralizeResult();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<WordsGeneralizeJobDetail.WordsGeneralizeResult> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, wordsGeneralizeResult, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "WordsGeneralizeResult" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return wordsGeneralizeResult;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return wordsGeneralizeResult;
    }
}
