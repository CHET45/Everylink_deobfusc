package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitVideoTagJob;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitVideoTagJob$SubmitVideoTagJobOperation$$XmlAdapter extends IXmlAdapter<SubmitVideoTagJob.SubmitVideoTagJobOperation> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitVideoTagJob.SubmitVideoTagJobOperation submitVideoTagJobOperation, String str) throws XmlPullParserException, IOException {
        if (submitVideoTagJobOperation == null) {
            return;
        }
        if (str == null) {
            str = "Operation";
        }
        xmlSerializer.startTag("", str);
        if (submitVideoTagJobOperation.videoTag != null) {
            QCloudXml.toXml(xmlSerializer, submitVideoTagJobOperation.videoTag, "VideoTag");
        }
        if (submitVideoTagJobOperation.jobLevel != null) {
            xmlSerializer.startTag("", "JobLevel");
            xmlSerializer.text(String.valueOf(submitVideoTagJobOperation.jobLevel));
            xmlSerializer.endTag("", "JobLevel");
        }
        if (submitVideoTagJobOperation.userData != null) {
            xmlSerializer.startTag("", "UserData");
            xmlSerializer.text(String.valueOf(submitVideoTagJobOperation.userData));
            xmlSerializer.endTag("", "UserData");
        }
        xmlSerializer.endTag("", str);
    }
}
