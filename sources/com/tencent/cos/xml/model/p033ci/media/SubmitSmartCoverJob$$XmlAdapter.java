package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitSmartCoverJob$$XmlAdapter extends IXmlAdapter<SubmitSmartCoverJob> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitSmartCoverJob submitSmartCoverJob, String str) throws XmlPullParserException, IOException {
        if (submitSmartCoverJob == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (submitSmartCoverJob.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(submitSmartCoverJob.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (submitSmartCoverJob.input != null) {
            QCloudXml.toXml(xmlSerializer, submitSmartCoverJob.input, "Input");
        }
        if (submitSmartCoverJob.operation != null) {
            QCloudXml.toXml(xmlSerializer, submitSmartCoverJob.operation, "Operation");
        }
        if (submitSmartCoverJob.callBackFormat != null) {
            xmlSerializer.startTag("", "CallBackFormat");
            xmlSerializer.text(String.valueOf(submitSmartCoverJob.callBackFormat));
            xmlSerializer.endTag("", "CallBackFormat");
        }
        if (submitSmartCoverJob.callBackType != null) {
            xmlSerializer.startTag("", "CallBackType");
            xmlSerializer.text(String.valueOf(submitSmartCoverJob.callBackType));
            xmlSerializer.endTag("", "CallBackType");
        }
        if (submitSmartCoverJob.callBack != null) {
            xmlSerializer.startTag("", "CallBack");
            xmlSerializer.text(String.valueOf(submitSmartCoverJob.callBack));
            xmlSerializer.endTag("", "CallBack");
        }
        if (submitSmartCoverJob.callBackMqConfig != null) {
            QCloudXml.toXml(xmlSerializer, submitSmartCoverJob.callBackMqConfig, "CallBackMqConfig");
        }
        xmlSerializer.endTag("", str);
    }
}
