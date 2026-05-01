package com.tencent.cos.xml.model.tag;

import com.tencent.cos.xml.model.tag.Tagging;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class Tagging$$XmlAdapter extends IXmlAdapter<Tagging> {
    private HashMap<String, ChildElementBinder<Tagging>> childElementBinders;

    public Tagging$$XmlAdapter() {
        HashMap<String, ChildElementBinder<Tagging>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("TagSet", new ChildElementBinder<Tagging>() { // from class: com.tencent.cos.xml.model.tag.Tagging$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, Tagging tagging, String str) throws XmlPullParserException, IOException {
                tagging.tagSet = (Tagging.TagSet) QCloudXml.fromXml(xmlPullParser, Tagging.TagSet.class, "TagSet");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public Tagging fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        Tagging tagging = new Tagging();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<Tagging> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, tagging, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Tagging" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return tagging;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return tagging;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, Tagging tagging, String str) throws XmlPullParserException, IOException {
        if (tagging == null) {
            return;
        }
        if (str == null) {
            str = "Tagging";
        }
        xmlSerializer.startTag("", str);
        if (tagging.tagSet != null) {
            QCloudXml.toXml(xmlSerializer, tagging.tagSet, "TagSet");
        }
        xmlSerializer.endTag("", str);
    }
}
