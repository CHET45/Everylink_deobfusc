package com.tencent.cos.xml.model.p033ci;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.BaseDescribeQueuesResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class BaseDescribeQueuesResponse$NotifyConfig$$XmlAdapter extends IXmlAdapter<BaseDescribeQueuesResponse.NotifyConfig> {
    private HashMap<String, ChildElementBinder<BaseDescribeQueuesResponse.NotifyConfig>> childElementBinders;

    public BaseDescribeQueuesResponse$NotifyConfig$$XmlAdapter() {
        HashMap<String, ChildElementBinder<BaseDescribeQueuesResponse.NotifyConfig>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.URL_ELEMENT, new ChildElementBinder<BaseDescribeQueuesResponse.NotifyConfig>() { // from class: com.tencent.cos.xml.model.ci.BaseDescribeQueuesResponse$NotifyConfig$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, BaseDescribeQueuesResponse.NotifyConfig notifyConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                notifyConfig.url = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<BaseDescribeQueuesResponse.NotifyConfig>() { // from class: com.tencent.cos.xml.model.ci.BaseDescribeQueuesResponse$NotifyConfig$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, BaseDescribeQueuesResponse.NotifyConfig notifyConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                notifyConfig.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Type", new ChildElementBinder<BaseDescribeQueuesResponse.NotifyConfig>() { // from class: com.tencent.cos.xml.model.ci.BaseDescribeQueuesResponse$NotifyConfig$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, BaseDescribeQueuesResponse.NotifyConfig notifyConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                notifyConfig.type = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Event", new ChildElementBinder<BaseDescribeQueuesResponse.NotifyConfig>() { // from class: com.tencent.cos.xml.model.ci.BaseDescribeQueuesResponse$NotifyConfig$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, BaseDescribeQueuesResponse.NotifyConfig notifyConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                notifyConfig.event = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("ResultFormat", new ChildElementBinder<BaseDescribeQueuesResponse.NotifyConfig>() { // from class: com.tencent.cos.xml.model.ci.BaseDescribeQueuesResponse$NotifyConfig$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, BaseDescribeQueuesResponse.NotifyConfig notifyConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                notifyConfig.resultFormat = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public BaseDescribeQueuesResponse.NotifyConfig fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        BaseDescribeQueuesResponse.NotifyConfig notifyConfig = new BaseDescribeQueuesResponse.NotifyConfig();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<BaseDescribeQueuesResponse.NotifyConfig> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, notifyConfig, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "NotifyConfig" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return notifyConfig;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return notifyConfig;
    }
}
