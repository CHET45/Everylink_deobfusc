package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.media.SubmitTranscodeJob;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitTranscodeJob$SubmitTranscodeJobSubtitle$$XmlAdapter extends IXmlAdapter<SubmitTranscodeJob.SubmitTranscodeJobSubtitle> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitTranscodeJob.SubmitTranscodeJobSubtitle submitTranscodeJobSubtitle, String str) throws XmlPullParserException, IOException {
        if (submitTranscodeJobSubtitle == null) {
            return;
        }
        if (str == null) {
            str = "Subtitle";
        }
        xmlSerializer.startTag("", str);
        if (submitTranscodeJobSubtitle.url != null) {
            xmlSerializer.startTag("", Constants.URL_ELEMENT);
            xmlSerializer.text(String.valueOf(submitTranscodeJobSubtitle.url));
            xmlSerializer.endTag("", Constants.URL_ELEMENT);
        }
        xmlSerializer.endTag("", str);
    }
}
