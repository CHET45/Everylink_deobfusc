package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.OpenAsrBucketResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class OpenAsrBucketResponse$$XmlAdapter extends IXmlAdapter<OpenAsrBucketResponse> {
    private HashMap<String, ChildElementBinder<OpenAsrBucketResponse>> childElementBinders;

    public OpenAsrBucketResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<OpenAsrBucketResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("RequestId", new ChildElementBinder<OpenAsrBucketResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.OpenAsrBucketResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, OpenAsrBucketResponse openAsrBucketResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                openAsrBucketResponse.requestId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("AsrBucket", new ChildElementBinder<OpenAsrBucketResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.OpenAsrBucketResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, OpenAsrBucketResponse openAsrBucketResponse, String str) throws XmlPullParserException, IOException {
                openAsrBucketResponse.asrBucket = (OpenAsrBucketResponse.OpenAsrBucketResponseAsrBucket) QCloudXml.fromXml(xmlPullParser, OpenAsrBucketResponse.OpenAsrBucketResponseAsrBucket.class, "AsrBucket");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public OpenAsrBucketResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        OpenAsrBucketResponse openAsrBucketResponse = new OpenAsrBucketResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<OpenAsrBucketResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, openAsrBucketResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return openAsrBucketResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return openAsrBucketResponse;
    }
}
