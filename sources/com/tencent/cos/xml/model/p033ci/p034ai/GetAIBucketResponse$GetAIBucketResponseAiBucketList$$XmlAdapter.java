package com.tencent.cos.xml.model.p033ci.p034ai;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.p034ai.GetAIBucketResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetAIBucketResponse$GetAIBucketResponseAiBucketList$$XmlAdapter extends IXmlAdapter<GetAIBucketResponse.GetAIBucketResponseAiBucketList> {
    private HashMap<String, ChildElementBinder<GetAIBucketResponse.GetAIBucketResponseAiBucketList>> childElementBinders;

    public GetAIBucketResponse$GetAIBucketResponseAiBucketList$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetAIBucketResponse.GetAIBucketResponseAiBucketList>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("BucketId", new ChildElementBinder<GetAIBucketResponse.GetAIBucketResponseAiBucketList>() { // from class: com.tencent.cos.xml.model.ci.ai.GetAIBucketResponse$GetAIBucketResponseAiBucketList$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAIBucketResponse.GetAIBucketResponseAiBucketList getAIBucketResponseAiBucketList, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getAIBucketResponseAiBucketList.bucketId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.NAME_ELEMENT, new ChildElementBinder<GetAIBucketResponse.GetAIBucketResponseAiBucketList>() { // from class: com.tencent.cos.xml.model.ci.ai.GetAIBucketResponse$GetAIBucketResponseAiBucketList$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAIBucketResponse.GetAIBucketResponseAiBucketList getAIBucketResponseAiBucketList, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getAIBucketResponseAiBucketList.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Region", new ChildElementBinder<GetAIBucketResponse.GetAIBucketResponseAiBucketList>() { // from class: com.tencent.cos.xml.model.ci.ai.GetAIBucketResponse$GetAIBucketResponseAiBucketList$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAIBucketResponse.GetAIBucketResponseAiBucketList getAIBucketResponseAiBucketList, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getAIBucketResponseAiBucketList.region = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreateTime", new ChildElementBinder<GetAIBucketResponse.GetAIBucketResponseAiBucketList>() { // from class: com.tencent.cos.xml.model.ci.ai.GetAIBucketResponse$GetAIBucketResponseAiBucketList$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAIBucketResponse.GetAIBucketResponseAiBucketList getAIBucketResponseAiBucketList, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getAIBucketResponseAiBucketList.createTime = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetAIBucketResponse.GetAIBucketResponseAiBucketList fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetAIBucketResponse.GetAIBucketResponseAiBucketList getAIBucketResponseAiBucketList = new GetAIBucketResponse.GetAIBucketResponseAiBucketList();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetAIBucketResponse.GetAIBucketResponseAiBucketList> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getAIBucketResponseAiBucketList, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "AiBucketList" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getAIBucketResponseAiBucketList;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getAIBucketResponseAiBucketList;
    }
}
