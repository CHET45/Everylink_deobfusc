package com.tencent.cos.xml.model.p033ci.asr.bean;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.asr.bean.CreateSpeechJobs;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class CreateSpeechJobs$CreateSpeechJobsInput$$XmlAdapter extends IXmlAdapter<CreateSpeechJobs.CreateSpeechJobsInput> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, CreateSpeechJobs.CreateSpeechJobsInput createSpeechJobsInput, String str) throws XmlPullParserException, IOException {
        if (createSpeechJobsInput == null) {
            return;
        }
        if (str == null) {
            str = "CreateSpeechJobsInput";
        }
        xmlSerializer.startTag("", str);
        if (createSpeechJobsInput.object != null) {
            xmlSerializer.startTag("", "Object");
            xmlSerializer.text(String.valueOf(createSpeechJobsInput.object));
            xmlSerializer.endTag("", "Object");
        }
        if (createSpeechJobsInput.url != null) {
            xmlSerializer.startTag("", Constants.URL_ELEMENT);
            xmlSerializer.text(String.valueOf(createSpeechJobsInput.url));
            xmlSerializer.endTag("", Constants.URL_ELEMENT);
        }
        xmlSerializer.endTag("", str);
    }
}
