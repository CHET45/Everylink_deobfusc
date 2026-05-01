package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitMediaInfoJob$$XmlAdapter extends IXmlAdapter<SubmitMediaInfoJob> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitMediaInfoJob submitMediaInfoJob, String str) throws XmlPullParserException, IOException {
        if (submitMediaInfoJob == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (submitMediaInfoJob.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(submitMediaInfoJob.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (submitMediaInfoJob.input != null) {
            QCloudXml.toXml(xmlSerializer, submitMediaInfoJob.input, "Input");
        }
        if (submitMediaInfoJob.operation != null) {
            QCloudXml.toXml(xmlSerializer, submitMediaInfoJob.operation, "Operation");
        }
        if (submitMediaInfoJob.callBackFormat != null) {
            xmlSerializer.startTag("", "CallBackFormat");
            xmlSerializer.text(String.valueOf(submitMediaInfoJob.callBackFormat));
            xmlSerializer.endTag("", "CallBackFormat");
        }
        if (submitMediaInfoJob.callBackType != null) {
            xmlSerializer.startTag("", "CallBackType");
            xmlSerializer.text(String.valueOf(submitMediaInfoJob.callBackType));
            xmlSerializer.endTag("", "CallBackType");
        }
        if (submitMediaInfoJob.callBack != null) {
            xmlSerializer.startTag("", "CallBack");
            xmlSerializer.text(String.valueOf(submitMediaInfoJob.callBack));
            xmlSerializer.endTag("", "CallBack");
        }
        if (submitMediaInfoJob.callBackMqConfig != null) {
            QCloudXml.toXml(xmlSerializer, submitMediaInfoJob.callBackMqConfig, "CallBackMqConfig");
        }
        xmlSerializer.endTag("", str);
    }
}
