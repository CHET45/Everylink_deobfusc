package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitExtractDigitalWatermarkJob$$XmlAdapter extends IXmlAdapter<SubmitExtractDigitalWatermarkJob> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitExtractDigitalWatermarkJob submitExtractDigitalWatermarkJob, String str) throws XmlPullParserException, IOException {
        if (submitExtractDigitalWatermarkJob == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (submitExtractDigitalWatermarkJob.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(submitExtractDigitalWatermarkJob.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (submitExtractDigitalWatermarkJob.input != null) {
            QCloudXml.toXml(xmlSerializer, submitExtractDigitalWatermarkJob.input, "Input");
        }
        if (submitExtractDigitalWatermarkJob.operation != null) {
            QCloudXml.toXml(xmlSerializer, submitExtractDigitalWatermarkJob.operation, "Operation");
        }
        if (submitExtractDigitalWatermarkJob.callBackFormat != null) {
            xmlSerializer.startTag("", "CallBackFormat");
            xmlSerializer.text(String.valueOf(submitExtractDigitalWatermarkJob.callBackFormat));
            xmlSerializer.endTag("", "CallBackFormat");
        }
        if (submitExtractDigitalWatermarkJob.callBackType != null) {
            xmlSerializer.startTag("", "CallBackType");
            xmlSerializer.text(String.valueOf(submitExtractDigitalWatermarkJob.callBackType));
            xmlSerializer.endTag("", "CallBackType");
        }
        if (submitExtractDigitalWatermarkJob.callBack != null) {
            xmlSerializer.startTag("", "CallBack");
            xmlSerializer.text(String.valueOf(submitExtractDigitalWatermarkJob.callBack));
            xmlSerializer.endTag("", "CallBack");
        }
        if (submitExtractDigitalWatermarkJob.callBackMqConfig != null) {
            QCloudXml.toXml(xmlSerializer, submitExtractDigitalWatermarkJob.callBackMqConfig, "CallBackMqConfig");
        }
        xmlSerializer.endTag("", str);
    }
}
