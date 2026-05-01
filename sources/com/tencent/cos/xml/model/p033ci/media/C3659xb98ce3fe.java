package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitVoiceSeparateJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitVoiceSeparateJobResponse$SubmitVoiceSeparateJobResponseInput$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3659xb98ce3fe extends IXmlAdapter<SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseInput> {
    private HashMap<String, ChildElementBinder<SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseInput>> childElementBinders;

    public C3659xb98ce3fe() {
        HashMap<String, ChildElementBinder<SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseInput>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Region", new ChildElementBinder<SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseInput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVoiceSeparateJobResponse$SubmitVoiceSeparateJobResponseInput$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseInput submitVoiceSeparateJobResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVoiceSeparateJobResponseInput.region = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BucketId", new ChildElementBinder<SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseInput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVoiceSeparateJobResponse$SubmitVoiceSeparateJobResponseInput$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseInput submitVoiceSeparateJobResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVoiceSeparateJobResponseInput.bucketId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Object", new ChildElementBinder<SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseInput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVoiceSeparateJobResponse$SubmitVoiceSeparateJobResponseInput$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseInput submitVoiceSeparateJobResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVoiceSeparateJobResponseInput.object = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseInput fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseInput submitVoiceSeparateJobResponseInput = new SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseInput();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitVoiceSeparateJobResponse.SubmitVoiceSeparateJobResponseInput> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitVoiceSeparateJobResponseInput, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Input" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitVoiceSeparateJobResponseInput;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitVoiceSeparateJobResponseInput;
    }
}
