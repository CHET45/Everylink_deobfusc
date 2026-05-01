package com.tencent.cos.xml.model.p033ci.p034ai;

import com.microsoft.azure.storage.Constants;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostNoiseReductionTemplete$$XmlAdapter extends IXmlAdapter<PostNoiseReductionTemplete> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostNoiseReductionTemplete postNoiseReductionTemplete, String str) throws XmlPullParserException, IOException {
        if (postNoiseReductionTemplete == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (postNoiseReductionTemplete.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(postNoiseReductionTemplete.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (postNoiseReductionTemplete.name != null) {
            xmlSerializer.startTag("", Constants.NAME_ELEMENT);
            xmlSerializer.text(String.valueOf(postNoiseReductionTemplete.name));
            xmlSerializer.endTag("", Constants.NAME_ELEMENT);
        }
        if (postNoiseReductionTemplete.noiseReduction != null) {
            QCloudXml.toXml(xmlSerializer, postNoiseReductionTemplete.noiseReduction, "NoiseReduction");
        }
        xmlSerializer.endTag("", str);
    }
}
