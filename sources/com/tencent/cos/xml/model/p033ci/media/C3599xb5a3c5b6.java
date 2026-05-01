package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitSmartCoverJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitSmartCoverJobResponse$SubmitSmartCoverJobResponseInput$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3599xb5a3c5b6 extends IXmlAdapter<SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseInput> {
    private HashMap<String, ChildElementBinder<SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseInput>> childElementBinders;

    public C3599xb5a3c5b6() {
        HashMap<String, ChildElementBinder<SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseInput>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Region", new ChildElementBinder<SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseInput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSmartCoverJobResponse$SubmitSmartCoverJobResponseInput$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseInput submitSmartCoverJobResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSmartCoverJobResponseInput.region = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BucketId", new ChildElementBinder<SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseInput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSmartCoverJobResponse$SubmitSmartCoverJobResponseInput$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseInput submitSmartCoverJobResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSmartCoverJobResponseInput.bucketId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Object", new ChildElementBinder<SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseInput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSmartCoverJobResponse$SubmitSmartCoverJobResponseInput$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseInput submitSmartCoverJobResponseInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSmartCoverJobResponseInput.object = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseInput fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseInput submitSmartCoverJobResponseInput = new SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseInput();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseInput> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitSmartCoverJobResponseInput, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Input" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitSmartCoverJobResponseInput;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitSmartCoverJobResponseInput;
    }
}
