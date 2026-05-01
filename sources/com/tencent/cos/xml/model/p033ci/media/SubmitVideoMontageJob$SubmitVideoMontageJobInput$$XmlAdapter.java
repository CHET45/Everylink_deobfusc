package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.media.SubmitVideoMontageJob;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitVideoMontageJob$SubmitVideoMontageJobInput$$XmlAdapter extends IXmlAdapter<SubmitVideoMontageJob.SubmitVideoMontageJobInput> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitVideoMontageJob.SubmitVideoMontageJobInput submitVideoMontageJobInput, String str) throws XmlPullParserException, IOException {
        if (submitVideoMontageJobInput == null) {
            return;
        }
        if (str == null) {
            str = "Input";
        }
        xmlSerializer.startTag("", str);
        if (submitVideoMontageJobInput.object != null) {
            xmlSerializer.startTag("", "Object");
            xmlSerializer.text(String.valueOf(submitVideoMontageJobInput.object));
            xmlSerializer.endTag("", "Object");
        }
        if (submitVideoMontageJobInput.url != null) {
            xmlSerializer.startTag("", Constants.URL_ELEMENT);
            xmlSerializer.text(String.valueOf(submitVideoMontageJobInput.url));
            xmlSerializer.endTag("", Constants.URL_ELEMENT);
        }
        xmlSerializer.endTag("", str);
    }
}
