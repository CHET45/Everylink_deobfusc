package com.tencent.cos.xml.model.p033ci.audit;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.audit.PostLiveVideoAudit;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostLiveVideoAudit$PostLiveVideoAuditInput$$XmlAdapter extends IXmlAdapter<PostLiveVideoAudit.PostLiveVideoAuditInput> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostLiveVideoAudit.PostLiveVideoAuditInput postLiveVideoAuditInput, String str) throws XmlPullParserException, IOException {
        if (postLiveVideoAuditInput == null) {
            return;
        }
        if (str == null) {
            str = "Input";
        }
        xmlSerializer.startTag("", str);
        if (postLiveVideoAuditInput.url != null) {
            xmlSerializer.startTag("", Constants.URL_ELEMENT);
            xmlSerializer.text(String.valueOf(postLiveVideoAuditInput.url));
            xmlSerializer.endTag("", Constants.URL_ELEMENT);
        }
        if (postLiveVideoAuditInput.dataId != null) {
            xmlSerializer.startTag("", "DataId");
            xmlSerializer.text(String.valueOf(postLiveVideoAuditInput.dataId));
            xmlSerializer.endTag("", "DataId");
        }
        if (postLiveVideoAuditInput.userInfo != null) {
            QCloudXml.toXml(xmlSerializer, postLiveVideoAuditInput.userInfo, "UserInfo");
        }
        xmlSerializer.endTag("", str);
    }
}
