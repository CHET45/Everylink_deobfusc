package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitVoiceSeparateJob$$XmlAdapter extends IXmlAdapter<SubmitVoiceSeparateJob> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitVoiceSeparateJob submitVoiceSeparateJob, String str) throws XmlPullParserException, IOException {
        if (submitVoiceSeparateJob == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (submitVoiceSeparateJob.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(submitVoiceSeparateJob.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (submitVoiceSeparateJob.input != null) {
            QCloudXml.toXml(xmlSerializer, submitVoiceSeparateJob.input, "Input");
        }
        if (submitVoiceSeparateJob.operation != null) {
            QCloudXml.toXml(xmlSerializer, submitVoiceSeparateJob.operation, "Operation");
        }
        if (submitVoiceSeparateJob.callBackFormat != null) {
            xmlSerializer.startTag("", "CallBackFormat");
            xmlSerializer.text(String.valueOf(submitVoiceSeparateJob.callBackFormat));
            xmlSerializer.endTag("", "CallBackFormat");
        }
        if (submitVoiceSeparateJob.callBackType != null) {
            xmlSerializer.startTag("", "CallBackType");
            xmlSerializer.text(String.valueOf(submitVoiceSeparateJob.callBackType));
            xmlSerializer.endTag("", "CallBackType");
        }
        if (submitVoiceSeparateJob.callBack != null) {
            xmlSerializer.startTag("", "CallBack");
            xmlSerializer.text(String.valueOf(submitVoiceSeparateJob.callBack));
            xmlSerializer.endTag("", "CallBack");
        }
        if (submitVoiceSeparateJob.callBackMqConfig != null) {
            QCloudXml.toXml(xmlSerializer, submitVoiceSeparateJob.callBackMqConfig, "CallBackMqConfig");
        }
        xmlSerializer.endTag("", str);
    }
}
