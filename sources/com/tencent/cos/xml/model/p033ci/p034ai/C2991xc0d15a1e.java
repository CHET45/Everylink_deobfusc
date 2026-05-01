package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.PostNoiseReductionResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.ai.PostNoiseReductionResponse$PostNoiseReductionResponseInput$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C2991xc0d15a1e extends IXmlAdapter<PostNoiseReductionResponse.PostNoiseReductionResponseInput> {
    private HashMap<String, ChildElementBinder<PostNoiseReductionResponse.PostNoiseReductionResponseInput>> childElementBinders;

    public C2991xc0d15a1e() {
        HashMap<String, ChildElementBinder<PostNoiseReductionResponse.PostNoiseReductionResponseInput>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Region", new ChildElementBinder<PostNoiseReductionResponse.PostNoiseReductionResponseInput>() { // from class: com.tencent.cos.xml.model.ci.ai.PostNoiseReductionResponse$PostNoiseReductionResponseInput$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostNoiseReductionResponse.PostNoiseReductionResponseInput postNoiseReductionResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postNoiseReductionResponseInput.region = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Bucket", new ChildElementBinder<PostNoiseReductionResponse.PostNoiseReductionResponseInput>() { // from class: com.tencent.cos.xml.model.ci.ai.PostNoiseReductionResponse$PostNoiseReductionResponseInput$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostNoiseReductionResponse.PostNoiseReductionResponseInput postNoiseReductionResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postNoiseReductionResponseInput.bucket = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Object", new ChildElementBinder<PostNoiseReductionResponse.PostNoiseReductionResponseInput>() { // from class: com.tencent.cos.xml.model.ci.ai.PostNoiseReductionResponse$PostNoiseReductionResponseInput$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostNoiseReductionResponse.PostNoiseReductionResponseInput postNoiseReductionResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postNoiseReductionResponseInput.object = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostNoiseReductionResponse.PostNoiseReductionResponseInput fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostNoiseReductionResponse.PostNoiseReductionResponseInput postNoiseReductionResponseInput = new PostNoiseReductionResponse.PostNoiseReductionResponseInput();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostNoiseReductionResponse.PostNoiseReductionResponseInput> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postNoiseReductionResponseInput, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Input" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postNoiseReductionResponseInput;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postNoiseReductionResponseInput;
    }
}
