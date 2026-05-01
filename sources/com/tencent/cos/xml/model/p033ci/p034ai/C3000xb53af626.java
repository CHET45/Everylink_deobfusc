package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.PostSegmentVideoBody;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.ai.PostSegmentVideoBody$PostSegmentVideoBodySegmentVideoBody$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3000xb53af626 extends IXmlAdapter<PostSegmentVideoBody.PostSegmentVideoBodySegmentVideoBody> {
    private HashMap<String, ChildElementBinder<PostSegmentVideoBody.PostSegmentVideoBodySegmentVideoBody>> childElementBinders;

    public C3000xb53af626() {
        HashMap<String, ChildElementBinder<PostSegmentVideoBody.PostSegmentVideoBodySegmentVideoBody>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Mode", new ChildElementBinder<PostSegmentVideoBody.PostSegmentVideoBodySegmentVideoBody>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSegmentVideoBody$PostSegmentVideoBodySegmentVideoBody$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSegmentVideoBody.PostSegmentVideoBodySegmentVideoBody postSegmentVideoBodySegmentVideoBody, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postSegmentVideoBodySegmentVideoBody.mode = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SegmentType", new ChildElementBinder<PostSegmentVideoBody.PostSegmentVideoBodySegmentVideoBody>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSegmentVideoBody$PostSegmentVideoBodySegmentVideoBody$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSegmentVideoBody.PostSegmentVideoBodySegmentVideoBody postSegmentVideoBodySegmentVideoBody, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postSegmentVideoBodySegmentVideoBody.segmentType = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BackgroundRed", new ChildElementBinder<PostSegmentVideoBody.PostSegmentVideoBodySegmentVideoBody>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSegmentVideoBody$PostSegmentVideoBodySegmentVideoBody$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSegmentVideoBody.PostSegmentVideoBodySegmentVideoBody postSegmentVideoBodySegmentVideoBody, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postSegmentVideoBodySegmentVideoBody.backgroundRed = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BackgroundGreen", new ChildElementBinder<PostSegmentVideoBody.PostSegmentVideoBodySegmentVideoBody>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSegmentVideoBody$PostSegmentVideoBodySegmentVideoBody$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSegmentVideoBody.PostSegmentVideoBodySegmentVideoBody postSegmentVideoBodySegmentVideoBody, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postSegmentVideoBodySegmentVideoBody.backgroundGreen = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BackgroundBlue", new ChildElementBinder<PostSegmentVideoBody.PostSegmentVideoBodySegmentVideoBody>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSegmentVideoBody$PostSegmentVideoBodySegmentVideoBody$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSegmentVideoBody.PostSegmentVideoBodySegmentVideoBody postSegmentVideoBodySegmentVideoBody, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postSegmentVideoBodySegmentVideoBody.backgroundBlue = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BackgroundLogoUrl", new ChildElementBinder<PostSegmentVideoBody.PostSegmentVideoBodySegmentVideoBody>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSegmentVideoBody$PostSegmentVideoBodySegmentVideoBody$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSegmentVideoBody.PostSegmentVideoBodySegmentVideoBody postSegmentVideoBodySegmentVideoBody, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postSegmentVideoBodySegmentVideoBody.backgroundLogoUrl = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BinaryThreshold", new ChildElementBinder<PostSegmentVideoBody.PostSegmentVideoBodySegmentVideoBody>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSegmentVideoBody$PostSegmentVideoBodySegmentVideoBody$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSegmentVideoBody.PostSegmentVideoBodySegmentVideoBody postSegmentVideoBodySegmentVideoBody, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postSegmentVideoBodySegmentVideoBody.binaryThreshold = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("RemoveRed", new ChildElementBinder<PostSegmentVideoBody.PostSegmentVideoBodySegmentVideoBody>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSegmentVideoBody$PostSegmentVideoBodySegmentVideoBody$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSegmentVideoBody.PostSegmentVideoBodySegmentVideoBody postSegmentVideoBodySegmentVideoBody, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postSegmentVideoBodySegmentVideoBody.removeRed = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("RemoveGreen", new ChildElementBinder<PostSegmentVideoBody.PostSegmentVideoBodySegmentVideoBody>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSegmentVideoBody$PostSegmentVideoBodySegmentVideoBody$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSegmentVideoBody.PostSegmentVideoBodySegmentVideoBody postSegmentVideoBodySegmentVideoBody, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postSegmentVideoBodySegmentVideoBody.removeGreen = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("RemoveBlue", new ChildElementBinder<PostSegmentVideoBody.PostSegmentVideoBodySegmentVideoBody>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSegmentVideoBody$PostSegmentVideoBodySegmentVideoBody$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSegmentVideoBody.PostSegmentVideoBodySegmentVideoBody postSegmentVideoBodySegmentVideoBody, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postSegmentVideoBodySegmentVideoBody.removeBlue = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostSegmentVideoBody.PostSegmentVideoBodySegmentVideoBody fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostSegmentVideoBody.PostSegmentVideoBodySegmentVideoBody postSegmentVideoBodySegmentVideoBody = new PostSegmentVideoBody.PostSegmentVideoBodySegmentVideoBody();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostSegmentVideoBody.PostSegmentVideoBodySegmentVideoBody> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postSegmentVideoBodySegmentVideoBody, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "SegmentVideoBody" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postSegmentVideoBodySegmentVideoBody;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postSegmentVideoBodySegmentVideoBody;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostSegmentVideoBody.PostSegmentVideoBodySegmentVideoBody postSegmentVideoBodySegmentVideoBody, String str) throws XmlPullParserException, IOException {
        if (postSegmentVideoBodySegmentVideoBody == null) {
            return;
        }
        if (str == null) {
            str = "SegmentVideoBody";
        }
        xmlSerializer.startTag("", str);
        if (postSegmentVideoBodySegmentVideoBody.mode != null) {
            xmlSerializer.startTag("", "Mode");
            xmlSerializer.text(String.valueOf(postSegmentVideoBodySegmentVideoBody.mode));
            xmlSerializer.endTag("", "Mode");
        }
        if (postSegmentVideoBodySegmentVideoBody.segmentType != null) {
            xmlSerializer.startTag("", "SegmentType");
            xmlSerializer.text(String.valueOf(postSegmentVideoBodySegmentVideoBody.segmentType));
            xmlSerializer.endTag("", "SegmentType");
        }
        if (postSegmentVideoBodySegmentVideoBody.backgroundRed != null) {
            xmlSerializer.startTag("", "BackgroundRed");
            xmlSerializer.text(String.valueOf(postSegmentVideoBodySegmentVideoBody.backgroundRed));
            xmlSerializer.endTag("", "BackgroundRed");
        }
        if (postSegmentVideoBodySegmentVideoBody.backgroundGreen != null) {
            xmlSerializer.startTag("", "BackgroundGreen");
            xmlSerializer.text(String.valueOf(postSegmentVideoBodySegmentVideoBody.backgroundGreen));
            xmlSerializer.endTag("", "BackgroundGreen");
        }
        if (postSegmentVideoBodySegmentVideoBody.backgroundBlue != null) {
            xmlSerializer.startTag("", "BackgroundBlue");
            xmlSerializer.text(String.valueOf(postSegmentVideoBodySegmentVideoBody.backgroundBlue));
            xmlSerializer.endTag("", "BackgroundBlue");
        }
        if (postSegmentVideoBodySegmentVideoBody.backgroundLogoUrl != null) {
            xmlSerializer.startTag("", "BackgroundLogoUrl");
            xmlSerializer.text(String.valueOf(postSegmentVideoBodySegmentVideoBody.backgroundLogoUrl));
            xmlSerializer.endTag("", "BackgroundLogoUrl");
        }
        if (postSegmentVideoBodySegmentVideoBody.binaryThreshold != null) {
            xmlSerializer.startTag("", "BinaryThreshold");
            xmlSerializer.text(String.valueOf(postSegmentVideoBodySegmentVideoBody.binaryThreshold));
            xmlSerializer.endTag("", "BinaryThreshold");
        }
        if (postSegmentVideoBodySegmentVideoBody.removeRed != null) {
            xmlSerializer.startTag("", "RemoveRed");
            xmlSerializer.text(String.valueOf(postSegmentVideoBodySegmentVideoBody.removeRed));
            xmlSerializer.endTag("", "RemoveRed");
        }
        if (postSegmentVideoBodySegmentVideoBody.removeGreen != null) {
            xmlSerializer.startTag("", "RemoveGreen");
            xmlSerializer.text(String.valueOf(postSegmentVideoBodySegmentVideoBody.removeGreen));
            xmlSerializer.endTag("", "RemoveGreen");
        }
        if (postSegmentVideoBodySegmentVideoBody.removeBlue != null) {
            xmlSerializer.startTag("", "RemoveBlue");
            xmlSerializer.text(String.valueOf(postSegmentVideoBodySegmentVideoBody.removeBlue));
            xmlSerializer.endTag("", "RemoveBlue");
        }
        xmlSerializer.endTag("", str);
    }
}
