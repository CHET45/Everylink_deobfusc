package com.tencent.cos.xml.model.p033ci.p034ai;

import com.microsoft.azure.storage.Constants;
import com.microsoft.azure.storage.queue.QueueConstants;
import com.tencent.cos.xml.model.p033ci.common.NotifyConfig;
import com.tencent.cos.xml.model.p033ci.p034ai.UpdateAsrQueueResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class UpdateAsrQueueResponse$UpdateAsrQueueResponseQueue$$XmlAdapter extends IXmlAdapter<UpdateAsrQueueResponse.UpdateAsrQueueResponseQueue> {
    private HashMap<String, ChildElementBinder<UpdateAsrQueueResponse.UpdateAsrQueueResponseQueue>> childElementBinders;

    public UpdateAsrQueueResponse$UpdateAsrQueueResponseQueue$$XmlAdapter() {
        HashMap<String, ChildElementBinder<UpdateAsrQueueResponse.UpdateAsrQueueResponseQueue>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("QueueId", new ChildElementBinder<UpdateAsrQueueResponse.UpdateAsrQueueResponseQueue>() { // from class: com.tencent.cos.xml.model.ci.ai.UpdateAsrQueueResponse$UpdateAsrQueueResponseQueue$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateAsrQueueResponse.UpdateAsrQueueResponseQueue updateAsrQueueResponseQueue, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                updateAsrQueueResponseQueue.queueId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.NAME_ELEMENT, new ChildElementBinder<UpdateAsrQueueResponse.UpdateAsrQueueResponseQueue>() { // from class: com.tencent.cos.xml.model.ci.ai.UpdateAsrQueueResponse$UpdateAsrQueueResponseQueue$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateAsrQueueResponse.UpdateAsrQueueResponseQueue updateAsrQueueResponseQueue, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                updateAsrQueueResponseQueue.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<UpdateAsrQueueResponse.UpdateAsrQueueResponseQueue>() { // from class: com.tencent.cos.xml.model.ci.ai.UpdateAsrQueueResponse$UpdateAsrQueueResponseQueue$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateAsrQueueResponse.UpdateAsrQueueResponseQueue updateAsrQueueResponseQueue, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                updateAsrQueueResponseQueue.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("NotifyConfig", new ChildElementBinder<UpdateAsrQueueResponse.UpdateAsrQueueResponseQueue>() { // from class: com.tencent.cos.xml.model.ci.ai.UpdateAsrQueueResponse$UpdateAsrQueueResponseQueue$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateAsrQueueResponse.UpdateAsrQueueResponseQueue updateAsrQueueResponseQueue, String str) throws XmlPullParserException, IOException {
                updateAsrQueueResponseQueue.notifyConfig = (NotifyConfig) QCloudXml.fromXml(xmlPullParser, NotifyConfig.class, "NotifyConfig");
            }
        });
        this.childElementBinders.put("MaxSize", new ChildElementBinder<UpdateAsrQueueResponse.UpdateAsrQueueResponseQueue>() { // from class: com.tencent.cos.xml.model.ci.ai.UpdateAsrQueueResponse$UpdateAsrQueueResponseQueue$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateAsrQueueResponse.UpdateAsrQueueResponseQueue updateAsrQueueResponseQueue, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                updateAsrQueueResponseQueue.maxSize = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("MaxConcurrent", new ChildElementBinder<UpdateAsrQueueResponse.UpdateAsrQueueResponseQueue>() { // from class: com.tencent.cos.xml.model.ci.ai.UpdateAsrQueueResponse$UpdateAsrQueueResponseQueue$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateAsrQueueResponse.UpdateAsrQueueResponseQueue updateAsrQueueResponseQueue, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                updateAsrQueueResponseQueue.maxConcurrent = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Category", new ChildElementBinder<UpdateAsrQueueResponse.UpdateAsrQueueResponseQueue>() { // from class: com.tencent.cos.xml.model.ci.ai.UpdateAsrQueueResponse$UpdateAsrQueueResponseQueue$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateAsrQueueResponse.UpdateAsrQueueResponseQueue updateAsrQueueResponseQueue, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                updateAsrQueueResponseQueue.category = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("UpdateTime", new ChildElementBinder<UpdateAsrQueueResponse.UpdateAsrQueueResponseQueue>() { // from class: com.tencent.cos.xml.model.ci.ai.UpdateAsrQueueResponse$UpdateAsrQueueResponseQueue$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateAsrQueueResponse.UpdateAsrQueueResponseQueue updateAsrQueueResponseQueue, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                updateAsrQueueResponseQueue.updateTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreateTime", new ChildElementBinder<UpdateAsrQueueResponse.UpdateAsrQueueResponseQueue>() { // from class: com.tencent.cos.xml.model.ci.ai.UpdateAsrQueueResponse$UpdateAsrQueueResponseQueue$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateAsrQueueResponse.UpdateAsrQueueResponseQueue updateAsrQueueResponseQueue, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                updateAsrQueueResponseQueue.createTime = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public UpdateAsrQueueResponse.UpdateAsrQueueResponseQueue fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        UpdateAsrQueueResponse.UpdateAsrQueueResponseQueue updateAsrQueueResponseQueue = new UpdateAsrQueueResponse.UpdateAsrQueueResponseQueue();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<UpdateAsrQueueResponse.UpdateAsrQueueResponseQueue> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, updateAsrQueueResponseQueue, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? QueueConstants.QUEUE_ELEMENT : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return updateAsrQueueResponseQueue;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return updateAsrQueueResponseQueue;
    }
}
