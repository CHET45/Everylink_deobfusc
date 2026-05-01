package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitDigitalWatermarkJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitDigitalWatermarkJobResponse$SubmitDigitalWatermarkJobResponseInput$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3561xe7a5f2b2 extends IXmlAdapter<SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseInput> {
    private HashMap<String, ChildElementBinder<SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseInput>> childElementBinders;

    public C3561xe7a5f2b2() {
        HashMap<String, ChildElementBinder<SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseInput>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Region", new ChildElementBinder<SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseInput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitDigitalWatermarkJobResponse$SubmitDigitalWatermarkJobResponseInput$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseInput submitDigitalWatermarkJobResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitDigitalWatermarkJobResponseInput.region = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BucketId", new ChildElementBinder<SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseInput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitDigitalWatermarkJobResponse$SubmitDigitalWatermarkJobResponseInput$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseInput submitDigitalWatermarkJobResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitDigitalWatermarkJobResponseInput.bucketId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Object", new ChildElementBinder<SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseInput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitDigitalWatermarkJobResponse$SubmitDigitalWatermarkJobResponseInput$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseInput submitDigitalWatermarkJobResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitDigitalWatermarkJobResponseInput.object = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseInput fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseInput submitDigitalWatermarkJobResponseInput = new SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseInput();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitDigitalWatermarkJobResponse.SubmitDigitalWatermarkJobResponseInput> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitDigitalWatermarkJobResponseInput, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Input" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitDigitalWatermarkJobResponseInput;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitDigitalWatermarkJobResponseInput;
    }
}
