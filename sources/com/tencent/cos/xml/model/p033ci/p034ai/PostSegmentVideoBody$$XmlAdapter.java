package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostSegmentVideoBody$$XmlAdapter extends IXmlAdapter<PostSegmentVideoBody> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostSegmentVideoBody postSegmentVideoBody, String str) throws XmlPullParserException, IOException {
        if (postSegmentVideoBody == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (postSegmentVideoBody.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(postSegmentVideoBody.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (postSegmentVideoBody.input != null) {
            QCloudXml.toXml(xmlSerializer, postSegmentVideoBody.input, "Input");
        }
        if (postSegmentVideoBody.operation != null) {
            QCloudXml.toXml(xmlSerializer, postSegmentVideoBody.operation, "Operation");
        }
        if (postSegmentVideoBody.callBackFormat != null) {
            xmlSerializer.startTag("", "CallBackFormat");
            xmlSerializer.text(String.valueOf(postSegmentVideoBody.callBackFormat));
            xmlSerializer.endTag("", "CallBackFormat");
        }
        if (postSegmentVideoBody.callBackType != null) {
            xmlSerializer.startTag("", "CallBackType");
            xmlSerializer.text(String.valueOf(postSegmentVideoBody.callBackType));
            xmlSerializer.endTag("", "CallBackType");
        }
        if (postSegmentVideoBody.callBack != null) {
            xmlSerializer.startTag("", "CallBack");
            xmlSerializer.text(String.valueOf(postSegmentVideoBody.callBack));
            xmlSerializer.endTag("", "CallBack");
        }
        if (postSegmentVideoBody.callBackMqConfig != null) {
            QCloudXml.toXml(xmlSerializer, postSegmentVideoBody.callBackMqConfig, "CallBackMqConfig");
        }
        xmlSerializer.endTag("", str);
    }
}
