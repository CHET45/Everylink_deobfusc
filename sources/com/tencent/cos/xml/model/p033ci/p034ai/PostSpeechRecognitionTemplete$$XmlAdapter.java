package com.tencent.cos.xml.model.p033ci.p034ai;

import com.microsoft.azure.storage.Constants;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostSpeechRecognitionTemplete$$XmlAdapter extends IXmlAdapter<PostSpeechRecognitionTemplete> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostSpeechRecognitionTemplete postSpeechRecognitionTemplete, String str) throws XmlPullParserException, IOException {
        if (postSpeechRecognitionTemplete == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (postSpeechRecognitionTemplete.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(postSpeechRecognitionTemplete.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (postSpeechRecognitionTemplete.name != null) {
            xmlSerializer.startTag("", Constants.NAME_ELEMENT);
            xmlSerializer.text(String.valueOf(postSpeechRecognitionTemplete.name));
            xmlSerializer.endTag("", Constants.NAME_ELEMENT);
        }
        if (postSpeechRecognitionTemplete.speechRecognition != null) {
            QCloudXml.toXml(xmlSerializer, postSpeechRecognitionTemplete.speechRecognition, "SpeechRecognition");
        }
        xmlSerializer.endTag("", str);
    }
}
