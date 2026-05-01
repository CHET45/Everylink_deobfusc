package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostVideoTargetRec$$XmlAdapter extends IXmlAdapter<PostVideoTargetRec> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostVideoTargetRec postVideoTargetRec, String str) throws XmlPullParserException, IOException {
        if (postVideoTargetRec == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (postVideoTargetRec.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(postVideoTargetRec.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (postVideoTargetRec.operation != null) {
            QCloudXml.toXml(xmlSerializer, postVideoTargetRec.operation, "Operation");
        }
        if (postVideoTargetRec.input != null) {
            QCloudXml.toXml(xmlSerializer, postVideoTargetRec.input, "Input");
        }
        if (postVideoTargetRec.callBackFormat != null) {
            xmlSerializer.startTag("", "CallBackFormat");
            xmlSerializer.text(String.valueOf(postVideoTargetRec.callBackFormat));
            xmlSerializer.endTag("", "CallBackFormat");
        }
        if (postVideoTargetRec.callBackType != null) {
            xmlSerializer.startTag("", "CallBackType");
            xmlSerializer.text(String.valueOf(postVideoTargetRec.callBackType));
            xmlSerializer.endTag("", "CallBackType");
        }
        if (postVideoTargetRec.callBack != null) {
            xmlSerializer.startTag("", "CallBack");
            xmlSerializer.text(String.valueOf(postVideoTargetRec.callBack));
            xmlSerializer.endTag("", "CallBack");
        }
        if (postVideoTargetRec.callBackMqConfig != null) {
            QCloudXml.toXml(xmlSerializer, postVideoTargetRec.callBackMqConfig, "CallBackMqConfig");
        }
        xmlSerializer.endTag("", str);
    }
}
