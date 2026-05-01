package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.blob.BlobConstants;
import com.tencent.cos.xml.model.p033ci.media.SubmitSnapshotJob;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitSnapshotJob$SubmitSnapshotJobOperation$$XmlAdapter extends IXmlAdapter<SubmitSnapshotJob.SubmitSnapshotJobOperation> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitSnapshotJob.SubmitSnapshotJobOperation submitSnapshotJobOperation, String str) throws XmlPullParserException, IOException {
        if (submitSnapshotJobOperation == null) {
            return;
        }
        if (str == null) {
            str = "Operation";
        }
        xmlSerializer.startTag("", str);
        if (submitSnapshotJobOperation.snapshot != null) {
            QCloudXml.toXml(xmlSerializer, submitSnapshotJobOperation.snapshot, BlobConstants.SNAPSHOT_ELEMENT);
        }
        if (submitSnapshotJobOperation.templateId != null) {
            xmlSerializer.startTag("", "TemplateId");
            xmlSerializer.text(String.valueOf(submitSnapshotJobOperation.templateId));
            xmlSerializer.endTag("", "TemplateId");
        }
        if (submitSnapshotJobOperation.output != null) {
            QCloudXml.toXml(xmlSerializer, submitSnapshotJobOperation.output, "Output");
        }
        if (submitSnapshotJobOperation.userData != null) {
            xmlSerializer.startTag("", "UserData");
            xmlSerializer.text(String.valueOf(submitSnapshotJobOperation.userData));
            xmlSerializer.endTag("", "UserData");
        }
        if (submitSnapshotJobOperation.jobLevel != null) {
            xmlSerializer.startTag("", "JobLevel");
            xmlSerializer.text(String.valueOf(submitSnapshotJobOperation.jobLevel));
            xmlSerializer.endTag("", "JobLevel");
        }
        xmlSerializer.endTag("", str);
    }
}
