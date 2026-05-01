package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitVideoMontageJob;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitVideoMontageJob$SubmitVideoMontageJobOperation$$XmlAdapter extends IXmlAdapter<SubmitVideoMontageJob.SubmitVideoMontageJobOperation> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitVideoMontageJob.SubmitVideoMontageJobOperation submitVideoMontageJobOperation, String str) throws XmlPullParserException, IOException {
        if (submitVideoMontageJobOperation == null) {
            return;
        }
        if (str == null) {
            str = "Operation";
        }
        xmlSerializer.startTag("", str);
        if (submitVideoMontageJobOperation.videoMontage != null) {
            QCloudXml.toXml(xmlSerializer, submitVideoMontageJobOperation.videoMontage, "VideoMontage");
        }
        if (submitVideoMontageJobOperation.templateId != null) {
            xmlSerializer.startTag("", "TemplateId");
            xmlSerializer.text(String.valueOf(submitVideoMontageJobOperation.templateId));
            xmlSerializer.endTag("", "TemplateId");
        }
        if (submitVideoMontageJobOperation.output != null) {
            QCloudXml.toXml(xmlSerializer, submitVideoMontageJobOperation.output, "Output");
        }
        if (submitVideoMontageJobOperation.userData != null) {
            xmlSerializer.startTag("", "UserData");
            xmlSerializer.text(String.valueOf(submitVideoMontageJobOperation.userData));
            xmlSerializer.endTag("", "UserData");
        }
        if (submitVideoMontageJobOperation.jobLevel != null) {
            xmlSerializer.startTag("", "JobLevel");
            xmlSerializer.text(String.valueOf(submitVideoMontageJobOperation.jobLevel));
            xmlSerializer.endTag("", "JobLevel");
        }
        xmlSerializer.endTag("", str);
    }
}
