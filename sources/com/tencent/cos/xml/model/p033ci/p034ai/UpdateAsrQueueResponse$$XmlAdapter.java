package com.tencent.cos.xml.model.p033ci.p034ai;

import com.microsoft.azure.storage.queue.QueueConstants;
import com.tencent.cos.xml.model.p033ci.p034ai.UpdateAsrQueueResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class UpdateAsrQueueResponse$$XmlAdapter extends IXmlAdapter<UpdateAsrQueueResponse> {
    private HashMap<String, ChildElementBinder<UpdateAsrQueueResponse>> childElementBinders;

    public UpdateAsrQueueResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<UpdateAsrQueueResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("RequestId", new ChildElementBinder<UpdateAsrQueueResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.UpdateAsrQueueResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateAsrQueueResponse updateAsrQueueResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                updateAsrQueueResponse.requestId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(QueueConstants.QUEUE_ELEMENT, new ChildElementBinder<UpdateAsrQueueResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.UpdateAsrQueueResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateAsrQueueResponse updateAsrQueueResponse, String str) throws XmlPullParserException, IOException {
                updateAsrQueueResponse.queue = (UpdateAsrQueueResponse.UpdateAsrQueueResponseQueue) QCloudXml.fromXml(xmlPullParser, UpdateAsrQueueResponse.UpdateAsrQueueResponseQueue.class, QueueConstants.QUEUE_ELEMENT);
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public UpdateAsrQueueResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        UpdateAsrQueueResponse updateAsrQueueResponse = new UpdateAsrQueueResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<UpdateAsrQueueResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, updateAsrQueueResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return updateAsrQueueResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return updateAsrQueueResponse;
    }
}
