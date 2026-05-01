package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitAnimationJob$$XmlAdapter extends IXmlAdapter<SubmitAnimationJob> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitAnimationJob submitAnimationJob, String str) throws XmlPullParserException, IOException {
        if (submitAnimationJob == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (submitAnimationJob.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(submitAnimationJob.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (submitAnimationJob.input != null) {
            QCloudXml.toXml(xmlSerializer, submitAnimationJob.input, "Input");
        }
        if (submitAnimationJob.operation != null) {
            QCloudXml.toXml(xmlSerializer, submitAnimationJob.operation, "Operation");
        }
        if (submitAnimationJob.callBackFormat != null) {
            xmlSerializer.startTag("", "CallBackFormat");
            xmlSerializer.text(String.valueOf(submitAnimationJob.callBackFormat));
            xmlSerializer.endTag("", "CallBackFormat");
        }
        if (submitAnimationJob.callBackType != null) {
            xmlSerializer.startTag("", "CallBackType");
            xmlSerializer.text(String.valueOf(submitAnimationJob.callBackType));
            xmlSerializer.endTag("", "CallBackType");
        }
        if (submitAnimationJob.callBack != null) {
            xmlSerializer.startTag("", "CallBack");
            xmlSerializer.text(String.valueOf(submitAnimationJob.callBack));
            xmlSerializer.endTag("", "CallBack");
        }
        if (submitAnimationJob.callBackMqConfig != null) {
            QCloudXml.toXml(xmlSerializer, submitAnimationJob.callBackMqConfig, "CallBackMqConfig");
        }
        xmlSerializer.endTag("", str);
    }
}
