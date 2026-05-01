package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.PostTranslation;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostTranslation$CallBackMqConfig$$XmlAdapter extends IXmlAdapter<PostTranslation.CallBackMqConfig> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostTranslation.CallBackMqConfig callBackMqConfig, String str) throws XmlPullParserException, IOException {
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
