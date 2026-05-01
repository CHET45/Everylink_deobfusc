package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class CloseAsrBucketResponse$$XmlAdapter extends IXmlAdapter<CloseAsrBucketResponse> {
    private HashMap<String, ChildElementBinder<CloseAsrBucketResponse>> childElementBinders;

    public CloseAsrBucketResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<CloseAsrBucketResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("RequestId", new ChildElementBinder<CloseAsrBucketResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.CloseAsrBucketResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CloseAsrBucketResponse closeAsrBucketResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                closeAsrBucketResponse.requestId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BucketName", new ChildElementBinder<CloseAsrBucketResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.CloseAsrBucketResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CloseAsrBucketResponse closeAsrBucketResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                closeAsrBucketResponse.bucketName = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public CloseAsrBucketResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        CloseAsrBucketResponse closeAsrBucketResponse = new CloseAsrBucketResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<CloseAsrBucketResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, closeAsrBucketResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return closeAsrBucketResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return closeAsrBucketResponse;
    }
}
