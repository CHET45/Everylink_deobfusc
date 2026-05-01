package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitDigitalWatermarkJob;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitDigitalWatermarkJob$SubmitDigitalWatermarkJobInput$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3557xc2dfff4 extends IXmlAdapter<SubmitDigitalWatermarkJob.SubmitDigitalWatermarkJobInput> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitDigitalWatermarkJob.SubmitDigitalWatermarkJobInput submitDigitalWatermarkJobInput, String str) throws XmlPullParserException, IOException {
        if (submitDigitalWatermarkJobInput == null) {
            return;
        }
        if (str == null) {
            str = "Input";
        }
        xmlSerializer.startTag("", str);
        if (submitDigitalWatermarkJobInput.object != null) {
            xmlSerializer.startTag("", "Object");
            xmlSerializer.text(String.valueOf(submitDigitalWatermarkJobInput.object));
            xmlSerializer.endTag("", "Object");
        }
        xmlSerializer.endTag("", str);
    }
}
