package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitTranscodeJob;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitTranscodeJob$SubmitTranscodeJobSubtitles$$XmlAdapter extends IXmlAdapter<SubmitTranscodeJob.SubmitTranscodeJobSubtitles> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitTranscodeJob.SubmitTranscodeJobSubtitles submitTranscodeJobSubtitles, String str) throws XmlPullParserException, IOException {
        if (submitTranscodeJobSubtitles == null) {
            return;
        }
        if (str == null) {
            str = "Subtitles";
        }
        xmlSerializer.startTag("", str);
        if (submitTranscodeJobSubtitles.subtitle != null) {
            for (int i = 0; i < submitTranscodeJobSubtitles.subtitle.size(); i++) {
                QCloudXml.toXml(xmlSerializer, submitTranscodeJobSubtitles.subtitle.get(i), "Subtitle");
            }
        }
        xmlSerializer.endTag("", str);
    }
}
