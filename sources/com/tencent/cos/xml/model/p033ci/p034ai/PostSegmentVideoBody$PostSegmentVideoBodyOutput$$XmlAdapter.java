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
public class PostSegmentVideoBody$PostSegmentVideoBodyOutput$$XmlAdapter extends IXmlAdapter<PostSegmentVideoBody.PostSegmentVideoBodyOutput> {
    private HashMap<String, ChildElementBinder<PostSegmentVideoBody.PostSegmentVideoBodyOutput>> childElementBinders;

    public PostSegmentVideoBody$PostSegmentVideoBodyOutput$$XmlAdapter() {
        HashMap<String, ChildElementBinder<PostSegmentVideoBody.PostSegmentVideoBodyOutput>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Region", new ChildElementBinder<PostSegmentVideoBody.PostSegmentVideoBodyOutput>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSegmentVideoBody$PostSegmentVideoBodyOutput$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSegmentVideoBody.PostSegmentVideoBodyOutput postSegmentVideoBodyOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postSegmentVideoBodyOutput.region = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Bucket", new ChildElementBinder<PostSegmentVideoBody.PostSegmentVideoBodyOutput>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSegmentVideoBody$PostSegmentVideoBodyOutput$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSegmentVideoBody.PostSegmentVideoBodyOutput postSegmentVideoBodyOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postSegmentVideoBodyOutput.bucket = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Object", new ChildElementBinder<PostSegmentVideoBody.PostSegmentVideoBodyOutput>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSegmentVideoBody$PostSegmentVideoBodyOutput$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSegmentVideoBody.PostSegmentVideoBodyOutput postSegmentVideoBodyOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postSegmentVideoBodyOutput.object = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostSegmentVideoBody.PostSegmentVideoBodyOutput fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostSegmentVideoBody.PostSegmentVideoBodyOutput postSegmentVideoBodyOutput = new PostSegmentVideoBody.PostSegmentVideoBodyOutput();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostSegmentVideoBody.PostSegmentVideoBodyOutput> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postSegmentVideoBodyOutput, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Output" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postSegmentVideoBodyOutput;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postSegmentVideoBodyOutput;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostSegmentVideoBody.PostSegmentVideoBodyOutput postSegmentVideoBodyOutput, String str) throws XmlPullParserException, IOException {
        if (postSegmentVideoBodyOutput == null) {
            return;
        }
        if (str == null) {
            str = "Output";
        }
        xmlSerializer.startTag("", str);
        if (postSegmentVideoBodyOutput.region != null) {
            xmlSerializer.startTag("", "Region");
            xmlSerializer.text(String.valueOf(postSegmentVideoBodyOutput.region));
            xmlSerializer.endTag("", "Region");
        }
        if (postSegmentVideoBodyOutput.bucket != null) {
            xmlSerializer.startTag("", "Bucket");
            xmlSerializer.text(String.valueOf(postSegmentVideoBodyOutput.bucket));
            xmlSerializer.endTag("", "Bucket");
        }
        if (postSegmentVideoBodyOutput.object != null) {
            xmlSerializer.startTag("", "Object");
            xmlSerializer.text(String.valueOf(postSegmentVideoBodyOutput.object));
            xmlSerializer.endTag("", "Object");
        }
        xmlSerializer.endTag("", str);
    }
}
