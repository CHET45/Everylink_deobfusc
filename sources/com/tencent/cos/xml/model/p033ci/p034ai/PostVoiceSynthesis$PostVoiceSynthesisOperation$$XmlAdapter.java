package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.PostVoiceSynthesis;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostVoiceSynthesis$PostVoiceSynthesisOperation$$XmlAdapter extends IXmlAdapter<PostVoiceSynthesis.PostVoiceSynthesisOperation> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostVoiceSynthesis.PostVoiceSynthesisOperation postVoiceSynthesisOperation, String str) throws XmlPullParserException, IOException {
        if (postVoiceSynthesisOperation == null) {
            return;
        }
        if (str == null) {
            str = "Operation";
        }
        xmlSerializer.startTag("", str);
        if (postVoiceSynthesisOperation.templateId != null) {
            xmlSerializer.startTag("", "TemplateId");
            xmlSerializer.text(String.valueOf(postVoiceSynthesisOperation.templateId));
            xmlSerializer.endTag("", "TemplateId");
        }
        if (postVoiceSynthesisOperation.ttsTpl != null) {
            QCloudXml.toXml(xmlSerializer, postVoiceSynthesisOperation.ttsTpl, "TtsTpl");
        }
        if (postVoiceSynthesisOperation.ttsConfig != null) {
            QCloudXml.toXml(xmlSerializer, postVoiceSynthesisOperation.ttsConfig, "TtsConfig");
        }
        if (postVoiceSynthesisOperation.output != null) {
            QCloudXml.toXml(xmlSerializer, postVoiceSynthesisOperation.output, "Output");
        }
        if (postVoiceSynthesisOperation.userData != null) {
            xmlSerializer.startTag("", "UserData");
            xmlSerializer.text(String.valueOf(postVoiceSynthesisOperation.userData));
            xmlSerializer.endTag("", "UserData");
        }
        if (postVoiceSynthesisOperation.jobLevel != null) {
            xmlSerializer.startTag("", "JobLevel");
            xmlSerializer.text(String.valueOf(postVoiceSynthesisOperation.jobLevel));
            xmlSerializer.endTag("", "JobLevel");
        }
        xmlSerializer.endTag("", str);
    }
}
