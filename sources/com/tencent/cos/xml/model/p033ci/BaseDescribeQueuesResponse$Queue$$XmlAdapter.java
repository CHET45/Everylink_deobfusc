package com.tencent.cos.xml.model.p033ci;

import com.microsoft.azure.storage.Constants;
import com.microsoft.azure.storage.queue.QueueConstants;
import com.tencent.cos.xml.model.p033ci.BaseDescribeQueuesResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class BaseDescribeQueuesResponse$Queue$$XmlAdapter extends IXmlAdapter<BaseDescribeQueuesResponse.Queue> {
    private HashMap<String, ChildElementBinder<BaseDescribeQueuesResponse.Queue>> childElementBinders;

    public BaseDescribeQueuesResponse$Queue$$XmlAdapter() {
        HashMap<String, ChildElementBinder<BaseDescribeQueuesResponse.Queue>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("QueueId", new ChildElementBinder<BaseDescribeQueuesResponse.Queue>() { // from class: com.tencent.cos.xml.model.ci.BaseDescribeQueuesResponse$Queue$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, BaseDescribeQueuesResponse.Queue queue, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                queue.queueId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.NAME_ELEMENT, new ChildElementBinder<BaseDescribeQueuesResponse.Queue>() { // from class: com.tencent.cos.xml.model.ci.BaseDescribeQueuesResponse$Queue$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, BaseDescribeQueuesResponse.Queue queue, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                queue.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<BaseDescribeQueuesResponse.Queue>() { // from class: com.tencent.cos.xml.model.ci.BaseDescribeQueuesResponse$Queue$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, BaseDescribeQueuesResponse.Queue queue, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                queue.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("MaxSize", new ChildElementBinder<BaseDescribeQueuesResponse.Queue>() { // from class: com.tencent.cos.xml.model.ci.BaseDescribeQueuesResponse$Queue$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, BaseDescribeQueuesResponse.Queue queue, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                queue.maxSize = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("MaxConcurrent", new ChildElementBinder<BaseDescribeQueuesResponse.Queue>() { // from class: com.tencent.cos.xml.model.ci.BaseDescribeQueuesResponse$Queue$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, BaseDescribeQueuesResponse.Queue queue, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                queue.maxConcurrent = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("UpdateTime", new ChildElementBinder<BaseDescribeQueuesResponse.Queue>() { // from class: com.tencent.cos.xml.model.ci.BaseDescribeQueuesResponse$Queue$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, BaseDescribeQueuesResponse.Queue queue, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                queue.updateTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreateTime", new ChildElementBinder<BaseDescribeQueuesResponse.Queue>() { // from class: com.tencent.cos.xml.model.ci.BaseDescribeQueuesResponse$Queue$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, BaseDescribeQueuesResponse.Queue queue, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                queue.createTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Category", new ChildElementBinder<BaseDescribeQueuesResponse.Queue>() { // from class: com.tencent.cos.xml.model.ci.BaseDescribeQueuesResponse$Queue$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, BaseDescribeQueuesResponse.Queue queue, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                queue.category = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("NotifyConfig", new ChildElementBinder<BaseDescribeQueuesResponse.Queue>() { // from class: com.tencent.cos.xml.model.ci.BaseDescribeQueuesResponse$Queue$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, BaseDescribeQueuesResponse.Queue queue, String str) throws XmlPullParserException, IOException {
                queue.notifyConfig = (BaseDescribeQueuesResponse.NotifyConfig) QCloudXml.fromXml(xmlPullParser, BaseDescribeQueuesResponse.NotifyConfig.class, "NotifyConfig");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public BaseDescribeQueuesResponse.Queue fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        BaseDescribeQueuesResponse.Queue queue = new BaseDescribeQueuesResponse.Queue();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<BaseDescribeQueuesResponse.Queue> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, queue, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? QueueConstants.QUEUE_ELEMENT : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return queue;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return queue;
    }
}
