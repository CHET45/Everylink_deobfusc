package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.common.NoiseReduction;
import com.tencent.cos.xml.model.p033ci.p034ai.PostNoiseReduction;
import com.tencent.cos.xml.model.p033ci.p034ai.PostNoiseReductionResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.ai.PostNoiseReductionResponse$PostNoiseReductionResponseOperation$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C2993x1bad363b extends IXmlAdapter<PostNoiseReductionResponse.PostNoiseReductionResponseOperation> {
    private HashMap<String, ChildElementBinder<PostNoiseReductionResponse.PostNoiseReductionResponseOperation>> childElementBinders;

    public C2993x1bad363b() {
        HashMap<String, ChildElementBinder<PostNoiseReductionResponse.PostNoiseReductionResponseOperation>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("TemplateId", new ChildElementBinder<PostNoiseReductionResponse.PostNoiseReductionResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.PostNoiseReductionResponse$PostNoiseReductionResponseOperation$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostNoiseReductionResponse.PostNoiseReductionResponseOperation postNoiseReductionResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postNoiseReductionResponseOperation.templateId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("TemplateName", new ChildElementBinder<PostNoiseReductionResponse.PostNoiseReductionResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.PostNoiseReductionResponse$PostNoiseReductionResponseOperation$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostNoiseReductionResponse.PostNoiseReductionResponseOperation postNoiseReductionResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postNoiseReductionResponseOperation.templateName = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("NoiseReduction", new ChildElementBinder<PostNoiseReductionResponse.PostNoiseReductionResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.PostNoiseReductionResponse$PostNoiseReductionResponseOperation$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostNoiseReductionResponse.PostNoiseReductionResponseOperation postNoiseReductionResponseOperation, String str) throws XmlPullParserException, IOException {
                postNoiseReductionResponseOperation.noiseReduction = (NoiseReduction) QCloudXml.fromXml(xmlPullParser, NoiseReduction.class, "NoiseReduction");
            }
        });
        this.childElementBinders.put("Output", new ChildElementBinder<PostNoiseReductionResponse.PostNoiseReductionResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.PostNoiseReductionResponse$PostNoiseReductionResponseOperation$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostNoiseReductionResponse.PostNoiseReductionResponseOperation postNoiseReductionResponseOperation, String str) throws XmlPullParserException, IOException {
                postNoiseReductionResponseOperation.output = (PostNoiseReduction.PostNoiseReductionOutput) QCloudXml.fromXml(xmlPullParser, PostNoiseReduction.PostNoiseReductionOutput.class, "Output");
            }
        });
        this.childElementBinders.put("UserData", new ChildElementBinder<PostNoiseReductionResponse.PostNoiseReductionResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.PostNoiseReductionResponse$PostNoiseReductionResponseOperation$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostNoiseReductionResponse.PostNoiseReductionResponseOperation postNoiseReductionResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postNoiseReductionResponseOperation.userData = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobLevel", new ChildElementBinder<PostNoiseReductionResponse.PostNoiseReductionResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.PostNoiseReductionResponse$PostNoiseReductionResponseOperation$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostNoiseReductionResponse.PostNoiseReductionResponseOperation postNoiseReductionResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postNoiseReductionResponseOperation.jobLevel = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostNoiseReductionResponse.PostNoiseReductionResponseOperation fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostNoiseReductionResponse.PostNoiseReductionResponseOperation postNoiseReductionResponseOperation = new PostNoiseReductionResponse.PostNoiseReductionResponseOperation();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostNoiseReductionResponse.PostNoiseReductionResponseOperation> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postNoiseReductionResponseOperation, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Operation" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postNoiseReductionResponseOperation;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postNoiseReductionResponseOperation;
    }
}
