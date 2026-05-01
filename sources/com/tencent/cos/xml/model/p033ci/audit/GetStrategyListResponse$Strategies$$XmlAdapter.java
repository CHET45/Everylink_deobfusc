package com.tencent.cos.xml.model.p033ci.audit;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.audit.GetStrategyListResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetStrategyListResponse$Strategies$$XmlAdapter extends IXmlAdapter<GetStrategyListResponse.Strategies> {
    private HashMap<String, ChildElementBinder<GetStrategyListResponse.Strategies>> childElementBinders;

    public GetStrategyListResponse$Strategies$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetStrategyListResponse.Strategies>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.NAME_ELEMENT, new ChildElementBinder<GetStrategyListResponse.Strategies>() { // from class: com.tencent.cos.xml.model.ci.audit.GetStrategyListResponse$Strategies$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetStrategyListResponse.Strategies strategies, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                strategies.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Service", new ChildElementBinder<GetStrategyListResponse.Strategies>() { // from class: com.tencent.cos.xml.model.ci.audit.GetStrategyListResponse$Strategies$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetStrategyListResponse.Strategies strategies, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                strategies.service = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BizType", new ChildElementBinder<GetStrategyListResponse.Strategies>() { // from class: com.tencent.cos.xml.model.ci.audit.GetStrategyListResponse$Strategies$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetStrategyListResponse.Strategies strategies, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                strategies.bizType = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("IsDefault", new ChildElementBinder<GetStrategyListResponse.Strategies>() { // from class: com.tencent.cos.xml.model.ci.audit.GetStrategyListResponse$Strategies$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetStrategyListResponse.Strategies strategies, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                strategies.isDefault = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreateTime", new ChildElementBinder<GetStrategyListResponse.Strategies>() { // from class: com.tencent.cos.xml.model.ci.audit.GetStrategyListResponse$Strategies$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetStrategyListResponse.Strategies strategies, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                strategies.createTime = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetStrategyListResponse.Strategies fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetStrategyListResponse.Strategies strategies = new GetStrategyListResponse.Strategies();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetStrategyListResponse.Strategies> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, strategies, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Strategies" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return strategies;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return strategies;
    }
}
