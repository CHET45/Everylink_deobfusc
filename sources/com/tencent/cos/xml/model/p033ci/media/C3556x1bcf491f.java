package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.common.MediaResult;
import com.tencent.cos.xml.model.p033ci.media.SubmitConcatJob;
import com.tencent.cos.xml.model.p033ci.media.SubmitConcatJobResponse;
import com.tencent.cos.xml.model.tag.MediaInfo;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitConcatJobResponse$SubmitConcatJobResponseOperation$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3556x1bcf491f extends IXmlAdapter<SubmitConcatJobResponse.SubmitConcatJobResponseOperation> {
    private HashMap<String, ChildElementBinder<SubmitConcatJobResponse.SubmitConcatJobResponseOperation>> childElementBinders;

    public C3556x1bcf491f() {
        HashMap<String, ChildElementBinder<SubmitConcatJobResponse.SubmitConcatJobResponseOperation>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("TemplateId", new ChildElementBinder<SubmitConcatJobResponse.SubmitConcatJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitConcatJobResponse$SubmitConcatJobResponseOperation$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitConcatJobResponse.SubmitConcatJobResponseOperation submitConcatJobResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitConcatJobResponseOperation.templateId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("TemplateName", new ChildElementBinder<SubmitConcatJobResponse.SubmitConcatJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitConcatJobResponse$SubmitConcatJobResponseOperation$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitConcatJobResponse.SubmitConcatJobResponseOperation submitConcatJobResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitConcatJobResponseOperation.templateName = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("ConcatTemplate", new ChildElementBinder<SubmitConcatJobResponse.SubmitConcatJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitConcatJobResponse$SubmitConcatJobResponseOperation$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitConcatJobResponse.SubmitConcatJobResponseOperation submitConcatJobResponseOperation, String str) throws XmlPullParserException, IOException {
                submitConcatJobResponseOperation.concatTemplate = (SubmitConcatJob.SubmitConcatJobConcatTemplate) QCloudXml.fromXml(xmlPullParser, SubmitConcatJob.SubmitConcatJobConcatTemplate.class, "ConcatTemplate");
            }
        });
        this.childElementBinders.put("Output", new ChildElementBinder<SubmitConcatJobResponse.SubmitConcatJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitConcatJobResponse$SubmitConcatJobResponseOperation$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitConcatJobResponse.SubmitConcatJobResponseOperation submitConcatJobResponseOperation, String str) throws XmlPullParserException, IOException {
                submitConcatJobResponseOperation.output = (SubmitConcatJob.SubmitConcatJobOutput) QCloudXml.fromXml(xmlPullParser, SubmitConcatJob.SubmitConcatJobOutput.class, "Output");
            }
        });
        this.childElementBinders.put("MediaInfo", new ChildElementBinder<SubmitConcatJobResponse.SubmitConcatJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitConcatJobResponse$SubmitConcatJobResponseOperation$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitConcatJobResponse.SubmitConcatJobResponseOperation submitConcatJobResponseOperation, String str) throws XmlPullParserException, IOException {
                submitConcatJobResponseOperation.mediaInfo = (MediaInfo) QCloudXml.fromXml(xmlPullParser, MediaInfo.class, "MediaInfo");
            }
        });
        this.childElementBinders.put("MediaResult", new ChildElementBinder<SubmitConcatJobResponse.SubmitConcatJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitConcatJobResponse$SubmitConcatJobResponseOperation$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitConcatJobResponse.SubmitConcatJobResponseOperation submitConcatJobResponseOperation, String str) throws XmlPullParserException, IOException {
                submitConcatJobResponseOperation.mediaResult = (MediaResult) QCloudXml.fromXml(xmlPullParser, MediaResult.class, "MediaResult");
            }
        });
        this.childElementBinders.put("UserData", new ChildElementBinder<SubmitConcatJobResponse.SubmitConcatJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitConcatJobResponse$SubmitConcatJobResponseOperation$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitConcatJobResponse.SubmitConcatJobResponseOperation submitConcatJobResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitConcatJobResponseOperation.userData = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobLevel", new ChildElementBinder<SubmitConcatJobResponse.SubmitConcatJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitConcatJobResponse$SubmitConcatJobResponseOperation$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitConcatJobResponse.SubmitConcatJobResponseOperation submitConcatJobResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitConcatJobResponseOperation.jobLevel = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitConcatJobResponse.SubmitConcatJobResponseOperation fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitConcatJobResponse.SubmitConcatJobResponseOperation submitConcatJobResponseOperation = new SubmitConcatJobResponse.SubmitConcatJobResponseOperation();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitConcatJobResponse.SubmitConcatJobResponseOperation> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitConcatJobResponseOperation, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Operation" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitConcatJobResponseOperation;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitConcatJobResponseOperation;
    }
}
