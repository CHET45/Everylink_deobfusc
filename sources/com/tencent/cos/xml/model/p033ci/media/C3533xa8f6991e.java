package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitAnimationJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitAnimationJobResponse$SubmitAnimationJobResponseInput$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3533xa8f6991e extends IXmlAdapter<SubmitAnimationJobResponse.SubmitAnimationJobResponseInput> {
    private HashMap<String, ChildElementBinder<SubmitAnimationJobResponse.SubmitAnimationJobResponseInput>> childElementBinders;

    public C3533xa8f6991e() {
        HashMap<String, ChildElementBinder<SubmitAnimationJobResponse.SubmitAnimationJobResponseInput>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Region", new ChildElementBinder<SubmitAnimationJobResponse.SubmitAnimationJobResponseInput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitAnimationJobResponse$SubmitAnimationJobResponseInput$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitAnimationJobResponse.SubmitAnimationJobResponseInput submitAnimationJobResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitAnimationJobResponseInput.region = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BucketId", new ChildElementBinder<SubmitAnimationJobResponse.SubmitAnimationJobResponseInput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitAnimationJobResponse$SubmitAnimationJobResponseInput$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitAnimationJobResponse.SubmitAnimationJobResponseInput submitAnimationJobResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitAnimationJobResponseInput.bucketId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Object", new ChildElementBinder<SubmitAnimationJobResponse.SubmitAnimationJobResponseInput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitAnimationJobResponse$SubmitAnimationJobResponseInput$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitAnimationJobResponse.SubmitAnimationJobResponseInput submitAnimationJobResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitAnimationJobResponseInput.object = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitAnimationJobResponse.SubmitAnimationJobResponseInput fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitAnimationJobResponse.SubmitAnimationJobResponseInput submitAnimationJobResponseInput = new SubmitAnimationJobResponse.SubmitAnimationJobResponseInput();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitAnimationJobResponse.SubmitAnimationJobResponseInput> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitAnimationJobResponseInput, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Input" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitAnimationJobResponseInput;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitAnimationJobResponseInput;
    }
}
