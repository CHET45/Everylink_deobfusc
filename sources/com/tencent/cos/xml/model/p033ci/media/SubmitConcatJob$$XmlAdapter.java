package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitConcatJob$$XmlAdapter extends IXmlAdapter<SubmitConcatJob> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitConcatJob submitConcatJob, String str) throws XmlPullParserException, IOException {
        if (submitConcatJob == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (submitConcatJob.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(submitConcatJob.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (submitConcatJob.input != null) {
            QCloudXml.toXml(xmlSerializer, submitConcatJob.input, "Input");
        }
        if (submitConcatJob.operation != null) {
            QCloudXml.toXml(xmlSerializer, submitConcatJob.operation, "Operation");
        }
        if (submitConcatJob.callBackFormat != null) {
            xmlSerializer.startTag("", "CallBackFormat");
            xmlSerializer.text(String.valueOf(submitConcatJob.callBackFormat));
            xmlSerializer.endTag("", "CallBackFormat");
        }
        if (submitConcatJob.callBackType != null) {
            xmlSerializer.startTag("", "CallBackType");
            xmlSerializer.text(String.valueOf(submitConcatJob.callBackType));
            xmlSerializer.endTag("", "CallBackType");
        }
        if (submitConcatJob.callBack != null) {
            xmlSerializer.startTag("", "CallBack");
            xmlSerializer.text(String.valueOf(submitConcatJob.callBack));
            xmlSerializer.endTag("", "CallBack");
        }
        if (submitConcatJob.callBackMqConfig != null) {
            QCloudXml.toXml(xmlSerializer, submitConcatJob.callBackMqConfig, "CallBackMqConfig");
        }
        xmlSerializer.endTag("", str);
    }
}
