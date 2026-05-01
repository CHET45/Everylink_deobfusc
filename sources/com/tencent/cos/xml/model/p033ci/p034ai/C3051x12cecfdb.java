package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.common.MediaInfo;
import com.tencent.cos.xml.model.p033ci.common.MediaResult;
import com.tencent.cos.xml.model.p033ci.p034ai.PostVoiceSynthesis;
import com.tencent.cos.xml.model.p033ci.p034ai.PostVoiceSynthesisResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.ai.PostVoiceSynthesisResponse$PostVoiceSynthesisResponseOperation$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3051x12cecfdb extends IXmlAdapter<PostVoiceSynthesisResponse.PostVoiceSynthesisResponseOperation> {
    private HashMap<String, ChildElementBinder<PostVoiceSynthesisResponse.PostVoiceSynthesisResponseOperation>> childElementBinders;

    public C3051x12cecfdb() {
        HashMap<String, ChildElementBinder<PostVoiceSynthesisResponse.PostVoiceSynthesisResponseOperation>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("TemplateId", new ChildElementBinder<PostVoiceSynthesisResponse.PostVoiceSynthesisResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVoiceSynthesisResponse$PostVoiceSynthesisResponseOperation$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVoiceSynthesisResponse.PostVoiceSynthesisResponseOperation postVoiceSynthesisResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postVoiceSynthesisResponseOperation.templateId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("TemplateName", new ChildElementBinder<PostVoiceSynthesisResponse.PostVoiceSynthesisResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVoiceSynthesisResponse$PostVoiceSynthesisResponseOperation$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVoiceSynthesisResponse.PostVoiceSynthesisResponseOperation postVoiceSynthesisResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postVoiceSynthesisResponseOperation.templateName = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("TtsTpl", new ChildElementBinder<PostVoiceSynthesisResponse.PostVoiceSynthesisResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVoiceSynthesisResponse$PostVoiceSynthesisResponseOperation$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVoiceSynthesisResponse.PostVoiceSynthesisResponseOperation postVoiceSynthesisResponseOperation, String str) throws XmlPullParserException, IOException {
                postVoiceSynthesisResponseOperation.ttsTpl = (PostVoiceSynthesis.PostVoiceSynthesisTtsTpl) QCloudXml.fromXml(xmlPullParser, PostVoiceSynthesis.PostVoiceSynthesisTtsTpl.class, "TtsTpl");
            }
        });
        this.childElementBinders.put("TtsConfig", new ChildElementBinder<PostVoiceSynthesisResponse.PostVoiceSynthesisResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVoiceSynthesisResponse$PostVoiceSynthesisResponseOperation$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVoiceSynthesisResponse.PostVoiceSynthesisResponseOperation postVoiceSynthesisResponseOperation, String str) throws XmlPullParserException, IOException {
                postVoiceSynthesisResponseOperation.ttsConfig = (PostVoiceSynthesis.PostVoiceSynthesisTtsConfig) QCloudXml.fromXml(xmlPullParser, PostVoiceSynthesis.PostVoiceSynthesisTtsConfig.class, "TtsConfig");
            }
        });
        this.childElementBinders.put("Output", new ChildElementBinder<PostVoiceSynthesisResponse.PostVoiceSynthesisResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVoiceSynthesisResponse$PostVoiceSynthesisResponseOperation$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVoiceSynthesisResponse.PostVoiceSynthesisResponseOperation postVoiceSynthesisResponseOperation, String str) throws XmlPullParserException, IOException {
                postVoiceSynthesisResponseOperation.output = (PostVoiceSynthesis.PostVoiceSynthesisOutput) QCloudXml.fromXml(xmlPullParser, PostVoiceSynthesis.PostVoiceSynthesisOutput.class, "Output");
            }
        });
        this.childElementBinders.put("MediaInfo", new ChildElementBinder<PostVoiceSynthesisResponse.PostVoiceSynthesisResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVoiceSynthesisResponse$PostVoiceSynthesisResponseOperation$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVoiceSynthesisResponse.PostVoiceSynthesisResponseOperation postVoiceSynthesisResponseOperation, String str) throws XmlPullParserException, IOException {
                postVoiceSynthesisResponseOperation.mediaInfo = (MediaInfo) QCloudXml.fromXml(xmlPullParser, MediaInfo.class, "MediaInfo");
            }
        });
        this.childElementBinders.put("MediaResult", new ChildElementBinder<PostVoiceSynthesisResponse.PostVoiceSynthesisResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVoiceSynthesisResponse$PostVoiceSynthesisResponseOperation$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVoiceSynthesisResponse.PostVoiceSynthesisResponseOperation postVoiceSynthesisResponseOperation, String str) throws XmlPullParserException, IOException {
                postVoiceSynthesisResponseOperation.mediaResult = (MediaResult) QCloudXml.fromXml(xmlPullParser, MediaResult.class, "MediaResult");
            }
        });
        this.childElementBinders.put("UserData", new ChildElementBinder<PostVoiceSynthesisResponse.PostVoiceSynthesisResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVoiceSynthesisResponse$PostVoiceSynthesisResponseOperation$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVoiceSynthesisResponse.PostVoiceSynthesisResponseOperation postVoiceSynthesisResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postVoiceSynthesisResponseOperation.userData = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobLevel", new ChildElementBinder<PostVoiceSynthesisResponse.PostVoiceSynthesisResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVoiceSynthesisResponse$PostVoiceSynthesisResponseOperation$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVoiceSynthesisResponse.PostVoiceSynthesisResponseOperation postVoiceSynthesisResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postVoiceSynthesisResponseOperation.jobLevel = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostVoiceSynthesisResponse.PostVoiceSynthesisResponseOperation fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostVoiceSynthesisResponse.PostVoiceSynthesisResponseOperation postVoiceSynthesisResponseOperation = new PostVoiceSynthesisResponse.PostVoiceSynthesisResponseOperation();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostVoiceSynthesisResponse.PostVoiceSynthesisResponseOperation> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postVoiceSynthesisResponseOperation, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Operation" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postVoiceSynthesisResponseOperation;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postVoiceSynthesisResponseOperation;
    }
}
