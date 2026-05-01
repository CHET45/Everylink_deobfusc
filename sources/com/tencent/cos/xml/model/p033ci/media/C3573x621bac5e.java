package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitMediaInfoJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitMediaInfoJobResponse$SubmitMediaInfoJobResponseInput$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3573x621bac5e extends IXmlAdapter<SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseInput> {
    private HashMap<String, ChildElementBinder<SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseInput>> childElementBinders;

    public C3573x621bac5e() {
        HashMap<String, ChildElementBinder<SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseInput>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Region", new ChildElementBinder<SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseInput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaInfoJobResponse$SubmitMediaInfoJobResponseInput$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseInput submitMediaInfoJobResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaInfoJobResponseInput.region = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BucketId", new ChildElementBinder<SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseInput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaInfoJobResponse$SubmitMediaInfoJobResponseInput$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseInput submitMediaInfoJobResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaInfoJobResponseInput.bucketId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Object", new ChildElementBinder<SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseInput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaInfoJobResponse$SubmitMediaInfoJobResponseInput$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseInput submitMediaInfoJobResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaInfoJobResponseInput.object = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseInput fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseInput submitMediaInfoJobResponseInput = new SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseInput();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseInput> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitMediaInfoJobResponseInput, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Input" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitMediaInfoJobResponseInput;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitMediaInfoJobResponseInput;
    }
}
