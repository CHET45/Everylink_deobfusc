package com.tencent.cos.xml.model.p033ci.p034ai;

import com.microsoft.azure.storage.Constants;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class UpdateNoiseReductionTemplete$$XmlAdapter extends IXmlAdapter<UpdateNoiseReductionTemplete> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, UpdateNoiseReductionTemplete updateNoiseReductionTemplete, String str) throws XmlPullParserException, IOException {
        if (updateNoiseReductionTemplete == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (updateNoiseReductionTemplete.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(updateNoiseReductionTemplete.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (updateNoiseReductionTemplete.name != null) {
            xmlSerializer.startTag("", Constants.NAME_ELEMENT);
            xmlSerializer.text(String.valueOf(updateNoiseReductionTemplete.name));
            xmlSerializer.endTag("", Constants.NAME_ELEMENT);
        }
        if (updateNoiseReductionTemplete.noiseReduction != null) {
            QCloudXml.toXml(xmlSerializer, updateNoiseReductionTemplete.noiseReduction, "NoiseReduction");
        }
        xmlSerializer.endTag("", str);
    }
}
