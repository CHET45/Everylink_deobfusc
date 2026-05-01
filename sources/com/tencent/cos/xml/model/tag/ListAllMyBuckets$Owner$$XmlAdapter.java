package com.tencent.cos.xml.model.tag;

import com.tencent.cos.xml.model.tag.ListAllMyBuckets;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class ListAllMyBuckets$Owner$$XmlAdapter extends IXmlAdapter<ListAllMyBuckets.Owner> {
    private HashMap<String, ChildElementBinder<ListAllMyBuckets.Owner>> childElementBinders;

    public ListAllMyBuckets$Owner$$XmlAdapter() {
        HashMap<String, ChildElementBinder<ListAllMyBuckets.Owner>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("ID", new ChildElementBinder<ListAllMyBuckets.Owner>() { // from class: com.tencent.cos.xml.model.tag.ListAllMyBuckets$Owner$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ListAllMyBuckets.Owner owner, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                owner.f1836id = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("DisplayName", new ChildElementBinder<ListAllMyBuckets.Owner>() { // from class: com.tencent.cos.xml.model.tag.ListAllMyBuckets$Owner$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ListAllMyBuckets.Owner owner, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                owner.disPlayName = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public ListAllMyBuckets.Owner fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        ListAllMyBuckets.Owner owner = new ListAllMyBuckets.Owner();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<ListAllMyBuckets.Owner> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, owner, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Owner" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return owner;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return owner;
    }
}
