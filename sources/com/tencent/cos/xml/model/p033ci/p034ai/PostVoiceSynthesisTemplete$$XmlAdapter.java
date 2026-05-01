package com.tencent.cos.xml.model.p033ci.p034ai;

import com.microsoft.azure.storage.Constants;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostVoiceSynthesisTemplete$$XmlAdapter extends IXmlAdapter<PostVoiceSynthesisTemplete> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostVoiceSynthesisTemplete postVoiceSynthesisTemplete, String str) throws XmlPullParserException, IOException {
        if (postVoiceSynthesisTemplete == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (postVoiceSynthesisTemplete.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(postVoiceSynthesisTemplete.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (postVoiceSynthesisTemplete.name != null) {
            xmlSerializer.startTag("", Constants.NAME_ELEMENT);
            xmlSerializer.text(String.valueOf(postVoiceSynthesisTemplete.name));
            xmlSerializer.endTag("", Constants.NAME_ELEMENT);
        }
        if (postVoiceSynthesisTemplete.mode != null) {
            xmlSerializer.startTag("", "Mode");
            xmlSerializer.text(String.valueOf(postVoiceSynthesisTemplete.mode));
            xmlSerializer.endTag("", "Mode");
        }
        if (postVoiceSynthesisTemplete.codec != null) {
            xmlSerializer.startTag("", "Codec");
            xmlSerializer.text(String.valueOf(postVoiceSynthesisTemplete.codec));
            xmlSerializer.endTag("", "Codec");
        }
        if (postVoiceSynthesisTemplete.voiceType != null) {
            xmlSerializer.startTag("", "VoiceType");
            xmlSerializer.text(String.valueOf(postVoiceSynthesisTemplete.voiceType));
            xmlSerializer.endTag("", "VoiceType");
        }
        if (postVoiceSynthesisTemplete.volume != null) {
            xmlSerializer.startTag("", "Volume");
            xmlSerializer.text(String.valueOf(postVoiceSynthesisTemplete.volume));
            xmlSerializer.endTag("", "Volume");
        }
        if (postVoiceSynthesisTemplete.speed != null) {
            xmlSerializer.startTag("", "Speed");
            xmlSerializer.text(String.valueOf(postVoiceSynthesisTemplete.speed));
            xmlSerializer.endTag("", "Speed");
        }
        if (postVoiceSynthesisTemplete.emotion != null) {
            xmlSerializer.startTag("", "Emotion");
            xmlSerializer.text(String.valueOf(postVoiceSynthesisTemplete.emotion));
            xmlSerializer.endTag("", "Emotion");
        }
        xmlSerializer.endTag("", str);
    }
}
