package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.PostNoiseReduction;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostNoiseReduction$PostNoiseReductionOperation$$XmlAdapter extends IXmlAdapter<PostNoiseReduction.PostNoiseReductionOperation> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostNoiseReduction.PostNoiseReductionOperation postNoiseReductionOperation, String str) throws XmlPullParserException, IOException {
        if (postNoiseReductionOperation == null) {
            return;
        }
        if (str == null) {
            str = "Operation";
        }
        xmlSerializer.startTag("", str);
        if (postNoiseReductionOperation.templateId != null) {
            xmlSerializer.startTag("", "TemplateId");
            xmlSerializer.text(String.valueOf(postNoiseReductionOperation.templateId));
            xmlSerializer.endTag("", "TemplateId");
        }
        if (postNoiseReductionOperation.noiseReduction != null) {
            QCloudXml.toXml(xmlSerializer, postNoiseReductionOperation.noiseReduction, "NoiseReduction");
        }
        if (postNoiseReductionOperation.jobLevel != null) {
            xmlSerializer.startTag("", "JobLevel");
            xmlSerializer.text(String.valueOf(postNoiseReductionOperation.jobLevel));
            xmlSerializer.endTag("", "JobLevel");
        }
        if (postNoiseReductionOperation.userData != null) {
            xmlSerializer.startTag("", "UserData");
            xmlSerializer.text(String.valueOf(postNoiseReductionOperation.userData));
            xmlSerializer.endTag("", "UserData");
        }
        if (postNoiseReductionOperation.output != null) {
            QCloudXml.toXml(xmlSerializer, postNoiseReductionOperation.output, "Output");
        }
        xmlSerializer.endTag("", str);
    }
}
