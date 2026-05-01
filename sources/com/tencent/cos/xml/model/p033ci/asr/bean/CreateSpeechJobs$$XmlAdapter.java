package com.tencent.cos.xml.model.p033ci.asr.bean;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class CreateSpeechJobs$$XmlAdapter extends IXmlAdapter<CreateSpeechJobs> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, CreateSpeechJobs createSpeechJobs, String str) throws XmlPullParserException, IOException {
        if (createSpeechJobs == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (createSpeechJobs.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(createSpeechJobs.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (createSpeechJobs.input != null) {
            QCloudXml.toXml(xmlSerializer, createSpeechJobs.input, "Input");
        }
        if (createSpeechJobs.operation != null) {
            QCloudXml.toXml(xmlSerializer, createSpeechJobs.operation, "Operation");
        }
        if (createSpeechJobs.queueId != null) {
            xmlSerializer.startTag("", "QueueId");
            xmlSerializer.text(String.valueOf(createSpeechJobs.queueId));
            xmlSerializer.endTag("", "QueueId");
        }
        if (createSpeechJobs.callBack != null) {
            xmlSerializer.startTag("", "CallBack");
            xmlSerializer.text(String.valueOf(createSpeechJobs.callBack));
            xmlSerializer.endTag("", "CallBack");
        }
        if (createSpeechJobs.callBackFormat != null) {
            xmlSerializer.startTag("", "CallBackFormat");
            xmlSerializer.text(String.valueOf(createSpeechJobs.callBackFormat));
            xmlSerializer.endTag("", "CallBackFormat");
        }
        if (createSpeechJobs.callBackType != null) {
            xmlSerializer.startTag("", "CallBackType");
            xmlSerializer.text(String.valueOf(createSpeechJobs.callBackType));
            xmlSerializer.endTag("", "CallBackType");
        }
        if (createSpeechJobs.callBackMqConfig != null) {
            QCloudXml.toXml(xmlSerializer, createSpeechJobs.callBackMqConfig, "CallBackMqConfig");
        }
        xmlSerializer.endTag("", str);
    }
}
