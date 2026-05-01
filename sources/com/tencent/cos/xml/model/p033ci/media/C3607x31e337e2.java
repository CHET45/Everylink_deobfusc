package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitSnapshotJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitSnapshotJobResponse$SubmitSnapshotJobResponseInput$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3607x31e337e2 extends IXmlAdapter<SubmitSnapshotJobResponse.SubmitSnapshotJobResponseInput> {
    private HashMap<String, ChildElementBinder<SubmitSnapshotJobResponse.SubmitSnapshotJobResponseInput>> childElementBinders;

    public C3607x31e337e2() {
        HashMap<String, ChildElementBinder<SubmitSnapshotJobResponse.SubmitSnapshotJobResponseInput>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Region", new ChildElementBinder<SubmitSnapshotJobResponse.SubmitSnapshotJobResponseInput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSnapshotJobResponse$SubmitSnapshotJobResponseInput$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSnapshotJobResponse.SubmitSnapshotJobResponseInput submitSnapshotJobResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSnapshotJobResponseInput.region = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BucketId", new ChildElementBinder<SubmitSnapshotJobResponse.SubmitSnapshotJobResponseInput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSnapshotJobResponse$SubmitSnapshotJobResponseInput$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSnapshotJobResponse.SubmitSnapshotJobResponseInput submitSnapshotJobResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSnapshotJobResponseInput.bucketId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Object", new ChildElementBinder<SubmitSnapshotJobResponse.SubmitSnapshotJobResponseInput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSnapshotJobResponse$SubmitSnapshotJobResponseInput$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSnapshotJobResponse.SubmitSnapshotJobResponseInput submitSnapshotJobResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSnapshotJobResponseInput.object = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitSnapshotJobResponse.SubmitSnapshotJobResponseInput fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitSnapshotJobResponse.SubmitSnapshotJobResponseInput submitSnapshotJobResponseInput = new SubmitSnapshotJobResponse.SubmitSnapshotJobResponseInput();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitSnapshotJobResponse.SubmitSnapshotJobResponseInput> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitSnapshotJobResponseInput, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Input" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitSnapshotJobResponseInput;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitSnapshotJobResponseInput;
    }
}
