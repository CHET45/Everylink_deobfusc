package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.common.VideoTargetRec;
import com.tencent.cos.xml.model.p033ci.p034ai.PostVideoTargetRecResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.ai.PostVideoTargetRecResponse$PostVideoTargetRecResponseOperation$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3032xe641091b extends IXmlAdapter<PostVideoTargetRecResponse.PostVideoTargetRecResponseOperation> {
    private HashMap<String, ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponseOperation>> childElementBinders;

    public C3032xe641091b() {
        HashMap<String, ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponseOperation>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("TemplateId", new ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVideoTargetRecResponse$PostVideoTargetRecResponseOperation$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVideoTargetRecResponse.PostVideoTargetRecResponseOperation postVideoTargetRecResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postVideoTargetRecResponseOperation.templateId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("TemplateName", new ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVideoTargetRecResponse$PostVideoTargetRecResponseOperation$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVideoTargetRecResponse.PostVideoTargetRecResponseOperation postVideoTargetRecResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postVideoTargetRecResponseOperation.templateName = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("VideoTargetRec", new ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVideoTargetRecResponse$PostVideoTargetRecResponseOperation$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVideoTargetRecResponse.PostVideoTargetRecResponseOperation postVideoTargetRecResponseOperation, String str) throws XmlPullParserException, IOException {
                postVideoTargetRecResponseOperation.videoTargetRec = (VideoTargetRec) QCloudXml.fromXml(xmlPullParser, VideoTargetRec.class, "VideoTargetRec");
            }
        });
        this.childElementBinders.put("VideoTargetRecResult", new ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVideoTargetRecResponse$PostVideoTargetRecResponseOperation$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVideoTargetRecResponse.PostVideoTargetRecResponseOperation postVideoTargetRecResponseOperation, String str) throws XmlPullParserException, IOException {
                postVideoTargetRecResponseOperation.videoTargetRecResult = (PostVideoTargetRecResponse.PostVideoTargetRecResponseVideoTargetRecResult) QCloudXml.fromXml(xmlPullParser, PostVideoTargetRecResponse.PostVideoTargetRecResponseVideoTargetRecResult.class, "VideoTargetRecResult");
            }
        });
        this.childElementBinders.put("UserData", new ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVideoTargetRecResponse$PostVideoTargetRecResponseOperation$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVideoTargetRecResponse.PostVideoTargetRecResponseOperation postVideoTargetRecResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postVideoTargetRecResponseOperation.userData = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobLevel", new ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVideoTargetRecResponse$PostVideoTargetRecResponseOperation$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVideoTargetRecResponse.PostVideoTargetRecResponseOperation postVideoTargetRecResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postVideoTargetRecResponseOperation.jobLevel = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostVideoTargetRecResponse.PostVideoTargetRecResponseOperation fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostVideoTargetRecResponse.PostVideoTargetRecResponseOperation postVideoTargetRecResponseOperation = new PostVideoTargetRecResponse.PostVideoTargetRecResponseOperation();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponseOperation> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postVideoTargetRecResponseOperation, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Operation" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postVideoTargetRecResponseOperation;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postVideoTargetRecResponseOperation;
    }
}
