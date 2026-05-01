package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitMediaSegmentJob;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitMediaSegmentJob$SubmitMediaSegmentJobOperation$$XmlAdapter extends IXmlAdapter<SubmitMediaSegmentJob.SubmitMediaSegmentJobOperation> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitMediaSegmentJob.SubmitMediaSegmentJobOperation submitMediaSegmentJobOperation, String str) throws XmlPullParserException, IOException {
        if (submitMediaSegmentJobOperation == null) {
            return;
        }
        if (str == null) {
            str = "Operation";
        }
        xmlSerializer.startTag("", str);
        if (submitMediaSegmentJobOperation.segment != null) {
            QCloudXml.toXml(xmlSerializer, submitMediaSegmentJobOperation.segment, "Segment");
        }
        if (submitMediaSegmentJobOperation.output != null) {
            QCloudXml.toXml(xmlSerializer, submitMediaSegmentJobOperation.output, "Output");
        }
        if (submitMediaSegmentJobOperation.jobLevel != null) {
            xmlSerializer.startTag("", "JobLevel");
            xmlSerializer.text(String.valueOf(submitMediaSegmentJobOperation.jobLevel));
            xmlSerializer.endTag("", "JobLevel");
        }
        xmlSerializer.endTag("", str);
    }
}
