package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.PostVoiceSynthesis;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostVoiceSynthesis$PostVoiceSynthesisOutput$$XmlAdapter extends IXmlAdapter<PostVoiceSynthesis.PostVoiceSynthesisOutput> {
    private HashMap<String, ChildElementBinder<PostVoiceSynthesis.PostVoiceSynthesisOutput>> childElementBinders;

    public PostVoiceSynthesis$PostVoiceSynthesisOutput$$XmlAdapter() {
        HashMap<String, ChildElementBinder<PostVoiceSynthesis.PostVoiceSynthesisOutput>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Region", new ChildElementBinder<PostVoiceSynthesis.PostVoiceSynthesisOutput>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVoiceSynthesis$PostVoiceSynthesisOutput$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVoiceSynthesis.PostVoiceSynthesisOutput postVoiceSynthesisOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postVoiceSynthesisOutput.region = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Bucket", new ChildElementBinder<PostVoiceSynthesis.PostVoiceSynthesisOutput>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVoiceSynthesis$PostVoiceSynthesisOutput$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVoiceSynthesis.PostVoiceSynthesisOutput postVoiceSynthesisOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postVoiceSynthesisOutput.bucket = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Object", new ChildElementBinder<PostVoiceSynthesis.PostVoiceSynthesisOutput>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVoiceSynthesis$PostVoiceSynthesisOutput$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVoiceSynthesis.PostVoiceSynthesisOutput postVoiceSynthesisOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postVoiceSynthesisOutput.object = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostVoiceSynthesis.PostVoiceSynthesisOutput fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostVoiceSynthesis.PostVoiceSynthesisOutput postVoiceSynthesisOutput = new PostVoiceSynthesis.PostVoiceSynthesisOutput();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostVoiceSynthesis.PostVoiceSynthesisOutput> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postVoiceSynthesisOutput, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Output" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postVoiceSynthesisOutput;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postVoiceSynthesisOutput;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostVoiceSynthesis.PostVoiceSynthesisOutput postVoiceSynthesisOutput, String str) throws XmlPullParserException, IOException {
        if (postVoiceSynthesisOutput == null) {
            return;
        }
        if (str == null) {
            str = "Output";
        }
        xmlSerializer.startTag("", str);
        if (postVoiceSynthesisOutput.region != null) {
            xmlSerializer.startTag("", "Region");
            xmlSerializer.text(String.valueOf(postVoiceSynthesisOutput.region));
            xmlSerializer.endTag("", "Region");
        }
        if (postVoiceSynthesisOutput.bucket != null) {
            xmlSerializer.startTag("", "Bucket");
            xmlSerializer.text(String.valueOf(postVoiceSynthesisOutput.bucket));
            xmlSerializer.endTag("", "Bucket");
        }
        if (postVoiceSynthesisOutput.object != null) {
            xmlSerializer.startTag("", "Object");
            xmlSerializer.text(String.valueOf(postVoiceSynthesisOutput.object));
            xmlSerializer.endTag("", "Object");
        }
        xmlSerializer.endTag("", str);
    }
}
