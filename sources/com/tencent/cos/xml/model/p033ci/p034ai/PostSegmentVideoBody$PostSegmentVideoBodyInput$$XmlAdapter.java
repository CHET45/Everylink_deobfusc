package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.PostSegmentVideoBody;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostSegmentVideoBody$PostSegmentVideoBodyInput$$XmlAdapter extends IXmlAdapter<PostSegmentVideoBody.PostSegmentVideoBodyInput> {
    private HashMap<String, ChildElementBinder<PostSegmentVideoBody.PostSegmentVideoBodyInput>> childElementBinders;

    public PostSegmentVideoBody$PostSegmentVideoBodyInput$$XmlAdapter() {
        HashMap<String, ChildElementBinder<PostSegmentVideoBody.PostSegmentVideoBodyInput>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Object", new ChildElementBinder<PostSegmentVideoBody.PostSegmentVideoBodyInput>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSegmentVideoBody$PostSegmentVideoBodyInput$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSegmentVideoBody.PostSegmentVideoBodyInput postSegmentVideoBodyInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postSegmentVideoBodyInput.object = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostSegmentVideoBody.PostSegmentVideoBodyInput fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostSegmentVideoBody.PostSegmentVideoBodyInput postSegmentVideoBodyInput = new PostSegmentVideoBody.PostSegmentVideoBodyInput();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostSegmentVideoBody.PostSegmentVideoBodyInput> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postSegmentVideoBodyInput, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Input" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postSegmentVideoBodyInput;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postSegmentVideoBodyInput;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostSegmentVideoBody.PostSegmentVideoBodyInput postSegmentVideoBodyInput, String str) throws XmlPullParserException, IOException {
        if (postSegmentVideoBodyInput == null) {
            return;
        }
        if (str == null) {
            str = "Input";
        }
        xmlSerializer.startTag("", str);
        if (postSegmentVideoBodyInput.object != null) {
            xmlSerializer.startTag("", "Object");
            xmlSerializer.text(String.valueOf(postSegmentVideoBodyInput.object));
            xmlSerializer.endTag("", "Object");
        }
        xmlSerializer.endTag("", str);
    }
}
