package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.VocalScore;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class VocalScore$CallBackMqConfig$$XmlAdapter extends IXmlAdapter<VocalScore.CallBackMqConfig> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, VocalScore.CallBackMqConfig callBackMqConfig, String str) throws XmlPullParserException, IOException {
        if (callBackMqConfig == null) {
            return;
        }
        if (str == null) {
            str = "CallBackMqConfig";
        }
        xmlSerializer.startTag("", str);
        if (callBackMqConfig.mqRegion != null) {
            xmlSerializer.startTag("", "MqRegion");
            xmlSerializer.text(String.valueOf(callBackMqConfig.mqRegion));
            xmlSerializer.endTag("", "MqRegion");
        }
        if (callBackMqConfig.mqMode != null) {
            xmlSerializer.startTag("", "MqMode");
            xmlSerializer.text(String.valueOf(callBackMqConfig.mqMode));
            xmlSerializer.endTag("", "MqMode");
        }
        if (callBackMqConfig.mqName != null) {
            xmlSerializer.startTag("", "MqName");
            xmlSerializer.text(String.valueOf(callBackMqConfig.mqName));
            xmlSerializer.endTag("", "MqName");
        }
        xmlSerializer.endTag("", str);
    }
}
