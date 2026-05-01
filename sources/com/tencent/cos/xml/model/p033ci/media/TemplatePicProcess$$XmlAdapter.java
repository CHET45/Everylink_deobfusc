package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class TemplatePicProcess$$XmlAdapter extends IXmlAdapter<TemplatePicProcess> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, TemplatePicProcess templatePicProcess, String str) throws XmlPullParserException, IOException {
        if (templatePicProcess == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (templatePicProcess.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(templatePicProcess.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (templatePicProcess.name != null) {
            xmlSerializer.startTag("", Constants.NAME_ELEMENT);
            xmlSerializer.text(String.valueOf(templatePicProcess.name));
            xmlSerializer.endTag("", Constants.NAME_ELEMENT);
        }
        if (templatePicProcess.picProcess != null) {
            QCloudXml.toXml(xmlSerializer, templatePicProcess.picProcess, "PicProcess");
        }
        xmlSerializer.endTag("", str);
    }
}
