package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class DeleteImageSearch$$XmlAdapter extends IXmlAdapter<DeleteImageSearch> {
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, DeleteImageSearch deleteImageSearch, String str) throws XmlPullParserException, IOException {
        if (deleteImageSearch == null) {
            return;
        }
        if (str == null) {
            str = "Request";
        }
        xmlSerializer.startTag("", str);
        if (deleteImageSearch.entityId != null) {
            xmlSerializer.startTag("", "EntityId");
            xmlSerializer.text(String.valueOf(deleteImageSearch.entityId));
            xmlSerializer.endTag("", "EntityId");
        }
        xmlSerializer.endTag("", str);
    }
}
