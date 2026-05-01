package com.tencent.cos.xml.model.p033ci.p034ai;

import com.microsoft.azure.storage.Constants;
import com.microsoft.azure.storage.queue.QueueConstants;
import com.tencent.cos.xml.model.p033ci.common.NotifyConfig;
import com.tencent.cos.xml.model.p033ci.p034ai.UpdateAIQueueResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class UpdateAIQueueResponse$UpdateAIQueueResponseQueue$$XmlAdapter extends IXmlAdapter<UpdateAIQueueResponse.UpdateAIQueueResponseQueue> {
    private HashMap<String, ChildElementBinder<UpdateAIQueueResponse.UpdateAIQueueResponseQueue>> childElementBinders;

    public UpdateAIQueueResponse$UpdateAIQueueResponseQueue$$XmlAdapter() {
        HashMap<String, ChildElementBinder<UpdateAIQueueResponse.UpdateAIQueueResponseQueue>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("QueueId", new ChildElementBinder<UpdateAIQueueResponse.UpdateAIQueueResponseQueue>() { // from class: com.tencent.cos.xml.model.ci.ai.UpdateAIQueueResponse$UpdateAIQueueResponseQueue$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateAIQueueResponse.UpdateAIQueueResponseQueue updateAIQueueResponseQueue, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                updateAIQueueResponseQueue.queueId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.NAME_ELEMENT, new ChildElementBinder<UpdateAIQueueResponse.UpdateAIQueueResponseQueue>() { // from class: com.tencent.cos.xml.model.ci.ai.UpdateAIQueueResponse$UpdateAIQueueResponseQueue$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateAIQueueResponse.UpdateAIQueueResponseQueue updateAIQueueResponseQueue, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                updateAIQueueResponseQueue.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<UpdateAIQueueResponse.UpdateAIQueueResponseQueue>() { // from class: com.tencent.cos.xml.model.ci.ai.UpdateAIQueueResponse$UpdateAIQueueResponseQueue$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateAIQueueResponse.UpdateAIQueueResponseQueue updateAIQueueResponseQueue, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                updateAIQueueResponseQueue.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("NotifyConfig", new ChildElementBinder<UpdateAIQueueResponse.UpdateAIQueueResponseQueue>() { // from class: com.tencent.cos.xml.model.ci.ai.UpdateAIQueueResponse$UpdateAIQueueResponseQueue$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateAIQueueResponse.UpdateAIQueueResponseQueue updateAIQueueResponseQueue, String str) throws XmlPullParserException, IOException {
                updateAIQueueResponseQueue.notifyConfig = (NotifyConfig) QCloudXml.fromXml(xmlPullParser, NotifyConfig.class, "NotifyConfig");
            }
        });
        this.childElementBinders.put("MaxSize", new ChildElementBinder<UpdateAIQueueResponse.UpdateAIQueueResponseQueue>() { // from class: com.tencent.cos.xml.model.ci.ai.UpdateAIQueueResponse$UpdateAIQueueResponseQueue$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateAIQueueResponse.UpdateAIQueueResponseQueue updateAIQueueResponseQueue, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                updateAIQueueResponseQueue.maxSize = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("MaxConcurrent", new ChildElementBinder<UpdateAIQueueResponse.UpdateAIQueueResponseQueue>() { // from class: com.tencent.cos.xml.model.ci.ai.UpdateAIQueueResponse$UpdateAIQueueResponseQueue$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateAIQueueResponse.UpdateAIQueueResponseQueue updateAIQueueResponseQueue, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                updateAIQueueResponseQueue.maxConcurrent = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Category", new ChildElementBinder<UpdateAIQueueResponse.UpdateAIQueueResponseQueue>() { // from class: com.tencent.cos.xml.model.ci.ai.UpdateAIQueueResponse$UpdateAIQueueResponseQueue$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateAIQueueResponse.UpdateAIQueueResponseQueue updateAIQueueResponseQueue, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                updateAIQueueResponseQueue.category = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("UpdateTime", new ChildElementBinder<UpdateAIQueueResponse.UpdateAIQueueResponseQueue>() { // from class: com.tencent.cos.xml.model.ci.ai.UpdateAIQueueResponse$UpdateAIQueueResponseQueue$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateAIQueueResponse.UpdateAIQueueResponseQueue updateAIQueueResponseQueue, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                updateAIQueueResponseQueue.updateTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreateTime", new ChildElementBinder<UpdateAIQueueResponse.UpdateAIQueueResponseQueue>() { // from class: com.tencent.cos.xml.model.ci.ai.UpdateAIQueueResponse$UpdateAIQueueResponseQueue$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateAIQueueResponse.UpdateAIQueueResponseQueue updateAIQueueResponseQueue, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                updateAIQueueResponseQueue.createTime = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public UpdateAIQueueResponse.UpdateAIQueueResponseQueue fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        UpdateAIQueueResponse.UpdateAIQueueResponseQueue updateAIQueueResponseQueue = new UpdateAIQueueResponse.UpdateAIQueueResponseQueue();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<UpdateAIQueueResponse.UpdateAIQueueResponseQueue> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, updateAIQueueResponseQueue, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? QueueConstants.QUEUE_ELEMENT : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return updateAIQueueResponseQueue;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return updateAIQueueResponseQueue;
    }
}
