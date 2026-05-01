package com.tencent.cos.xml.model.p033ci.audit;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class UpdateStrategyResponse$$XmlAdapter extends IXmlAdapter<UpdateStrategyResponse> {
    private HashMap<String, ChildElementBinder<UpdateStrategyResponse>> childElementBinders;

    public UpdateStrategyResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<UpdateStrategyResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("BizType", new ChildElementBinder<UpdateStrategyResponse>() { // from class: com.tencent.cos.xml.model.ci.audit.UpdateStrategyResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateStrategyResponse updateStrategyResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                updateStrategyResponse.bizType = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("RequestId", new ChildElementBinder<UpdateStrategyResponse>() { // from class: com.tencent.cos.xml.model.ci.audit.UpdateStrategyResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateStrategyResponse updateStrategyResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                updateStrategyResponse.requestId = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public UpdateStrategyResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        UpdateStrategyResponse updateStrategyResponse = new UpdateStrategyResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<UpdateStrategyResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, updateStrategyResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return updateStrategyResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return updateStrategyResponse;
    }
}
