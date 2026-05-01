package com.tencent.cos.xml.model.p033ci.p034ai;

import com.microsoft.azure.storage.Constants;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class UpdateAIQueue$$XmlAdapter extends IXmlAdapter<UpdateAIQueue> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, UpdateAIQueue updateAIQueue, String str) throws XmlPullParserException, IOException {
        if (updateAIQueue == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (updateAIQueue.name != null) {
            xmlSerializer.startTag("", Constants.NAME_ELEMENT);
            xmlSerializer.text(String.valueOf(updateAIQueue.name));
            xmlSerializer.endTag("", Constants.NAME_ELEMENT);
        }
        if (updateAIQueue.state != null) {
            xmlSerializer.startTag("", "State");
            xmlSerializer.text(String.valueOf(updateAIQueue.state));
            xmlSerializer.endTag("", "State");
        }
        if (updateAIQueue.notifyConfig != null) {
            QCloudXml.toXml(xmlSerializer, updateAIQueue.notifyConfig, "NotifyConfig");
        }
        xmlSerializer.endTag("", str);
    }
}
