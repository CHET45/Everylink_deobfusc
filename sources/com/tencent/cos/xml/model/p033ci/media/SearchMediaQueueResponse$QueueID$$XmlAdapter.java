package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SearchMediaQueueResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class SearchMediaQueueResponse$QueueID$$XmlAdapter extends IXmlAdapter<SearchMediaQueueResponse.QueueID> {
    private HashMap<String, ChildElementBinder<SearchMediaQueueResponse.QueueID>> childElementBinders;

    public SearchMediaQueueResponse$QueueID$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SearchMediaQueueResponse.QueueID>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("NonExistPIDs", new ChildElementBinder<SearchMediaQueueResponse.QueueID>() { // from class: com.tencent.cos.xml.model.ci.media.SearchMediaQueueResponse$QueueID$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SearchMediaQueueResponse.QueueID queueID, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                queueID.queueID = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SearchMediaQueueResponse.QueueID fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SearchMediaQueueResponse.QueueID queueID = new SearchMediaQueueResponse.QueueID();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SearchMediaQueueResponse.QueueID> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, queueID, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "NonExistPIDs" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return queueID;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return queueID;
    }
}
