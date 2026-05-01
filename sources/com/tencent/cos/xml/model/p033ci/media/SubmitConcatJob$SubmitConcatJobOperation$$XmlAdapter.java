package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitConcatJob;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitConcatJob$SubmitConcatJobOperation$$XmlAdapter extends IXmlAdapter<SubmitConcatJob.SubmitConcatJobOperation> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitConcatJob.SubmitConcatJobOperation submitConcatJobOperation, String str) throws XmlPullParserException, IOException {
        if (submitConcatJobOperation == null) {
            return;
        }
        if (str == null) {
            str = "Operation";
        }
        xmlSerializer.startTag("", str);
        if (submitConcatJobOperation.concatTemplate != null) {
            QCloudXml.toXml(xmlSerializer, submitConcatJobOperation.concatTemplate, "ConcatTemplate");
        }
        if (submitConcatJobOperation.templateId != null) {
            xmlSerializer.startTag("", "TemplateId");
            xmlSerializer.text(String.valueOf(submitConcatJobOperation.templateId));
            xmlSerializer.endTag("", "TemplateId");
        }
        if (submitConcatJobOperation.output != null) {
            QCloudXml.toXml(xmlSerializer, submitConcatJobOperation.output, "Output");
        }
        if (submitConcatJobOperation.jobLevel != null) {
            xmlSerializer.startTag("", "JobLevel");
            xmlSerializer.text(String.valueOf(submitConcatJobOperation.jobLevel));
            xmlSerializer.endTag("", "JobLevel");
        }
        xmlSerializer.endTag("", str);
    }
}
