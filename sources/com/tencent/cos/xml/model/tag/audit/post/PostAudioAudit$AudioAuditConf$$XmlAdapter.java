package com.tencent.cos.xml.model.tag.audit.post;

import com.tencent.cos.xml.model.tag.audit.post.PostAudioAudit;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostAudioAudit$AudioAuditConf$$XmlAdapter extends IXmlAdapter<PostAudioAudit.AudioAuditConf> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostAudioAudit.AudioAuditConf audioAuditConf, String str) throws XmlPullParserException, IOException {
        if (audioAuditConf == null) {
            return;
        }
        if (str == null) {
            str = "Conf";
        }
        xmlSerializer.startTag("", str);
        if (audioAuditConf.callbackVersion != null) {
            xmlSerializer.startTag("", "CallbackVersion");
            xmlSerializer.text(String.valueOf(audioAuditConf.callbackVersion));
            xmlSerializer.endTag("", "CallbackVersion");
        }
        if (audioAuditConf.detectType != null) {
            xmlSerializer.startTag("", "DetectType");
            xmlSerializer.text(String.valueOf(audioAuditConf.detectType));
            xmlSerializer.endTag("", "DetectType");
        }
        xmlSerializer.startTag("", "Async");
        xmlSerializer.text(String.valueOf(audioAuditConf.async));
        xmlSerializer.endTag("", "Async");
        if (audioAuditConf.callback != null) {
            xmlSerializer.startTag("", "Callback");
            xmlSerializer.text(String.valueOf(audioAuditConf.callback));
            xmlSerializer.endTag("", "Callback");
        }
        if (audioAuditConf.callbackType != 0) {
            xmlSerializer.startTag("", "CallbackType");
            xmlSerializer.text(String.valueOf(audioAuditConf.callbackType));
            xmlSerializer.endTag("", "CallbackType");
        }
        if (audioAuditConf.bizType != null) {
            xmlSerializer.startTag("", "BizType");
            xmlSerializer.text(String.valueOf(audioAuditConf.bizType));
            xmlSerializer.endTag("", "BizType");
        }
        if (audioAuditConf.freeze != null) {
            QCloudXml.toXml(xmlSerializer, audioAuditConf.freeze, "Freeze");
        }
        xmlSerializer.endTag("", str);
    }
}
