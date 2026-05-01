package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitPicProcessJob$$XmlAdapter extends IXmlAdapter<SubmitPicProcessJob> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitPicProcessJob submitPicProcessJob, String str) throws XmlPullParserException, IOException {
        if (submitPicProcessJob == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (submitPicProcessJob.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(submitPicProcessJob.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (submitPicProcessJob.input != null) {
            QCloudXml.toXml(xmlSerializer, submitPicProcessJob.input, "Input");
        }
        if (submitPicProcessJob.operation != null) {
            QCloudXml.toXml(xmlSerializer, submitPicProcessJob.operation, "Operation");
        }
        if (submitPicProcessJob.callBackFormat != null) {
            xmlSerializer.startTag("", "CallBackFormat");
            xmlSerializer.text(String.valueOf(submitPicProcessJob.callBackFormat));
            xmlSerializer.endTag("", "CallBackFormat");
        }
        if (submitPicProcessJob.callBackType != null) {
            xmlSerializer.startTag("", "CallBackType");
            xmlSerializer.text(String.valueOf(submitPicProcessJob.callBackType));
            xmlSerializer.endTag("", "CallBackType");
        }
        if (submitPicProcessJob.callBack != null) {
            xmlSerializer.startTag("", "CallBack");
            xmlSerializer.text(String.valueOf(submitPicProcessJob.callBack));
            xmlSerializer.endTag("", "CallBack");
        }
        if (submitPicProcessJob.callBackMqConfig != null) {
            QCloudXml.toXml(xmlSerializer, submitPicProcessJob.callBackMqConfig, "CallBackMqConfig");
        }
        xmlSerializer.endTag("", str);
    }
}
