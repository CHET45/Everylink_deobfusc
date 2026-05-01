package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitPicProcessJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitPicProcessJobResponse$SubmitPicProcessJobResponseInput$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3592x9ecb4064 extends IXmlAdapter<SubmitPicProcessJobResponse.SubmitPicProcessJobResponseInput> {
    private HashMap<String, ChildElementBinder<SubmitPicProcessJobResponse.SubmitPicProcessJobResponseInput>> childElementBinders;

    public C3592x9ecb4064() {
        HashMap<String, ChildElementBinder<SubmitPicProcessJobResponse.SubmitPicProcessJobResponseInput>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Region", new ChildElementBinder<SubmitPicProcessJobResponse.SubmitPicProcessJobResponseInput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitPicProcessJobResponse$SubmitPicProcessJobResponseInput$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitPicProcessJobResponse.SubmitPicProcessJobResponseInput submitPicProcessJobResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitPicProcessJobResponseInput.region = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BucketId", new ChildElementBinder<SubmitPicProcessJobResponse.SubmitPicProcessJobResponseInput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitPicProcessJobResponse$SubmitPicProcessJobResponseInput$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitPicProcessJobResponse.SubmitPicProcessJobResponseInput submitPicProcessJobResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitPicProcessJobResponseInput.bucketId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Object", new ChildElementBinder<SubmitPicProcessJobResponse.SubmitPicProcessJobResponseInput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitPicProcessJobResponse$SubmitPicProcessJobResponseInput$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitPicProcessJobResponse.SubmitPicProcessJobResponseInput submitPicProcessJobResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitPicProcessJobResponseInput.object = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitPicProcessJobResponse.SubmitPicProcessJobResponseInput fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitPicProcessJobResponse.SubmitPicProcessJobResponseInput submitPicProcessJobResponseInput = new SubmitPicProcessJobResponse.SubmitPicProcessJobResponseInput();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitPicProcessJobResponse.SubmitPicProcessJobResponseInput> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitPicProcessJobResponseInput, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Input" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitPicProcessJobResponseInput;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitPicProcessJobResponseInput;
    }
}
