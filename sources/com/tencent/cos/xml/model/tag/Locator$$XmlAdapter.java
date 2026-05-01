package com.tencent.cos.xml.model.tag;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class Locator$$XmlAdapter extends IXmlAdapter<Locator> {
    private HashMap<String, ChildElementBinder<Locator>> childElementBinders;

    public Locator$$XmlAdapter() {
        HashMap<String, ChildElementBinder<Locator>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Region", new ChildElementBinder<Locator>() { // from class: com.tencent.cos.xml.model.tag.Locator$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, Locator locator, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                locator.region = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Bucket", new ChildElementBinder<Locator>() { // from class: com.tencent.cos.xml.model.tag.Locator$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, Locator locator, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                locator.bucket = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Object", new ChildElementBinder<Locator>() { // from class: com.tencent.cos.xml.model.tag.Locator$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, Locator locator, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                locator.object = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public Locator fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        Locator locator = new Locator();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<Locator> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, locator, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Locator" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return locator;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return locator;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, Locator locator, String str) throws XmlPullParserException, IOException {
        if (locator == null) {
            return;
        }
        if (str == null) {
            str = "Locator";
        }
        xmlSerializer.startTag("", str);
        if (locator.region != null) {
            xmlSerializer.startTag("", "Region");
            xmlSerializer.text(String.valueOf(locator.region));
            xmlSerializer.endTag("", "Region");
        }
        if (locator.bucket != null) {
            xmlSerializer.startTag("", "Bucket");
            xmlSerializer.text(String.valueOf(locator.bucket));
            xmlSerializer.endTag("", "Bucket");
        }
        if (locator.object != null) {
            xmlSerializer.startTag("", "Object");
            xmlSerializer.text(String.valueOf(locator.object));
            xmlSerializer.endTag("", "Object");
        }
        xmlSerializer.endTag("", str);
    }
}
