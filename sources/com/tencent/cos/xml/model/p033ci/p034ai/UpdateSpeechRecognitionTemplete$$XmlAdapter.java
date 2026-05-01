package com.tencent.cos.xml.model.p033ci.p034ai;

import com.microsoft.azure.storage.Constants;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class UpdateSpeechRecognitionTemplete$$XmlAdapter extends IXmlAdapter<UpdateSpeechRecognitionTemplete> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, UpdateSpeechRecognitionTemplete updateSpeechRecognitionTemplete, String str) throws XmlPullParserException, IOException {
        if (updateSpeechRecognitionTemplete == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (updateSpeechRecognitionTemplete.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(updateSpeechRecognitionTemplete.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (updateSpeechRecognitionTemplete.name != null) {
            xmlSerializer.startTag("", Constants.NAME_ELEMENT);
            xmlSerializer.text(String.valueOf(updateSpeechRecognitionTemplete.name));
            xmlSerializer.endTag("", Constants.NAME_ELEMENT);
        }
        if (updateSpeechRecognitionTemplete.speechRecognition != null) {
            QCloudXml.toXml(xmlSerializer, updateSpeechRecognitionTemplete.speechRecognition, "SpeechRecognition");
        }
        xmlSerializer.endTag("", str);
    }
}
