package com.tencent.cos.xml.model.tag;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class DescribeDocProcessBuckets$$XmlAdapter extends IXmlAdapter<DescribeDocProcessBuckets> {
    private HashMap<String, ChildElementBinder<DescribeDocProcessBuckets>> childElementBinders;

    public DescribeDocProcessBuckets$$XmlAdapter() {
        HashMap<String, ChildElementBinder<DescribeDocProcessBuckets>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("RequestId", new ChildElementBinder<DescribeDocProcessBuckets>() { // from class: com.tencent.cos.xml.model.tag.DescribeDocProcessBuckets$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DescribeDocProcessBuckets describeDocProcessBuckets, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                describeDocProcessBuckets.requestId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("TotalCount", new ChildElementBinder<DescribeDocProcessBuckets>() { // from class: com.tencent.cos.xml.model.tag.DescribeDocProcessBuckets$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DescribeDocProcessBuckets describeDocProcessBuckets, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                describeDocProcessBuckets.totalCount = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("PageNumber", new ChildElementBinder<DescribeDocProcessBuckets>() { // from class: com.tencent.cos.xml.model.tag.DescribeDocProcessBuckets$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DescribeDocProcessBuckets describeDocProcessBuckets, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                describeDocProcessBuckets.pageNumber = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("PageSize", new ChildElementBinder<DescribeDocProcessBuckets>() { // from class: com.tencent.cos.xml.model.tag.DescribeDocProcessBuckets$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DescribeDocProcessBuckets describeDocProcessBuckets, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                describeDocProcessBuckets.pageSize = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("DocBucketList", new ChildElementBinder<DescribeDocProcessBuckets>() { // from class: com.tencent.cos.xml.model.tag.DescribeDocProcessBuckets$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DescribeDocProcessBuckets describeDocProcessBuckets, String str) throws XmlPullParserException, IOException {
                if (describeDocProcessBuckets.docBucketList == null) {
                    describeDocProcessBuckets.docBucketList = new ArrayList();
                }
                describeDocProcessBuckets.docBucketList.add((BucketDocumentPreviewState) QCloudXml.fromXml(xmlPullParser, BucketDocumentPreviewState.class, "DocBucketList"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public DescribeDocProcessBuckets fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        DescribeDocProcessBuckets describeDocProcessBuckets = new DescribeDocProcessBuckets();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<DescribeDocProcessBuckets> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, describeDocProcessBuckets, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return describeDocProcessBuckets;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return describeDocProcessBuckets;
    }
}
