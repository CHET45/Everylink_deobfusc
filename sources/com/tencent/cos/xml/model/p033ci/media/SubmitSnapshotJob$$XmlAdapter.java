package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitSnapshotJob$$XmlAdapter extends IXmlAdapter<SubmitSnapshotJob> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitSnapshotJob submitSnapshotJob, String str) throws XmlPullParserException, IOException {
        if (submitSnapshotJob == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (submitSnapshotJob.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(submitSnapshotJob.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (submitSnapshotJob.input != null) {
            QCloudXml.toXml(xmlSerializer, submitSnapshotJob.input, "Input");
        }
        if (submitSnapshotJob.operation != null) {
            QCloudXml.toXml(xmlSerializer, submitSnapshotJob.operation, "Operation");
        }
        if (submitSnapshotJob.callBackFormat != null) {
            xmlSerializer.startTag("", "CallBackFormat");
            xmlSerializer.text(String.valueOf(submitSnapshotJob.callBackFormat));
            xmlSerializer.endTag("", "CallBackFormat");
        }
        if (submitSnapshotJob.callBackType != null) {
            xmlSerializer.startTag("", "CallBackType");
            xmlSerializer.text(String.valueOf(submitSnapshotJob.callBackType));
            xmlSerializer.endTag("", "CallBackType");
        }
        if (submitSnapshotJob.callBack != null) {
            xmlSerializer.startTag("", "CallBack");
            xmlSerializer.text(String.valueOf(submitSnapshotJob.callBack));
            xmlSerializer.endTag("", "CallBack");
        }
        if (submitSnapshotJob.callBackMqConfig != null) {
            QCloudXml.toXml(xmlSerializer, submitSnapshotJob.callBackMqConfig, "CallBackMqConfig");
        }
        xmlSerializer.endTag("", str);
    }
}
