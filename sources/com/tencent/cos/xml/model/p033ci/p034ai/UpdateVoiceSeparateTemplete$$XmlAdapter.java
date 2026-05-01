package com.tencent.cos.xml.model.p033ci.p034ai;

import com.microsoft.azure.storage.Constants;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class UpdateVoiceSeparateTemplete$$XmlAdapter extends IXmlAdapter<UpdateVoiceSeparateTemplete> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, UpdateVoiceSeparateTemplete updateVoiceSeparateTemplete, String str) throws XmlPullParserException, IOException {
        if (updateVoiceSeparateTemplete == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (updateVoiceSeparateTemplete.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(updateVoiceSeparateTemplete.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (updateVoiceSeparateTemplete.name != null) {
            xmlSerializer.startTag("", Constants.NAME_ELEMENT);
            xmlSerializer.text(String.valueOf(updateVoiceSeparateTemplete.name));
            xmlSerializer.endTag("", Constants.NAME_ELEMENT);
        }
        if (updateVoiceSeparateTemplete.audioMode != null) {
            xmlSerializer.startTag("", "AudioMode");
            xmlSerializer.text(String.valueOf(updateVoiceSeparateTemplete.audioMode));
            xmlSerializer.endTag("", "AudioMode");
        }
        if (updateVoiceSeparateTemplete.audioConfig != null) {
            QCloudXml.toXml(xmlSerializer, updateVoiceSeparateTemplete.audioConfig, "AudioConfig");
        }
        xmlSerializer.endTag("", str);
    }
}
