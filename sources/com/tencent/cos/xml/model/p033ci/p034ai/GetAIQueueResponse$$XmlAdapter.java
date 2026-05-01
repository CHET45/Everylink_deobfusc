package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.GetAIQueueResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetAIQueueResponse$$XmlAdapter extends IXmlAdapter<GetAIQueueResponse> {
    private HashMap<String, ChildElementBinder<GetAIQueueResponse>> childElementBinders;

    public GetAIQueueResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetAIQueueResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("RequestId", new ChildElementBinder<GetAIQueueResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.GetAIQueueResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAIQueueResponse getAIQueueResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getAIQueueResponse.requestId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("TotalCount", new ChildElementBinder<GetAIQueueResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.GetAIQueueResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAIQueueResponse getAIQueueResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getAIQueueResponse.totalCount = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("PageNumber", new ChildElementBinder<GetAIQueueResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.GetAIQueueResponse$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAIQueueResponse getAIQueueResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getAIQueueResponse.pageNumber = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("PageSize", new ChildElementBinder<GetAIQueueResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.GetAIQueueResponse$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAIQueueResponse getAIQueueResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getAIQueueResponse.pageSize = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("QueueList", new ChildElementBinder<GetAIQueueResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.GetAIQueueResponse$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAIQueueResponse getAIQueueResponse, String str) throws XmlPullParserException, IOException {
                if (getAIQueueResponse.queueList == null) {
                    getAIQueueResponse.queueList = new ArrayList();
                }
                getAIQueueResponse.queueList.add((GetAIQueueResponse.GetAIQueueResponseQueueList) QCloudXml.fromXml(xmlPullParser, GetAIQueueResponse.GetAIQueueResponseQueueList.class, "QueueList"));
            }
        });
        this.childElementBinders.put("NonExistPIDs", new ChildElementBinder<GetAIQueueResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.GetAIQueueResponse$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAIQueueResponse getAIQueueResponse, String str) throws XmlPullParserException, IOException {
                if (getAIQueueResponse.nonExistPIDs == null) {
                    getAIQueueResponse.nonExistPIDs = new ArrayList();
                }
                int eventType = xmlPullParser.getEventType();
                while (eventType != 1) {
                    if (eventType == 2) {
                        xmlPullParser.next();
                        getAIQueueResponse.nonExistPIDs.add(xmlPullParser.getText());
                    } else if (eventType == 3 && "NonExistPIDs".equalsIgnoreCase(xmlPullParser.getName())) {
                        return;
                    }
                    eventType = xmlPullParser.next();
                }
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetAIQueueResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetAIQueueResponse getAIQueueResponse = new GetAIQueueResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetAIQueueResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getAIQueueResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getAIQueueResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getAIQueueResponse;
    }
}
