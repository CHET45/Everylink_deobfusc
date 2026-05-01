package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.common.MediaResult;
import com.tencent.cos.xml.model.p033ci.media.SubmitVoiceSeparateJob;
import com.tencent.cos.xml.model.p033ci.media.SubmitVoiceSeparateJobResponse;
import com.tencent.cos.xml.model.tag.MediaInfo;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitVoiceSeparateJobResponse$SubmitVoiceSeparateJobResponseOperation$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3661xd5d0d01b extends IXmlAdapter<SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseOperation> {
    private HashMap<String, ChildElementBinder<SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseOperation>> childElementBinders;

    public C3661xd5d0d01b() {
        HashMap<String, ChildElementBinder<SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseOperation>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("TemplateId", new ChildElementBinder<SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVoiceSeparateJobResponse$SubmitVoiceSeparateJobResponseOperation$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseOperation submitVoiceSeparateJobResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVoiceSeparateJobResponseOperation.templateId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("TemplateName", new ChildElementBinder<SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVoiceSeparateJobResponse$SubmitVoiceSeparateJobResponseOperation$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseOperation submitVoiceSeparateJobResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVoiceSeparateJobResponseOperation.templateName = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("VoiceSeparate", new ChildElementBinder<SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVoiceSeparateJobResponse$SubmitVoiceSeparateJobResponseOperation$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseOperation submitVoiceSeparateJobResponseOperation, String str) throws XmlPullParserException, IOException {
                submitVoiceSeparateJobResponseOperation.voiceSeparate = (SubmitVoiceSeparateJob.VoiceSeparate) QCloudXml.fromXml(xmlPullParser, SubmitVoiceSeparateJob.VoiceSeparate.class, "VoiceSeparate");
            }
        });
        this.childElementBinders.put("Output", new ChildElementBinder<SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVoiceSeparateJobResponse$SubmitVoiceSeparateJobResponseOperation$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseOperation submitVoiceSeparateJobResponseOperation, String str) throws XmlPullParserException, IOException {
                submitVoiceSeparateJobResponseOperation.output = (SubmitVoiceSeparateJob.SubmitVoiceSeparateJobOutput) QCloudXml.fromXml(xmlPullParser, SubmitVoiceSeparateJob.SubmitVoiceSeparateJobOutput.class, "Output");
            }
        });
        this.childElementBinders.put("MediaInfo", new ChildElementBinder<SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVoiceSeparateJobResponse$SubmitVoiceSeparateJobResponseOperation$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseOperation submitVoiceSeparateJobResponseOperation, String str) throws XmlPullParserException, IOException {
                submitVoiceSeparateJobResponseOperation.mediaInfo = (MediaInfo) QCloudXml.fromXml(xmlPullParser, MediaInfo.class, "MediaInfo");
            }
        });
        this.childElementBinders.put("MediaResult", new ChildElementBinder<SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVoiceSeparateJobResponse$SubmitVoiceSeparateJobResponseOperation$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseOperation submitVoiceSeparateJobResponseOperation, String str) throws XmlPullParserException, IOException {
                submitVoiceSeparateJobResponseOperation.mediaResult = (MediaResult) QCloudXml.fromXml(xmlPullParser, MediaResult.class, "MediaResult");
            }
        });
        this.childElementBinders.put("UserData", new ChildElementBinder<SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVoiceSeparateJobResponse$SubmitVoiceSeparateJobResponseOperation$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseOperation submitVoiceSeparateJobResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVoiceSeparateJobResponseOperation.userData = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobLevel", new ChildElementBinder<SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVoiceSeparateJobResponse$SubmitVoiceSeparateJobResponseOperation$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseOperation submitVoiceSeparateJobResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVoiceSeparateJobResponseOperation.jobLevel = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseOperation fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseOperation submitVoiceSeparateJobResponseOperation = new SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseOperation();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseOperation> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitVoiceSeparateJobResponseOperation, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Operation" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitVoiceSeparateJobResponseOperation;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitVoiceSeparateJobResponseOperation;
    }
}
