package com.tencent.cos.xml.model.p033ci.audit;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class CreateStrategyResponse$$XmlAdapter extends IXmlAdapter<CreateStrategyResponse> {
    private HashMap<String, ChildElementBinder<CreateStrategyResponse>> childElementBinders;

    public CreateStrategyResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<CreateStrategyResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("BizType", new ChildElementBinder<CreateStrategyResponse>() { // from class: com.tencent.cos.xml.model.ci.audit.CreateStrategyResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CreateStrategyResponse createStrategyResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                createStrategyResponse.bizType = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("RequestId", new ChildElementBinder<CreateStrategyResponse>() { // from class: com.tencent.cos.xml.model.ci.audit.CreateStrategyResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CreateStrategyResponse createStrategyResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                createStrategyResponse.requestId = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public CreateStrategyResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        CreateStrategyResponse createStrategyResponse = new CreateStrategyResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<CreateStrategyResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, createStrategyResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return createStrategyResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return createStrategyResponse;
    }
}
