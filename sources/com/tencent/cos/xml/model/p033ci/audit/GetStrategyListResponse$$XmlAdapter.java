package com.tencent.cos.xml.model.p033ci.audit;

import com.tencent.cos.xml.model.p033ci.audit.GetStrategyListResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetStrategyListResponse$$XmlAdapter extends IXmlAdapter<GetStrategyListResponse> {
    private HashMap<String, ChildElementBinder<GetStrategyListResponse>> childElementBinders;

    public GetStrategyListResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetStrategyListResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("RequestId", new ChildElementBinder<GetStrategyListResponse>() { // from class: com.tencent.cos.xml.model.ci.audit.GetStrategyListResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetStrategyListResponse getStrategyListResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getStrategyListResponse.requestId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("TotalCount", new ChildElementBinder<GetStrategyListResponse>() { // from class: com.tencent.cos.xml.model.ci.audit.GetStrategyListResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetStrategyListResponse getStrategyListResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getStrategyListResponse.totalCount = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Strategies", new ChildElementBinder<GetStrategyListResponse>() { // from class: com.tencent.cos.xml.model.ci.audit.GetStrategyListResponse$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetStrategyListResponse getStrategyListResponse, String str) throws XmlPullParserException, IOException {
                if (getStrategyListResponse.strategies == null) {
                    getStrategyListResponse.strategies = new ArrayList();
                }
                getStrategyListResponse.strategies.add((GetStrategyListResponse.Strategies) QCloudXml.fromXml(xmlPullParser, GetStrategyListResponse.Strategies.class, "Strategies"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetStrategyListResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetStrategyListResponse getStrategyListResponse = new GetStrategyListResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetStrategyListResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getStrategyListResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getStrategyListResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getStrategyListResponse;
    }
}
