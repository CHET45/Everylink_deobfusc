package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.PostTranslation;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostTranslation$PostTranslationOperation$$XmlAdapter extends IXmlAdapter<PostTranslation.PostTranslationOperation> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostTranslation.PostTranslationOperation postTranslationOperation, String str) throws XmlPullParserException, IOException {
        if (postTranslationOperation == null) {
            return;
        }
        if (str == null) {
            str = "Operation";
        }
        xmlSerializer.startTag("", str);
        if (postTranslationOperation.translation != null) {
            QCloudXml.toXml(xmlSerializer, postTranslationOperation.translation, "Translation");
        }
        if (postTranslationOperation.output != null) {
            QCloudXml.toXml(xmlSerializer, postTranslationOperation.output, "Output");
        }
        if (postTranslationOperation.userData != null) {
            xmlSerializer.startTag("", "UserData");
            xmlSerializer.text(String.valueOf(postTranslationOperation.userData));
            xmlSerializer.endTag("", "UserData");
        }
        if (postTranslationOperation.jobLevel != null) {
            xmlSerializer.startTag("", "JobLevel");
            xmlSerializer.text(String.valueOf(postTranslationOperation.jobLevel));
            xmlSerializer.endTag("", "JobLevel");
        }
        if (postTranslationOperation.noNeedOutput != null) {
            xmlSerializer.startTag("", "NoNeedOutput");
            xmlSerializer.text(String.valueOf(postTranslationOperation.noNeedOutput));
            xmlSerializer.endTag("", "NoNeedOutput");
        }
        xmlSerializer.endTag("", str);
    }
}
