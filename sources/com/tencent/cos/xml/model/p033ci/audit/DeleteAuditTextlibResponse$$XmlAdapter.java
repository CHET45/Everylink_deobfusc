package com.tencent.cos.xml.model.p033ci.audit;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class DeleteAuditTextlibResponse$$XmlAdapter extends IXmlAdapter<DeleteAuditTextlibResponse> {
    private HashMap<String, ChildElementBinder<DeleteAuditTextlibResponse>> childElementBinders;

    public DeleteAuditTextlibResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<DeleteAuditTextlibResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("RequestId", new ChildElementBinder<DeleteAuditTextlibResponse>() { // from class: com.tencent.cos.xml.model.ci.audit.DeleteAuditTextlibResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DeleteAuditTextlibResponse deleteAuditTextlibResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                deleteAuditTextlibResponse.requestId = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public DeleteAuditTextlibResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        DeleteAuditTextlibResponse deleteAuditTextlibResponse = new DeleteAuditTextlibResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<DeleteAuditTextlibResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, deleteAuditTextlibResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return deleteAuditTextlibResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return deleteAuditTextlibResponse;
    }
}
