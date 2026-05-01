package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitTranscodeJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJobResponse$SubmitTranscodeJobResponseInput$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3625x361a67e extends IXmlAdapter<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseInput> {
    private HashMap<String, ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseInput>> childElementBinders;

    public C3625x361a67e() {
        HashMap<String, ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseInput>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Region", new ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseInput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJobResponse$SubmitTranscodeJobResponseInput$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJobResponse.SubmitTranscodeJobResponseInput submitTranscodeJobResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitTranscodeJobResponseInput.region = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BucketId", new ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseInput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJobResponse$SubmitTranscodeJobResponseInput$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJobResponse.SubmitTranscodeJobResponseInput submitTranscodeJobResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitTranscodeJobResponseInput.bucketId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Object", new ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseInput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJobResponse$SubmitTranscodeJobResponseInput$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJobResponse.SubmitTranscodeJobResponseInput submitTranscodeJobResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitTranscodeJobResponseInput.object = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitTranscodeJobResponse.SubmitTranscodeJobResponseInput fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitTranscodeJobResponse.SubmitTranscodeJobResponseInput submitTranscodeJobResponseInput = new SubmitTranscodeJobResponse.SubmitTranscodeJobResponseInput();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitTranscodeJobResponse.SubmitTranscodeJobResponseInput> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitTranscodeJobResponseInput, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Input" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitTranscodeJobResponseInput;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitTranscodeJobResponseInput;
    }
}
