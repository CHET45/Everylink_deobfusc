package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.VocalScore;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class VocalScore$VocalScoreOperation$$XmlAdapter extends IXmlAdapter<VocalScore.VocalScoreOperation> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, VocalScore.VocalScoreOperation vocalScoreOperation, String str) throws XmlPullParserException, IOException {
        if (vocalScoreOperation == null) {
            return;
        }
        if (str == null) {
            str = "Operation";
        }
        xmlSerializer.startTag("", str);
        if (vocalScoreOperation.vocalScore != null) {
            QCloudXml.toXml(xmlSerializer, vocalScoreOperation.vocalScore, "VocalScore");
        }
        if (vocalScoreOperation.userData != null) {
            xmlSerializer.startTag("", "UserData");
            xmlSerializer.text(String.valueOf(vocalScoreOperation.userData));
            xmlSerializer.endTag("", "UserData");
        }
        if (vocalScoreOperation.jobLevel != null) {
            xmlSerializer.startTag("", "JobLevel");
            xmlSerializer.text(String.valueOf(vocalScoreOperation.jobLevel));
            xmlSerializer.endTag("", "JobLevel");
        }
        xmlSerializer.endTag("", str);
    }
}
