package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.PostSoundHound;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostSoundHound$PostSoundHoundInput$$XmlAdapter extends IXmlAdapter<PostSoundHound.PostSoundHoundInput> {
    private HashMap<String, ChildElementBinder<PostSoundHound.PostSoundHoundInput>> childElementBinders;

    public PostSoundHound$PostSoundHoundInput$$XmlAdapter() {
        HashMap<String, ChildElementBinder<PostSoundHound.PostSoundHoundInput>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Object", new ChildElementBinder<PostSoundHound.PostSoundHoundInput>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSoundHound$PostSoundHoundInput$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSoundHound.PostSoundHoundInput postSoundHoundInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postSoundHoundInput.object = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostSoundHound.PostSoundHoundInput fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostSoundHound.PostSoundHoundInput postSoundHoundInput = new PostSoundHound.PostSoundHoundInput();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostSoundHound.PostSoundHoundInput> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postSoundHoundInput, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Input" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postSoundHoundInput;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postSoundHoundInput;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostSoundHound.PostSoundHoundInput postSoundHoundInput, String str) throws XmlPullParserException, IOException {
        if (postSoundHoundInput == null) {
            return;
        }
        if (str == null) {
            str = "Input";
        }
        xmlSerializer.startTag("", str);
        if (postSoundHoundInput.object != null) {
            xmlSerializer.startTag("", "Object");
            xmlSerializer.text(String.valueOf(postSoundHoundInput.object));
            xmlSerializer.endTag("", "Object");
        }
        xmlSerializer.endTag("", str);
    }
}
