package com.tencent.cos.xml.model.tag;

import com.microsoft.azure.storage.Constants;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class DescribeBucket$$XmlAdapter extends IXmlAdapter<DescribeBucket> {
    private HashMap<String, ChildElementBinder<DescribeBucket>> childElementBinders;

    public DescribeBucket$$XmlAdapter() {
        HashMap<String, ChildElementBinder<DescribeBucket>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("BucketId", new ChildElementBinder<DescribeBucket>() { // from class: com.tencent.cos.xml.model.tag.DescribeBucket$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DescribeBucket describeBucket, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                describeBucket.bucketId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.NAME_ELEMENT, new ChildElementBinder<DescribeBucket>() { // from class: com.tencent.cos.xml.model.tag.DescribeBucket$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DescribeBucket describeBucket, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                describeBucket.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Region", new ChildElementBinder<DescribeBucket>() { // from class: com.tencent.cos.xml.model.tag.DescribeBucket$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DescribeBucket describeBucket, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                describeBucket.region = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreateTime", new ChildElementBinder<DescribeBucket>() { // from class: com.tencent.cos.xml.model.tag.DescribeBucket$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DescribeBucket describeBucket, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                describeBucket.createTime = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public DescribeBucket fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        DescribeBucket describeBucket = new DescribeBucket();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<DescribeBucket> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, describeBucket, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "DescribeBucket" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return describeBucket;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return describeBucket;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, DescribeBucket describeBucket, String str) throws XmlPullParserException, IOException {
        if (describeBucket == null) {
            return;
        }
        if (str == null) {
            str = "DescribeBucket";
        }
        xmlSerializer.startTag("", str);
        if (describeBucket.bucketId != null) {
            xmlSerializer.startTag("", "BucketId");
            xmlSerializer.text(String.valueOf(describeBucket.bucketId));
            xmlSerializer.endTag("", "BucketId");
        }
        if (describeBucket.name != null) {
            xmlSerializer.startTag("", Constants.NAME_ELEMENT);
            xmlSerializer.text(String.valueOf(describeBucket.name));
            xmlSerializer.endTag("", Constants.NAME_ELEMENT);
        }
        if (describeBucket.region != null) {
            xmlSerializer.startTag("", "Region");
            xmlSerializer.text(String.valueOf(describeBucket.region));
            xmlSerializer.endTag("", "Region");
        }
        if (describeBucket.createTime != null) {
            xmlSerializer.startTag("", "CreateTime");
            xmlSerializer.text(String.valueOf(describeBucket.createTime));
            xmlSerializer.endTag("", "CreateTime");
        }
        xmlSerializer.endTag("", str);
    }
}
