package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitVideoTagJob$$XmlAdapter extends IXmlAdapter<SubmitVideoTagJob> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitVideoTagJob submitVideoTagJob, String str) throws XmlPullParserException, IOException {
        if (submitVideoTagJob == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (submitVideoTagJob.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(submitVideoTagJob.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (submitVideoTagJob.input != null) {
            QCloudXml.toXml(xmlSerializer, submitVideoTagJob.input, "Input");
        }
        if (submitVideoTagJob.operation != null) {
            QCloudXml.toXml(xmlSerializer, submitVideoTagJob.operation, "Operation");
        }
        if (submitVideoTagJob.callBackFormat != null) {
            xmlSerializer.startTag("", "CallBackFormat");
            xmlSerializer.text(String.valueOf(submitVideoTagJob.callBackFormat));
            xmlSerializer.endTag("", "CallBackFormat");
        }
        if (submitVideoTagJob.callBackType != null) {
            xmlSerializer.startTag("", "CallBackType");
            xmlSerializer.text(String.valueOf(submitVideoTagJob.callBackType));
            xmlSerializer.endTag("", "CallBackType");
        }
        if (submitVideoTagJob.callBack != null) {
            xmlSerializer.startTag("", "CallBack");
            xmlSerializer.text(String.valueOf(submitVideoTagJob.callBack));
            xmlSerializer.endTag("", "CallBack");
        }
        if (submitVideoTagJob.callBackMqConfig != null) {
            QCloudXml.toXml(xmlSerializer, submitVideoTagJob.callBackMqConfig, "CallBackMqConfig");
        }
        xmlSerializer.endTag("", str);
    }
}
