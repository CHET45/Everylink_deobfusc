package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.PostTranslation;
import com.tencent.cos.xml.model.p033ci.p034ai.PostTranslationResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.ai.PostTranslationResponse$PostTranslationResponseOperation$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3024x73a352df extends IXmlAdapter<PostTranslationResponse.PostTranslationResponseOperation> {
    private HashMap<String, ChildElementBinder<PostTranslationResponse.PostTranslationResponseOperation>> childElementBinders;

    public C3024x73a352df() {
        HashMap<String, ChildElementBinder<PostTranslationResponse.PostTranslationResponseOperation>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Translation", new ChildElementBinder<PostTranslationResponse.PostTranslationResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.PostTranslationResponse$PostTranslationResponseOperation$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostTranslationResponse.PostTranslationResponseOperation postTranslationResponseOperation, String str) throws XmlPullParserException, IOException {
                postTranslationResponseOperation.translation = (PostTranslation.PostTranslationTranslation) QCloudXml.fromXml(xmlPullParser, PostTranslation.PostTranslationTranslation.class, "Translation");
            }
        });
        this.childElementBinders.put("Output", new ChildElementBinder<PostTranslationResponse.PostTranslationResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.PostTranslationResponse$PostTranslationResponseOperation$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostTranslationResponse.PostTranslationResponseOperation postTranslationResponseOperation, String str) throws XmlPullParserException, IOException {
                postTranslationResponseOperation.output = (PostTranslation.PostTranslationOutput) QCloudXml.fromXml(xmlPullParser, PostTranslation.PostTranslationOutput.class, "Output");
            }
        });
        this.childElementBinders.put("UserData", new ChildElementBinder<PostTranslationResponse.PostTranslationResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.PostTranslationResponse$PostTranslationResponseOperation$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostTranslationResponse.PostTranslationResponseOperation postTranslationResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postTranslationResponseOperation.userData = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobLevel", new ChildElementBinder<PostTranslationResponse.PostTranslationResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.PostTranslationResponse$PostTranslationResponseOperation$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostTranslationResponse.PostTranslationResponseOperation postTranslationResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postTranslationResponseOperation.jobLevel = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("AITranslateResult", new ChildElementBinder<PostTranslationResponse.PostTranslationResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.PostTranslationResponse$PostTranslationResponseOperation$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostTranslationResponse.PostTranslationResponseOperation postTranslationResponseOperation, String str) throws XmlPullParserException, IOException {
                postTranslationResponseOperation.aITranslateResult = (PostTranslationResponse.PostTranslationResponseAITranslateResult) QCloudXml.fromXml(xmlPullParser, PostTranslationResponse.PostTranslationResponseAITranslateResult.class, "AITranslateResult");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostTranslationResponse.PostTranslationResponseOperation fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostTranslationResponse.PostTranslationResponseOperation postTranslationResponseOperation = new PostTranslationResponse.PostTranslationResponseOperation();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostTranslationResponse.PostTranslationResponseOperation> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postTranslationResponseOperation, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Operation" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postTranslationResponseOperation;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postTranslationResponseOperation;
    }
}
