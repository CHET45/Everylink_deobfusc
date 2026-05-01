package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitVoiceSeparateJob;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitVoiceSeparateJob$SubmitVoiceSeparateJobOperation$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3651xfa0153fb extends IXmlAdapter<SubmitVoiceSeparateJob.SubmitVoiceSeparateJobOperation> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitVoiceSeparateJob.SubmitVoiceSeparateJobOperation submitVoiceSeparateJobOperation, String str) throws XmlPullParserException, IOException {
        if (submitVoiceSeparateJobOperation == null) {
            return;
        }
        if (str == null) {
            str = "Operation";
        }
        xmlSerializer.startTag("", str);
        if (submitVoiceSeparateJobOperation.voiceSeparate != null) {
            QCloudXml.toXml(xmlSerializer, submitVoiceSeparateJobOperation.voiceSeparate, "VoiceSeparate");
        }
        if (submitVoiceSeparateJobOperation.templateId != null) {
            xmlSerializer.startTag("", "TemplateId");
            xmlSerializer.text(String.valueOf(submitVoiceSeparateJobOperation.templateId));
            xmlSerializer.endTag("", "TemplateId");
        }
        if (submitVoiceSeparateJobOperation.output != null) {
            QCloudXml.toXml(xmlSerializer, submitVoiceSeparateJobOperation.output, "Output");
        }
        if (submitVoiceSeparateJobOperation.jobLevel != null) {
            xmlSerializer.startTag("", "JobLevel");
            xmlSerializer.text(String.valueOf(submitVoiceSeparateJobOperation.jobLevel));
            xmlSerializer.endTag("", "JobLevel");
        }
        xmlSerializer.endTag("", str);
    }
}
