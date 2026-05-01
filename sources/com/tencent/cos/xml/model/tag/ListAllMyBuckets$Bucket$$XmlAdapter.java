package com.tencent.cos.xml.model.tag;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.tag.ListAllMyBuckets;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class ListAllMyBuckets$Bucket$$XmlAdapter extends IXmlAdapter<ListAllMyBuckets.Bucket> {
    private HashMap<String, ChildElementBinder<ListAllMyBuckets.Bucket>> childElementBinders;

    public ListAllMyBuckets$Bucket$$XmlAdapter() {
        HashMap<String, ChildElementBinder<ListAllMyBuckets.Bucket>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.NAME_ELEMENT, new ChildElementBinder<ListAllMyBuckets.Bucket>() { // from class: com.tencent.cos.xml.model.tag.ListAllMyBuckets$Bucket$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ListAllMyBuckets.Bucket bucket, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                bucket.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Location", new ChildElementBinder<ListAllMyBuckets.Bucket>() { // from class: com.tencent.cos.xml.model.tag.ListAllMyBuckets$Bucket$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ListAllMyBuckets.Bucket bucket, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                bucket.location = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreationDate", new ChildElementBinder<ListAllMyBuckets.Bucket>() { // from class: com.tencent.cos.xml.model.tag.ListAllMyBuckets$Bucket$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ListAllMyBuckets.Bucket bucket, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                bucket.createDate = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Type", new ChildElementBinder<ListAllMyBuckets.Bucket>() { // from class: com.tencent.cos.xml.model.tag.ListAllMyBuckets$Bucket$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ListAllMyBuckets.Bucket bucket, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                bucket.type = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public ListAllMyBuckets.Bucket fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        ListAllMyBuckets.Bucket bucket = new ListAllMyBuckets.Bucket();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<ListAllMyBuckets.Bucket> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, bucket, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Bucket" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return bucket;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return bucket;
    }
}
