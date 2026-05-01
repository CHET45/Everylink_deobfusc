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
public class PostTranslation$PostTranslationOutput$$XmlAdapter extends IXmlAdapter<PostTranslation.PostTranslationOutput> {
    private HashMap<String, ChildElementBinder<PostTranslation.PostTranslationOutput>> childElementBinders;

    public PostTranslation$PostTranslationOutput$$XmlAdapter() {
        HashMap<String, ChildElementBinder<PostTranslation.PostTranslationOutput>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Region", new ChildElementBinder<PostTranslation.PostTranslationOutput>() { // from class: com.tencent.cos.xml.model.ci.ai.PostTranslation$PostTranslationOutput$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostTranslation.PostTranslationOutput postTranslationOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postTranslationOutput.region = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Bucket", new ChildElementBinder<PostTranslation.PostTranslationOutput>() { // from class: com.tencent.cos.xml.model.ci.ai.PostTranslation$PostTranslationOutput$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostTranslation.PostTranslationOutput postTranslationOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postTranslationOutput.bucket = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Object", new ChildElementBinder<PostTranslation.PostTranslationOutput>() { // from class: com.tencent.cos.xml.model.ci.ai.PostTranslation$PostTranslationOutput$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostTranslation.PostTranslationOutput postTranslationOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postTranslationOutput.object = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostTranslation.PostTranslationOutput fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostTranslation.PostTranslationOutput postTranslationOutput = new PostTranslation.PostTranslationOutput();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostTranslation.PostTranslationOutput> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postTranslationOutput, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Output" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postTranslationOutput;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postTranslationOutput;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostTranslation.PostTranslationOutput postTranslationOutput, String str) throws XmlPullParserException, IOException {
        if (postTranslationOutput == null) {
            return;
        }
        if (str == null) {
            str = "Output";
        }
        xmlSerializer.startTag("", str);
        if (postTranslationOutput.region != null) {
            xmlSerializer.startTag("", "Region");
            xmlSerializer.text(String.valueOf(postTranslationOutput.region));
            xmlSerializer.endTag("", "Region");
        }
        if (postTranslationOutput.bucket != null) {
            xmlSerializer.startTag("", "Bucket");
            xmlSerializer.text(String.valueOf(postTranslationOutput.bucket));
            xmlSerializer.endTag("", "Bucket");
        }
        if (postTranslationOutput.object != null) {
            xmlSerializer.startTag("", "Object");
            xmlSerializer.text(String.valueOf(postTranslationOutput.object));
            xmlSerializer.endTag("", "Object");
        }
        xmlSerializer.endTag("", str);
    }
}
