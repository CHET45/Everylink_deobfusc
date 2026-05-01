package com.tencent.cos.xml.model.p033ci.asr.bean;

import com.tencent.cos.xml.model.tag.DescribeBucket;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class DescribeSpeechBucketsResponse$$XmlAdapter extends IXmlAdapter<DescribeSpeechBucketsResponse> {
    private HashMap<String, ChildElementBinder<DescribeSpeechBucketsResponse>> childElementBinders;

    public DescribeSpeechBucketsResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<DescribeSpeechBucketsResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("RequestId", new ChildElementBinder<DescribeSpeechBucketsResponse>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.DescribeSpeechBucketsResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DescribeSpeechBucketsResponse describeSpeechBucketsResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                describeSpeechBucketsResponse.requestId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("TotalCount", new ChildElementBinder<DescribeSpeechBucketsResponse>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.DescribeSpeechBucketsResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DescribeSpeechBucketsResponse describeSpeechBucketsResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                describeSpeechBucketsResponse.totalCount = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("PageNumber", new ChildElementBinder<DescribeSpeechBucketsResponse>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.DescribeSpeechBucketsResponse$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DescribeSpeechBucketsResponse describeSpeechBucketsResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                describeSpeechBucketsResponse.pageNumber = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("PageSize", new ChildElementBinder<DescribeSpeechBucketsResponse>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.DescribeSpeechBucketsResponse$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DescribeSpeechBucketsResponse describeSpeechBucketsResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                describeSpeechBucketsResponse.pageSize = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("AsrBucketList", new ChildElementBinder<DescribeSpeechBucketsResponse>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.DescribeSpeechBucketsResponse$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DescribeSpeechBucketsResponse describeSpeechBucketsResponse, String str) throws XmlPullParserException, IOException {
                if (describeSpeechBucketsResponse.asrBucketList == null) {
                    describeSpeechBucketsResponse.asrBucketList = new ArrayList();
                }
                describeSpeechBucketsResponse.asrBucketList.add((DescribeBucket) QCloudXml.fromXml(xmlPullParser, DescribeBucket.class, "AsrBucketList"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public DescribeSpeechBucketsResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        DescribeSpeechBucketsResponse describeSpeechBucketsResponse = new DescribeSpeechBucketsResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<DescribeSpeechBucketsResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, describeSpeechBucketsResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return describeSpeechBucketsResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return describeSpeechBucketsResponse;
    }
}
