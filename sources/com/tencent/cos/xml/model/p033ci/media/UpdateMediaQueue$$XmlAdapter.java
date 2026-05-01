package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class UpdateMediaQueue$$XmlAdapter extends IXmlAdapter<UpdateMediaQueue> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, UpdateMediaQueue updateMediaQueue, String str) throws XmlPullParserException, IOException {
        if (updateMediaQueue == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (updateMediaQueue.name != null) {
            xmlSerializer.startTag("", Constants.NAME_ELEMENT);
            xmlSerializer.text(String.valueOf(updateMediaQueue.name));
            xmlSerializer.endTag("", Constants.NAME_ELEMENT);
        }
        if (updateMediaQueue.state != null) {
            xmlSerializer.startTag("", "State");
            xmlSerializer.text(String.valueOf(updateMediaQueue.state));
            xmlSerializer.endTag("", "State");
        }
        if (updateMediaQueue.notifyConfig != null) {
            QCloudXml.toXml(xmlSerializer, updateMediaQueue.notifyConfig, "NotifyConfig");
        }
        xmlSerializer.endTag("", str);
    }
}
