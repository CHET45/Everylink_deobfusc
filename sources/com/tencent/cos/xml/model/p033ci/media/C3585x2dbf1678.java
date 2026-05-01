package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitMediaSegmentJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitMediaSegmentJobResponse$SubmitMediaSegmentJobResponseInput$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3585x2dbf1678 extends IXmlAdapter<SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseInput> {
    private HashMap<String, ChildElementBinder<SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseInput>> childElementBinders;

    public C3585x2dbf1678() {
        HashMap<String, ChildElementBinder<SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseInput>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Region", new ChildElementBinder<SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseInput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaSegmentJobResponse$SubmitMediaSegmentJobResponseInput$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseInput submitMediaSegmentJobResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaSegmentJobResponseInput.region = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BucketId", new ChildElementBinder<SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseInput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaSegmentJobResponse$SubmitMediaSegmentJobResponseInput$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseInput submitMediaSegmentJobResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaSegmentJobResponseInput.bucketId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Object", new ChildElementBinder<SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseInput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaSegmentJobResponse$SubmitMediaSegmentJobResponseInput$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseInput submitMediaSegmentJobResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaSegmentJobResponseInput.object = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseInput fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseInput submitMediaSegmentJobResponseInput = new SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseInput();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseInput> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitMediaSegmentJobResponseInput, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Input" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitMediaSegmentJobResponseInput;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitMediaSegmentJobResponseInput;
    }
}
