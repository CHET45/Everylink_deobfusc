package com.tencent.cos.xml.model.p033ci.p034ai;

import com.microsoft.azure.storage.Constants;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class UpdateVoiceSynthesisTemplete$$XmlAdapter extends IXmlAdapter<UpdateVoiceSynthesisTemplete> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, UpdateVoiceSynthesisTemplete updateVoiceSynthesisTemplete, String str) throws XmlPullParserException, IOException {
        if (updateVoiceSynthesisTemplete == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (updateVoiceSynthesisTemplete.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(updateVoiceSynthesisTemplete.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (updateVoiceSynthesisTemplete.name != null) {
            xmlSerializer.startTag("", Constants.NAME_ELEMENT);
            xmlSerializer.text(String.valueOf(updateVoiceSynthesisTemplete.name));
            xmlSerializer.endTag("", Constants.NAME_ELEMENT);
        }
        if (updateVoiceSynthesisTemplete.mode != null) {
            xmlSerializer.startTag("", "Mode");
            xmlSerializer.text(String.valueOf(updateVoiceSynthesisTemplete.mode));
            xmlSerializer.endTag("", "Mode");
        }
        if (updateVoiceSynthesisTemplete.codec != null) {
            xmlSerializer.startTag("", "Codec");
            xmlSerializer.text(String.valueOf(updateVoiceSynthesisTemplete.codec));
            xmlSerializer.endTag("", "Codec");
        }
        if (updateVoiceSynthesisTemplete.voiceType != null) {
            xmlSerializer.startTag("", "VoiceType");
            xmlSerializer.text(String.valueOf(updateVoiceSynthesisTemplete.voiceType));
            xmlSerializer.endTag("", "VoiceType");
        }
        if (updateVoiceSynthesisTemplete.volume != null) {
            xmlSerializer.startTag("", "Volume");
            xmlSerializer.text(String.valueOf(updateVoiceSynthesisTemplete.volume));
            xmlSerializer.endTag("", "Volume");
        }
        if (updateVoiceSynthesisTemplete.speed != null) {
            xmlSerializer.startTag("", "Speed");
            xmlSerializer.text(String.valueOf(updateVoiceSynthesisTemplete.speed));
            xmlSerializer.endTag("", "Speed");
        }
        if (updateVoiceSynthesisTemplete.emotion != null) {
            xmlSerializer.startTag("", "Emotion");
            xmlSerializer.text(String.valueOf(updateVoiceSynthesisTemplete.emotion));
            xmlSerializer.endTag("", "Emotion");
        }
        xmlSerializer.endTag("", str);
    }
}
