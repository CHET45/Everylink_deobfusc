package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SearchMediaQueueResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class SearchMediaQueueResponse$$XmlAdapter extends IXmlAdapter<SearchMediaQueueResponse> {
    private HashMap<String, ChildElementBinder<SearchMediaQueueResponse>> childElementBinders;

    public SearchMediaQueueResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SearchMediaQueueResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("RequestId", new ChildElementBinder<SearchMediaQueueResponse>() { // from class: com.tencent.cos.xml.model.ci.media.SearchMediaQueueResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SearchMediaQueueResponse searchMediaQueueResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                searchMediaQueueResponse.requestId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("TotalCount", new ChildElementBinder<SearchMediaQueueResponse>() { // from class: com.tencent.cos.xml.model.ci.media.SearchMediaQueueResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SearchMediaQueueResponse searchMediaQueueResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                searchMediaQueueResponse.totalCount = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("PageNumber", new ChildElementBinder<SearchMediaQueueResponse>() { // from class: com.tencent.cos.xml.model.ci.media.SearchMediaQueueResponse$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SearchMediaQueueResponse searchMediaQueueResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                searchMediaQueueResponse.pageNumber = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("PageSize", new ChildElementBinder<SearchMediaQueueResponse>() { // from class: com.tencent.cos.xml.model.ci.media.SearchMediaQueueResponse$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SearchMediaQueueResponse searchMediaQueueResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                searchMediaQueueResponse.pageSize = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("QueueList", new ChildElementBinder<SearchMediaQueueResponse>() { // from class: com.tencent.cos.xml.model.ci.media.SearchMediaQueueResponse$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SearchMediaQueueResponse searchMediaQueueResponse, String str) throws XmlPullParserException, IOException {
                if (searchMediaQueueResponse.queueList == null) {
                    searchMediaQueueResponse.queueList = new ArrayList();
                }
                searchMediaQueueResponse.queueList.add((SearchMediaQueueResponse.SearchMediaQueueResponseQueueList) QCloudXml.fromXml(xmlPullParser, SearchMediaQueueResponse.SearchMediaQueueResponseQueueList.class, "QueueList"));
            }
        });
        this.childElementBinders.put("NonExistPIDs", new ChildElementBinder<SearchMediaQueueResponse>() { // from class: com.tencent.cos.xml.model.ci.media.SearchMediaQueueResponse$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SearchMediaQueueResponse searchMediaQueueResponse, String str) throws XmlPullParserException, IOException {
                if (searchMediaQueueResponse.nonExistPIDs == null) {
                    searchMediaQueueResponse.nonExistPIDs = new ArrayList();
                }
                searchMediaQueueResponse.nonExistPIDs.add((SearchMediaQueueResponse.QueueID) QCloudXml.fromXml(xmlPullParser, SearchMediaQueueResponse.QueueID.class, "NonExistPIDs"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SearchMediaQueueResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SearchMediaQueueResponse searchMediaQueueResponse = new SearchMediaQueueResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SearchMediaQueueResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, searchMediaQueueResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return searchMediaQueueResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return searchMediaQueueResponse;
    }
}
