package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.PostTranslation;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostTranslation$PostTranslationTranslation$$XmlAdapter extends IXmlAdapter<PostTranslation.PostTranslationTranslation> {
    private HashMap<String, ChildElementBinder<PostTranslation.PostTranslationTranslation>> childElementBinders;

    public PostTranslation$PostTranslationTranslation$$XmlAdapter() {
        HashMap<String, ChildElementBinder<PostTranslation.PostTranslationTranslation>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Lang", new ChildElementBinder<PostTranslation.PostTranslationTranslation>() { // from class: com.tencent.cos.xml.model.ci.ai.PostTranslation$PostTranslationTranslation$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostTranslation.PostTranslationTranslation postTranslationTranslation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postTranslationTranslation.lang = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Type", new ChildElementBinder<PostTranslation.PostTranslationTranslation>() { // from class: com.tencent.cos.xml.model.ci.ai.PostTranslation$PostTranslationTranslation$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostTranslation.PostTranslationTranslation postTranslationTranslation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postTranslationTranslation.type = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostTranslation.PostTranslationTranslation fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostTranslation.PostTranslationTranslation postTranslationTranslation = new PostTranslation.PostTranslationTranslation();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostTranslation.PostTranslationTranslation> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postTranslationTranslation, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Translation" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postTranslationTranslation;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postTranslationTranslation;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostTranslation.PostTranslationTranslation postTranslationTranslation, String str) throws XmlPullParserException, IOException {
        if (postTranslationTranslation == null) {
            return;
        }
        if (str == null) {
            str = "Translation";
        }
        xmlSerializer.startTag("", str);
        if (postTranslationTranslation.lang != null) {
            xmlSerializer.startTag("", "Lang");
            xmlSerializer.text(String.valueOf(postTranslationTranslation.lang));
            xmlSerializer.endTag("", "Lang");
        }
        if (postTranslationTranslation.type != null) {
            xmlSerializer.startTag("", "Type");
            xmlSerializer.text(String.valueOf(postTranslationTranslation.type));
            xmlSerializer.endTag("", "Type");
        }
        xmlSerializer.endTag("", str);
    }
}
