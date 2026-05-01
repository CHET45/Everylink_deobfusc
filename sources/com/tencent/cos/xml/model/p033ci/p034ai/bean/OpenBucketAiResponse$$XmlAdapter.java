package com.tencent.cos.xml.model.p033ci.p034ai.bean;

import com.tencent.cos.xml.model.tag.DescribeBucket;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class OpenBucketAiResponse$$XmlAdapter extends IXmlAdapter<OpenBucketAiResponse> {
    private HashMap<String, ChildElementBinder<OpenBucketAiResponse>> childElementBinders;

    public OpenBucketAiResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<OpenBucketAiResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("RequestId", new ChildElementBinder<OpenBucketAiResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.bean.OpenBucketAiResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, OpenBucketAiResponse openBucketAiResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                openBucketAiResponse.requestId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("AiBucket", new ChildElementBinder<OpenBucketAiResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.bean.OpenBucketAiResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, OpenBucketAiResponse openBucketAiResponse, String str) throws XmlPullParserException, IOException {
                openBucketAiResponse.aiBucket = (DescribeBucket) QCloudXml.fromXml(xmlPullParser, DescribeBucket.class, "AiBucket");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public OpenBucketAiResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        OpenBucketAiResponse openBucketAiResponse = new OpenBucketAiResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<OpenBucketAiResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, openBucketAiResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return openBucketAiResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return openBucketAiResponse;
    }
}
