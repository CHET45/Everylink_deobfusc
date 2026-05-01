package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitVoiceSeparateJob;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitVoiceSeparateJob$SubmitVoiceSeparateJobInput$$XmlAdapter extends IXmlAdapter<SubmitVoiceSeparateJob.SubmitVoiceSeparateJobInput> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitVoiceSeparateJob.SubmitVoiceSeparateJobInput submitVoiceSeparateJobInput, String str) throws XmlPullParserException, IOException {
        if (submitVoiceSeparateJobInput == null) {
            return;
        }
        if (str == null) {
            str = "Input";
        }
        xmlSerializer.startTag("", str);
        if (submitVoiceSeparateJobInput.object != null) {
            xmlSerializer.startTag("", "Object");
            xmlSerializer.text(String.valueOf(submitVoiceSeparateJobInput.object));
            xmlSerializer.endTag("", "Object");
        }
        xmlSerializer.endTag("", str);
    }
}
