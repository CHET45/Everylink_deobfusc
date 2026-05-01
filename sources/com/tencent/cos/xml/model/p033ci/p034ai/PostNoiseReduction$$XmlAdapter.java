package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostNoiseReduction$$XmlAdapter extends IXmlAdapter<PostNoiseReduction> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostNoiseReduction postNoiseReduction, String str) throws XmlPullParserException, IOException {
        if (postNoiseReduction == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (postNoiseReduction.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(postNoiseReduction.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (postNoiseReduction.input != null) {
            QCloudXml.toXml(xmlSerializer, postNoiseReduction.input, "Input");
        }
        if (postNoiseReduction.operation != null) {
            QCloudXml.toXml(xmlSerializer, postNoiseReduction.operation, "Operation");
        }
        if (postNoiseReduction.callBackFormat != null) {
            xmlSerializer.startTag("", "CallBackFormat");
            xmlSerializer.text(String.valueOf(postNoiseReduction.callBackFormat));
            xmlSerializer.endTag("", "CallBackFormat");
        }
        if (postNoiseReduction.callBackType != null) {
            xmlSerializer.startTag("", "CallBackType");
            xmlSerializer.text(String.valueOf(postNoiseReduction.callBackType));
            xmlSerializer.endTag("", "CallBackType");
        }
        if (postNoiseReduction.callBack != null) {
            xmlSerializer.startTag("", "CallBack");
            xmlSerializer.text(String.valueOf(postNoiseReduction.callBack));
            xmlSerializer.endTag("", "CallBack");
        }
        if (postNoiseReduction.callBackMqConfig != null) {
            QCloudXml.toXml(xmlSerializer, postNoiseReduction.callBackMqConfig, "CallBackMqConfig");
        }
        xmlSerializer.endTag("", str);
    }
}
