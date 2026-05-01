package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitVideoMontageJob$$XmlAdapter extends IXmlAdapter<SubmitVideoMontageJob> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitVideoMontageJob submitVideoMontageJob, String str) throws XmlPullParserException, IOException {
        if (submitVideoMontageJob == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (submitVideoMontageJob.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(submitVideoMontageJob.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (submitVideoMontageJob.input != null) {
            QCloudXml.toXml(xmlSerializer, submitVideoMontageJob.input, "Input");
        }
        if (submitVideoMontageJob.operation != null) {
            QCloudXml.toXml(xmlSerializer, submitVideoMontageJob.operation, "Operation");
        }
        if (submitVideoMontageJob.callBackFormat != null) {
            xmlSerializer.startTag("", "CallBackFormat");
            xmlSerializer.text(String.valueOf(submitVideoMontageJob.callBackFormat));
            xmlSerializer.endTag("", "CallBackFormat");
        }
        if (submitVideoMontageJob.callBackType != null) {
            xmlSerializer.startTag("", "CallBackType");
            xmlSerializer.text(String.valueOf(submitVideoMontageJob.callBackType));
            xmlSerializer.endTag("", "CallBackType");
        }
        if (submitVideoMontageJob.callBack != null) {
            xmlSerializer.startTag("", "CallBack");
            xmlSerializer.text(String.valueOf(submitVideoMontageJob.callBack));
            xmlSerializer.endTag("", "CallBack");
        }
        if (submitVideoMontageJob.callBackMqConfig != null) {
            QCloudXml.toXml(xmlSerializer, submitVideoMontageJob.callBackMqConfig, "CallBackMqConfig");
        }
        xmlSerializer.endTag("", str);
    }
}
