package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class VocalScore$$XmlAdapter extends IXmlAdapter<VocalScore> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, VocalScore vocalScore, String str) throws XmlPullParserException, IOException {
        if (vocalScore == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (vocalScore.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(vocalScore.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (vocalScore.input != null) {
            QCloudXml.toXml(xmlSerializer, vocalScore.input, "Input");
        }
        if (vocalScore.operation != null) {
            QCloudXml.toXml(xmlSerializer, vocalScore.operation, "Operation");
        }
        if (vocalScore.callBackFormat != null) {
            xmlSerializer.startTag("", "CallBackFormat");
            xmlSerializer.text(String.valueOf(vocalScore.callBackFormat));
            xmlSerializer.endTag("", "CallBackFormat");
        }
        if (vocalScore.callBackType != null) {
            xmlSerializer.startTag("", "CallBackType");
            xmlSerializer.text(String.valueOf(vocalScore.callBackType));
            xmlSerializer.endTag("", "CallBackType");
        }
        if (vocalScore.callBack != null) {
            xmlSerializer.startTag("", "CallBack");
            xmlSerializer.text(String.valueOf(vocalScore.callBack));
            xmlSerializer.endTag("", "CallBack");
        }
        if (vocalScore.callBackMqConfig != null) {
            QCloudXml.toXml(xmlSerializer, vocalScore.callBackMqConfig, "CallBackMqConfig");
        }
        xmlSerializer.endTag("", str);
    }
}
