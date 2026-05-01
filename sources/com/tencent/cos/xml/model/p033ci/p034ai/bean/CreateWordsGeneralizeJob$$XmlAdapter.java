package com.tencent.cos.xml.model.p033ci.p034ai.bean;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class CreateWordsGeneralizeJob$$XmlAdapter extends IXmlAdapter<CreateWordsGeneralizeJob> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, CreateWordsGeneralizeJob createWordsGeneralizeJob, String str) throws XmlPullParserException, IOException {
        if (createWordsGeneralizeJob == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (createWordsGeneralizeJob.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(createWordsGeneralizeJob.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (createWordsGeneralizeJob.input != null) {
            QCloudXml.toXml(xmlSerializer, createWordsGeneralizeJob.input, "Input");
        }
        if (createWordsGeneralizeJob.operation != null) {
            QCloudXml.toXml(xmlSerializer, createWordsGeneralizeJob.operation, "Operation");
        }
        if (createWordsGeneralizeJob.queueId != null) {
            xmlSerializer.startTag("", "QueueId");
            xmlSerializer.text(String.valueOf(createWordsGeneralizeJob.queueId));
            xmlSerializer.endTag("", "QueueId");
        }
        if (createWordsGeneralizeJob.callBack != null) {
            xmlSerializer.startTag("", "CallBack");
            xmlSerializer.text(String.valueOf(createWordsGeneralizeJob.callBack));
            xmlSerializer.endTag("", "CallBack");
        }
        if (createWordsGeneralizeJob.callBackFormat != null) {
            xmlSerializer.startTag("", "CallBackFormat");
            xmlSerializer.text(String.valueOf(createWordsGeneralizeJob.callBackFormat));
            xmlSerializer.endTag("", "CallBackFormat");
        }
        xmlSerializer.endTag("", str);
    }
}
