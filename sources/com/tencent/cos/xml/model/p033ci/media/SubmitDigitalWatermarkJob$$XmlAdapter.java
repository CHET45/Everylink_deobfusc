package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitDigitalWatermarkJob$$XmlAdapter extends IXmlAdapter<SubmitDigitalWatermarkJob> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitDigitalWatermarkJob submitDigitalWatermarkJob, String str) throws XmlPullParserException, IOException {
        if (submitDigitalWatermarkJob == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (submitDigitalWatermarkJob.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(submitDigitalWatermarkJob.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (submitDigitalWatermarkJob.input != null) {
            QCloudXml.toXml(xmlSerializer, submitDigitalWatermarkJob.input, "Input");
        }
        if (submitDigitalWatermarkJob.operation != null) {
            QCloudXml.toXml(xmlSerializer, submitDigitalWatermarkJob.operation, "Operation");
        }
        if (submitDigitalWatermarkJob.callBackFormat != null) {
            xmlSerializer.startTag("", "CallBackFormat");
            xmlSerializer.text(String.valueOf(submitDigitalWatermarkJob.callBackFormat));
            xmlSerializer.endTag("", "CallBackFormat");
        }
        if (submitDigitalWatermarkJob.callBackType != null) {
            xmlSerializer.startTag("", "CallBackType");
            xmlSerializer.text(String.valueOf(submitDigitalWatermarkJob.callBackType));
            xmlSerializer.endTag("", "CallBackType");
        }
        if (submitDigitalWatermarkJob.callBack != null) {
            xmlSerializer.startTag("", "CallBack");
            xmlSerializer.text(String.valueOf(submitDigitalWatermarkJob.callBack));
            xmlSerializer.endTag("", "CallBack");
        }
        if (submitDigitalWatermarkJob.callBackMqConfig != null) {
            QCloudXml.toXml(xmlSerializer, submitDigitalWatermarkJob.callBackMqConfig, "CallBackMqConfig");
        }
        xmlSerializer.endTag("", str);
    }
}
