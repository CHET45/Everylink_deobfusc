package com.tencent.cos.xml.model.tag;

import com.tencent.cos.xml.model.tag.RefererConfiguration;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class RefererConfiguration$Domain$$XmlAdapter extends IXmlAdapter<RefererConfiguration.Domain> {
    private HashMap<String, ChildElementBinder<RefererConfiguration.Domain>> childElementBinders;

    public RefererConfiguration$Domain$$XmlAdapter() {
        HashMap<String, ChildElementBinder<RefererConfiguration.Domain>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Domain", new ChildElementBinder<RefererConfiguration.Domain>() { // from class: com.tencent.cos.xml.model.tag.RefererConfiguration$Domain$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, RefererConfiguration.Domain domain, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                domain.domain = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public RefererConfiguration.Domain fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        RefererConfiguration.Domain domain = new RefererConfiguration.Domain();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<RefererConfiguration.Domain> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, domain, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Domain" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return domain;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return domain;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, RefererConfiguration.Domain domain, String str) throws XmlPullParserException, IOException {
        if (domain == null) {
            return;
        }
        if (str == null) {
            str = "Domain";
        }
        xmlSerializer.startTag("", str);
        if (domain.domain != null) {
            xmlSerializer.text(String.valueOf(domain.domain));
        }
        xmlSerializer.endTag("", str);
    }
}
