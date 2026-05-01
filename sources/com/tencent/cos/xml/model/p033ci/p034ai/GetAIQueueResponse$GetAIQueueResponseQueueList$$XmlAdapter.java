package com.tencent.cos.xml.model.p033ci.p034ai;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.p034ai.GetAIQueueResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetAIQueueResponse$GetAIQueueResponseQueueList$$XmlAdapter extends IXmlAdapter<GetAIQueueResponse.GetAIQueueResponseQueueList> {
    private HashMap<String, ChildElementBinder<GetAIQueueResponse.GetAIQueueResponseQueueList>> childElementBinders;

    public GetAIQueueResponse$GetAIQueueResponseQueueList$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetAIQueueResponse.GetAIQueueResponseQueueList>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("QueueId", new ChildElementBinder<GetAIQueueResponse.GetAIQueueResponseQueueList>() { // from class: com.tencent.cos.xml.model.ci.ai.GetAIQueueResponse$GetAIQueueResponseQueueList$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAIQueueResponse.GetAIQueueResponseQueueList getAIQueueResponseQueueList, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getAIQueueResponseQueueList.queueId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.NAME_ELEMENT, new ChildElementBinder<GetAIQueueResponse.GetAIQueueResponseQueueList>() { // from class: com.tencent.cos.xml.model.ci.ai.GetAIQueueResponse$GetAIQueueResponseQueueList$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAIQueueResponse.GetAIQueueResponseQueueList getAIQueueResponseQueueList, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getAIQueueResponseQueueList.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<GetAIQueueResponse.GetAIQueueResponseQueueList>() { // from class: com.tencent.cos.xml.model.ci.ai.GetAIQueueResponse$GetAIQueueResponseQueueList$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAIQueueResponse.GetAIQueueResponseQueueList getAIQueueResponseQueueList, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getAIQueueResponseQueueList.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("NotifyConfig", new ChildElementBinder<GetAIQueueResponse.GetAIQueueResponseQueueList>() { // from class: com.tencent.cos.xml.model.ci.ai.GetAIQueueResponse$GetAIQueueResponseQueueList$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAIQueueResponse.GetAIQueueResponseQueueList getAIQueueResponseQueueList, String str) throws XmlPullParserException, IOException {
                getAIQueueResponseQueueList.notifyConfig = (GetAIQueueResponse.GetAIQueueResponseNotifyConfig) QCloudXml.fromXml(xmlPullParser, GetAIQueueResponse.GetAIQueueResponseNotifyConfig.class, "NotifyConfig");
            }
        });
        this.childElementBinders.put("MaxSize", new ChildElementBinder<GetAIQueueResponse.GetAIQueueResponseQueueList>() { // from class: com.tencent.cos.xml.model.ci.ai.GetAIQueueResponse$GetAIQueueResponseQueueList$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAIQueueResponse.GetAIQueueResponseQueueList getAIQueueResponseQueueList, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getAIQueueResponseQueueList.maxSize = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("MaxConcurrent", new ChildElementBinder<GetAIQueueResponse.GetAIQueueResponseQueueList>() { // from class: com.tencent.cos.xml.model.ci.ai.GetAIQueueResponse$GetAIQueueResponseQueueList$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAIQueueResponse.GetAIQueueResponseQueueList getAIQueueResponseQueueList, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getAIQueueResponseQueueList.maxConcurrent = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Category", new ChildElementBinder<GetAIQueueResponse.GetAIQueueResponseQueueList>() { // from class: com.tencent.cos.xml.model.ci.ai.GetAIQueueResponse$GetAIQueueResponseQueueList$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAIQueueResponse.GetAIQueueResponseQueueList getAIQueueResponseQueueList, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getAIQueueResponseQueueList.category = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("UpdateTime", new ChildElementBinder<GetAIQueueResponse.GetAIQueueResponseQueueList>() { // from class: com.tencent.cos.xml.model.ci.ai.GetAIQueueResponse$GetAIQueueResponseQueueList$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAIQueueResponse.GetAIQueueResponseQueueList getAIQueueResponseQueueList, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getAIQueueResponseQueueList.updateTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreateTime", new ChildElementBinder<GetAIQueueResponse.GetAIQueueResponseQueueList>() { // from class: com.tencent.cos.xml.model.ci.ai.GetAIQueueResponse$GetAIQueueResponseQueueList$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAIQueueResponse.GetAIQueueResponseQueueList getAIQueueResponseQueueList, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getAIQueueResponseQueueList.createTime = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetAIQueueResponse.GetAIQueueResponseQueueList fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetAIQueueResponse.GetAIQueueResponseQueueList getAIQueueResponseQueueList = new GetAIQueueResponse.GetAIQueueResponseQueueList();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetAIQueueResponse.GetAIQueueResponseQueueList> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getAIQueueResponseQueueList, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "QueueList" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getAIQueueResponseQueueList;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getAIQueueResponseQueueList;
    }
}
