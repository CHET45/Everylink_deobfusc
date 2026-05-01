package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitTranscodeJob$$XmlAdapter extends IXmlAdapter<SubmitTranscodeJob> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitTranscodeJob submitTranscodeJob, String str) throws XmlPullParserException, IOException {
        if (submitTranscodeJob == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (submitTranscodeJob.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(submitTranscodeJob.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (submitTranscodeJob.input != null) {
            QCloudXml.toXml(xmlSerializer, submitTranscodeJob.input, "Input");
        }
        if (submitTranscodeJob.operation != null) {
            QCloudXml.toXml(xmlSerializer, submitTranscodeJob.operation, "Operation");
        }
        if (submitTranscodeJob.queueType != null) {
            xmlSerializer.startTag("", "QueueType");
            xmlSerializer.text(String.valueOf(submitTranscodeJob.queueType));
            xmlSerializer.endTag("", "QueueType");
        }
        if (submitTranscodeJob.callBackFormat != null) {
            xmlSerializer.startTag("", "CallBackFormat");
            xmlSerializer.text(String.valueOf(submitTranscodeJob.callBackFormat));
            xmlSerializer.endTag("", "CallBackFormat");
        }
        if (submitTranscodeJob.callBackType != null) {
            xmlSerializer.startTag("", "CallBackType");
            xmlSerializer.text(String.valueOf(submitTranscodeJob.callBackType));
            xmlSerializer.endTag("", "CallBackType");
        }
        if (submitTranscodeJob.callBack != null) {
            xmlSerializer.startTag("", "CallBack");
            xmlSerializer.text(String.valueOf(submitTranscodeJob.callBack));
            xmlSerializer.endTag("", "CallBack");
        }
        if (submitTranscodeJob.callBackMqConfig != null) {
            QCloudXml.toXml(xmlSerializer, submitTranscodeJob.callBackMqConfig, "CallBackMqConfig");
        }
        xmlSerializer.endTag("", str);
    }
}
