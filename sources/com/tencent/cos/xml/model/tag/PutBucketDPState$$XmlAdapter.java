package com.tencent.cos.xml.model.tag;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class PutBucketDPState$$XmlAdapter extends IXmlAdapter<PutBucketDPState> {
    private HashMap<String, ChildElementBinder<PutBucketDPState>> childElementBinders;

    public PutBucketDPState$$XmlAdapter() {
        HashMap<String, ChildElementBinder<PutBucketDPState>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("RequestId", new ChildElementBinder<PutBucketDPState>() { // from class: com.tencent.cos.xml.model.tag.PutBucketDPState$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PutBucketDPState putBucketDPState, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                putBucketDPState.RequestId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("DocBucket", new ChildElementBinder<PutBucketDPState>() { // from class: com.tencent.cos.xml.model.tag.PutBucketDPState$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PutBucketDPState putBucketDPState, String str) throws XmlPullParserException, IOException {
                putBucketDPState.DocBucket = (BucketDocumentPreviewState) QCloudXml.fromXml(xmlPullParser, BucketDocumentPreviewState.class, "DocBucket");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PutBucketDPState fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PutBucketDPState putBucketDPState = new PutBucketDPState();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PutBucketDPState> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, putBucketDPState, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return putBucketDPState;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return putBucketDPState;
    }
}
