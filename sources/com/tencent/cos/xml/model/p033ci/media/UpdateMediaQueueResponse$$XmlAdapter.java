package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.queue.QueueConstants;
import com.tencent.cos.xml.model.p033ci.media.UpdateMediaQueueResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class UpdateMediaQueueResponse$$XmlAdapter extends IXmlAdapter<UpdateMediaQueueResponse> {
    private HashMap<String, ChildElementBinder<UpdateMediaQueueResponse>> childElementBinders;

    public UpdateMediaQueueResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<UpdateMediaQueueResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("RequestId", new ChildElementBinder<UpdateMediaQueueResponse>() { // from class: com.tencent.cos.xml.model.ci.media.UpdateMediaQueueResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateMediaQueueResponse updateMediaQueueResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                updateMediaQueueResponse.requestId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(QueueConstants.QUEUE_ELEMENT, new ChildElementBinder<UpdateMediaQueueResponse>() { // from class: com.tencent.cos.xml.model.ci.media.UpdateMediaQueueResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateMediaQueueResponse updateMediaQueueResponse, String str) throws XmlPullParserException, IOException {
                updateMediaQueueResponse.queue = (UpdateMediaQueueResponse.UpdateMediaQueueResponseQueue) QCloudXml.fromXml(xmlPullParser, UpdateMediaQueueResponse.UpdateMediaQueueResponseQueue.class, QueueConstants.QUEUE_ELEMENT);
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public UpdateMediaQueueResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        UpdateMediaQueueResponse updateMediaQueueResponse = new UpdateMediaQueueResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<UpdateMediaQueueResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, updateMediaQueueResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return updateMediaQueueResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return updateMediaQueueResponse;
    }
}
