package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.common.MediaResult;
import com.tencent.cos.xml.model.p033ci.media.SubmitAnimationJob;
import com.tencent.cos.xml.model.p033ci.media.SubmitAnimationJobResponse;
import com.tencent.cos.xml.model.tag.MediaInfo;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitAnimationJobResponse$SubmitAnimationJobResponseOperation$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3535xe11af53b extends IXmlAdapter<SubmitAnimationJobResponse.SubmitAnimationJobResponseOperation> {
    private HashMap<String, ChildElementBinder<SubmitAnimationJobResponse.SubmitAnimationJobResponseOperation>> childElementBinders;

    public C3535xe11af53b() {
        HashMap<String, ChildElementBinder<SubmitAnimationJobResponse.SubmitAnimationJobResponseOperation>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("TemplateId", new ChildElementBinder<SubmitAnimationJobResponse.SubmitAnimationJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitAnimationJobResponse$SubmitAnimationJobResponseOperation$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitAnimationJobResponse.SubmitAnimationJobResponseOperation submitAnimationJobResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitAnimationJobResponseOperation.templateId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("TemplateName", new ChildElementBinder<SubmitAnimationJobResponse.SubmitAnimationJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitAnimationJobResponse$SubmitAnimationJobResponseOperation$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitAnimationJobResponse.SubmitAnimationJobResponseOperation submitAnimationJobResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitAnimationJobResponseOperation.templateName = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Animation", new ChildElementBinder<SubmitAnimationJobResponse.SubmitAnimationJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitAnimationJobResponse$SubmitAnimationJobResponseOperation$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitAnimationJobResponse.SubmitAnimationJobResponseOperation submitAnimationJobResponseOperation, String str) throws XmlPullParserException, IOException {
                submitAnimationJobResponseOperation.animation = (SubmitAnimationJob.SubmitAnimationJobAnimation) QCloudXml.fromXml(xmlPullParser, SubmitAnimationJob.SubmitAnimationJobAnimation.class, "Animation");
            }
        });
        this.childElementBinders.put("Output", new ChildElementBinder<SubmitAnimationJobResponse.SubmitAnimationJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitAnimationJobResponse$SubmitAnimationJobResponseOperation$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitAnimationJobResponse.SubmitAnimationJobResponseOperation submitAnimationJobResponseOperation, String str) throws XmlPullParserException, IOException {
                submitAnimationJobResponseOperation.output = (SubmitAnimationJob.SubmitAnimationJobOutput) QCloudXml.fromXml(xmlPullParser, SubmitAnimationJob.SubmitAnimationJobOutput.class, "Output");
            }
        });
        this.childElementBinders.put("MediaInfo", new ChildElementBinder<SubmitAnimationJobResponse.SubmitAnimationJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitAnimationJobResponse$SubmitAnimationJobResponseOperation$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitAnimationJobResponse.SubmitAnimationJobResponseOperation submitAnimationJobResponseOperation, String str) throws XmlPullParserException, IOException {
                submitAnimationJobResponseOperation.mediaInfo = (MediaInfo) QCloudXml.fromXml(xmlPullParser, MediaInfo.class, "MediaInfo");
            }
        });
        this.childElementBinders.put("MediaResult", new ChildElementBinder<SubmitAnimationJobResponse.SubmitAnimationJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitAnimationJobResponse$SubmitAnimationJobResponseOperation$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitAnimationJobResponse.SubmitAnimationJobResponseOperation submitAnimationJobResponseOperation, String str) throws XmlPullParserException, IOException {
                submitAnimationJobResponseOperation.mediaResult = (MediaResult) QCloudXml.fromXml(xmlPullParser, MediaResult.class, "MediaResult");
            }
        });
        this.childElementBinders.put("UserData", new ChildElementBinder<SubmitAnimationJobResponse.SubmitAnimationJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitAnimationJobResponse$SubmitAnimationJobResponseOperation$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitAnimationJobResponse.SubmitAnimationJobResponseOperation submitAnimationJobResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitAnimationJobResponseOperation.userData = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobLevel", new ChildElementBinder<SubmitAnimationJobResponse.SubmitAnimationJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitAnimationJobResponse$SubmitAnimationJobResponseOperation$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitAnimationJobResponse.SubmitAnimationJobResponseOperation submitAnimationJobResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitAnimationJobResponseOperation.jobLevel = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitAnimationJobResponse.SubmitAnimationJobResponseOperation fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitAnimationJobResponse.SubmitAnimationJobResponseOperation submitAnimationJobResponseOperation = new SubmitAnimationJobResponse.SubmitAnimationJobResponseOperation();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitAnimationJobResponse.SubmitAnimationJobResponseOperation> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitAnimationJobResponseOperation, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Operation" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitAnimationJobResponseOperation;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitAnimationJobResponseOperation;
    }
}
