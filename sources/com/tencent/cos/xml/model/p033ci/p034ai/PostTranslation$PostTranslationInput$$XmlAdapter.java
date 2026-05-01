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
public class PostTranslation$PostTranslationInput$$XmlAdapter extends IXmlAdapter<PostTranslation.PostTranslationInput> {
    private HashMap<String, ChildElementBinder<PostTranslation.PostTranslationInput>> childElementBinders;

    public PostTranslation$PostTranslationInput$$XmlAdapter() {
        HashMap<String, ChildElementBinder<PostTranslation.PostTranslationInput>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Object", new ChildElementBinder<PostTranslation.PostTranslationInput>() { // from class: com.tencent.cos.xml.model.ci.ai.PostTranslation$PostTranslationInput$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostTranslation.PostTranslationInput postTranslationInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postTranslationInput.object = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Lang", new ChildElementBinder<PostTranslation.PostTranslationInput>() { // from class: com.tencent.cos.xml.model.ci.ai.PostTranslation$PostTranslationInput$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostTranslation.PostTranslationInput postTranslationInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postTranslationInput.lang = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Type", new ChildElementBinder<PostTranslation.PostTranslationInput>() { // from class: com.tencent.cos.xml.model.ci.ai.PostTranslation$PostTranslationInput$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostTranslation.PostTranslationInput postTranslationInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postTranslationInput.type = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BasicType", new ChildElementBinder<PostTranslation.PostTranslationInput>() { // from class: com.tencent.cos.xml.model.ci.ai.PostTranslation$PostTranslationInput$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostTranslation.PostTranslationInput postTranslationInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postTranslationInput.basicType = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostTranslation.PostTranslationInput fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostTranslation.PostTranslationInput postTranslationInput = new PostTranslation.PostTranslationInput();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostTranslation.PostTranslationInput> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postTranslationInput, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Input" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postTranslationInput;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postTranslationInput;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostTranslation.PostTranslationInput postTranslationInput, String str) throws XmlPullParserException, IOException {
        if (postTranslationInput == null) {
            return;
        }
        if (str == null) {
            str = "Input";
        }
        xmlSerializer.startTag("", str);
        if (postTranslationInput.object != null) {
            xmlSerializer.startTag("", "Object");
            xmlSerializer.text(String.valueOf(postTranslationInput.object));
            xmlSerializer.endTag("", "Object");
        }
        if (postTranslationInput.lang != null) {
            xmlSerializer.startTag("", "Lang");
            xmlSerializer.text(String.valueOf(postTranslationInput.lang));
            xmlSerializer.endTag("", "Lang");
        }
        if (postTranslationInput.type != null) {
            xmlSerializer.startTag("", "Type");
            xmlSerializer.text(String.valueOf(postTranslationInput.type));
            xmlSerializer.endTag("", "Type");
        }
        if (postTranslationInput.basicType != null) {
            xmlSerializer.startTag("", "BasicType");
            xmlSerializer.text(String.valueOf(postTranslationInput.basicType));
            xmlSerializer.endTag("", "BasicType");
        }
        xmlSerializer.endTag("", str);
    }
}
