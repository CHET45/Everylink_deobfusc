package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.GetAIBucketResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetAIBucketResponse$$XmlAdapter extends IXmlAdapter<GetAIBucketResponse> {
    private HashMap<String, ChildElementBinder<GetAIBucketResponse>> childElementBinders;

    public GetAIBucketResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetAIBucketResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("RequestId", new ChildElementBinder<GetAIBucketResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.GetAIBucketResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAIBucketResponse getAIBucketResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getAIBucketResponse.requestId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("TotalCount", new ChildElementBinder<GetAIBucketResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.GetAIBucketResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAIBucketResponse getAIBucketResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getAIBucketResponse.totalCount = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("PageNumber", new ChildElementBinder<GetAIBucketResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.GetAIBucketResponse$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAIBucketResponse getAIBucketResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getAIBucketResponse.pageNumber = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("PageSize", new ChildElementBinder<GetAIBucketResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.GetAIBucketResponse$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAIBucketResponse getAIBucketResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getAIBucketResponse.pageSize = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("AiBucketList", new ChildElementBinder<GetAIBucketResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.GetAIBucketResponse$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAIBucketResponse getAIBucketResponse, String str) throws XmlPullParserException, IOException {
                if (getAIBucketResponse.aiBucketList == null) {
                    getAIBucketResponse.aiBucketList = new ArrayList();
                }
                getAIBucketResponse.aiBucketList.add((GetAIBucketResponse.GetAIBucketResponseAiBucketList) QCloudXml.fromXml(xmlPullParser, GetAIBucketResponse.GetAIBucketResponseAiBucketList.class, "AiBucketList"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetAIBucketResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetAIBucketResponse getAIBucketResponse = new GetAIBucketResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetAIBucketResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getAIBucketResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getAIBucketResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getAIBucketResponse;
    }
}
