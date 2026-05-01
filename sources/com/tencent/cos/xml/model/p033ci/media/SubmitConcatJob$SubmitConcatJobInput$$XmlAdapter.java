package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitConcatJob;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitConcatJob$SubmitConcatJobInput$$XmlAdapter extends IXmlAdapter<SubmitConcatJob.SubmitConcatJobInput> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitConcatJob.SubmitConcatJobInput submitConcatJobInput, String str) throws XmlPullParserException, IOException {
        if (submitConcatJobInput == null) {
            return;
        }
        if (str == null) {
            str = "Input";
        }
        xmlSerializer.startTag("", str);
        if (submitConcatJobInput.object != null) {
            xmlSerializer.startTag("", "Object");
            xmlSerializer.text(String.valueOf(submitConcatJobInput.object));
            xmlSerializer.endTag("", "Object");
        }
        xmlSerializer.endTag("", str);
    }
}
