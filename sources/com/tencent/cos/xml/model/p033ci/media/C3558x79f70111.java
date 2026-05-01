package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitDigitalWatermarkJob;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitDigitalWatermarkJob$SubmitDigitalWatermarkJobOperation$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3558x79f70111 extends IXmlAdapter<SubmitDigitalWatermarkJob.SubmitDigitalWatermarkJobOperation> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitDigitalWatermarkJob.SubmitDigitalWatermarkJobOperation submitDigitalWatermarkJobOperation, String str) throws XmlPullParserException, IOException {
        if (submitDigitalWatermarkJobOperation == null) {
            return;
        }
        if (str == null) {
            str = "Operation";
        }
        xmlSerializer.startTag("", str);
        if (submitDigitalWatermarkJobOperation.output != null) {
            QCloudXml.toXml(xmlSerializer, submitDigitalWatermarkJobOperation.output, "Output");
        }
        if (submitDigitalWatermarkJobOperation.digitalWatermark != null) {
            QCloudXml.toXml(xmlSerializer, submitDigitalWatermarkJobOperation.digitalWatermark, "DigitalWatermark");
        }
        if (submitDigitalWatermarkJobOperation.jobLevel != null) {
            xmlSerializer.startTag("", "JobLevel");
            xmlSerializer.text(String.valueOf(submitDigitalWatermarkJobOperation.jobLevel));
            xmlSerializer.endTag("", "JobLevel");
        }
        if (submitDigitalWatermarkJobOperation.userData != null) {
            xmlSerializer.startTag("", "UserData");
            xmlSerializer.text(String.valueOf(submitDigitalWatermarkJobOperation.userData));
            xmlSerializer.endTag("", "UserData");
        }
        xmlSerializer.endTag("", str);
    }
}
