package com.tencent.cos.xml.model.tag;

import com.tencent.cos.xml.model.tag.Tagging;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class Tagging$TagSet$$XmlAdapter extends IXmlAdapter<Tagging.TagSet> {
    private HashMap<String, ChildElementBinder<Tagging.TagSet>> childElementBinders;

    public Tagging$TagSet$$XmlAdapter() {
        HashMap<String, ChildElementBinder<Tagging.TagSet>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Tag", new ChildElementBinder<Tagging.TagSet>() { // from class: com.tencent.cos.xml.model.tag.Tagging$TagSet$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, Tagging.TagSet tagSet, String str) throws XmlPullParserException, IOException {
                if (tagSet.tag == null) {
                    tagSet.tag = new ArrayList();
                }
                int eventType = xmlPullParser.getEventType();
                while (eventType != 1) {
                    if (eventType != 2) {
                        if (eventType == 3 && "Tag".equalsIgnoreCase(xmlPullParser.getName())) {
                            return;
                        }
                    } else {
                        tagSet.tag.add((Tagging.Tag) QCloudXml.fromXml(xmlPullParser, Tagging.Tag.class, "Tag"));
                    }
                    eventType = xmlPullParser.next();
                }
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public Tagging.TagSet fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        Tagging.TagSet tagSet = new Tagging.TagSet();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<Tagging.TagSet> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, tagSet, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "TagSet" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return tagSet;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return tagSet;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, Tagging.TagSet tagSet, String str) throws XmlPullParserException, IOException {
        if (tagSet == null) {
            return;
        }
        if (str == null) {
            str = "TagSet";
        }
        xmlSerializer.startTag("", str);
        if (tagSet.tag != null) {
            for (int i = 0; i < tagSet.tag.size(); i++) {
                QCloudXml.toXml(xmlSerializer, tagSet.tag.get(i), "Tag");
            }
        }
        xmlSerializer.endTag("", str);
    }
}
