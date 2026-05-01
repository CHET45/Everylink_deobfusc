package com.tencent.cos.xml.model.tag;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.tag.DescribeMediaBucketsResult;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class DescribeMediaBucketsResult$MediaBucketList$$XmlAdapter extends IXmlAdapter<DescribeMediaBucketsResult.MediaBucketList> {
    private HashMap<String, ChildElementBinder<DescribeMediaBucketsResult.MediaBucketList>> childElementBinders;

    public DescribeMediaBucketsResult$MediaBucketList$$XmlAdapter() {
        HashMap<String, ChildElementBinder<DescribeMediaBucketsResult.MediaBucketList>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("BucketId", new ChildElementBinder<DescribeMediaBucketsResult.MediaBucketList>() { // from class: com.tencent.cos.xml.model.tag.DescribeMediaBucketsResult$MediaBucketList$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DescribeMediaBucketsResult.MediaBucketList mediaBucketList, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaBucketList.bucketId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.NAME_ELEMENT, new ChildElementBinder<DescribeMediaBucketsResult.MediaBucketList>() { // from class: com.tencent.cos.xml.model.tag.DescribeMediaBucketsResult$MediaBucketList$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DescribeMediaBucketsResult.MediaBucketList mediaBucketList, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaBucketList.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Region", new ChildElementBinder<DescribeMediaBucketsResult.MediaBucketList>() { // from class: com.tencent.cos.xml.model.tag.DescribeMediaBucketsResult$MediaBucketList$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DescribeMediaBucketsResult.MediaBucketList mediaBucketList, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaBucketList.region = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreateTime", new ChildElementBinder<DescribeMediaBucketsResult.MediaBucketList>() { // from class: com.tencent.cos.xml.model.tag.DescribeMediaBucketsResult$MediaBucketList$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DescribeMediaBucketsResult.MediaBucketList mediaBucketList, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                mediaBucketList.createTime = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public DescribeMediaBucketsResult.MediaBucketList fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        DescribeMediaBucketsResult.MediaBucketList mediaBucketList = new DescribeMediaBucketsResult.MediaBucketList();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<DescribeMediaBucketsResult.MediaBucketList> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, mediaBucketList, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "MediaBucketList" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return mediaBucketList;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return mediaBucketList;
    }
}
