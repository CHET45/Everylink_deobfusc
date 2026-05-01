package com.tencent.cos.xml.model.p033ci.audit;

import com.tencent.cos.xml.model.p033ci.audit.GetStrategyDetailResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetStrategyDetailResponse$$XmlAdapter extends IXmlAdapter<GetStrategyDetailResponse> {
    private HashMap<String, ChildElementBinder<GetStrategyDetailResponse>> childElementBinders;

    public GetStrategyDetailResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetStrategyDetailResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("RequestId", new ChildElementBinder<GetStrategyDetailResponse>() { // from class: com.tencent.cos.xml.model.ci.audit.GetStrategyDetailResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetStrategyDetailResponse getStrategyDetailResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getStrategyDetailResponse.requestId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Strategy", new ChildElementBinder<GetStrategyDetailResponse>() { // from class: com.tencent.cos.xml.model.ci.audit.GetStrategyDetailResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetStrategyDetailResponse getStrategyDetailResponse, String str) throws XmlPullParserException, IOException {
                getStrategyDetailResponse.strategy = (GetStrategyDetailResponse.Strategy) QCloudXml.fromXml(xmlPullParser, GetStrategyDetailResponse.Strategy.class, "Strategy");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetStrategyDetailResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetStrategyDetailResponse getStrategyDetailResponse = new GetStrategyDetailResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetStrategyDetailResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getStrategyDetailResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getStrategyDetailResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getStrategyDetailResponse;
    }
}
