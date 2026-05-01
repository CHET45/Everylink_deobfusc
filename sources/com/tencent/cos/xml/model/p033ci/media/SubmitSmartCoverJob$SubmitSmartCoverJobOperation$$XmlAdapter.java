package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitSmartCoverJob;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitSmartCoverJob$SubmitSmartCoverJobOperation$$XmlAdapter extends IXmlAdapter<SubmitSmartCoverJob.SubmitSmartCoverJobOperation> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitSmartCoverJob.SubmitSmartCoverJobOperation submitSmartCoverJobOperation, String str) throws XmlPullParserException, IOException {
        if (submitSmartCoverJobOperation == null) {
            return;
        }
        if (str == null) {
            str = "Operation";
        }
        xmlSerializer.startTag("", str);
        if (submitSmartCoverJobOperation.templateId != null) {
            xmlSerializer.startTag("", "TemplateId");
            xmlSerializer.text(String.valueOf(submitSmartCoverJobOperation.templateId));
            xmlSerializer.endTag("", "TemplateId");
        }
        if (submitSmartCoverJobOperation.smartCover != null) {
            QCloudXml.toXml(xmlSerializer, submitSmartCoverJobOperation.smartCover, "SmartCover");
        }
        if (submitSmartCoverJobOperation.output != null) {
            QCloudXml.toXml(xmlSerializer, submitSmartCoverJobOperation.output, "Output");
        }
        if (submitSmartCoverJobOperation.userData != null) {
            xmlSerializer.startTag("", "UserData");
            xmlSerializer.text(String.valueOf(submitSmartCoverJobOperation.userData));
            xmlSerializer.endTag("", "UserData");
        }
        if (submitSmartCoverJobOperation.jobLevel != null) {
            xmlSerializer.startTag("", "JobLevel");
            xmlSerializer.text(String.valueOf(submitSmartCoverJobOperation.jobLevel));
            xmlSerializer.endTag("", "JobLevel");
        }
        xmlSerializer.endTag("", str);
    }
}
