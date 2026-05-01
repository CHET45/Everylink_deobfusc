package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitExtractDigitalWatermarkJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitExtractDigitalWatermarkJobResponse$SubmitExtractDigitalWatermarkJobResponseInput$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3569x867186be extends IXmlAdapter<SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseInput> {
    private HashMap<String, ChildElementBinder<SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseInput>> childElementBinders;

    public C3569x867186be() {
        HashMap<String, ChildElementBinder<SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseInput>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Region", new ChildElementBinder<SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseInput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitExtractDigitalWatermarkJobResponse$SubmitExtractDigitalWatermarkJobResponseInput$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseInput submitExtractDigitalWatermarkJobResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitExtractDigitalWatermarkJobResponseInput.region = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BucketId", new ChildElementBinder<SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseInput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitExtractDigitalWatermarkJobResponse$SubmitExtractDigitalWatermarkJobResponseInput$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseInput submitExtractDigitalWatermarkJobResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitExtractDigitalWatermarkJobResponseInput.bucketId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Object", new ChildElementBinder<SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseInput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitExtractDigitalWatermarkJobResponse$SubmitExtractDigitalWatermarkJobResponseInput$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseInput submitExtractDigitalWatermarkJobResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitExtractDigitalWatermarkJobResponseInput.object = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseInput fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseInput submitExtractDigitalWatermarkJobResponseInput = new SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseInput();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitExtractDigitalWatermarkJobResponse.SubmitExtractDigitalWatermarkJobResponseInput> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitExtractDigitalWatermarkJobResponseInput, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Input" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitExtractDigitalWatermarkJobResponseInput;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitExtractDigitalWatermarkJobResponseInput;
    }
}
