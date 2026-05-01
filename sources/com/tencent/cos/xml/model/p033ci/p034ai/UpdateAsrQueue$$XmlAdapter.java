package com.tencent.cos.xml.model.p033ci.p034ai;

import com.microsoft.azure.storage.Constants;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class UpdateAsrQueue$$XmlAdapter extends IXmlAdapter<UpdateAsrQueue> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, UpdateAsrQueue updateAsrQueue, String str) throws XmlPullParserException, IOException {
        if (updateAsrQueue == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (updateAsrQueue.name != null) {
            xmlSerializer.startTag("", Constants.NAME_ELEMENT);
            xmlSerializer.text(String.valueOf(updateAsrQueue.name));
            xmlSerializer.endTag("", Constants.NAME_ELEMENT);
        }
        if (updateAsrQueue.state != null) {
            xmlSerializer.startTag("", "State");
            xmlSerializer.text(String.valueOf(updateAsrQueue.state));
            xmlSerializer.endTag("", "State");
        }
        if (updateAsrQueue.notifyConfig != null) {
            QCloudXml.toXml(xmlSerializer, updateAsrQueue.notifyConfig, "NotifyConfig");
        }
        xmlSerializer.endTag("", str);
    }
}
