package com.tencent.cos.xml.model.p033ci;

import com.tencent.cos.xml.model.p033ci.BaseDescribeQueuesResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class BaseDescribeQueuesResponse$$XmlAdapter extends IXmlAdapter<BaseDescribeQueuesResponse> {
    private HashMap<String, ChildElementBinder<BaseDescribeQueuesResponse>> childElementBinders;

    public BaseDescribeQueuesResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<BaseDescribeQueuesResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("RequestId", new ChildElementBinder<BaseDescribeQueuesResponse>() { // from class: com.tencent.cos.xml.model.ci.BaseDescribeQueuesResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, BaseDescribeQueuesResponse baseDescribeQueuesResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                baseDescribeQueuesResponse.requestId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("TotalCount", new ChildElementBinder<BaseDescribeQueuesResponse>() { // from class: com.tencent.cos.xml.model.ci.BaseDescribeQueuesResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, BaseDescribeQueuesResponse baseDescribeQueuesResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                baseDescribeQueuesResponse.totalCount = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("PageNumber", new ChildElementBinder<BaseDescribeQueuesResponse>() { // from class: com.tencent.cos.xml.model.ci.BaseDescribeQueuesResponse$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, BaseDescribeQueuesResponse baseDescribeQueuesResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                baseDescribeQueuesResponse.pageNumber = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("PageSize", new ChildElementBinder<BaseDescribeQueuesResponse>() { // from class: com.tencent.cos.xml.model.ci.BaseDescribeQueuesResponse$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, BaseDescribeQueuesResponse baseDescribeQueuesResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                baseDescribeQueuesResponse.pageSize = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("QueueList", new ChildElementBinder<BaseDescribeQueuesResponse>() { // from class: com.tencent.cos.xml.model.ci.BaseDescribeQueuesResponse$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, BaseDescribeQueuesResponse baseDescribeQueuesResponse, String str) throws XmlPullParserException, IOException {
                if (baseDescribeQueuesResponse.queueList == null) {
                    baseDescribeQueuesResponse.queueList = new ArrayList();
                }
                baseDescribeQueuesResponse.queueList.add((BaseDescribeQueuesResponse.Queue) QCloudXml.fromXml(xmlPullParser, BaseDescribeQueuesResponse.Queue.class, "QueueList"));
            }
        });
        this.childElementBinders.put("NonExistPIDs", new ChildElementBinder<BaseDescribeQueuesResponse>() { // from class: com.tencent.cos.xml.model.ci.BaseDescribeQueuesResponse$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, BaseDescribeQueuesResponse baseDescribeQueuesResponse, String str) throws XmlPullParserException, IOException {
                if (baseDescribeQueuesResponse.nonExistPIDs == null) {
                    baseDescribeQueuesResponse.nonExistPIDs = new ArrayList();
                }
                baseDescribeQueuesResponse.nonExistPIDs.add((BaseDescribeQueuesResponse.QueueID) QCloudXml.fromXml(xmlPullParser, BaseDescribeQueuesResponse.QueueID.class, "NonExistPIDs"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public BaseDescribeQueuesResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        BaseDescribeQueuesResponse baseDescribeQueuesResponse = new BaseDescribeQueuesResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<BaseDescribeQueuesResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, baseDescribeQueuesResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return baseDescribeQueuesResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return baseDescribeQueuesResponse;
    }
}
