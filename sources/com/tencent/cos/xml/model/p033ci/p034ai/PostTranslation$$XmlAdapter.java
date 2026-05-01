package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostTranslation$$XmlAdapter extends IXmlAdapter<PostTranslation> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostTranslation postTranslation, String str) throws XmlPullParserException, IOException {
        if (postTranslation == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (postTranslation.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(postTranslation.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (postTranslation.input != null) {
            QCloudXml.toXml(xmlSerializer, postTranslation.input, "Input");
        }
        if (postTranslation.operation != null) {
            QCloudXml.toXml(xmlSerializer, postTranslation.operation, "Operation");
        }
        if (postTranslation.callBackFormat != null) {
            xmlSerializer.startTag("", "CallBackFormat");
            xmlSerializer.text(String.valueOf(postTranslation.callBackFormat));
            xmlSerializer.endTag("", "CallBackFormat");
        }
        if (postTranslation.callBackType != null) {
            xmlSerializer.startTag("", "CallBackType");
            xmlSerializer.text(String.valueOf(postTranslation.callBackType));
            xmlSerializer.endTag("", "CallBackType");
        }
        if (postTranslation.callBack != null) {
            xmlSerializer.startTag("", "CallBack");
            xmlSerializer.text(String.valueOf(postTranslation.callBack));
            xmlSerializer.endTag("", "CallBack");
        }
        if (postTranslation.callBackMqConfig != null) {
            QCloudXml.toXml(xmlSerializer, postTranslation.callBackMqConfig, "CallBackMqConfig");
        }
        xmlSerializer.endTag("", str);
    }
}
