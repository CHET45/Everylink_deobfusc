package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetActionSequenceResponse$$XmlAdapter extends IXmlAdapter<GetActionSequenceResponse> {
    private HashMap<String, ChildElementBinder<GetActionSequenceResponse>> childElementBinders;

    public GetActionSequenceResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetActionSequenceResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("ActionSequence", new ChildElementBinder<GetActionSequenceResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.GetActionSequenceResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetActionSequenceResponse getActionSequenceResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getActionSequenceResponse.actionSequence = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetActionSequenceResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetActionSequenceResponse getActionSequenceResponse = new GetActionSequenceResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetActionSequenceResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getActionSequenceResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getActionSequenceResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getActionSequenceResponse;
    }
}
