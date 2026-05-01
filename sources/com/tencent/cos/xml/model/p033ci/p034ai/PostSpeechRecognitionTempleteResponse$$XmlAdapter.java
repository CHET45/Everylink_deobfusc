package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.common.SpeechRecognitionTempleteResponseTemplate;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class PostSpeechRecognitionTempleteResponse$$XmlAdapter extends IXmlAdapter<PostSpeechRecognitionTempleteResponse> {
    private HashMap<String, ChildElementBinder<PostSpeechRecognitionTempleteResponse>> childElementBinders;

    public PostSpeechRecognitionTempleteResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<PostSpeechRecognitionTempleteResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Template", new ChildElementBinder<PostSpeechRecognitionTempleteResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSpeechRecognitionTempleteResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSpeechRecognitionTempleteResponse postSpeechRecognitionTempleteResponse, String str) throws XmlPullParserException, IOException {
                postSpeechRecognitionTempleteResponse.template = (SpeechRecognitionTempleteResponseTemplate) QCloudXml.fromXml(xmlPullParser, SpeechRecognitionTempleteResponseTemplate.class, "Template");
            }
        });
        this.childElementBinders.put("RequestId", new ChildElementBinder<PostSpeechRecognitionTempleteResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSpeechRecognitionTempleteResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSpeechRecognitionTempleteResponse postSpeechRecognitionTempleteResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postSpeechRecognitionTempleteResponse.requestId = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostSpeechRecognitionTempleteResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostSpeechRecognitionTempleteResponse postSpeechRecognitionTempleteResponse = new PostSpeechRecognitionTempleteResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostSpeechRecognitionTempleteResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postSpeechRecognitionTempleteResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postSpeechRecognitionTempleteResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postSpeechRecognitionTempleteResponse;
    }
}
