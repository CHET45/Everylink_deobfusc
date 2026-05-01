package com.tencent.cos.xml.model.tag;

import com.tencent.cos.xml.model.tag.IntelligentTieringConfiguration;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class IntelligentTieringConfiguration$$XmlAdapter extends IXmlAdapter<IntelligentTieringConfiguration> {
    private HashMap<String, ChildElementBinder<IntelligentTieringConfiguration>> childElementBinders;

    public IntelligentTieringConfiguration$$XmlAdapter() {
        HashMap<String, ChildElementBinder<IntelligentTieringConfiguration>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Status", new ChildElementBinder<IntelligentTieringConfiguration>() { // from class: com.tencent.cos.xml.model.tag.IntelligentTieringConfiguration$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, IntelligentTieringConfiguration intelligentTieringConfiguration, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                intelligentTieringConfiguration.status = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Transition", new ChildElementBinder<IntelligentTieringConfiguration>() { // from class: com.tencent.cos.xml.model.tag.IntelligentTieringConfiguration$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, IntelligentTieringConfiguration intelligentTieringConfiguration, String str) throws XmlPullParserException, IOException {
                intelligentTieringConfiguration.transition = (IntelligentTieringConfiguration.Transition) QCloudXml.fromXml(xmlPullParser, IntelligentTieringConfiguration.Transition.class, "Transition");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public IntelligentTieringConfiguration fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        IntelligentTieringConfiguration intelligentTieringConfiguration = new IntelligentTieringConfiguration();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<IntelligentTieringConfiguration> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, intelligentTieringConfiguration, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "IntelligentTieringConfiguration" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return intelligentTieringConfiguration;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return intelligentTieringConfiguration;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, IntelligentTieringConfiguration intelligentTieringConfiguration, String str) throws XmlPullParserException, IOException {
        if (intelligentTieringConfiguration == null) {
            return;
        }
        if (str == null) {
            str = "IntelligentTieringConfiguration";
        }
        xmlSerializer.startTag("", str);
        if (intelligentTieringConfiguration.status != null) {
            xmlSerializer.startTag("", "Status");
            xmlSerializer.text(String.valueOf(intelligentTieringConfiguration.status));
            xmlSerializer.endTag("", "Status");
        }
        if (intelligentTieringConfiguration.transition != null) {
            QCloudXml.toXml(xmlSerializer, intelligentTieringConfiguration.transition, "Transition");
        }
        xmlSerializer.endTag("", str);
    }
}
