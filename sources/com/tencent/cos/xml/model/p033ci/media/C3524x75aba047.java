package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.media.SearchMediaQueueResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SearchMediaQueueResponse$SearchMediaQueueResponseNotifyConfig$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3524x75aba047 extends IXmlAdapter<SearchMediaQueueResponse.SearchMediaQueueResponseNotifyConfig> {
    private HashMap<String, ChildElementBinder<SearchMediaQueueResponse.SearchMediaQueueResponseNotifyConfig>> childElementBinders;

    public C3524x75aba047() {
        HashMap<String, ChildElementBinder<SearchMediaQueueResponse.SearchMediaQueueResponseNotifyConfig>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.URL_ELEMENT, new ChildElementBinder<SearchMediaQueueResponse.SearchMediaQueueResponseNotifyConfig>() { // from class: com.tencent.cos.xml.model.ci.media.SearchMediaQueueResponse$SearchMediaQueueResponseNotifyConfig$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SearchMediaQueueResponse.SearchMediaQueueResponseNotifyConfig searchMediaQueueResponseNotifyConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                searchMediaQueueResponseNotifyConfig.url = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<SearchMediaQueueResponse.SearchMediaQueueResponseNotifyConfig>() { // from class: com.tencent.cos.xml.model.ci.media.SearchMediaQueueResponse$SearchMediaQueueResponseNotifyConfig$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SearchMediaQueueResponse.SearchMediaQueueResponseNotifyConfig searchMediaQueueResponseNotifyConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                searchMediaQueueResponseNotifyConfig.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Type", new ChildElementBinder<SearchMediaQueueResponse.SearchMediaQueueResponseNotifyConfig>() { // from class: com.tencent.cos.xml.model.ci.media.SearchMediaQueueResponse$SearchMediaQueueResponseNotifyConfig$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SearchMediaQueueResponse.SearchMediaQueueResponseNotifyConfig searchMediaQueueResponseNotifyConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                searchMediaQueueResponseNotifyConfig.type = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Event", new ChildElementBinder<SearchMediaQueueResponse.SearchMediaQueueResponseNotifyConfig>() { // from class: com.tencent.cos.xml.model.ci.media.SearchMediaQueueResponse$SearchMediaQueueResponseNotifyConfig$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SearchMediaQueueResponse.SearchMediaQueueResponseNotifyConfig searchMediaQueueResponseNotifyConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                searchMediaQueueResponseNotifyConfig.event = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("ResultFormat", new ChildElementBinder<SearchMediaQueueResponse.SearchMediaQueueResponseNotifyConfig>() { // from class: com.tencent.cos.xml.model.ci.media.SearchMediaQueueResponse$SearchMediaQueueResponseNotifyConfig$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SearchMediaQueueResponse.SearchMediaQueueResponseNotifyConfig searchMediaQueueResponseNotifyConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                searchMediaQueueResponseNotifyConfig.resultFormat = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SearchMediaQueueResponse.SearchMediaQueueResponseNotifyConfig fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SearchMediaQueueResponse.SearchMediaQueueResponseNotifyConfig searchMediaQueueResponseNotifyConfig = new SearchMediaQueueResponse.SearchMediaQueueResponseNotifyConfig();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SearchMediaQueueResponse.SearchMediaQueueResponseNotifyConfig> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, searchMediaQueueResponseNotifyConfig, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "NotifyConfig" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return searchMediaQueueResponseNotifyConfig;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return searchMediaQueueResponseNotifyConfig;
    }
}
