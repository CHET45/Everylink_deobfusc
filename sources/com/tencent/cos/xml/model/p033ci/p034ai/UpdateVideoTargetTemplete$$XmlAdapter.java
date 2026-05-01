package com.tencent.cos.xml.model.p033ci.p034ai;

import com.microsoft.azure.storage.Constants;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class UpdateVideoTargetTemplete$$XmlAdapter extends IXmlAdapter<UpdateVideoTargetTemplete> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, UpdateVideoTargetTemplete updateVideoTargetTemplete, String str) throws XmlPullParserException, IOException {
        if (updateVideoTargetTemplete == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (updateVideoTargetTemplete.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(updateVideoTargetTemplete.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (updateVideoTargetTemplete.name != null) {
            xmlSerializer.startTag("", Constants.NAME_ELEMENT);
            xmlSerializer.text(String.valueOf(updateVideoTargetTemplete.name));
            xmlSerializer.endTag("", Constants.NAME_ELEMENT);
        }
        if (updateVideoTargetTemplete.videoTargetRec != null) {
            QCloudXml.toXml(xmlSerializer, updateVideoTargetTemplete.videoTargetRec, "VideoTargetRec");
        }
        xmlSerializer.endTag("", str);
    }
}
