package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class TriggerWorkflowResponse$$XmlAdapter extends IXmlAdapter<TriggerWorkflowResponse> {
    private HashMap<String, ChildElementBinder<TriggerWorkflowResponse>> childElementBinders;

    public TriggerWorkflowResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TriggerWorkflowResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("RequestId", new ChildElementBinder<TriggerWorkflowResponse>() { // from class: com.tencent.cos.xml.model.ci.media.TriggerWorkflowResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TriggerWorkflowResponse triggerWorkflowResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                triggerWorkflowResponse.requestId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("InstanceId", new ChildElementBinder<TriggerWorkflowResponse>() { // from class: com.tencent.cos.xml.model.ci.media.TriggerWorkflowResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TriggerWorkflowResponse triggerWorkflowResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                triggerWorkflowResponse.instanceId = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TriggerWorkflowResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TriggerWorkflowResponse triggerWorkflowResponse = new TriggerWorkflowResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TriggerWorkflowResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, triggerWorkflowResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return triggerWorkflowResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return triggerWorkflowResponse;
    }
}
