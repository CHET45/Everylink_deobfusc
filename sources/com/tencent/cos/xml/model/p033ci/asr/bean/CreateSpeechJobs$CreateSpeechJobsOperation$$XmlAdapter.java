package com.tencent.cos.xml.model.p033ci.asr.bean;

import com.tencent.cos.xml.model.p033ci.asr.bean.CreateSpeechJobs;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class CreateSpeechJobs$CreateSpeechJobsOperation$$XmlAdapter extends IXmlAdapter<CreateSpeechJobs.CreateSpeechJobsOperation> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, CreateSpeechJobs.CreateSpeechJobsOperation createSpeechJobsOperation, String str) throws XmlPullParserException, IOException {
        if (createSpeechJobsOperation == null) {
            return;
        }
        if (str == null) {
            str = "Operation";
        }
        xmlSerializer.startTag("", str);
        if (createSpeechJobsOperation.speechRecognition != null) {
            QCloudXml.toXml(xmlSerializer, createSpeechJobsOperation.speechRecognition, "SpeechRecognition");
        }
        if (createSpeechJobsOperation.output != null) {
            QCloudXml.toXml(xmlSerializer, createSpeechJobsOperation.output, "Output");
        }
        if (createSpeechJobsOperation.userData != null) {
            xmlSerializer.startTag("", "UserData");
            xmlSerializer.text(String.valueOf(createSpeechJobsOperation.userData));
            xmlSerializer.endTag("", "UserData");
        }
        xmlSerializer.startTag("", "JobLevel");
        xmlSerializer.text(String.valueOf(createSpeechJobsOperation.jobLevel));
        xmlSerializer.endTag("", "JobLevel");
        if (createSpeechJobsOperation.templateId != null) {
            xmlSerializer.startTag("", "TemplateId");
            xmlSerializer.text(String.valueOf(createSpeechJobsOperation.templateId));
            xmlSerializer.endTag("", "TemplateId");
        }
        xmlSerializer.endTag("", str);
    }
}
