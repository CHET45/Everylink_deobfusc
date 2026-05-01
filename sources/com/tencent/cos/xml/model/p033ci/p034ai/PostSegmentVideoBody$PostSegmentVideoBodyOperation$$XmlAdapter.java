package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.PostSegmentVideoBody;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostSegmentVideoBody$PostSegmentVideoBodyOperation$$XmlAdapter extends IXmlAdapter<PostSegmentVideoBody.PostSegmentVideoBodyOperation> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostSegmentVideoBody.PostSegmentVideoBodyOperation postSegmentVideoBodyOperation, String str) throws XmlPullParserException, IOException {
        if (postSegmentVideoBodyOperation == null) {
            return;
        }
        if (str == null) {
            str = "Operation";
        }
        xmlSerializer.startTag("", str);
        if (postSegmentVideoBodyOperation.segmentVideoBody != null) {
            QCloudXml.toXml(xmlSerializer, postSegmentVideoBodyOperation.segmentVideoBody, "SegmentVideoBody");
        }
        if (postSegmentVideoBodyOperation.output != null) {
            QCloudXml.toXml(xmlSerializer, postSegmentVideoBodyOperation.output, "Output");
        }
        if (postSegmentVideoBodyOperation.userData != null) {
            xmlSerializer.startTag("", "UserData");
            xmlSerializer.text(String.valueOf(postSegmentVideoBodyOperation.userData));
            xmlSerializer.endTag("", "UserData");
        }
        if (postSegmentVideoBodyOperation.jobLevel != null) {
            xmlSerializer.startTag("", "JobLevel");
            xmlSerializer.text(String.valueOf(postSegmentVideoBodyOperation.jobLevel));
            xmlSerializer.endTag("", "JobLevel");
        }
        xmlSerializer.endTag("", str);
    }
}
