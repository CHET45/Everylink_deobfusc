package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitExtractDigitalWatermarkJob;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitExtractDigitalWatermarkJob$SubmitExtractDigitalWatermarkJobInput$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3565x9387e7de extends IXmlAdapter<SubmitExtractDigitalWatermarkJob.SubmitExtractDigitalWatermarkJobInput> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitExtractDigitalWatermarkJob.SubmitExtractDigitalWatermarkJobInput submitExtractDigitalWatermarkJobInput, String str) throws XmlPullParserException, IOException {
        if (submitExtractDigitalWatermarkJobInput == null) {
            return;
        }
        if (str == null) {
            str = "Input";
        }
        xmlSerializer.startTag("", str);
        if (submitExtractDigitalWatermarkJobInput.object != null) {
            xmlSerializer.startTag("", "Object");
            xmlSerializer.text(String.valueOf(submitExtractDigitalWatermarkJobInput.object));
            xmlSerializer.endTag("", "Object");
        }
        xmlSerializer.endTag("", str);
    }
}
