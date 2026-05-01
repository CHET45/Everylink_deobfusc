package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.media.SearchMediaQueueResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SearchMediaQueueResponse$SearchMediaQueueResponseQueueList$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3525x3907f823 extends IXmlAdapter<SearchMediaQueueResponse.SearchMediaQueueResponseQueueList> {
    private HashMap<String, ChildElementBinder<SearchMediaQueueResponse.SearchMediaQueueResponseQueueList>> childElementBinders;

    public C3525x3907f823() {
        HashMap<String, ChildElementBinder<SearchMediaQueueResponse.SearchMediaQueueResponseQueueList>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("QueueId", new ChildElementBinder<SearchMediaQueueResponse.SearchMediaQueueResponseQueueList>() { // from class: com.tencent.cos.xml.model.ci.media.SearchMediaQueueResponse$SearchMediaQueueResponseQueueList$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SearchMediaQueueResponse.SearchMediaQueueResponseQueueList searchMediaQueueResponseQueueList, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                searchMediaQueueResponseQueueList.queueId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.NAME_ELEMENT, new ChildElementBinder<SearchMediaQueueResponse.SearchMediaQueueResponseQueueList>() { // from class: com.tencent.cos.xml.model.ci.media.SearchMediaQueueResponse$SearchMediaQueueResponseQueueList$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SearchMediaQueueResponse.SearchMediaQueueResponseQueueList searchMediaQueueResponseQueueList, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                searchMediaQueueResponseQueueList.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<SearchMediaQueueResponse.SearchMediaQueueResponseQueueList>() { // from class: com.tencent.cos.xml.model.ci.media.SearchMediaQueueResponse$SearchMediaQueueResponseQueueList$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SearchMediaQueueResponse.SearchMediaQueueResponseQueueList searchMediaQueueResponseQueueList, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                searchMediaQueueResponseQueueList.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("NotifyConfig", new ChildElementBinder<SearchMediaQueueResponse.SearchMediaQueueResponseQueueList>() { // from class: com.tencent.cos.xml.model.ci.media.SearchMediaQueueResponse$SearchMediaQueueResponseQueueList$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SearchMediaQueueResponse.SearchMediaQueueResponseQueueList searchMediaQueueResponseQueueList, String str) throws XmlPullParserException, IOException {
                searchMediaQueueResponseQueueList.notifyConfig = (SearchMediaQueueResponse.SearchMediaQueueResponseNotifyConfig) QCloudXml.fromXml(xmlPullParser, SearchMediaQueueResponse.SearchMediaQueueResponseNotifyConfig.class, "NotifyConfig");
            }
        });
        this.childElementBinders.put("MaxSize", new ChildElementBinder<SearchMediaQueueResponse.SearchMediaQueueResponseQueueList>() { // from class: com.tencent.cos.xml.model.ci.media.SearchMediaQueueResponse$SearchMediaQueueResponseQueueList$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SearchMediaQueueResponse.SearchMediaQueueResponseQueueList searchMediaQueueResponseQueueList, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                searchMediaQueueResponseQueueList.maxSize = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("MaxConcurrent", new ChildElementBinder<SearchMediaQueueResponse.SearchMediaQueueResponseQueueList>() { // from class: com.tencent.cos.xml.model.ci.media.SearchMediaQueueResponse$SearchMediaQueueResponseQueueList$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SearchMediaQueueResponse.SearchMediaQueueResponseQueueList searchMediaQueueResponseQueueList, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                searchMediaQueueResponseQueueList.maxConcurrent = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Category", new ChildElementBinder<SearchMediaQueueResponse.SearchMediaQueueResponseQueueList>() { // from class: com.tencent.cos.xml.model.ci.media.SearchMediaQueueResponse$SearchMediaQueueResponseQueueList$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SearchMediaQueueResponse.SearchMediaQueueResponseQueueList searchMediaQueueResponseQueueList, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                searchMediaQueueResponseQueueList.category = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("UpdateTime", new ChildElementBinder<SearchMediaQueueResponse.SearchMediaQueueResponseQueueList>() { // from class: com.tencent.cos.xml.model.ci.media.SearchMediaQueueResponse$SearchMediaQueueResponseQueueList$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SearchMediaQueueResponse.SearchMediaQueueResponseQueueList searchMediaQueueResponseQueueList, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                searchMediaQueueResponseQueueList.updateTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreateTime", new ChildElementBinder<SearchMediaQueueResponse.SearchMediaQueueResponseQueueList>() { // from class: com.tencent.cos.xml.model.ci.media.SearchMediaQueueResponse$SearchMediaQueueResponseQueueList$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SearchMediaQueueResponse.SearchMediaQueueResponseQueueList searchMediaQueueResponseQueueList, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                searchMediaQueueResponseQueueList.createTime = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SearchMediaQueueResponse.SearchMediaQueueResponseQueueList fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SearchMediaQueueResponse.SearchMediaQueueResponseQueueList searchMediaQueueResponseQueueList = new SearchMediaQueueResponse.SearchMediaQueueResponseQueueList();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SearchMediaQueueResponse.SearchMediaQueueResponseQueueList> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, searchMediaQueueResponseQueueList, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "QueueList" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return searchMediaQueueResponseQueueList;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return searchMediaQueueResponseQueueList;
    }
}
