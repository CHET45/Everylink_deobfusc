package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.media.SubmitExtractDigitalWatermarkJob;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitExtractDigitalWatermarkJob$SubmitExtractDigitalWatermarkJobExtractDigitalWatermark$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3564x29e8c061 extends IXmlAdapter<SubmitExtractDigitalWatermarkJob.SubmitExtractDigitalWatermarkJobExtractDigitalWatermark> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitExtractDigitalWatermarkJob.SubmitExtractDigitalWatermarkJobExtractDigitalWatermark submitExtractDigitalWatermarkJobExtractDigitalWatermark, String str) throws XmlPullParserException, IOException {
        if (submitExtractDigitalWatermarkJobExtractDigitalWatermark == null) {
            return;
        }
        if (str == null) {
            str = "ExtractDigitalWatermark";
        }
        xmlSerializer.startTag("", str);
        if (submitExtractDigitalWatermarkJobExtractDigitalWatermark.type != null) {
            xmlSerializer.startTag("", "Type");
            xmlSerializer.text(String.valueOf(submitExtractDigitalWatermarkJobExtractDigitalWatermark.type));
            xmlSerializer.endTag("", "Type");
        }
        if (submitExtractDigitalWatermarkJobExtractDigitalWatermark.version != null) {
            xmlSerializer.startTag("", Constants.AnalyticsConstants.VERSION_ELEMENT);
            xmlSerializer.text(String.valueOf(submitExtractDigitalWatermarkJobExtractDigitalWatermark.version));
            xmlSerializer.endTag("", Constants.AnalyticsConstants.VERSION_ELEMENT);
        }
        xmlSerializer.endTag("", str);
    }
}
