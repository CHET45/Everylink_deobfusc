package com.tencent.cos.xml.model.p033ci.audit;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.audit.CreateStrategy;
import com.tencent.cos.xml.model.p033ci.audit.GetStrategyDetailResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetStrategyDetailResponse$Strategy$$XmlAdapter extends IXmlAdapter<GetStrategyDetailResponse.Strategy> {
    private HashMap<String, ChildElementBinder<GetStrategyDetailResponse.Strategy>> childElementBinders;

    public GetStrategyDetailResponse$Strategy$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetStrategyDetailResponse.Strategy>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.NAME_ELEMENT, new ChildElementBinder<GetStrategyDetailResponse.Strategy>() { // from class: com.tencent.cos.xml.model.ci.audit.GetStrategyDetailResponse$Strategy$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetStrategyDetailResponse.Strategy strategy, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                strategy.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Service", new ChildElementBinder<GetStrategyDetailResponse.Strategy>() { // from class: com.tencent.cos.xml.model.ci.audit.GetStrategyDetailResponse$Strategy$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetStrategyDetailResponse.Strategy strategy, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                strategy.service = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BizType", new ChildElementBinder<GetStrategyDetailResponse.Strategy>() { // from class: com.tencent.cos.xml.model.ci.audit.GetStrategyDetailResponse$Strategy$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetStrategyDetailResponse.Strategy strategy, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                strategy.bizType = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("TextLibs", new ChildElementBinder<GetStrategyDetailResponse.Strategy>() { // from class: com.tencent.cos.xml.model.ci.audit.GetStrategyDetailResponse$Strategy$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetStrategyDetailResponse.Strategy strategy, String str) throws XmlPullParserException, IOException {
                if (strategy.textLibs == null) {
                    strategy.textLibs = new ArrayList();
                }
                int eventType = xmlPullParser.getEventType();
                while (eventType != 1) {
                    if (eventType == 2) {
                        xmlPullParser.next();
                        strategy.textLibs.add(xmlPullParser.getText());
                    } else if (eventType == 3 && "TextLibs".equalsIgnoreCase(xmlPullParser.getName())) {
                        return;
                    }
                    eventType = xmlPullParser.next();
                }
            }
        });
        this.childElementBinders.put("Labels", new ChildElementBinder<GetStrategyDetailResponse.Strategy>() { // from class: com.tencent.cos.xml.model.ci.audit.GetStrategyDetailResponse$Strategy$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetStrategyDetailResponse.Strategy strategy, String str) throws XmlPullParserException, IOException {
                strategy.labels = (CreateStrategy.Labels) QCloudXml.fromXml(xmlPullParser, CreateStrategy.Labels.class, "Labels");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetStrategyDetailResponse.Strategy fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetStrategyDetailResponse.Strategy strategy = new GetStrategyDetailResponse.Strategy();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetStrategyDetailResponse.Strategy> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, strategy, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Strategy" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return strategy;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return strategy;
    }
}
