package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitAnimationJob;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitAnimationJob$SubmitAnimationJobOperation$$XmlAdapter extends IXmlAdapter<SubmitAnimationJob.SubmitAnimationJobOperation> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitAnimationJob.SubmitAnimationJobOperation submitAnimationJobOperation, String str) throws XmlPullParserException, IOException {
        if (submitAnimationJobOperation == null) {
            return;
        }
        if (str == null) {
            str = "Operation";
        }
        xmlSerializer.startTag("", str);
        if (submitAnimationJobOperation.animation != null) {
            QCloudXml.toXml(xmlSerializer, submitAnimationJobOperation.animation, "Animation");
        }
        if (submitAnimationJobOperation.templateId != null) {
            xmlSerializer.startTag("", "TemplateId");
            xmlSerializer.text(String.valueOf(submitAnimationJobOperation.templateId));
            xmlSerializer.endTag("", "TemplateId");
        }
        if (submitAnimationJobOperation.output != null) {
            QCloudXml.toXml(xmlSerializer, submitAnimationJobOperation.output, "Output");
        }
        if (submitAnimationJobOperation.userData != null) {
            xmlSerializer.startTag("", "UserData");
            xmlSerializer.text(String.valueOf(submitAnimationJobOperation.userData));
            xmlSerializer.endTag("", "UserData");
        }
        if (submitAnimationJobOperation.jobLevel != null) {
            xmlSerializer.startTag("", "JobLevel");
            xmlSerializer.text(String.valueOf(submitAnimationJobOperation.jobLevel));
            xmlSerializer.endTag("", "JobLevel");
        }
        xmlSerializer.endTag("", str);
    }
}
