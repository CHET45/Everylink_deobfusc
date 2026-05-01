package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitPicProcessJob;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitPicProcessJob$SubmitPicProcessJobOperation$$XmlAdapter extends IXmlAdapter<SubmitPicProcessJob.SubmitPicProcessJobOperation> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitPicProcessJob.SubmitPicProcessJobOperation submitPicProcessJobOperation, String str) throws XmlPullParserException, IOException {
        if (submitPicProcessJobOperation == null) {
            return;
        }
        if (str == null) {
            str = "Operation";
        }
        xmlSerializer.startTag("", str);
        if (submitPicProcessJobOperation.templateId != null) {
            xmlSerializer.startTag("", "TemplateId");
            xmlSerializer.text(String.valueOf(submitPicProcessJobOperation.templateId));
            xmlSerializer.endTag("", "TemplateId");
        }
        if (submitPicProcessJobOperation.picProcess != null) {
            QCloudXml.toXml(xmlSerializer, submitPicProcessJobOperation.picProcess, "PicProcess");
        }
        if (submitPicProcessJobOperation.output != null) {
            QCloudXml.toXml(xmlSerializer, submitPicProcessJobOperation.output, "Output");
        }
        if (submitPicProcessJobOperation.userData != null) {
            xmlSerializer.startTag("", "UserData");
            xmlSerializer.text(String.valueOf(submitPicProcessJobOperation.userData));
            xmlSerializer.endTag("", "UserData");
        }
        if (submitPicProcessJobOperation.jobLevel != null) {
            xmlSerializer.startTag("", "JobLevel");
            xmlSerializer.text(String.valueOf(submitPicProcessJobOperation.jobLevel));
            xmlSerializer.endTag("", "JobLevel");
        }
        xmlSerializer.endTag("", str);
    }
}
