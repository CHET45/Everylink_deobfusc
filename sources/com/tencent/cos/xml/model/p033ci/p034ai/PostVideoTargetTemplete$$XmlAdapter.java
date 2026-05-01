package com.tencent.cos.xml.model.p033ci.p034ai;

import com.microsoft.azure.storage.Constants;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostVideoTargetTemplete$$XmlAdapter extends IXmlAdapter<PostVideoTargetTemplete> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostVideoTargetTemplete postVideoTargetTemplete, String str) throws XmlPullParserException, IOException {
        if (postVideoTargetTemplete == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (postVideoTargetTemplete.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(postVideoTargetTemplete.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (postVideoTargetTemplete.name != null) {
            xmlSerializer.startTag("", Constants.NAME_ELEMENT);
            xmlSerializer.text(String.valueOf(postVideoTargetTemplete.name));
            xmlSerializer.endTag("", Constants.NAME_ELEMENT);
        }
        if (postVideoTargetTemplete.videoTargetRec != null) {
            QCloudXml.toXml(xmlSerializer, postVideoTargetTemplete.videoTargetRec, "VideoTargetRec");
        }
        xmlSerializer.endTag("", str);
    }
}
