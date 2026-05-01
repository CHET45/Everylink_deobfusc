package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostSoundHound$$XmlAdapter extends IXmlAdapter<PostSoundHound> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostSoundHound postSoundHound, String str) throws XmlPullParserException, IOException {
        if (postSoundHound == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (postSoundHound.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(postSoundHound.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (postSoundHound.input != null) {
            QCloudXml.toXml(xmlSerializer, postSoundHound.input, "Input");
        }
        if (postSoundHound.operation != null) {
            QCloudXml.toXml(xmlSerializer, postSoundHound.operation, "Operation");
        }
        if (postSoundHound.callBackFormat != null) {
            xmlSerializer.startTag("", "CallBackFormat");
            xmlSerializer.text(String.valueOf(postSoundHound.callBackFormat));
            xmlSerializer.endTag("", "CallBackFormat");
        }
        if (postSoundHound.callBackType != null) {
            xmlSerializer.startTag("", "CallBackType");
            xmlSerializer.text(String.valueOf(postSoundHound.callBackType));
            xmlSerializer.endTag("", "CallBackType");
        }
        if (postSoundHound.callBack != null) {
            xmlSerializer.startTag("", "CallBack");
            xmlSerializer.text(String.valueOf(postSoundHound.callBack));
            xmlSerializer.endTag("", "CallBack");
        }
        if (postSoundHound.callBackMqConfig != null) {
            QCloudXml.toXml(xmlSerializer, postSoundHound.callBackMqConfig, "CallBackMqConfig");
        }
        xmlSerializer.endTag("", str);
    }
}
