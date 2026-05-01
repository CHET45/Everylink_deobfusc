package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitTranscodeJob;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitTranscodeJob$SubmitTranscodeJobOperation$$XmlAdapter extends IXmlAdapter<SubmitTranscodeJob.SubmitTranscodeJobOperation> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitTranscodeJob.SubmitTranscodeJobOperation submitTranscodeJobOperation, String str) throws XmlPullParserException, IOException {
        if (submitTranscodeJobOperation == null) {
            return;
        }
        if (str == null) {
            str = "Operation";
        }
        xmlSerializer.startTag("", str);
        if (submitTranscodeJobOperation.templateId != null) {
            xmlSerializer.startTag("", "TemplateId");
            xmlSerializer.text(String.valueOf(submitTranscodeJobOperation.templateId));
            xmlSerializer.endTag("", "TemplateId");
        }
        if (submitTranscodeJobOperation.transcode != null) {
            QCloudXml.toXml(xmlSerializer, submitTranscodeJobOperation.transcode, "Transcode");
        }
        if (submitTranscodeJobOperation.watermarkTemplateId != null) {
            for (int i = 0; i < submitTranscodeJobOperation.watermarkTemplateId.size(); i++) {
                xmlSerializer.startTag("", "WatermarkTemplateId");
                xmlSerializer.text(String.valueOf(submitTranscodeJobOperation.watermarkTemplateId.get(i)));
                xmlSerializer.endTag("", "WatermarkTemplateId");
            }
        }
        if (submitTranscodeJobOperation.watermark != null) {
            for (int i2 = 0; i2 < submitTranscodeJobOperation.watermark.size(); i2++) {
                QCloudXml.toXml(xmlSerializer, submitTranscodeJobOperation.watermark.get(i2), "Watermark");
            }
        }
        if (submitTranscodeJobOperation.removeWatermark != null) {
            QCloudXml.toXml(xmlSerializer, submitTranscodeJobOperation.removeWatermark, "RemoveWatermark");
        }
        if (submitTranscodeJobOperation.subtitles != null) {
            QCloudXml.toXml(xmlSerializer, submitTranscodeJobOperation.subtitles, "Subtitles");
        }
        if (submitTranscodeJobOperation.digitalWatermark != null) {
            QCloudXml.toXml(xmlSerializer, submitTranscodeJobOperation.digitalWatermark, "DigitalWatermark");
        }
        if (submitTranscodeJobOperation.output != null) {
            QCloudXml.toXml(xmlSerializer, submitTranscodeJobOperation.output, "Output");
        }
        if (submitTranscodeJobOperation.userData != null) {
            xmlSerializer.startTag("", "UserData");
            xmlSerializer.text(String.valueOf(submitTranscodeJobOperation.userData));
            xmlSerializer.endTag("", "UserData");
        }
        if (submitTranscodeJobOperation.jobLevel != null) {
            xmlSerializer.startTag("", "JobLevel");
            xmlSerializer.text(String.valueOf(submitTranscodeJobOperation.jobLevel));
            xmlSerializer.endTag("", "JobLevel");
        }
        xmlSerializer.endTag("", str);
    }
}
