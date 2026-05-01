package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetLiveCodeResponse$$XmlAdapter extends IXmlAdapter<GetLiveCodeResponse> {
    private HashMap<String, ChildElementBinder<GetLiveCodeResponse>> childElementBinders;

    public GetLiveCodeResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetLiveCodeResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("LiveCode", new ChildElementBinder<GetLiveCodeResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.GetLiveCodeResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveCodeResponse getLiveCodeResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveCodeResponse.liveCode = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetLiveCodeResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetLiveCodeResponse getLiveCodeResponse = new GetLiveCodeResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetLiveCodeResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getLiveCodeResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getLiveCodeResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getLiveCodeResponse;
    }
}
