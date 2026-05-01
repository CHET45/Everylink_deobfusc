package com.tencent.cos.xml.model.tag.audit.post;

import com.microsoft.azure.storage.blob.BlobConstants;
import com.tencent.cos.xml.model.tag.audit.post.PostVideoAudit;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostVideoAudit$Snapshot$$XmlAdapter extends IXmlAdapter<PostVideoAudit.Snapshot> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostVideoAudit.Snapshot snapshot, String str) throws XmlPullParserException, IOException {
        if (snapshot == null) {
            return;
        }
        if (str == null) {
            str = BlobConstants.SNAPSHOT_ELEMENT;
        }
        xmlSerializer.startTag("", str);
        if (snapshot.mode != null) {
            xmlSerializer.startTag("", "Mode");
            xmlSerializer.text(String.valueOf(snapshot.mode));
            xmlSerializer.endTag("", "Mode");
        }
        xmlSerializer.startTag("", "Count");
        xmlSerializer.text(String.valueOf(snapshot.count));
        xmlSerializer.endTag("", "Count");
        if (snapshot.timeInterval != 0.0f) {
            xmlSerializer.startTag("", "TimeInterval");
            xmlSerializer.text(String.valueOf(snapshot.timeInterval));
            xmlSerializer.endTag("", "TimeInterval");
        }
        xmlSerializer.endTag("", str);
    }
}
