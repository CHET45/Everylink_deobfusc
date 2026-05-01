package com.tencent.cos.xml.model.p033ci.p034ai.bean;

import com.tencent.cos.xml.model.p033ci.p034ai.bean.WordsGeneralizeJobDetail;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.ai.bean.WordsGeneralizeJobDetail$WordsGeneralizeJobDetailOperation$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3142x7e1df6db extends IXmlAdapter<WordsGeneralizeJobDetail.WordsGeneralizeJobDetailOperation> {
    private HashMap<String, ChildElementBinder<WordsGeneralizeJobDetail.WordsGeneralizeJobDetailOperation>> childElementBinders;

    public C3142x7e1df6db() {
        HashMap<String, ChildElementBinder<WordsGeneralizeJobDetail.WordsGeneralizeJobDetailOperation>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("WordsGeneralizeResult", new ChildElementBinder<WordsGeneralizeJobDetail.WordsGeneralizeJobDetailOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.bean.WordsGeneralizeJobDetail$WordsGeneralizeJobDetailOperation$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, WordsGeneralizeJobDetail.WordsGeneralizeJobDetailOperation wordsGeneralizeJobDetailOperation, String str) throws XmlPullParserException, IOException {
                wordsGeneralizeJobDetailOperation.wordsGeneralizeResult = (WordsGeneralizeJobDetail.WordsGeneralizeResult) QCloudXml.fromXml(xmlPullParser, WordsGeneralizeJobDetail.WordsGeneralizeResult.class, "WordsGeneralizeResult");
            }
        });
        this.childElementBinders.put("WordsGeneralize", new ChildElementBinder<WordsGeneralizeJobDetail.WordsGeneralizeJobDetailOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.bean.WordsGeneralizeJobDetail$WordsGeneralizeJobDetailOperation$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, WordsGeneralizeJobDetail.WordsGeneralizeJobDetailOperation wordsGeneralizeJobDetailOperation, String str) throws XmlPullParserException, IOException {
                wordsGeneralizeJobDetailOperation.wordsGeneralize = (WordsGeneralize) QCloudXml.fromXml(xmlPullParser, WordsGeneralize.class, "WordsGeneralize");
            }
        });
        this.childElementBinders.put("UserData", new ChildElementBinder<WordsGeneralizeJobDetail.WordsGeneralizeJobDetailOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.bean.WordsGeneralizeJobDetail$WordsGeneralizeJobDetailOperation$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, WordsGeneralizeJobDetail.WordsGeneralizeJobDetailOperation wordsGeneralizeJobDetailOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                wordsGeneralizeJobDetailOperation.userData = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobLevel", new ChildElementBinder<WordsGeneralizeJobDetail.WordsGeneralizeJobDetailOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.bean.WordsGeneralizeJobDetail$WordsGeneralizeJobDetailOperation$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, WordsGeneralizeJobDetail.WordsGeneralizeJobDetailOperation wordsGeneralizeJobDetailOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                wordsGeneralizeJobDetailOperation.jobLevel = Integer.parseInt(xmlPullParser.getText());
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public WordsGeneralizeJobDetail.WordsGeneralizeJobDetailOperation fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        WordsGeneralizeJobDetail.WordsGeneralizeJobDetailOperation wordsGeneralizeJobDetailOperation = new WordsGeneralizeJobDetail.WordsGeneralizeJobDetailOperation();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<WordsGeneralizeJobDetail.WordsGeneralizeJobDetailOperation> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, wordsGeneralizeJobDetailOperation, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Operation" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return wordsGeneralizeJobDetailOperation;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return wordsGeneralizeJobDetailOperation;
    }
}
