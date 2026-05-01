package com.tencent.cos.xml.model.tag;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.tag.IntelligentTieringConfiguration;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class IntelligentTieringConfiguration$Transition$$XmlAdapter extends IXmlAdapter<IntelligentTieringConfiguration.Transition> {
    private HashMap<String, ChildElementBinder<IntelligentTieringConfiguration.Transition>> childElementBinders;

    public IntelligentTieringConfiguration$Transition$$XmlAdapter() {
        HashMap<String, ChildElementBinder<IntelligentTieringConfiguration.Transition>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.AnalyticsConstants.DAYS_ELEMENT, new ChildElementBinder<IntelligentTieringConfiguration.Transition>() { // from class: com.tencent.cos.xml.model.tag.IntelligentTieringConfiguration$Transition$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, IntelligentTieringConfiguration.Transition transition, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                transition.days = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("RequestFrequent", new ChildElementBinder<IntelligentTieringConfiguration.Transition>() { // from class: com.tencent.cos.xml.model.tag.IntelligentTieringConfiguration$Transition$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, IntelligentTieringConfiguration.Transition transition, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                transition.requestFrequent = Integer.parseInt(xmlPullParser.getText());
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public IntelligentTieringConfiguration.Transition fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        IntelligentTieringConfiguration.Transition transition = new IntelligentTieringConfiguration.Transition();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<IntelligentTieringConfiguration.Transition> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, transition, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Transition" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return transition;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return transition;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, IntelligentTieringConfiguration.Transition transition, String str) throws XmlPullParserException, IOException {
        if (transition == null) {
            return;
        }
        if (str == null) {
            str = "Transition";
        }
        xmlSerializer.startTag("", str);
        xmlSerializer.startTag("", Constants.AnalyticsConstants.DAYS_ELEMENT);
        xmlSerializer.text(String.valueOf(transition.days));
        xmlSerializer.endTag("", Constants.AnalyticsConstants.DAYS_ELEMENT);
        xmlSerializer.startTag("", "RequestFrequent");
        xmlSerializer.text(String.valueOf(transition.requestFrequent));
        xmlSerializer.endTag("", "RequestFrequent");
        xmlSerializer.endTag("", str);
    }
}
