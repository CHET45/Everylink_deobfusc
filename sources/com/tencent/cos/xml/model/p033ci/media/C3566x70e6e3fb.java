package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitExtractDigitalWatermarkJob;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitExtractDigitalWatermarkJob$SubmitExtractDigitalWatermarkJobOperation$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3566x70e6e3fb extends IXmlAdapter<SubmitExtractDigitalWatermarkJob.SubmitExtractDigitalWatermarkJobOperation> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitExtractDigitalWatermarkJob.SubmitExtractDigitalWatermarkJobOperation submitExtractDigitalWatermarkJobOperation, String str) throws XmlPullParserException, IOException {
        if (submitExtractDigitalWatermarkJobOperation == null) {
            return;
        }
        if (str == null) {
            str = "Operation";
        }
        xmlSerializer.startTag("", str);
        if (submitExtractDigitalWatermarkJobOperation.extractDigitalWatermark != null) {
            QCloudXml.toXml(xmlSerializer, submitExtractDigitalWatermarkJobOperation.extractDigitalWatermark, "ExtractDigitalWatermark");
        }
        if (submitExtractDigitalWatermarkJobOperation.jobLevel != null) {
            xmlSerializer.startTag("", "JobLevel");
            xmlSerializer.text(String.valueOf(submitExtractDigitalWatermarkJobOperation.jobLevel));
            xmlSerializer.endTag("", "JobLevel");
        }
        if (submitExtractDigitalWatermarkJobOperation.userData != null) {
            xmlSerializer.startTag("", "UserData");
            xmlSerializer.text(String.valueOf(submitExtractDigitalWatermarkJobOperation.userData));
            xmlSerializer.endTag("", "UserData");
        }
        xmlSerializer.endTag("", str);
    }
}
