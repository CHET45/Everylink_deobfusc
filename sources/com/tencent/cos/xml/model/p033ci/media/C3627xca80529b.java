package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.common.DigitalWatermark;
import com.tencent.cos.xml.model.p033ci.common.MediaResult;
import com.tencent.cos.xml.model.p033ci.media.SubmitTranscodeJob;
import com.tencent.cos.xml.model.p033ci.media.SubmitTranscodeJobResponse;
import com.tencent.cos.xml.model.p033ci.media.TemplateWatermark;
import com.tencent.cos.xml.model.tag.MediaInfo;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJobResponse$SubmitTranscodeJobResponseOperation$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3627xca80529b extends IXmlAdapter<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseOperation> {
    private HashMap<String, ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseOperation>> childElementBinders;

    public C3627xca80529b() {
        HashMap<String, ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseOperation>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("TemplateId", new ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJobResponse$SubmitTranscodeJobResponseOperation$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJobResponse.SubmitTranscodeJobResponseOperation submitTranscodeJobResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitTranscodeJobResponseOperation.templateId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("TemplateName", new ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJobResponse$SubmitTranscodeJobResponseOperation$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJobResponse.SubmitTranscodeJobResponseOperation submitTranscodeJobResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitTranscodeJobResponseOperation.templateName = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Transcode", new ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJobResponse$SubmitTranscodeJobResponseOperation$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJobResponse.SubmitTranscodeJobResponseOperation submitTranscodeJobResponseOperation, String str) throws XmlPullParserException, IOException {
                submitTranscodeJobResponseOperation.transcode = (SubmitTranscodeJob.SubmitTranscodeJobTranscode) QCloudXml.fromXml(xmlPullParser, SubmitTranscodeJob.SubmitTranscodeJobTranscode.class, "Transcode");
            }
        });
        this.childElementBinders.put("Watermark", new ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJobResponse$SubmitTranscodeJobResponseOperation$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJobResponse.SubmitTranscodeJobResponseOperation submitTranscodeJobResponseOperation, String str) throws XmlPullParserException, IOException {
                if (submitTranscodeJobResponseOperation.watermark == null) {
                    submitTranscodeJobResponseOperation.watermark = new ArrayList();
                }
                submitTranscodeJobResponseOperation.watermark.add((TemplateWatermark.Watermark) QCloudXml.fromXml(xmlPullParser, TemplateWatermark.Watermark.class, "Watermark"));
            }
        });
        this.childElementBinders.put("RemoveWatermark", new ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJobResponse$SubmitTranscodeJobResponseOperation$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJobResponse.SubmitTranscodeJobResponseOperation submitTranscodeJobResponseOperation, String str) throws XmlPullParserException, IOException {
                submitTranscodeJobResponseOperation.removeWatermark = (SubmitTranscodeJob.SubmitTranscodeJobRemoveWatermark) QCloudXml.fromXml(xmlPullParser, SubmitTranscodeJob.SubmitTranscodeJobRemoveWatermark.class, "RemoveWatermark");
            }
        });
        this.childElementBinders.put("WatermarkTemplateId", new ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJobResponse$SubmitTranscodeJobResponseOperation$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJobResponse.SubmitTranscodeJobResponseOperation submitTranscodeJobResponseOperation, String str) throws XmlPullParserException, IOException {
                if (submitTranscodeJobResponseOperation.watermarkTemplateId == null) {
                    submitTranscodeJobResponseOperation.watermarkTemplateId = new ArrayList();
                }
                int eventType = xmlPullParser.getEventType();
                while (eventType != 1) {
                    if (eventType == 2) {
                        xmlPullParser.next();
                        submitTranscodeJobResponseOperation.watermarkTemplateId.add(xmlPullParser.getText());
                    } else if (eventType == 3 && "WatermarkTemplateId".equalsIgnoreCase(xmlPullParser.getName())) {
                        return;
                    }
                    eventType = xmlPullParser.next();
                }
            }
        });
        this.childElementBinders.put("Output", new ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJobResponse$SubmitTranscodeJobResponseOperation$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJobResponse.SubmitTranscodeJobResponseOperation submitTranscodeJobResponseOperation, String str) throws XmlPullParserException, IOException {
                submitTranscodeJobResponseOperation.output = (SubmitTranscodeJob.SubmitTranscodeJobOutput) QCloudXml.fromXml(xmlPullParser, SubmitTranscodeJob.SubmitTranscodeJobOutput.class, "Output");
            }
        });
        this.childElementBinders.put("MediaInfo", new ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJobResponse$SubmitTranscodeJobResponseOperation$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJobResponse.SubmitTranscodeJobResponseOperation submitTranscodeJobResponseOperation, String str) throws XmlPullParserException, IOException {
                submitTranscodeJobResponseOperation.mediaInfo = (MediaInfo) QCloudXml.fromXml(xmlPullParser, MediaInfo.class, "MediaInfo");
            }
        });
        this.childElementBinders.put("MediaResult", new ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJobResponse$SubmitTranscodeJobResponseOperation$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJobResponse.SubmitTranscodeJobResponseOperation submitTranscodeJobResponseOperation, String str) throws XmlPullParserException, IOException {
                submitTranscodeJobResponseOperation.mediaResult = (MediaResult) QCloudXml.fromXml(xmlPullParser, MediaResult.class, "MediaResult");
            }
        });
        this.childElementBinders.put("DigitalWatermark", new ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJobResponse$SubmitTranscodeJobResponseOperation$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJobResponse.SubmitTranscodeJobResponseOperation submitTranscodeJobResponseOperation, String str) throws XmlPullParserException, IOException {
                submitTranscodeJobResponseOperation.digitalWatermark = (DigitalWatermark) QCloudXml.fromXml(xmlPullParser, DigitalWatermark.class, "DigitalWatermark");
            }
        });
        this.childElementBinders.put("UserData", new ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJobResponse$SubmitTranscodeJobResponseOperation$$XmlAdapter.11
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJobResponse.SubmitTranscodeJobResponseOperation submitTranscodeJobResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitTranscodeJobResponseOperation.userData = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobLevel", new ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJobResponse$SubmitTranscodeJobResponseOperation$$XmlAdapter.12
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJobResponse.SubmitTranscodeJobResponseOperation submitTranscodeJobResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitTranscodeJobResponseOperation.jobLevel = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitTranscodeJobResponse.SubmitTranscodeJobResponseOperation fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitTranscodeJobResponse.SubmitTranscodeJobResponseOperation submitTranscodeJobResponseOperation = new SubmitTranscodeJobResponse.SubmitTranscodeJobResponseOperation();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseOperation> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitTranscodeJobResponseOperation, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Operation" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitTranscodeJobResponseOperation;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitTranscodeJobResponseOperation;
    }
}
