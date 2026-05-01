package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitConcatJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitConcatJobResponse$SubmitConcatJobResponseInput$$XmlAdapter extends IXmlAdapter<SubmitConcatJobResponse.SubmitConcatJobResponseInput> {
    private HashMap<String, ChildElementBinder<SubmitConcatJobResponse.SubmitConcatJobResponseInput>> childElementBinders;

    public SubmitConcatJobResponse$SubmitConcatJobResponseInput$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SubmitConcatJobResponse.SubmitConcatJobResponseInput>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Region", new ChildElementBinder<SubmitConcatJobResponse.SubmitConcatJobResponseInput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitConcatJobResponse$SubmitConcatJobResponseInput$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitConcatJobResponse.SubmitConcatJobResponseInput submitConcatJobResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitConcatJobResponseInput.region = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BucketId", new ChildElementBinder<SubmitConcatJobResponse.SubmitConcatJobResponseInput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitConcatJobResponse$SubmitConcatJobResponseInput$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitConcatJobResponse.SubmitConcatJobResponseInput submitConcatJobResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitConcatJobResponseInput.bucketId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Object", new ChildElementBinder<SubmitConcatJobResponse.SubmitConcatJobResponseInput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitConcatJobResponse$SubmitConcatJobResponseInput$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitConcatJobResponse.SubmitConcatJobResponseInput submitConcatJobResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitConcatJobResponseInput.object = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitConcatJobResponse.SubmitConcatJobResponseInput fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitConcatJobResponse.SubmitConcatJobResponseInput submitConcatJobResponseInput = new SubmitConcatJobResponse.SubmitConcatJobResponseInput();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitConcatJobResponse.SubmitConcatJobResponseInput> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitConcatJobResponseInput, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Input" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitConcatJobResponseInput;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitConcatJobResponseInput;
    }
}
