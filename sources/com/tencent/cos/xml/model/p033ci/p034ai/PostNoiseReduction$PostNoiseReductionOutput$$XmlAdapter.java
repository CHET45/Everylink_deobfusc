package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.PostNoiseReduction;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostNoiseReduction$PostNoiseReductionOutput$$XmlAdapter extends IXmlAdapter<PostNoiseReduction.PostNoiseReductionOutput> {
    private HashMap<String, ChildElementBinder<PostNoiseReduction.PostNoiseReductionOutput>> childElementBinders;

    public PostNoiseReduction$PostNoiseReductionOutput$$XmlAdapter() {
        HashMap<String, ChildElementBinder<PostNoiseReduction.PostNoiseReductionOutput>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Region", new ChildElementBinder<PostNoiseReduction.PostNoiseReductionOutput>() { // from class: com.tencent.cos.xml.model.ci.ai.PostNoiseReduction$PostNoiseReductionOutput$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostNoiseReduction.PostNoiseReductionOutput postNoiseReductionOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postNoiseReductionOutput.region = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Bucket", new ChildElementBinder<PostNoiseReduction.PostNoiseReductionOutput>() { // from class: com.tencent.cos.xml.model.ci.ai.PostNoiseReduction$PostNoiseReductionOutput$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostNoiseReduction.PostNoiseReductionOutput postNoiseReductionOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postNoiseReductionOutput.bucket = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Object", new ChildElementBinder<PostNoiseReduction.PostNoiseReductionOutput>() { // from class: com.tencent.cos.xml.model.ci.ai.PostNoiseReduction$PostNoiseReductionOutput$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostNoiseReduction.PostNoiseReductionOutput postNoiseReductionOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postNoiseReductionOutput.object = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostNoiseReduction.PostNoiseReductionOutput fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostNoiseReduction.PostNoiseReductionOutput postNoiseReductionOutput = new PostNoiseReduction.PostNoiseReductionOutput();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostNoiseReduction.PostNoiseReductionOutput> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postNoiseReductionOutput, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Output" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postNoiseReductionOutput;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postNoiseReductionOutput;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostNoiseReduction.PostNoiseReductionOutput postNoiseReductionOutput, String str) throws XmlPullParserException, IOException {
        if (postNoiseReductionOutput == null) {
            return;
        }
        if (str == null) {
            str = "Output";
        }
        xmlSerializer.startTag("", str);
        if (postNoiseReductionOutput.region != null) {
            xmlSerializer.startTag("", "Region");
            xmlSerializer.text(String.valueOf(postNoiseReductionOutput.region));
            xmlSerializer.endTag("", "Region");
        }
        if (postNoiseReductionOutput.bucket != null) {
            xmlSerializer.startTag("", "Bucket");
            xmlSerializer.text(String.valueOf(postNoiseReductionOutput.bucket));
            xmlSerializer.endTag("", "Bucket");
        }
        if (postNoiseReductionOutput.object != null) {
            xmlSerializer.startTag("", "Object");
            xmlSerializer.text(String.valueOf(postNoiseReductionOutput.object));
            xmlSerializer.endTag("", "Object");
        }
        xmlSerializer.endTag("", str);
    }
}
