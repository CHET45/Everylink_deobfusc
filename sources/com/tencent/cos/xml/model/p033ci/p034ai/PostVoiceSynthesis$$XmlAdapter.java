package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostVoiceSynthesis$$XmlAdapter extends IXmlAdapter<PostVoiceSynthesis> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostVoiceSynthesis postVoiceSynthesis, String str) throws XmlPullParserException, IOException {
        if (postVoiceSynthesis == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (postVoiceSynthesis.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(postVoiceSynthesis.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (postVoiceSynthesis.operation != null) {
            QCloudXml.toXml(xmlSerializer, postVoiceSynthesis.operation, "Operation");
        }
        if (postVoiceSynthesis.callBackFormat != null) {
            xmlSerializer.startTag("", "CallBackFormat");
            xmlSerializer.text(String.valueOf(postVoiceSynthesis.callBackFormat));
            xmlSerializer.endTag("", "CallBackFormat");
        }
        if (postVoiceSynthesis.callBackType != null) {
            xmlSerializer.startTag("", "CallBackType");
            xmlSerializer.text(String.valueOf(postVoiceSynthesis.callBackType));
            xmlSerializer.endTag("", "CallBackType");
        }
        if (postVoiceSynthesis.callBack != null) {
            xmlSerializer.startTag("", "CallBack");
            xmlSerializer.text(String.valueOf(postVoiceSynthesis.callBack));
            xmlSerializer.endTag("", "CallBack");
        }
        if (postVoiceSynthesis.callBackMqConfig != null) {
            QCloudXml.toXml(xmlSerializer, postVoiceSynthesis.callBackMqConfig, "CallBackMqConfig");
        }
        xmlSerializer.endTag("", str);
    }
}
