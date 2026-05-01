package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitMediaSegmentJob$$XmlAdapter extends IXmlAdapter<SubmitMediaSegmentJob> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitMediaSegmentJob submitMediaSegmentJob, String str) throws XmlPullParserException, IOException {
        if (submitMediaSegmentJob == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (submitMediaSegmentJob.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(submitMediaSegmentJob.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (submitMediaSegmentJob.input != null) {
            QCloudXml.toXml(xmlSerializer, submitMediaSegmentJob.input, "Input");
        }
        if (submitMediaSegmentJob.operation != null) {
            QCloudXml.toXml(xmlSerializer, submitMediaSegmentJob.operation, "Operation");
        }
        if (submitMediaSegmentJob.callBackFormat != null) {
            xmlSerializer.startTag("", "CallBackFormat");
            xmlSerializer.text(String.valueOf(submitMediaSegmentJob.callBackFormat));
            xmlSerializer.endTag("", "CallBackFormat");
        }
        if (submitMediaSegmentJob.callBackType != null) {
            xmlSerializer.startTag("", "CallBackType");
            xmlSerializer.text(String.valueOf(submitMediaSegmentJob.callBackType));
            xmlSerializer.endTag("", "CallBackType");
        }
        if (submitMediaSegmentJob.callBack != null) {
            xmlSerializer.startTag("", "CallBack");
            xmlSerializer.text(String.valueOf(submitMediaSegmentJob.callBack));
            xmlSerializer.endTag("", "CallBack");
        }
        if (submitMediaSegmentJob.callBackMqConfig != null) {
            QCloudXml.toXml(xmlSerializer, submitMediaSegmentJob.callBackMqConfig, "CallBackMqConfig");
        }
        xmlSerializer.endTag("", str);
    }
}
